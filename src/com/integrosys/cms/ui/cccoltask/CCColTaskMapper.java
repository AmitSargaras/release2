/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccoltask;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.ui.common.CountryList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/14 08:19:00 $ Tag: $Name: $
 */

public class CCColTaskMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CCColTaskMapper() {
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
		CCColTaskForm aForm = (CCColTaskForm) cForm;
		ICCTask summary = (ICCTask) obj;

		try {
			if (obj != null) {
				aForm.setCustomerCategory(summary.getCustomerCategory());
				aForm.setCustomerType(summary.getCustomerType());
				CountryList lst = CountryList.getInstance();
				aForm.setDomicileCountry(lst.getCountryName(summary.getDomicileCountry()));
				aForm.setColRemarks(summary.getRemarks());
				if ((summary.getLegalRef() != null) && !summary.getLegalRef().equals("0")) {
					aForm.setLegalRef(summary.getLegalRef());
				}
				aForm.setLegalName(summary.getLegalName());
				aForm.setOrgCode(summary.getOrgCode());
				aForm.setSubProfileID(String.valueOf(summary.getLimitProfileID()));

			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
