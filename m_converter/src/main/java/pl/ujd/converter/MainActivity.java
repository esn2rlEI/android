package pl.ujd.converter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public final class MainActivity extends AppCompatActivity {

    private final String[] ROMANS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private final int[] ARABICS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private final Map<Character, Integer> ROARABS = Map.ofEntries(Map.entry('M', 1000), Map.entry('D', 500),
            Map.entry('C', 100), Map.entry('L', 50), Map.entry('X', 10), Map.entry('V', 5), Map.entry('I', 1));

    private EditText input;
    private TextView result;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.input = this.findViewById(R.id.input);
        this.result = this.findViewById(R.id.result);

        if (savedInstanceState != null) {
            this.result.setText(savedInstanceState.getString("result"));
        }

        this.findViewById(R.id.submit).setOnClickListener(view -> {
            final String text = this.input.getText().toString();
            if (TextUtils.isEmpty(text)) {
                this.result.setText(""); return;
            }
            if (TextUtils.isDigitsOnly(text) && this.isArabicValid(text)) {
                this.result.setText(this.toRoman(Integer.parseInt(text)));
            } else if (this.isRomansOnly(text.toUpperCase()) && this.isRomansValid(text.toUpperCase())) {
                this.result.setText(String.format("%s", this.toArabic(text.toUpperCase())));
            } else {
                this.result.setText(String.format("%s", "Invalid numeral"));
            }
        });
    }

    @Override protected void onSaveInstanceState(@NonNull final Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("result", this.result.getText().toString());
    }

    private boolean isRomansOnly(final String text) {
        for (int i = 0; i < text.length(); i++) {
            boolean s = false;
            for (int j = 0; j < this.ROMANS.length; j++) {
                if (("" + text.charAt(i)).equals(this.ROMANS[j])) {s = true; break;}
            }
            if (!s) return false;
        }
        return true;
    }

    private boolean isRomansValid(final String text) {
        return text.matches("(^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$)");
    }

    private String toRoman(int number) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.ARABICS.length; i++) {
            while (number >= this.ARABICS[i]) {
                number -= this.ARABICS[i]; builder.append(this.ROMANS[i]);
            }
        }
        return builder.toString();
    }

    private boolean isArabicValid(final String number) {
        return !number.startsWith("0");
    }

    private int toArabic(final String number) {
        int current = 0, previous = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            final int value = this.ROARABS.getOrDefault(number.charAt(i), 0);
            if (value < previous) current -= value;
            else current += value;
            previous = value;
        }
        return current;
    }

}