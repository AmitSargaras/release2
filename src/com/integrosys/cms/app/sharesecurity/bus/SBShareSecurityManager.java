/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBShareSecurityManager extends EJBObject {
	public IShareSecurity createShareSecurity(IShareSecurity shareSec) throws ShareSecurityException, RemoteException;

	public Collection getShareSecurityForCollateral(Long colId) throws ShareSecurityException, RemoteException;

	public Map getSharedSecNameForCollaterals(List colIdList) throws ShareSecurityException, RemoteException;

	public Collection getSharedSecForColSource(Long colId, String sourceId) throws ShareSecurityException,
			RemoteException;

	public List getSharedSecNameForCollateral(Long colId) throws SearchDAOException, RemoteException;

	public Vector getSharedSecurityValidationResult(OBShareSecurityValidation obShareSecurityValidation)
			throws ShareSecurityValidationException, RemoteException;

	public void createShareSecForGCMS(long secId, String sourceSecId) throws ShareSecurityException, RemoteException;

}
