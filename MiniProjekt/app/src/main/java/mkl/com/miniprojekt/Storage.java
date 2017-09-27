package mkl.com.miniprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by MattiasKristenLyngdr on 21-09-2017.
 */

public class Storage {
    private static Storage uniqueInstance;
    private static DataBaseHelper dbHelper;

    public static Storage getUniqueInstance(Context context){

        if (uniqueInstance==null){
            uniqueInstance = new Storage(context);
            dummyShops();
            dummyProducts();
        }
        return uniqueInstance;
    }

    public Storage(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public static Cursor getShops() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("SHOP",
                new String[] {"_id", "NAME"},
                null, null, null, null, null);
        return cursor;
    }

    public static Cursor getShoppingList() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("SHOPPING_LIST",
                new String[] {"_id", "PRODUCT_ID", "QTY"},
                null, null,
                null, null, null);
        return cursor;
    }

    public static void addShop(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        long storeID = db.insert("SHOP", null, values);
        Log.d("PENIS", "store id: " + storeID);
        db.close();
    }

    public static void addToShoppingList(long id, int qty) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PRODUCT_ID", id);
        values.put("QTY", qty);
        db.insert("SHOPPING_LIST", null, values);
        db.close();
    }


    public static void removeShop(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        db.execSQL("DELETE FROM SHOP WHERE _id = ?", new String[]{idToUse});
        db.close();
    }

    public static void removeProduct(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        db.execSQL("DELETE FROM PRODUCT WHERE _id = ?", new String[]{idToUse});
        db.close();
    }

    public static void addProduct(Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues productValues = new ContentValues();
        productValues.put("NAME", product.getName());
        productValues.put("PRICE", product.getPrice());
        productValues.put("SALEPRICE", product.getSalePrice());
        productValues.put("SHOPID",product.getShopId());
        productValues.put("QUANTITY", product.getQuantity());
        db.insert("PRODUCT",null,productValues);
        db.close();
    }

    public static Cursor getProducts(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT WHERE SHOPID = ?", new String[]{idToUse});
        return cursor;
    }

    public static Cursor getSingleProduct(long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String idToUse = String.valueOf(id);
        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT WHERE _id = ?", new String[]{idToUse});
        cursor.moveToFirst();
        return cursor;
    }

    public static void updateProduct(Product product, long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        ContentValues productValues = new ContentValues();
        productValues.put("NAME", product.getName());
        productValues.put("PRICE", product.getPrice());
        productValues.put("SALEPRICE", product.getSalePrice());
        productValues.put("QUANTITY", product.getQuantity());
        db.update("PRODUCT",productValues,"_id = ?", new String[]{idToUse});
        db.close();
    }

    private static void dummyShops(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor shopCursor = db.rawQuery("SELECT * FROM SHOP",null);

        if(shopCursor.getCount()==0){
            addShop("Netto");
            addShop("Føtex");
            addShop("Fakta");

        }
        shopCursor.close();
        db.close();

    }
    private static void dummyProducts(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor productCursor = db.rawQuery("SELECT * FROM PRODUCT",null);

        if(productCursor.getCount()==0){

            addProduct(new Product("Kylling",39.95,25,100,1));
            addProduct(new Product("Banan",3.95,2,200,2));
            addProduct(new Product("Rugbrød",19.95,15,50,3));
            addProduct(new Product("Mayonaise",99.95,32,1000,1));
            addProduct(new Product("Ketchup",32.95,25,1000,2));
            addProduct(new Product("Rullepølse",12.95,10,80,3));
            addProduct(new Product("Spegepølse",27.95,25,70,1));
            addProduct(new Product("Laks",98.75,50,87,2));
            addProduct(new Product("Fanta",19.95,18,54,3));
            addProduct(new Product("Cola",17.95,12,66,1));
            addProduct(new Product("Remulade",13.95,10.95,42,2));
            addProduct(new Product("Æble",2.95,2,90,3));
            addProduct(new Product("Tun",7.95,4.95,27,1));
            addProduct(new Product("Toiletpapir",23,10,32,2));
            addProduct(new Product("Leverpostej",7.95,4.95,27,3));
            addProduct(new Product("pate",117.95,24.95,27,1));
            addProduct(new Product("Toiletpapir",23,10,32,2));
            addProduct(new Product("Ananas",24.95,20,54,3));
            addProduct(new Product("Ribeye",239.95,250,45,1));
            addProduct(new Product("Ris",17.95,12,78,2));
            addProduct(new Product("Pasta",18.75,10,77,3));
            addProduct(new Product("Salsa",21,18,32,1));
            addProduct(new Product("Nachos",29.95,25,250,2));
            addProduct(new Product("Mozzarella",7.95,6,12,3));

        }
        productCursor.close();
        db.close();
    }
}
