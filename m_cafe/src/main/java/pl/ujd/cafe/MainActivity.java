package pl.ujd.cafe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

    private TextView info;
    private ImageView map;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_main);

        final Spinner drinks = this.findViewById(R.id.drinkSpinner);
        final Spinner snacks = this.findViewById(R.id.snackSpinner);
        final Spinner locations = this.findViewById(R.id.locationSpinner);

        this.info = this.findViewById(R.id.infoText);
        this.map = this.findViewById(R.id.mapImage);

        drinks.setOnItemSelectedListener(new SimpleListener() {
            @Override public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                final String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("None")) {
                    info.setText(item + " - Price: " + getDrinkPrice(item));
                    map.setImageDrawable(null);
                }
            }
        });
        snacks.setOnItemSelectedListener(new SimpleListener() {
            @Override public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                final String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("None")) {
                    info.setText(item + " - Price: " + getSnackPrice(item));
                    map.setImageDrawable(null);
                }
            }
        });
        locations.setOnItemSelectedListener(new SimpleListener() {
            @Override public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                final String item = parent.getItemAtPosition(position).toString();
                if (!item.equals("None")) {
                    if (item.equals("Warsaw Central Cafe")) {
                        info.setText("Warsaw Central Cafe\nAddress: Main Street 1\nOpen: 08:00-22:00");
//                        map.setImageResource(R.drawable.warsaw_central_cafe);
                    } else if (item.equals("Downtown Deli Cracow")) {
                        info.setText("Downtown Deli Cracow\nAddress: High Street 1\nOpen: 07:00-23:00");
//                        map.setImageResource(R.drawable.downtown_deli_cracow);
                    }
                }
            }
        });
    }

    private String getSnackPrice(final String name) {
        switch (name) {
            case "Sandwich": return "5.00 PLN";
            case "Cookie": return "1.20 PLN";
            case "Croissant": return "3.00 PLN";
            default: return "N/A";
        }
    }

    private String getDrinkPrice(final String name) {
        switch (name) {
            case "Espresso": return "4.00 PLN";
            case "Latte": return "4.20 PLN";
            case "Tea": return "2.00 PLN";
            default: return "N/A";
        }
    }

    abstract class SimpleListener implements AdapterView.OnItemSelectedListener {
        public void onNothingSelected(final AdapterView<?> parent) {}
    }

}