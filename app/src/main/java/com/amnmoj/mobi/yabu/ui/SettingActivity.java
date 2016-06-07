package com.amnmoj.mobi.yabu.ui;

import com.amnmoj.mobi.yabu.R;
import com.amnmoj.mobi.yabu.logic.help;
import com.amnmoj.mobi.yabu.logic.services.SmsTracker;
import com.amnmoj.mobi.yabu.tools.Pref;
import com.amnmoj.mobi.yabu.tools.tools;

import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private AlertDialog mAlertDialog;
	private Dialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Pref.getInstance(getApplicationContext()).isFirst()) {
			firstInitial();
		} else {
			notFirstInitial();
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("pref_key_sms_operator_number")) {
			String smsManagerOperator = Pref.getInstance(getApplicationContext()).getSmsManagerOperator();
			if (!smsManagerOperator.startsWith("00")) {
				Pref.getInstance(getApplicationContext()).setKEY_SMS_MANAGER_OPERATOR("0098" + smsManagerOperator.replaceFirst("0", ""));
			}
		} else if (key.equals("pref_key_missed_call_number")) {
			String missedCallOperatorNumber = Pref.getInstance(getApplicationContext()).getMissedCallOperatorNumber();
			if (!missedCallOperatorNumber.startsWith("00")) {
				Pref.getInstance(getApplicationContext()).setKEY_MISSED_CALL_OPERATOR_NUMBER("0098" + missedCallOperatorNumber.replaceFirst("0", ""));
			}
		}

		try {
			if (key.equals("pref_key_enable_app")) {
				if (sharedPreferences.getBoolean("pref_key_enable_app", false)) {
					getPreferenceScreen().findPreference("pref_category_missedcall").setEnabled(true);
					getPreferenceScreen().findPreference("pref_category_sms").setEnabled(true);
					getPreferenceScreen().findPreference("pref_category_detail").setEnabled(true);
					if (sharedPreferences.getBoolean("pref_key_enable_sms", false)) {
						getPreferenceScreen().findPreference("pref_key_operator_type").setEnabled(true);
						getPreferenceScreen().findPreference("pref_key_enable_location").setEnabled(true);
						getPreferenceScreen().findPreference("pref_key_enable_alert").setEnabled(true);
						getPreferenceScreen().findPreference("pref_key_enable_audio_profile_change").setEnabled(true);
						getPreferenceScreen().findPreference("pref_key_enable_forward_calls").setEnabled(true);
					} else {
						getPreferenceScreen().findPreference("pref_key_operator_type").setEnabled(false);
						getPreferenceScreen().findPreference("pref_key_enable_location").setEnabled(false);
						getPreferenceScreen().findPreference("pref_key_enable_alert").setEnabled(false);
						getPreferenceScreen().findPreference("pref_key_enable_audio_profile_change").setEnabled(false);
						getPreferenceScreen().findPreference("pref_key_enable_forward_calls").setEnabled(false);
					}
					if (sharedPreferences.getString("pref_key_operator_type", "just").equals("just")) {
						getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(true);
					} else {
						getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(false);
					}
					if (sharedPreferences.getBoolean("pref_key_enable_missed_call", false)) {
						getPreferenceScreen().findPreference("pref_key_missed_call_number").setEnabled(true);
					} else {
						getPreferenceScreen().findPreference("pref_key_missed_call_number").setEnabled(false);
					}
				} else {
					getPreferenceScreen().findPreference("pref_category_missedcall").setEnabled(false);
					getPreferenceScreen().findPreference("pref_category_sms").setEnabled(false);
					getPreferenceScreen().findPreference("pref_category_detail").setEnabled(false);
				}
			} else if (key.equals("pref_key_enable_sms")) {
				if (sharedPreferences.getBoolean("pref_key_enable_sms", false)) {
					getPreferenceScreen().findPreference("pref_key_operator_type").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_location").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_alert").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_audio_profile_change").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_forward_calls").setEnabled(true);
					if (sharedPreferences.getString("pref_key_operator_type", "just").equals("just")) {
						getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(true);
					} else {
						getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(false);
					}
				} else {
					getPreferenceScreen().findPreference("pref_key_operator_type").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_location").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_alert").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_audio_profile_change").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_forward_calls").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(false);
				}

			} else if (key.equals("pref_key_operator_type")) {
				if (getPreferenceScreen().findPreference("pref_key_sms_operator_number") != null) {
					{
						if (sharedPreferences.getString("pref_key_operator_type", "just").equals("just")) {
							getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(true);
						} else {
							getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(false);
						}
					}
				} else if (key.equals("pref_key_language")) {
					Locale locale = new Locale(sharedPreferences.getString("pref_key_language", "just"));
					Locale.setDefault(locale);
					Configuration config = new Configuration();
					config.locale = locale;
					getApplicationContext().getResources().updateConfiguration(config, null);
					setPreferenceScreen(null);
					addPreferencesFromResource(R.xml.settings);
				}
			} else if (key.equals("pref_key_enable_missed_call")) {
				if (sharedPreferences.getBoolean("pref_key_enable_missed_call", false)) {
					getPreferenceScreen().findPreference("pref_key_missed_call_number").setEnabled(true);
				} else {
					getPreferenceScreen().findPreference("pref_key_missed_call_number").setEnabled(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void firstInitial() {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		Locale locale = new Locale(sharedPreferences.getString("pref_key_language", "fa"));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getApplicationContext().getResources().updateConfiguration(config, null);

		mDialog = new Dialog(this);
		mDialog.setContentView(R.layout.layout_first_initial);
		mDialog.setTitle(R.string.fill_below);
		mDialog.setCancelable(false);

		Button button = (Button) mDialog.findViewById(R.id.b_first_initial_ok);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText userNumber = (EditText) mDialog.findViewById(R.id.et_usernumber);
				EditText pincode = (EditText) mDialog.findViewById(R.id.et_pincode);
				EditText backupNumber = (EditText) mDialog.findViewById(R.id.et_backup_number);
				if (userNumber.getText().toString().equals("") || pincode.getText().toString().equals("") || backupNumber.getText().toString().equals("")) {
					mAlertDialog = new AlertDialog.Builder(SettingActivity.this).setTitle(R.string.alert_).setMessage(R.string.fill_all_parts)
							.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									mAlertDialog.cancel();
								}
							}).setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
				} else if (!userNumber.getText().toString().startsWith("00") || !backupNumber.getText().toString().startsWith("00")) {
					mAlertDialog = new AlertDialog.Builder(SettingActivity.this).setTitle(R.string.alert_).setMessage(R.string.number_should)
							.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									mAlertDialog.cancel();
								}
							}).setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
				} else {
					done(userNumber.getText().toString(), pincode.getText().toString(), backupNumber.getText().toString());
					mDialog.cancel();
					notFirstInitial();
				}
			}
		});
		mDialog.show();

	}

	@SuppressWarnings("deprecation")
	private void notFirstInitial() {

		// Pref.getInstance(getApplicationContext()).setLastSimCardSerialNumber(tools.getSimSerialNumber(getApplicationContext()));
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		Locale locale = new Locale(sharedPreferences.getString("pref_key_language", "fa"));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getApplicationContext().getResources().updateConfiguration(config, null);

		startService(new Intent(this, SmsTracker.class));

		addPreferencesFromResource(R.xml.settings);

		Preference preference = getPreferenceScreen().findPreference("pref_category_help_opener");
		preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				Intent intent = new Intent(getApplicationContext(), help.class);
				startActivity(intent);
				return false;
			}
		});
		try {
			if (sharedPreferences.getBoolean("pref_key_enable_app", false)) {
				getPreferenceScreen().findPreference("pref_category_missedcall").setEnabled(true);
				getPreferenceScreen().findPreference("pref_category_sms").setEnabled(true);
				getPreferenceScreen().findPreference("pref_category_detail").setEnabled(true);
				if (sharedPreferences.getBoolean("pref_key_enable_sms", false)) {
					getPreferenceScreen().findPreference("pref_key_operator_type").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_location").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_alert").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_audio_profile_change").setEnabled(true);
					getPreferenceScreen().findPreference("pref_key_enable_forward_calls").setEnabled(true);
				} else {
					getPreferenceScreen().findPreference("pref_key_operator_type").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_location").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_alert").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_audio_profile_change").setEnabled(false);
					getPreferenceScreen().findPreference("pref_key_enable_forward_calls").setEnabled(false);
				}
				if (getPreferenceScreen().findPreference("pref_key_sms_operator_number") != null) {
					if (sharedPreferences.getString("pref_key_operator_type", "just").equals("just")) {
						getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(true);
					} else {
						getPreferenceScreen().findPreference("pref_key_sms_operator_number").setEnabled(false);
					}
				}
				if (sharedPreferences.getBoolean("pref_key_enable_missed_call", false)) {
					getPreferenceScreen().findPreference("pref_key_missed_call_number").setEnabled(true);
				} else {
					getPreferenceScreen().findPreference("pref_key_missed_call_number").setEnabled(false);
				}
			} else {
				getPreferenceScreen().findPreference("pref_category_missedcall").setEnabled(false);
				getPreferenceScreen().findPreference("pref_category_sms").setEnabled(false);
				getPreferenceScreen().findPreference("pref_category_detail").setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void done(String userNumber, String pincode, String backupnumber) {
		Pref.getInstance(getApplicationContext()).setPinCode(pincode);
		Pref.getInstance(getApplicationContext()).setKEY_BACK_UP_NUMBER(backupnumber);
		Pref.getInstance(getApplicationContext()).setKEY_MISSED_CALL_OPERATOR_NUMBER(backupnumber);
		Pref.getInstance(getApplicationContext()).setKEY_SMS_MANAGER_OPERATOR(backupnumber);
		Pref.getInstance(getApplicationContext()).setLastSimCardSerialNumber(tools.getSimSerialNumber(getApplicationContext()));
		Pref.getInstance(getApplicationContext()).setKEY_USER_NUMBER(userNumber);
		Pref.getInstance(getApplicationContext()).setFirst(false);

	}
}
