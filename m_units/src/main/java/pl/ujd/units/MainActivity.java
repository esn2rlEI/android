package pl.ujd.units;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public final class MainActivity extends AppCompatActivity {

    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private EditText input;
    private TextView result;

    private String conversionType = "Number System";
    private String fromUnit = "";
    private String toUnit = "";

    @Override public void onSaveInstanceState(@NonNull final Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("stopwatch_input", this.input.getText().toString());
        state.putString("stopwatch_result", this.result.getText().toString());
        state.putString("stopwatch_from_unit", this.fromUnit);
        state.putString("stopwatch_to_unit", this.toUnit);
    }

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        this.toUnitSpinner = findViewById(R.id.toUnitSpinner);
        this.input = findViewById(R.id.inputEditText);
        this.result = findViewById(R.id.resultTextView);

        if (savedInstanceState != null) {
            this.input.setText(savedInstanceState.getString("stopwatch_input"));
            this.result.setText(savedInstanceState.getString("stopwatch_result"));
            this.fromUnit = savedInstanceState.getString("stopwatch_from_unit");
            this.toUnit = savedInstanceState.getString("stopwatch_to_unit");
        }

        ((Spinner) this.findViewById(R.id.conversionTypeSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                conversionType = parent.getItemAtPosition(position).toString();
                setupUnitSpinners();
            }
            public void onNothingSelected(final AdapterView<?> parent) {}
        });

        this.findViewById(R.id.convertButton).setOnClickListener(v -> {
            try {
                final double inputValue = Double.parseDouble(this.input.getText().toString());
                this.result.setText(this.performConversion(inputValue));
            } catch (final NumberFormatException exception) {
                this.result.setText(R.string.invalid_input);
            }
        });
    }

    private void setupUnitSpinners() {
        final String[] units;

        if ("Number System".equals(this.conversionType)) {
            units = new String[]{"Decimal", "Binary", "Quaternary", "Octal", "Hexadecimal"};
        } else if ("Currency".equals(this.conversionType)) {
            units = new String[]{"PLN", "USD", "EUR"};
        } else {
            units = new String[]{"mm", "cm", "inch", "ft", "yd", "m", "km",
                    "mm²", "cm²", "m²", "km²", "a", "ha"};
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.fromUnitSpinner.setAdapter(adapter);
        this.toUnitSpinner.setAdapter(adapter);

        this.fromUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                fromUnit = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(final AdapterView<?> parent) {}
        });
        this.toUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                toUnit = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(final AdapterView<?> parent) {}
        });
    }

    private String performConversion(final double value) {
        switch (this.conversionType) {
            case "Number System":
                return convertNumberSystem((long) value, this.fromUnit, this.toUnit);
            case "Currency":
                return convertCurrency(value, this.fromUnit, this.toUnit);
            case "Unit":
                return convertUnit(value, this.fromUnit, this.toUnit);
            default:
                return "Invalid conversion.";
        }
    }

    private String convertNumberSystem(final long value, final String from, final String to) {
        try {
            long decimal = parseNumber(value, from);
            switch (to) {
                case "Decimal": return String.valueOf(decimal);
                case "Binary": return Long.toBinaryString(decimal);
                case "Quaternary": return Long.toString(decimal, 4);
                case "Octal": return Long.toOctalString(decimal);
                case "Hexadecimal": return Long.toHexString(decimal).toUpperCase();
                default: return "Invalid conversion";
            }
        } catch (final Exception exception) {
            return "Invalid input for the selected numeral system";
        }
    }

    private long parseNumber(final long value, final String from) {
        final String str = String.valueOf(value);
        switch (from) {
            case "Decimal": return Long.parseLong(str, 10);
            case "Binary": return Long.parseLong(str, 2);
            case "Quaternary": return Long.parseLong(str, 4);
            case "Octal": return Long.parseLong(str, 8);
            case "Hexadecimal": return Long.parseLong(str, 16);
            default: return value;
        }
    }

    private String convertCurrency(final double value, final String from, final String to) {
        final double plnToUSD = 0.25;
        final double plnToEUR = 0.22;
        final double usdToPLN = 4.0;
        final double eurToPLN = 4.5;

        double result = value;

        if (from.equals("PLN")) {
            if (to.equals("USD")) result = value * plnToUSD;
            if (to.equals("EUR")) result = value * plnToEUR;
        } else if (from.equals("USD")) {
            if (to.equals("PLN")) result = value * usdToPLN;
        } else if (from.equals("EUR")) {
            if (to.equals("PLN")) result = value * eurToPLN;
        } else if (from.equals(to)) {
            result = value;
        }

        return String.format(Locale.getDefault(), "%.2f", result);
    }

    private String convertUnit(final double value, final String from, final String to) {
        double result = value;
        double fromMultiplier = getLengthMultiplier(from);
        double toMultiplier = getLengthMultiplier(to);

        if (fromMultiplier > 0 && toMultiplier > 0) {
            result = value * fromMultiplier / toMultiplier;
        } else {
            return "Invalid units.";
        }

        return String.format(Locale.getDefault(), "%.4f", result);
    }

    private double getLengthMultiplier(final String unit) {
        switch (unit) {
            case "mm": return 0.001;
            case "cm": return 0.01;
            case "inch": return 0.0254;
            case "ft": return 0.3048;
            case "yd": return 0.9144;
            case "m": return 1.0;
            case "km": return 1000.0;
            default: return -1;
        }
    }

}