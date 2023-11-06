package com.example.hsai_project.fragments.shops;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hsai_project.R;

public class ShopsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shops, container, false);

        getChildFragmentManager().beginTransaction().add(R.id.shops_grid, new ShopsItemFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.shops_grid, new ShopsItemFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.shops_grid, new ShopsItemFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.shops_grid, new ShopsItemFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.shops_grid, new ShopsItemFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.shops_grid, new ShopsItemFragment()).commit();

        return root;
    }
}
