package com.example.hsai_project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hsai_project.fragments.reservations.ReservationItem;
import com.example.hsai_project.fragments.shoppingcart.ShoppingcartItem;

import java.util.List;

@Dao
public interface ProductDao {

    // product table
    @Insert
    void insert(ProductEntity product);
    @Update
    void update(ProductEntity product);
    @Delete
    void delete(ProductEntity product);

    @Query("SELECT * FROM product_table ORDER BY Price ASC")
    LiveData<List<ProductEntity>>getAllProducts();


    // compare
    @Query("SELECT * FROM product_table WHERE inCompare = 1")
    LiveData<List<ProductEntity>>getAllCompareProducts();

    @Query("SELECT COUNT(*) FROM product_table WHERE inCompare = 1")
    LiveData<Integer> getCompared();

    @Query("SELECT * FROM product_table WHERE id = :id")
    LiveData<List<ProductEntity>>getFullProduct(int id);

    // shoppingcart
    @Query("SELECT x.id,x.productName,x.Price,x.Store, x.inCart as amount FROM product_table as x WHERE x.inCart > 0")
    LiveData<List<ShoppingcartItem>> getAllShoppingcartProducts();

    @Query("UPDATE product_table SET inCart = 0 WHERE id = :id")
    void deleteFromShoppingCart(int id);

    // explore
    @Query("SELECT * FROM product_table ORDER BY timesviewed LIMIT 10")
    LiveData<List<ProductEntity>> getTopViewed();

    @Query("SELECT * FROM product_table ORDER BY timesbought LIMIT 10")
    LiveData<List<ProductEntity>> getTopBought();

    @Query("SELECT * FROM product_table WHERE categorie = :cat ORDER BY timesviewed LIMIT 10")
    LiveData<List<ProductEntity>> get10Cat(String cat);

    // reservation
    @Query("SELECT x.id,x.productName,x.Price,x.Store, x.reservated as amount FROM product_table as x WHERE x.reservated > 0")
    LiveData<List<ReservationItem>> getAllReservations();
    @Query("UPDATE product_table SET reservated = :amount WHERE id = :id")
    void insertReservation(int id, int amount);

    // wishlist
    @Query("SELECT * FROM product_table WHERE isWishlist == 1 ORDER BY Price DESC")
    LiveData<List<ProductEntity>>getAllWishlistProducts();

    @Query("UPDATE product_table SET isWishlist = 1 WHERE id =:id")
    void addToWishlist(int id);

    @Query("UPDATE product_table SET isWishlist = 0 WHERE id =:id")
    void removeFromWishlist(int id);

}
