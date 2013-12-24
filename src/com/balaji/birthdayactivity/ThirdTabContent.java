package com.balaji.birthdayactivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdTabContent extends Activity {
	// http://www.vogella.com/articles/AndroidListView/article.html
	// TextView statusTextView;
	AlertDialog.Builder alertDialogBuilder;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);		
		setContentView(R.layout.thirdtabcontent);

		alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);

		// statusTextView = (TextView)findViewById(R.id.statusText);
		Button addNew = (Button) findViewById(R.id.addNewDob);
		Button loadFromFile = (Button) findViewById(R.id.loadFromFile);
		Button loadToFile = (Button) findViewById(R.id.loadToFile);
		Button deleteAllButon = (Button) findViewById(R.id.deleteAllButton);
		Button infoButton = (Button) findViewById(R.id.infoButton);

		addNew.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), AddNewDOB.class);

				View view = ThirdTab.thirdTab
						.getLocalActivityManager()
						.startActivity("AddNewDOB",
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView();
				// Again, replace the view
				ThirdTab.thirdTab.replaceView(view);
			}
		});

		loadFromFile.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// statusTextView.setText("Please wait while loading data");

				alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
				alertDialogBuilder.setTitle("Confirm");
				alertDialogBuilder
						.setMessage("This will replace the current data with the data available in the file \"sdcard/dob.txt\". Are you sure want to continue?");
				alertDialogBuilder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Utility.loadFromFile(ThirdTab.thirdTab);
								// statusTextView.setText("Data loading successfull");
								Toast toast = Toast.makeText(
										getApplicationContext(),
										"Data loading successfull",
										Toast.LENGTH_SHORT);
								toast.show();
								dialog.cancel();
							}
						});

				alertDialogBuilder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// statusTextView.setText("Status");
								dialog.cancel();
							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
			}
		});

		loadToFile.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// sdcard not found. read from bundle
					System.out
							.println("sdcard not found. Not able to write file");
					alertDialogBuilder = new AlertDialog.Builder(
							ThirdTab.thirdTab);
					alertDialogBuilder.setTitle("Error!!!");
					alertDialogBuilder
							.setMessage("sdcard not found. Not able to create backup file");
					alertDialogBuilder.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();
				} else {
					alertDialogBuilder = new AlertDialog.Builder(
							ThirdTab.thirdTab);
					alertDialogBuilder.setTitle("Confirm");
					alertDialogBuilder
							.setMessage("This will delete the old \"sdcard/dob.txt\" and create new file. Are you sure want to continue?");
					alertDialogBuilder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// sdcard found
									try {
										File sdcard = Environment
												.getExternalStorageDirectory();
										// Get the text file
										File myFile = new File(sdcard,
												"dob.txt");
										myFile.createNewFile();
										FileOutputStream fOut = new FileOutputStream(
												myFile);
										OutputStreamWriter myOutWriter = new OutputStreamWriter(
												fOut);
										DBHelper dbHelper = new DBHelper(
												getApplicationContext());
										List<DateOfBirth> allDOBs = dbHelper
												.getAllDateOfBirths(null);
										for (DateOfBirth dob : allDOBs) {
											String nameWithChar = dob.getName();
											nameWithChar = nameWithChar
													.replace(" ", "_");
											String originalDOB = dob
													.getDobDate().getDate()
													+ " "
													+ (dob.getDobDate()
															.getMonth() + 1)
													+ " "
													+ dob.getOriginalYear();
											myOutWriter.append(nameWithChar
													+ " " + originalDOB);
											myOutWriter.append("\n");
										}

										myOutWriter.close();
										fOut.close();
										// statusTextView.setText("Data backup successfull");
										Toast toast = Toast.makeText(
												getApplicationContext(),
												"Data backup successfull",
												Toast.LENGTH_SHORT);
										toast.show();

									} catch (Exception e) {
										System.out.println(e);
									}
								}
							});

					alertDialogBuilder.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
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

		deleteAllButon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
				alertDialogBuilder.setTitle("Confirm");
				alertDialogBuilder
						.setMessage("This action will delete all the data. Are you sure want to continue?");
				alertDialogBuilder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								DBHelper dbHelper = new DBHelper(
										getApplicationContext());
								dbHelper.deleteAllRecords();
								dialog.cancel();
								Toast toast = Toast.makeText(
										getApplicationContext(),
										"All data deleted successfully",
										Toast.LENGTH_SHORT);
								toast.show();
							}
						});

				alertDialogBuilder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// statusTextView.setText("Status");
								dialog.cancel();
							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();

			}
		});
		infoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				alertDialogBuilder = new AlertDialog.Builder(ThirdTab.thirdTab);
//				alertDialogBuilder.setTitle("Contact Info");
//				alertDialogBuilder
//						.setMessage("Developed by Ant and Buffalo. For any queries email to antandbuffalo@gmail.com");
//				alertDialogBuilder.setPositiveButton("OK", null);
//				// create alert dialog
//				AlertDialog alertDialog = alertDialogBuilder.create();
//				// show it
//				alertDialog.show();
				
				Intent i = new Intent(v.getContext(), ContactInfo.class);

				startActivity(i);
				// Again, replace the view
			}	
		});
	}
}
