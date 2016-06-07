package com.amnmoj.mobi.yabu.logic.services;

import com.amnmoj.mobi.yabu.logic.protocol.ProtocolManager;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SmsListener {

	private static SmsListener mSmsListener;

	public static SmsListener getmSmsListener(Context context) {
		if (mSmsListener == null) {
			mSmsListener = new SmsListener(context);
		}
		return mSmsListener;
	}

	private MyContentObserver mContentObserver = new MyContentObserver();
	private Context mContext;

	public SmsListener(Context context) {
		mContext = context;
		mContext.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, mContentObserver);
	}

	private void smsNotify() {
		Cursor cur = mContext.getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
		if (cur.moveToFirst()) {
			Log.d("FFF",
					"sssssssssssssssssssssdadasdasd " + cur.getString(cur.getColumnIndex("address")) + cur.getString(cur.getColumnIndex("body")) + " "
							+ cur.getString(cur.getColumnIndex("date")) + " " + cur.getString(cur.getColumnIndex("_id")) + " "
							+ cur.getString(cur.getColumnIndex("read")) + " " + cur.getString(cur.getColumnIndex("seen")) + " "
							+ cur.getString(cur.getColumnIndex("protocol")) + " " + cur.getString(cur.getColumnIndex("status")));
		}
		String from = cur.getString(cur.getColumnIndex("address"));
		String body = cur.getString(cur.getColumnIndex("body"));
		String id = cur.getString(cur.getColumnIndex("_id"));
		String read = cur.getString(cur.getColumnIndex("read"));
		String seen = cur.getString(cur.getColumnIndex("seen"));
		String protocol = cur.getString(cur.getColumnIndex("protocol"));
		String status = cur.getString(cur.getColumnIndex("status"));
		cur.close();

		if (read.equals("0") && seen.equals("0") && protocol.equals("0") && status.equals("-1")) {
			if (ProtocolManager.getInstance(mContext).onReceiveSms(from, body)) {
				Log.d("FFF", "Delete Message");
				mContext.getContentResolver().delete(Uri.parse("content://sms"), "_id =?", new String[] { id });
			}
		}

	}

	private class MyContentObserver extends ContentObserver {

		public MyContentObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Log.d("FFF", "New Change on sms DB ");
			smsNotify();
		}

	}
}
