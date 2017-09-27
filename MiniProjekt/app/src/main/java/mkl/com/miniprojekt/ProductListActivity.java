package mkl.com.miniprojekt;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;

public class ProductListActivity extends AppCompatActivity {

    ListView listView;
    int storeID;
    AlertDialog alert;
    int ProductID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra("ShopName");
        storeID = getIntent().getIntExtra("ProductID", 0);
        Log.d("PENIS", "ProductID from intent: " + storeID);
        setTitle(name);

        listView = (ListView) findViewById(R.id.lv_products);
        registerForContextMenu(listView);

        final SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        Storage.getProducts(storeID),
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0);
        listView.setAdapter(cursorAdapter);

        final AlertDialog.Builder add_product_builder = new AlertDialog.Builder(this);

        add_product_builder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Storage.addToShoppingList(ProductID, 2);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alert = add_product_builder.create();

        LayoutInflater add_product_inflater = LayoutInflater.from(this);
        View addProductView = add_product_inflater.inflate(R.layout.add_product_to_shopping_list, null);

        alert.setTitle("Add");
        alert.setView(addProductView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = cursorAdapter.getCursor();
                ProductID = cursor.getInt(cursor.getColumnIndex("_id"));
                final String name = cursor.getString(cursor.getColumnIndex("NAME"));

                alert.setMessage("Add " + name + " to shopping list!");
                alert.show();

                final NumberPicker qty = (NumberPicker) alert.findViewById(R.id.np_qty);
                qty.setMinValue(1);
                qty.setMaxValue(10);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(this, AddProductActivity.class);

                Log.d("PENIS", "id to addProductActivity : " + storeID);

                intent.putExtra("id", storeID);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_products, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete_product:
                Storage.removeProduct(info.id);
                Cursor newCurser = Storage.getProducts((int)info.id);
                SimpleCursorAdapter adapter = (SimpleCursorAdapter) listView.getAdapter();
                adapter.changeCursor(newCurser);
        }

        return super.onContextItemSelected(item);
    }

}
