/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBStgPartialSCCertificateBean.java,v 1.1 2003/08/11 12:49:32 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the scc entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:49:32 $ Tag: $Name: $
 */

public abstract class EBStgPartialSCCertificateBean extends EBPartialSCCertificateBean {
	/**
	 * Default Constructor
	 */
	public EBStgPartialSCCertificateBean() {
	}

	/**
	 * Method to get EB Local Home for scc item
	 */
	protected EBPartialSCCertificateItemLocalHome getEBPartialSCCertificateItemLocalHome()
			throws SCCertificateException {
		EBPartialSCCertificateItemLocalHome home = (EBPartialSCCertificateItemLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_STAGING_PSCC_ITEM_LOCAL_JNDI,
						EBPartialSCCertificateItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new SCCertificateException("EBPartialSCCertificatetItemLocalHome is null!");
	}

}