package com.example.laboratorio4.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MagnetometerEventListener implements SensorEventListener {
    //Igual que el caso anterior. Codigo reutilizado
    private SensorListenerCallback callback;

    public MagnetometerEventListener(SensorListenerCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            if (callback != null) {
                callback.onMagneticFieldChanged(x, y, z);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface SensorListenerCallback {
        void onMagneticFieldChanged(float x, float y, float z);
    }
}
