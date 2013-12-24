package com.balaji.birthdayactivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SecondTab extends ListActivity {
	/** Called when the activity is first created. */
	EditText inputSearch;
	//ArrayAdapter<String> adapter;
	SimpleAdapter simpleAdapter;
	//List<String> searchResults = new ArrayList<String>();
	List<Map<String, String>> originalData = new ArrayList<Map<String, String>>();
	List<DateOfBirth> users = new ArrayList<DateOfBirth>();
	List<DateOfBirth> beforeToday = new ArrayList<DateOfBirth>();
	List<DateOfBirth> afterToday = new ArrayList<DateOfBirth>();
	List<Map<String, String>> displayData = new ArrayList<Map<String, String>>();
	DBHelper dbHelper;
	int passingId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.secondtab);
		
		sortAndGetAllData();
		System.out.println("all " + users.size());
		
		filerDataForSearch();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(v.getContext(), EditDetails.class);
		
		//Log.i("val --", searchResults.get(position).toString());
		Map<String, String> datum = displayData.get(position);
		for(int i=0; i<users.size(); i++)
		{
			DateOfBirth dateOfBirth = users.get(i);

			if(dateOfBirth.getDisplayName().equalsIgnoreCase(datum.get("name")))
			{
				Log.i("ori1 -- ", dateOfBirth.getName());
				//Log.i("sel1 -- ", searchResults.get(position));
				
				Bundle bundle = new Bundle();
				passingId = dateOfBirth.getId();
				bundle.putInt("Id", passingId);
				bundle.putString("oldName", datum.get("name"));
				intent.putExtras(bundle);
			}
		}
		//startActivity(intent);
		startActivityForResult(intent, 1);
	}	
	public void onResume()
	{
		super.onResume();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		String isDelete = "no";
		if (requestCode == 1) {
	         if(resultCode == RESULT_OK){
	          isDelete = data.getStringExtra("isDelete");
	         	}
	         }
			reloadAllDatas(isDelete);	
	}
	
	public void filerDataForSearch()
	{
		if(users.size() == 0)
		{
			//searchResults.add("Empty");
			
			Map<String, String> datum = new HashMap<String, String>();			
			datum.put("name", "Empty");
			datum.put("age", "Completing: 0 year");
			displayData.add(datum);
		}
		
		for(DateOfBirth user : users)
		{
			//searchResults.add(user.getDisplayName());
			
			Map<String, String> datum = new HashMap<String, String>();			
			datum.put("name", user.getDisplayName());
			if(Integer.parseInt(user.getAge()) == 1)
			{
				datum.put("age", "Completing : " + user.getAge() + " year");
			}
			else
			{
				datum.put("age", "Completing : " + user.getAge() + " years");	
			}
			displayData.add(datum);
		}
		originalData.addAll(displayData);
		//adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults);
		
		simpleAdapter = new SimpleAdapter(this, displayData, android.R.layout.simple_list_item_2, new String[]{"name", "age"}, new int[]{android.R.id.text1, android.R.id.text2}); 
		//setListAdapter(adapter);
		ListView listView = (ListView)findViewById(android.R.id.list);
		listView.setAdapter(simpleAdapter);
	
//		stopService(new Intent(this, MyService.class));
		
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new TextWatcher() {
		     
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		    	//searchResults.clear();
		    	displayData.clear();
		        //adapter.getFilter().filter(cs);  
		    	String searchString = inputSearch.getText().toString().toLowerCase(); 
		    	 
		    	   for(int i=0;i<originalData.size();i++)
		    	   {
		    		   Map<String, String> datum = originalData.get(i);
		    		   String playerName = datum.get("name");
		    		   playerName = playerName.toLowerCase();
		    			   if(playerName.contains(searchString))
		    				   displayData.add(originalData.get(i));
		    				   //searchResults.add(originalData.get(i));
		    	   }
		    	   //adapter.notifyDataSetChanged();
		    	   simpleAdapter.notifyDataSetChanged();
		    }
		     
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
		        // TODO Auto-generated method stub
		         
		    }
		     
		    public void afterTextChanged(Editable arg0) {
		        // TODO Auto-generated method stub                         
		    }
		});
//		adapter.notifyDataSetChanged();
	}
	
	public void sortAndGetAllData()
	{
		if(users.size() <= 0)
		{
			dbHelper = new DBHelper(this);
			users = dbHelper.getAllDateOfBirths(null);			
		}
		//Collections.sort(users, new DateComparator());
		
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		today.setYear(2000);
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		if(beforeToday.size() > 0)
		{
			beforeToday.clear();
		}
		if(afterToday.size() > 0)
		{
			afterToday.clear();
		}
		for(DateOfBirth dateOfBirth : users)
		{
			//System.out.println(dateOfBirth.getDobDate().toString());
			//System.out.println(today.toString());
					
			if(dateOfBirth.getDobDate().before(today))
			{
				beforeToday.add(dateOfBirth);
			}
			else	//includes today
			{
				if(dateOfBirth.getDobDate().getDate() == today.getDate() && dateOfBirth.getDobDate().getMonth() == today.getMonth())
				{
					//dateOfBirth.setname(dateOfBirth.getName() + " * ");
					//dateOfBirth.setDisplayName(dateOfBirth.getDobDate().getDate() + " / " + (dateOfBirth.getDobDate().getMonth()+1) + " - " + dateOfBirth.getName() + " * ");
					if(!dateOfBirth.getDisplayName().contains("*"))
					{
						dateOfBirth.setDisplayName(dateOfBirth.getDisplayName() + "*");	
						//...
						int modifiedAge = Integer.parseInt(dateOfBirth.getAge()) - 1;
						dateOfBirth.setAge(modifiedAge + "");
					}
				}
				afterToday.add(dateOfBirth);
			}
		}
			
		//users.removeAll(users);
		users.clear();
		
		/* Second Tab Content */
		System.out.println("before1 " + afterToday.size());
		System.out.println("before2 " + beforeToday.size());
		Collections.sort(afterToday, new DateComparator());
		Collections.sort(beforeToday, new DateComparator());
		users.addAll(afterToday);
		users.addAll(beforeToday);
	}
	public void reloadAllDatas(String isDelete)
	{
		if (isDelete.equalsIgnoreCase("no")) //updating the user 
		{
			for (DateOfBirth user : users) {
				if (user.getId() == passingId) {
					if (dbHelper == null) {
						dbHelper = new DBHelper(this);
					}

					DateOfBirth updatedDOB = dbHelper
							.getDateOfBirthForId(passingId);
					user.setId(updatedDOB.getId());
					user.setName(updatedDOB.getName());
					user.setDob(updatedDOB.getDob());
					user.setDobDate(updatedDOB.getDobDate());
					user.setOriginalYear(updatedDOB.getOriginalYear());
					user.setAge(updatedDOB.getAge());
					user.setDisplayName(updatedDOB.getDisplayName());
					
					break;
				}
			}
		}
		else
		{
			for (DateOfBirth user : users) 
			{
				if (user.getId() == passingId) 
				{
					users.remove(user);
					break;
				}
			}
		}
		
		sortAndGetAllData();
		
		if(originalData.size() > 0)
		{
			originalData.clear();
		}
		
		for(DateOfBirth user : users)
		{
			//originalData.add(user.getDisplayName());
			Map<String, String> datum = new HashMap<String, String>();			
			datum.put("name", user.getDisplayName());
			if(Integer.parseInt(user.getAge()) == 1)
			{
				datum.put("age", "Completing : " + user.getAge() + " year");
			}
			else
			{
				datum.put("age", "Completing : " + user.getAge() + " years");	
			}
			//displayData.add(datum);
			originalData.add(datum);
		}
		
		//System.out.println("ini--" + searchResults.size());
		//searchResults.clear();
		displayData.clear();
		
    	String searchString = inputSearch.getText().toString().toLowerCase(); 
    	 
		for (int i = 0; i < originalData.size(); i++) 
		{
			Map<String, String> datum = originalData.get(i);;
			String playerName = datum.get("name");
			playerName = playerName.toLowerCase();
			if (playerName.contains(searchString))
				displayData.add(originalData.get(i));
				//searchResults.add(originalData.get(i));
		}
		//adapter.notifyDataSetChanged();
		simpleAdapter.notifyDataSetChanged();
	//	filerDataForSearch();
	}
}