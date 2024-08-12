package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 25, 2007 Time: 11:40:20 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EBStagingBridgingLoanBean extends EBBridgingLoanBean {
	/**
	 * Method to get EB Local Home for the Property Type
	 */
	protected EBPropertyTypeLocalHome getEBPropertyTypeLocalHome() throws BridgingLoanException {
		EBPropertyTypeLocalHome home = (EBPropertyTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_PROPERTY_TYPE_LOCAL_JNDI, EBPropertyTypeLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBPropertyTypeLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Project Schedule
	 */
	protected EBProjectScheduleLocalHome getEBProjectScheduleLocalHome() throws BridgingLoanException {
		EBProjectScheduleLocalHome home = (EBProjectScheduleLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_PROJECT_SCHEDULE_LOCAL_JNDI, EBProjectScheduleLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBProjectScheduleLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Disbursement
	 */
	protected EBDisbursementLocalHome getEBDisbursementLocalHome() throws BridgingLoanException {
		EBDisbursementLocalHome home = (EBDisbursementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_DISBURSEMENT_LOCAL_JNDI, EBDisbursementLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBDisbursementLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Settlement
	 */
	protected EBSettlementLocalHome getEBSettlementLocalHome() throws BridgingLoanException {
		EBSettlementLocalHome home = (EBSettlementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_SETTLEMENT_LOCAL_JNDI, EBSettlementLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBSettlementLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Build Up
	 */
	protected EBBuildUpLocalHome getEBBuildUpLocalHome() throws BridgingLoanException {
		EBBuildUpLocalHome home = (EBBuildUpLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_BUILDUP_LOCAL_JNDI, EBBuildUpLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBBuildUpLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the FDR
	 */
	protected EBFDRLocalHome getEBFDRLocalHome() throws BridgingLoanException {
		EBFDRLocalHome home = (EBFDRLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_FDR_LOCAL_JNDI, EBFDRLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBFDRLocalHome is null!");
	}
}