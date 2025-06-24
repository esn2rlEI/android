package pl.ujd.converter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public final class MainActivity extends AppCompatActivity {

    private EditText arabic;
    private Button submit;
    private TextView result;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_main);

        this.arabic = this.findViewById(R.id.arabic);
        this.submit = this.findViewById(R.id.submit);
        this.result = this.findViewById(R.id.result);

        this.submit.setOnClickListener(view -> {
            final String input = this.arabic.getText().toString();
            if (!TextUtils.isEmpty(input) && TextUtils.isDigitsOnly(input)) {
                this.result.setText(this.toRoman(Integer.parseInt(input)));
            } else {
                this.result.setText("Invalid number");
            }
        });
    }

    private String toRoman(int number) {
        final String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                builder.append(symbols[i]);
            }
        }
        return builder.toString();
    }

}