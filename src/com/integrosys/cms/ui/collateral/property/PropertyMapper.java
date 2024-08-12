//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.property;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.CollateralForm;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public abstract class PropertyMapper extends CollateralMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		super.mapFormToOB((CollateralForm) cForm, inputs, obj);
		return PropertyMapperHelper.mapFormToOB((PropertyForm) cForm, inputs, obj);
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		super.mapOBToForm((CollateralForm) cForm, obj, inputs);
		return PropertyMapperHelper.mapOBToForm((PropertyForm) cForm, obj, inputs);
	}

}
