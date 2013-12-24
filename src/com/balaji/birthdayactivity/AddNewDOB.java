package com.balaji.birthdayactivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewDOB extends Activity {
	DatePicker datePicker;
	AlertDialog.Builder alertDialogBuilder;
	String defaultFileName;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.addnewdob);
		
		Button button1 = (Button) findViewById(R.id.button1);
		datePicker = (DatePicker)findViewById(R.id.datePicker1);
		datePicker.setCalendarViewShown(false);
		
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText name = (EditText)findViewById(R.id.editText1);
				
				String plainName = name.getText().toString().trim();
				//TextView textView = (TextView)findViewById(R.id.textView3);
				
				if(!(plainName.equalsIgnoreCase("")))
				{	
					if(plainName.equalsIgnoreCase("csea") || plainName.equalsIgnoreCase("inext"))
					{
						defaultFileName = "inext.txt";						
						if(plainName.equalsIgnoreCase("csea"))
						{
							defaultFileName = "dob.txt";
						}
						
						alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
						alertDialogBuilder.setTitle("Confirmation");
						alertDialogBuilder.setMessage("Are you sure want to delete current data and load default datas of " + plainName + "?");
						alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {	
								try {
//									progressDialog.show();
								DBHelper dbHelper = new DBHelper(getApplicationContext());
								dbHelper.deleteAllRecords();
								
								BufferedReader br;
								DataInputStream in = null;

								if (!Environment.getExternalStorageState().equals(
										Environment.MEDIA_MOUNTED)) {
									// sdcard not found. read from bundle
									AssetManager am = getApplicationContext().getAssets();
									InputStream fstream = am.open(defaultFileName);
									// Get the object of DataInputStream
									in = new DataInputStream(fstream);

									br = new BufferedReader(new InputStreamReader(in));
								} else {
									File sdcard = Environment.getExternalStorageDirectory();
									// Get the text file
									File file = new File(sdcard, defaultFileName);
									if (file.exists()) // check for existence
									{
										br = new BufferedReader(new FileReader(file));
									} else // read from bundle if not exist
									{
										AssetManager am = getApplicationContext().getAssets();
										InputStream fstream = am.open(defaultFileName);
										// Get the object of DataInputStream
										in = new DataInputStream(fstream);
										br = new BufferedReader(new InputStreamReader(in));
									}
								}

								String strLine, n1;
								int d1, m1, y1, yOriginal;
								DateOfBirth dateOfBirth = new DateOfBirth();
								// Read File Line By Line
								while ((strLine = br.readLine()) != null) {
									// Print the content on the console
									System.out.println("in main ac -- " + strLine);
									
									String[] lineComponents = strLine.split(" ");
									n1 = lineComponents[0];
									n1 = n1.replace("_", " ");
									d1 = Integer.parseInt(lineComponents[1]);
									m1 = Integer.parseInt(lineComponents[2]);
									y1 = Integer.parseInt("2000");
									yOriginal = Integer.parseInt(lineComponents[3]);
									
									dateOfBirth.setName(n1);
									dateOfBirth.setDob(d1 + " " + m1 + " " + y1);
									dateOfBirth.setOriginalYear(yOriginal);
									dbHelper.addDOB(dateOfBirth);
				/*					
									for (int i = 0; i < strLine.length(); i++) {
										if (strLine.charAt(i) == ' ') {
											String n1 = strLine.substring(0, i);
											int d1 = Integer.parseInt(strLine.substring(i + 1,
													i + 3));
											int m1 = Integer.parseInt(strLine.substring(i + 4,
													i + 6));
											// System.out.println("date " + d1 + "month " + m1);
											DateOfBirth dateOfBirth = new DateOfBirth();
											dateOfBirth.setname(n1);
											dateOfBirth.setDob(d1 + " " + m1 + " " + "2000");
											dbHelper.addDOB(dateOfBirth);
											break;
										}
									}
				*/
								}
								// Close the input stream
								//progressDialog.dismiss();
								in.close();
							} catch (Exception e) {// Catch exception if any
								System.err.println("Error: " + e.getMessage());
							}								
								
								dialog.cancel();
							}
						  });

						alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						});

						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
						// show it
						alertDialog.show();				
					}
					else
					{
						DBHelper dbHelper = new DBHelper(v.getContext());
						
						int d1 = datePicker.getDayOfMonth();
						int m1 = datePicker.getMonth() + 1;
						int y1 = Integer.parseInt("2000");
						int yOriginal = datePicker.getYear();
						DateOfBirth dateOfBirth = new DateOfBirth();
						dateOfBirth.setName(plainName);
						System.out.println(d1 + " " + m1 + " " + y1);
						dateOfBirth.setDob(d1 + " " + m1 + " " + y1);
						dateOfBirth.setOriginalYear(yOriginal);
						dbHelper.addDOB(dateOfBirth);
						
						alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
						alertDialogBuilder.setTitle("Successfull");
						alertDialogBuilder.setMessage(plainName + " Added Successfully");
						alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						  });
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
						// show it
						alertDialog.show();
						
						//textView.setText(plainName + " - Successfully added");
						name.setText("");
						}
				}
				else
				{
					//textView.setText("Please enter a name");
					alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
					alertDialogBuilder.setTitle("Error!!!");
					alertDialogBuilder.setMessage("Please enter Name");
					alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					  });
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();

				}
			}
		}); 
	}
	public void onBackPressed() {
		//http://android-developers.blogspot.in/2009/12/back-and-other-hard-keys-three-stories.html
	ThirdTab.thirdTab.back();  
	return;  
	}
}
