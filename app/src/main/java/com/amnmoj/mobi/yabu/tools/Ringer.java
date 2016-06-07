package com.amnmoj.mobi.yabu.tools;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class Ringer {
	private static Ringer instance;

	private MediaPlayer mMediaPlayer;
	private AudioManager mAudioManager;
	private Context mContext;

	public static Ringer getInstance(Context context) {
		if (instance == null)
			instance = new Ringer(context);
		return instance;
	}

	private Ringer(Context context) {
		mContext = context;
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	public void playMessageRing() {
		try {
			Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(500);
			Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(mContext, alert);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			mMediaPlayer.setLooping(false);
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				public void onPrepared(MediaPlayer mp) {
					mMediaPlayer.start();
				}
			});
			mMediaPlayer.prepare();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
