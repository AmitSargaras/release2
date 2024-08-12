package com.integrosys.cms.app.creditriskparam.bus.policycap;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

//import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SBStagingPolicyCapBusManagerBean extends SBPolicyCapBusManagerBean {

	/**
	 * To get the home handler for the PolicyCap Entity Bean
	 * @return EBPolicyCapHome - the home handler for the PolicyCap entity bean
	 */
	protected EBPolicyCapHome getEBPolicyCapHome() {
		EBPolicyCapHome ejbHome = (EBPolicyCapHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_POLICY_CAP_JNDI, EBPolicyCapHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the PolicyCapGroup Entity Bean
	 * @return EBPolicyCapHomeGroup - the home handler for the PolicyCapGroup
	 *         entity bean
	 */
	protected EBPolicyCapGroupHome getEBPolicyCapGroupHome() {

		EBPolicyCapGroupHome ejbHome = (EBPolicyCapGroupHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_POLICY_CAP_GROUP_JNDI, EBPolicyCapGroupHome.class.getName());
		return ejbHome;
	}
}
