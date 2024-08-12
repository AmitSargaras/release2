package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;

public class RestoreSLICommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.AN_OB_SL, SLUIConstants.CN_I_SL, FORM_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_OB_SL, SLUIConstants.CN_I_SL, FORM_SCOPE } };
	}

	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		resultMap.put(SLUIConstants.AN_OB_SL, paramMap.get(SLUIConstants.AN_OB_SL));
	}

}
