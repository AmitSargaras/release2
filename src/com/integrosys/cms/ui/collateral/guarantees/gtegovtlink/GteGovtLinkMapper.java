package com.integrosys.cms.ui.collateral.guarantees.gtegovtlink;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.guarantees.GuaranteesMapper;

public class GteGovtLinkMapper extends GuaranteesMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = GteGovtLinkMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return GteGovtLinkMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		GteGovtLinkMapperHelper.mapOBToForm((GteGovtLinkForm) cForm, obj, inputs);

		return cForm;

	}

}
