package com.amnmoj.mobi.yabu.logic.receivers;

import com.amnmoj.mobi.yabu.logic.protocol.ProtocolManager;
import com.amnmoj.mobi.yabu.logic.services.SmsTracker;
import com.amnmoj.mobi.yabu.tools.Pref;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;


public class CallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		context.startService(new Intent(context, SmsTracker.class));
		if (Pref.getInstance(context).isEnable() && Pref.getInstance(context).isMissedCallOn()) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
					ProtocolManager.getInstance(context).onReceiveNewCallState(telephony.getCallState(), intent.getStringExtra("incoming_number"));
				}
			}).start();
		}
	}

}
