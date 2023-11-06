package com.example.hsai_project.fragments.shops;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;
import com.example.hsai_project.fragments.explore.ExploreScrollFragment;


public class ShopsItemFragment extends Fragment {

    public ShopsItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_shops_item, container, false);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("StoreId", 0);
                Navigation.findNavController(root).navigate(R.id.store_fragment, bundle);
            }
        });

        return root;
    }
}
