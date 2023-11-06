package com.example.hsai_project.fragments;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.ProductListViewModel;
import com.example.hsai_project.R;

import java.util.List;
import java.util.Observable;

public class ProductCategorieFragment extends Fragment {
    private ProductListViewModel productListViewModel;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View categories = inflater.inflate(R.layout.fragment_product_subcategorie, container, false);
        /*TextView textView = categories.findViewById(R.id.product_categorie_sub);*/
        GridView gridView = categories.findViewById(R.id.product_categorie_sub_grid);

        if (getArguments() != null){
            ProductCategorieFragmentArgs args = ProductCategorieFragmentArgs.fromBundle(getArguments());
            String chosenCategory = args.getChosenCategory();
            if(chosenCategory == "Laptops & Computers"){
                String[] categories_1 = {"Laptops", "Computers"};
                int[] categorieImage = {R.drawable.ic_laptop_windows_black_150dp, R.drawable.ic_desktop_windows_black_150dp};

                ProductAdapter adapter = new ProductAdapter(this.getContext(), categories_1, categorieImage);
                gridView.setAdapter(adapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0){
                            Navigation.findNavController(categories).navigate(R.id.product_list);
                        }
                    }
                });
            }
        }





        return categories;
    }


}
