package com.integrosys.cms.ui.collateral.document.docloi;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.document.DocumentMapper;

public class DocLoIMapper extends DocumentMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = DocLoIMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return DocLoIMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		DocLoIMapperHelper.mapOBToForm((DocLoIForm) cForm, obj, inputs);

		return cForm;

	}
}
