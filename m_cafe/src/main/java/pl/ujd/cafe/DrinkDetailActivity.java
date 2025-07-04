package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import pl.ujd.cafe.models.Item;

public final class DrinkDetailActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_drink_detail);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("drink_name");
        final float price = intent.getFloatExtra("drink_price", 0.0f);
        final String description = intent.getStringExtra("drink_description");
        final int imageId = intent.getIntExtra("drink_image_id", 0);

        ((TextView) this.findViewById(R.id.drink_name)).setText(name);
        ((TextView) this.findViewById(R.id.drink_price)).setText(String.format(Locale.getDefault(), "%.2f PLN", price));
        ((TextView) this.findViewById(R.id.drink_description)).setText(description);
        ((ImageView) this.findViewById(R.id.drink_image)).setImageResource(imageId);

        this.findViewById(R.id.add_to_cart).setOnClickListener(view -> {
            final String input = ((EditText) this.findViewById(R.id.quantity_input)).getText().toString();
            int quantity = 1;
            if (TextUtils.isDigitsOnly(input)) quantity = Integer.parseInt(input);
            if (quantity < 1 || quantity > 10) {
                Toast.makeText(this, "1-10 products at once", Toast.LENGTH_SHORT).show(); return;
            }
            for (int i = 0; i < quantity; i++) {
                CartManager.getInstance().addItem(new Item(name, price, description, imageId));
            }
            Toast.makeText(this, "Added to cart (x" + quantity + ")", Toast.LENGTH_SHORT).show();
        });
    }

}