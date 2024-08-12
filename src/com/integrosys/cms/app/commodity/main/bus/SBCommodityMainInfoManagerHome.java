/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/SBCommodityMainInfoManagerHome.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the checklist bus
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:52:40 $ Tag: $Name: $
 */
public interface SBCommodityMainInfoManagerHome extends EJBHome {
	public SBCommodityMainInfoManager create() throws CreateException, RemoteException;
}