/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * Entity bean implementation for staging MF Template entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFTemplateStagingBean extends EBMFTemplateBean {

	/**
	 * Get the sequence of primary key for this staging MF Template.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_TEMPLATE_STAGE;
	}

	protected String getFindExcludeStatus() {
		return "";
	}

	/**
	 * Method to get staging EB Local Home for EBMFItem
	 * 
	 * @return EBMFItemLocalHome
	 * @throws PropertyParametersException on errors
	 */
	protected EBMFItemLocalHome getEBMFItemLocalHome() throws PropertyParametersException {
		EBMFItemLocalHome home = (EBMFItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MF_ITEM_LOCAL_STAGING_JNDI, EBMFItemLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new PropertyParametersException("EBMFItemLocalHome for staging is null!");
		}
	}

	/**
	 * Method to get staging EB Local Home for EBMFTemplateSecSubType
	 * 
	 * @return EBMFTemplateSecSubTypeLocalHome
	 * @throws PropertyParametersException on errors
	 */
	protected EBMFTemplateSecSubTypeLocalHome getEBMFTemplateSecSubTypeLocalHome() throws PropertyParametersException {
		EBMFTemplateSecSubTypeLocalHome home = (EBMFTemplateSecSubTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MF_TEMPLATE_SEC_SUBTYPE_LOCAL_STAGING_JNDI, EBMFTemplateSecSubTypeLocalHome.class
						.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new PropertyParametersException("EBMFTemplateSecSubTypeLocalHome for staging is null!");
		}
	}

}