/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralParameterHome.java,v 1.5 2003/08/25 10:53:41 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Entity bean home interface for security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/25 10:53:41 $ Tag: $Name: $
 */
public interface EBCollateralParameterHome extends EJBHome {
	/**
	 * Creates security parameter.
	 * 
	 * @param colParam of type ICollateralParameter
	 * @return ejb object of security parameter
	 * @throws CreateException on error creating the security parameter
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralParameter create(ICollateralParameter colParam) throws CreateException, RemoteException;

	/**
	 * Finds the security parameter by its primary key.
	 * 
	 * @param pk security parameter's primary key
	 * @return ejb object of security parameter
	 * @throws FinderException on error finding the security parameter
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralParameter findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Finds the security parameter by country code and security subtype code.
	 * 
	 * @param countryCode country ISO code
	 * @param colSubTypeCode security subtype code
	 * @return security parameter
	 * @throws FinderException on error finding the security parameter
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralParameter findByCountryAndSubTypeCode(String countryCode, String colSubTypeCode)
			throws FinderException, RemoteException;

	/**
	 * Search security parameter based on the country code and security type
	 * code.
	 * 
	 * @param countryCode of type String
	 * @param colType of type String
	 * @return a list of security parameters
	 * @throws SearchDAOException on errror searching the security parameter
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter[] searchByCountryAndColType(String countryCode, String colType)
			throws SearchDAOException, RemoteException;

	/**
	 * Search security parameter based on the group id.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws SearchDAOException on error searching the security parameter
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter[] searchByGroupID(long groupID) throws SearchDAOException, RemoteException;

	/**
	 * Finds security parameter by its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBCollateralParameter</code>s
	 */
	public Collection findByGroupID(long groupID) throws FinderException, RemoteException;
}
