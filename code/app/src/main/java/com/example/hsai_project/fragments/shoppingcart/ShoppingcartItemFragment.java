package com.example.hsai_project.fragments.shoppingcart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hsai_project.R;


public class ShoppingcartItemFragment extends Fragment {

    public ShoppingcartItemFragment(ShoppingcartItem item) {
        m_item = item;
    }

    private ShoppingcartItem m_item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_shoppingcart_item, container, false);

        TextView productname = (TextView)root.findViewById(R.id.shoppingcart_item_productname);
        productname.setText(m_item.getProductName());

        TextView storename = (TextView)root.findViewById(R.id.shoppingcart_item_storename);
        storename.setText(m_item.getStore());

        final EditText amounttext = root.findViewById(R.id.shoppingcart_item_amounttext);
        amounttext.setText(String.valueOf(m_item.getAmount()));

        Button addbutton = root.findViewById(R.id.shoppingcart_item_addbutton);
        final Button removebutton = root.findViewById(R.id.shoppingcart_item_removebutton);

        if(m_item.getAmount() <= 1)
            removebutton.setVisibility(View.INVISIBLE);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_item.setAmount(m_item.getAmount() + 1);
                amounttext.setText(String.valueOf(m_item.getAmount()));
                if(m_item.getAmount() > 1)
                    removebutton.setVisibility(View.VISIBLE);
            }
        });

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_item.setAmount(m_item.getAmount() -1 );
                if(m_item.getAmount() <= 1) {
                    m_item.setAmount(1);
                    removebutton.setVisibility(View.INVISIBLE);
                }
                amounttext.setText(String.valueOf(m_item.getAmount()));
            }
        });

        return root;
    }
}
