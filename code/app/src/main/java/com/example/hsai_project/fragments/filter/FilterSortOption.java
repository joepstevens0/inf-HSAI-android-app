package com.example.hsai_project.fragments.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FilterSortOption extends FilterInfo {

    private String m_option;

    public FilterSortOption() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_filter_sort_option, container, false);

        Spinner spin = (Spinner)root.findViewById(R.id.sort_option_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_option_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_option = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return root;
    }

    List<ProductEntity> filterItems(List<ProductEntity> items){
        // bubble sort
        for(int i  = 0; i < items.size();++i){
            for(int j = i; j < items.size()-1;++j){
                ProductEntity o1 = items.get(j);
                ProductEntity o2 = items.get(j+1);
                if(!compare(o1,o2)){
                    items.set(j, o2);
                    items.set(j+1, o1);
                }

            }
        }
        return items;
    }

    private boolean compare(ProductEntity o1, ProductEntity o2){
        if(m_option == getResources().getString(R.string.sortoption_lowtohigh))
            return o1.getPrice() < o2.getPrice();
        if(m_option == getResources().getString(R.string.sortoption_hightolow))
            return o1.getPrice() > o2.getPrice();
        if(m_option == getResources().getString(R.string.sortoption_alf))
            return o1.getProductName().compareTo(o2.getProductName()) == -1;
        return true;
    }

}
