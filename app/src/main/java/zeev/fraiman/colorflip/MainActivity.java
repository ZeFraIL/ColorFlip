package zeev.fraiman.colorflip;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final long BLITZ_MODE_DURATION = 30000; // 30 seconds
    private final int GRID_SIZE = 5;
    private View[][] grid = new View[GRID_SIZE][GRID_SIZE];
    private int[] colors = {Color.RED, Color.BLUE};
    private int[][] colorState = new int[GRID_SIZE][GRID_SIZE]; // 0 for first color, 1 for second
    private TextView stepsTextView;
    private int stepsCount = 0;
    private Switch blitzModeSwitch;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private boolean isBlitzModeActive = false;
    private boolean isTimeUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepsTextView = findViewById(R.id.steps_text_view);
        GridLayout gridLayout = findViewById(R.id.grid_layout);
        blitzModeSwitch = findViewById(R.id.blitz_mode_switch);
        timerTextView = findViewById(R.id.timer_text_view);

        gridLayout.setColumnCount(GRID_SIZE);
        gridLayout.setRowCount(GRID_SIZE);

        initializeGrid(gridLayout);

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> initializeGrid(gridLayout));

        blitzModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isBlitzModeActive = isChecked;
            if (isChecked) {
                startTimer();
                timerTextView.setVisibility(View.VISIBLE);
            } else {
                stopTimer();
                timerTextView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initializeGrid(GridLayout gridLayout) {
        gridLayout.removeAllViews();
        stepsCount = 0;
        isTimeUp = false;
        updateStepsText();
        stopTimer();
        blitzModeSwitch.setChecked(false);
        timerTextView.setVisibility(View.INVISIBLE);

        Random random = new Random();
        int squareSize = getResources().getDisplayMetrics().widthPixels / (GRID_SIZE + 1);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                View square = new View(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = squareSize;
                params.height = squareSize;
                params.setMargins(2, 2, 2, 2);
                square.setLayoutParams(params);

                colorState[i][j] = random.nextInt(2);
                square.setBackgroundColor(colors[colorState[i][j]]);

                final int row = i;
                final int col = j;
                square.setOnClickListener(v -> onSquareClicked(row, col));

                grid[i][j] = square;
                gridLayout.addView(square);
            }
        }
    }

    private void onSquareClicked(int row, int col) {
        if (isTimeUp) {
            Toast.makeText(this, "Time's up! Reset to play again.", Toast.LENGTH_SHORT).show();
            return;
        }

        stepsCount++;
        updateStepsText();
        int clickedColorIndex = colorState[row][col];
        int[][] positionsToFlip = {
                {row - 1, col},   // Top
                {row + 1, col},   // Bottom
                {row, col - 1},   // Left
                {row, col + 1}    // Right
        };

        for (int[] pos : positionsToFlip) {
            int r = pos[0];
            int c = pos[1];
            if (r >= 0 && r < GRID_SIZE && c >= 0 && c < GRID_SIZE) {
                if (colorState[r][c] != clickedColorIndex) {
                    flipColor(r, c);
                }
            }
        }

        checkWinCondition();
    }

    private void flipColor(int row, int col) {
        colorState[row][col] = 1 - colorState[row][col];
        grid[row][col].setBackgroundColor(colors[colorState[row][col]]);
    }

    private void checkWinCondition() {
        int firstColor = colorState[0][0];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (colorState[i][j] != firstColor) {
                    return; // Not all squares are the same color
                }
            }
        }
        stopTimer();
        Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
    }

    private void updateStepsText() {
        stepsTextView.setText("Steps: " + stepsCount);
    }

    private void startTimer() {
        isTimeUp = false;
        countDownTimer = new CountDownTimer(BLITZ_MODE_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60));
            }

            @Override
            public void onFinish() {
                isTimeUp = true;
                timerTextView.setText("00:00");
                Toast.makeText(MainActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
