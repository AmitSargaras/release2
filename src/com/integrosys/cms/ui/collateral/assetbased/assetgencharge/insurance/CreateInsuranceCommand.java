/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/CreateInsuranceCommand.java,v 1.7 2006/09/06 01:56:37 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/06 01:56:37 $ Tag: $Name: $
 */

public class CreateInsuranceCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "insuranceObj", "java.util.HashMap", FORM_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE }, { "tab", "java.lang.String", SERVICE_SCOPE },
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
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE }, });
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
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();

		String tab = (String) map.get("tab");
		result.put("tab", tab);

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		HashMap insuranceMap = (HashMap) map.get("insuranceObj");
		IGeneralCharge iCol = (IGeneralCharge) insuranceMap.get("col");
		String strKey = (String) insuranceMap.get("strKey");
		DefaultLogger.debug(this, "strKey: " + strKey);

		ICollateral iCollateral = (ICollateral) itrxValue.getStagingCollateral();
		HashMap insuranceMap1 = (HashMap) iCol.getInsurance();
		IInsurancePolicy insurance = (IInsurancePolicy) insuranceMap1.get(strKey);

		String collateralId = (String) map.get("collateralID");
		long lCollateralId = 0;
		if ((collateralId != null) && (collateralId.trim().length() > 0)) {
			lCollateralId = Long.parseLong(collateralId);
		}
		DefaultLogger.debug(this, "CollId is :" + lCollateralId);

		int documentCount = 0;
		String docNo = insurance.getDocumentNo();
		boolean isCreate = true;
		long insPolicyId = 0;
		try {
			if ((docNo != null) && (docNo.trim().length() > 0)) {
				documentCount = collateralProxy.getDocumentNoCount(docNo, isCreate, insPolicyId, lCollateralId);
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage(), e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "*******documentCount is :" + documentCount);

		IInsurancePolicy insPolicies[] = iCollateral.getInsurancePolicies();
		for (int i = 0; i < insPolicies.length; i++) {
			IInsurancePolicy tempInsPolicy = insPolicies[i];
			String tempDocNo = tempInsPolicy.getDocumentNo();
			if ((tempDocNo != null) && tempDocNo.trim().equalsIgnoreCase(docNo)) {
				documentCount++;
			}
		}

		if (AssetGenChargeUtil.hasSameInsurancePolicy((HashMap) iCol.getInsurance(), strKey)) {
			exceptionMap.put("insuranceErr", new ActionMessage("error.collateral.asset.gcharge.insurance.duplicate"));
		}
		else if (documentCount > 0) {
			exceptionMap.put("documentAlreadyBoundToInsurancePolicy", new ActionMessage(
					"error.collateral.insurancepolicy.documentNumberDuplicate"));
		}
		else {
			iCol.setLastUsedInsuranceIndex(iCol.getLastUsedInsuranceIndex() + 1);
			itrxValue.setStagingCollateral(iCol);
			result.put("serviceColObj", itrxValue);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
