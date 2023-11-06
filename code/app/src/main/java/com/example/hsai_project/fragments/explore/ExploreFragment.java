package com.example.hsai_project.fragments.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.R;

public class ExploreFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        createFragments();

        return root;
    }

    private void createFragments(){
        ProductDatabase db = ProductDatabase.getInstance(getContext());

        ExploreScrollFragment frag = new ExploreScrollFragment( db.productDao().getTopViewed(), "Meest bekeken");
        addFragment(frag);

        frag = new ExploreScrollFragment(db.productDao().getTopBought(),"Meest gekocht");
        addFragment(frag);

        frag = new ExploreScrollFragment(db.productDao().get10Cat("Laptop"), "Top 10 laptops");
        addFragment(frag);
        frag = new ExploreScrollFragment(db.productDao().get10Cat("PC"), "Top 10 dekstops");
        addFragment(frag);
        frag = new ExploreScrollFragment(db.productDao().get10Cat("Coffee"), "Top 10 koffiezetapparaten");
        addFragment(frag);
    }

    private void addFragment(ExploreScrollFragment frag){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.explore_fragmentcontainer, frag);
        transaction.commit();
    }
}
