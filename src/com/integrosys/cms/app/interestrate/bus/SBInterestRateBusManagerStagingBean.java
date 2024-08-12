/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.util.Date;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * The SBInterestRateBusManagerStagingBean acts as the facade to the Entity
 * Beans for interest stage data.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBInterestRateBusManagerStagingBean extends SBInterestRateBusManagerBean {
	/**
	 * Default Constructor
	 */
	public SBInterestRateBusManagerStagingBean() {
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#getInterestRate
	 */
	public IInterestRate[] getInterestRate(String intRateType, Date monthYear) throws InterestRateException {

		try {
			IInterestRateDAO dao = InterestRateDAOFactory.getStagingDAO();
			IInterestRate[] collection = dao.getInterestRateByMonth(intRateType, monthYear);
			return collection;
		}
		catch (SearchDAOException e) {
			throw new InterestRateException("SearchDAOException caught at getInterestRate " + e.toString());
		}
		catch (Exception e) {
			throw new InterestRateException("Exception caught at getInterestRate: " + e.toString());
		}

	}

	/**
	 * helper method to get staging interestrate home interface.
	 * 
	 * @return interestrate home interface
	 * @throws InterestRateException on errors encountered
	 */
	protected EBInterestRateHome getEBInterestRateHome() throws InterestRateException {
		EBInterestRateHome ejbHome = (EBInterestRateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_INT_RATE_STAGING_JNDI, EBInterestRateHome.class.getName());

		if (ejbHome == null) {
			throw new InterestRateException("EBInterestRateHome for staging is null!");
		}

		return ejbHome;
	}

}