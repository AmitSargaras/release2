package com.integrosys.cms.app.commoncode.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface EBCommonCodeTypeHome extends EJBHome {
	/**
	 * Create a CommonCodeType
	 * @param anICommonCodeType of ICommonCodeType type
	 * @return EBCommonCodeType - the remote handler for the created ddn
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCommonCodeType create(ICommonCodeType anICommonCodeType) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the CommonCodeType ID
	 * @param aPK of Long type
	 * @return EBCommonCodeType - the remote handler for the CommonCodeType that
	 *         has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCommonCodeType findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by primary Key, the CommonCodeType category code
	 * @param categoryCode of String type
	 * @return EBCommonCodeType - the remote handler for the CommonCodeType that
	 *         has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCommonCodeType findByCategoryCode(String categoryCode) throws FinderException, RemoteException;

	/**
	 * Find by category Type
	 * @param categoryCode
	 * @return Collection - a collection of CommonCodeType
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public Collection findByCategoryListByType(int categoryCode) throws FinderException, RemoteException;

}
