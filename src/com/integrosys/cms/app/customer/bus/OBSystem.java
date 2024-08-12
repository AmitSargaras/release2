/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBSystem implements ISystem {
	private long _systemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
	private long customerId;

	private String system;
	
	private String systemCustomerId;
	
	private long LEID;
	/**
	 * Default Constructor
	 */
	public OBSystem() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBSystem(ISystem value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	

	public long getSystemID() {
		return _systemID;
	}

	public void setSystemID(long systemID) {
		_systemID = systemID;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}


	public String getSystemCustomerId() {
		return systemCustomerId;
	}

	public void setSystemCustomerId(String systemCustomerId) {
		this.systemCustomerId = systemCustomerId;
	}

	public long getLEID() {
		return LEID;
	}

	
	public void setLEID(long LEID) {
		this.LEID = LEID;
		
	}
	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}