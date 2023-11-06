package com.example.hsai_project.fragments.reservations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsai_project.R;


public class ReservationsItemFragment extends Fragment {

    ReservationItem m_item;
    ReservationsFragment m_parent;

    public ReservationsItemFragment(ReservationItem item, ReservationsFragment parent) {
        m_item = item;
        m_parent = parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_reservations_item, container, false);

        TextView productname = (TextView)root.findViewById(R.id.reservations_item_productname);
        productname.setText(m_item.getProductName());
        TextView storename = (TextView)root.findViewById(R.id.reservations_item_storename);
        storename.setText(m_item.getStore());
        TextView amounttext = (TextView)root.findViewById(R.id.reservations_item_amount);
        amounttext.setText(String.valueOf(m_item.getAmount()));


        Button b = (Button) root.findViewById(R.id.reservations_item_removebutton);
        final ReservationsItemFragment t = this;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.reservation_cancel_text);

                builder.setNegativeButton(R.string.reservation_cancel_stop, null);
                builder.setPositiveButton(R.string.reservation_cancel_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_parent.cancelReservation(t);
                        Toast.makeText(getContext(), R.string.reservation_cancel_toast, Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return root;
    }

    public ReservationItem getItem(){
        return m_item;
    }
}
