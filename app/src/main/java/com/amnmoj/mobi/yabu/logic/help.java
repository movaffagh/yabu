package com.amnmoj.mobi.yabu.logic;

import com.amnmoj.mobi.yabu.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class help extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_help);
		WebView mbrowser = (WebView) findViewById(R.id.mwebview);
		mbrowser.loadUrl("file:///android_asset/index.html");

	}
}
