/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SBStagingSCCertificateBusManagerBean.java,v 1.2 2003/08/11 12:48:22 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/11 12:48:22 $ Tag: $Name: $
 */
public class SBStagingSCCertificateBusManagerBean extends SBSCCertificateBusManagerBean {

	/**
	 * To get the home handler for the staging SC Certificate Entity Bean
	 * @return EBSCCertificateHome - the home handler for the sc certificate
	 *         entity bean
	 */
	protected EBSCCertificateHome getEBSCCertificateHome() {
		EBSCCertificateHome ejbHome = (EBSCCertificateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_SCCERTIFICATE_JNDI, EBSCCertificateHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the PartialSCCertificate Entity Bean
	 * @return EBPartialSCCCertificateHome - the home handler for the Partial SC
	 *         Certificate entity bean
	 */
	protected EBPartialSCCertificateHome getEBPartialSCCertificateHome() {
		EBPartialSCCertificateHome ejbHome = (EBPartialSCCertificateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_PSCCERTIFICATE_JNDI, EBPartialSCCertificateHome.class.getName());
		return ejbHome;
	}

}
