package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public final class LocationDetailActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_location_detail);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("location_name");
        final String address = intent.getStringExtra("location_address");
        final String hours = intent.getStringExtra("location_hours");
        final int imageId = intent.getIntExtra("location_image_id", 0);

        ((TextView) this.findViewById(R.id.location_name)).setText(name);
        ((TextView) this.findViewById(R.id.location_address)).setText(address);
        ((TextView) this.findViewById(R.id.location_hours)).setText(String.format(Locale.getDefault(), "Open: %s", hours));
        ((ImageView) this.findViewById(R.id.location_image)).setImageResource(imageId);
    }

}