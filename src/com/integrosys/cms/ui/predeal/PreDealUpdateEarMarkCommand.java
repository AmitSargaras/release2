/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealUpdateEarMarkCommand
 *
 * Created on 11:35:40 AM
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
import com.integrosys.cms.app.predeal.bus.OBPreDeal;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.app.predeal.trx.OBPreDealTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 30, 2007 Time: 11:35:40 AM
 */
public class PreDealUpdateEarMarkCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.EVENT, "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.EARMARK_ID, "java.lang.String", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		OBPreDealTrxValue value = (OBPreDealTrxValue) hashMap.get(PreDealConstants.OB_PRE_DEAL_TRX_VALUE);
		OBPreDeal ob = (OBPreDeal) hashMap.get(PreDealConstants.PRE_DEAL_FROM);
		OBTrxContext trxContext = (OBTrxContext) hashMap.get("theOBTrxContext");
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();
		String event = (String) hashMap.get(PreDealConstants.EVENT);

		try {
			String updateType;

			value.setTrxContext(trxContext);

			if (PreDealConstants.EVENT_MAKER_SUBMIT_RELEASE.equals(event)) {
				value.getStagingPreDeal().setEarMarkStatus(PreDealConstants.EARMARK_STATUS_RELEASED);
				value.getStagingPreDeal().setInfoIncorrectDetails(ob.getInfoIncorrectDetails());
				value.getStagingPreDeal().setInfoCorrectInd(ob.getInfoCorrectInd());
				value.getStagingPreDeal().setReleaseStatus(ob.getReleaseStatus());

				// DefaultLogger.debug ( this , AccessorUtil.printMethodValue (
				// ob ) ) ;

				updateType = com.integrosys.cms.app.predeal.PreDealConstants.UPDATE_TYPE_RELEASE;
			}
			else if (PreDealConstants.EVENT_MAKER_SUBMIT_TRANSFER.equals(event)) {
				value.getStagingPreDeal().setEarMarkStatus(PreDealConstants.EARMARK_STATUS_HOLDING);
				updateType = com.integrosys.cms.app.predeal.PreDealConstants.UPDATE_TYPE_TRANSFER;
			}
			else if (PreDealConstants.EVENT_MAKER_SUBMIT_DELETE.equals(event)) {
				value.getStagingPreDeal().setEarMarkStatus(PreDealConstants.EARMARK_STATUS_DELETED);
				updateType = com.integrosys.cms.app.predeal.PreDealConstants.UPDATE_TYPE_DELETE;
			}
			else {
				throw new PreDealException("Unknown event : " + event + ". Unable to proceed");
			}

			DefaultLogger.debug(this, "Update type is : " + updateType);

			ICMSTrxValue iTrxValue = proxy.makerUpdateEar(trxContext, value, value.getStagingPreDeal(), updateType);

			result.put("request.ITrxValue", iTrxValue);
		}
		catch (PreDealException e) {
			DefaultLogger.debug(this, "Error updating ear mark item ", e);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}