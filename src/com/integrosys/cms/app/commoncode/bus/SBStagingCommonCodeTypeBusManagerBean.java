package com.integrosys.cms.app.commoncode.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class SBStagingCommonCodeTypeBusManagerBean extends SBCommonCodeTypeBusManagerBean {

	/**
	 * To get the home handler for the Common Code Type Entity Bean
	 * @return EBCommonCodeTypeHome - the home handler for the Common Code Type
	 *         entity bean
	 */
	protected EBCommonCodeTypeHome getEBCommonCodeTypeHome() {
		EBCommonCodeTypeHome ejbHome = (EBCommonCodeTypeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_COMMON_CODE_TYPE_JNDI, EBCommonCodeTypeHome.class.getName());
		return ejbHome;
	}
}
