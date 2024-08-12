/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for trading agreement staging
 * bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBTradingAgreementStagingBean extends EBTradingAgreementBean {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBTradingAgreementStagingBean() {
	}

	protected EBThresholdRatingLocalHome getEBLocalHomeThresholdRating() throws LimitException {
		DefaultLogger.debug(this, "getEBLocalHomeThresholdRating for staging");
		EBThresholdRatingLocalHome home = (EBThresholdRatingLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_THRESHOLD_RATING_LOCAL_STAGING_JNDI, EBThresholdRatingLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBThresholdRatingLocalHome for staging is null!");
		}
	}

}