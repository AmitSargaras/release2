/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealConfrimApproveCommand
 *
 * Created on 3:20:41 PM
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
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 29, 2007 Time: 3:20:41 PM
 */
public class PreDealConfirmApproveCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ PreDealConstants.TRX_ID, "java.lang.String", REQUEST_SCOPE },
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

		OBPreDealTrxValue trxValue = (OBPreDealTrxValue) hashMap.get(PreDealConstants.OB_PRE_DEAL_TRX_VALUE);
		OBTrxContext trxContext = (OBTrxContext) hashMap.get("theOBTrxContext");
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		try {
			trxValue.setTrxContext(trxContext);

			result.put("request.ITrxValue", proxy.checkerApprove(trxContext, trxValue));
		}
		catch (PreDealException e) {
			DefaultLogger.debug(this, "Error approving new ear Mark ", e);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}