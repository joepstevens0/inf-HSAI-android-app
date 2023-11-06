package com.example.hsai_project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.ProductListAdapter;
import com.example.hsai_project.ProductListViewModel;
import com.example.hsai_project.R;
import com.example.hsai_project.fragments.filter.FilterList;

import java.util.List;
import java.util.Vector;

public class ProductListFragment extends Fragment {
    private ProductListViewModel productListViewModel;
    public Vector<ProductEntity> inCompareProducts = new Vector<>();

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View product = inflater.inflate(R.layout.product_list, container, false);
        /*product.setMinimumWidth(20);*/

        RecyclerView recyclerView = product.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        final ProductListAdapter adapter = new ProductListAdapter();
        recyclerView.setAdapter(adapter);
        productListViewModel = ViewModelProviders.of(ProductListFragment.this).get(ProductListViewModel.class);
        final ImageView filterButton = product.findViewById(R.id.filter_image_list);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FilterList filterList = new FilterList();
                getChildFragmentManager().beginTransaction().add(R.id.filter_container, filterList).commit();
            }
        });



        adapter.setOnItemClickListener(new ProductListAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(ProductEntity productEntity) {
                ProductListFragmentDirections.ActionProductListToProductItemView action = ProductListFragmentDirections.actionProductListToProductItemView(productEntity.getId());
                Navigation.findNavController(product).navigate(action);
            }
        });

        adapter.setOnItemFavClickListener(new ProductListAdapter.OnitemFavClickListener() {
            @Override
            public void onItemFavClick(ProductEntity productEntity) {

                boolean isWishlist = productEntity.isWishlist();
                if(!isWishlist){
                    Toast.makeText(getContext(),productEntity.getProductName() + " has been added to the wishlist", Toast.LENGTH_SHORT).show();
                }

                productEntity.setWishlist(!isWishlist);
                productListViewModel.update(productEntity);

            }
        });

        adapter.setOnItemCompareClickListener(new ProductListAdapter.OnitemCompareClickListener() {
            @Override
            public void onItemCompareClick(final ProductEntity productEntity) {
                final boolean inCompare = productEntity.isInCompare();
                LiveData<Integer> counter = comparedItems();
                counter.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer == null)
                            return;
                        if((!inCompare) && (integer < 2)){
                            Toast.makeText(getContext(), productEntity.getProductName() + " is toegevoegd aan vergelijking", Toast.LENGTH_SHORT).show();
                            productEntity.setInCompare(true);
                            productListViewModel.update(productEntity);
                        }
                        else if(inCompare){
                            Toast.makeText(getContext(), productEntity.getProductName() + " zit al in vergelijking", Toast.LENGTH_SHORT).show();
                        }
                        else if (integer == 2){
                            Toast.makeText(getContext(), "je kan niks meer toevoegen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        productListViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                adapter.setProducts(productEntities);
                /*Toast.makeText(getContext(), "something changed", Toast.LENGTH_SHORT).show();*/
            }
        });

        ImageView goToCompare = product.findViewById(R.id.compare_image_navigator);

        goToCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(product).navigate(R.id.list_nav_compare);
            }
        });

        return product;
    }

    private LiveData<Integer> comparedItems(){
        ProductDatabase db = ProductDatabase.getInstance(getContext());
        return db.productDao().getCompared();
    }
}
