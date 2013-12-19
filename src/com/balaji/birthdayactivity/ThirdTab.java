package com.balaji.birthdayactivity;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ThirdTab extends ActivityGroup {
	public static ThirdTab thirdTab;
	private ArrayList<View> history;
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);    
	
	this.history = new ArrayList<View>();  
	thirdTab = this;

	Intent i = new Intent(this, ThirdTabContent.class);
	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	View view = getLocalActivityManager().startActivity("ThirdTabContent", i).getDecorView();

	replaceView(view);  
	//getWindow().setWindowAnimations(android.R.style.Animation_Translucent);
    //setContentView(R.layout.helpwindow);
	}
	
	public void replaceView(View v) {  
		// Adds the old one to history  
		history.add(v);  
		// Changes this Groups View to the new View.  
		setContentView(v);  
		}

	public void back() {  
		if(history.size() > 0) 
		{  
			history.remove(history.size()-1);  
			if(history.size() > 0)
				setContentView(history.get(history.size()-1));
			else
				finish();
		}
		else
		{  
			finish();  
		}  
	}  
	
	@Override  
	public void onBackPressed() {
		//http://android-developers.blogspot.in/2009/12/back-and-other-hard-keys-three-stories.html
	ThirdTab.thirdTab.back();  
	return;  
	}

	
		/*
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				Intent activity3Intent = new Intent(v.getContext(), AddNewDOB.class);
				StringBuffer urlString = new StringBuffer();
				//Activity1 parentActivity = (Activity1)getParent();
				replaceContentView("activity3", activity3Intent);
				}
		});*/
	
	/* public void replaceContentView(String id, Intent newIntent) {
		View view = getLocalActivityManager().startActivity(id,newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(); 
		this.setContentView(view);
		}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		System.out.println("back");
		Intent intent = new Intent(this, BirthdayActivity.class);
		//replaceContentView("activity4", intent);
		startActivity(intent);
		finish();
	 }
*/	
	
/*	
	List<UserDOB> users = new ArrayList<UserDOB>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			// Open the file that is the first
			// command line parameter

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
			int thisMonth = cal.get(Calendar.MONTH);
			// Read File Line By Line
			int member = 0;
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println(strLine);
				for (int i = 0; i < strLine.length(); i++) 
				{
					if (strLine.charAt(i) == ' ') 
					{
						String n1 = strLine.substring(0, i);
						int d1 = Integer.parseInt(strLine.substring(i + 1,
								i + 3));
						int m1 = Integer.parseInt(strLine.substring(i + 4,
								i + 6));
						int y1 = Integer.parseInt(strLine.substring(i + 7,
								i + 9));
						// System.out.println("date " + d1 + "month " + m1);
						UserDOB user = new UserDOB();
						user.name = d1 + "/" + m1 + " - " +n1;
						user.date = d1;
						user.month = m1;
						user.year = y1;
						Date birthDate = new Date();
						
						birthDate.setDate(d1);
						birthDate.setMonth(m1 - 1);
						birthDate.setYear(2000);
						
						user.setBirthDate(birthDate);

						users.add(user);
						break;
					}
				}
			}
			// Close the input stream
			in.close();
			
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("before " + users.size());
		Collections.sort(users, new DateComparator());
		System.out.println("after " + users.size());
		
		setContentView(R.layout.thirdtab);
		
		String[] names = new String[users.size()];
		int i = 0;
		for(UserDOB user : users)
		{
			names[i] = user.name;
			i++;
		}		
		
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names));
	} */
}
