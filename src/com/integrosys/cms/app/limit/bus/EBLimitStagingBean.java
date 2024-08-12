/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitStagingBean.java,v 1.5 2003/08/26 08:01:46 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.EBCoBorrowerDetailsStagingLocalHome;

/**
 * This entity bean represents the persistence for Limit.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/26 08:01:46 $ Tag: $Name: $
 */
public abstract class EBLimitStagingBean extends EBLimitBean {
	/**
	 * Default Constructor
	 */
	public EBLimitStagingBean() {
	}

	// ************************ BeanController Methods **************
	/**
	 * Method to get EB Local Home for EBLimitSysXRef
	 * 
	 * @return EBLimitSysXRefLocalHome
	 * @throws LimitException on errors
	 */
	protected EBLimitSysXRefLocalHome getEBLocalHomeLimitSysXRef() throws LimitException {
		EBLimitSysXRefLocalHome home = (EBLimitSysXRefLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_SYS_REF_LOCAL_JNDI_STAGING, EBLimitSysXRefLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitSysXRefLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBLimitCovenant
	 * 
	 * @return EBLimitCovenantLocalHome
	 * @throws LimitException on errors
	 */
	protected EBLimitCovenantLocalHome getEBLocalHomeLimitCovenant() throws LimitException {
		EBLimitCovenantLocalHome home = (EBLimitCovenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_COVENANT_LOCAL_JNDI_STAGING, EBLimitCovenantLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitCovenantLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBCollateralAllocation
	 * 
	 * @return EBCollateralAllocationLocalHome
	 * @throws LimitException on errors
	 */
	protected EBCollateralAllocationLocalHome getEBLocalHomeCollateralAllocation() throws LimitException {
		EBCollateralAllocationLocalHome home = (EBCollateralAllocationLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COLLATERAL_ALLOCATION_LOCAL_JNDI_STAGING, EBCollateralAllocationLocalHome.class
						.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCollateralAllocationLocalHome is null!");
		}
	}
	
	protected EBFacilityCoBorrowerDetailsStagingLocalHome getEBFacilityCoBorrowerDetailsLocalHome() throws CustomerException {
		EBFacilityCoBorrowerDetailsStagingLocalHome home = (EBFacilityCoBorrowerDetailsStagingLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FACILITY_CO_BORROWER_DETAILS_STAGING_JNDI, EBFacilityCoBorrowerDetailsStagingLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBFacilityCoBorrowerDetailsStagingLocalHome is null!");
		}
	}
}