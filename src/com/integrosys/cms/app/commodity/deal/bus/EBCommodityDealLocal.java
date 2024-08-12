/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/EBCommodityDealLocal.java,v 1.3 2004/07/12 09:52:28 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Entity bean remote interface to EBCommodityDealBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/12 09:52:28 $ Tag: $Name: $
 */
public interface EBCommodityDealLocal extends EJBLocalObject {
	/**
	 * Get the commodity deal business object.
	 * 
	 * @return commodity deal
	 * @throws CommodityDealException on error getting the deal value
	 */
	public ICommodityDeal getValue() throws CommodityDealException;

	/**
	 * Set the commodity deal business object.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @throws VersionMismatchException if the deal version is different from
	 *         backend
	 */
	public void setValue(ICommodityDeal deal) throws VersionMismatchException;

	/**
	 * Set the commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @throws VersionMismatchException if the deal version is invalid
	 */
	public void setDealStatus(ICommodityDeal deal) throws VersionMismatchException;
}