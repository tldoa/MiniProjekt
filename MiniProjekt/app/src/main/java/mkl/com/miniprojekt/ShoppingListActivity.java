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

        TextView tv_total = (TextView) findViewById(R.id.tv_total);
        TextView tv_discount = (TextView) findViewById(R.id.tv_discount);
        TextView tv_salePrice = (TextView) findViewById(R.id.tv_sale_price);
        tv_total.setText("Total: " + total);
        tv_discount.setText("Total: " + discount);
        tv_salePrice.setText("Sale Price: " + saleprice);


        final SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.shoppinglist_layout_list,
                        productCursor,
                        new String[]{"NAME","SALEPRICE","QTY"},
                        new int[]{R.id.shoppinglist_product_name, R.id.shoppinglist_product_price, R.id.shoppinglist_product_quantity},
                        0);
        listView.setAdapter(cursorAdapter);

    }

}
