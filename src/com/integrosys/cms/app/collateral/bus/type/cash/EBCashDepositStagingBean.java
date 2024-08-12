/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntityStagingBean.java,v 1.1 2003/07/02 04:17:46 kllee Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for Legal Entity details
 * (staging)
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/02 04:17:46 $ Tag: $Name: $
 */
public abstract class EBCashDepositStagingBean extends EBCashDepositBean{
	/**
	 * Default Constructor
	 */
	public EBCashDepositStagingBean() {
		super();
	}

	

	protected EBLienLocalHome getEBLocalHomeLien() throws CollateralException {
		EBLienLocalHome home = (EBLienLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIEN_LOCAL_JNDI_STAGING, EBLienLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBLienLocalHome is null!");
		}
	}
	
	
	
	
	
	
	
	

	

	

	
}