package com.integrosys.cms.ui.collateral.document.docdoa;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;

public class PrepareDocDoACommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "typeOfAssignmentID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "typeOfAssignmentValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		TypeOfAssignmentList list = TypeOfAssignmentList.getInstance();
		Collection typeOfAssignmentID = list.getTypeOfAssignmentListID();
		Collection typeOfAssignmentValue = list.getTypeOfAssignmentListValue();
		result.put("typeOfAssignmentID", typeOfAssignmentID);
		result.put("typeOfAssignmentValue", typeOfAssignmentValue);
		return;
	}

}
