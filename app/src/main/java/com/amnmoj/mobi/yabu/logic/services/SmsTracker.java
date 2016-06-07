package com.amnmoj.mobi.yabu.logic.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SmsTracker extends Service {

	private SmsListener mSmsListener;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		doJob();
		// TODO do something useful
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void doJob() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mSmsListener = SmsListener.getmSmsListener(getApplicationContext());
			}
		}).start();
	}
}
