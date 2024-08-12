/**
 * 
 */
package com.integrosys.cms.ui.image;

/**
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 0.0 $
 * @since $Date: Mar 15, 2011 4:20:37 PM $ Tag: $Name: $
 */

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxyHome;

public class MIImageUploadAddUIHelper {
	public SBMILmtProxy getSBMILmtProxy() {
		return (SBMILmtProxy) (BeanController.getEJB(ICMSJNDIConstant.SB_MI_LMT_PROXY_JNDI, SBMILmtProxyHome.class
				.getName()));
	}

}
