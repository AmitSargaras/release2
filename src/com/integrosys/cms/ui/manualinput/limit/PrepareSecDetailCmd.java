/*
 * Created on 2007-2-20
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
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareSecDetailCmd extends AbstractCommand {
	
	 public String[][] getParameterDescriptor() { return (new String[][]{
			 { "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
	 });
	}
	 

	public String[][] getResultDescriptor() {
		return (new String[][] { { "sourceIdList", "java.util.List", REQUEST_SCOPE },
				{ "securitySubtypeList", "java.util.List", REQUEST_SCOPE },
				 { "inrValue", "java.lang.String", REQUEST_SCOPE },
					{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
					{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
					{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
					{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
					{ "facCat", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			List subtypeList = getTypeSubtypeList();
			
			result.put("securitySubtypeList", subtypeList);
			result.put("fundedAmount", map.get("fundedAmount"));
			result.put("nonFundedAmount", map.get("nonFundedAmount"));
			result.put("memoExposer", map.get("memoExposer"));
			result.put("sanctionedLimit", map.get("sanctionedLimit"));
			result.put("facCat", map.get("facCat"));
			result.put("inrValue", map.get("inrValue"));
			List sourceList = getSourceIdList();
			result.put("sourceIdList", sourceList);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getTypeSubtypeList() {
		List lbValList = new ArrayList();
		try {
			MILimitUIHelper helper = new MILimitUIHelper();
			List tempList = helper.getSBMILmtProxy().getSecSubtypeList();
			if (tempList != null) {
				for (int i = 0; i < tempList.size(); i++) {
					OBCollateralSubType type = (OBCollateralSubType) (tempList.get(i));
					String desc = type.getTypeName() + " - " + type.getSubTypeName();
					LabelValueBean lvBean = new LabelValueBean(desc, type.getSubTypeCode());
					lbValList.add(lvBean);
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSourceIdList() {
		List lbValList = new ArrayList();
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_SEC_SOURCE);
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
