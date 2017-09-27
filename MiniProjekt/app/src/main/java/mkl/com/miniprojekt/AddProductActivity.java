package mkl.com.miniprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    public void onClickCreateProduct(View view) {

        TextView productName = (TextView) findViewById(R.id.txt_product_name);
        TextView productPrice = (TextView) findViewById(R.id.txt_product_price);
        TextView productSalePrice = (TextView) findViewById(R.id.txt_product_salePrice);
        TextView productQuantity = (TextView) findViewById(R.id.txt_product_quantity);

        String name = productName.getText().toString();
        double price = Double.parseDouble(productPrice.getText().toString()) ;
        double salePrice = Double.parseDouble(productSalePrice.getText().toString());
        int quantity = Integer.parseInt(productQuantity.getText().toString());

//        int id = getIntent().getIntExtra("id", 0);

//        Storage.addProduct(name, price, salePrice, id);
//        Intent intent = new Intent(this, ProductListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("ProductID", id);
//        startActivity(intent);

        final int shopId = getIntent().getIntExtra("ShopID", 0);
        final String shopName = getIntent().getStringExtra("shopName");

        Product product = new Product(name,price,salePrice,quantity,shopId);
        Storage.addProduct(product);
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("NAME", shopName);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
