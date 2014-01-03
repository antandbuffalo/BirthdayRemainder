	package com.antandbuffalo.birthdayactivity;

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
				// Intent emailIntent = new Intent(Intent.ACTION_VIEW,
				// Uri.parse("mailto:antandbuffalo@gmail.com?Birthday Remainder"));
				// //emailIntent.setClassName("com.google.android.gm",
				// "com.google.android.gm.ConversationListActivity");
				// //emailIntent.putExtra(Intent.EXTRA_SUBJECT,
				// "Support for Birthday Remainder");
				// startActivity(emailIntent);
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { "antandbuffalo@gmail.com" });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Birthday Remainder");
				startActivity(Intent.createChooser(emailIntent, "Contact Us"));
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
