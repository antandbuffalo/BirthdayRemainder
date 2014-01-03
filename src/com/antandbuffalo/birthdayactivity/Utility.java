package com.antandbuffalo.birthdayactivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

public class Utility {
	
	public static int getDiffYears(Date first, int originalYear, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    Log.i("date1", a.get(Calendar.YEAR)+"");
	    Log.i("date2", b.get(Calendar.YEAR)+"");
	    int diff = b.get(Calendar.YEAR) - originalYear;
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff + 1;
	}
	
	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal;
	}
	
	public static void setRepeatingAlarm(Context context, AlarmManager am)
	{
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
	public static void loadFromFile(final Context context)
	{
		AlertDialog.Builder alertDialogBuilder;
		alertDialogBuilder = new AlertDialog.Builder(context);
		try {
			final BufferedReader br;
			final DataInputStream in;

			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// sdcard not found. read from bundle
				AssetManager am = context.getAssets();
				InputStream fstream = am.open("dob.txt");
				// Get the object of DataInputStream
				in = new DataInputStream(fstream);

				alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
				alertDialogBuilder.setTitle("Alert!!!");
				alertDialogBuilder.setMessage("sdcard not found. Do you want to load app default datas");
				alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						loadDataFromFile(context, null, in);
						// Close the input stream
						dialog.cancel();
					}
				});
				alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();				

			} else {
				File sdcard = Environment.getExternalStorageDirectory();
				// Get the text file
				File file = new File(sdcard, "dob.txt");
				if (file.exists()) // check for existence
				{
					br = new BufferedReader(new FileReader(file));
					loadDataFromFile(context, br, null);
					
					alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
					alertDialogBuilder.setTitle("Success");
					alertDialogBuilder.setMessage("Data loaded successfully");
					alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();				
				}
				else // read from bundle if not exist
				{
					AssetManager am = context.getAssets();
					InputStream fstream = am.open("dob.txt");
					// Get the object of DataInputStream
					in = new DataInputStream(fstream);
					alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
					alertDialogBuilder.setTitle("Alert!!!");
					alertDialogBuilder.setMessage("\"sdcard/dob.txt\" file not found. Do you want to load app default datas?");
					alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							loadDataFromFile(context, null, in);
							//function call to load data from local file
							dialog.cancel();
						}
					});
					alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});

					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();				
				}
			}
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}
	public static void loadDataFromFile(Context context, BufferedReader br, DataInputStream in)
	{
		if(br == null)
		{
			br = new BufferedReader(new InputStreamReader(in));	
		}
		String strLine, n1;
		int d1, m1, y1, yOriginal;
		DateOfBirth dateOfBirth = new DateOfBirth();
		DBHelper dbHelper = new DBHelper(context);
		dbHelper.deleteAllRecords();
		try {
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				System.out.println("in main ac -- " + strLine);
				
				String[] lineComponents = strLine.split(" ");
				n1 = lineComponents[0].replace("_", " ");
				d1 = Integer.parseInt(lineComponents[1]);
				m1 = Integer.parseInt(lineComponents[2]);
				y1 = Integer.parseInt("2000");
				yOriginal = Integer.parseInt(lineComponents[3]);
				
				dateOfBirth.setName(n1);
				dateOfBirth.setDob(d1 + " " + m1 + " " + y1);
				dateOfBirth.setOriginalYear(yOriginal);
				
				dbHelper.addDOB(dateOfBirth);
			}
			in.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
