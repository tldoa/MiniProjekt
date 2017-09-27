package mkl.com.miniprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Date;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

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

    public static Cursor getProducts(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Log.d("PENIS", "ShopID used By GetProduct In Storage : " + id);

        Cursor cursor = db.query("PRODUCT",
                new String[] {"_id", "NAME"},
                "SHOP_ID = ?", new String[] {id + ""},
                null, null, null);
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

    public static void addProduct(String name, double price, double salePrice, int shopID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("PENIS", "Id given to Add Product in Storage: " + shopID);
        values.put("NAME", name);
        values.put("PRICE", price);
        values.put("SALE_PRICE", salePrice);
        values.put("SHOP_ID", shopID);
        db.insert("PRODUCT", null, values);
        db.close();
    }

    public static void addToShoppingList(int id, int qty) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PRODUCT_ID", id);
        values.put("QTY", qty);
        db.insert("SHOPPING_LIST", null, values);
        db.close();
    }

    public static void removeShop(long item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("SHOP", "_id = ?", new String[]{String.valueOf(item)});
    }

    public static void removeProduct(long item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("PRODUCT", "_id = ?", new String[]{String.valueOf(item)});
    }
}
