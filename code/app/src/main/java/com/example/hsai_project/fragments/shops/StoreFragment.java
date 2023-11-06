package com.example.hsai_project.fragments.shops;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.R;
import com.example.hsai_project.fragments.explore.ExploreScrollFragment;

import java.util.List;


public class StoreFragment extends Fragment {
    private LiveData<List<ProductEntity>> m_products;

    private boolean m_openhoursisopen = false;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        ProductDatabase db = ProductDatabase.getInstance(getContext());
        // TODO get products from correct store
        m_products = db.productDao().getAllProducts();

        getChildFragmentManager().beginTransaction().add(R.id.store_products_container, new ExploreScrollFragment(m_products, "Producten van deze winkel")).commit();

        final CardView openhours = (CardView)root.findViewById(R.id.store_openhours);

        final ImageView icon = (ImageView)root.findViewById(R.id.store_openhours_icon);
        openhours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_openhoursisopen = !m_openhoursisopen;
                if(m_openhoursisopen) {
                    icon.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    // TODO  add fragment
                }else{
                    icon.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                    // TODO remove fragment
                }
            }
        });
        return root;
    }
}
