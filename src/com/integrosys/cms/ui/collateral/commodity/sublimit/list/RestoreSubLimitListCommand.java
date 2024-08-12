package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.HashMap;

import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;

public class RestoreSubLimitListCommand extends SubLimitCommand {

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, FORM_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, FORM_SCOPE } };
	}

	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		resultMap.put(SLUIConstants.AN_CMDT_LIMIT_MAP, paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP));
	}
}
