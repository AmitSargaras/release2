package com.integrosys.cms.ui.collateral.document.docdoa;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.document.DocumentMapper;

public class DocDoAMapper extends DocumentMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = DocDoAMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return DocDoAMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		DocDoAMapperHelper.mapOBToForm((DocDoAForm) cForm, obj, inputs);

		return cForm;

	}
}
