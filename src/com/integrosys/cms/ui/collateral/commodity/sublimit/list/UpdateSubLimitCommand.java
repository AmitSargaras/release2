/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/UpdateSubLimitCommand.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag com.integrosys.cms.ui.collateral.commodity.sublimit.list.
 *      UpdateSubLimitCommand.java
 */
public class UpdateSubLimitCommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, FORM_SCOPE },
				{ SLUIConstants.AN_COMM_MAIN_TRX_VALUE, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_COMM_MAIN_TRX_VALUE, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see
	 * com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand#execute
	 * (java.util.HashMap, java.util.HashMap, java.util.HashMap)
	 */
	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		HashMap limitMap = (HashMap) paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		HashMap trxValueMap = (HashMap) paramMap.get(SLUIConstants.AN_COMM_MAIN_TRX_VALUE);
		if (trxValueMap != null) {
			DefaultLogger.debug(this, "Update limit details.");
			trxValueMap.put(SLUIConstants.AN_STAGE_LMT, limitMap);
		}
		resultMap.put(SLUIConstants.AN_COMM_MAIN_TRX_VALUE, trxValueMap);
	}
}
