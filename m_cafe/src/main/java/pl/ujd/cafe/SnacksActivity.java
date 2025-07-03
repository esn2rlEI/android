package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pl.ujd.cafe.models.Item;

public final class SnacksActivity extends AppCompatActivity {

    private final ArrayList<Item> snacks = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_snacks);

        this.snacks.add(new Item("Muffin", "Blueberry muffin", R.drawable.muffin));
        this.snacks.add(new Item("Cookie", "Chocolate cookie", R.drawable.cookie));

        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.snacks);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {
            final Item item = this.snacks.get(position);
            final Intent intent = new Intent(SnacksActivity.this, SnackDetailActivity.class);
            intent.putExtra("snack_name", item.getName());
            intent.putExtra("snack_description", item.getDescription());
            intent.putExtra("snack_image_id", item.getImageId());
            this.startActivity(intent);
        });
    }

}