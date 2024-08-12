/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CheckerApproveRejectLimitCommand.java,v 1.3 2003/09/02 10:22:39 pooja Exp $
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
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 10:22:39 $ Tag: $Name: $
 */
public class CheckerApproveRejectLimitCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public CheckerApproveRejectLimitCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "limitOb", "com.integrosys.cms.app.limit.bus.OBLimit", FORM_SCOPE },
				// {"limitObStg","com.integrosys.cms.app.limit.bus.OBLimit",
				// FORM_SCOPE},
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitTrxValue", SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "sysXRef", "java.util.list", REQUEST_SCOPE },
				{ "outerlimitreference", "java.lang.String", REQUEST_SCOPE }
		// {"trxCon" , "com.integrosys.cms.app.limit.transaction", FORM_SCOPE}
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
		HashMap returnMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			DefaultLogger.debug(this, "Before Doing Search");
			String trxID = (String) map.get("trxID");
			DefaultLogger.debug(this, "limitid rdec" + trxID);
			ILimitTrxValue limittrxvalue = limitproxy.getTrxLimit(trxID);
			ILimitProxy outerlimitidproxy = LimitProxyFactory.getProxy();
			result.put("limitOb", limittrxvalue.getStagingLimit());
			result.put("sysXRef", limittrxvalue.getStagingLimit().getLimitSysXRefs());
			try {
				OBLimit outerlimit = (OBLimit) outerlimitidproxy.getLimit(limittrxvalue.getStagingLimit()
						.getOuterLimitID());
				result.put("outerlimitreference", outerlimit.getLimitRef());
				DefaultLogger.debug(this, "outerlimitreference" + outerlimit.getLimitRef());

			}
			catch (Exception e) {
				result.put("outerlimitreference", "-");
				DefaultLogger.debug(this, "outerlimitreference" + "-");

			}

			DefaultLogger.debug(this, "activated amount from actual table"
					+ limittrxvalue.getLimit().getActivatedLimitAmount());
			DefaultLogger.debug(this, "activated amount from staging table"
					+ limittrxvalue.getStagingLimit().getActivatedLimitAmount());

			result.put("trxValue", limittrxvalue);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}

}
