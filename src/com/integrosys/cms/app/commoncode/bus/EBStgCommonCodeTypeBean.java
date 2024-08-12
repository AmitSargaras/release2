package com.integrosys.cms.app.commoncode.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public abstract class EBStgCommonCodeTypeBean extends EBCommonCodeTypeBean {
	/**
	 * Method to get EB Local Home for common code type object
	 */
	protected EBCommonCodeTypeHome getEBCommonCodeTypeHome() throws CommonCodeTypeException {
		EBCommonCodeTypeHome home = (EBCommonCodeTypeHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_COMMON_CODE_TYPE_JNDI, EBCommonCodeTypeHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CommonCodeTypeException("EBCommonCodeTypeHome is null!");
	}
}
