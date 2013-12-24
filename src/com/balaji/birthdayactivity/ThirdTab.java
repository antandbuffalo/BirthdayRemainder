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
	//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	
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
}
