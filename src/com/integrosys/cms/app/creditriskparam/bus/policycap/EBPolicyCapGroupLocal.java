/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Entity Local Interface of PolicyCapGroup Bean
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public interface EBPolicyCapGroupLocal extends EJBLocalObject {

	/**
	 * Retrieve an instance of a policy cap group
	 * @return IPolicyCapGroup - the object encapsulating the policy cap info
	 * @throws PolicyCapException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPolicyCapGroup getValue() throws PolicyCapException;

	/**
	 * Set the policy cap object
	 * @param policyCapGroup - IPolicyCapGroup
	 * @throws PolicyCapException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException on remote errors
	 */
	public void setValue(IPolicyCapGroup policyCapGroup) throws PolicyCapException, ConcurrentUpdateException;

}
