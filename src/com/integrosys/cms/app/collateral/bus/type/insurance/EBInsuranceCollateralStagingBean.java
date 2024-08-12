/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/EBInsuranceCollateralStagingBean.java,v 1.1 2005/09/29 09:39:37 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging asset of type post dated cheque.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:39:37 $ Tag: $Name: $
 */
public abstract class EBInsuranceCollateralStagingBean extends EBInsuranceCollateralBean {
	/**
	 * Get credit default swaps item local home
	 * 
	 * @return EBCDSItemLocalHome
	 */
	protected EBCDSItemLocalHome getEBCDSItemLocalHome() {
		EBCDSItemLocalHome ejbHome = (EBCDSItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INSURANCE_CDS_STAGING_LOCAL_JNDI, EBCDSItemLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCDSItemLocalHome for staging is Null!");
		}

		return ejbHome;
	}
}