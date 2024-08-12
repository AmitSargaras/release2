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
public interface ISystem extends java.io.Serializable {
	
	public long getSystemID();

	public void setSystemID(long systemID);

	public String getSystem();

	public void setSystem(String system);

	public String getSystemCustomerId();

	public void setSystemCustomerId(String systemCustomerId); 
	
	  public long getLEID();
		
		public void setLEID(long value);

}