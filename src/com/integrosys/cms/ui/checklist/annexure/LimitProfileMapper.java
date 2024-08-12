/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

import java.util.HashMap;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/15 08:25:16 $ Tag: $Name: $
 */

public class LimitProfileMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public LimitProfileMapper() {
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		return null;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		AnnexureForm aForm = (AnnexureForm) cForm;
		ILimitProfile limitProfile = (ILimitProfile) obj;
		if (limitProfile != null) {
			aForm.setLimitProfileId(String.valueOf(limitProfile.getLimitProfileID()));
			aForm.setSubProfileId(String.valueOf(limitProfile.getCustomerID()));
		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
