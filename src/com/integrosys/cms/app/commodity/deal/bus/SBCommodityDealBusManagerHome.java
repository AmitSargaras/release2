/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/SBCommodityDealBusManagerHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to SBCommodityDealManagerBean session bean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface SBCommodityDealBusManagerHome extends EJBHome {
	/**
	 * Creates commodity deal manager ejb object.
	 * 
	 * @return deal manager session bean
	 * @throws RemoteException on errors during remote method call
	 */
	public SBCommodityDealBusManager create() throws CreateException, RemoteException;
}