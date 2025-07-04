package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// req:
    // snacks (x3), drinks (x3) and locations (x3) from database +
    // each item from list has "add to cart" button and amount to add +
    // each item in cart has "trash" button
    // item details contain name, image, description and price +
    // location details contain name, open hours and address +
// opt:
    // cart sums order price
    // location details contain map (img)
    // "checkout" button in cart sends order confirmation mail
    // home page has logo

public final class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.findViewById(R.id.drinks_button).setOnClickListener(view -> {
            this.startActivity(new Intent(this, DrinksActivity.class));
        });
        this.findViewById(R.id.snacks_button).setOnClickListener(view -> {
            this.startActivity(new Intent(this, SnacksActivity.class));
        });
        this.findViewById(R.id.locations_button).setOnClickListener(view -> {
            this.startActivity(new Intent(this, LocationsActivity.class));
        });
        this.findViewById(R.id.cart_button).setOnClickListener(view -> {
            this.startActivity(new Intent(this, CartActivity.class));
        });
    }

}