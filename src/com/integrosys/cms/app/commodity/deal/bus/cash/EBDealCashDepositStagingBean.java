/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/cash/EBDealCashDepositStagingBean.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.cash;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public abstract class EBDealCashDepositStagingBean extends EBDealCashDepositBean {
	/**
	 * Get staging cash deposit local home.
	 * 
	 * @return EBCashDepositLocalHome
	 */
	protected EBDealCashDepositLocalHome getLocalHome() {
		EBDealCashDepositLocalHome ejbHome = (EBDealCashDepositLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEAL_CASH_DEPOSIT_STAGING_LOCAL_JNDI, EBDealCashDepositLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCashDepositStagingLocalHome is Null!");
		}

		return ejbHome;
	}
}
