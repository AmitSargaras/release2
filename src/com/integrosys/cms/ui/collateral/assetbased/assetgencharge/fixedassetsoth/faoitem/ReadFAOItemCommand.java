/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/faoitem/ReadFAOItemCommand.java,v 1.3 2005/04/01 09:41:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/04/01 09:41:11 $ Tag: $Name: $
 */

public class ReadFAOItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
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
				{ "faoItemObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageFAOItem", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers",
						REQUEST_SCOPE },
				{ "actualFAOItem",
						"com.integorsys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers",
						REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String strIndex = (String) map.get("indexID");

		String from_page = (String) map.get("from_page");

		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iCol = null;

		if ((from_page != null) && from_page.equals(EVENT_READ)) {
			iCol = (IGeneralCharge) trxValue.getCollateral();
		}
		else {
			iCol = (IGeneralCharge) trxValue.getStagingCollateral();
		}

		IFixedAssetOthers fao = null;
		if (strIndex.equals("-1")) {
			fao = new OBFixedAssetOthers();
			String generatedID = iCol.generateNewID(OBGeneralCharge.TYPE_FIXEDASSETOTHERS);
			DefaultLogger.debug(this, "<<<<<<<<<<<<<<< New Generated FAO ID: " + generatedID);
			fao.setFAOID(generatedID);
		}
		else {
			HashMap faoMap = (HashMap) iCol.getFixedAssetOthers();
			fao = (IFixedAssetOthers) faoMap.get(strIndex);
		}

		if (from_page.equals(EVENT_PROCESS)) {
			IFixedAssetOthers actualFao = null;
			HashMap actualFaoMap = (HashMap) ((IGeneralCharge) trxValue.getCollateral()).getFixedAssetOthers();
			if (actualFaoMap != null) {
				actualFao = (IFixedAssetOthers) actualFaoMap.get(strIndex);
			}
			result.put("actualFAOItem", actualFao);
			result.put("stageFAOItem", fao);
			if (fao == null) {
				fao = actualFao;
			}
		}

		HashMap returnMap = new HashMap();
		returnMap.put("obj", fao);
		returnMap.put("ccy", iCol.getCurrencyCode());
		returnMap.put("securityLocation", iCol.getCollateralLocation());

		result.put("faoItemObj", returnMap);
		result.put("from_page", from_page);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
