/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.bfl;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.OBTATEntry;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/15 10:11:02 $ Tag: $Name: $
 */

public class BflMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public BflMapper() {
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		BflForm aForm = (BflForm) cForm;
		// ILimitProfile limitProfile =
		// (ILimitProfile)map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ITATEntry ent = new OBTATEntry();
		ent.setRemarks(aForm.getTatRemarks());
		return ent;
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
		return cForm;
	}
}
