/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/IAccessProfileDAO.java,v 1.4 2005/09/28 07:36:19 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.util.Collection;

/**
 * Contains the data access logic for access profile table.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/28 07:36:19 $ Tag: $Name: $
 */
public interface IAccessProfileDAO {

	/**
	 * Gets the list of access profile records that matches on
	 * <code>action</code>, <code>event</code>, <code>roleType</code>. If and
	 * only if any of the input argument is <b><code>null</code></b>, then that
	 * argument <b>will not</b> be used as part of the retrieval condition.
	 * 
	 * @param action
	 * @param event
	 * @param roleType
	 * @return An array of records. Array is empty if no records are found.
	 */
	public IAccessProfile[] getAccessProfiles(String action, String event, long roleType);

	/**
	 * Get all the access function records.
	 * 
	 * @return a List of access function records
	 */
	public Collection getAccessProfileCollection();
	
	public void setUserLastAccessTime(String loginID);
}
