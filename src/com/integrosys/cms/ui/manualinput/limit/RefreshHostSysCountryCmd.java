/*
 * Created on Mar 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshHostSysCountryCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "hostSystemCountry", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "hostSystemNameList", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String hostSystemCountry = (String) (map.get("hostSystemCountry"));
			result.put("hostSystemNameList", getHostSystemName(hostSystemCountry));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	/**
	 * @param hostSystemCountry
	 * @return
	 */
	private List getHostSystemName(String hostSystemCountry) {
		List lbValList = new ArrayList();
		if ((hostSystemCountry != null) && !hostSystemCountry.trim().equals("")) {
			HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("ACCT_SOURCE", null, hostSystemCountry);
			Object[] keyArr = map.keySet().toArray();
			for (int i = 0; i < keyArr.length; i++) {
				Object nextKey = keyArr[i];
				LabelValueBean lvBean = new LabelValueBean(
						UIUtil.replaceSpecialCharForXml(map.get(nextKey).toString()), UIUtil
								.replaceSpecialCharForXml(nextKey.toString()));
				lbValList.add(lvBean);
			}
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
