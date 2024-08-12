package com.integrosys.cms.app.commoncode.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface EBCommonCodeType extends EJBObject {
	/**
	 * Retrieve an instance of a common code type
	 * @return ICommonCodeType - the object encapsulating the common code type
	 * @throws CommonCodeTypeException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICommonCodeType getValue() throws CommonCodeTypeException, RemoteException;

	/**
	 * Set the common code type
	 * @param anICommonCodeType of ICommonCodeType type
	 * @throws CommonCodeTypeException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICommonCodeType anICommonCodeType) throws CommonCodeTypeException, ConcurrentUpdateException,
			RemoteException;

}
