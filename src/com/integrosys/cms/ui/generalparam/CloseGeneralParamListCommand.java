/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/CloseBondListCommand.java,v 1.8 2005/01/12 06:39:12 hshii Exp $
 */
package com.integrosys.cms.ui.generalparam;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class CloseGeneralParamListCommand extends AbstractCommand {
	
	private IGeneralParamProxy generalParamProxy;

	public IGeneralParamProxy getGeneralParamProxy() {
		return generalParamProxy;
	}

	public void setGeneralParamProxy(IGeneralParamProxy generalParamProxy) {
		this.generalParamProxy = generalParamProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IGeneralParamGroupTrxValue value = (IGeneralParamGroupTrxValue) map.get("generalParamGroupTrxValue");

			// String remarks = (String)map.get(GeneralParamListForm.MAPPER);
			// value.setRemarks(remarks);

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			if (ICMSConstant.STATE_DRAFT.equals(value.getStatus())) {
				value = getGeneralParamProxy().makerCloseDraftGeneralParamGroup(trxContext, value);
			}
			else {
				value = getGeneralParamProxy().makerCloseRejectedGeneralParamGroup(trxContext, value);
			}

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
