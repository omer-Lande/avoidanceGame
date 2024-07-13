package com.example.caravoidancegame;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.caravoidancegame.Sensors.SensorDetector;
import com.example.caravoidancegame.Util.Sounds;
import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class CarAvoidActivity extends AppCompatActivity{

    private boolean isTimerStarted = false;

    public static final String KEY_SPEED = "KEY_SPEED";
    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_LONGITUDE="KEY_LONGITUDE";
    public static final String KEY_LATITUDE="KEY_LATITUDE";


    private Handler handler= new Handler();
    private Runnable obstacleDownRunnable;
    private Random random = new Random();

    private AppCompatImageView[] CarAvoid_IMG_Heart;

    private AppCompatImageView[] carLanes;
    private AppCompatImageView [][]cells;
    private GameManager gm;
    private MaterialButton leftArrow;
    private MaterialButton rightArrow;
    private final int carRows = 5;
    private String playerName = "";
    private int newObstacleDELAY = 2000;
    private int obstacleDownDELAY = 1000;
    private SensorDetector sensorDetector;
    private Sounds sounds;
    private double latitude = 0.0;
    private double longitude = 0.0;
    public boolean isSensors = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sounds = new Sounds(this);
        getValuesPreviousIntent();
        gm = new GameManager(3,playerName);
        sensorDetector = new SensorDetector(this,callBack_move);

        findViews();
        initGame();
        startGame();
    }

    private SensorDetector.CallBack_CarView callBack_move = new SensorDetector.CallBack_CarView() {
        @Override
        public void moveCarWithSensor(int i) {
            gm.getCells()[gm.getCurrentRow()][gm.getCurrentCarLane()].setVisibility(View.INVISIBLE);
            gm.moveCarSensors(i);
            gm.getCells()[gm.getCurrentRow()][gm.getCurrentCarLane()].setVisibility(View.VISIBLE);
        }

        @Override
        public void changeSpeedWithSensor(int obsSpeed) {
            obstacleDownDELAY = obsSpeed;
        }
    };

        private void initGame() {
        Intent previousIntent = getIntent();
            boolean isFastMode = previousIntent.getExtras().getBoolean(KEY_SPEED);
            isSensors= previousIntent.getExtras().getBoolean(KEY_MODE);
            playerName = previousIntent.getExtras().getString(KEY_NAME);
            latitude = previousIntent.getExtras().getDouble(KEY_LATITUDE,0);
            longitude = previousIntent.getExtras().getDouble(KEY_LONGITUDE,0);

        if (isFastMode) {
            newObstacleDELAY = 1200; // Adjust the delay for fast mode
        }

        if (isSensors) {
            leftArrow.setVisibility(View.INVISIBLE);
            rightArrow.setVisibility(View.INVISIBLE);
            sensorDetector.startX();
        } else {
            leftArrow.setOnClickListener(view -> gm.moveCarLeft());
            rightArrow.setOnClickListener(view -> gm.moveCarRight());
        }
    }

    private void getValuesPreviousIntent(){
        Intent previousIntent = getIntent();
        playerName = previousIntent.getExtras().getString(KEY_NAME);
        latitude = previousIntent.getExtras().getDouble(KEY_LATITUDE,0);
        longitude = previousIntent.getExtras().getDouble(KEY_LONGITUDE,0);
    }

    private void findViews() {
        CarAvoid_IMG_Heart = new AppCompatImageView[]{
                findViewById(R.id.CarAvoid_IMG_heart1),
                findViewById(R.id.CarAvoid_IMG_heart2),
                findViewById(R.id.CarAvoid_IMG_heart3),
        };
        carLanes = new AppCompatImageView[]{
                findViewById(R.id.CarAvoid_IMG_car_lane_1),
                findViewById(R.id.CarAvoid_IMG_car_lane_2),
                findViewById(R.id.CarAvoid_IMG_car_lane_3),
                findViewById(R.id.CarAvoid_IMG_car_lane_4),
                findViewById(R.id.CarAvoid_IMG_car_lane_5)
        };
        cells = new AppCompatImageView[][]{
                {findViewById(R.id.CarAvoid_IMG_row1_obs1), findViewById(R.id.CarAvoid_IMG_row1_obs2), findViewById(R.id.CarAvoid_IMG_row1_obs3), findViewById(R.id.CarAvoid_IMG_row1_obs4), findViewById(R.id.CarAvoid_IMG_row1_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row2_obs1), findViewById(R.id.CarAvoid_IMG_row2_obs2), findViewById(R.id.CarAvoid_IMG_row2_obs3), findViewById(R.id.CarAvoid_IMG_row2_obs4), findViewById(R.id.CarAvoid_IMG_row2_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row3_obs1), findViewById(R.id.CarAvoid_IMG_row3_obs2), findViewById(R.id.CarAvoid_IMG_row3_obs3), findViewById(R.id.CarAvoid_IMG_row3_obs4), findViewById(R.id.CarAvoid_IMG_row3_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row4_obs1), findViewById(R.id.CarAvoid_IMG_row4_obs2), findViewById(R.id.CarAvoid_IMG_row4_obs3), findViewById(R.id.CarAvoid_IMG_row4_obs4), findViewById(R.id.CarAvoid_IMG_row4_obs5)},
                {findViewById(R.id.CarAvoid_IMG_row5_obs1), findViewById(R.id.CarAvoid_IMG_row5_obs2), findViewById(R.id.CarAvoid_IMG_row5_obs3), findViewById(R.id.CarAvoid_IMG_row5_obs4), findViewById(R.id.CarAvoid_IMG_row5_obs5)},
                {findViewById(R.id.CarAvoid_IMG_car_lane_1), findViewById(R.id.CarAvoid_IMG_car_lane_2), findViewById(R.id.CarAvoid_IMG_car_lane_3), findViewById(R.id.CarAvoid_IMG_car_lane_4), findViewById(R.id.CarAvoid_IMG_car_lane_5)}
        };

        findViewById(R.id.CarAvoid_IMG_explode).setVisibility(View.INVISIBLE);
        leftArrow = findViewById(R.id.carAvoid_BTN_left);
        rightArrow = findViewById(R.id.carAvoid_BTN_right);
        gm.setCells(cells);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm.moveCarLeft();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm.moveCarRight();
            }
        });
    }

    public void startGame() {
        updateLivesUI();
        updateScoreUI(0);

        ImageView carLane3 = findViewById(R.id.CarAvoid_IMG_car_lane_3);
        if (carLane3 != null) {
            carLane3.setVisibility(View.VISIBLE);
        }

       startObstaclesTimerOnce();
    }


    private void startObstaclesTimerOnce() {
        if (!isTimerStarted) {
            isTimerStarted = true;
            startObstaclesTimer();
        }
    }

    private void startObstaclesTimer() {
        obstacleDownRunnable = new Runnable() {
            @Override
            public void run() {
                int col = random.nextInt(5);
                boolean isTrafficCone = random.nextBoolean();
                for (int row = 0; row < carRows; row++) {
                    final int finalRow = row;
                    final int finalCol = col;
                    final boolean finalTrafficCone = isTrafficCone;


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalRow > 0) {
                                gm.getCells()[finalRow - 1][finalCol].setVisibility(View.INVISIBLE);
                            }
                            Log.d(TAG, "final row: " + finalRow);
                            Log.d(TAG, "final col: " + finalCol);
                            Log.d(TAG, "current row: " + gm.getCurrentRow());
                            Log.d(TAG, "current car lane: " + gm.getCurrentCarLane());
                            Log.d(TAG, "current player name: " + playerName);
                            gm.increaseScore();
                            updateScoreUI(gm.getScore());
                            gm.getCells()[finalRow][finalCol].setImageResource(finalTrafficCone ? R.drawable.img_traffic_cone_1 : R.drawable.img_coin); // Set the image resource
                            gm.getCells()[finalRow][finalCol].setVisibility(View.VISIBLE);
                            if ((finalRow+1) == gm.getCurrentRow() && finalCol == gm.getCurrentCarLane()) {
                                checkType(finalTrafficCone ? 0 : 1);

                            }
                        }
                    }, finalRow * 500);



                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gm.getCells()[finalRow][finalCol].setVisibility(View.INVISIBLE);
                        }
                    }, (finalRow + 1) * 500);
                }

                handler.postDelayed(this, obstacleDownDELAY);
            }
        };

        handler.post(obstacleDownRunnable);
    }

    private void handleCollision() {
        Toast.makeText(this, "Collision", Toast.LENGTH_SHORT).show();

        //Add vibration when collision
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (v != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                v.vibrate(effect);
            }
        }
        playCrashSound();
        gm.decreaseLive();
        updateLivesUI();

        if (gm.getLives() == 0) {
            Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show();
            handler.removeCallbacks(obstacleDownRunnable);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameOver();
                }
            }, 6000);//Wait 6 sec until game starts over
        }
    }

    private void gameOver() {
        saveScore();
        switchActivity();
    }

    private void switchActivity() {
        Intent intent = new Intent(CarAvoidActivity.this, ScoreboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveScore() {
        gm.save(longitude, latitude);
    }

    private void checkType(int type) {
        if(type == 0) {
            handleCollision();
        }
        else{
            handleScore();
        }
    }
    //Handle collision event when the plane collects coin.
    private void handleScore() {
        Toast.makeText(this, "very nice!", Toast.LENGTH_SHORT).show();
        gm.increaseScore();
        updateScoreUI(gm.getScore());
        playCoinSound();
    }

    private void playCrashSound() {
        sounds.playSound(R.raw.crash);
    }

    private void playCoinSound() {
        sounds.playSound(R.raw.coin_pickup);
    }




    private void updateScoreUI(int score) {
        TextView scoreTextView = findViewById(R.id.CarAvoid_LBL_score);
        scoreTextView.setText(String.valueOf(score));
    }

    private void updateLivesUI() {
        for (int i = 0; i < CarAvoid_IMG_Heart.length; i++) {
            if (gm.getLives() > i) {
                CarAvoid_IMG_Heart[i].setVisibility(View.VISIBLE);
            } else {
                CarAvoid_IMG_Heart[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void moveCarLeft() {
        int carPos = 0;
        for (int i = 0; i < carLanes.length; i++) {
            if (carLanes[i].getVisibility() == View.VISIBLE) {
                carPos = i;
                break;
            }
        }
        if (carPos > 0) {
            carLanes[carPos].setVisibility(View.INVISIBLE);
            carLanes[carPos - 1].setVisibility(View.VISIBLE);
        }
    }

    public void moveCarRight() {
        int carPos = 0;
        for (int i = 0; i < carLanes.length; i++) {
            if (carLanes[i].getVisibility() == View.VISIBLE) {
                carPos = i;
                break;
            }
        }
        if (carPos < carLanes.length - 1) {
            carLanes[carPos].setVisibility(View.INVISIBLE);
            carLanes[carPos + 1].setVisibility(View.VISIBLE);
        }
    }

//    private void vibrate() {
//        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        if (v != null) {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//            } else {
//                v.vibrate(500);
//            }
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorDetector != null) {
            sensorDetector.stopX();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorDetector != null) {
            sensorDetector.startX();
        }
        startObstaclesTimerOnce();
    }
}


//todo fix car disappear and coin disappear and obstacle disappear.
//todo fix check collision
//todo fix scoreboard
// TODO: fix sensors
// TODO: fix icon
// TODO: add map