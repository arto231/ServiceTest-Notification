package com.hdxy.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	/***
	 * 服务的启动与绑定 发送通知
	 * 
	 * @author shadow
	 * **/
	private MyBinder myBinder = null;
	private TextView tv_result;
	private Button btn_startService, btn_stopService, btn_bindService,
			btn_unbindService, btn_connection, btn_sendNoti,btn_stopNoti;
	private Intent intent;
	private static final String service_action = "START_SERVICE_MYSERVICEEXAMPLE";
	private Notification.Builder builder;
	private NotificationManager nm;
	private Notification noti;
	private RemoteViews remoteView;
	public boolean isThreadContinue=true;

	private int[] charTimeNumbers = new int[6];
	

	private int[] timeImages = {// 图片数组
	R.drawable.su01,// 0
			R.drawable.su02,// 1
			R.drawable.su03,// 2
			R.drawable.su04,// 3
			R.drawable.su05,// 4
			R.drawable.su06,// 5
			R.drawable.su07,// 6
			R.drawable.su08,// 7
			R.drawable.su09,// 8
			R.drawable.su10 // 9
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intent = new Intent(service_action);

		btn_startService = (Button) findViewById(R.id.btn_startService);
		btn_stopService = (Button) findViewById(R.id.btn_StopService);
		btn_bindService = (Button) findViewById(R.id.btn_BindService);
		btn_unbindService = (Button) findViewById(R.id.btn_UnbindService);
		btn_connection = (Button) findViewById(R.id.btn_ConnectionService);
		btn_sendNoti = (Button) findViewById(R.id.btn_SendNotification);
		btn_stopNoti=(Button) findViewById(R.id.btn_Stop_Notification_update);

		tv_result = (TextView) findViewById(R.id.TextView_showConnectionResult);

		btn_startService.setOnClickListener(this);
		btn_stopService.setOnClickListener(this);
		btn_bindService.setOnClickListener(this);
		btn_unbindService.setOnClickListener(this);
		btn_connection.setOnClickListener(this);
		btn_sendNoti.setOnClickListener(this);
		btn_stopNoti.setOnClickListener(this);

	}

	ServiceConnection con = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {

			myBinder = (MyBinder) arg1;

		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_startService:
			startService(intent);
			break;
		case R.id.btn_StopService:
			stopService(intent);
			break;
		case R.id.btn_BindService:
			bindService(intent, con, Context.BIND_AUTO_CREATE);
			break;
		case R.id.btn_UnbindService:

			try {
				unbindService(con);
			} catch (Exception e) {
				Toast.makeText(this, "先绑定服务才能解绑", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_ConnectionService:
			try {
				tv_result.setText(myBinder.getResult());
			} catch (Exception e) {
				Toast.makeText(this, "先绑定服务才能修改主界面控件", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btn_SendNotification:
			isThreadContinue=true;
			builder = new Notification.Builder(this);
			PendingIntent pi = PendingIntent.getService(this, 0, intent,
					PendingIntent.FLAG_CANCEL_CURRENT);

			builder.setTicker("A ticker");
			builder.setContentIntent(pi);
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setAutoCancel(true);
			builder.setContentTitle("悬浮视图的通知");
			builder.setContentText("in progress");
			nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			noti = builder.build();
			new Thread(new Runnable() {

				@Override
				public void run() {
					Log.d("Thread", "thread running");

					while (isThreadContinue) {

						remoteView = new RemoteViews(getPackageName(),
								R.xml.my_notificationview);
						SimpleDateFormat formater = new SimpleDateFormat(
								"hhmmss");
						Date time = new Date();
						String correcttime = formater.format(time);
						for (int i = 0; i < 6; i++) {
							charTimeNumbers[i] = correcttime.charAt(i);
						}

						remoteView.setImageViewResource(R.id.time_h1,
								timeImages[charTimeNumbers[0] - 48]);
						remoteView.setImageViewResource(R.id.time_h0,
								timeImages[charTimeNumbers[1] - 48]);
						remoteView.setImageViewResource(R.id.time_m1,
								timeImages[charTimeNumbers[2] - 48]);
						remoteView.setImageViewResource(R.id.time_m0,
								timeImages[charTimeNumbers[3] - 48]);
						remoteView.setImageViewResource(R.id.time_s1,
								timeImages[charTimeNumbers[4] - 48]);
						remoteView.setImageViewResource(R.id.time_s0,
								timeImages[charTimeNumbers[5] - 48]);
						noti.bigContentView = remoteView;
						nm.notify(0, noti);
						Log.d("Thread", "Time Set IS"+correcttime);
						try {
							Thread.sleep(1000);

						} catch (Exception e) {
							Log.e("Thread", "thread seelp flure");
						}
					}
				}
			}).start();

			break;
		case R.id.btn_Stop_Notification_update:
			isThreadContinue=false;
			break;
		default:
			break;
		}

	}
}
