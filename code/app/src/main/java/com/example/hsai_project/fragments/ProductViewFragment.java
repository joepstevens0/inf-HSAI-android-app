package com.example.hsai_project.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsai_project.ProductDao;
import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;

import com.bumptech.glide.Glide;
import com.example.hsai_project.fragments.explore.ExploreFragmentDirections;

public class ProductViewFragment extends Fragment {

    private boolean m_fav = false;
    private ProductEntity m_product;


    public ProductViewFragment(ProductEntity product){
        m_product = product;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_product_view, container, false);
        // favorite button
        ImageView fav = (ImageView) root.findViewById(R.id.product_fav_button);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite(v);
            }
        });
        if(m_product.isWishlist())
            fav.setImageResource(R.drawable.ic_favorite_black_24dp);

        TextView name = (TextView) root.findViewById(R.id.product_text);
        name.setText(m_product.getProductName());
        TextView price = (TextView) root.findViewById(R.id.product_price);
        price.setText(String.valueOf(m_product.getPrice()));

        ImageView image = (ImageView) root.findViewById(R.id.product_image);
        Glide.with(image.getContext()).load(m_product.getImage()).into(image);

        ImageView compare = (ImageView)root.findViewById(R.id.vergelijk_icon);
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean inCompare = m_product.isInCompare();
                LiveData<Integer> counter = comparedItems();
                counter.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer == null)
                            return;
                        if((!inCompare) && (integer < 2)){
                            Toast.makeText(getContext(), m_product.getProductName() + " is toegevoegd aan vergelijking", Toast.LENGTH_SHORT).show();
                            m_product.setInCompare(true);
                            addCompareItem();
                        }
                        else if(inCompare){
                            Toast.makeText(getContext(), m_product.getProductName() + " zit al in vergelijking", Toast.LENGTH_SHORT).show();
                        }
                        else if (integer == 2){
                            Toast.makeText(getContext(), "je kan niks meer toevoegen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreFragmentDirections.ActionBottomNavExploreToProductItemView action = ExploreFragmentDirections.actionBottomNavExploreToProductItemView(m_product.getId());
                Navigation.findNavController(root).navigate(action);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // check update name and price from database
    }

    private void toggleFavorite(View v){
        ImageView view = ((ImageView)v);
        if(m_product.isWishlist()) {
            view.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            removeFromWishlist();
        }
        else {
            addToWishlist();
            view.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }


    private void addToWishlist(){
       new UpdateWishlistAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(m_product.getId());
    }

    private void removeFromWishlist(){
       new DeleteWishlistAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(m_product.getId());
    }

    protected static class UpdateWishlistAsyncTask extends AsyncTask<Integer, Void, Void>{
        private ProductDao productDao;
        protected UpdateWishlistAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(Integer... entities) {
            for(int i = 0; i < entities.length;++i) {
                productDao.addToWishlist(entities[i]);
            }
            return null;
        }
    }

    public static class DeleteWishlistAsyncTask extends AsyncTask<Integer, Void, Void>{
        private ProductDao productDao;
        protected DeleteWishlistAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(Integer... ids) {
            for(int i = 0; i < ids.length;++i) {
                productDao.removeFromWishlist(ids[i]);
            }
            return null;
        }
    }

    private LiveData<Integer> comparedItems(){
        ProductDatabase db = ProductDatabase.getInstance(getContext());
        return db.productDao().getCompared();
    }

    private void addCompareItem(){
        ProductDatabase db = ProductDatabase.getInstance(getContext());

        new UpdateItemAsyncTask(db.productDao()).execute(m_product);
    }

    protected static class UpdateItemAsyncTask extends AsyncTask<ProductEntity, Void, Void>{
        private ProductDao productDao;
        protected UpdateItemAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductEntity... entities) {
            for(int i = 0; i < entities.length;++i) {
                productDao.update(entities[i]);
            }
            return null;
        }
    }

}
