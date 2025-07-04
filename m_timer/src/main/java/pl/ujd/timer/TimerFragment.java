package pl.ujd.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public final class TimerFragment extends Fragment {

    private EditText input1, input2;
    private Button start1, start2;
    private TextView result1, result2;

    private CountDownTimer timer1, timer2;

    @Override public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        this.input1 = new EditText(this.getContext());
        this.input1.setHint("Seconds for timer #1");
        layout.addView(this.input1);
        this.result1 = new TextView(this.getContext());
        layout.addView(this.result1);
        this.start1 = new Button(this.getContext());
        this.start1.setText(R.string.timer_start);
        layout.addView(this.start1);

        this.input2 = new EditText(this.getContext());
        this.input2.setHint("Seconds for timer #2");
        layout.addView(this.input2);
        this.result2 = new TextView(this.getContext());
        layout.addView(this.result2);
        this.start2 = new Button(this.getContext());
        this.start2.setText(R.string.timer_start);
        layout.addView(this.start2);

        if (savedInstanceState != null) {
            this.input1.setText(savedInstanceState.getString("timer_input_1"));
            this.input2.setText(savedInstanceState.getString("timer_input_2"));
            this.result1.setText(savedInstanceState.getString("timer_result_1"));
            this.result2.setText(savedInstanceState.getString("timer_result_2"));
            if (!TextUtils.isEmpty(this.result1.getText()) && TextUtils.isDigitsOnly(this.result1.getText())) {
                this.input1.setText(this.result1.getText());
                this.start1.performClick();
            }
            if (!TextUtils.isEmpty(this.result2.getText()) && TextUtils.isDigitsOnly(this.result2.getText())) {
                this.input2.setText(this.result2.getText());
                this.start2.performClick();
            }
        }

        this.start1.setOnClickListener(view -> {
            if (TextUtils.isEmpty(this.input1.getText()) || !TextUtils.isDigitsOnly(this.input1.getText())) return;
            final long seconds = Long.parseLong(this.input1.getText().toString());
            if (this.timer1 != null) this.timer1.cancel();
            this.timer1 = new CountDownTimer(seconds * 1000, 10) {
                @Override public void onTick(final long millisUntilFinished) {
                    final long remaining = (millisUntilFinished % 1000) / 10;
                    result1.setText(String.format(Locale.getDefault(), "%02d.%02d", millisUntilFinished / 1000, remaining));
                }
                @Override public void onFinish() {
                    result1.setText(R.string.timer_done);
                }
            };
            this.timer1.start();
        });
        this.start2.setOnClickListener(view -> {
            if (TextUtils.isEmpty(this.input2.getText()) || !TextUtils.isDigitsOnly(this.input2.getText())) return;
            final long seconds = Long.parseLong(this.input2.getText().toString());
            if (this.timer2 != null) this.timer2.cancel();
            this.timer2 = new CountDownTimer(seconds * 1000, 10) {
                @Override public void onTick(final long millisUntilFinished) {
                    final long remaining = (millisUntilFinished % 1000) / 10;
                    result2.setText(String.format(Locale.getDefault(), "%02d.%02d", millisUntilFinished / 1000, remaining));
                }
                @Override public void onFinish() {
                    result2.setText(R.string.timer_done);
                }
            };
            this.timer2.start();
        });

        return layout;
    }

    @Override public void onSaveInstanceState(@NonNull final Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("timer_input_1", this.input1.getText().toString());
        state.putString("timer_input_2", this.input2.getText().toString());
        state.putString("timer_result_1", this.result1.getText().toString());
        state.putString("timer_result_2", this.result2.getText().toString());
    }

}