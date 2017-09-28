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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;


public class ProductListActivity extends AppCompatActivity {
    private int shopId;
    private long productID;
    private int quantitySelected;
    private String shopName;
    private Cursor cursor;
    private ListView pView;
    private AlertDialog editAlert;
    private AlertDialog addAlert;
    private View editView;
    private int productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shopName = getIntent().getStringExtra("NAME");
        setTitle(shopName);

        shopId = getIntent().getIntExtra("shopId",0);
        pView = (ListView) findViewById(R.id.lv_products);
        registerForContextMenu(pView);

        cursor = Storage.getProducts(shopId);

        final AlertDialog.Builder add_product_builder = new AlertDialog.Builder(this);

        add_product_builder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NumberPicker numberPicker = (NumberPicker) addAlert.findViewById(R.id.np_qty);
                        quantitySelected = numberPicker.getValue();
                        Storage.addToShoppingList(productID, quantitySelected);

                        Cursor c = Storage.getSingleProduct(productID);
                        Product product = new Product(
                                c.getString(1),
                                c.getInt(2),
                                c.getInt(3),
                                c.getInt(4),
                                c.getInt(5)
                        );

                        int remainingQty = product.getQuantity() - quantitySelected;

                        product.setQuantity(remainingQty);
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
        addAlert = add_product_builder.create();

        LayoutInflater add_product_inflater = LayoutInflater.from(this);
        View addProductView = add_product_inflater.inflate(R.layout.add_product_to_shopping_list, null);

        addAlert.setTitle("Add");
        addAlert.setView(addProductView);

        final SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.productlist_layout_list,
                        cursor,
                        new String[]{"NAME", "PRICE","QUANTITY"},
                        new int[]{R.id.single_product_name, R.id.single_product_price, R.id.single_product_quantity},
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
                        EditText editQuantity = (EditText) editView.findViewById(R.id.edit_quantity);

                        String updateName = editName.getText().toString();
                        Double updatePrice = Double.parseDouble(editPrice.getText().toString());
                        Double updateSalePrice = Double.parseDouble(editSalePrice.getText().toString());
                        int updateQuantity = Integer.parseInt(editQuantity.getText().toString());
                        Product product = new Product(updateName, updatePrice, updateSalePrice, updateQuantity, shopId);
                        Storage.updateProduct(product, productID);
                        cursor = Storage.getProducts(shopId);
                        SimpleCursorAdapter adapter = (SimpleCursorAdapter) pView.getAdapter();
                        adapter.changeCursor(cursor);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        editAlert = editBuilder.create();

        LayoutInflater editProduct = LayoutInflater.from(this);

        editView = editProduct.inflate(R.layout.edit_product, null);
        editAlert.setView(editView);

        pView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = cursorAdapter.getCursor();
                productID = cursor.getInt(cursor.getColumnIndex("_id"));
                productQuantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                final String name = cursor.getString(cursor.getColumnIndex("NAME"));

                addAlert.setMessage("Add " + name + " to shopping list!");
                addAlert.show();

                final NumberPicker qty = (NumberPicker) addAlert.findViewById(R.id.np_qty);
                qty.setMinValue(0);
                
                qty.setMaxValue(productQuantity);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(this, AddProductActivity.class);
                intent.putExtra("ShopID", shopId);
                intent.putExtra("shopName",shopName);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_products, menu);
    }

    @Override
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
                EditText editQuantity = (EditText) editView.findViewById(R.id.edit_quantity);

                Cursor cursorProduct = Storage.getSingleProduct(info.id);

                editName.setText(cursorProduct.getString(1));
                editPrice.setText(String.valueOf(cursorProduct.getFloat(2)));
                editSalePrice.setText(String.valueOf(cursorProduct.getFloat(3)));
                editQuantity.setText(String.valueOf(cursorProduct.getInt(4)));

                productID = info.id;
                editAlert.show();


            default: return super.onContextItemSelected(item);

        }
    }
}
