
package com.example.caravoidancegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.caravoidencegame.R;

public class Activity_Menu extends AppCompatActivity {

    private EditText playerNameEditText;
    private Switch fastModeSwitch;
    private Switch sensorModeSwitch;
    private Button startGameButton;
    private Button scoreboardButton;
    private AppCompatImageView carGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();


        startGameButton.setOnClickListener(view -> {
            String playerName = playerNameEditText.getText().toString();
            boolean isFastMode = fastModeSwitch.isChecked();
            boolean isSensorMode = sensorModeSwitch.isChecked();

            Intent intent = new Intent(Activity_Menu.this, GameManager.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("isFastMode", isFastMode);
            intent.putExtra("isSensorMode", isSensorMode);
            startActivity(intent);
        });

        scoreboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Menu.this, Activity_Score.class);
            startActivity(intent);
        });
    }

    private void findViews() {
        playerNameEditText = findViewById(R.id.playerName);
        fastModeSwitch = findViewById(R.id.fastModeSwitch);
        sensorModeSwitch = findViewById(R.id.sensorModeSwitch);
        startGameButton = findViewById(R.id.startGameButton);
        scoreboardButton = findViewById(R.id.scoreboardButton);
        carGif = findViewById(R.id.car_gif);
    }
}
