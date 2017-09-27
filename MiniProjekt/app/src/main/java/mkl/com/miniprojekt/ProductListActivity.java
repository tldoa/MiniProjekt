package mkl.com.miniprojekt;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ProductListActivity extends AppCompatActivity {
    private int shopId;
    private long productID;
    private View editView;
    private String shopName;
    private Cursor cursor;
    private ListView pView;
    private AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shopName = getIntent().getStringExtra("NAME");
        setTitle(shopName);

        shopId= getIntent().getIntExtra("shopId",0);
        pView = (ListView) findViewById(R.id.lv_products);
        registerForContextMenu(pView);

        cursor = Storage.getProducts(shopId);

        final SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        cursor,
                        new String[]{"_id","NAME"},
                        new int[]{android.R.id.text1, android.R.id.text2},
                        0);
        pView.setAdapter(cursorAdapter);

        AlertDialog.Builder editBuilder = new AlertDialog.Builder(this);
        editBuilder.setMessage("Edit Product")
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        EditText editName = (EditText) editView.findViewById(R.id.edit_name);
                        EditText editPrice = (EditText) editView.findViewById(R.id.edit_price);
                        EditText editSalePrice = (EditText) editView.findViewById(R.id.edit_saleprice);

                        String updateName= editName.getText().toString();
                        Double updatePrice = Double.parseDouble(editPrice.getText().toString());
                        Double updateSalePrice = Double.parseDouble(editSalePrice.getText().toString());
                        Product product = new Product(updateName,updatePrice,updateSalePrice,shopId);
                        Storage.updateProduct(product, productID);
                        cursor = Storage.getProducts(shopId);
                        SimpleCursorAdapter adapter = (SimpleCursorAdapter) pView.getAdapter();
                        adapter.changeCursor(cursor);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alert = editBuilder.create();
        LayoutInflater editProduct = LayoutInflater.from(this);
        editView = editProduct.inflate(R.layout.edit_product,null);

        alert.setView(editView);
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
                intent.putExtra("ShopID", shopId);
                intent.putExtra("shopName",shopName);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_context, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.action_delete_product:
                Storage.removeProduct(info.id);

                cursor = Storage.getProducts(shopId);
                SimpleCursorAdapter adapter = (SimpleCursorAdapter) pView.getAdapter();
                adapter.changeCursor(cursor);

                return true;
            case R.id.action_edit_product:
                EditText editName = (EditText) editView.findViewById(R.id.edit_name);
                EditText editPrice = (EditText) editView.findViewById(R.id.edit_price);
                EditText editSalePrice = (EditText) editView.findViewById(R.id.edit_saleprice);

                Cursor cursorProduct = Storage.getSingleProduct(info.id);

                editName.setText(cursorProduct.getString(1));
                editPrice.setText(String.valueOf(cursorProduct.getFloat(2)));
                editSalePrice.setText(String.valueOf(cursorProduct.getFloat(3)));
                productID = info.id;
                alert.show();


            default: return super.onContextItemSelected(item);

        }


    }


}
