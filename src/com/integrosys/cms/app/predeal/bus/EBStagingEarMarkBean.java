/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBStgEarMarkBean
 *
 * Created on 11:46:55 AM
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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 11:46:55 AM
 */
public abstract class EBStagingEarMarkBean extends EBEarMarkBean {

	protected EBEarMarkHome getEBEarMarkHome() {
		EBEarMarkHome home = (EBEarMarkHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_STAGING_PRE_DEAL_JNDI,
				EBEarMarkHome.class.getName());

		if (home == null) {
			DefaultLogger.debug(this, "EB Staging Ear Mark Bean is null");
		}

		return home;
	}

}
