/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */

public class ReadInternalLimitListCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{
						"theILParamTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue",
						SERVICE_SCOPE },
				{ "theILParamFullList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			IInternalLimitParameterTrxValue trxValue = getInternalLimitProxy().getILParamTrxValue();
			if (trxValue == null) {
				trxValue = getEmptyTrxValue();
			}
			resultMap.put("theILParamTrxValue", trxValue);
			resultMap.put("theILParamFullList", trxValue.getActualILPList());
			resultMap.put("offset", new Integer(0));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private IInternalLimitParameterTrxValue getEmptyTrxValue() {
		IInternalLimitParameterTrxValue trxValue = new OBInternalLimitParameterTrxValue();
		trxValue.setActualILPList(new ArrayList());
		trxValue.setStagingILPList(new ArrayList());
		return trxValue;
	}
}
