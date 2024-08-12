/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/SBCommodityMaintenanceProxyHome.java,v 1.2 2004/06/04 04:53:42 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the checklist bus
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:42 $ Tag: $Name: $
 */
public interface SBCommodityMaintenanceProxyHome extends EJBHome {
	public SBCommodityMaintenanceProxy create() throws CreateException, RemoteException;
}