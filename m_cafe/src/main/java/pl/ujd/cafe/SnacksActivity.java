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

public final class SnacksActivity extends AppCompatActivity {

    private final DatabaseHelper HELPER = new DatabaseHelper(this);

    private final ArrayList<Item> snacks = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_snacks);

        final SQLiteDatabase database = this.HELPER.getReadableDatabase();
        final Cursor cursor = database.query("snacks", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            final String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            final float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
            final String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            this.snacks.add(new Item(name, price, description, this.getImageIdForSnack(name)));
        }
        cursor.close();

        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.snacks);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {
            final Item item = this.snacks.get(position);
            final Intent intent = new Intent(SnacksActivity.this, SnackDetailActivity.class);
            intent.putExtra("snack_name", item.getName());
            intent.putExtra("snack_price", item.getPrice());
            intent.putExtra("snack_description", item.getDescription());
            intent.putExtra("snack_image_id", item.getImageId());
            this.startActivity(intent);
        });
    }

    private int getImageIdForSnack(final String name) {
        switch (name.toLowerCase()) {
            case "cookie": return R.drawable.cookie;
            case "muffin": return R.drawable.muffin;
            case "croissant": return R.drawable.cookie;
            default: return R.drawable.espresso;
        }
    }

}