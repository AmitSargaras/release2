/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/SBCheckListProxyManagerHome.java,v 1.1 2003/06/30 09:02:23 hltan Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the checklist proxy
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/30 09:02:23 $ Tag: $Name: $
 */
public interface SBCheckListProxyManagerHome extends EJBHome {
	public SBCheckListProxyManager create() throws CreateException, RemoteException;
}
