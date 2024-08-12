/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/PrepareSubLimitListCommand.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
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
 * @since 2005-9-14
 * @Tag : com.integrosys.cms.ui.collateral.commodity.sublimit.list.
 *      PrepareSubLimitListCommand.java
 */
public class PrepareSubLimitListCommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, FORM_SCOPE }, };
	}

	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		HashMap limitMap = (HashMap) paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		DefaultLogger.debug(this, " Num of Limit :" + (limitMap == null ? 0 : limitMap.size()));
		resultMap.put(SLUIConstants.AN_CMDT_LIMIT_MAP, limitMap);
	}
}
