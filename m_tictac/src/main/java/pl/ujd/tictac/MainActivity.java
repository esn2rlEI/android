package pl.ujd.tictac;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public final class MainActivity extends AppCompatActivity {

    private final Button[][] buttons = new Button[3][3];

    private TextView score;

    private double a = 0.0, b = 0.0;
    private boolean gate = true;
    private int moves = 0;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.score = findViewById(R.id.score);

        if (savedInstanceState != null) {
            this.score.setText(savedInstanceState.getString("score"));
            this.a = savedInstanceState.getDouble("a");
            this.b = savedInstanceState.getDouble("b");
            this.gate = savedInstanceState.getBoolean("gate");
            this.moves = savedInstanceState.getInt("moves");
        }

        final GridLayout layout = findViewById(R.id.game);
        for (int i = 0; i < layout.getRowCount(); i++) {
            for (int j = 0; j < layout.getColumnCount(); j++) {

                final Button field = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(i, 1.0f),
                        GridLayout.spec(j, 1.0f)
                );
                params.width = 0; params.height = 0;

                field.setLayoutParams(params);
                field.setTextSize(24);

                if (savedInstanceState != null) {
                    field.setText(savedInstanceState.getString(String.format(Locale.getDefault(), "field=%d:%d", i, j)));
                }

                final int row = i, col = j;
                field.setOnClickListener(view -> this.select(row, col));

                this.buttons[i][j] = field;
                layout.addView(field);
            }
        }

        this.findViewById(R.id.restart).setOnClickListener(view -> this.reset());
        this.update();
    }

    @Override protected void onSaveInstanceState(@NonNull final Bundle state) {
        super.onSaveInstanceState(state);
        for (int i = 0; i < this.buttons.length; i++) {
            for (int j = 0; j < this.buttons[i].length; j++) {
                state.putString(String.format(Locale.getDefault(), "field=%d:%d", i, j), this.buttons[i][j].getText().toString());
            }
        }
        state.putString("score", this.score.getText().toString());
        state.putDouble("a", this.a);
        state.putDouble("b", this.b);
        state.putBoolean("gate", this.gate);
        state.putInt("moves", this.moves);
    }

    private boolean check(final String symbol) {
        for (int i = 0; i < 3; i++)
            if (this.buttons[i][0].getText().equals(symbol) && this.buttons[i][1].getText().equals(symbol) && this.buttons[i][2].getText().equals(symbol)) return true;
        for (int i = 0; i < 3; i++)
            if (this.buttons[0][i].getText().equals(symbol) && this.buttons[1][i].getText().equals(symbol) && this.buttons[2][i].getText().equals(symbol)) return true;
        if (this.buttons[0][0].getText().equals(symbol) && this.buttons[1][1].getText().equals(symbol) && this.buttons[2][2].getText().equals(symbol)) return true;
        return this.buttons[0][2].getText().equals(symbol) && this.buttons[1][1].getText().equals(symbol) && this.buttons[2][0].getText().equals(symbol);
    }

    private void select(final int row, final int col) {
        final String symbol = this.gate ? "X" : "O";

        this.buttons[row][col].setText(symbol);
        this.buttons[row][col].setEnabled(false);
        this.moves++;

        if (this.check(symbol)) {
            this.end(symbol); this.reset();
        } else if (this.moves == 9) {
            this.end("Draw"); this.reset();
        } else {
            this.gate = !this.gate;
        }
    }

    private void reset() {
        this.moves = 0;
        this.gate = true;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.buttons[i][j].setText("");
                this.buttons[i][j].setEnabled(true);
            }
        }
    }

    private void end(final String winner) {
        if (winner.equals("X")) this.a += 1.0;
        else if ("O".equals(winner)) this.b += 1.0;
        else {this.a += 0.5; this.b += 0.5;}
        this.update();
    }

    private void update() {
        this.score.setText(String.format(Locale.getDefault(), "X: %.1f | O: %.1f", this.a, this.b));
    }

}