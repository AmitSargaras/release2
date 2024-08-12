/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationMakerReadRejectedCmd.java,v 1 2007/02/09 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.cms.app.liquidation.proxy.LiquidationProxyFactory;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to reedit the liquidation value after
 * rejected by checker Description: command that get the value that being
 * rejected by checker from database to let the user to reedit the value
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class MakerReadRejectedLiquidationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE },
				{ "currency.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "nplInfo", "java.util.Collection", SERVICE_SCOPE },
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.OBLiquidation", SERVICE_SCOPE },
				{ "sess.collateralID", "java.lang.String", SERVICE_SCOPE },
		// {"InitialLiquidation",
		// "com.integrosys.cms.app.liquidation.bus.OBLiquidation", FORM_SCOPE}
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get the value where checker rejected
	 * from database for Liquidation is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String trxId = (String) map.get("TrxId");
		trxId = trxId.trim();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		try {
			ILiquidationProxy proxy = LiquidationProxyFactory.getProxy();

			ILiquidationTrxValue liquidationTrxVal = proxy.getLiquidationTrxValueByTrxID(trxContext, trxId);
			ILiquidation liq = liquidationTrxVal.getStagingLiquidation();
			Collection nplInfo = proxy.getNPLInfo(liq.getCollateralID());
			resultMap.put("nplInfo", nplInfo);
			resultMap.put("sess.collateralID", String.valueOf(liq.getCollateralID()));

			// if current status is other than ACTIVE & REJECTED, then show
			// workInProgress.
			// i.e. allow edit only if status is either ACTIVE or REJECTED
			if ((!liquidationTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
					&& (!liquidationTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED))

			) {
				resultMap.put("wip", "wip");
				resultMap.put("InitialLiquidation", liquidationTrxVal.getStagingLiquidation());

			}
			else {
				resultMap.put("LiquidationTrxValue", liquidationTrxVal);
			}
			resultMap.put("InitialLiquidation", liquidationTrxVal.getStagingLiquidation());

			resultMap.put("currency.map", MaintainLiquidationHelper.buildCurrencyMap());

		}
		catch (LiquidationException e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to read rejected liquidation from workflwo");
			cpe.initCause(e);
			throw cpe;
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
