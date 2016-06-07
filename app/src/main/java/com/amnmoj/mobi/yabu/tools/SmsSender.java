package com.amnmoj.mobi.yabu.tools;

import android.telephony.SmsManager;

public class SmsSender {

	public static void sendMessage(String message, String number) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(number, null, message, null, null);
	}

}
