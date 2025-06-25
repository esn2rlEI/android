package pl.ujd.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public final class TimerFragment extends Fragment {

    private TextView result1, result2;
    private EditText input1, input2;
    private Button start1, start2;

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
        this.start1.setText("START #1");
        layout.addView(this.start1);

        this.input2 = new EditText(this.getContext());
        this.input2.setHint("Seconds for timer #2");
        layout.addView(this.input2);
        this.result2 = new TextView(this.getContext());
        layout.addView(this.result2);
        this.start2 = new Button(this.getContext());
        this.start2.setText("START #2");
        layout.addView(this.start2);

        this.start1.setOnClickListener(view -> {
            final long seconds = Long.parseLong(this.input1.getText().toString());
            if (this.timer1 != null) this.timer1.cancel();
            this.timer1 = new CountDownTimer(seconds * 1000, 10) {
                @Override public void onTick(final long millisUntilFinished) {
                    final long remaining = (millisUntilFinished % 1000) / 10;
                    result1.setText(String.format("%02d.%02d", millisUntilFinished / 1000, remaining));
                }
                @Override public void onFinish() {
                    result1.setText("Done");
                }
            };
            this.timer1.start();
        });
        this.start2.setOnClickListener(view -> {
            final long seconds = Long.parseLong(this.input2.getText().toString());
            if (this.timer2 != null) this.timer2.cancel();
            this.timer2 = new CountDownTimer(seconds * 1000, 10) {
                @Override public void onTick(final long millisUntilFinished) {
                    final long remaining = (millisUntilFinished % 1000) / 10;
                    result2.setText(String.format("%02d.%02d", millisUntilFinished / 1000, remaining));
                }
                @Override public void onFinish() {
                    result2.setText("Done");
                }
            };
            this.timer2.start();
        });

        return layout;
    }

}