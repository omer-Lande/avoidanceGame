package com.example.caravoidancegame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorDetector implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorDetectorCallback callback;
    private static final float TILT_THRESHOLD = 5.0f; // Adjust this value as needed

    public SensorDetector(Context context, SensorDetectorCallback callback) {
        this.callback = callback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public void start() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            if (x > TILT_THRESHOLD) {
                callback.onTiltRight();
            } else if (x < -TILT_THRESHOLD) {
                callback.onTiltLeft();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No implementation needed
    }

    public interface SensorDetectorCallback {
        void onTiltLeft();
        void onTiltRight();
    }
}
