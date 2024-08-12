/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealPrepareDeleteEarMarkCommand
 *
 * Created on 10:07:44 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.app.predeal.trx.OBPreDealTrxValue;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 30, 2007 Time: 10:07:44 AM
 */
public class PreDealPrepareUpdateEarMarkCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.EARMARK_ID, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.EARMARK_ID_SESSION, "java.lang.String", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.EVENT_MAKER_WIP, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.EARMARK_ID_SESSION, "java.lang.String", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String earMarkID = (String) hashMap.get(PreDealConstants.EARMARK_ID);
		String event = (String) hashMap.get(PreDealConstants.EVENT);
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		if ((earMarkID == null) || earMarkID.equals("")) {
			earMarkID = (String) hashMap.get(PreDealConstants.EARMARK_ID_SESSION);
		}

		DefaultLogger.debug(this, "earMarkID : " + earMarkID);
		DefaultLogger.debug(this, "event : " + event);

		result.put(PreDealConstants.EARMARK_ID_SESSION, earMarkID);

		try {
			OBPreDealTrxValue value = (OBPreDealTrxValue) proxy.getEarByEarMarkId(earMarkID);
			String toState = value.getToState(); // actually the current state
													// !!!!!

			DefaultLogger.debug(this, "Current state of the item : " + toState);

			if (!ICMSConstant.STATE_ACTIVE.equals(toState)) // if the current
															// items is not
															// active
			{
				result.put(PreDealConstants.EVENT_MAKER_WIP, PreDealConstants.EVENT_MAKER_WIP);
			}
			else {
				result.put(PreDealConstants.OB_PRE_DEAL_TRX_VALUE, value);
				result.put(PreDealConstants.PRE_DEAL_RECORD, hashMap.get(PreDealConstants.PRE_DEAL_RECORD));

				if (!PreDealConstants.EVENT_MAKER_PREPARE_RELEASE.equals(event)) {
					result.put(PreDealConstants.PRE_DEAL_FROM, value.getPreDeal());
				}
			}

		}
		catch (PreDealException e) {
			DefaultLogger.debug(this, "Error search ear mark item for ear mark id : " + earMarkID, e);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}