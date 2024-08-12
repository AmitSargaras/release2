/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/ReadFAOCommand.java,v 1.7 2005/05/25 03:07:41 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/05/25 03:07:41 $ Tag: $Name: $
 */

public class ReadFAOCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
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
		return (new String[][] { { "form.collateralObject", "java.util.HashMap", FORM_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "faoSummaryList", "java.util.Collection", SERVICE_SCOPE }, });
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

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		HashMap returnMap = new HashMap();
		IGeneralCharge iCol = null;
		String from_event = (String) map.get("from_page");
		if (itrxValue != null) {
			if ((from_event != null) && from_event.equals(EVENT_READ)) {
				iCol = (IGeneralCharge) itrxValue.getCollateral();
			}
			else {
				iCol = (IGeneralCharge) itrxValue.getStagingCollateral();
			}
		}

		Collection faoSummaryList = new ArrayList();
		if ((from_event != null) && from_event.equals(EVENT_PROCESS)) {
			IGeneralCharge actualCol = (IGeneralCharge) itrxValue.getCollateral();
			IFixedAssetOthers[] actualFaoList = null;
			if ((actualCol.getFixedAssetOthers() != null) && (actualCol.getFixedAssetOthers().size() > 0)) {
				actualFaoList = (IFixedAssetOthers[]) actualCol.getFixedAssetOthers().values().toArray(
						new IFixedAssetOthers[0]);
			}
			IFixedAssetOthers[] stageFaoList = null;
			if ((iCol.getFixedAssetOthers() != null) && (iCol.getFixedAssetOthers().size() > 0)) {
				stageFaoList = (IFixedAssetOthers[]) iCol.getFixedAssetOthers().values().toArray(
						new IFixedAssetOthers[0]);
			}
			try {
				if ((actualFaoList != null) || (stageFaoList != null)) {
					List compareResultList = CompareOBUtil.compOBArray(stageFaoList, actualFaoList);
					faoSummaryList = AssetGenChargeUtil.formatFixedAssetOthersList(itrxValue, compareResultList);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			faoSummaryList = AssetGenChargeUtil.formatFixedAssetOthersList(iCol);
			/*
			 * remove the setting of insurance coverage amount to mapping object
			 * if (from_event != null &&
			 * (from_event.equals(FAOAction.EVENT_PREPARE_UPDATE) ||
			 * from_event.equals(FAOAction.EVENT_PROCESS_UPDATE))) { iCol =
			 * AssetGenChargeUtil.setInsuranceCoverageAmt(faoSummaryList, iCol,
			 * CollateralConstant.TAB_FAO); }
			 */
		}
		returnMap.put("faoSummaryList", faoSummaryList);
		returnMap.put("col", iCol);

		result.put("form.collateralObject", returnMap);
		result.put("faoSummaryList", faoSummaryList);
		result.put("tab", CollateralConstant.TAB_FAO);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
