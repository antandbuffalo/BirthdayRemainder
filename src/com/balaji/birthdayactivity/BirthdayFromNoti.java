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

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class BirthdayFromNoti extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.tab);
		
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
		spec = tabHost.newTabSpec("other").setIndicator("Other",null).setContent(intent);
        tabHost.addTab(spec);
	}
}