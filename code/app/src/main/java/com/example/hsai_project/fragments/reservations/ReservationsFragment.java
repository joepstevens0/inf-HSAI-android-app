package com.example.hsai_project.fragments.reservations;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.hsai_project.ProductDao;
import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.R;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {

    LiveData<List<ReservationItem>> m_items;
    List<ReservationsItemFragment> m_frags = new ArrayList<ReservationsItemFragment>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_reservations, container, false);

        ProductDatabase db = ProductDatabase.getInstance(getContext());

        m_items = db.productDao().getAllReservations();
        m_items.observe(getViewLifecycleOwner(), new Observer<List<ReservationItem>>() {
            @Override
            public void onChanged(List<ReservationItem> productEntities) {
                addFrags(root);
                setButtons(root);
            }
        });

        return root;
    }

    private void setButtons(View root){
        if(m_items.getValue() == null)
            return;
        TextView emptytext = (TextView) root.findViewById(R.id.reservations_empty);

        if(m_items.getValue().size() <= 0) {
            emptytext.setVisibility(View.VISIBLE);
        }else
            emptytext.setVisibility(View.INVISIBLE);
    }

    public void addFrags(View root){
        if(m_items.getValue() == null)
            return;
        for(Fragment frag: m_frags) {
            getChildFragmentManager().beginTransaction().remove(frag).commit();
        }
        for(int i = 0; i < m_items.getValue().size(); ++i) {
            ReservationsItemFragment frag = new ReservationsItemFragment(m_items.getValue().get(i), this);

            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.reservations_fragmentcontrainer, frag);
            transaction.commit();
            m_frags.add(frag);
        }
    }

    public void cancelReservation(ReservationsItemFragment frag){
        ReservationItem item = frag.getItem();
        ProductDatabase db = ProductDatabase.getInstance(getContext());
        new cancelReservationAsyncTask(db.productDao()).execute(frag.getItem());


        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.remove(frag);
        transaction.commit();

        m_frags.remove(frag);
    }

    private static class cancelReservationAsyncTask extends AsyncTask<ReservationItem, Void, Void> {
        private ProductDao productDao;
        private cancelReservationAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ReservationItem... entities) {
            for(int i = 0; i < entities.length;++i) {
                productDao.insertReservation(entities[i].getId(), 0);
            }
            return null;
        }
    }
}