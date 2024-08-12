/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequestHeader.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that is required for request
 * header
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IRequestHeader extends Serializable {
	public String getRequestTo();

	public String getRequestFrom();

	public String getSignNo();

	public Date getRequestDate();

	public void setRequestTo(String aRequestTo);

	public void setRequestFrom(String aRequestFrom);

	public void setSignNo(String aSignNo);

	public void setRequestDate(Date aRequestDate);
}
