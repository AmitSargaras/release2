/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.SBPropertyParametersHome;

/**
 * This factory creates SBPropertyParameters.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class PropertyParametersHelper {
	private static PropertyParametersHelper thisInstance;

	/**
	 * Default Constructor
	 */
	public PropertyParametersHelper() {
	}

	public synchronized static PropertyParametersHelper getInstance() {
		if (thisInstance == null) {
			thisInstance = new PropertyParametersHelper();
		}
		return thisInstance;
	}

	/**
	 * Get the SB for the actual storage of property parameter
	 * 
	 * @return SBPropertyParameters
	 * @throws TrxOperationException on errors
	 */
	public SBPropertyParameters getActualPropertyParameters() throws TrxOperationException {
		SBPropertyParameters home = (SBPropertyParameters) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_PARAMETERS_HOME, SBPropertyParametersHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new TrxOperationException("SBPropertyParameters for Actual is null!");
		}
	}

	/**
	 * Get the SB for the staging storage of property parameter
	 * 
	 * @return SBPropertyParameters
	 * @throws TrxOperationException on errors
	 */
	public SBPropertyParameters getStagingPropertyParameters() throws TrxOperationException {
		SBPropertyParameters home = (SBPropertyParameters) BeanController.getEJB(
				ICMSJNDIConstant.SB_STG_PROPERTY_PARAMETERS_HOME, SBPropertyParametersHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new TrxOperationException("SBPropertyParameters for Staging is null!");
		}
	}

}