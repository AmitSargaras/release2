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
public interface EBDisbursementDetail extends EJBObject {

	/**
	 * Return the Disbursement Detail ID of the Disbursement
	 * @return long - the Disbursement Detail ID
	 */
	public long getDisburseDetailID() throws RemoteException;

	/**
	 * Return the common reference of the Disbursement Detail
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the Disbursement Detail information.
	 * @return IDisbursementDetail
	 */
	public IDisbursementDetail getValue() throws RemoteException;

	/**
	 * Sets the IDisbursement object.
	 */
	public void setValue(IDisbursementDetail value) throws RemoteException;
}
