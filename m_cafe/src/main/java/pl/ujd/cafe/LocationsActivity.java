package pl.ujd.cafe;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public final class LocationsActivity extends AppCompatActivity {

    final ArrayList<String> locations = new ArrayList<>();

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_locations);

        this.locations.add("Warsaw, Waleczna 33");
        this.locations.add("Cracow, Al. Smoka 8");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.locations);

        ((ListView) this.findViewById(R.id.item_list)).setAdapter(adapter);
        ((ListView) this.findViewById(R.id.item_list)).setOnItemClickListener((parent, view, position, id) -> {});
    }

}