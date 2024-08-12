/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging Recovery entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBRecoveryStagingBean extends EBRecoveryBean {

	private static final long serialVersionUID = -443288774453237661L;

	public abstract void setStatus(String status);

	public abstract String getStatus();

	protected EBRecoveryIncomeLocalHome getEBRecoveryIncomeLocalHome() throws LiquidationException {
		EBRecoveryIncomeLocalHome ejbHome = (EBRecoveryIncomeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECOVERY_INCOME_STAGING_LOCAL_JNDI, EBRecoveryIncomeLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryIncomeLocalHome is null!");
		}

		return ejbHome;
	}

	protected boolean isStaging() {
		return true;
	}
}