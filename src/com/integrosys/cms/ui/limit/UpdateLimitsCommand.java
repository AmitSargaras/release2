/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/UpdateLimitsCommand.java,v 1.8 2003/11/11 04:12:16 pooja Exp $
 */

package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/11/11 04:12:16 $ Tag: $Name: $
 */
public class UpdateLimitsCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateLimitsCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limitObItem", "com.integrosys.cms.app.limit.bus.OBLimit", FORM_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "trxRes", "com.integrosys.cms.app.transaction.OBCMSTrxResult", SERVICE_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE }

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			OBLimit ob = (OBLimit) map.get("limitObItem");
			DefaultLogger.debug(this, "after getting OBLimit");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			OBLimitTrxValue txnvalue = (OBLimitTrxValue) map.get("trxValue");
			DefaultLogger.debug(this, "before calling proxy");
			theOBTrxContext.setTrxCountryOrigin(txnvalue.getLimit().getBookingLocation().getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(txnvalue.getLimit().getBookingLocation().getOrganisationCode());
			ICMSTrxResult resultOb = limitproxy.makerUpdateLimit(theOBTrxContext, txnvalue, ob);
			DefaultLogger.debug(this, "before putting result");
			result.put("trxRes", resultOb);
			result.put("request.ITrxResult", resultOb);
			DefaultLogger.debug(this, "after putting result");

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
