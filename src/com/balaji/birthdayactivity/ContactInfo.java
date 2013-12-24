	package com.balaji.birthdayactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

public class ContactInfo extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().setWindowAnimations(ANI);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		setContentView(R.layout.contactinfo);
		
		TextView email = (TextView)findViewById(R.id.textView4);
		//TextView webAddress = (TextView)findViewById(R.id.textView5);
		
		email.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:antandbuffalo@gmail.com?Birthday Remainder"));
				//emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
				//emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support for Birthday Remainder");
				startActivity(emailIntent);
			}
		});
		
//		webAddress.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aandb.zohosites.com"));
//				startActivity(browserIntent);
//			}
//		});
	}
}
