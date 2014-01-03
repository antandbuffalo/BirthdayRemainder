package com.antandbuffalo.birthdayactivity;

import java.util.Comparator;

public class DateComparator implements Comparator<DateOfBirth> {

	public int compare(DateOfBirth o1, DateOfBirth o2) {
        return o1.getDobDate().compareTo(o2.getDobDate());
    }
	
}
