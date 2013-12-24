/*package com.balaji.birthdayactivity;

 import android.app.Activity;
 import android.os.Bundle;

 public class BirthdayActivity extends Activity {
 // Called when the activity is first created.
 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.tab);
 }
 }
 */

package com.balaji.birthdayactivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TabHost;

public class BirthdayActivity extends TabActivity {
	AlarmManager am;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		setContentView(R.layout.main);

//		ProgressDialog progressDialog;
//		progressDialog = new ProgressDialog(this);
//		progressDialog.setCancelable(true);
//		progressDialog.setMessage("Please wait while initial launch...");
	
		/** TabHost will have Tabs */
		TabHost tabHost = getTabHost(); //(TabHost) findViewById(android.R.id.tabhost);

		/**
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		/** tid1 is firstTabSpec Id. Its used to access outside. */
		TabHost.TabSpec spec;
		Intent intent;
		
		intent = new Intent().setClass(this, FirstTab.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		spec = tabHost.newTabSpec("today").setIndicator("Today",null).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, SecondTab.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		spec = tabHost.newTabSpec("upComing").setIndicator("Up Coming",null).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ThirdTab.class);
		spec = tabHost.newTabSpec("settings").setIndicator("Settings",null).setContent(intent);
        tabHost.addTab(spec);

		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// setOneTimeAlarm();
		//setRepeatingAlarm();
		Utility.setRepeatingAlarm(this, am);
	}

	public void setRepeatingAlarm() {
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar calendar = Calendar.getInstance();
		// 9 AM
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
		// am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
		// (5 * 1000), pendingIntent);
	}
}