package pl.ujd.timer;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public final class StopwatchFragment extends Fragment {

    private final Handler HANDLER = new Handler();
    private final long INTERVAL = 10;

    private TextView time1, time2;
    private Button start1, start2, reset1, reset2, stop1, stop2;

    private long timeElapsed1 = 0, timeElapsed2 = 0;
    private boolean isRunning1 = false, isRunning2 = false;

    private final Runnable UPDATE1 = new Runnable() {
        @Override public void run() {
            if (isRunning1) {
                timeElapsed1 += INTERVAL;
                time1.setText(format(timeElapsed1));
                HANDLER.postDelayed(this, INTERVAL);
            }
        }
    };
    private final Runnable UPDATE2 = new Runnable() {
        @Override public void run() {
            if (isRunning2) {
                timeElapsed2 += INTERVAL;
                time2.setText(format(timeElapsed2));
                HANDLER.postDelayed(this, INTERVAL);
            }
        }
    };

    @Override public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        this.time1 = new TextView(this.getContext());
        this.time1.setText(R.string.stopwatch_zero);
        layout.addView(this.time1);
        layout.addView(this.getButtonLayout(1,
                this.start1 = new Button(this.getContext()),
                this.reset1 = new Button(this.getContext()),
                this.stop1 = new Button(this.getContext()))
        );
        this.time2 = new TextView(this.getContext());
        this.time2.setText(R.string.stopwatch_zero);
        layout.addView(this.time2);
        layout.addView(this.getButtonLayout(2,
                this.start2 = new Button(this.getContext()),
                this.reset2 = new Button(this.getContext()),
                this.stop2 = new Button(this.getContext()))
        );

        if (savedInstanceState != null) {
            this.time1.setText(savedInstanceState.getString("stopwatch_result_1"));
            this.time2.setText(savedInstanceState.getString("stopwatch_result_2"));
            this.timeElapsed1 = savedInstanceState.getLong("stopwatch_elapsed_1");
            this.timeElapsed2 = savedInstanceState.getLong("stopwatch_elapsed_2");

            if (savedInstanceState.getBoolean("stopwatch_is_running_1")) this.start1.performClick();
            if (savedInstanceState.getBoolean("stopwatch_is_running_2")) this.start2.performClick();
        }

        return layout;
    }

    @Override public void onSaveInstanceState(@NonNull final Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("stopwatch_result_1", this.time1.getText().toString());
        state.putString("stopwatch_result_2", this.time2.getText().toString());
        state.putLong("stopwatch_elapsed_1", this.timeElapsed1);
        state.putLong("stopwatch_elapsed_2", this.timeElapsed2);
        state.putBoolean("stopwatch_is_running_1", this.isRunning1);
        state.putBoolean("stopwatch_is_running_2", this.isRunning2);
    }

    private LinearLayout getButtonLayout(final int index, final Button start, final Button reset, final Button stop) {
        final LinearLayout row = new LinearLayout(this.getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);

        start.setText(R.string.stopwatch_start);
        row.addView(start);
        reset.setText(R.string.stopwatch_reset);
        row.addView(reset);
        stop.setText(R.string.stopwatch_stop);
        row.addView(stop);

        if (index == 1) {
            start.setOnClickListener(view -> {
                if (!this.isRunning1) {
                    this.isRunning1 = true;
                    this.HANDLER.post(this.UPDATE1);
                }
            });
            reset.setOnClickListener(view -> {
                this.isRunning1 = false;
                this.timeElapsed1 = 0;
                this.time1.setText(this.format(0));
            });
            stop.setOnClickListener(view -> this.isRunning1 = false);
        } else {
            start.setOnClickListener(view -> {
                if (!this.isRunning2) {
                    this.isRunning2 = true;
                    this.HANDLER.post(this.UPDATE2);
                }
            });
            reset.setOnClickListener(view -> {
                this.isRunning2 = false;
                this.timeElapsed2 = 0;
                this.time2.setText(this.format(0));
            });
            stop.setOnClickListener(view -> this.isRunning2 = false);
        }

        return row;
    }

    private String format(final long millis) {
        final long s = millis / 1000;
        final long m = s / 60;
        return String.format(Locale.getDefault(), "%02d:%02d.%02d", m, s % 60, (millis % 1000) / 10);
    }

}