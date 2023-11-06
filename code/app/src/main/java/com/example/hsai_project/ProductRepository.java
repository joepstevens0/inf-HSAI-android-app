package com.example.hsai_project;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<ProductEntity>>allProducts;
    private LiveData<List<ProductEntity>>allWishlistProducts;
    /*private LiveData<List<ProductEntity>>allCompareProducts;*/

    private LiveData<List<ProductEntity>> topviewed;

    public ProductRepository(Application application){
        ProductDatabase database = ProductDatabase.getInstance(application);
        productDao = database.productDao();
        allProducts = productDao.getAllProducts();
        allWishlistProducts = productDao.getAllWishlistProducts();
        /*allCompareProducts = productDao.getAllCompareProducts();*/

        // explore
        topviewed = productDao.getTopViewed();
    }

    public void insert(ProductEntity productEntity){
        new InsertProductAsyncTask(productDao).execute(productEntity);
    }

    public void update(ProductEntity productEntity){
        new UpdateProductAsyncTask(productDao).execute(productEntity);
    }

    public void delete(ProductEntity productEntity){
        new DeleteProductAsyncTask(productDao).execute(productEntity);
    }

    public LiveData<List<ProductEntity>> getAllProducts(){
        return allProducts;
    }

    public LiveData<List<ProductEntity>> getAllWishlistProducts() {
        return allWishlistProducts;
    }

    /*public LiveData<List<ProductEntity>> getAllCompareProducts() {
        return getAllCompareProducts();
    }*/
    public LiveData<List<ProductEntity>> getTopviewed(){ return topviewed;}



    private static class InsertProductAsyncTask extends AsyncTask<ProductEntity, Void, Void>{
        private ProductDao productDao;
        private InsertProductAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductEntity... productEntities) {
            productDao.insert(productEntities[0]);
            return null;
        }
    }


    private static class UpdateProductAsyncTask extends AsyncTask<ProductEntity, Void, Void>{
        private ProductDao productDao;
        private UpdateProductAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductEntity... productEntities) {
            productDao.update(productEntities[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<ProductEntity, Void, Void>{
        private ProductDao productDao;
        private DeleteProductAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(ProductEntity... productEntities) {
            productDao.delete(productEntities[0]);
            return null;
        }
    }

}
