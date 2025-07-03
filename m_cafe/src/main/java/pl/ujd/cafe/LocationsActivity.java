package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pl.ujd.cafe.models.Location;

public final class LocationsActivity extends AppCompatActivity {

    final ArrayList<Location> locations = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_locations);

        this.locations.add(new Location("Downtown", "123 Main St", 40.7128, -74.0060));
        this.locations.add(new Location("Uptown", "456 Elm St", 40.7851, -73.9683));

        final ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.locations);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {
            final Location loc = this.locations.get(position);
            final Intent intent = new Intent(LocationsActivity.this, MapActivity.class);
            intent.putExtra("lat", loc.getLatitude());
            intent.putExtra("lng", loc.getLongitude());
            intent.putExtra("location_name", loc.getName());
            this.startActivity(intent);
        });
    }

}