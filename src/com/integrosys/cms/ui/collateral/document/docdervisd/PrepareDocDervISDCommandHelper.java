/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/PrepareDocDervISDCommandHelper.java,v 1.5 2003/09/10 03:03:30 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docdervisd;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/09/10 03:03:30 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareDocDervISDCommandHelper {

	public static String[][] getResultDescriptor() {

		return (new String[][] { { "iSDAProductListCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "iSDAProductListValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "iFEMAProductID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "iFEMAProductValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "iCOMProductID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "iCOMProductValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		ISDAProductList list = ISDAProductList.getInstance();
		Collection iSDAProductListID = list.getISDAProductListID();
		Collection iSDAProductListValue = list.getISDAProductListValue();
		result.put("iSDAProductListCode", iSDAProductListID);
		result.put("iSDAProductListValue", iSDAProductListValue);
		IFEMAProductList list1 = IFEMAProductList.getInstance();
		result.put("iFEMAProductID", list1.getIFEMAProductListID());
		result.put("iFEMAProductValue", list1.getIFEMAProductListValue());
		ICOMProductList list2 = ICOMProductList.getInstance();
		result.put("iCOMProductID", list2.getICOMProductListID());
		result.put("iCOMProductValue", list2.getICOMProductListValue());
		return;
	}

}
