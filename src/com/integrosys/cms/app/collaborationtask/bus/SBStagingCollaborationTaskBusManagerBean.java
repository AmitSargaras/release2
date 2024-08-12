/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/SBStagingCollaborationTaskBusManagerBean.java,v 1.2 2003/08/31 13:56:57 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/31 13:56:57 $ Tag: $Name: $
 */
public class SBStagingCollaborationTaskBusManagerBean extends SBCollaborationTaskBusManagerBean {

	/**
	 * To get the home handler for the staging collateral task Entity Bean
	 * @return EBCollateralTaskHome - the home handler for the collateral task
	 *         entity bean
	 */
	protected EBCollateralTaskHome getEBCollateralTaskHome() {
		EBCollateralTaskHome ejbHome = (EBCollateralTaskHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_COLLATERAL_TASK_JNDI, EBCollateralTaskHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the SCCertificate Entity Bean
	 * @return EBCCTaskHome - the home handler for the SC Certificate entity
	 *         bean
	 */
	protected EBCCTaskHome getEBCCTaskHome() {
		EBCCTaskHome ejbHome = (EBCCTaskHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_STAGING_CC_TASK_JNDI,
				EBCCTaskHome.class.getName());
		return ejbHome;
	}
}
