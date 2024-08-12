/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cc;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/25 07:07:47 $ Tag: $Name: $
 */

public class OwnerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public OwnerMapper() {
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		CCCheckListForm aForm = (CCCheckListForm) cForm;
		String custCategory = aForm.getCustCategory();
		String tlimitProfileID = aForm.getLimitProfileID();
		long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		if ((tlimitProfileID != null) && (tlimitProfileID.trim().length() > 0)) {
			limitProfileID = Long.parseLong(tlimitProfileID);
		}
		String tLegalID = aForm.getLegalID();
		long legalID = Long.parseLong(tLegalID);

		String applicationType = StringUtils.isBlank(aForm.getApplicationType()) ? null : aForm.getApplicationType();

		OBCCCheckListOwner owner = new OBCCCheckListOwner(limitProfileID, legalID, custCategory, applicationType);
		return owner;
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
		CCCheckListForm aForm = (CCCheckListForm) cForm;
		if (obj != null) {
			ICheckListItem tempOb = (ICheckListItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
