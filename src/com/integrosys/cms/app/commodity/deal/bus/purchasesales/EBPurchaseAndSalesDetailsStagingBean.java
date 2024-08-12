/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/EBPurchaseAndSalesDetailsStagingBean.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public abstract class EBPurchaseAndSalesDetailsStagingBean extends EBPurchaseAndSalesDetailsBean {
	/**
	 * Get purchase and sales details local home.
	 * 
	 * @return EBPurchaseAndSalesDetailsLocalHome
	 */
	protected EBPurchaseAndSalesDetailsLocalHome getLocalHome() {
		EBPurchaseAndSalesDetailsLocalHome ejbHome = (EBPurchaseAndSalesDetailsLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_PURCHASE_SALES_STAGING_LOCAL_JNDI,
						EBPurchaseAndSalesDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPurchaseAndSalesDetailsStagingLocalHome is Null!");
		}

		return ejbHome;
	}
}
