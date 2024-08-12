/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/doclou/PrepareDocLoUCommandHelper.java,v 1.2 2003/07/18 11:12:54 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docagreement;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * 
 * @author Thurein
 * @since  2/Sep/2008	
 *
 */
public class PrepareDocAgreementCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "propID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 
				{ "titleID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 
				{ "titleValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 
				{ "leaseID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 
				{ "leaseValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 

		});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		DefaultLogger.debug("doing","in  the helper");
		CommonCodeList commonCode = CommonCodeList.getInstance( CategoryCodeConstant.PROPERTY_TYPE);
		result.put("propID", commonCode.getCommonCodeValues());
		result.put("propValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.TITLE_TYPE);
		result.put("titleID", commonCode.getCommonCodeValues());
		result.put("titleValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.LEASE_TYPE);
		result.put("leaseID", commonCode.getCommonCodeValues());
		result.put("leaseValue", commonCode.getCommonCodeLabels());

		
		
	}

}
