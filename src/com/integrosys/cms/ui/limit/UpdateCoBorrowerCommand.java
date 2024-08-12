/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/UpdateCoBorrowerCommand.java,v 1.4 2003/09/12 10:53:14 pooja Exp $
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
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBCoBorrowerLimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/12 10:53:14 $ Tag: $Name: $
 */
public class UpdateCoBorrowerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateCoBorrowerCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.OBCoBorrowerLimitTrxValue",
						SERVICE_SCOPE },
				{ "coBorrowerOb", "com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit", FORM_SCOPE },

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
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			OBCoBorrowerLimit ob = (OBCoBorrowerLimit) map.get("coBorrowerOb");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			OBCoBorrowerLimitTrxValue coBorrowerTrxValue = (OBCoBorrowerLimitTrxValue) map
					.get("coBorrowerLimitTrxValue");

			long outerLimitID = coBorrowerTrxValue.getLimit().getOuterLimitID();
			ILimit outerLimit = limitProxy.getLimit(outerLimitID);

			theOBTrxContext.setTrxCountryOrigin(outerLimit.getBookingLocation().getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(outerLimit.getBookingLocation().getOrganisationCode());

			ICMSTrxResult trxResult = limitProxy.makerUpdateCoBorrowerLimit(theOBTrxContext, coBorrowerTrxValue, ob);

			result.put("trxRes", trxResult);
			result.put("request.ITrxResult", trxResult);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}