package com.integrosys.cms.app.contractfinancing.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class SBStagingContractFinancingBusManagerBean extends SBContractFinancingBusManagerBean {

	/**
	 * To get the home handler for the ContractFinancing Entity Bean
	 * @return EBContractFinancingHome - the home handler for the
	 *         ContractFinancing entity bean
	 */
	protected EBContractFinancingHome getEBContractFinancingHome() {
		EBContractFinancingHome ejbHome = (EBContractFinancingHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_CONTRACT_FINANCING_JNDI, EBContractFinancingHome.class.getName());
		return ejbHome;
	}
}
