/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/SBDataProtection.java,v 1.4 2006/03/17 07:20:31 hshii Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.dataprotection.bus.ICollateralMetaData;
import com.integrosys.cms.app.dataprotection.bus.IDataAccessProfile;

/**
 * Description: Remote interface to Session Bean SBDataProtectionBean
 * 
 * @author $jtan$<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/17 07:20:31 $ Tag: $Name: $
 * 
 */
public interface SBDataProtection extends EJBObject {

	/**
	 * @return a list of ICollateralMetaData
	 * @throws RemoteException - if remote call encounters error
	 */
	public ICollateralMetaData[] getCollateralMetaDataBySubType(String type) throws RemoteException;

	/**
	 * Get a list of Data Access Profile.
	 * 
	 * @param type data access module type
	 * @param subtype data access module subtype
	 * @param roleType user team type membership id
	 * @return an array of IDataAccessProfile objects
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public IDataAccessProfile[] getDataAccessProfile(String type, String subtype, long roleType) throws RemoteException;

	/**
	 * Check if given type, subtype, country and organisation code can access by
	 * multiple roles
	 * 
	 * @param type module type
	 * @param subtype module subtype
	 * @param ctryCode access country code
	 * @param orgCode access organisation code
	 * @return true if data access by multiple roles, otherwise false
	 */
	public boolean isMultipleRoleAccessByCtryOrg(String type, String subtype, String ctryCode, String orgCode)
			throws RemoteException;

}
