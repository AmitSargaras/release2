package com.integrosys.cms.ui.collateral.cash.cashcash;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.cash.CashMapper;

/**
 * Created by IntelliJ IDEA. User: Naveen Date: Feb 20, 2007 Time: 4:45:05 PM To
 * change this template use Options | File Templates.
 */
public class CashCashMapper extends CashMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = CashCashMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return CashCashMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		CashCashMapperHelper.mapOBToForm((CashCashForm) cForm, obj, inputs);

		return cForm;

	}

	/*
	 * public String[][] getParameterDescriptor() { return (new String[][]{
	 * {com.
	 * integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale"
	 * , GLOBAL_SCOPE}, }); }
	 */
}
