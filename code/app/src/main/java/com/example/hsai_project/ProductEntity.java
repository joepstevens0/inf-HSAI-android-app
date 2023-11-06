package com.example.hsai_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class ProductEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String productName;
    private int Price;
    private String Store;
    private String categorie;
    private String Image;
    private String description = "";


    private boolean isWishlist = false;
    private boolean inCompare = false;

    // explore data
    private int timesviewed = 0;
    private int timesbought = 0;

    // shoppingcart
    private int inCart = 0;

    // reservation
    private int reservated = 0;

    public ProductEntity(String productName, int Price, String Store, String Image, String categorie) {
        this.productName = productName;
        this.Price = Price;
        this.Store = Store;
        this.categorie = categorie;
        this.Image = Image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setStore(String store) {
        Store = store;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return Price;
    }

    public String getStore() {
        return Store;
    }

    public String getCategorie() { return this.categorie;}

    public int getTimesbought() {
        return timesbought;
    }

    public int getTimesviewed() {
        return timesviewed;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setTimesbought(int timesbought) {
        this.timesbought = timesbought;
    }

    public void setTimesviewed(int timesviewed) {
        this.timesviewed = timesviewed;
    }

    public void addView() { timesviewed+=1;}
    public void addBuy() { timesbought += 1;}

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isWishlist() {
        return isWishlist;
    }

    public void setWishlist(boolean wishlist) {
        isWishlist = wishlist;
    }

    public boolean isInCompare() {
        return inCompare;
    }

    public void setInCompare(boolean inCompare) {
        this.inCompare = inCompare;
    }

    public int getInCart() {
        return inCart;
    }

    public void setInCart(int inCart) {
        this.inCart = inCart;
    }

    public int getReservated() {
        return reservated;
    }

    public void setReservated(int reservated) {
        this.reservated = reservated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
