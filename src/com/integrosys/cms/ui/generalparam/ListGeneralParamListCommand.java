/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ListBondListCommand.java,v 1.2 2003/08/11 12:06:50 btchng Exp $
 */
package com.integrosys.cms.ui.generalparam;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class ListGeneralParamListCommand extends AbstractCommand {
	
	private IGeneralParamProxy generalParamProxy;

	public IGeneralParamProxy getGeneralParamProxy() {
		return generalParamProxy;
	}

	public void setGeneralParamProxy(IGeneralParamProxy generalParamProxy) {
		this.generalParamProxy = generalParamProxy;
	}	
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
		{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE }, // To
																													// populate
																													// the
																													// form
																													// .
				{ GeneralParamListForm.MAPPER, "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		DefaultLogger.debug(this, "entering doExecute(...)");

		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IGeneralParamGroupTrxValue trxValue = (IGeneralParamGroupTrxValue) hashMap.get("generalParamGroupTrxValue");

		resultMap.put("generalParamGroupTrxValue", trxValue);
		resultMap.put(GeneralParamListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
