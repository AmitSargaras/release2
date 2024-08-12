package com.integrosys.cms.ui.custrelationship;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Customer Relationship Utility Class 
 * @author siew kheat
 *
 */
public class CustRelUtils {

	/**
	 * Compute Age based on the date of birth
	 * @param dobDate Date of Birth
	 * @return age 
	 */
	public static int computeAge(Date dobDate) {
		
		if (dobDate == null)
			return -1;
		
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.setTime(dobDate);
		
	    // Create a calendar object with today's date
	    Calendar today = Calendar.getInstance();
	    
	    // Get age based on year
	    int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
	    
	    // Add the tentative age to the date of birth to get this year's birthday
	    dateOfBirth.add(Calendar.YEAR, age);
	    
	    // If this year's birthday has not happened yet, subtract one from age
	    if (today.before(dateOfBirth)) {
	        age--;
	    }
	    
	    return age;

	}
	
	/**
	 * Remove Id from collection of int
	 * @param shareHolderId
	 * @param entRelValues
	 * @return Collection which has its element shareholder matched id removed
	 */
	public static Collection removeIntFromCollection(int value, Collection c) {
		
		if (c == null || c.size() == 0)
			return c;
		
		Iterator iterator = c.iterator();
		while (iterator.hasNext()) {
			String entityRelStr = (String)iterator.next();
			int entityRelInt = (entityRelStr != null) ? Integer.parseInt(entityRelStr) :
				0;
			
			if (entityRelInt  == value)
				iterator.remove();
		}
		
		return c;
	}
	
	/**
	 * Remove a String from collection of String
	 * @param shareHolderLabel
	 * @param entRelValues
	 * @return
	 */
	public static Collection removeStringFromCollection(String value, Collection c) {
		
		if (c == null || c.size() == 0)
			return c;
		
		Iterator iterator = c.iterator();
		while (iterator.hasNext()) {
			String entityRelStr = (String)iterator.next();
			
			if (entityRelStr.equalsIgnoreCase(value))
				iterator.remove();
		}
		
		return c;
	}
	
	
	/**
	 * format Double object
	 * @param format
	 * @param value
	 * @return
	 */
	public static String formatDouble(String format, Double value) {
		if (value == null)
			return null;
		
		DecimalFormat df = new DecimalFormat(format);
		String returnValue = df.format(value.doubleValue());
		
		return returnValue;
	}
	
	
	/**
	public static void main(String[] args) throws Exception {
		
		Collection entRelLabels = new ArrayList();
		entRelLabels.add("1");
		entRelLabels.add("2");
		entRelLabels.add("3");
		entRelLabels.add("4");
		entRelLabels = CustRelUtils.removeShareHolder("2", entRelLabels);
		System.out.println("outside entRelLabels " + entRelLabels);
		
	}
	**/
}
