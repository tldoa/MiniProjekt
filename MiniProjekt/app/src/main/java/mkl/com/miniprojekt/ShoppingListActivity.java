package mkl.com.miniprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShoppingListActivity extends AppCompatActivity {

    ListView listView;

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
    }


}
