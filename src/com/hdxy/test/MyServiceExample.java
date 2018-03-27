package com.hdxy.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyServiceExample extends Service {

	@Override
	public void onCreate() {
		Log.d("MyServiceExample", "onCreate()");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyServiceExample",
				"onStartCommand(Intent intent, int flags, int startId)");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		
		Log.d("MyServiceExample", "onBind(Intent arg0)");

		return new MyBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("MyServiceExample", "onUnbind(Intent intent)");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.d("MyServiceExample", "onDestroy()");
		super.onDestroy();
	}

}
