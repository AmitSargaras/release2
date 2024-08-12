/*
 * Created on Jun 22, 2003
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBSecApportionment extends EJBObject {

	public ISecApportionment getValue() throws RemoteException;

	public void setValue(ISecApportionment value) throws RemoteException;

}
