package com.example.hsai_project.fragments.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.hsai_project.R;

import java.util.ArrayList;
import java.util.List;

public class FilterMerk extends FilterInfo {

    String[] merken = {"Apple", "HP", "Asus", "Dell", "Lenovo", "Samsung", "Sony", "Acer"};
    List<checkmarkFrag> m_frags = new ArrayList<>();

    public FilterMerk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_filter_merk, container, false);

        for(int i = 0; i < merken.length;++i){
            checkmarkFrag frag = new checkmarkFrag(merken[i]);
            getChildFragmentManager().beginTransaction().add(R.id.sort_merk_container, frag).commit();
            m_frags.add(frag);
        }

        Button reset = (Button)root.findViewById(R.id.sort_item_resetbutton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i < m_frags.size();++i){
                    m_frags.get(i).setActivated(true);
                }
            }
        });

        return root;
    }

    public static class checkmarkFrag extends Fragment{
        String m_name;
        CheckBox m_box;

        checkmarkFrag(String name){
            m_name = name;
        }

        public void setActivated(boolean state){
            m_box.setChecked(state);
        }

        public boolean getState(){
            return m_box.isChecked();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root =  inflater.inflate(R.layout.fragment_checkbox, container, false);

            m_box = (CheckBox)root.findViewById(R.id.checkbox);
            m_box.setText(m_name);
            m_box.setChecked(true);

            return root;
        }
    }
}
