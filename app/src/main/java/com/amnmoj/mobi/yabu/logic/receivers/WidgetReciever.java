package com.amnmoj.mobi.yabu.logic.receivers;

import com.amnmoj.mobi.yabu.R;
import com.amnmoj.mobi.yabu.logic.services.SmsTracker;
import com.amnmoj.mobi.yabu.tools.Pref;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;


public class WidgetReciever extends AppWidgetProvider {
	private RemoteViews remoteViews;
	private ComponentName watchWidget;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		watchWidget = new ComponentName(context, WidgetReciever.class);
		Intent intentClick = new Intent(context, WidgetReciever.class);
		intentClick.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, "" + appWidgetIds[0]);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetIds[0], intentClick, 0);
		remoteViews.setOnClickPendingIntent(R.id.b_widget, pendingIntent);
		remoteViews.setOnClickPendingIntent(R.id.l_widget, pendingIntent);
		appWidgetManager.updateAppWidget(watchWidget, remoteViews);

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, SmsTracker.class));
		if (remoteViews == null) {
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		}
		if (intent.getAction() == null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {

				remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
				if (Pref.getInstance(context).isEnable()) {
					remoteViews.setImageViewResource(R.id.b_widget, R.drawable.ic_widget_off);
					Pref.getInstance(context).setEnable(false);
				} else {
					remoteViews.setImageViewResource(R.id.b_widget, R.drawable.ic_widget_on);
					Pref.getInstance(context).setEnable(true);
				}
				watchWidget = new ComponentName(context, WidgetReciever.class);
				(AppWidgetManager.getInstance(context)).updateAppWidget(watchWidget, remoteViews);
			}
		} else {
			super.onReceive(context, intent);
		}
		if (Pref.getInstance(context).isEnable()) {
			remoteViews.setImageViewResource(R.id.b_widget, R.drawable.ic_widget_on);
		} else {
			remoteViews.setImageViewResource(R.id.b_widget, R.drawable.ic_widget_off);
		}
		watchWidget = new ComponentName(context, WidgetReciever.class);
		(AppWidgetManager.getInstance(context)).updateAppWidget(watchWidget, remoteViews);
	}
}
