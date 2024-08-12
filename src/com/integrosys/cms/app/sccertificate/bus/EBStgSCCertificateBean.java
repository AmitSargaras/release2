/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBStgSCCertificateBean.java,v 1.1 2003/08/11 12:49:32 hltan Exp $
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

public abstract class EBStgSCCertificateBean extends EBSCCertificateBean {
	/**
	 * Default Constructor
	 */
	public EBStgSCCertificateBean() {
	}

	/**
	 * Method to get EB Local Home for scc item
	 */
	protected EBSCCertificateItemLocalHome getEBSCCertificateItemLocalHome() throws SCCertificateException {
		EBSCCertificateItemLocalHome home = (EBSCCertificateItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_SCC_ITEM_LOCAL_JNDI, EBSCCertificateItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new SCCertificateException("EBSCCertificatetItemLocalHome is null!");
	}

}