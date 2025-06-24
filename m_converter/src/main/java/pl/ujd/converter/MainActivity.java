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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            } else if (!TextUtils.isEmpty(input) && this.isRomansOnly(input.toUpperCase())) {
                this.result.setText("" + this.toArabic(input.toUpperCase()));
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

    private int toArabic(final String number) {
        final Map<Character, Integer> values = new HashMap<>();
        values.put('I', 1);
        values.put('V', 5);
        values.put('X', 10);
        values.put('L', 50);
        values.put('C', 100);
        values.put('D', 500);
        values.put('M', 1000);
        int current = 0, previous = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            final int value = values.getOrDefault(number.charAt(i), 0);
            if (value < previous) current -= value;
            else current += value;
            previous = value;
        }
        return current;
    }

    private boolean isRomansOnly(final String text) {
        final List<Character> values = new ArrayList<>();
        values.add('I');
        values.add('V');
        values.add('X');
        values.add('L');
        values.add('C');
        values.add('D');
        values.add('M');
        for (int i = 0; i < text.length(); i++) {
            if (!values.contains(text.charAt(i))) return false;
        }
        return true;
    }

}