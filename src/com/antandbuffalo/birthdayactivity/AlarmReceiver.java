package com.antandbuffalo.birthdayactivity;

import java.util.Calendar;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	NotificationManager nm;
	String names;
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("inside receive");
		
		nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		CharSequence from = "Birthday";
		CharSequence message = birthdayNamesToday(context);
		
		//notification opening intent
		Intent resultingIntent = new Intent(context, BirthdayFromNoti.class);
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, resultingIntent, 0);
		Notification notif = new Notification(R.drawable.bir1, message, System.currentTimeMillis());
		notif.setLatestEventInfo(context, from, message, contentIntent);
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notif.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
		
		if(!names.equalsIgnoreCase(""))
		{
			nm.notify(1, notif);
		}
	}
	
	public String birthdayNamesToday(Context context)
	{
		DBHelper dbHelper = new DBHelper(context);
		Calendar cal = Calendar.getInstance();
		
		int thisDate = cal.get(Calendar.DATE);
		int thisMonth = cal.get(Calendar.MONTH) + 1;
		
		String todayInString = thisDate + " " + thisMonth + " " + "2000"; 

		List<DateOfBirth> selectedDOBs = dbHelper.getAllDateOfBirths(todayInString);

		names = "";
		for(DateOfBirth user : selectedDOBs)
		{
			names = names + user.getName() + ", ";
		}	
		if(!(names.equalsIgnoreCase("")))
		names = names.substring(0, names.length() - 2);
		
		return names;
	}

}
