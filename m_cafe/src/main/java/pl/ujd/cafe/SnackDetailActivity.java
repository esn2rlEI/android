package pl.ujd.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.ujd.cafe.models.Item;

public final class SnackDetailActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_snack_detail);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("snack_name");
        final String description = intent.getStringExtra("snack_description");
        final int imageId = intent.getIntExtra("snack_image_id", 0);

        ((TextView) this.findViewById(R.id.snack_name)).setText(name);
        ((TextView) this.findViewById(R.id.snack_description)).setText(description);
        ((ImageView) this.findViewById(R.id.snack_image)).setImageResource(imageId);

        this.findViewById(R.id.add_to_cart).setOnClickListener(view -> {
            CartManager.getInstance().addItem(new Item(name, description, imageId));
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

}