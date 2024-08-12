/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanAgencyStagingBean.java,v 1.6 2004/07/15 06:27:32 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/15 06:27:32 $ Tag: $Name: $
 */

public abstract class EBLoanAgencyStagingBean extends EBLoanAgencyBean {
	public EBLoanAgencyStagingBean() {
		isStaging = true;
	}

	protected EBLoanLimitLocalHome _getLimitLocalHome() {
		EBLoanLimitLocalHome ejbHome = (EBLoanLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LOAN_LIMIT_STAGING_LOCAL_JNDI, EBLoanLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLoanLimitStagingLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBBorrowerLocalHome _getBorrowerLocalHome() {
		EBBorrowerLocalHome ejbHome = (EBBorrowerLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BORROWER_STAGING_LOCAL_JNDI, EBBorrowerLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBBorrowerLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBGuarantorLocalHome _getGuarantorLocalHome() {
		EBGuarantorLocalHome ejbHome = (EBGuarantorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_GUARANTOR_STAGING_LOCAL_JNDI, EBGuarantorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBGuarantorLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBParticipantLocalHome _getParticipantLocalHome() {
		EBParticipantLocalHome ejbHome = (EBParticipantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PARTICIPANT_STAGING_LOCAL_JNDI, EBParticipantLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBParticipantLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBSubLimitLocalHome _getSubLimitLocalHome() {
		EBSubLimitLocalHome ejbHome = (EBSubLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SUBLIMIT_STAGING_LOCAL_JNDI, EBSubLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSubLimitLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBLoanScheduleLocalHome _getLoanScheduleLocalHome() {
		EBLoanScheduleLocalHome ejbHome = (EBLoanScheduleLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LOANSCHEDULE_STAGING_LOCAL_JNDI, EBLoanScheduleLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLoanScheduleLocalHome is Null!");
		}

		return ejbHome;
	}

}
