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
 * 1. ACCELEROMETER ���٣��������ٶȵġ� ����2.GRAVITY ����������ڴ�Ҷ�֪���� ����3.GYROSCOPE
 * �����ǣ���������������ǿ��Щ��������Ϸ���������е��ź��ģ�API Level 9���������͡� ����4. LIGHT
 * ���߸�Ӧ�����ܶ�Android�ֻ�����Ļ�����Ǹ��������Ӧ���������Զ����ڵġ� ����5. LINEAR_ACCELERATION ���Լ�������API
 * Level 9�����ġ� ����6. MAGNETIC_FIELD �ż���Ӧ���� ����7. ORIENTATION �����Ӧ���� ����8. PRESSURE
 * ѹ����Ӧ���� ����9. PROXIMITY �����Ӧ��������ͨ����ر���Ļ��������á� ����10. ROTATION_VECTOR
 * ��ת������Android 2.3�����ģ�������ǹ�ȥ����ͼ��ᷢ��������Ǻ����õģ��������ﻹ�Ƕ���Ϸ�����𵽸����� ����11. TEMPERATURE
 * �¶ȸ�Ӧ�������Ի�ȡ�ֻ����ڲ��¶ȣ��������ܱߵ���Щ��࣬�Ͼ��ֻ��ڲ�һ���¶ȱȽϸߡ�
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

		// ׼����ʾ��Ϣ��UI�齨
		final TextView tx1 = (TextView) findViewById(R.id.TextView01);

		// ��ϵͳ�����л�ô�����������
		SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// �Ӵ������������л��ȫ���Ĵ������б�
		List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);

		// ��ʾ�ж��ٸ�������
		tx1.setText("�������ֻ���" + allSensors.size() + "�������������Ƿֱ��ǣ�\n");

		// ��ʾÿ���������ľ�����Ϣ
		for (Sensor s : allSensors) {
			String tempString = "\n" + "  �豸���ƣ�" + s.getName() + "\n" + "  �豸�汾��" + s.getVersion() + "\n" + "  ��Ӧ�̣�"
					+ s.getVendor() + "\n";
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				tx1.setText(tx1.getText().toString() + s.getType() + " ���ٶȴ�����accelerometer" + tempString);
				break;
			// case Sensor.TYPE_GRAVITY:
			// tx1.setText(tx1.getText().toString() + s.getType() +
			// " ����������gravity API 9" + tempString);
			// break;
			case Sensor.TYPE_GYROSCOPE:
				tx1.setText(tx1.getText().toString() + s.getType() + " �����Ǵ�����gyroscope" + tempString);
				break;
			case Sensor.TYPE_LIGHT:
				tx1.setText(tx1.getText().toString() + s.getType() + " �������ߴ�����light" + tempString);
				break;
			// case Sensor.TYPE_LINEAR_ACCELERATION:
			// tx1.setText(tx1.getText().toString() + s.getType() +
			// " ���Լ�����LINEAR_ACCELERATION API9" + tempString);
			// break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				tx1.setText(tx1.getText().toString() + s.getType() + " ��ų�������magnetic field" + tempString);
				break;
			case Sensor.TYPE_ORIENTATION:
				tx1.setText(tx1.getText().toString() + s.getType() + " ���򴫸���orientation" + tempString);
				break;
			case Sensor.TYPE_PRESSURE:
				tx1.setText(tx1.getText().toString() + s.getType() + " ѹ��������pressure" + tempString);
				break;
			case Sensor.TYPE_PROXIMITY:
				tx1.setText(tx1.getText().toString() + s.getType() + " ���봫����proximity" + tempString);
				break;
			// case Sensor.TYPE_ROTATION_VECTOR:
			// tx1.setText(tx1.getText().toString() + s.getType() +
			// " ��ת����ROTATION" + tempString);
			// break;
			case Sensor.TYPE_TEMPERATURE:
				tx1.setText(tx1.getText().toString() + s.getType() + " �¶ȴ�����temperature" + tempString);
				break;
			default:
				tx1.setText(tx1.getText().toString() + s.getType() + " δ֪������" + tempString);
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
		// ��title����ʾ�����������ı仯
		sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// ע��listener�������������Ǽ��ľ�ȷ��
		sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);

		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				Log.e("ShakeListener", "ҡһҡ��ҡһҡ");
				Toast.makeText(HelloSensor.this, "ҡһҡ��ҡһҡ", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
