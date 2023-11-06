package com.example.hsai_project.fragments;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.hsai_project.ProductDao;
import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.ProductListViewModel;
import com.example.hsai_project.R;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class ProductItemView extends Fragment {
    private ProductListViewModel productListView;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View product = inflater.inflate(R.layout.fragment_product_item_view, container, false);
        ProductItemViewArgs args = ProductItemViewArgs.fromBundle(getArguments());

        int productId = args.getProductId();


        final ImageView productImageView = product.findViewById(R.id.product_image_view);
        /*Glide.with(getContext()).load(productImage).into(productImageView);*/
        /*TextView productNameView = product.findViewById(R.id.product_name_view);*/
        /*productNameView.setText(productName);*/
        final TextView productPriceView = product.findViewById(R.id.product_price_view);
        /*productPriceView.setText(String.valueOf(productPrice));*/
        final TextView productNameView = product.findViewById(R.id.product_name_view);

        ProductDatabase db = ProductDatabase.getInstance(getContext());
        final LiveData<List<ProductEntity>> m_data = db.productDao().getFullProduct(productId);

        final ImageView addToWishlistIcon = product.findViewById(R.id.product_view_addToWishlist);

        final ImageView addToShoppingcart = product.findViewById(R.id.product_view_shoppingcart);

        final ExpandableTextView expandableTextView = (ExpandableTextView) product.findViewById(R.id.expandable_description);

        final ImageView expandIcon = product.findViewById(R.id.expand_description_button);


        final TextView  descriptionButton = product.findViewById(R.id.product_view_description_text);
        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableTextView.isExpanded())
                {
                    expandableTextView.collapse();
                    expandIcon.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else
                {
                    expandableTextView.expand();
                    expandIcon.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });


        m_data.observe(getViewLifecycleOwner(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                if (m_data.getValue() == null){

                }
                else {
                    productNameView.setText(m_data.getValue().get(0).getProductName());
                    Glide.with(getContext()).load(m_data.getValue().get(0).getImage()).into(productImageView);
                    productPriceView.setText(String.valueOf(m_data.getValue().get(0).getPrice()));
                    expandableTextView.setText(m_data.getValue().get(0).getDescription());
                }
            }
        });

        addToWishlistIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductEntity productEntity = m_data.getValue().get(0);
                boolean isWishlist = m_data.getValue().get(0).isWishlist();
                if(!isWishlist){
                    Toast.makeText(getContext(),productEntity.getProductName() + " has been added to the wishlist", Toast.LENGTH_SHORT).show();
                    addToWishlistIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
                    addToWishlist(productEntity.getId());
                }
                else{
                    addToWishlistIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    removeFromWishlist(productEntity.getId());
                }
            }
        });

        addToWishlistIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductEntity productEntity = m_data.getValue().get(0);
                boolean isWishlist = m_data.getValue().get(0).isWishlist();
                if(!isWishlist){
                    Toast.makeText(getContext(),productEntity.getProductName() + " has been added to the wishlist", Toast.LENGTH_SHORT).show();
                    addToWishlistIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
                    addToWishlist(productEntity.getId());
                }
                else{
                    addToWishlistIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    removeFromWishlist(productEntity.getId());
                }
            }
        });

        addToShoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_data == null)
                    return;
                ProductEntity productEntity = m_data.getValue().get(0);
                int incart = m_data.getValue().get(0).getInCart();

                if(incart > 0){
                    Toast.makeText(getContext(),productEntity.getProductName() + "Product zit al in winkelwagen", Toast.LENGTH_SHORT).show();
                }else{
                    new addShoppincartAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(productEntity);
                    Toast.makeText(getContext(),productEntity.getProductName() + " toegevoegd aan winkelwagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        return product;
    }


    private void addToWishlist(int id){
        new ProductItemView.UpdateWishlistAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(id);
    }

    private void removeFromWishlist(int id){
        new ProductItemView.DeleteWishlistAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(id);
    }
    

    private static class addShoppincartAsyncTask extends AsyncTask<ProductEntity, Void, Void> {
        private ProductDao productDao;

        private addShoppincartAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductEntity... entities) {
            for (int i = 0; i < entities.length; ++i) {
                entities[i].setInCart(entities[i].getInCart() + 1);
                productDao.update(entities[i]);
            }
            return null;
        }
    }

    private static class UpdateWishlistAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ProductDao productDao;
        private UpdateWishlistAsyncTask(ProductDao productDao){
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

    private static class DeleteWishlistAsyncTask extends AsyncTask<Integer, Void, Void>{
        private ProductDao productDao;
        private DeleteWishlistAsyncTask(ProductDao productDao){
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
}
