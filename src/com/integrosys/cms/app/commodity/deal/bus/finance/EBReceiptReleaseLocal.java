/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBReceiptReleaseLocal.java,v 1.1 2004/09/07 07:36:30 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBReceiptReleaseBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/07 07:36:30 $ Tag: $Name: $
 */
public interface EBReceiptReleaseLocal extends EJBLocalObject {
	/**
	 * Get the deal release details.
	 * 
	 * @return release details
	 */
	public IReceiptRelease getValue();

	/**
	 * Set the deal release details.
	 * 
	 * @param release is of type IReceiptRelease
	 */
	public void setValue(IReceiptRelease release);

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}