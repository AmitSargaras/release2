/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBStagingPreDealManagerBean
 *
 * Created on 2:13:26 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 2:13:26 PM
 */
public class SBStagingPreDealBusManagerBean extends SBPreDealBusManagerBean {

	protected EBEarMarkHome getEBEarMarkHome() {
		EBEarMarkHome ejbHome = (EBEarMarkHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_STAGING_EAR_MARK_JNDI,
				EBEarMarkHome.class.getName());

		if (ejbHome == null) {
			DefaultLogger.debug(this, "EJB home is null");
		}

		return ejbHome;
	}

	protected boolean isStagingOp() {
		return true;
	}

}
