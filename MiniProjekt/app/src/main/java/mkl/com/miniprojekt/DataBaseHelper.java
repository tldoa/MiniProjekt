package mkl.com.miniprojekt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MattiasKristenLyngdr on 21-09-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "localDB";
    private static final int DB_VERSION = 1;

    DataBaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateDatabase(sqLiteDatabase,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void updateDatabase(SQLiteDatabase db,int oldVersion, int newVersion){
        if (oldVersion<=1){
            db.execSQL("CREATE TABLE HUMANS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"NAME TEXT);");


        }

//        if (oldVersion<2){
//            db.execSQL("ALTER TABLE HUMANS ADD COLUMN GIRLFRIEND");
//        }
    }
}