/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/EBCommodityDeal.java,v 1.3 2004/07/12 09:51:32 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Entity bean remote interface to EBCommodityDealBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/12 09:51:32 $ Tag: $Name: $
 */
public interface EBCommodityDeal extends EJBObject {
	/**
	 * Get the commodity deal business object.
	 * 
	 * @return commodity deal
	 * @throws CommodityDealException on error getting the deal value
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDeal getValue() throws CommodityDealException, RemoteException;

	/**
	 * Set the commodity deal business object.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @throws VersionMismatchException if the deal version is different from
	 *         backend
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICommodityDeal deal) throws VersionMismatchException, RemoteException;

	/**
	 * Set the commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @throws VersionMismatchException if the deal version is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setDealStatus(ICommodityDeal deal) throws VersionMismatchException, RemoteException;
}