/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/AccessProfileDAOFactory.java,v 1.2 2003/11/04 05:11:27 btchng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

/**
 * Produces the various DAO implementation for the access profile table.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/11/04 05:11:27 $ Tag: $Name: $
 */
public class AccessProfileDAOFactory {

	/**
	 * Gets the DAO for access profile table.
	 * 
	 * @return
	 */
	public static IAccessProfileDAO getAccessProfileDAO() {
		return new AccessProfileDAO();
	}
}
