/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/SBStagingCCCertificateBusManagerBean.java,v 1.1 2003/08/04 12:53:48 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/04 12:53:48 $ Tag: $Name: $
 */
public class SBStagingCCCertificateBusManagerBean extends SBCCCertificateBusManagerBean {

	/**
	 * To get the home handler for the staging CC Certificate Entity Bean
	 * @return EBCCCertificateHome - the home handler for the cc certificate
	 *         entity bean
	 */
	protected EBCCCertificateHome getEBCCCertificateHome() {
		EBCCCertificateHome ejbHome = (EBCCCertificateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_CCCERTIFICATE_JNDI, EBCCCertificateHome.class.getName());
		return ejbHome;
	}
}
