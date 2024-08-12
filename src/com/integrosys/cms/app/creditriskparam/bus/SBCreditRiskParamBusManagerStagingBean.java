/*
Copyright Integro Technologies Pte Ltd
$Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;

/**
 * SBCreditRiskParamBusManagerStagingBean Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class SBCreditRiskParamBusManagerStagingBean extends SBCreditRiskParamBusManagerBean {

	protected EBCreditRiskParamHome getEBCreditRiskParamHome() {
		return (EBCreditRiskParamHome) BeanController.getEJBHome("EBCreditRiskParamHomeStaging",
				EBCreditRiskParamHome.class.getName());
	}

	// protected EBCreditRiskParamGroupHome getEBCreditRiskParamGroupHome() {
	// return (EBCreditRiskParamGroupHome)BeanController.getEJBHome(
	// "EBCreditRiskParamGroupHomeStaging",
	// EBCreditRiskParamGroupHome.class.getName());
	// }
}
