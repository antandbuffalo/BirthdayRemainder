package com.balaji.birthdayactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDetails extends Activity {
	AlertDialog.Builder alertDialogBuilder;
	DBHelper dbHelper;
	DateOfBirth dateOfBirth;
	DatePicker datePicker;
	EditText name;
	int id;
	Context context;
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);    
	setContentView(R.layout.editdetails);
	
	context = this;
	//alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
	
	Bundle bundle = getIntent().getExtras();
	id = bundle.getInt("Id");
	
	dbHelper = new DBHelper(this);
	dateOfBirth = dbHelper.getDateOfBirthForId(id);
	
	name = (EditText)findViewById(R.id.editName);
	name.setText(dateOfBirth.getName());
	
	datePicker = (DatePicker)findViewById(R.id.editDatePicker);
	datePicker.setCalendarViewShown(false);
	datePicker.updateDate(dateOfBirth.getOriginalYear(), dateOfBirth.getDobDate().getMonth(), dateOfBirth.getDobDate().getDate());

	Button updateButton = (Button)findViewById(R.id.updateButton);
	updateButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			
			name = (EditText)findViewById(R.id.editName);
			
			String plainName = name.getText().toString().trim();
			
			if(!(plainName.equalsIgnoreCase("")))
			{	
				int d1 = datePicker.getDayOfMonth();
				int m1 = datePicker.getMonth() + 1;
				int y1 = Integer.parseInt("2000");
				int yOriginal = datePicker.getYear();
				dateOfBirth.setName(plainName);
				System.out.println(d1 + " " + m1 + " " + y1);
				dateOfBirth.setDob(d1 + " " + m1 + " " + y1);
				dateOfBirth.setOriginalYear(yOriginal);
				dbHelper.updateDOB(dateOfBirth);
				Toast toast = Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT);
				toast.show();
				finish();
			}
			else
			{
				Toast toast = Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	});
	
	Button deleteButton = (Button)findViewById(R.id.deleteButton);
	deleteButton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle("Confirm Deletion");
			alertDialogBuilder
					.setMessage("Are you sure want to delete this?");
			alertDialogBuilder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int idLocal) {
							
							if(dbHelper.deleteRecordForTheId(id))
							{
								Toast toast = Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_SHORT);
								toast.show();
								Intent intent = new Intent();
								intent.putExtra("isDelete","yes");
								setResult(RESULT_OK, intent);        
								finish();
							}
							else
							{
								Toast toast = Toast.makeText(getApplicationContext(), "Error While Deletion", Toast.LENGTH_SHORT);
								toast.show();				
							}
							
							dialog.cancel();
						}
					});

			alertDialogBuilder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int idLocal) {
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
	
	}
}
