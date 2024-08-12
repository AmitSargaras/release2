package com.integrosys.cms.ui.commoncode;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class StagingCommonCodeTypeMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public StagingCommonCodeTypeMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "OBCommonCodeTypeTrxValue",
				"com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue", SERVICE_SCOPE } });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		MaintainCommonCodeTypeForm aForm = (MaintainCommonCodeTypeForm) cForm;
		OBCommonCodeTypeTrxValue obCommonCodeTypeTrxValue = (OBCommonCodeTypeTrxValue) map
				.get("OBCommonCodeTypeTrxValue");
		ICommonCodeType temp;
		temp = obCommonCodeTypeTrxValue.getStagingCommonCodeType();
		if (temp == null) {
			throw new MapperException("The Staging ob is null in mapper");
		}

		temp.setActiveStatus(aForm.getCategoryStatus());
		temp.setCommonCategoryCode(aForm.getCategoryCode());
		temp.setCommonCategoryName(aForm.getCategoryName());
		temp.setCommonCategoryId(aForm.getCategoryId());

		return temp;
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
		MaintainCommonCodeTypeForm aForm = (MaintainCommonCodeTypeForm) cForm;
		if (obj != null) {
			ICommonCodeType tempOb = (ICommonCodeType) obj;
			aForm.setCategoryId(tempOb.getCommonCategoryId());
			aForm.setCategoryCode(tempOb.getCommonCategoryCode());
			aForm.setCategoryName(tempOb.getCommonCategoryName());
			aForm.setCategoryStatus(tempOb.getActiveStatus());
		}
		return aForm;
	}
}
