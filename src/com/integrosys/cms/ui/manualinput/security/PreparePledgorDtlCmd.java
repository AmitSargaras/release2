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
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PreparePledgorDtlCmd extends AbstractCommand {
	public String[][] getResultDescriptor() {
		return (new String[][] { { "sourceIdList", "java.util.List", REQUEST_SCOPE },
				{ "idTypeList", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			List sourceList = getSourceIdList();
			result.put("sourceIdList", sourceList);
			List idTypeList = getIdTypeList();
			result.put("idTypeList", idTypeList);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getSourceIdList() {
		List lbValList = new ArrayList();
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("LE_ID_TYPE");
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getIdTypeList() {
		List lbValList = new ArrayList();
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("ID_TYPE");
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
