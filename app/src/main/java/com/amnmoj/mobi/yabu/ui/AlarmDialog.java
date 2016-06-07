package com.amnmoj.mobi.yabu.ui;

import com.amnmoj.mobi.yabu.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class AlarmDialog extends Activity {

	public static final String KEY_MESSAGE = "com.mmdt.phonealert.ui.KEY_MESSAGE";
	public static final String KEY_IS_MESSAGE = "com.mmdt.phonealert.ui.KEY_IS_MESSAGE";
	public static final String KEY_IS_FLASH = "com.mmdt.phonealert.ui.KEY_IS_FLASH";
	public static final String KEY_IS_ALERT = "com.mmdt.phonealert.ui.KEY_IS_ALERT";

	private AlertDialog mAlertDialog;
	private boolean isMessage;
	private boolean isFlash;
	private boolean isAlert;
	private String mMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		setContentView(R.layout.dialog_alarm);

		isMessage = getIntent().getBooleanExtra(KEY_IS_MESSAGE, false);
		isFlash = getIntent().getBooleanExtra(KEY_IS_FLASH, false);
		isAlert = getIntent().getBooleanExtra(KEY_IS_ALERT, false);
		mMessage = getString(R.string.find_me);
		if (isMessage) {
			mMessage = getIntent().getStringExtra(KEY_MESSAGE);
		}

		mAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.alert_).setMessage(mMessage)
				.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mAlertDialog.cancel();
						if (mCamera != null) {
							try {
								mCamera.stopPreview();
								mCamera.release();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						finish();
					}
				}).setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();

		if (isFlash) {
			try {
				mCamera = Camera.open();
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							flash();
						}
					});
				}
			}, 1000, 1000);
		}
		if (isAlert) {
			try {
				ringHighVolume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onResume() {
		mVibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
		super.onResume();
	}

	private Timer timer = new Timer();
	private boolean active = true;
	private Vibrator mVibrator;
	private MediaPlayer mMediaPlayer = new MediaPlayer();
	private Camera mCamera;

	private void flash() {

		if (active) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			ImageView background_off_screen = (ImageView) findViewById(R.id.background_off_screen);
			background_off_screen.setVisibility(View.VISIBLE);
			if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
				try {
					Parameters p = mCamera.getParameters();
					p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					mCamera.setParameters(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (isAlert) {
				mVibrator.vibrate(500);

			}
		} else {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			ImageView background_off_screen = (ImageView) findViewById(R.id.background_off_screen);
			background_off_screen.setVisibility(View.GONE);
			if (mCamera != null) {
				try {
					Parameters p = mCamera.getParameters();
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					mCamera.setParameters(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (isAlert) {
				mVibrator.vibrate(500);
			}
		}
		active = !active;
	}

	private void ringHighVolume() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {

		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

		mMediaPlayer.setVolume(1, 1);
		mMediaPlayer.setDataSource(getApplicationContext(), alert);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				mMediaPlayer.start();
			}
		});
		mMediaPlayer.prepare();
	}

	@Override
	public void onAttachedToWindow() {
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	@Override
	protected void onDestroy() {
		if (mMediaPlayer != null)
			mMediaPlayer.stop();
		if (timer != null)
			timer.cancel();
		super.onDestroy();
	}
}
