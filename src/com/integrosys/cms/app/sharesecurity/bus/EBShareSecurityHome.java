/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBShareSecurityHome extends EJBHome {
	public EBShareSecurity create(IShareSecurity sec) throws CreateException, RemoteException;

	public EBShareSecurity findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	public Collection findByCollateralId(Long colId) throws FinderException, RemoteException;

	public Collection findByColAndSource(Long colId, String sourceId) throws FinderException, RemoteException;

	public Collection findBySecurityIdAndSource(String securityId, String sourceId) throws FinderException,
			RemoteException;
}
