package com.antandbuffalo.birthdayactivity;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteTracker extends BroadcastReceiver {
	AlarmManager am;
	Context context;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		context = arg0;
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// setOneTimeAlarm();
		System.out.println("inside boot complete");
		setRepeatingAlarm();
	}
	public void setRepeatingAlarm() {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
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
