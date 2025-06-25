package pl.ujd.cafe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;

    private TextView info;
    private ImageView map;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_main);

        this.info = this.findViewById(R.id.infoText);
        this.map = this.findViewById(R.id.mapImage);

        this.helper = new DatabaseHelper(this);

        this.spinnerSetup(R.id.drinkSpinner, "drinks");
        this.spinnerSetup(R.id.snackSpinner, "snacks");
        this.spinnerSetup(R.id.locationSpinner, "locations");
    }

    private void spinnerSetup(final int id, final String table) {
        final Spinner spinner = this.findViewById(id);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                this.getNames(table)
        );
        adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String selected = parent.getItemAtPosition(position).toString();
                if (!selected.equals("None")) {
                    if (table.equals("drinks") || table.equals("snacks")) {
                        showProductInfo(table, selected);
                    } else showLocationInfo(selected);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String[] getNames(final String table) {
        final SQLiteDatabase database = this.helper.getReadableDatabase();
        final Cursor cursor = database.rawQuery("SELECT name FROM " + table, null);
        final String[] names = new String[cursor.getCount() + 1];
        names[0] = "None";
        int i = 1;
        while (cursor.moveToNext()) {
            names[i++] = cursor.getString(0);
        }
        cursor.close();
        return names;
    }

    private void showProductInfo(final String table, final String name) {
        final SQLiteDatabase database = this.helper.getReadableDatabase();
        final Cursor cursor = database.rawQuery("SELECT price, description FROM " + table + " WHERE name=?", new String[]{name});
        if (cursor.moveToFirst()) {
            double price = cursor.getDouble(0);
            String description = cursor.getString(1);
            this.info.setText(name + " – Price: " + price + " PLN, " + description);
//            this.map.setImageDrawable(null);
        }
        cursor.close();
    }

    private void showLocationInfo(final String name) {
        final SQLiteDatabase database = this.helper.getReadableDatabase();
        final Cursor cursor = database.rawQuery("SELECT address, hours, map_image_name FROM locations WHERE name=?", new String[]{name});
        if (cursor.moveToFirst()) {
            final String address = cursor.getString(0);
            final String hours = cursor.getString(1);
            this.info.setText(name + '\n' + "Address: " + address + '\n' + "Open: " + hours);
//            this.map.setImageResource(this.getResources().getIdentifier(name, "drawable", this.getPackageName()));
        }
        cursor.close();
    }

}

























