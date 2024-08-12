/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/AssetLifeCheckerRejectEditCmd.java,v 1 2007/01/31 Jerlin Exp $
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
 * Describe this class. Purpose: for Checker to reject the transaction
 * Description: command that store the transaction as rejected by checker
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/31$ Tag: $Name$
 */

public class AssetLifeCheckerRejectEditCmd extends AbstractCommand {

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
		return (new String[][] {
				{ "CollateralAssetLifeTrxValue", OBCollateralAssetLifeTrxValue.class.getName(), SERVICE_SCOPE },
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
	 * results back into the HashMap.Here rejection for Asset Life make by
	 * checker is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICollateralAssetLifeTrxValue collateralAssetLifesTrxVal = (ICollateralAssetLifeTrxValue) map
				.get("CollateralAssetLifeTrxValue");

		try {
			collateralAssetLifesTrxVal = getCollateralAssetLifeProxy().checkerRejectUpdateCollateralAssetLife(
					trxContext, collateralAssetLifesTrxVal);
			resultMap.put("request.ITrxValue", collateralAssetLifesTrxVal);

		}
		catch (CollateralException e) {
			throw new CommandProcessingException("failed to reject update collateral asset life workflow", e);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
