package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface EBPolicyCap extends EJBObject {

	/**
	 * Retrieve an instance of a policy cap
	 * @return IPolicyCap - the object encapsulating the policy cap info
	 * @throws PolicyCapException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPolicyCap getValue() throws PolicyCapException, RemoteException;

	/**
	 * Set the policy cap object
	 * @param anIPolicyCap - IPolicyCap
	 * @throws PolicyCapException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException on remote errors
	 */
	public void setValue(IPolicyCap anIPolicyCap) throws PolicyCapException, ConcurrentUpdateException, RemoteException;

}
