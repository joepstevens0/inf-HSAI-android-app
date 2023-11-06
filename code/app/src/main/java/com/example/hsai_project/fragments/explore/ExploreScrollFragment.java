package com.example.hsai_project.fragments.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;
import com.example.hsai_project.fragments.ProductViewFragment;

import java.util.ArrayList;
import java.util.List;


public class ExploreScrollFragment extends Fragment {

    private LiveData<List<ProductEntity>> m_items;
    private String m_catname;
    private List<ProductViewFragment> m_frags = new ArrayList<>();


    public ExploreScrollFragment(LiveData<List<ProductEntity>> items, String catname){
        m_items = items;
        m_catname = catname;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View root =  inflater.inflate(R.layout.fragment_explore_scroll, container, false);
        final HorizontalScrollView hsv = root.findViewById(R.id.explore_scroll_view);
        ImageView right = root.findViewById(R.id.explore_scroll_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hsv.smoothScrollBy(300,0);
            }
        });

        ImageView left = root.findViewById(R.id.explore_scroll_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hsv.smoothScrollBy(-300,0);
            }
        });

        TextView cat = (TextView) root.findViewById(R.id.explore_scroll_cat);
        cat.setText(m_catname);

        m_items.observe(getViewLifecycleOwner(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                addFrags(root);
            }
        });

        return root;
    }

    private void addFrags(View root){
        if(m_items.getValue() == null)
            return;
        FragmentManager manager = this.getChildFragmentManager();
        for(int i  = 0; i < m_frags.size();++i){
            manager.beginTransaction().remove(m_frags.get(i)).commit();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        for(int i = 0 ;i < m_items.getValue().size();++i) {
            ProductViewFragment frag = new ProductViewFragment(m_items.getValue().get(i));
            transaction.add(R.id.explore_scroll_fragmentcontainer, frag);
            m_frags.add(frag);
        }
        transaction.commit();
    }
}
