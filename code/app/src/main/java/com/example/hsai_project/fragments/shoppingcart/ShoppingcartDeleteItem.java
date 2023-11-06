package com.example.hsai_project.fragments.shoppingcart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hsai_project.R;


public class ShoppingcartDeleteItem extends Fragment {


    ShoppingcartItem m_item;
    CheckBox m_checkbox;

    public ShoppingcartDeleteItem(ShoppingcartItem item) {
        m_item = item;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_shoppingcart_delete_item, container, false);

        TextView productname = (TextView)root.findViewById(R.id.shoppingcart_delete_item_productname);
        productname.setText(m_item.getProductName());

        TextView shopname = (TextView)root.findViewById(R.id.shoppingcart_delete_item_storename);
        shopname.setText(m_item.getStore());

        m_checkbox = (CheckBox) root.findViewById(R.id.shoppingcart_delete_item_checkbox);

        return root;
    }

    public boolean isActive(){
        return m_checkbox.isChecked();
    }

    public int getItemId(){
        return m_item.getId();
    }
}
