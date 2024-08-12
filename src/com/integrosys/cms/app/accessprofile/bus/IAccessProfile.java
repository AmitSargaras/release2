/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/IAccessProfile.java,v 1.6 2005/10/27 06:28:50 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.io.Serializable;

/**
 * Represents a access profile record.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/27 06:28:50 $ Tag: $Name: $
 */
public interface IAccessProfile extends Serializable {
	/**
	 * Get the ID of the function access.
	 * 
	 * @return long
	 */
	public long getId();

	/**
	 * Set the ID of the function access.
	 * 
	 * @param id function access primary key
	 */
	public void setId(long id);

	/**
	 * Get the Action of function access. It is the same syntax as the action
	 * path string in struts-config.xml.
	 * 
	 * @return Action String
	 */
	public String getAction();

	/**
	 * Set the Action of function access. It must be the same syntax as the
	 * action path string in struts-config.xml
	 * 
	 * @param action Action string
	 */
	public void setAction(String action);

	/**
	 * Get the Event part of function access. It is the same syntax as Integro
	 * UI framework event handling mechanism.
	 * 
	 * @return Event String
	 */
	public String getEvent();

	/**
	 * Set the Event part of function access. It must be the same syntax as
	 * Integro UI framework event handling mechanism.
	 * 
	 * @param event Event string
	 */
	public void setEvent(String event);

	/**
	 * Get the user Role of function access.
	 * 
	 * @return Role String.
	 */
	public long getRoleType();

	/**
	 * Set the user Role of function access.
	 * 
	 * @param role Role string
	 */
	public void setRoleType(long role);
}
