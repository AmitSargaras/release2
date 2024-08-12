/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/MaintainSystemParametersMapper.java,v 1.5 2005/05/10 10:22:09 wltan Exp $
 */
package com.integrosys.cms.ui.commoncode;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commoncode.bus.OBCommonCodeType;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 * @since $Id$
 */
public class MaintainCommonCodeTypeMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public MaintainCommonCodeTypeMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "OBCommonCodeTypeTrxValue", "com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue",
						SERVICE_SCOPE } });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		String event = (String) map.get(ICommonEventConstant.EVENT);
		MaintainCommonCodeTypeForm aForm = (MaintainCommonCodeTypeForm) cForm;

		DefaultLogger.debug(this, "Mapping for event " + event);

		OBCommonCodeType obCommonCodeType = new OBCommonCodeType();
		obCommonCodeType.setCommonCategoryId(aForm.getCategoryId());
		obCommonCodeType.setCommonCategoryCode(aForm.getCategoryCode());
		obCommonCodeType.setCommonCategoryName(aForm.getCategoryName());
		obCommonCodeType.setActiveStatus(aForm.getCategoryStatus());

		return obCommonCodeType;
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
		try {
			MaintainCommonCodeTypeForm aForm = (MaintainCommonCodeTypeForm) cForm;
			if (obj != null) {
				OBCommonCodeType obCommonCodeType = (OBCommonCodeType) obj;
				aForm.setCategoryId(obCommonCodeType.getCommonCategoryId());
				aForm.setCategoryCode(obCommonCodeType.getCommonCategoryCode());
				aForm.setCategoryName(obCommonCodeType.getCommonCategoryName());
				aForm.setCategoryStatus(obCommonCodeType.getActiveStatus());
			}
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in MaintainCommonCodeTypeMapper is" + e);
		}
		return null;

	}
}
