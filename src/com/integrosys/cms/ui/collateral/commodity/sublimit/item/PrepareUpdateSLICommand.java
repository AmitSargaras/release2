/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/item/PrepareUpdateSLICommand.java,v 1.3 2006/09/27 02:19:26 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.OBSubLimit;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitUtils;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag 
 *      com.integrosys.cms.ui.collateral.commodity.sublimit.PrepareUpdateSLICommand
 *      .java
 */
public class PrepareUpdateSLICommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { "event", SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.FN_IDX_ID, SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.FN_LIMIT_ID, SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.AN_OB_SL, SLUIConstants.CN_I_SL, FORM_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_OB_SL, SLUIConstants.CN_I_SL, FORM_SCOPE },
				{ SLUIConstants.AN_SLT_LIST, SLUIConstants.CN_LIST, REQUEST_SCOPE },
				{ SLUIConstants.AN_CURRENCY_COLLECTION, SLUIConstants.CN_COLLECTION, REQUEST_SCOPE } };
	}

	/*
	 * @see
	 * com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand#execute
	 * (java.util.HashMap, java.util.HashMap, java.util.HashMap)
	 */
	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		String idxStr = (String) paramMap.get(SLUIConstants.FN_IDX_ID);
		int index = -1;
		if (idxStr != null) {
			index = Integer.parseInt(idxStr);
		}
		DefaultLogger.debug(this, "Index : " + index);
		OBSubLimit sl = null;
		String event = (String) paramMap.get("event");
		if (SLUIConstants.EN_RE_PREPARE_UPDATE_ITEM.equals(event)) {
			sl = (OBSubLimit) paramMap.get(SLUIConstants.AN_OB_SL);
		}
		else if (index == -1) {
			sl = new OBSubLimit();
		}
		else {
			sl = (OBSubLimit) getToUpdateSL(index, paramMap);
		}
		resultMap.put(SLUIConstants.AN_OB_SL, sl);
		resultMap.put(SLUIConstants.AN_SLT_LIST, SubLimitUtils.getALLSLTList());
		resultMap.put(SLUIConstants.AN_CURRENCY_COLLECTION, CurrencyList.getInstance().getCountryValues());
	}

	private ISubLimit getToUpdateSL(int index, HashMap paramMap) {
		String limitId = (String) paramMap.get(SLUIConstants.FN_LIMIT_ID);
		HashMap limitMap = (HashMap) paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		ICollateralLimitMap cLimitMap = (ICollateralLimitMap) limitMap.get(limitId);
		ISubLimit[] subLimitArray = cLimitMap.getSubLimit();
		return subLimitArray[index];
	}
}
