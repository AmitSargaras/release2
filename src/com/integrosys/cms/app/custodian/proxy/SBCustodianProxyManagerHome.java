/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/SBCustodianProxyManagerHome.java,v 1.1 2003/06/10 09:02:56 hltan Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the custodian proxy
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/10 09:02:56 $ Tag: $Name: $
 */
public interface SBCustodianProxyManagerHome extends EJBHome {
	public SBCustodianProxyManager create() throws CreateException, RemoteException;
}
