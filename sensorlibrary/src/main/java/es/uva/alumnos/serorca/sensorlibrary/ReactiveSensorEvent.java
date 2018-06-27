package es.uva.alumnos.serorca.sensorlibrary;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

public final class ReactiveSensorEvent {
    private SensorEvent sensorEvent;
    private Sensor sensor;
    private int accuracy = -1;

    public ReactiveSensorEvent(SensorEvent sensorEvent) {
        this.sensorEvent = sensorEvent;
    }

    public ReactiveSensorEvent(Sensor sensor, int accuracy) {
        this.sensor = sensor;
        this.accuracy = accuracy;
    }

    public SensorEvent getSensorEvent() {
        return sensorEvent;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public boolean isSensorChanged() {
        return sensorEvent != null;
    }

    public boolean isAccuracyChanged() {
        return sensor != null && accuracy != -1;
    }
}