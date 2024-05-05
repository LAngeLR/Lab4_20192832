package com.example.laboratorio4.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;

public class AcelerometerEventListener implements SensorEventListener {
    private SensorListenerCallback callback;

    public AcelerometerEventListener(SensorListenerCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float accelerationTotal = (float) Math.sqrt(x * x + y * y + z * z);

            if (callback != null) {
                callback.onAccelerationChanged(accelerationTotal);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public interface SensorListenerCallback {
        void onAccelerationChanged(float accelerationTotal);
    }
}
