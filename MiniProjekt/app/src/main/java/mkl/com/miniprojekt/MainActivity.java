package mkl.com.miniprojekt;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Storage.getUniqueInstance(this);

        listView = (ListView) findViewById(R.id.lv_shops);
        registerForContextMenu(listView);

        cursor = Storage.getShops();
        final SimpleCursorAdapter cursorAdapter =
                new ShopCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        cursor,
                        new String[]{"_id","NAME"},
                        new int[]{android.R.id.text1, android.R.id.text2},
                        0);
        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = cursorAdapter.getCursor();
                final int id = cursor.getInt(cursor.getColumnIndex("_id"));

                String shopName = cursor.getString(1);
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("NAME", shopName);
                intent.putExtra("shopId", id);
                startActivity(intent);
            }
        });

    }

    public class ShopCursorAdapter extends SimpleCursorAdapter {

        public ShopCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }
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
                Intent intent = new Intent(this, AddShopActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_shopping_list:
                Intent shoppingListIntent = new Intent(this, ShoppingListActivity.class);
                startActivity(shoppingListIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shop_context, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.action_delete_shop:
                Storage.removeShop(info.id);
                Storage.removeShopProducts(info.id);
                cursor = Storage.getShops();
                ShopCursorAdapter adapter = (ShopCursorAdapter)listView.getAdapter();
                adapter.changeCursor(cursor);
                return true;
            default: return super.onContextItemSelected(item);
        }
    }
}
