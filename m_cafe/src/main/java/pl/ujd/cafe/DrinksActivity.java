package pl.ujd.cafe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pl.ujd.cafe.models.Item;

public final class DrinksActivity extends AppCompatActivity {

    private final DatabaseHelper HELPER = new DatabaseHelper(this);

    private final ArrayList<Item> drinks = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_drinks);

        final SQLiteDatabase database = this.HELPER.getReadableDatabase();
        final Cursor cursor = database.query("drinks", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            final String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            final float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
            final String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            this.drinks.add(new Item(name, price, description, this.getImageIdForDrink(name)));
        }
        cursor.close();

        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.drinks);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {
            final Item item = this.drinks.get(position);
            final Intent intent = new Intent(DrinksActivity.this, DrinkDetailActivity.class);
            intent.putExtra("drink_name", item.getName());
            intent.putExtra("drink_price", item.getPrice());
            intent.putExtra("drink_description", item.getDescription());
            intent.putExtra("drink_image_id", item.getImageId());
            this.startActivity(intent);
        });
    }

    private int getImageIdForDrink(final String name) {
        switch (name.toLowerCase()) {
            case "espresso": return R.drawable.espresso;
            case "latte": return R.drawable.latte;
            case "tea": return R.drawable.latte;
            default: return R.drawable.espresso;
        }
    }

}