/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshSecSubtypeCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "securityType", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "secSubtypeList", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String securityType = (String) (map.get("securityType"));
			result.put("secSubtypeList", getSecuritySubtypeList(securityType));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getSecuritySubtypeList(String secTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (secTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy()
						.getCollateralSubTypesByTypeCode(secTypeValue);
				if (subtypeLst != null) {
					for (int i = 0; i < subtypeLst.length; i++) {
						ICollateralSubType nextSubtype = subtypeLst[i];
						String id = nextSubtype.getSubTypeCode();
						String value = nextSubtype.getSubTypeName();
						LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(value), UIUtil
								.replaceSpecialCharForXml(id));
						lbValList.add(lvBean);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
