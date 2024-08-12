/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/DeleteFAOCommand.java,v 1.2 2005/03/31 10:00:00 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth;

import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/31 10:00:00 $ Tag: $Name: $
 */

public class DeleteFAOCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "java.util.HashMap", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "faoSummaryList", "java.util.Collection", SERVICE_SCOPE }, });
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
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

		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");

		HashMap colMap = (HashMap) map.get("form.collateralObject");
		try {
			IGeneralCharge col = (IGeneralCharge) AccessorUtil.deepClone(colMap.get("col"));
			String[] deleteFaos = (String[]) colMap.get("deleteFaos");
			String[] deleteInsurances = (String[]) colMap.get("deleteInsurances");

			String event = (String) map.get("event");

			if (event.startsWith(CollateralConstant.FAO)) {
				// if fao still link to at least one insurance, cannot be
				// deleted
				HashMap faoMap = (HashMap) col.getFixedAssetOthers();
				HashMap faoInsuranceMap = (HashMap) col.get_FixedAssetOthers_Insurance_Map();
				boolean hasException = false;
				if (deleteFaos != null) {
					for (int i = 0; i < deleteFaos.length; i++) {
						Collection insuranceList = (Collection) faoInsuranceMap.get(deleteFaos[i]);
						if ((insuranceList != null) && !insuranceList.isEmpty()) {
							exceptionMap.put("faoItemDelete" + deleteFaos[i], new ActionMessage(
									"collateral.fao.delete.faoitem"));
							hasException = true;
						}
						else {
							faoMap.remove(deleteFaos[i]);
							faoInsuranceMap.remove(deleteFaos[i]);
						}
					}
				}
				if (hasException) {
					DefaultLogger.debug(this, "<<<<<<<< has Exception for deletion of fao");
					exceptionMap.put("faoItemDelete", new ActionMessage("error.collateral.fao.deletefao"));
				}
				else {
					col.setFixedAssetOthers(faoMap);
					col.set_FixedAssetOthers_Insurance_Map(faoInsuranceMap);
				}
			}
			else {
				// delete insurance regardless the linkage with fao still exist
				// if fao linkage still exist, delete the linkage too
				col = AssetGenChargeUtil.deleteInsuranceList(col, deleteInsurances, CollateralConstant.TAB_FAO);
			}

			DefaultLogger.debug(this, "ExceptionMap size: " + exceptionMap.size());

			HashMap returnMap = new HashMap();
			Collection faoSummaryList = (Collection) map.get("faoSummaryList");
			if ((exceptionMap != null) && !exceptionMap.isEmpty()) {
				returnMap.put("hasException", "true");
				col = (IGeneralCharge) trxValue.getStagingCollateral();
			}
			else {
				trxValue.setStagingCollateral(col);
				faoSummaryList = AssetGenChargeUtil.formatFixedAssetOthersList(col);
			}

			returnMap.put("col", col);
			returnMap.put("faoSummaryList", faoSummaryList);

			result.put("faoSummaryList", faoSummaryList);
			result.put("serviceColObj", trxValue);
			result.put("form.collateralObject", returnMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
