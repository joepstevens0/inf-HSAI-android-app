package com.example.hsai_project.fragments.filter;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsai_project.R;

public class FilterItem extends Fragment {
    private boolean m_closed = true;
    private String m_name;

    FilterInfo m_frag;

    public FilterItem(FilterInfo frag, String name) {
        m_frag = frag;
        m_name = name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_filter_item, container, false);

        TextView name= (TextView)root.findViewById(R.id.sort_item_name);
        name.setText(m_name);

        CardView dropdown = (CardView)root.findViewById(R.id.sort_item_dropdown);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCard(v);
            }
        });

        return root;
    }

    private void openCard(View v) {
        m_closed = !m_closed;
        if (m_closed) {
            getChildFragmentManager().beginTransaction().remove(m_frag).commit();
            ImageView i = (ImageView) v.findViewById(R.id.sort_item_dropdownicon);
            i.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        } else{
            getChildFragmentManager().beginTransaction().add(R.id.sort_item_fragmentcontainer, m_frag).commit();
            ImageView i = (ImageView) v.findViewById(R.id.sort_item_dropdownicon);
            i.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
    }
}
