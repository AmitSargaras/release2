/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for MF Checklist staging bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFChecklistStagingBean extends EBMFChecklistBean {

	/**
	 * Get the sequence of primary key for this staging MF Checklist.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_CHECKLIST_STAGE;
	}

	/**
	 * Method to get staging EB Local Home for EBMFChecklistItem
	 * 
	 * @return EBMFChecklistItemLocalHome
	 * @throws CollateralException on errors
	 */
	protected EBMFChecklistItemLocalHome getEBMFChecklistItemLocalHome() throws CollateralException {
		EBMFChecklistItemLocalHome home = (EBMFChecklistItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MF_CHECKLIST_ITEM_LOCAL_STAGING_JNDI, EBMFChecklistItemLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBMFChecklistItemLocalHome for staging is null!");
		}
	}

}