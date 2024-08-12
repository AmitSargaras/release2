/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccoltask;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.ui.common.CountryList;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/08/07 11:24:44 $ Tag: $Name: $
 */

public class SecurityColTaskMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SecurityColTaskMapper() {
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
		SecurityColTaskForm aForm = (SecurityColTaskForm) cForm;
		ICollateralTask summary = (ICollateralTask) obj;
		// System.out.println("$$$$$$$$$$$$$$$$summary11"+summary);
		try {
			if ((obj != null) && (summary != null)) {
				aForm.setCollateralId(String.valueOf(summary.getCollateralID()));
				aForm.setCollateralRef(String.valueOf(summary.getCollateralRef()));
				// aForm.setLimitId(String.valueOf(summary.getLimitList()[0].
				// getLimitID()));
				CountryList list = CountryList.getInstance();
				aForm.setColLocation(list.getCountryName(summary.getCollateralLocation()));
				aForm.setSecurityOrganization(summary.getSecurityOrganisation());
				aForm.setLeSubProfileID(summary.getLeSubProfileID());
				aForm.setCustomerCategory(summary.getCustomerCategory());
				if (summary.getCollateralType() != null) {
					aForm.setColType(summary.getCollateralType().getTypeName());
				}
				if (summary.getCollateralSubType() != null) {
					aForm.setColSubType(summary.getCollateralSubType().getSubTypeName().trim());
				}
				aForm.setColRemarks(summary.getRemarks());
				DefaultLogger.debug(this, "remark sin mapper" + summary.getRemarks());
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
