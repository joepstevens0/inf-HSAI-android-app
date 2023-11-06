package com.example.hsai_project.fragments.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;

import java.util.List;

public class FilterScreensize extends FilterInfo {

    int m_min = 9;
    int m_max = 19;
    public FilterScreensize() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_filter_screensize, container, false);

        final CrystalRangeSeekbar sb = (CrystalRangeSeekbar)root.findViewById(R.id.sort_screen_rangeSeekbar);
        final TextView max = (TextView)root.findViewById(R.id.sort_screen_max);
        final TextView min = (TextView)root.findViewById(R.id.sort_screen_min);
        sb.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                min.setText("min:" + minValue.toString());
                max.setText("max:" + ((maxValue.intValue() >= 2000)? " +" : "") + maxValue.toString() );
                m_min = minValue.intValue();
                m_max = maxValue.intValue();
            }
        });

        Button reset = (Button)root.findViewById(R.id.sort_item_resetbutton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Prijs reset", Toast.LENGTH_SHORT).show();
                sb.setMinValue(9).apply();
                sb.setMaxValue(19).apply();
            }
        });

        return root;
    }

    List<ProductEntity> filterItems(List<ProductEntity> items){
        // TODO
        return items;
    }
}
