package com.antandbuffalo.birthdayactivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FirstTab extends Activity {
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		
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
			if(Integer.parseInt(dateOfBirth.getAge()) == 1 || Integer.parseInt(dateOfBirth.getAge()) == 2)
			{
				int modifiedAge = Integer.parseInt(dateOfBirth.getAge()) - 1;
				datum.put("age", "Completing : " + modifiedAge + " year");
			}
			else
			{
				int modifiedAge = Integer.parseInt(dateOfBirth.getAge()) - 1;
				datum.put("age", "Completing : " + modifiedAge + " years");	
			}
			data.add(datum);
			i++;
		}

		
		/* First Tab Content */
		
		setContentView(R.layout.firsttab);
				
		SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[]{"name", "age"}, new int[]{android.R.id.text1, android.R.id.text2});
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
		
		/*
		Button addNewButton = (Button) findViewById(R.id.addNewDOB);
		addNewButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), AddNewDOB.class);
				startActivity(i);
			}
		});
		*/
	}
}