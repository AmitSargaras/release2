/*
 * Created on Jun 22, 2003
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBSecApportionmentHome extends EJBHome {

	public EBSecApportionment create(ISecApportionment apportionment) throws CreateException, RemoteException;

	public EBSecApportionment findByPrimaryKey(Long secApportionmentID) throws FinderException, RemoteException;
}
