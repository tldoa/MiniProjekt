package mkl.com.miniprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    public void onClickCreateProduct(View view) {
        TextView productName = (TextView) findViewById(R.id.txt_product_name);
        TextView productPrice = (TextView) findViewById(R.id.np_productPrice);
        TextView productSalePrice = (TextView) findViewById(R.id.np_productSalePrice);

        String name = productName.getText().toString();
        double price = Double.parseDouble(productPrice.getText().toString()) ;
        double salePrice = Double.parseDouble(productSalePrice.getText().toString());

        int id = getIntent().getIntExtra("id", 0);
        Log.d("PENIS", "ID seen from the AddProductActivity: " + id);

        Storage.addProduct(name, price, salePrice, id);
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("ProductID", id);
        startActivity(intent);
    }
}
