/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.whatifana;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 * The Mapper for WhatIfCondReportForm.
 * 
 * @author Author: Siew Kheat
 */
public class WhatIfCondReportMapper extends AbstractCommonMapper {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE } });

	}
	
	public Object mapFormToOB(CommonForm form, HashMap hashMap)
			throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		WhatIfCondReportForm newForm = (WhatIfCondReportForm) form;

		String event = newForm.getEvent();

		DefaultLogger.debug(this, "event :" + event);

		return newForm;

	}

	public CommonForm mapOBToForm(CommonForm form, Object object,
			HashMap hashMap) throws MapperException {
		return null; 
	}
}
