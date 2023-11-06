package com.example.hsai_project.fragments.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;

import java.util.ArrayList;
import java.util.List;

public class FilterList extends Fragment {
    private List<FilterInfo> m_frags = new ArrayList<>();
    private List<ProductEntity> m_list;


    public FilterList() {
        // Required empty public constructor
    }

    public void setList(List<ProductEntity> list){
        m_list = list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_filter_list, container, false);

        ImageView close_filter = root.findViewById(R.id.close_filter);
        close_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(FilterList.this).commit();
            }
        });

        Button apply = (Button)root.findViewById(R.id.sort_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter();
                getFragmentManager().beginTransaction().remove(FilterList.this).commit();
            }
        });

        addFrag(new FilterPrice(), "Prijs");
        addFrag(new FilterSortOption(), "Sorteren op");
        addFrag(new FilterMerk(), "Merk");
        addFrag(new FilterScreensize(), "Schermgrootte");


        return root;
    }

    private void addFrag(FilterInfo frag, String name){
        m_frags.add(frag);

        getChildFragmentManager().beginTransaction().add(R.id.sort_list,new FilterItem(frag, name)).commit();
    }
    private void filter(){
        if(m_list == null)
            return;
        for(int i = 0 ;i < m_frags.size();++i){
            m_list = m_frags.get(i).filterItems(m_list);
        }
    }


}
