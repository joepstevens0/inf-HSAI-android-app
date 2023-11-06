package com.example.hsai_project.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsai_project.R;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] categories;
    private int[] images;

    public ProductAdapter(Context context, String[] categories, int[] images){
        this.context = context;
        this.categories = categories;
        this.images = images;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.product_sub_categorie_view, null);
        }

        ImageView imageView = convertView.findViewById(R.id.image_product_view);
        TextView textView = convertView.findViewById(R.id.text_product_view);
        imageView.setImageResource(images[position]);
        textView.setText(categories[position]);
        return convertView;
    }
}
