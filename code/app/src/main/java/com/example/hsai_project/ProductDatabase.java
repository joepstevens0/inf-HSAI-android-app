package com.example.hsai_project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {ProductEntity.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase instance;

    public abstract ProductDao productDao();

    public static synchronized ProductDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ProductDao productDao;
        private PopulateDbAsyncTask(ProductDatabase db){

            productDao = db.productDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            List<ProductEntity> list = new ArrayList<>();
            ProductEntity t;

            // laptops
            t = new ProductEntity("LENOVO Laptop Ideapad 330-17IKBR Intel Core i3-7020U (81DM00F2MB)", 799, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73613985/fee_240_148_png/HP-Laptop-Pavilion-15-cs3030nb-Intel-Core-i5-1035G1-%285QY85EA%29", "Laptop");
            list.add(t);
            t = new ProductEntity("HP Laptop Pavilion 15-cs3030nb Intel Core i5-1035G1 (5QY85EA)", 1299, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_71580955/fee_240_148_png/ACER-Laptop-Aspire-7-A715-74G-77K7-Intel-Core-i7-9750H-%28NH.Q5TEH.012%29", "Laptop");
            list.add(t);
            t = new ProductEntity("ACER Laptop Aspire 7 A715-74G-77K7 Intel Core i7-9750H (NH.Q5TEH.012)", 1099, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_71939913/fee_240_148_png/HP-Gaming-laptop-Pavilion-15-dk0011nb-Intel-Core-i5-9300H-%286SZ60EA%23UUG%29", "Laptop");
            list.add(t);
            t = new ProductEntity("HP Gaming laptop Pavilion 15-dk0011nb Intel Core i5-9300H (6SZ60EA#UUG)", 499, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_72059343/fee_240_148_png/LENOVO-Laptop-Ideapad-S145-14IIL-Intel-Core-i3-1005G1-%2881W60078MB%29", "Laptop");
            list.add(t);
            t = new ProductEntity("LENOVO Laptop Ideapad S145-14IIL Intel Core i3-1005G1 (81W60078MB) ", 1049, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_70086944/fee_240_148_png/HP-Laptop-ENVY-13-aq1006nb-Intel-Core-i7-1065G7-%288PS67EA%29", "Laptop");
            list.add(t);
            t = new ProductEntity("HP Laptop ENVY 13-aq1006nb Intel Core i7-1065G7 (8PS67EA)", 899, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_72085560/fee_240_148_png/ASUS-Laptop-VivoBook-S15-S532FL-BQ313T-BE-Intel-Core-i5-10210U-%2890NB0MJ2-M05300%29", "Laptop");
            list.add(t);
            t = new ProductEntity("ASUS Laptop VivoBook S15 S532FL-BQ313T-BE Intel Core i5-10210U (90NB0MJ2-M05300)", 1399, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_72672190/fee_240_148_png/HUAWEI-Laptop-Matebook-X-Pro-Intel-Core-i7-8565U-Space-Grey-%28MACHR-W29B%29", "Laptop");
            list.add(t);

            // pc
            t = new ProductEntity("MEDION Desktop PC E66001 Intel Core i5-9400F (10023411)", 599, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_74393524/fee_240_148_png/MEDION-Desktop-PC-E66001-Intel-Core-i5-9400F-%2810023411%29", "PC");
            list.add(t);
            t = new ProductEntity("ACER Desktop PC Aspire XC-330 A9422 AMD A9-9420 (DT.B9FEH.009)", 399, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_74595587/fee_240_148_png/ACER-Desktop-PC-Aspire-XC-330-A9422-AMD-A9-9420-%28DT.B9FEH.009%29", "PC");
            list.add(t);
            t = new ProductEntity("EXTREMEGAMER Gaming PC Ultimate V10 AMD Ryzen 7 3700X", 3199, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-82035044/fee_240_148_png/EXTREMEGAMER-Gaming-PC-Ultimate-V10-Intel-Core-i9-9900K", "PC");
            list.add(t);
            t = new ProductEntity("EXTREMEGAMER Gaming PC Legend V10 Intel Core i7-9700K", 2999, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-82035044/fee_240_148_png/EXTREMEGAMER-Gaming-PC-Ultimate-V10-AMD-Ryzen-7-3700X", "PC");
            list.add(t);
            t = new ProductEntity("MEDIA PC Desktop PC Level 4 Silent Edition Intel Core i5-9400F", 699, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73524355/fee_240_148_png/MEDIA-PC-Desktop-PC-Level-4-Silent-Edition-Intel-Core-i5-9400F", "PC");
            list.add(t);

            // coffee
            t = new ProductEntity("ARIETE Elektrisch Italiaans koffiezetapparaat Moka Aroma (1358)", 49, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-76633029/fee_240_148_png/ARIETE-Elektrisch-Italiaans-koffiezetapparaat-Moka-Aroma-%281358%29", "Coffee");
            list.add(t);
            t = new ProductEntity("TRISTAR Koffiemolen (KM-2270)", 21, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-79404912/fee_240_148_png/TRISTAR-Koffiemolen-%28KM-2270%29", "Coffee");
            list.add(t);
            t = new ProductEntity("MELITTA Italiaans koffiezetapparaat (FRENCH PRESS 8 CUPS)", 17, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-76702049/fee_240_148_png/MELITTA-Italiaans-koffiezetapparaat-%28FRENCH-PRESS-8-CUPS%29", "Coffee");
            list.add(t);
            t = new ProductEntity("MELITTA Koffiezetapparaat Easy (1023-02)", 27, "MediaMarkt", "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-78482443/fee_240_148_png/MELITTA-Koffiezetapparaat-Easy-%281023-02%29", "Coffee");
            list.add(t);

            for(int i = 0; i < list.size();++i){
                t = list.get(i);
                switch (t.getCategorie()){
                    case "Laptop":
                        t.setDescription(
                        "Processor:Intel Core i3-1005G1\n" +
                        "Schermdiagonaal (cm/inch):35.6 cm / 14 inch\n" +
                        "Resolutie (pixels):1920 x 1080\n" +
                        "Geheugengrootte (RAM):8 GB\n" +
                        "1ste schijf:SSD , 256 GB , M.2 via NVMe\n" +
                        "Grafische kaart:Intel UHD ");
                        break;
                    case "PC":
                        t.setDescription(
                                "Processor: Intel Core i9-9900K\n" +
                                "Grafische kaart: NVIDIA GeForce RTX 2080 Ti Ventus\n" +
                                "Grafisch geheugen: 11 GB\n" +
                                "Geheugengrootte (RAM): 32 GB\n" +
                                "Capaciteit 1ste schijf: 500 GB\n" +
                                "Capaciteit 2de schijf:2 TB ");
                        break;
                    case "Coffee":
                        t.setDescription(
                                "Producttype: Koffiemolen\n" +
                                "Inhoud (bonenreservoir): 70 g\n" +
                                "Maalgraden: 1\n" +
                                "Automatische uitschakeling: Nee\n" +
                                "Gebruiksmodus: Netvoeding\n" +
                                "Materiaal behuizing: Inox:\n" +
                                "Koffiesoort: Gemalen koffie");
                        break;
                }
                productDao.insert(t);
            }

            return null;
        }
    }
}
