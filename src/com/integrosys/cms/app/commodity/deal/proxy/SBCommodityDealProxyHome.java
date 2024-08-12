/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/SBCommodityDealProxyHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to the SBCommodityDealProxy session bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface SBCommodityDealProxyHome extends EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error creating the ejb object
	 * @throws RemoteException on error during remote method call
	 */
	public SBCommodityDealProxy create() throws CreateException, RemoteException;
}