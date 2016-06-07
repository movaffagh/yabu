package com.amnmoj.mobi.yabu.tools;

import android.content.Context;
import android.telephony.TelephonyManager;

public class tools {

	public static String getSimSerialNumber(Context context) {

		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getSimSerialNumber();

	}
}
