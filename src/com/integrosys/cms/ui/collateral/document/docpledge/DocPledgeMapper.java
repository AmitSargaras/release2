//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.document.docpledge;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.document.DocumentMapper;

/**
 * Created by IntelliJ IDEA. User: kienleong Date: 2007/02/28 To change this
 * template use Options | File Templates.
 */
public class DocPledgeMapper extends DocumentMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = DocPledgeMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return DocPledgeMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		DocPledgeMapperHelper.mapOBToForm((DocPledgeForm) cForm, obj, inputs);

		return cForm;

	}

	/*
	 * public String[][] getParameterDescriptor() { return (new String[][]{
	 * {com.
	 * integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale"
	 * , GLOBAL_SCOPE}, }); }
	 */
}
