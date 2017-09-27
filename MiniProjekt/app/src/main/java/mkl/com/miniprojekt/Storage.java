package mkl.com.miniprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MattiasKristenLyngdr on 21-09-2017.
 */

public class Storage {
    private static Storage uniqueInstance;
    private static DataBaseHelper dbHelper;

    public static Storage getUniqueInstance(Context context){

        if (uniqueInstance==null){
            uniqueInstance = new Storage(context);
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

    public static void addShop(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", name);
        db.insert("SHOP", null, values);
        db.close();
    }

    public static void removeShop(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        db.execSQL("DELETE FROM SHOP WHERE _id = ?", new String[]{idToUse});
        db.close();
    }

    public static void addProduct(Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues productValues = new ContentValues();
        productValues.put("NAME", product.getName());
        productValues.put("PRICE", product.getPrice());
        productValues.put("SALEPRICE", product.getSalePrice());
        productValues.put("SHOPID",product.getShopId());
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

    public static void removeProduct(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        db.execSQL("DELETE FROM PRODUCT WHERE _id = ?", new String[]{idToUse});
        db.close();
    }

    public static void updateProduct(Product product, long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String idToUse = String.valueOf(id);
        ContentValues productValues = new ContentValues();
        productValues.put("NAME", product.getName());
        productValues.put("PRICE", product.getPrice());
        productValues.put("SALEPRICE", product.getSalePrice());
        db.update("PRODUCT",productValues,"_id = ?", new String[]{idToUse});
        db.close();
    }
}
