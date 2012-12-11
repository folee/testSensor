package com.emerson.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * ¼ì²âÊÖ»úÒ¡»ÎµÄ¼àÌýÆ÷
 */
public class ShakeListener implements SensorEventListener {
	private String				TAG						= "ShakeListener";
	private static final int	SPEED_SHRESHOLD			= 50000;
	private static final int	UPTATE_INTERVAL_TIME	= 3;
	private SensorManager		sensorManager;
	private Sensor				sensor;
	private OnShakeListener		onShakeListener;
	private Context				mContext;
	private float				lastX;
	private float				lastY;
	private float				lastZ;
	private long				lastUpdateTime;

	public ShakeListener(Context c) {
		mContext = c;
		start();
	}

	public void start() {
		sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if (sensor != null) {
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		}
		lastUpdateTime = System.currentTimeMillis()/1000;
	}

	public void stop() {
		sensorManager.unregisterListener(this);
	}

	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	public void onSensorChanged(SensorEvent event) {
		long currentUpdateTime = System.currentTimeMillis()/1000;
		long timeInterval = currentUpdateTime - lastUpdateTime;
		if (timeInterval < UPTATE_INTERVAL_TIME){
//			Log.i(TAG, "timeInterval = " + timeInterval);
			return;
		}

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;

		lastX = x;
		lastY = y;
		lastZ = z;

		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000;
		if (speed >= SPEED_SHRESHOLD) {
			Log.v(TAG, "speed = " + speed);
			Log.i(TAG, "timeInterval = " + timeInterval);
			onShakeListener.onShake();
			lastUpdateTime = currentUpdateTime;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public interface OnShakeListener {
		public void onShake();
	}

}