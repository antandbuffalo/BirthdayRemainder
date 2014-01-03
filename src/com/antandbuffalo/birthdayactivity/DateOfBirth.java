package com.antandbuffalo.birthdayactivity;

import java.util.Date;


public class DateOfBirth {
	private int id;
	private String name;
	private String dob;
	private Date dobDate;
	private int originalYear;
	private String displayName;
	private String age;
    
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String Name) {
    	this.name = Name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String Dob) {
        this.dob = Dob;
    }
    
    public int getOriginalYear() {
        return originalYear;
    }

    public void setOriginalYear(int OriginalYear) {
        this.originalYear = OriginalYear;
    }
    
    public Date getDobDate() {
        return dobDate;
    }

    public void setDobDate(Date DobDate) {
        this.dobDate = DobDate;
    }
    
    public String getDisplayName() {
        return displayName;
    }    
    
    public void setDisplayName(String DisplayName) {
    	this.displayName = DisplayName;
    }
    
    public String getAge() {
        return age;
    }    
    
    public void setAge(String Age) {
    	this.age = Age;
    }    
}
