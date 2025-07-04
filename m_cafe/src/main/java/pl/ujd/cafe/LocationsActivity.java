package pl.ujd.cafe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pl.ujd.cafe.models.Location;

public final class LocationsActivity extends AppCompatActivity {

    private final DatabaseHelper HELPER = new DatabaseHelper(this);

    final ArrayList<Location> locations = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_locations);

        final SQLiteDatabase database = this.HELPER.getReadableDatabase();
        final Cursor cursor = database.query("locations", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            final String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            final String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            final String hours = cursor.getString(cursor.getColumnIndexOrThrow("hours"));
            this.locations.add(new Location(name, address, hours, this.getImageIdForLocation(name)));
        }
        cursor.close();

        final ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.locations);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {
            final Location location = this.locations.get(position);
            final Intent intent = new Intent(LocationsActivity.this, LocationDetailActivity.class);
            intent.putExtra("location_name", location.getName());
            intent.putExtra("location_address", location.getAddress());
            intent.putExtra("location_hours", location.getHours());
            intent.putExtra("location_image_id", location.getImageId());
            this.startActivity(intent);
        });
    }

    private int getImageIdForLocation(final String name) {
        switch (name.toLowerCase()) {
            case "the clever clogs tavern": return R.drawable.clogs;
            case "the new port": return R.drawable.new_port;
            default: return R.drawable.seven_cats;
        }
    }

}