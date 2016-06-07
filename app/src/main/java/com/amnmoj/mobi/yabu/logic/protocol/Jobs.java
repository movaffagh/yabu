package com.amnmoj.mobi.yabu.logic.protocol;

import com.amnmoj.mobi.yabu.R;
import com.amnmoj.mobi.yabu.logic.location.MyLocation;
import com.amnmoj.mobi.yabu.logic.location.MyLocationListener;
import com.amnmoj.mobi.yabu.tools.SmsSender;
import com.amnmoj.mobi.yabu.ui.AlarmDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;


public class Jobs {

	private static Jobs mInstance;

	public static Jobs getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new Jobs(context);
		}
		return mInstance;
	}

	private Context mContext;

	private Jobs(Context context) {
		mContext = context;
	}

	private Location mLastLocation;

	private MyLocationListener myLocationListener = new MyLocationListener() {

		@Override
		public void onReceive(Location location) {
		}
	};

	public void sendLocation(final String to) {
		MyLocation.getInstance(mContext).addListener(myLocationListener);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(45000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean shouldTry = true;
				int counter = 0;
				while (shouldTry) {
					mLastLocation = MyLocation.getInstance(mContext).getLocation();
					if (mLastLocation == null) {
						counter++;
						Log.d("FFF", "location unavailbe yet");
						if (counter == 30) {
							SmsSender.sendMessage(mContext.getString(R.string.location_unavailable), to);
							break;
						}
					} else {
						Log.d("FFF", "location available");
						SmsSender.sendMessage(mContext.getString(R.string.location_) + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude() + ","
								+ mLastLocation.getSpeed(), to);
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

	public void showers(boolean isFlash, boolean isAlert, boolean isMessage, String message) {
		Intent intent = new Intent(mContext, AlarmDialog.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(AlarmDialog.KEY_IS_FLASH, isFlash);
		intent.putExtra(AlarmDialog.KEY_IS_ALERT, isAlert);
		intent.putExtra(AlarmDialog.KEY_IS_MESSAGE, isMessage);
		intent.putExtra(AlarmDialog.KEY_MESSAGE, message);
		mContext.startActivity(intent);
	}

	public void sendSimCardChanged(String userNumber, String to) {
		SmsSender.sendMessage(mContext.getString(R.string.change_sim_message_before) + userNumber + mContext.getString(R.string.change_sim_message_after), to);
	}

	public void setGeneralAudioProfile() {
		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}

	public void setSilentAudioProfile() {
		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}

	public void setVibrateAudioProfile() {
		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}

	public void forwardCallsTo(String to) {
		String key_code = "*21*" + to + Uri.encode("#");
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + key_code));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}

	public void disableforwardCalls() {
		String key_code = Uri.encode("#") + "21" + Uri.encode("#");
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + key_code));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
}
