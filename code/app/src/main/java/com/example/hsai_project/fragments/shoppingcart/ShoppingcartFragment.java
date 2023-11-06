package com.example.hsai_project.fragments.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.hsai_project.ProductDao;
import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingcartFragment extends Fragment {

    private static int REQUEST_CODE_DELETE_ITEMS = 0;

    LiveData<List<ShoppingcartItem>> m_data;
    List<ShoppingcartItemFragment> m_frags = new ArrayList<ShoppingcartItemFragment>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_shoppingcart, container, false);

        getEntries(root);

        return root;
    }

    public void getEntries(final View root){
        ProductDatabase db = ProductDatabase.getInstance(getContext());

        m_data = db.productDao().getAllShoppingcartProducts();
        m_data.observe(getViewLifecycleOwner(), new Observer<List<ShoppingcartItem>>() {
            @Override
            public void onChanged(List<ShoppingcartItem> productEntities) {
                addFrags(root);
                setButtons(root);
            }
        });
    }
    private void setButtons(final View root){
        if(m_data.getValue() == null)
            return;
        Button deletebutton = root.findViewById(R.id.shoppingcart_deletebutton);
        Button reservation = root.findViewById(R.id.shoppingcart_reservate);
        TextView emptytext = (TextView) root.findViewById(R.id.shoppingcart_empty);
        if(m_data.getValue().size() > 0) {
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ShoppingcartDelete.class);
                    startActivityForResult(intent, REQUEST_CODE_DELETE_ITEMS);
                }
            });
            emptytext.setVisibility(View.INVISIBLE);
            reservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reservate();
                }
            });
        }
        else {
            emptytext.setVisibility(View.VISIBLE);
            deletebutton.setVisibility(View.INVISIBLE);
            reservation.setVisibility(View.INVISIBLE);

        }

    }

    public void addFrags(View root){
        if(m_data.getValue() == null)
            return;

        for(Fragment frag: m_frags) {
            getChildFragmentManager().beginTransaction().remove(frag).commit();
        }
        for(int i = 0; i < m_data.getValue().size();++i) {
            ShoppingcartItemFragment frag = new ShoppingcartItemFragment(m_data.getValue().get(i));

            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.shoppincart_fragmentcontainer, frag);
            transaction.commit();
            m_frags.add(frag);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_DELETE_ITEMS){
            if(resultCode == Activity.RESULT_OK){
                int[] d = data.getIntArrayExtra(ShoppingcartDelete.EXTRA_REMOVED_ITEM_IDS);
                deleteItems(d);
                if(d.length > 0)
                    Toast.makeText(getActivity(), "Items removed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteItems(int[] itemids){
        if(m_data.getValue() == null)
            return;
        for(int i = 0; i < itemids.length;++i){
            int id = itemids[i];
            for(int j =0; j < m_data.getValue().size();++j){
                if(m_data.getValue().get(j).getId() == id){
                    removeItem(j);
                    break;
                }
            }
        }
    }

    private void removeItem(int index){
        if(m_data.getValue() == null)
            return;

        ProductDatabase db = ProductDatabase.getInstance(getContext());
        new removeReservationAsyncTask(db.productDao()).execute(m_data.getValue().get(index));

        m_data.getValue().remove(index);

        ShoppingcartItemFragment item = m_frags.remove(index);

        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.remove(item);
        transaction.commit();
    }


    private void reservate(){
        if(m_data.getValue() == null)
            return;
        ProductDatabase db = ProductDatabase.getInstance(getContext());
        for(int i = 0; i < m_data.getValue().size();++i){
            ShoppingcartItem item = m_data.getValue().get(i);
            new addReservationAsyncTask(db.productDao()).execute(item);
        }

        if(m_data.getValue() != null) {
            for (int i = 0; i < m_data.getValue().size(); ++i) {
                removeItem(i);
            }
        }

        Toast.makeText(getContext(),"Items gereserveerd", Toast.LENGTH_LONG).show();
    }

    private static class removeReservationAsyncTask extends AsyncTask<ShoppingcartItem, Void, Void> {
        private ProductDao productDao;
        private removeReservationAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ShoppingcartItem... entities) {
            for(int i = 0; i < entities.length;++i) {
                productDao.deleteFromShoppingCart(entities[i].getId());
            }
            return null;
        }
    }

    private static class addReservationAsyncTask extends AsyncTask<ShoppingcartItem, Void, Void> {
        private ProductDao productDao;
        private addReservationAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ShoppingcartItem... entities) {
            for(int i = 0; i < entities.length;++i) {
                productDao.insertReservation(entities[i].getId(), entities[i].getAmount());
            }
            return null;
        }
    }
}