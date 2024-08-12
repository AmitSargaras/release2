package com.integrosys.cms.ui.collateral.guarantees.gteinwardlc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.guarantees.GuaranteesMapper;

public class GteInwardLCMapper extends GuaranteesMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = GteInwardLCMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return GteInwardLCMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		GteInwardLCMapperHelper.mapOBToForm((GteInwardLCForm) cForm, obj, inputs);

		return cForm;

	}

}
