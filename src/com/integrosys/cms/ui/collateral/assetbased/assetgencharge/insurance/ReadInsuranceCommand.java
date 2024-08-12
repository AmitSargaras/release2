/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/ReadInsuranceCommand.java,v 1.8 2006/09/06 01:56:52 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/09/06 01:56:52 $ Tag: $Name: $
 */

public class ReadInsuranceCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "insuranceObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageInsurance", "com.integrosys.cms.app.collateral.bus.type.asset.IInsuranceInfo", REQUEST_SCOPE },
				{ "actualInsurance", "com.integrosys.cms.app.collateral.bus.type.asset.IInsuranceInfo", REQUEST_SCOPE },
				{ "col", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge",
						SERVICE_SCOPE }, { "le_id_bca_ref_num", "java.lang.String", REQUEST_SCOPE } });
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
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");

		String from_page = (String) map.get("from_page");
		String tab = (String) map.get("tab");

		IGeneralCharge iCol = null;
		try {
			if ((from_page != null) && from_page.equals(EVENT_READ)) {
				iCol = (IGeneralCharge) AccessorUtil.deepClone(trxValue.getCollateral());
			}
			else {
				iCol = (IGeneralCharge) AccessorUtil.deepClone(trxValue.getStagingCollateral());
			}
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

		String index = (String) map.get("indexID");

		IInsurancePolicy insurance = AssetGenChargeUtil.getInsuranceInfo(iCol, index);

		if ((from_page != null) && from_page.equals(EVENT_PROCESS)) {
			IInsurancePolicy actual = AssetGenChargeUtil.getInsuranceInfo((IGeneralCharge) trxValue.getCollateral(),
					index);

			result.put("actualInsurance", actual);
			result.put("stageInsurance", insurance);

			if (insurance == null) {
				insurance = actual;
			}
		}

		/*
		 * IInsurancePolicy insurance; if (from_event != null) { insurance =
		 * getItem(iCol.getInsurancePolicies(), indexStr); if (insurance == null
		 * && from_event.equals("process")) { insurance =
		 * getItem(((ICollateral)itrxValue
		 * .getCollateral()).getInsurancePolicies(), indexStr); } } else { int
		 * index = Integer.parseInt(indexStr); if ((int)index >= 0) { insurance
		 * = iCol.getInsurancePolicies()[index]; } else { insurance = null; } }
		 */
		String collateralId = (String) map.get("collateralID");
		long lCollateralId = 0;
		if ((collateralId != null) && (collateralId.trim().length() > 0)) {
			lCollateralId = Long.parseLong(collateralId);
		}
		long llmtProfileId = 0;
		if (insurance != null && insurance.getLmtProfileId() != null) {
			llmtProfileId = insurance.getLmtProfileId().longValue();
		}
		String sCollateralId = String.valueOf(lCollateralId);
		String slmtProfileId = String.valueOf(llmtProfileId);

		String leIdBcaRefNum = null;
		if ((slmtProfileId != null) && (slmtProfileId.trim().length() > 0)
				&& (llmtProfileId != ICMSConstant.LONG_INVALID_VALUE)) {

			try {
				leIdBcaRefNum = limitProxy.getLEIdAndBCARef(llmtProfileId);
			}
			catch (Exception e) {
				DefaultLogger.error(this, e.getMessage(), e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}

		HashMap insuranceMap = new HashMap();
		if (index.equals("-1")) {
			if (tab.equals(CollateralConstant.TAB_STOCK)) {
				insurance.setCategory(IInsurancePolicy.STOCK);
			}
			else {
				insurance.setCategory(IInsurancePolicy.FAO);
			}
		}
		insuranceMap = AssetGenChargeUtil.generateInsuranceMap(insurance, iCol);

		result.put("insuranceObj", insuranceMap);
		result.put("from_page", from_page);
		result.put("le_id_bca_ref_num", leIdBcaRefNum);
		if (from_page.equals(InsuranceAction.EVENT_PREPARE_UPDATE)
				|| from_page.equals(InsuranceAction.EVENT_PROCESS_UPDATE)) {
			result.put("col", iCol);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
