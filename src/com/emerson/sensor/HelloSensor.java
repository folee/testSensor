package com.emerson.sensor;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.emerson.sensor.ShakeListener.OnShakeListener;

/**
 * 1. ACCELEROMETER 加速，描述加速度的。 　　2.GRAVITY 重力，这个在大家都知道。 　　3.GYROSCOPE
 * 陀螺仪，对于物体跌落检测更强大些，开发游戏少了它会有点遗憾的，API Level 9新增的类型。 　　4. LIGHT
 * 光线感应器，很多Android手机的屏幕亮度是根据这个感应器的数组自动调节的。 　　5. LINEAR_ACCELERATION 线性加速器，API
 * Level 9新增的。 　　6. MAGNETIC_FIELD 磁极感应器。 　　7. ORIENTATION 方向感应器。 　　8. PRESSURE
 * 压力感应器。 　　9. PROXIMITY 距离感应器，对于通话后关闭屏幕背光很有用。 　　10. ROTATION_VECTOR
 * 旋转向量，Android 2.3新增的，如果我们过去处理图像会发现这个还是很有用的，不过这里还是对游戏开发起到辅助。 　　11. TEMPERATURE
 * 温度感应器，可以获取手机的内部温度，不过和周边的有些差距，毕竟手机内部一般温度比较高。
 * 
 * @author wellemerson
 * 
 */
public class HelloSensor extends Activity {
	Sensor			sensor;
	private float	x, y, z;
	ShakeListener	mShakeListener	= null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_sensor);

		// 准备显示信息的UI组建
		final TextView tx1 = (TextView) findViewById(R.id.TextView01);

		// 从系统服务中获得传感器管理器
		SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// 从传感器管理器中获得全部的传感器列表
		List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);

		// 显示有多少个传感器
		tx1.setText("经检测该手机有" + allSensors.size() + "个传感器，他们分别是：\n");

		// 显示每个传感器的具体信息
		for (Sensor s : allSensors) {
			String tempString = "\n" + "  设备名称：" + s.getName() + "\n" + "  设备版本：" + s.getVersion() + "\n" + "  供应商："
					+ s.getVendor() + "\n";
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				tx1.setText(tx1.getText().toString() + s.getType() + " 加速度传感器accelerometer" + tempString);
				break;
			// case Sensor.TYPE_GRAVITY:
			// tx1.setText(tx1.getText().toString() + s.getType() +
			// " 重力传感器gravity API 9" + tempString);
			// break;
			case Sensor.TYPE_GYROSCOPE:
				tx1.setText(tx1.getText().toString() + s.getType() + " 陀螺仪传感器gyroscope" + tempString);
				break;
			case Sensor.TYPE_LIGHT:
				tx1.setText(tx1.getText().toString() + s.getType() + " 环境光线传感器light" + tempString);
				break;
			// case Sensor.TYPE_LINEAR_ACCELERATION:
			// tx1.setText(tx1.getText().toString() + s.getType() +
			// " 线性加速器LINEAR_ACCELERATION API9" + tempString);
			// break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				tx1.setText(tx1.getText().toString() + s.getType() + " 电磁场传感器magnetic field" + tempString);
				break;
			case Sensor.TYPE_ORIENTATION:
				tx1.setText(tx1.getText().toString() + s.getType() + " 方向传感器orientation" + tempString);
				break;
			case Sensor.TYPE_PRESSURE:
				tx1.setText(tx1.getText().toString() + s.getType() + " 压力传感器pressure" + tempString);
				break;
			case Sensor.TYPE_PROXIMITY:
				tx1.setText(tx1.getText().toString() + s.getType() + " 距离传感器proximity" + tempString);
				break;
			// case Sensor.TYPE_ROTATION_VECTOR:
			// tx1.setText(tx1.getText().toString() + s.getType() +
			// " 旋转向量ROTATION" + tempString);
			// break;
			case Sensor.TYPE_TEMPERATURE:
				tx1.setText(tx1.getText().toString() + s.getType() + " 温度传感器temperature" + tempString);
				break;
			default:
				tx1.setText(tx1.getText().toString() + s.getType() + " 未知传感器" + tempString);
				break;
			}
		}
		SensorEventListener lsn = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent e) {
				x = e.values[SensorManager.DATA_X];
				y = e.values[SensorManager.DATA_Y];
				z = e.values[SensorManager.DATA_Z];
				setTitle("x=" + (int) x + "," + "y=" + (int) y + "," + "z=" + (int) z);
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};
		// 在title上显示重力传感器的变化
		sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 注册listener，第三个参数是检测的精确度
		sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);

		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				Log.e("ShakeListener", "摇一摇啊摇一摇");
				Toast.makeText(HelloSensor.this, "摇一摇啊摇一摇", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
