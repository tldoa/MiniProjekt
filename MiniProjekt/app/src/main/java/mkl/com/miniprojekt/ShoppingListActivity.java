package mkl.com.miniprojekt;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShoppingListActivity extends AppCompatActivity {

    ListView listView;
    double saleprice;
    double discount;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        listView = (ListView) findViewById(R.id.lv_shopping_list);

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "" +
                "SELECT * " +
                "FROM PRODUCT p " +
                "JOIN SHOPPING_LIST sl " +
                "ON p._id = sl.PRODUCT_ID";

        Cursor productCursor = db.rawQuery(query, new String[]{});

        productCursor.moveToFirst();
        this.total = 0;
        this.discount = 0;
        this.saleprice = 0;
        while(!productCursor.isAfterLast()) {
            double price = productCursor.getColumnIndex("PRICE");
            double salePrice = productCursor.getColumnIndex("SALEPRICE");
            int qty = productCursor.getColumnIndex("QTY");

            total += qty * price;
            discount = (qty * salePrice) - total;
            saleprice = total + discount;
            productCursor.moveToNext();
        }
//        TextView tv_total = (TextView) findViewById(R.id.tv_total);
//        TextView tv_discount = (TextView) findViewById(R.id.tv_discount);
//        TextView tv_salePrice = (TextView) findViewById(R.id.tv_sale_price);
//        tv_total.setText("Total: " + total);
//        tv_discount.setText("Total: " + discount);
//        tv_salePrice.setText("Sale Price: " + saleprice);


        final SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.shoppinglist_layout_list,
                        productCursor,
                        new String[]{"NAME","SALEPRICE","QTY"},
                        new int[]{R.id.shoppinglist_product_name, R.id.shoppinglist_product_price, R.id.shoppinglist_product_quantity},
                        0);
        listView.setAdapter(cursorAdapter);





//        ArrayList<String> listOfIDs = new ArrayList<>();
//      Find all PRODUCT ID's in the SHOPPING_LIST
//        Cursor shoppingListCursor = db.query("SHOPPING_LIST", new String[]{"PRODUCT_ID"}, null, null, null, null, null);

//      Add All ID's to ArrayList
//        shoppingListCursor.moveToFirst();
//        while (!shoppingListCursor.isAfterLast()) {
//            listOfIDs.add(shoppingListCursor.getInt(0)+"");
//            shoppingListCursor.moveToNext();
//        }

//      Convert ArrayList to Array
//        String[] listOfIDsAsArray = listOfIDs.toArray(new String[listOfIDs.size()]);

//      Find All PRODUCTS with the same ID as the PRODUCTS from the SHOPPING_LIST
//        Cursor productCursor = db.query(
//                "PRODUCT",
//                new String[] {"NAME"},
//                "_id IN ( ?, ?, ? )",
//                listOfIDsAsArray,
//                null, null, null);

//        productCursor.moveToFirst();
//        while (!productCursor.isAfterLast()) {
//            Log.d("PENIS", productCursor.getString(0)+"");
//            productCursor.moveToNext();
//        }

    }

//    public String myJoin(ArrayList listOfIDs) {
//        String listOfIDsJoined = "";
//
//        for (int i = 0 ; i < listOfIDs.size() ; i++) {
//            listOfIDsJoined += listOfIDs.get(i) + ", ";
//        }
//        return listOfIDsJoined.substring(0, listOfIDsJoined.length()-2);
//    }

//    public String makePlaceholder(int size) {
//        String str = "";
//        for (int j = 0 ; j < size ; j++) {
//            str += "?, ";
//        }
//        return str.substring(0, str.length() - 2);
//    }

}
