package com.example.hsai_project.fragments.shoppingcart;

public class ShoppingcartItem {
    private int id;
    private String productName;
    private int Price;
    private String Store;
    private int amount;

    public ShoppingcartItem(int id ,String productName, int Price, String Store, int amount){
        this.id = id;
        this.productName = productName;
        this.Price = Price;
        this.Store = Store;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId(){
        return this.id;
    }
}
