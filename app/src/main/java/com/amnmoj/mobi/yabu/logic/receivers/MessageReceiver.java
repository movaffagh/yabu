package com.amnmoj.mobi.yabu.logic.receivers;

import com.amnmoj.mobi.yabu.logic.protocol.ProtocolManager;
import com.amnmoj.mobi.yabu.logic.services.SmsTracker;
import com.amnmoj.mobi.yabu.tools.Pref;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;


public class MessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		context.startService(new Intent(context, SmsTracker.class));
		Log.d("FFF", "Receive Message");
		if (Pref.getInstance(context).isEnable() && Pref.getInstance(context).isSmsOn()) {
			Log.d("FFF", "sms is on");
			Bundle bundle = intent.getExtras();
			String sms = "";
			String sender = null;
			Object messages[] = (Object[]) bundle.get("pdus");
			SmsMessage smsMessage[] = new SmsMessage[messages.length];
			for (int n = 0; n < messages.length; n++) {
				smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
				sms += smsMessage[n].getMessageBody();
			}
			if (sender == null) {
				sender = smsMessage[0].getDisplayOriginatingAddress();
			}
			final String message = sms;
			final String from = sender;
			if (ProtocolManager.getInstance(context).onReceiveSms(from, message)) {
				abortBroadcast();
			}
		}
	}
}
