/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the IInterestRateManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateBusManagerStagingImpl extends InterestRateBusManagerImpl {
	/**
	 * Default constructor.
	 */
	public InterestRateBusManagerStagingImpl() {
		super();
	}

	/**
	 * helper method to get an ejb object to interestrate business manager
	 * session bean.
	 * 
	 * @return interestrate manager ejb object
	 * @throws InterestRateException on errors encountered
	 */
	protected SBInterestRateBusManager getBusManager() throws InterestRateException {
		SBInterestRateBusManager theEjb = (SBInterestRateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_INT_RATE_MGR_STAGING_JNDI, SBInterestRateBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new InterestRateException("SBInterestRateBusManager for Staging is null!");
		}

		return theEjb;
	}
}