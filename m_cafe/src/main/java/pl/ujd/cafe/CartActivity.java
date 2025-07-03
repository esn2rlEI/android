package pl.ujd.cafe;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.ujd.cafe.models.Item;

public final class CartActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_cart);

        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CartManager.getInstance().getItems());
        ((ListView) this.findViewById(R.id.cart_list)).setAdapter(adapter);

        this.findViewById(R.id.checkout_button).setOnClickListener(view -> {
            CartManager.getInstance().clear();
            adapter.clear(); adapter.notifyDataSetChanged();
            Toast.makeText(this, "Checkout", Toast.LENGTH_SHORT).show();
        });
    }

}