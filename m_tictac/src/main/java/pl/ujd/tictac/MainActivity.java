package pl.ujd.tictac;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

    private final Button[][] buttons = new Button[3][3];
    private GridLayout gameGrid;
    private TextView scoreSummary;

    private boolean xTurn = true;
    private int moves = 0;

    private double scoreX = 0.0, scoreO = 0.0;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.gameGrid = findViewById(R.id.gameGrid);
        this.scoreSummary = findViewById(R.id.scoreSummary);
        final Button newGameButton = findViewById(R.id.newGameButton);

        for (int i = 0; i < this.gameGrid.getRowCount(); i++) {
            for (int j = 0; j < this.gameGrid.getColumnCount(); j++) {
                final Button btn = new Button(this);
                btn.setTextSize(24);
                final int row = i;
                final int col = j;

                btn.setOnClickListener(view -> this.makeMove(row, col));

                this.gameGrid.addView(btn);
                this.buttons[i][j] = btn;
            }
        }

        newGameButton.setOnClickListener(view -> this.resetBoard());
        this.updateSummary();
    }

    private boolean checkWinner(final String symbol) {
        for (int i = 0; i < 3; i++)
            if (this.buttons[i][0].getText().equals(symbol) && this.buttons[i][1].getText().equals(symbol) && this.buttons[i][2].getText().equals(symbol)) return true;
        for (int i = 0; i < 3; i++)
            if (this.buttons[0][i].getText().equals(symbol) && this.buttons[1][i].getText().equals(symbol) && this.buttons[2][i].getText().equals(symbol)) return true;
        if (this.buttons[0][0].getText().equals(symbol) && this.buttons[1][1].getText().equals(symbol) && this.buttons[2][2].getText().equals(symbol)) return true;
        return this.buttons[0][2].getText().equals(symbol) && this.buttons[1][1].getText().equals(symbol) && this.buttons[2][0].getText().equals(symbol);
    }

    private void makeMove(final int row, final int col) {
        final String symbol = this.xTurn ? "X" : "O";
        this.buttons[row][col].setText(symbol);
        this.buttons[row][col].setEnabled(false);
        this.moves++;

        if (checkWinner(symbol)) {
            this.endGame(symbol);
            this.resetBoard();
        } else if (this.moves == 9) {
            this.endGame("Draw");
            this.resetBoard();
        } else {
            this.xTurn = !this.xTurn;
        }
    }

    private void resetBoard() {
        this.moves = 0;
        this.xTurn = true;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.buttons[i][j].setText("");
                this.buttons[i][j].setEnabled(true);
            }
        }
    }

    private void endGame(String winner) {
        if ("X".equals(winner)) {
            this.scoreX += 1.0;
        } else if ("O".equals(winner)) {
            this.scoreO += 1.0;
        } else {
            this.scoreX += 0.5;
            this.scoreO += 0.5;
        }
        this.updateSummary();
    }

    private void updateSummary() {
        this.scoreSummary.setText(String.format("X: %.1f | O: %.1f", this.scoreX, this.scoreO));
    }

}