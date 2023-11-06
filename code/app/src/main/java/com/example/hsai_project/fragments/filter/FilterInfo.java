package com.example.hsai_project.fragments.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;

import java.util.List;

public class FilterInfo extends Fragment {
    public FilterInfo() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return null;
    }

    List<ProductEntity> filterItems(List<ProductEntity> items){
        return items;
    }

}
