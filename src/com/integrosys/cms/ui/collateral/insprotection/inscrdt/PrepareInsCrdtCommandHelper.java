/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inscrdt/PrepareInsCrdtCommandHelper.java,v 1.6 2003/12/17 03:17:45 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.CommonCodeList;


/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/12/17 03:17:45 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareInsCrdtCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "insuranceId", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coreMarketID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coreMarketValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		CommonCodeList commonCode;
		Collection insuranceId = InsuranceList.getInstance().getInsuranceListID();
		Collection insuranceValue = InsuranceList.getInstance().getInsuranceListValue();
		Collection list1 = CurrencyList.getInstance().getCountryValues();
		Collection coreMarketList = CoreMarketList.getInstance().getCoreMarketID();
		
		result.put("currencyCode", list1);
		result.put("insuranceId", insuranceId);
		result.put("insuranceValue", insuranceValue);
		result.put("coreMarketID", coreMarketList);
		coreMarketList = CoreMarketList.getInstance().getCoreMarketValue();
		result.put("coreMarketValue", coreMarketList);
		
		return;
	}
}
