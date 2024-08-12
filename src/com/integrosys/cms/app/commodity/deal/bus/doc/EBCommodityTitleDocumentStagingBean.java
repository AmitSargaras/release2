/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBCommodityTitleDocumentStagingBean.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging Commodity Title Document entity.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public abstract class EBCommodityTitleDocumentStagingBean extends EBCommodityTitleDocumentBean {
	/**
	 * Get warehouse receipt local home.
	 * 
	 * @return EBWarehouseReceiptLocalHome
	 */
	protected EBWarehouseReceiptLocalHome getEBWarehouseReceiptLocalHome() {
		EBWarehouseReceiptLocalHome ejbHome = (EBWarehouseReceiptLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_WAREHOUSE_RECEIPT_STAGING_LOCAL_JNDI, EBWarehouseReceiptLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBWarehouseReceiptStagingLocalHome is Null!");
		}

		return ejbHome;
	}
}