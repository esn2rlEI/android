package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pl.ujd.cafe.models.Item;

public final class DrinksActivity extends AppCompatActivity {

    private final ArrayList<Item> drinks = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_drinks);

        this.drinks.add(new Item("Espresso", "Strong coffee shot", R.drawable.espresso));
        this.drinks.add(new Item("Latte", "Milky", R.drawable.latte));

        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.drinks);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {
            final Item item = this.drinks.get(position);
            final Intent intent = new Intent(DrinksActivity.this, DrinkDetailActivity.class);
            intent.putExtra("drink_name", item.getName());
            intent.putExtra("drink_description", item.getDescription());
            intent.putExtra("drink_image_id", item.getImageId());
            this.startActivity(intent);
        });
    }

}