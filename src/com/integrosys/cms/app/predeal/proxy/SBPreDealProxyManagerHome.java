/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBPreDealProxyManagerHome
 *
 * Created on 5:34:34 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 5:34:34 PM
 */
public interface SBPreDealProxyManagerHome extends EJBHome {
	public SBPreDealProxyManager create() throws CreateException, RemoteException;
}
