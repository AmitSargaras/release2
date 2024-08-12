/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.UserException;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/24 12:52:24 $ Tag: $Name: $
 */
public class MakerCnclEditCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerCnclEditCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBUserTrxValue userTrxVal = (OBUserTrxValue) map.get("CommonUserTrxValue");
		try {
			CMSUserProxy proxy = new CMSUserProxy();
			ICompTrxResult trxResult = proxy.makerCancelUpdateUser(trxContext, userTrxVal);
			resultMap.put("request.ITrxResult", trxResult);
		}
		catch (RemoteException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (UserException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
