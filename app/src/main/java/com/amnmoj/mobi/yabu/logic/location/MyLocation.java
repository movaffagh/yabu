package com.amnmoj.mobi.yabu.logic.location;

import com.amnmoj.mobi.yabu.tools.Hacks;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;


public class MyLocation implements LocationListener {

	private static MyLocation instance;

	public static MyLocation getInstance(Context context) {
		if (instance == null) {
			instance = new MyLocation(context);
		}
		return instance;
	}

	private Context mContext;
	private MyLocationListener mMyLocationListener;

	private LocationManager mLocationManager;

	private MyLocation(Context context) {
		mContext = context;
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		start();
	}

	public void addListener(MyLocationListener myLocationListener) {
		mMyLocationListener = myLocationListener;
	}

	private void start() {
		try {
			Looper.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean gpsIsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean networkIsEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean passiveIsEnabled = mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
		Log.d("FFF", gpsIsEnabled + " " + networkIsEnabled + " " + passiveIsEnabled);

		if (!gpsIsEnabled) {
			enableData();
		}
		if (gpsIsEnabled) {
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, this);
		}
		if (networkIsEnabled) {
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 50, this);
		}
		if (passiveIsEnabled) {
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 50, this);
		}

	}

	public Location getLocation() {
		Location best = null;
		List<String> allProviders = mLocationManager.getAllProviders();
		for (String provider : allProviders) {
			Location lastKnownLocation = mLocationManager.getLastKnownLocation(provider);
			if (lastKnownLocation == null) {
				Log.d("FFF", "Provider : " + provider + " is null");
			} else {
				Log.d("FFF", "Provider : " + provider + " Accuarcy : " + lastKnownLocation.getAccuracy() + " " + lastKnownLocation.getLatitude() + " "
						+ lastKnownLocation.getLongitude() + " " + lastKnownLocation.getAltitude() + " " + lastKnownLocation.getSpeed());

				if (best == null) {
					best = lastKnownLocation;
				} else if (best.getAccuracy() > lastKnownLocation.getAccuracy()) {
					best = lastKnownLocation;
				}
			}

		}
		if (best != null) {
			Log.d("FFF", "Best Provider : " + best.getProvider() + " Accuarcy : " + best.getAccuracy() + " " + best.getLatitude() + " " + best.getLongitude()
					+ " " + best.getAltitude() + " " + best.getSpeed());
		} else {
			Log.d("FFF", "Best is null");
		}

		return best;

	}

	@Override
	public void onLocationChanged(Location location) {
		if (mMyLocationListener != null)
			mMyLocationListener.onReceive(location);
		Log.d("FFF", "onLocationChanged");
		Log.d("FFF",
				" Provider : " + location.getProvider() + " Accuarcy : " + location.getAccuracy() + " " + location.getLatitude() + " "
						+ location.getLongitude() + " " + location.getAltitude() + " " + location.getSpeed());
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("FFF", "onStatusChanged");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("FFF", "onProviderEnabled");
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("FFF", "onProviderDisabled");
	}

	private void enableGPS() {
		if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Log.d("FFF", "GPS is enable");
		} else {
			Log.d("FFF", "try to enable GPS");
			Hacks.turnGPSOn(mContext);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Log.d("FFF", "succesfully enable GPS with hack");
			} else {
				Log.d("FFF", "failed to enable GPS with hack");
			}
		}
	}

	private void enableData() {
		TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED) {
			Log.d("FFF", "Network is enable");
		} else {
			Log.d("FFF", "try to enable Network");
			try {
				Hacks.turnDataOn(mContext);
			} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchFieldException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				Log.d("FFF", "succesfully enable Network with hack");
			} else {
				Log.d("FFF", "failed to enable Network with hack");
			}
		}
	}

	private void disableData() {
		try {
			Hacks.turnDataOff(mContext);
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

}
