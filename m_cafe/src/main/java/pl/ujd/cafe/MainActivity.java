package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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