package com.example.caravoidancegame.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorDetector{

    private SensorManager sensorManager;
    private Sensor sensor;
    long timeStamp = 0;
    private final int responseTime = 300;

    public interface CallBack_CarView {
        void moveCarWithSensor(int index);
        void changeSpeedWithSensor(int speed);
    }
    private CallBack_CarView CallBack_CarView;
    public SensorDetector(Context context,CallBack_CarView CallBack_CarView) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.CallBack_CarView = CallBack_CarView;
    }

    private SensorEventListener sensorEventListenerX = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            calculateStepX(x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateStepX(float x) {

        if (x > 3.0) {//left
            if (System.currentTimeMillis() - timeStamp > responseTime) {
                timeStamp = System.currentTimeMillis();
                CallBack_CarView.moveCarWithSensor(-1);
            }
        }
        if (x < -3.0) {//right
            if (System.currentTimeMillis() - timeStamp > responseTime) {
                timeStamp = System.currentTimeMillis();
                CallBack_CarView.moveCarWithSensor(1);
            }
        }
    }

    public void startX() {
        sensorManager.registerListener(sensorEventListenerX, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopX() {
        sensorManager.unregisterListener(sensorEventListenerX);
    }

}
