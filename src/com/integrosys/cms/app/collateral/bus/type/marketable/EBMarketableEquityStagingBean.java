/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.util.Collection;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.EBMarketableEquityLineDetailLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for marketable equity staging.
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: 1.00 $
 * @since $Date: 2007/09/19 17:50:00 $ Tag: $Name: $
 */
public abstract class EBMarketableEquityStagingBean extends EBMarketableEquityBean {

	/**
	 * Overriden method to disable the CMR invocation CMR to the similar table
	 * and id is not allowed in websphere
	 */
	public Collection getEquityDetailCMR() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Overriden method to disable CMR invocation CMR to the similar table and
	 * id is not allowed in websphere
	 */
	public void setEquityDetailCMR(Collection EquityDetails) {
		// Do nothing

	}
	
	protected EBMarketableEquityLineDetailLocalHome getEBLocalLineDetail() {
		EBMarketableEquityLineDetailLocalHome home = (EBMarketableEquityLineDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MARKETABLE_EQUITY_LINE_DETAIL_LOCAL_JNDI_STAGING, EBMarketableEquityLineDetailLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new RuntimeException("EBMarketableEquityLineDetailLocalHome is null!");
		}
	}

}
