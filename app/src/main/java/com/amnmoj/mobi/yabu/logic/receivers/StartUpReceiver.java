package com.amnmoj.mobi.yabu.logic.receivers;

import com.amnmoj.mobi.yabu.logic.protocol.ProtocolManager;
import com.amnmoj.mobi.yabu.logic.services.SmsTracker;
import com.amnmoj.mobi.yabu.tools.Pref;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class StartUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		context.startService(new Intent(context, SmsTracker.class));
		if (Pref.getInstance(context).isEnable() && Pref.getInstance(context).isCheckSimChangedOn()) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					ProtocolManager.getInstance(context).onBootStartUpReceive();
				}
			}).start();
		}

	}

}
