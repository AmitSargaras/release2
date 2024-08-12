/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBCreditRiskParamProxyManagerHome
 *
 * Created on 11:12:24 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 11:12:24 AM
 */
public interface SBCreditRiskParamProxyManagerHome extends EJBHome {
	public SBCreditRiskParamProxyManager create() throws CreateException, RemoteException;
}
