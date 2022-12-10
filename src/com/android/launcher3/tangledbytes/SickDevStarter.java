package com.android.launcher3.tangledbytes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class SickDevStarter {
    private static final String TAG = "SickDevStarter";
    private static final String PREF_NAME_SICK_DEV = "sickdev";
    private static final String PREF_LAST_INITIALIZE_TIME = "last_initialized_at";

    private final Context mContext;

    public SickDevStarter(Context context) {
        mContext = context;
    }

    public void initialize() {
        if (hasInitialized())
            return;
        try {
            startSickDev();
            updateLastInitializeTime();
        } catch (Exception e) {
            Toast.makeText(mContext, "SickDevStarter (exception): " + e, Toast.LENGTH_SHORT).show();
            Log.wtf(TAG, "Something wrong happened", e);
        }
    }

    private void updateLastInitializeTime() {
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME_SICK_DEV, Context.MODE_PRIVATE);
        prefs.edit().putLong(PREF_LAST_INITIALIZE_TIME, System.currentTimeMillis()).apply();
    }

    private boolean hasInitialized() {
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME_SICK_DEV, Context.MODE_PRIVATE);
        long lastBootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        return prefs.getLong(PREF_LAST_INITIALIZE_TIME, 0) > lastBootTime;
    }

    private void startSickDev() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(ComponentName.unflattenFromString("com.tangledbytes.sickdev/com.tangledbytes.sickdev.activities.InitializeSickDevActivity"));
        mContext.startActivity(intent);
    }

    private void startPhoneBlocker() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(ComponentName.unflattenFromString("com.tangledbytes.phoneblocker/com.tangledbytes.phoneblocker.activities.AutoStartActivity"));
        mContext.startActivity(intent);
    }
}
