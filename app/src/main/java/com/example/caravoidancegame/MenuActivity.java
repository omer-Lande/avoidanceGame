package com.example.caravoidancegame;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import im.delight.android.location.SimpleLocation;
import com.example.caravoidancegame.R;


public class MenuActivity extends AppCompatActivity {

    private EditText playerNameEditText;
    private Switch fastModeSwitch;
    private Switch sensorModeSwitch;
    private Button startGameButton;
    private Button scoreboardButton;
    private AppCompatImageView carStart;
    private double latitude = 0.0;
    private double longitude= 0.0;
    private SimpleLocation location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.location = new SimpleLocation(this);
        requestLocationPermission(location);
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

                Intent intent = new Intent(MenuActivity.this, CarAvoidActivity.class);
                intent.putExtra(CarAvoidActivity.KEY_NAME,playerNameEditText.getText().toString());
                intent.putExtra(CarAvoidActivity.KEY_SPEED, isFastMode);
                intent.putExtra(CarAvoidActivity.KEY_MODE, isSensorMode);
                intent.putExtra(CarAvoidActivity.KEY_LATITUDE,latitude);
                intent.putExtra(CarAvoidActivity.KEY_LONGITUDE,longitude);
                Log.d(TAG, "Player Name: " + playerName);
                Log.d(TAG, "Fast Mode: " + isFastMode);
                Log.d(TAG, "Sensor Mode: " + isSensorMode);
                Log.d(TAG, "Latitude: " + latitude);
                Log.d(TAG, "Longitude: " + longitude);
                startActivity(intent);
                finish();
            }
        });

        scoreboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, ScoreboardActivity.class);
            startActivity(intent);
            finish();
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

    private void requestLocationPermission(SimpleLocation location) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);

        }
        putLatLon(location);
    }

    private void putLatLon(SimpleLocation location){
        location.beginUpdates();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
