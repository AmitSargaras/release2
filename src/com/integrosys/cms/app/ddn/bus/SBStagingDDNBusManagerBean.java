/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/SBStagingDDNBusManagerBean.java,v 1.2 2005/06/08 06:39:05 htli Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the ddn bus manager.
 * This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: htli $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/06/08 06:39:05 $ Tag: $Name: $
 */
public class SBStagingDDNBusManagerBean extends SBDDNBusManagerBean {

	/**
	 * To get the home handler for the staging DDN Entity Bean
	 * @return EBDDNHome - the home handler for the DDN entity bean
	 */
	protected EBDDNHome getEBDDNHome() {
		EBDDNHome ejbHome = (EBDDNHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_STAGING_DDN_JNDI, EBDDNHome.class
				.getName());
		return ejbHome;
	}
}
