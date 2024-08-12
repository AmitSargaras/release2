package com.integrosys.cms.app.contractfinancing.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: Kien Leong Date: Mar 27, 2007 Time: 5:08:21
 * PM To change this template use File | Settings | File Templates.
 */
public abstract class EBStagingContractFinancingBean extends EBContractFinancingBean {

	protected EBContractFacilityTypeLocalHome getEBContractFacilityTypeLocalHome() throws ContractFinancingException {
		EBContractFacilityTypeLocalHome home = (EBContractFacilityTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CF_FACILITY_TYPE_LOCAL_JNDI, EBContractFacilityTypeLocalHome.class
						.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBContractFacilityTypeLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Advance EB
	 */
	protected EBAdvanceLocalHome getEBAdvanceLocalHome() throws ContractFinancingException {
		EBAdvanceLocalHome home = (EBAdvanceLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CF_ADVANCE_LOCAL_JNDI, EBAdvanceLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBAdvanceLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the TNC EB
	 */
	protected EBTNCLocalHome getEBTNCLocalHome() throws ContractFinancingException {
		EBTNCLocalHome home = (EBTNCLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CF_TNC_LOCAL_JNDI, EBTNCLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBTNCLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the FDR EB
	 */
	protected EBFDRLocalHome getEBFDRLocalHome() throws ContractFinancingException {
		EBFDRLocalHome home = (EBFDRLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CF_FDR_LOCAL_JNDI, EBFDRLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBFDRLocalHome is null!");
	}
}
