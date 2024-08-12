package com.integrosys.cms.app.bridgingloan.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public interface SBBridgingLoanProxyManagerHome extends EJBHome {

	public SBBridgingLoanProxyManager create() throws CreateException, RemoteException;
}
