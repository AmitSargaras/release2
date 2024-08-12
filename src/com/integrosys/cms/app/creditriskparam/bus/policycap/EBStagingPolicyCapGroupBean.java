/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity Class of Staging PolicyCapGroup Bean
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public abstract class EBStagingPolicyCapGroupBean extends EBPolicyCapGroupBean {

	/**
	 * Get the EJB Local Home interface of PolicyCap
	 * @return
	 * @throws PolicyCapException
	 */
	private EBPolicyCapLocalHome getEBPolicyCapLocalHome() throws PolicyCapException {

		EBPolicyCapLocalHome home = (EBPolicyCapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_POLICY_CAP_LOCAL_JNDI, EBPolicyCapLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		else {
			throw new PolicyCapException("EBPolicyCapLocalHome is null");
		}
	}

}
