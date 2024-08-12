/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/02/09 11:01:19 $ Tag: $Name: $
 */

public class OwnerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public OwnerMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE }, });
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
		CCReceiptForm aForm = (CCReceiptForm) cForm;
		String custCategory = aForm.getCustCategory();
		String tlimitProfileID = aForm.getLimitProfileID();
		long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		if (!ICMSConstant.CHECKLIST_NON_BORROWER.equals(custCategory)) {
			if ((tlimitProfileID != null) && (tlimitProfileID.trim().length() > 0)) {
				limitProfileID = Long.parseLong(tlimitProfileID);
			}
		}
		String tLegalID = aForm.getLegalID();
		long legalID = Long.parseLong(tLegalID);
		DefaultLogger.debug(this, "limitProfileID------->" + limitProfileID);
		DefaultLogger.debug(this, "legalID----------->" + legalID);
		DefaultLogger.debug(this, "custCategory----------->" + custCategory);
		OBCCCheckListOwner owner = new OBCCCheckListOwner(limitProfileID, legalID, custCategory);
		DefaultLogger.debug(this, "owner object in Mapper" + owner);
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
		CCReceiptForm aForm = (CCReceiptForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if (obj != null) {
			ICheckListItem tempOb = (ICheckListItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
			aForm.setActionParty(tempOb.getActionParty());
			aForm.setDocRemarks(tempOb.getRemarks());
			aForm.setDocRef(tempOb.getDocRef());
			aForm.setFormNo(tempOb.getFormNo());
			aForm.setDocDate(DateUtil.formatDate(locale, tempOb.getDocDate()));
			aForm.setDocExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
