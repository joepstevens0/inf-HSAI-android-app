package com.example.hsai_project.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.hsai_project.ProductDao;
import com.example.hsai_project.ProductDatabase;
import com.example.hsai_project.ProductEntity;
import com.example.hsai_project.ProductListViewModel;
import com.example.hsai_project.R;

import java.util.List;
import java.util.Vector;

public class CompareFragment extends Fragment {
    private ProductListViewModel productListViewModel;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compare, container, false);
        final TextView compareProduct_1 = root.findViewById(R.id.compare_text_product_1);
        final TextView compareProduct_2 = root.findViewById(R.id.compare_text_product_2);

        final TextView product_1 = root.findViewById(R.id.compare_product_name_1);
        final TextView product_2 = root.findViewById(R.id.compare_product_name_2);

        final ImageView product_image_1 = root.findViewById(R.id.compare_image_product_1);
        final ImageView product_image_2 = root.findViewById(R.id.compare_image_product_2);

        final ImageView product_image_delete_1 = root.findViewById(R.id.compare_delete_1);
        final ImageView product_image_delete_2 = root.findViewById(R.id.compare_delete_2);

        compareProduct_1.setMovementMethod(new ScrollingMovementMethod());
        compareProduct_2.setMovementMethod(new ScrollingMovementMethod());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            compareProduct_1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    compareProduct_2.scrollTo(scrollX, scrollY);
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            compareProduct_2.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    compareProduct_1.scrollTo(scrollX, scrollY);
                }
            });
        }

        ProductDatabase db = ProductDatabase.getInstance(getContext());
        final LiveData<List<ProductEntity>>  m_data = db.productDao().getAllCompareProducts();


        if (m_data.getValue() == null){
            product_1.setText("No products selected");
            product_2.setText("No products selected");
        }
        product_image_delete_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_data.getValue().size()>0){
                    m_data.getValue().get(0).setInCompare(false);
                    new UpdateItemAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(m_data.getValue().get(0));
                }
            }
        });

        product_image_delete_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_data.getValue().size() >= 1){
                    m_data.getValue().get(1).setInCompare(false);
                    new UpdateItemAsyncTask(ProductDatabase.getInstance(getContext()).productDao()).execute(m_data.getValue().get(1));
                }
            }
        });

        m_data.observe(getViewLifecycleOwner(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                if (m_data.getValue() == null) {
                    return;
                } else if (m_data.getValue().size() <= 0){
                    product_1.setText(getString(R.string.compare_no_product));
                    Glide.with(getContext()).load("").into(product_image_1);
                    product_image_delete_1.setVisibility(View.INVISIBLE);
                    compareProduct_1.setText("");
                    product_2.setText(getString(R.string.compare_no_product));
                    Glide.with(getContext()).load("").into(product_image_2);
                    product_image_delete_2.setVisibility(View.INVISIBLE);
                    compareProduct_2.setText("");
                }
                else if (m_data.getValue().size() == 1){
                    product_1.setText(m_data.getValue().get(0).getProductName());
                    Glide.with(getContext()).load(m_data.getValue().get(0).getImage()).into(product_image_1);
                    product_image_delete_1.setVisibility(View.VISIBLE);
                    compareProduct_1.setText(m_data.getValue().get(0).getDescription());
                    product_2.setText(getString(R.string.compare_no_product));
                    Glide.with(getContext()).load("").into(product_image_2);
                    product_image_delete_2.setVisibility(View.INVISIBLE);
                    compareProduct_2.setText("");
                }
                else if (m_data.getValue().size() >= 2){
                    product_1.setText(m_data.getValue().get(0).getProductName());
                    Glide.with(getContext()).load(m_data.getValue().get(0).getImage()).into(product_image_1);
                    product_image_delete_1.setVisibility(View.VISIBLE);
                    compareProduct_1.setText(m_data.getValue().get(0).getDescription());
                    product_2.setText(m_data.getValue().get(1).getProductName());
                    Glide.with(getContext()).load(m_data.getValue().get(1).getImage()).into(product_image_2);
                    product_image_delete_2.setVisibility(View.VISIBLE);
                    compareProduct_2.setText(m_data.getValue().get(0).getDescription());
                }
            }
        });

        return root;
    }

    protected static class UpdateItemAsyncTask extends AsyncTask<ProductEntity, Void, Void> {
        private ProductDao productDao;
        protected UpdateItemAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductEntity... entities) {
            for(int i = 0; i < entities.length;++i) {
                productDao.update(entities[i]);
            }
            return null;
        }
    }
}