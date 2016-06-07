package com.amnmoj.mobi.yabu.logic.protocol;

import com.amnmoj.mobi.yabu.tools.Pref;
import com.amnmoj.mobi.yabu.tools.tools;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;


public class ProtocolManager {

	private static ProtocolManager mInstance;

	public static ProtocolManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ProtocolManager(context);
		}
		return mInstance;

	}

	private Context mContext;

	private int mLastCallState = TelephonyManager.CALL_STATE_IDLE;

	private ProtocolManager(Context context) {
		mContext = context;
	}

	public boolean onReceiveSms(String from, String body) {
		Log.d("FFF", "2");
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		Locale locale = new Locale(sharedPreferences.getString("pref_key_language", "fa"));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		mContext.getResources().updateConfiguration(config, null);

		final String normalizeFrom = from.replaceFirst("\\+", "00");
		Log.d("FFF", "Message from " + normalizeFrom + " boddy : " + body);
		try {
			String allowNumber = Pref.getInstance(mContext).getSmsManagerOperator();
			Log.d("FFF", "aaaaaaaa");
			if (allowNumber.toLowerCase(Locale.ENGLISH).equals("any") || normalizeFrom.equals(Pref.getInstance(mContext).getSmsManagerOperator())) {
				final String[] codes = body.split("@");
				Log.d("FFF", "bbbbbbb");
				if (codes[0].equals(Pref.getInstance(mContext).getPinCode())) {
					switch (codes[1].toUpperCase(Locale.ENGLISH).trim()) {
					case Protocols.ALERT_SHOWER:
					case Protocols.ALERT_SHOWER_FA:
					case Protocols.ALERT_SHOWER_N:
						Log.d("FFF", "ALERT_SHOWER");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(false, true, false, null);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.FLASH_SHOWER:
					case Protocols.FLASH_SHOWER_FA:
					case Protocols.FLASH_SHOWER_N:
						Log.d("FFF", "FLASH_SHOWER");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(true, false, false, null);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.MESSAGE_SHOWER:
					case Protocols.MESSAGE_SHOWER_FA:
					case Protocols.MESSAGE_SHOWER_N:
						Log.d("FFF", "MESSAGE_SHOWER");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(false, false, true, codes[2]);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.FLASH_ALERT:
					case Protocols.FLASH_ALERT_FA:
					case Protocols.FLASH_ALERT_N:
						Log.d("FFF", "FLASH_ALERT");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(true, true, false, null);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.MESSAGE_ALERT:
					case Protocols.MESSAGE_ALERT_FA:
					case Protocols.MESSAGE_ALERT_N:
						Log.d("FFF", "MESSAGE_ALERT");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(false, true, true, codes[2]);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.FLASH_MESSAGE:
					case Protocols.FLASH_MESSAGE_FA:
					case Protocols.FLASH_MESSAGE_N:
						Log.d("FFF", "FLASH_MESSAGE");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(true, false, true, codes[2]);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.FLASH_MESSAGE_ALERT:
					case Protocols.FLASH_MESSAGE_ALERT_FA:
					case Protocols.FLASH_MESSAGE_ALERT_N:
						Log.d("FFF", "FLASH_MESSAGE_ALERT");
						if (Pref.getInstance(mContext).isKEY_ALERT_SHOWEREnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).showers(true, true, true, codes[2]);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.FORWARD_CALLS:
					case Protocols.FORWARD_CALLS_FA:
					case Protocols.FORWARD_CALLS_N:
						Log.d("FFF", "FORWARD_CALLS");
						if (Pref.getInstance(mContext).isKEY_FORWARD_CALLSEnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).forwardCallsTo(codes[2]);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.CANCELL_FORWARD_CALLS:
					case Protocols.CANCELL_FORWARD_CALLS_FA:
					case Protocols.CANCELL_FORWARD_CALLS_N:
						Log.d("FFF", "FORWARD_CALLS");
						if (Pref.getInstance(mContext).isKEY_FORWARD_CALLSEnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).disableforwardCalls();
								}
							}).start();
							return true;
						}
						break;
					case Protocols.LOCATION_REQUEST:
					case Protocols.LOCATION_REQUEST_FA:
					case Protocols.LOCATION_REQUEST_N:
						Log.d("FFF", "LOCATION_REQUEST");
						if (Pref.getInstance(mContext).isKEY_LOCATION_REQUESTEnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).sendLocation(normalizeFrom);
								}
							}).start();
							return true;
						}
						break;
					case Protocols.SET_GENERAL_AUDIO:
					case Protocols.SET_GENERAL_AUDIO_N:
					case Protocols.SET_GENERAL_AUDIO_FA:
						Log.d("FFF", "SET_GENERAL_AUDIO");
						if (Pref.getInstance(mContext).isKEY_SET_AUDIO_PROFILEEnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).setGeneralAudioProfile();
								}
							}).start();
							return true;
						}
						break;
					case Protocols.SET_SILENT_AUDIO:
					case Protocols.SET_SILENT_AUDIO_FA:
					case Protocols.SET_SILENT_AUDIO_N:
						Log.d("FFF", "SET_SILENT_AUDIO");
						if (Pref.getInstance(mContext).isKEY_SET_AUDIO_PROFILEEnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).setSilentAudioProfile();
								}
							}).start();
							return true;
						}
						break;
					case Protocols.SET_VIBTATE_AUDIO:
					case Protocols.SET_VIBTATE_AUDIO_FA:
					case Protocols.SET_VIBTATE_AUDIO_N:
						Log.d("FFF", "SET_VIBTATE_AUDIO");
						if (Pref.getInstance(mContext).isKEY_SET_AUDIO_PROFILEEnable()) {
							new Thread(new Runnable() {

								@Override
								public void run() {
									Jobs.getInstance(mContext).setVibrateAudioProfile();
								}
							}).start();
							return true;
						}
						break;
					default:
						Log.d("FFF", "Wrong Request");
						break;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void onReceiveNewCallState(int callState, String from) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		Locale locale = new Locale(sharedPreferences.getString("pref_key_language", "fa"));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		mContext.getResources().updateConfiguration(config, null);

		if (from != null) {
			from = from.replaceFirst("\\+", "00");
			if (mLastCallState != callState) {
				Log.d("FFF", "Call state : " + callState + " " + from);
				if (from != null && from.equals(Pref.getInstance(mContext).getMissedCallOperatorNumber())
						&& mLastCallState == TelephonyManager.CALL_STATE_RINGING) {
					switch (callState) {
					case TelephonyManager.CALL_STATE_IDLE:
						if (mLastCallState == TelephonyManager.CALL_STATE_RINGING) {
							Log.d("FFF", "Missed call happened");
							// Jobs.getInstance(mContext).showers(true, false,
							// false, null);
							Jobs.getInstance(mContext).showers(true, true, false, null);
						}

						break;
					default:
						break;
					}
				}
			}
			mLastCallState = callState;
		}
	}

	public void onBootStartUpReceive() {
		int counter = 0;
		while (tools.getSimSerialNumber(mContext) == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (counter == 30) {
				break;
			}
		}
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		Locale locale = new Locale(sharedPreferences.getString("pref_key_language", "fa"));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		mContext.getResources().updateConfiguration(config, null);
		if (Pref.getInstance(mContext).getLastSimCardSerialNumber() != null && tools.getSimSerialNumber(mContext) != null) {
			if (!Pref.getInstance(mContext).getLastSimCardSerialNumber().equals(tools.getSimSerialNumber(mContext))) {
				Log.d("FFF", tools.getSimSerialNumber(mContext) + "  ssssss " + Pref.getInstance(mContext).getLastSimCardSerialNumber());
				TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
				while (telephony.getSimState() != TelephonyManager.SIM_STATE_READY) {
					Log.d("FFF", "wait to ready sim . . .");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				Jobs.getInstance(mContext).sendSimCardChanged(Pref.getInstance(mContext).getUserNumber(), Pref.getInstance(mContext).getBackUpNumber());
			}
		}
	}
}
