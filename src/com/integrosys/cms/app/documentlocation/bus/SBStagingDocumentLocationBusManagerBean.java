/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/SBStagingDocumentLocationBusManagerBean.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public class SBStagingDocumentLocationBusManagerBean extends SBDocumentLocationBusManagerBean {
	/**
	 * To get the home handler for the documentation location Entity Bean
	 * @return EBCCDocumentLocationHome - the home handler for the SC
	 *         Certificate entity bean
	 */
	protected EBCCDocumentLocationHome getEBCCDocumentLocationHome() {
		EBCCDocumentLocationHome ejbHome = (EBCCDocumentLocationHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_CC_DOC_LOCATION_JNDI, EBCCDocumentLocationHome.class.getName());
		return ejbHome;
	}
}
