/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBStgCCCertificateBean.java,v 1.1 2004/01/13 06:21:45 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the ccc entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/13 06:21:45 $ Tag: $Name: $
 */

public abstract class EBStgCCCertificateBean extends EBCCCertificateBean {
	/**
	 * Default Constructor
	 */
	public EBStgCCCertificateBean() {
	}

	/**
	 * Method to get EB Local Home for ccc item
	 */
	protected EBCCCertificateItemLocalHome getEBCCCertificateItemLocalHome() throws CCCertificateException {
		EBCCCertificateItemLocalHome home = (EBCCCertificateItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CCC_ITEM_LOCAL_JNDI, EBCCCertificateItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CCCertificateException("EBCCCertificatetItemLocalHome is null!");
	}

}