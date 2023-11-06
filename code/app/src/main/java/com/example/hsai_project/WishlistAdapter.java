package com.example.hsai_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistHolder> {
    private List<ProductEntity>wishlistproducts = new ArrayList<>();
    private WishlistAdapter.OnitemDeleteClickListener deletelistener;

    @NonNull
    @Override
    public WishlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wishListView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_wishlist_item, parent, false);
        return new WishlistHolder(wishListView);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistHolder holder, int position) {
        ProductEntity currentWishlistProduct = wishlistproducts.get(position);
        holder.wishlistProduct.setText(currentWishlistProduct.getProductName());
    }

    @Override
    public int getItemCount() {
        return wishlistproducts.size();
    }

    public void setWishlistproducts(List<ProductEntity> wishlistproducts){
        this.wishlistproducts = wishlistproducts;
        notifyDataSetChanged();
    }

    class WishlistHolder extends RecyclerView.ViewHolder{
        private TextView wishlistProduct;
        private ImageView deleteProduct;

        public WishlistHolder(@NonNull View itemView) {
            super(itemView);
            wishlistProduct = itemView.findViewById(R.id.wishlist_item_text);
            deleteProduct = itemView.findViewById(R.id.wishlist_item_delete_image);
            deleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(deletelistener != null && position != RecyclerView.NO_POSITION)
                        deletelistener.onItemDeleteClick(wishlistproducts.get(position));
                }
            });
        }
    }

    public interface OnitemDeleteClickListener{
        void onItemDeleteClick(ProductEntity productEntity);
    }

    public void setOnItemDeleteClickListener(WishlistAdapter.OnitemDeleteClickListener deletelistener){
        this.deletelistener = deletelistener;
    }

}

