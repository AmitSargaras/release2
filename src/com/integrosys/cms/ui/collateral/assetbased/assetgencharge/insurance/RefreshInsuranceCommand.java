/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/RefreshInsuranceCommand.java,v 1.7 2006/04/11 09:02:05 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/04/11 09:02:05 $ Tag: $Name: $
 */
public class RefreshInsuranceCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "insuranceObj", "java.util.HashMap", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "tab", "java.lang.String", SERVICE_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "lmtProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE }

		});
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
				{ "col", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge",
						SERVICE_SCOPE }, { "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE } });
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

		// get insurance obj at set in result
		HashMap objMap = (HashMap) map.get("insuranceObj");
		IGeneralCharge iCol = (IGeneralCharge) objMap.get("col");

		String index = (String) objMap.get("strKey");

		String tab = (String) map.get("tab");

		IInsurancePolicy insurance = AssetGenChargeUtil.getInsuranceInfo(iCol, index);

		HashMap insuranceMap = AssetGenChargeUtil.generateInsuranceMap(insurance, iCol);

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();

		String from_event = (String) map.get("event");
		DefaultLogger.debug(this, "from_event:" + from_event);
		String docsIdFromForm = (String) map.get("documentNo");
		DefaultLogger.debug(this, "docsIdFromForm:" + docsIdFromForm);
		DefaultLogger.debug(this, "from_event:" + from_event);

		String collateralId = (String) map.get("collateralID");

		String lmtProfileId = null;
		long lCollateralId = 0;
		long llmtProfileId = 0;
		DefaultLogger.debug(this, "CollateralId:" + collateralId);

		if ((collateralId != null) && (collateralId.trim().length() > 0)) {
			lCollateralId = Long.parseLong(collateralId);
		}

		DefaultLogger.debug(this, "lCollateralId:" + lCollateralId);

		lmtProfileId = (String) map.get("lmtProfileId");

		if ((lmtProfileId != null) && (lmtProfileId.trim().length() > 0)) {
			llmtProfileId = Long.parseLong(lmtProfileId);
		}

		result.put("insuranceObj", insuranceMap);
		result.put("col", iCol);
		// result.put("strKey", index);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
