//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.cash;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.CollateralForm;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public abstract class CashMapper extends CollateralMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		super.mapFormToOB((CollateralForm) cForm, inputs, obj);
		return CashMapperHelper.mapFormToOB((CashForm) cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		super.mapOBToForm((CollateralForm) cForm, obj, inputs);
		return CashMapperHelper.mapOBToForm((CashForm) cForm, obj, inputs);

	}

	/*
	 * public String[][] getParameterDescriptor() { return (new String[][]{
	 * {com.
	 * integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale"
	 * , GLOBAL_SCOPE}, }); }
	 */
}
