package com.amnmoj.mobi.yabu.logic.receivers;

import com.amnmoj.mobi.yabu.logic.services.SmsTracker;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AllReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Log.d("FFF", "Receiver");
		context.startService(new Intent(context, SmsTracker.class));

	}

}
