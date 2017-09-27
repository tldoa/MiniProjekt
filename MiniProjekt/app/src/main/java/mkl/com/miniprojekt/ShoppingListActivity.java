package mkl.com.miniprojekt;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    ListView listView;
    double originalPrice;
    double discount;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);


        listView = (ListView) findViewById(R.id.lv_shopping_list);

        final SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        Storage.getShoppingList(),
                        new String[]{"PRODUCT_ID"},
                        new int[]{android.R.id.text1},
                        0);
        listView.setAdapter(cursorAdapter);

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<String> listOfIDs = new ArrayList<>();
//      Find all PRODUCT ID's in the SHOPPING_LIST
        Cursor shoppingListCursor = db.query("SHOPPING_LIST", new String[]{"PRODUCT_ID"}, null, null, null, null, null);

//      Add All ID's to ArrayList
        shoppingListCursor.moveToFirst();
        while (!shoppingListCursor.isAfterLast()) {
            listOfIDs.add(shoppingListCursor.getInt(0)+"");
            shoppingListCursor.moveToNext();
        }

//      Convert ArrayList to Array
        String[] listOfIDsAsArray = listOfIDs.toArray(new String[listOfIDs.size()]);

//      Find All PRODUCTS with the same ID as the PRODUCTS from the SHOPPING_LIST
        Cursor productCursor = db.query(
                "PRODUCT",
                new String[] {"NAME"},
                "_id IN ( ?, ?, ? )",
                listOfIDsAsArray,
                null, null, null);

        productCursor.moveToFirst();
        while (!productCursor.isAfterLast()) {
            Log.d("PENIS", productCursor.getString(0)+"");
            productCursor.moveToNext();
        }

    }

    public String myJoin(ArrayList listOfIDs) {
        String listOfIDsJoined = "";

        for (int i = 0 ; i < listOfIDs.size() ; i++) {
            listOfIDsJoined += listOfIDs.get(i) + ", ";
        }
        return listOfIDsJoined.substring(0, listOfIDsJoined.length()-2);
    }

    public String makePlaceholder(int size) {
        String str = "";
        for (int j = 0 ; j < size ; j++) {
            str += "?, ";
        }
        return str.substring(0, str.length() - 2);
    }

}
