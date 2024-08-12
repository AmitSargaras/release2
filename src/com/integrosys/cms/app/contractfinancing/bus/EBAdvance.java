package com.integrosys.cms.app.contractfinancing.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBAdvance extends EJBObject {

	/**
	 * Return the Advance ID of the Payment Object
	 * @return long - the Advance ID
	 */
	public long getAdvanceID() throws RemoteException;

	/**
	 * Return the common reference of the Advance Object
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the Advance information.
	 * @return IAdvance
	 */
	public IAdvance getValue() throws ContractFinancingException, RemoteException;

}
