package mkl.com.miniprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MattiasKristenLyngdr on 21-09-2017.
 */

public class Storage {
    private static Storage uniqueInstance;
    //public static Storage uniqueInstance;
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

}
