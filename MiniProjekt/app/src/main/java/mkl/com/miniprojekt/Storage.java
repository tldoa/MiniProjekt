package mkl.com.miniprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
