/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationCheckerApproveEditCmd.java,v 1 2007/02/08 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;
import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.cms.app.liquidation.proxy.LiquidationProxyFactory;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for checker to approve the transaction
 * Description: command that let the checker to approve the transaction that
 * being make by the maker
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class CheckerApproveEditLiquidationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE },
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.OBLiquidation", SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here approval for Liquidation is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILiquidationTrxValue liquidationTrxVal = (ILiquidationTrxValue) map.get("LiquidationTrxValue");
		DefaultLogger.debug(this, ((OBLiquidation) map.get("InitialLiquidation")));

		try {
			ILiquidationProxy proxy = LiquidationProxyFactory.getProxy();
			proxy.checkerApproveUpdateLiquidation(trxContext, liquidationTrxVal);
			resultMap.put("request.ITrxValue", liquidationTrxVal);

		}
		catch (LiquidationException e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to submit checker approve liquidation workflow");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
