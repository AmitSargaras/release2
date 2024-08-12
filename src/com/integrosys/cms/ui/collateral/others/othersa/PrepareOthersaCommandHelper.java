/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/othersa/PrepareOthersaCommandHelper.java
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.others.othersa;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.InsuranceTypeList;
import com.integrosys.cms.ui.collateral.InsurerNameList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/12 12:24:11 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareOthersaCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] {
				{ "InsurerNameID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "InsurerNameValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "InsuranceTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "InsuranceTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				// { "event", "java.lang.String",
				// ICommonEventConstant.REQUEST_SCOPE },
				{ "limitProfileIds", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leid_bcarefIds", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) throws CommandProcessingException {
		long collateralId = ICMSConstant.LONG_INVALID_VALUE;
		ILimitProxy proxy = null;
		Collection list = InsurerNameList.getInstance().getInsurerNameID();
		result.put("InsurerNameID", list);
		list = InsurerNameList.getInstance().getInsurerNameValue();
		result.put("InsurerNameValue", list);
		list = InsuranceTypeList.getInstance().getInsuranceTypeID();
		result.put("InsuranceTypeID", list);
		list = InsuranceTypeList.getInstance().getInsuranceTypeValue();
		result.put("InsuranceTypeValue", list);

		String collateralIDStr = (String) map.get("collateralID");

		if (collateralIDStr != null)
			collateralId = Long.parseLong(collateralIDStr);
		String strLmtProfileId = (String) map.get("lmtProfileId");

		String event = (String) map.get("event");

		HashMap limitProfileIdMap = null;
		if (collateralId != ICMSConstant.LONG_INVALID_VALUE)
			try {
				proxy = LimitProxyFactory.getProxy();
				limitProfileIdMap = proxy.getLimitProfileIds(collateralId);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}

		// result.put("limitProfileId" ,limitProfileIdMap );
		Collection limitProfileIds = null;
		Collection leid_bcarefIds = null;
		if (limitProfileIdMap != null) {
			limitProfileIds = limitProfileIdMap.keySet();
			leid_bcarefIds = limitProfileIdMap.values();
		}
		result.put("limitProfileIds", limitProfileIds);
		result.put("leid_bcarefIds", leid_bcarefIds);

		long llmtProfileId = ICMSConstant.LONG_INVALID_VALUE;
		if ((strLmtProfileId != null) && (strLmtProfileId.trim().length() > 0)) {
			llmtProfileId = Long.parseLong(strLmtProfileId);
		}
		else if (event != null)
			if (event.equals(OthersaAction.EVENT_UPDATE_RETURN) || event.equals(OthersaAction.EVENT_PREPARE_UPDATE)
					|| event.equals(OthersaAction.EVENT_PROCESS_UPDATE)) {
				ICollateralTrxValue trxvalue = (ICollateralTrxValue) map.get("serviceColObj");
				ICollateral col = trxvalue.getStagingCollateral();
				IInsurancePolicy[] policyList = col.getInsurancePolicies();
				if ((policyList != null) && (policyList.length > 0) && (policyList[0].getLmtProfileId() != null)) {
					llmtProfileId = policyList[0].getLmtProfileId().longValue();
				}
			}

		return;
	}
}
