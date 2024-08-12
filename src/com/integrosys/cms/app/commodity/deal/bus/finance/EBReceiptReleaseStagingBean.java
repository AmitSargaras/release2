/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBReceiptReleaseStagingBean.java,v 1.1 2004/09/07 07:36:30 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging release details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/07 07:36:30 $ Tag: $Name: $
 */
public abstract class EBReceiptReleaseStagingBean extends EBReceiptReleaseBean {
	/**
	 * Get staging released warehouse receipt local home.
	 * 
	 * @return EBSettleWarehouseReceiptLocalHome
	 */
	protected EBSettleWarehouseReceiptLocalHome getEBSettleWarehouseReceiptLocalHome() {
		EBSettleWarehouseReceiptLocalHome ejbHome = (EBSettleWarehouseReceiptLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SETTLE_WAREHOUSE_STAGING_LOCAL_JNDI, EBSettleWarehouseReceiptLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSettleWarehouseReceiptStagingLocalHome is Null!");
		}

		return ejbHome;
	}
}