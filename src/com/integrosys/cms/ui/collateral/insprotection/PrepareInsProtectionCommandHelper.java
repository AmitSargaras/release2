/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/PrepareInsProtectionCommandHelper.java,v 1.3 2003/12/17 03:17:14 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/17 03:17:14 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareInsProtectionCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "natureOfChargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 
				{ "arrInsurerID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "arrInsurerValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "optionListID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "optionListValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insurerList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
			
				});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		Collection natureList = NatureOfChargeList.getInstance().getNatureOfChargeListID();
		CommonCodeList commonCode;

		result.put("natureOfChargeID", natureList);
		natureList = NatureOfChargeList.getInstance().getNatureOfChargeListValue();
		result.put("natureOfChargeValue", natureList);

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ARR_INSURER);
		result.put("arrInsurerID", commonCode.getCommonCodeValues());
		result.put("arrInsurerValue", commonCode.getCommonCodeLabels());
		
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.OPTION_LIST);
		result.put("optionListID", commonCode.getCommonCodeValues());
		result.put("optionListValue", commonCode.getCommonCodeLabels());

		
		return;
	}

}
