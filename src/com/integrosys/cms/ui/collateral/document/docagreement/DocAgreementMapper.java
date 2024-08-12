//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.document.docagreement;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.document.DocumentMapper;

/**
 * 
 * @author Thurein
 * @since  2/Sep/2008	
 *
 */
public class DocAgreementMapper extends DocumentMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = DocAgreementMapperHelper.getObject(inputs);

		super.mapFormToOB(cForm, inputs, obj);
		return DocAgreementMapperHelper.mapFormToOB(cForm, inputs, obj);

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);

		DocAgreementMapperHelper.mapOBToForm((DocAgreementForm) cForm, obj, inputs);

		return cForm;

	}

	/*
	 * public String[][] getParameterDescriptor() { return (new String[][]{
	 * {com.
	 * integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale"
	 * , GLOBAL_SCOPE}, }); }
	 */
}
