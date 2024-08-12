package com.integrosys.cms.app.creditriskparam.bus.policycap;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface EBPolicyCapLocal extends EJBLocalObject {

	/**
	 * Retrieve an instance of a policy cap
	 * @return IPolicyCap - the object encapsulating the policy cap info
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCap getValue() throws PolicyCapException;

	/**
	 * Set the policy cap object
	 * @param anIPolicyCap - IPolicyCap
	 * @throws PolicyCapException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IPolicyCap anIPolicyCap) throws PolicyCapException, ConcurrentUpdateException;

}
