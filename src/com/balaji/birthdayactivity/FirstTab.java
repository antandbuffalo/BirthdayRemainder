package com.balaji.birthdayactivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FirstTab extends Activity {
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		Context context = getApplicationContext();
//		NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

/*		try {
			// Open the file that is the first
			// command line parameter
//			File file = new File("dob.txt");
//			System.out.println(file.isFile());
//			FileInputStream fstream = new FileInputStream("/Birthday/dob.txt");
//			FileInputStream fstream = openFileInput("dob.txt");
//			AssetFileDescriptor descriptor = getAssets().openFd("myfile.txt");
//			FileReader reader = new FileReader(descriptor.getFileDescriptor());

			
			AssetManager am = getApplicationContext().getAssets();
			InputStream fstream = am.open("dob.txt");		
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br;
			
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				//sdcard not found
				br = new BufferedReader(new InputStreamReader(in));
			}
			else
			{
				File sdcard = Environment.getExternalStorageDirectory();
				//Get the text file
				File file = new File(sdcard,"dob.txt");
				if(file.exists())	//check for existence
				{
					br = new BufferedReader(new FileReader(file));
				}
				else	//read from bundle if not exist
				{
					br = new BufferedReader(new InputStreamReader(in));
				}
			}
			
			String strLine;
			Calendar cal = Calendar.getInstance();
			int thisDate = cal.get(Calendar.DATE);
			int thisMonth = cal.get(Calendar.MONTH) + 1;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				System.out.println(strLine);
				for(int i=0; i<strLine.length(); i++)
				{
					if(strLine.charAt(i) == ' ')
					{
						String n1 = strLine.substring(0, i);
//						System.out.println("date " + strLine.substring(i+1, i+3) + "month " + strLine.substring(i+4, i+6));
						int d1 = Integer.parseInt(strLine.substring(i+1, i+3));
						int m1 = Integer.parseInt(strLine.substring(i+4, i+6));
//						System.out.println("date " + d1 + "month " + m1);
						if(m1 == thisMonth && d1 == thisDate)
						{
							UserDOB userDob = new UserDOB();
							userDob.name = thisDate +" - "+ n1;
							Date birthDate = new Date();
							Date today = cal.getTime();
							
							birthDate.setDate(d1);
							birthDate.setMonth(m1 - 1);
							birthDate.setYear(today.getYear());
							//System.out.println("current year -- "+ today.getYear() + "cal --" + Calendar.YEAR);							
							userDob.setBirthDate(birthDate);
							users.add(userDob);
						}
						break;
					}
				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		*/

		String[] names;
		DBHelper dbHelper = new DBHelper(this);
		Calendar cal = Calendar.getInstance();
		
		int thisDate = cal.get(Calendar.DATE);
		int thisMonth = cal.get(Calendar.MONTH) + 1;
		
		String todayInString = thisDate + " " + thisMonth + " " + "2000"; 

		List<DateOfBirth> selectedDOBs = dbHelper.getAllDateOfBirths(todayInString);
		int i=0;
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		if(selectedDOBs.isEmpty())
		{
			names = new String[1];			
			names[0] = "Empty";
			Map<String, String> datum = new HashMap<String, String>();			
			datum.put("name", "Empty");
			datum.put("age", "Completing : 0 year");
			data.add(datum);
		}
		else
		{
			names = new String[selectedDOBs.size()];
		}
				
		for (DateOfBirth dateOfBirth : selectedDOBs) {
			names[i] = dateOfBirth.getDobDate().getDate() + " - " + dateOfBirth.getName();
//			System.out.println("dis -- id--" + dateOfBirth.getId()
//					+ " --name--" + dateOfBirth.getName() + "--dob--"
//					+ dateOfBirth.getDob());
			Map<String, String> datum = new HashMap<String, String>();
			datum.put("name", names[i]);
			if(Integer.parseInt(dateOfBirth.getAge()) == 1)
			{
				datum.put("age", "Completing : " + dateOfBirth.getAge() + " years");
			}
			else
			{
				datum.put("age", "Completing : " + dateOfBirth.getAge() + " years");	
			}
			data.add(datum);
			i++;
		}

		
		/* First Tab Content */

//		if(users.size() == 0)
//		{
//			names = new String[1];		
//			names[0] = "Empty";
//		}
//		else
//		{
//			names = new String[users.size()];
//		}
//		int i = 0;
//		for(UserDOB user : users)
//		{
//			names[i] = user.name;
//			i++;
//		}
		
		setContentView(R.layout.firsttab);
				
		SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[]{"name", "age"}, new int[]{android.R.id.text1, android.R.id.text2});
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
/*		Notification notification = new Notification();
		notification.icon = R.drawable.icon;
		notification.tickerText = "Hai";
		notification.when = System.currentTimeMillis();
		
		Intent notificationIntent = new Intent(context, BirthdayActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		
		notification.setLatestEventInfo(context, "testing", "testing test", contentIntent);
		
		notificationManager.notify(100, notification);
		// ListView listView = (ListView)findViewById(R.id.listView1);
		// setListAdapter(new ArrayAdapter<String>(this, R.id.listView1,
		// items));
*/
//		startService(new Intent(this, MyService.class));
	}
}