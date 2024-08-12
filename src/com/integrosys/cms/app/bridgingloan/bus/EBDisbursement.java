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
public interface EBDisbursement extends EJBObject {

	/**
	 * Return the Disbursement ID of the Disbursement
	 * @return long - the Disbursement ID
	 */
	public long getDisbursementID() throws RemoteException;

	/**
	 * Return the common reference of the Disbursement
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the Disbursement information.
	 * @return IDisbursement
	 */
	public IDisbursement getValue() throws BridgingLoanException, RemoteException;

	/**
	 * Sets the IDisbursement object.
	 * @param value of type IDisbursement
	 * @throws BridgingLoanException
	 */
	public void setValue(IDisbursement value) throws BridgingLoanException, RemoteException;
}