/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/AssetLifeCheckerReadCmd.java,v 1 2007/01/31 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.proxy.assetlife.ICollateralAssetLifeProxy;
import com.integrosys.cms.app.collateral.trx.assetlife.ICollateralAssetLifeTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.OBCollateralAssetLifeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Checker to get the transaction information
 * Description: command that let the checker pull the information that the maker
 * submmit
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/31$ Tag: $Name$
 */

public class AssetLifeCheckerReadCmd extends AbstractCommand {

	private ICollateralAssetLifeProxy collateralAssetLifeProxy;

	public void setCollateralAssetLifeProxy(ICollateralAssetLifeProxy collateralAssetLifeProxy) {
		this.collateralAssetLifeProxy = collateralAssetLifeProxy;
	}

	public ICollateralAssetLifeProxy getCollateralAssetLifeProxy() {
		return collateralAssetLifeProxy;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "CollateralAssetLifeTrxValue", OBCollateralAssetLifeTrxValue.class.getName(), SERVICE_SCOPE },
				{ "countries.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here checker get the value for Asset Life
	 * is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		String trxId = (String) map.get("TrxId");
		trxId = trxId.trim();
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxId);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		try {
			ICollateralAssetLifeTrxValue collateralAssetLifeTrxVal = getCollateralAssetLifeProxy()
					.getCollateralAssetLifeTrxValueByTrxID(trxContext, trxId);
			resultMap.put("CollateralAssetLifeTrxValue", collateralAssetLifeTrxVal);
		}
		catch (CollateralException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
