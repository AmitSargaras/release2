/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequestSubject.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;

/**
 * This interface defines the list of attributes that is required for request
 * subject
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IRequestSubject extends Serializable {
	public long getCustomerID();

	public String getCustomerName();

	public String getSubject();

	public void setCustomerID(long aCustomerID);

	public void setCustomerName(String aCustomerName);

	public void setSubject(String aSubject);
}
