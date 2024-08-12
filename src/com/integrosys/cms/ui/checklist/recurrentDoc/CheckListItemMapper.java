/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDoc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 04:22:26 $ Tag: $Name: $
 */

public class CheckListItemMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CheckListItemMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem",
				SERVICE_SCOPE }, });
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
		RecurrentDocCheckListForm  form =(RecurrentDocCheckListForm)cForm;
		ICheckListItem item= new OBCheckListItem();
		
		item.setActionParty(form.getAppendStatementType());
		
		return item;
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
		RecurrentDocCheckListForm aForm = (RecurrentDocCheckListForm) cForm;
		if (obj != null) {
			ICheckListItem tempOb = (ICheckListItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
