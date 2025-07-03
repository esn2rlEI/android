package pl.ujd.stats;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public final class MainActivity extends AppCompatActivity {

    private EditText paceInput, speedInput, distanceInput, timeInput, goalInput;
    private TextView result;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.paceInput = this.findViewById(R.id.paceInput);
        this.speedInput = this.findViewById(R.id.speedInput);
        this.distanceInput = this.findViewById(R.id.distanceInput);
        this.timeInput = this.findViewById(R.id.timeInput);
        this.goalInput = this.findViewById(R.id.goalInput);

        this.findViewById(R.id.paceSubmit).setOnClickListener(view -> {
            if (!TextUtils.isEmpty(this.paceInput.getText())) {
                final double pace = Double.parseDouble(this.paceInput.getText().toString());
                this.result.setText(this.getResult(60 / pace, pace));
            }
        });
        this.findViewById(R.id.speedSubmit).setOnClickListener(view -> {
            if (!TextUtils.isEmpty(this.speedInput.getText())) {
                final double speed = Double.parseDouble(this.speedInput.getText().toString());
                this.result.setText(this.getResult(speed, 60 / speed));
            }
        });
        this.findViewById(R.id.distanceSubmit).setOnClickListener(view -> {
            if (!TextUtils.isEmpty(this.paceInput.getText()) && !TextUtils.isEmpty(distanceInput.getText())) {
                final double pace = Double.parseDouble(this.paceInput.getText().toString());
                final double distance = Double.parseDouble(this.distanceInput.getText().toString());
                this.result.setText(String.format("Time: %s", this.format(Math.round(distance * pace * 60))));
            }
        });
        this.findViewById(R.id.goalSubmit).setOnClickListener(view -> {
            if (!TextUtils.isEmpty(this.timeInput.getText()) &&  !TextUtils.isEmpty(this.goalInput.getText())) {
                final double time = Double.parseDouble(this.timeInput.getText().toString());
                final double distance = Double.parseDouble(this.goalInput.getText().toString());
                final double pace = time / distance;
                this.result.setText(String.format(
                        Locale.getDefault(),
                        "Pace: %.2f min/km, Speed: %.2f km/h",
                        pace,
                        60 / pace
                ));
            }
        });

        this.result = this.findViewById(R.id.result);

        if (savedInstanceState != null) {
            this.result.setText(savedInstanceState.getString("result"));
        }
    }

    @Override protected void onSaveInstanceState(@NonNull final Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("result", this.result.getText().toString());
    }

    private String getResult(final double speed, final double pace) {
        final long full = Math.round(42.195 * pace * 60);
        final long half = Math.round((float) full / 2);
        return String.format(
                Locale.getDefault(),
                "Speed: %.2f km/h\nMarathon: %s\nHalf-marathon: %s",
                speed,
                this.format(full),
                this.format(half)
        );
    }

    private String format(final long seconds) {
        return String.format(Locale.getDefault(), "%dh %dm %ds", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

}