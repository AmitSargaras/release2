/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface ICriFac extends java.io.Serializable {
	
	public long getCriFacID();

	public void setCriFacID(long criFacID);

	public long getLEID();
		
	public void setLEID(long value);

	public  String getFacilityFor();

	public  void setFacilityFor(String facilityFor);
	
	public  String getFacilityName();

	public  void setFacilityName(String facilityName);

	public  String getFacilityAmount();

	public  void setFacilityAmount(String facilityAmount);	
	
	public String getLineNo();

	public void setLineNo(String lineNo);	
	
	public String getSerialNo();

	public void setSerialNo(String serialNo);	
	
	public String getEstateType();

	public void setEstateType(String estateType);	
	
	public String getCommRealEstateType();

	public void setCommRealEstateType(String commRealEstateType);	
	
	public String getPrioritySector();

	public void setPrioritySector(String prioritySector);	

}