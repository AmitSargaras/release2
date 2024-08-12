/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/RefreshInsurancePolicyCommand.java,v 1.2 2006/04/11 09:05:36 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.insurancepolicy;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/04/11 09:05:36 $ Tag: $Name: $
 */
public class RefreshInsurancePolicyCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "insurancePolicyObj", "com.integrosys.cms.app.collateral.bus.IInsurancePolicy", FORM_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "lmtProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
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
		return (new String[][] { { "insurancePolicyObj", "java.util.HashMap", FORM_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitProfileIds", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leid_bcarefIds", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }

		});
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
		OBInsurancePolicy obInsurancePolicy = (OBInsurancePolicy) map.get("insurancePolicyObj");
		//ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		//ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		String from_event = (String) map.get("event");
		String docsIdFromForm = (String) obInsurancePolicy.getDocumentNo();
		DefaultLogger.debug(this, "docsIdFromForm:" + docsIdFromForm);
		DefaultLogger.debug(this, "from_event:" + from_event);

		String collateralId = (String) map.get("collateralID");
		//String lmtProfileId = null;
		long lCollateralId = 0;
		//long llmtProfileId = 0;
		DefaultLogger.debug(this, "CollateralId:" + collateralId);

		if ((collateralId != null) && (collateralId.trim().length() > 0)) {
			lCollateralId = Long.parseLong(collateralId);
		}
		DefaultLogger.debug(this, "lCollateralId:" + lCollateralId);

		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		HashMap insuranceMap = new HashMap();
		insuranceMap.put("obj", map.get("insurancePolicyObj"));
		insuranceMap.put("col", trxValue.getStagingCollateral());

		result.put("insurancePolicyObj", insuranceMap);

		/*
		lmtProfileId = String.valueOf(obInsurancePolicy.getLmtProfileId());

		if ((lmtProfileId != null) && (lmtProfileId.trim().length() > 0)) {
			llmtProfileId = Long.parseLong(lmtProfileId);
		}
		*/
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
