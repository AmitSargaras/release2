/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/AssetLifeMakerEditCmd.java,v 1 2007/01/31 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.OBCollateralAssetLife;
import com.integrosys.cms.app.collateral.proxy.assetlife.ICollateralAssetLifeProxy;
import com.integrosys.cms.app.collateral.trx.assetlife.ICollateralAssetLifeTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.OBCollateralAssetLifeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to edit the year Description: command
 * that let the checker to save the year that being edited to the database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/31$ Tag: $Name$
 */

public class AssetLifeMakerEditCmd extends AbstractCommand {

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
		return (new String[][] { { "CollateralAssetLifes", "java.util.Collection", FORM_SCOPE },
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

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		Collection paramsCol = (Collection) map.get("CollateralAssetLifes");
		OBCollateralAssetLife[] obCollateralAssetLifes = null;
		obCollateralAssetLifes = new OBCollateralAssetLife[paramsCol.size()];
		obCollateralAssetLifes = (OBCollateralAssetLife[]) paramsCol.toArray(obCollateralAssetLifes);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		ICollateralAssetLifeTrxValue collateralAssetLifesTrxVal = (ICollateralAssetLifeTrxValue) map
				.get("CollateralAssetLifeTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obCollateralAssetLifes' = "
				+ obCollateralAssetLifes);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);

		try {
			ICollateralAssetLifeTrxValue trxValue = getCollateralAssetLifeProxy().makerUpdateCollateralAssetLife(
					trxContext, collateralAssetLifesTrxVal, obCollateralAssetLifes);
			resultMap.put("request.ITrxValue", trxValue);
			resultMap.put("CollateralAssetLifeTrxValue", trxValue);
		}
		catch (CollateralException e) {
			throw new CommandProcessingException("failed to update asset life", e);
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
