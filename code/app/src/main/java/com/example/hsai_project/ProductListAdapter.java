package com.example.hsai_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHoler> {
    private List<ProductEntity>products = new ArrayList<>();
    private  OnitemClickListener listener;
    private OnitemFavClickListener favlistener;
    private OnitemCompareClickListener comparelistener;

    @NonNull
    @Override
    public ProductHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product_view, parent, false);
        return new ProductHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHoler holder, int position) {
        ProductEntity currentProduct = products.get(position);
        holder.textViewName.setText(currentProduct.getProductName());
        holder.textViewPrice.setText(String.valueOf(currentProduct.getPrice()));
        holder.compareFunction.setImageResource(R.drawable.ic_compare_arrows_black_24dp);
        Glide.with(holder.ViewImage.getContext()).load(currentProduct.getImage()).into(holder.ViewImage);
        boolean isFav = currentProduct.isWishlist();
        if(!isFav){
            holder.wishlistLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        else{
            holder.wishlistLike.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<ProductEntity>products){
        this.products = products;
        notifyDataSetChanged();
    }

    class ProductHoler extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private TextView textViewPrice;
        private ImageView ViewImage;
        private ImageView wishlistLike;
        private ImageView compareFunction;

        public ProductHoler(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.product_text);
            textViewPrice = itemView.findViewById(R.id.product_price);
            ViewImage = itemView.findViewById(R.id.product_image);
            wishlistLike = itemView.findViewById(R.id.product_fav_button);
            compareFunction = itemView.findViewById(R.id.vergelijk_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(products.get(position));
                    }
                }
            });

            wishlistLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        favlistener.onItemFavClick(products.get(position));
                    }
                }
            });

            compareFunction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(comparelistener != null && position != RecyclerView.NO_POSITION) {
                        comparelistener.onItemCompareClick(products.get(position));
                    }
                }
            });

        }
    }


    public interface OnitemClickListener {
        void onItemClick(ProductEntity productEntity);
    }

    public interface OnitemFavClickListener{
        void onItemFavClick(ProductEntity productEntity);
    }

    public void setOnItemFavClickListener(OnitemFavClickListener favlistener){
        this.favlistener = favlistener;
    }

    public void setOnItemClickListener(OnitemClickListener listener){
        this.listener = listener;
    }

    public interface OnitemCompareClickListener{
        void onItemCompareClick(ProductEntity productEntity);
    }

    public void setOnItemCompareClickListener(OnitemCompareClickListener comparelistener){
        this.comparelistener = comparelistener;
    }
}
