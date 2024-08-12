/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitProfileStagingBean.java,v 1.2 2003/07/10 02:07:52 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.EBCMSCustomerUdfLocalHome;

/**
 * This entity bean represents the persistence for Limit Profile.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/10 02:07:52 $ Tag: $Name: $
 */
public abstract class EBLimitProfileStagingBean extends EBLimitProfileBean {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBLimitProfileStagingBean() {
	}

	/**
	 * Method to retrieve limits Not imlemented. Returns null.
	 * 
	 * @return ILimit[]
	 * @throws LimitException on errors
	 */
	protected ILimit[] retrieveLimits() throws LimitException {
		return null;
	}

	public String getMigratedInd() { return null; }
	
	public void setMigratedInd(String migratedInd) { return; }
	
	protected EBTradingAgreementLocalHome getEBLocalHomeTradingAgreement() throws LimitException {
		EBTradingAgreementLocalHome home = (EBTradingAgreementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_TRADING_AGREEMENT_LOCAL_STAGING_JNDI, EBTradingAgreementLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBTradingAgreementLocalHome for staging is null!");
		}
	}
	
	protected EBLimitProfileUdfLocalHome getEBLimitProfileUdfLocalUdfInfo() throws LimitException {
		EBLimitProfileUdfLocalHome home = (EBLimitProfileUdfLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_LIMIT_PROFILE_UDF_LOCAL_JNDI_STAGING, EBLimitProfileUdfLocalHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitProfileUdfLocalHome is null!");
		}
	}

}