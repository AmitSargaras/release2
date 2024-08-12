/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealDeleteEarMarkCommand
 *
 * Created on 11:37:26 AM
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
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.app.predeal.trx.OBPreDealTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 30, 2007 Time: 11:37:26 AM
 */
public class PreDealDeleteEarMarkCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "java.lang.Object", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		OBPreDealTrxValue value = (OBPreDealTrxValue) hashMap.get(PreDealConstants.OB_PRE_DEAL_TRX_VALUE);
		OBTrxContext trxContext = (OBTrxContext) hashMap.get("theOBTrxContext");
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		try {
			value.getStagingPreDeal().setEarMarkStatus(PreDealConstants.EARMARK_STATUS_DELETED);
			value.setTrxContext(trxContext);

			ICMSTrxValue iTrxValue = proxy.makerUpdateEar(trxContext, value, value.getStagingPreDeal(),
					com.integrosys.cms.app.predeal.PreDealConstants.UPDATE_TYPE_DELETE);

			result.put("request.ITrxValue", iTrxValue);
		}
		catch (PreDealException e) {
			DefaultLogger.debug(this, "Error deleting ear mark item ", e);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}