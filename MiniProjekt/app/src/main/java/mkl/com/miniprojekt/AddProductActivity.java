package mkl.com.miniprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    public void onClickCreateProduct(View view) {
            final int shopId = getIntent().getIntExtra("ShopID", 0);
            final String shopName = getIntent().getStringExtra("shopName");
        EditText nameText = (EditText)findViewById(R.id.txt_product_name);
        EditText priceText = (EditText)findViewById(R.id.txt_product_price);
        EditText salePriceText = (EditText)findViewById(R.id.txt_product_salePrice);

        String name = nameText.getText().toString();
        double price = Double.parseDouble(priceText.getText().toString());
        double salePrice = Double.parseDouble(salePriceText.getText().toString());


        Product product = new Product(name,price,salePrice,shopId);
                    Storage.addProduct(product);
                    Intent intent = new Intent(this, ProductListActivity.class);
                    intent.putExtra("shopId", shopId);
                    intent.putExtra("NAME", shopName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
    }
}
