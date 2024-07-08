package com.example.caravoidancegame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.caravoidencegame.R;

public class Activity_Menu extends AppCompatActivity {

    private EditText playerNameEditText;
    private Switch fastModeSwitch;
    private Switch sensorModeSwitch;
    private Button startGameButton;
    private Button scoreboardButton;
    private AppCompatImageView carStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();

        // Set up listeners for the switches
        fastModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fastModeSwitch.setTextColor(Color.GREEN);
            } else {
                fastModeSwitch.setTextColor(Color.GRAY);
            }
        });

        sensorModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sensorModeSwitch.setTextColor(Color.GREEN);
            } else {
                sensorModeSwitch.setTextColor(Color.GRAY);
            }
        });

        startGameButton.setOnClickListener(view -> {
            String playerName = playerNameEditText.getText().toString();
            if(playerName.trim().isEmpty()){
                Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
            }
            else{
                boolean isFastMode = fastModeSwitch.isChecked();
                boolean isSensorMode = sensorModeSwitch.isChecked();

                Intent intent = new Intent(Activity_Menu.this, Activity_CarAvoid.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("isFastMode", isFastMode);
                intent.putExtra("isSensorMode", isSensorMode);
                startActivity(intent);
            }
        });

        scoreboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Menu.this, Activity_Score.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void findViews() {
        playerNameEditText = findViewById(R.id.playerName);
        fastModeSwitch = findViewById(R.id.fastModeSwitch);
        sensorModeSwitch = findViewById(R.id.sensorModeSwitch);
        startGameButton = findViewById(R.id.startGameButton);
        scoreboardButton = findViewById(R.id.scoreboardButton);
        carStart = findViewById(R.id.carStart);
        carStart.setVisibility(View.VISIBLE);
    }
}
