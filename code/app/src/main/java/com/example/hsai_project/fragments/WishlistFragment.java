package com.example.hsai_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.ProductListAdapter;
import com.example.hsai_project.ProductListViewModel;
import com.example.hsai_project.R;
import com.example.hsai_project.WishlistAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class WishlistFragment extends Fragment {

    private ProductListViewModel productListViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_wishlist, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.wishlistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final WishlistAdapter adapter = new WishlistAdapter();
        recyclerView.setAdapter(adapter);
        productListViewModel = ViewModelProviders.of(WishlistFragment.this).get(ProductListViewModel.class);

        adapter.setOnItemDeleteClickListener(new WishlistAdapter.OnitemDeleteClickListener() {
            @Override
            public void onItemDeleteClick(ProductEntity productEntity) {
                boolean isWishlist = productEntity.isWishlist();
                if(isWishlist){
                    /*Toast.makeText(getContext(),productEntity.getProductName() + " has been deleted", Toast.LENGTH_SHORT).show();*/
                    Snackbar removeElement = Snackbar.make(root, productEntity.getProductName() + " is verwijderd", Snackbar.LENGTH_LONG);
                    removeElement.show();
                }

                productEntity.setWishlist(!isWishlist);
                productListViewModel.update(productEntity);
            }
        });

        productListViewModel.getAllWishlistProducts().observe(getViewLifecycleOwner(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                adapter.setWishlistproducts(productEntities);
            }
        });

        /*final TextView textView = root.findViewById(R.id.text_wishlist);
        textView.setText("wishlist");*/

        return root;
    }

    public void onClick(View v){
        Toast.makeText(getContext(), "trying to remove", Toast.LENGTH_SHORT);
    }
}