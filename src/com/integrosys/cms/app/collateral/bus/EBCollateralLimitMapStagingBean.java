/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralLimitMapStagingBean.java,v 1.2 2005/10/10 11:55:45 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.EBSubLimitLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-29
 * @Tag 
 *      com.integrosys.cms.app.collateral.bus.EBCollateralLimitMapStagingBean.java
 */
public abstract class EBCollateralLimitMapStagingBean extends EBCollateralLimitMapBean {
	protected EBSubLimitLocalHome getSubLimitLocal() {
		EBSubLimitLocalHome home = (EBSubLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SUB_LIMIT_STAGING_LOCAL_JNDI, EBSubLimitLocalHome.class.getName());
		return home;
	}
}
