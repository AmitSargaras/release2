package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class SBStagingBridgingLoanBusManagerBean extends SBBridgingLoanBusManagerBean {
	/**
	 * To get the home handler for the ContractFinancing Entity Bean
	 * @return EBBridgingLoanHome - the home handler for the ContractFinancing
	 *         entity bean
	 */
	protected EBBridgingLoanHome getEBBridgingLoanHome() {
		EBBridgingLoanHome ejbHome = (EBBridgingLoanHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_BRIDGING_LOAN_JNDI, EBBridgingLoanHome.class.getName());
		return ejbHome;
	}
}
