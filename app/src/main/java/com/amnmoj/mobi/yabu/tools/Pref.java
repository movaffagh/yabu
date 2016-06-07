package com.amnmoj.mobi.yabu.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Pref {

	private static Pref mInstance;

	public static Pref getInstance(Context context) {

		if (mInstance == null) {
			mInstance = new Pref(context);
		}
		return mInstance;

	}

	private static final String KEY_IS_FIRST_RUN = "com.mmdt.phonealert.logic.Pref.KEY_IS_FIRST_RUN";

	private static final String KEY_SIM_CARD_SERIAL_NUMBER = "com.mmdt.phonealert.logic.Pref.KEY_SIM_CARD_SERIAL_NUMBER";

	private static final String KE_ENABLE = "pref_key_enable_app";
	private static final String KEY_PIN_CODE = "pref_key_pin_code";
	private static final String KEY_IS_SMS_ON = "pref_key_enable_sms";
	private static final String KEY_IS_MISSED_CALL_ON = "pref_key_enable_missed_call";
	private static final String KEY_IS_CHECK_SIM_CARD_CHANGE_ON = "pref_key_enable_simcard_change";
	private static final String KEY_BACK_UP_NUMBER = "pref_key_backup_number";
	private static final String KEY_MISSED_CALL_OPERATOR_NUMBER = "pref_key_missed_call_number";
	private static final String KEY_SMS_MANAGER_OPERATOR = "pref_key_sms_operator_number";

	private static final String KEY_USER_NUMBER = "pref_key_user_number";
	private static final String KEY_ALERT_SHOWER = "pref_key_enable_alert";
	private static final String KEY_LOCATION_REQUEST = "pref_key_enable_location";
	private static final String KEY_SET_AUDIO_PROFILE = "pref_key_enable_audio_profile_change";
	private static final String KEY_FORWARD_CALLS = "pref_key_enable_forward_calls";

	private Context mContext;
	private SharedPreferences mSharedPreferences;

	private Pref(Context context) {
		mContext = context;
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	public void setKEY_USER_NUMBER(String number) {
		mSharedPreferences.edit().putString(KEY_USER_NUMBER, number).commit();
	}

	public void setKEY_BACK_UP_NUMBER(String number) {
		mSharedPreferences.edit().putString(KEY_BACK_UP_NUMBER, number).commit();
	}

	public void setKEY_SMS_MANAGER_OPERATOR(String number) {
		mSharedPreferences.edit().putString(KEY_SMS_MANAGER_OPERATOR, number).commit();
	}

	public void setKEY_MISSED_CALL_OPERATOR_NUMBER(String number) {
		mSharedPreferences.edit().putString(KEY_MISSED_CALL_OPERATOR_NUMBER, number).commit();
	}

	public void setLastSimCardSerialNumber(String number) {
		mSharedPreferences.edit().putString(KEY_SIM_CARD_SERIAL_NUMBER, number).commit();
	}

	public String getLastSimCardSerialNumber() {
		return mSharedPreferences.getString(KEY_SIM_CARD_SERIAL_NUMBER, null);
	}

	public String getUserNumber() {
		return mSharedPreferences.getString(KEY_USER_NUMBER, null);
	}

	public String getPinCode() {
		return mSharedPreferences.getString(KEY_PIN_CODE, null);
	}

	public void setPinCode(String pinCode) {
		mSharedPreferences.edit().putString(KEY_PIN_CODE, pinCode).commit();
	}

	public boolean isSmsOn() {
		return mSharedPreferences.getBoolean(KEY_IS_SMS_ON, false);
	}

	public boolean isMissedCallOn() {
		return mSharedPreferences.getBoolean(KEY_IS_MISSED_CALL_ON, false);
	}

	public boolean isCheckSimChangedOn() {
		return mSharedPreferences.getBoolean(KEY_IS_CHECK_SIM_CARD_CHANGE_ON, false);
	}

	public String getBackUpNumber() {
		return mSharedPreferences.getString(KEY_BACK_UP_NUMBER, "");
	}

	public String getMissedCallOperatorNumber() {
		return mSharedPreferences.getString(KEY_MISSED_CALL_OPERATOR_NUMBER, "");
	}

	public String getSmsManagerOperator() {

		if (mSharedPreferences.getString("pref_key_operator_type", "just").equals("just")) {
			return mSharedPreferences.getString(KEY_SMS_MANAGER_OPERATOR, "");
		} else {
			return "any";
		}

	}

	public boolean isKEY_ALERT_SHOWEREnable() {
		return mSharedPreferences.getBoolean(KEY_ALERT_SHOWER, false);
	}

	public void setKEY_LOCATION_REQUEST(boolean isEnable) {
		mSharedPreferences.edit().putBoolean(KEY_LOCATION_REQUEST, isEnable).commit();
	}

	public boolean isKEY_LOCATION_REQUESTEnable() {
		return mSharedPreferences.getBoolean(KEY_LOCATION_REQUEST, false);
	}

	public boolean isKEY_SET_AUDIO_PROFILEEnable() {
		return mSharedPreferences.getBoolean(KEY_SET_AUDIO_PROFILE, false);
	}

	public void setKEY_FORWARD_CALLS(boolean isEnable) {
		mSharedPreferences.edit().putBoolean(KEY_FORWARD_CALLS, isEnable).commit();
	}

	public boolean isKEY_FORWARD_CALLSEnable() {
		return mSharedPreferences.getBoolean(KEY_FORWARD_CALLS, false);
	}

	public boolean isEnable() {
		return mSharedPreferences.getBoolean(KE_ENABLE, false);
	}

	public void setEnable(boolean isEnable) {
		mSharedPreferences.edit().putBoolean(KE_ENABLE, isEnable).commit();
	}

	public boolean isFirst() {
		return mSharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true);
	}

	public void setFirst(boolean first) {
		mSharedPreferences.edit().putBoolean(KEY_IS_FIRST_RUN, first).commit();
	}
}
