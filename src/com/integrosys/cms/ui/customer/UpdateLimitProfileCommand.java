/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/UpdateLimitProfileCommand.java,v 1.10 2004/09/03 07:06:58 pooja Exp $
 */

package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/09/03 07:06:58 $ Tag: $Name: $
 */
public class UpdateLimitProfileCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateLimitProfileCommand() {

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
				{ "limitprofileObItem", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
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
			OBLimitProfile ob = (OBLimitProfile) map.get("limitprofileObItem");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			theOBTrxContext.setTrxCountryOrigin(ob.getOriginatingLocation().getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(ob.getOriginatingLocation().getOrganisationCode());
			OBLimitProfileTrxValue txnvalue = (OBLimitProfileTrxValue) map.get("trxValue");
			// txnvalue.setTransactionSubType("LimitProfile");
			ICMSTrxResult resultOb = limitproxy.makerUpdateLimitProfile(theOBTrxContext, txnvalue, ob);
			result.put("trxRes", resultOb);
			result.put("request.ITrxResult", resultOb);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			CommandProcessingException cpe = new CommandProcessingException ("Fail to update limit profile");
			cpe.initCause(e);
			throw cpe;
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
