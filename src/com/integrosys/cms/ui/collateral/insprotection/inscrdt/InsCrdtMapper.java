//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.insprotection.InsProtectionMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class InsCrdtMapper extends InsProtectionMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = InsCrdtMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return InsCrdtMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		InsCrdtMapperHelper.mapOBToForm((InsCrdtForm) cForm, obj, inputs);

		return cForm;

	}

	/*
	 * public String[][] getParameterDescriptor() { return (new String[][]{
	 * {com.
	 * integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale"
	 * , GLOBAL_SCOPE}, }); }
	 */
}
