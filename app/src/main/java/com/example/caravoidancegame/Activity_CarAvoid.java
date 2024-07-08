package com.example.caravoidancegame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.caravoidencegame.R;
import com.google.android.material.button.MaterialButton;

import java.util.Random;



public class Activity_CarAvoid extends AppCompatActivity {

    private boolean isTimerStarted = false;

    public static final String KEY_SPEED = "KEY_SPEED";
    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_NAME = "KEY_NAME";

    private Handler obstacleMoveHandler;
    private Handler newObstacleHandler;

    private AppCompatImageView[] CarAvoid_IMG_Heart;

    private AppCompatImageView[] carLanes;
    private AppCompatImageView[][] obstacleMatrix;
    private AppCompatImageView[][] coinMatrix;
    private GameManager gm;
    private Runnable obstacleMoveRunnable;
    private Runnable newObstacleRunnable;
    private MaterialButton leftArrow;
    private MaterialButton rightArrow;
    private String playerName;
    private int newObstacleDELAY = 1000;
    private int obstacleDownDELAY = 500;
    private SensorDetector sensorDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gm = new GameManager(6);
        findViews();
        initGame();
        startGame();
       // setupArrowButtons();
    }

    private void initGame() {
        Intent previousIntent = getIntent();
        boolean isFastMode = previousIntent.getBooleanExtra("isFastMode", false);
        boolean isSensorMode = previousIntent.getBooleanExtra("isSensorMode", false);
        playerName = previousIntent.getStringExtra("playerName");

        if (isFastMode) {
            newObstacleDELAY = 600; // Adjust the delay for fast mode
        }

        if (isSensorMode) {
            leftArrow.setVisibility(View.INVISIBLE);
            rightArrow.setVisibility(View.INVISIBLE);
            sensorDetector.start(); // Uncomment and implement this method to start sensor detection
        } else {
            leftArrow.setOnClickListener(view -> moveCarLeft());
            rightArrow.setOnClickListener(view -> moveCarRight());
        }
    }




    private void findViews() {
        CarAvoid_IMG_Heart = new AppCompatImageView[] {
                findViewById(R.id.CarAvoid_IMG_heart1),
                findViewById(R.id.CarAvoid_IMG_heart2),
                findViewById(R.id.CarAvoid_IMG_heart3),

        };
        carLanes = new AppCompatImageView[] {
                findViewById(R.id.CarAvoid_IMG_car_lane_1),
                findViewById(R.id.CarAvoid_IMG_car_lane_2),
                findViewById(R.id.CarAvoid_IMG_car_lane_3),
                findViewById(R.id.CarAvoid_IMG_car_lane_4),
                findViewById(R.id.CarAvoid_IMG_car_lane_5)
        };
        obstacleMatrix = new AppCompatImageView[][] {
                {findViewById(R.id.CarAvoid_IMG_row1_obs1), findViewById(R.id.CarAvoid_IMG_row1_obs2), findViewById(R.id.CarAvoid_IMG_row1_obs3), findViewById(R.id.CarAvoid_IMG_row1_obs4), findViewById(R.id.CarAvoid_IMG_row1_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row2_obs1), findViewById(R.id.CarAvoid_IMG_row2_obs2), findViewById(R.id.CarAvoid_IMG_row2_obs3), findViewById(R.id.CarAvoid_IMG_row2_obs4), findViewById(R.id.CarAvoid_IMG_row2_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row3_obs1), findViewById(R.id.CarAvoid_IMG_row3_obs2), findViewById(R.id.CarAvoid_IMG_row3_obs3), findViewById(R.id.CarAvoid_IMG_row3_obs4), findViewById(R.id.CarAvoid_IMG_row3_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row4_obs1), findViewById(R.id.CarAvoid_IMG_row4_obs2), findViewById(R.id.CarAvoid_IMG_row4_obs3), findViewById(R.id.CarAvoid_IMG_row4_obs4), findViewById(R.id.CarAvoid_IMG_row4_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row5_obs1), findViewById(R.id.CarAvoid_IMG_row5_obs2), findViewById(R.id.CarAvoid_IMG_row5_obs3), findViewById(R.id.CarAvoid_IMG_row5_obs4), findViewById(R.id.CarAvoid_IMG_row5_obs5)},
                {findViewById(R.id.CarAvoid_IMG_car_lane_1), findViewById(R.id.CarAvoid_IMG_car_lane_2), findViewById(R.id.CarAvoid_IMG_car_lane_3), findViewById(R.id.CarAvoid_IMG_car_lane_4), findViewById(R.id.CarAvoid_IMG_car_lane_5)}
        };

        coinMatrix = new AppCompatImageView[][] {
                {findViewById(R.id.CarAvoid_IMG_row1_bonus1), findViewById(R.id.CarAvoid_IMG_row1_bonus2), findViewById(R.id.CarAvoid_IMG_row1_bonus3), findViewById(R.id.CarAvoid_IMG_row1_bonus4), findViewById(R.id.CarAvoid_IMG_row1_bonus5)},
                {findViewById(R.id.CarAvoid_IMG_row2_bonus1), findViewById(R.id.CarAvoid_IMG_row2_bonus2), findViewById(R.id.CarAvoid_IMG_row2_bonus3), findViewById(R.id.CarAvoid_IMG_row2_bonus4), findViewById(R.id.CarAvoid_IMG_row2_bonus5)},
                {findViewById(R.id.CarAvoid_IMG_row3_bonus1), findViewById(R.id.CarAvoid_IMG_row3_bonus2), findViewById(R.id.CarAvoid_IMG_row3_bonus3), findViewById(R.id.CarAvoid_IMG_row3_bonus4), findViewById(R.id.CarAvoid_IMG_row3_bonus5)},
                {findViewById(R.id.CarAvoid_IMG_row4_bonus1), findViewById(R.id.CarAvoid_IMG_row4_bonus2), findViewById(R.id.CarAvoid_IMG_row4_bonus3), findViewById(R.id.CarAvoid_IMG_row4_bonus4), findViewById(R.id.CarAvoid_IMG_row4_bonus5)},
                {findViewById(R.id.CarAvoid_IMG_row5_bonus1), findViewById(R.id.CarAvoid_IMG_row5_bonus2), findViewById(R.id.CarAvoid_IMG_row5_bonus3), findViewById(R.id.CarAvoid_IMG_row5_bonus4), findViewById(R.id.CarAvoid_IMG_row5_bonus5)},
                {findViewById(R.id.CarAvoid_IMG_car_lane_1), findViewById(R.id.CarAvoid_IMG_car_lane_2), findViewById(R.id.CarAvoid_IMG_car_lane_3), findViewById(R.id.CarAvoid_IMG_car_lane_4), findViewById(R.id.CarAvoid_IMG_car_lane_5)}
        };

        findViewById(R.id.CarAvoid_IMG_explode).setVisibility(View.INVISIBLE);
        leftArrow = findViewById(R.id.carAvoid_BTN_left);
        rightArrow = findViewById(R.id.carAvoid_BTN_right);
    }

    public void startGame() {
        updateLivesUI();
        updateScoreUI(0);
        updateCarsUI();

        ImageView carLane3 = findViewById(R.id.CarAvoid_IMG_car_lane_3);
        if (carLane3 != null) {
            carLane3.setVisibility(View.VISIBLE);
        }

        startObstaclesTimerOnce();

    }

    private void updateCarsUI() {
        for(int i = 0; i < carLanes.length; i ++){
            carLanes[i].setVisibility(View.INVISIBLE);
        }
        carLanes[2].setVisibility(View.VISIBLE);
    }

    private void setupArrowButtons() {
        findViewById(R.id.carAvoid_BTN_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCarLeft();
                startObstaclesTimerOnce();
            }
        });

        findViewById(R.id.carAvoid_BTN_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCarRight();
                startObstaclesTimerOnce();
            }
        });
    }

    private void startObstaclesTimerOnce() {
        if (!isTimerStarted) {
            isTimerStarted = true;
            startObstaclesTimer();
        }
    }


    private void startObstaclesTimer() {
        obstacleMoveHandler = new Handler();
        newObstacleHandler = new Handler();

        obstacleMoveRunnable = new Runnable() {
            @Override
            public void run() {
                moveObstaclesDown();
                checkCollision();
                updateScoreUI(gm.increaseScore());
                obstacleMoveHandler.postDelayed(this, obstacleDownDELAY); // Repeat every second
            }
        };


        newObstacleRunnable = new Runnable() {
            @Override
            public void run() {
                addNewObstacle();
                newObstacleHandler.postDelayed(this, newObstacleDELAY); // Repeat every two seconds
            }
        };


        obstacleMoveHandler.postDelayed(obstacleMoveRunnable, obstacleDownDELAY); // Start after 1 second
        newObstacleHandler.postDelayed(newObstacleRunnable, newObstacleDELAY);
    }
    private void updateScoreUI(int score){
        TextView scoreTextView = findViewById(R.id.CarAvoid_LBL_score);
        if (scoreTextView != null) {
            scoreTextView.setText(String.format("%03d", score));
        }
    }

    private void checkCollision() {
        ImageView carImageView = carLanes[gm.getCurrentCarLane()];

        // Get the obstacleImageView from the row corresponding to the current car lane
        ImageView obstacleImageView = obstacleMatrix[obstacleMatrix.length - 2][gm.getCurrentCarLane()];
        findViewById(R.id.CarAvoid_IMG_explode).setVisibility(View.INVISIBLE);
        if (carImageView != null && obstacleImageView != null &&
                carImageView.getVisibility() == View.VISIBLE &&
                obstacleImageView.getVisibility() == View.VISIBLE ) {
            handleCollision();
        }
    }




    private void handleCollision() {
        gm.decreaseLive();
        Toast.makeText(this, "Collision!", Toast.LENGTH_SHORT).show();
        findViewById(R.id.CarAvoid_IMG_explode).setVisibility(View.VISIBLE);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(effect);
            }
        }
        if (gm.getLives() == 0) {
            endGame();
        } else {
            updateLivesUI();
        }
    }

    private void updateLivesUI() {
        int SZ = CarAvoid_IMG_Heart.length;

        for (int i = 0; i < SZ; i++) {
            CarAvoid_IMG_Heart[i].setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < SZ - gm.getLives(); i++) {
            CarAvoid_IMG_Heart[SZ - i - 1].setVisibility(View.INVISIBLE);
        }
    }

    private void endGame() {
        gm.setLives(gm.getinitialLives());
        gm.setScore(0);
        gm.setCurrentCarLane(2);
        startGame();
    }

    private void moveCarLeft() {
        if (gm.getCurrentCarLane() > 0) {
            setCarLaneVisibility(gm.getCurrentCarLane(), View.INVISIBLE);
            gm.decreaseLane(gm.getCurrentCarLane());
            setCarLaneVisibility(gm.getCurrentCarLane(), View.VISIBLE);
        }
    }

    private void moveCarRight() {
        if (gm.getCurrentCarLane() < carLanes.length - 1) {
            setCarLaneVisibility(gm.getCurrentCarLane(), View.INVISIBLE);
            gm.increaseLane(gm.getCurrentCarLane());
            setCarLaneVisibility(gm.getCurrentCarLane(), View.VISIBLE);
        }
    }

    private void setCarLaneVisibility(int laneIndex, int visibility) {
        ImageView carLane = carLanes[laneIndex];
        if (carLane != null) {
            carLane.setVisibility(visibility);
        }
    }

    private void moveObstaclesDown() {
        // Move existing obstacles down
        for (int row = obstacleMatrix.length - 2; row > 0; row--) {
            for (int col = 0; col < obstacleMatrix[row].length; col++) {
                ImageView currentObstacle = obstacleMatrix[row][col];
                ImageView aboveObstacle = obstacleMatrix[row - 1][col];
                if (currentObstacle != null && aboveObstacle != null) {
                    currentObstacle.setVisibility(aboveObstacle.getVisibility());
                    aboveObstacle.setVisibility(View.INVISIBLE);
                }
            }
        }

        // Clear top row
        for (int col = 0; col < obstacleMatrix[0].length; col++) {
            ImageView imageView = obstacleMatrix[0][col];
            if (imageView != null) {
                imageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void addNewObstacle() {
        // Add new obstacles in the top row randomly
        Random random = new Random();
        int newObstacleColumn = random.nextInt(obstacleMatrix[0].length);
        ImageView newObstacle = obstacleMatrix[0][newObstacleColumn];
        if (newObstacle != null) {
            newObstacle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (obstacleMoveHandler != null) {
            obstacleMoveHandler.removeCallbacks(obstacleMoveRunnable);
        }
        if (newObstacleHandler != null) {
            newObstacleHandler.removeCallbacks(newObstacleRunnable);
        }
    }
}