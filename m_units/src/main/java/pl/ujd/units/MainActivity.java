package pl.ujd.units;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner conversionTypeSpinner, fromUnitSpinner, toUnitSpinner;
    private EditText inputEditText;
    private TextView resultTextView;

    private String conversionType = "Number System"; // Default
    private String fromUnit = "";
    private String toUnit = "";

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.conversionTypeSpinner = findViewById(R.id.conversionTypeSpinner);
        this.fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        this.toUnitSpinner = findViewById(R.id.toUnitSpinner);
        this.inputEditText = findViewById(R.id.inputEditText);
        this.resultTextView = findViewById(R.id.resultTextView);
        final Button convertButton = findViewById(R.id.convertButton);

        this.conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                conversionType = parent.getItemAtPosition(position).toString();
                setupUnitSpinners();
            }
            public void onNothingSelected(final AdapterView<?> parent) {}
        });

        convertButton.setOnClickListener(v -> {
            try {
                final double inputValue = Double.parseDouble(this.inputEditText.getText().toString());
                final String result = performConversion(inputValue);
                this.resultTextView.setText(result);
            } catch (NumberFormatException e) {
                this.resultTextView.setText("Invalid input");
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
        long decimal;

        try {
            decimal = parseNumber(value, from);
        } catch (Exception e) {
            return "Invalid input for the selected numeral system.";
        }

        switch (to) {
            case "Decimal": return String.valueOf(decimal);
            case "Binary": return Long.toBinaryString(decimal);
            case "Quaternary": return Long.toString(decimal, 4);
            case "Octal": return Long.toOctalString(decimal);
            case "Hexadecimal": return Long.toHexString(decimal).toUpperCase();
            default: return "Invalid conversion.";
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

        return String.format("%.2f", result);
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

        return String.format("%.4f", result);
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