/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/AssetLifeMakerReadCmd.java,v 1 2007/01/31 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to get the value from storage
 * Description: command that help the maker to get the value from storage to let
 * the user to edit or view the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/31$ Tag: $Name$
 */

public class AssetLifeMakerReadCmd extends AbstractCommand {

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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialCollateralAssetLife", "com.integrosys.cms.app.collateral.bus.OBCollateralAssetLife",
						FORM_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialCollateralAssetLife", "java.util.list", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get the value for Asset Life from ob
	 * is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBCollateralAssetLife obCollateralSybType = (OBCollateralAssetLife) map.get("InitialCollateralAssetLife");

		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event
				+ ", securityAssetLifeID=+ obCollateralSybType.getTypeCode()");
		try {

			if (!event.equals("maker_edit_assetlife_confirm_error")) {

				ICollateralAssetLifeTrxValue collateralAssetLifeTrxVal = getCollateralAssetLifeProxy()
						.getCollateralAssetLifeTrxValue(trxContext);

				if (MaintainAssetLifeAction.EVENT_VIEW.equals(event)) {
					resultMap.put("CollateralAssetLifeTrxValue", collateralAssetLifeTrxVal);
					resultMap.put("InitialCollateralAssetLife", collateralAssetLifeTrxVal.getCollateralAssetLifes());

				}
				else {
					// if current status is other than ACTIVE & REJECTED, then
					// show workInProgress.
					// i.e. allow edit only if status is either ACTIVE or
					// REJECTED
					if (!((collateralAssetLifeTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (collateralAssetLifeTrxVal
							.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {

						resultMap.put("wip", "wip");
						resultMap.put("InitialCollateralAssetLife", collateralAssetLifeTrxVal
								.getStagingCollateralAssetLifes());

					}
					else {
						resultMap.put("CollateralAssetLifeTrxValue", collateralAssetLifeTrxVal);
					}

					resultMap.put("InitialCollateralAssetLife", collateralAssetLifeTrxVal.getCollateralAssetLifes());
				}
			}

			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		}
		catch (CollateralException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
