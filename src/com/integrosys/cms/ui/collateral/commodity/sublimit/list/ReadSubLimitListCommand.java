/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/ReadSubLimitListCommand.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14
 * @Tag :
 *      com.integrosys.cms.ui.collateral.commodity.sublimit.ListSubLimitCommand
 *      .java
 */
public class ReadSubLimitListCommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.AN_COMM_MAIN_TRX_VALUE, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		HashMap trxValueMap = (HashMap) paramMap.get(SLUIConstants.AN_COMM_MAIN_TRX_VALUE);
		HashMap limitMap = getLimitMap(trxValueMap);
		DefaultLogger.debug(this, " Num of Limit :" + (limitMap == null ? 0 : limitMap.size()));
		resultMap.put(SLUIConstants.AN_CMDT_LIMIT_MAP, limitMap);
	}

	private HashMap getLimitMap(HashMap trxValueMap) throws CommandProcessingException {
		if ((trxValueMap == null) || trxValueMap.isEmpty()) {
			return new HashMap();
		}
		try {
			HashMap limitMap = (HashMap) AccessorUtil.deepClone((HashMap) trxValueMap.get(SLUIConstants.AN_STAGE_LMT));
			return limitMap;
		}
		catch (Exception e) {
			throw new CommandProcessingException("Cann't clone LimitMap.");
		}
	}
}
