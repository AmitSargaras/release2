package com.integrosys.cms.app.bridgingloan.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBBuildUp extends EJBObject {

	/**
	 * Return the BuildUp ID of the BuildUp
	 * @return long - the BuildUp ID
	 */
	public long getBuildUpID() throws RemoteException;

	/**
	 * Return the common reference of the BuildUp
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the BuildUp information.
	 * @return IBuildUp
	 */
	public IBuildUp getValue() throws BridgingLoanException, RemoteException;

	/**
	 * Sets the IBuildUp object.
	 * @param value of type IBuildUp
	 * @throws BridgingLoanException
	 */
	public void setValue(IBuildUp value) throws BridgingLoanException, RemoteException;
}