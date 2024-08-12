/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/UpdateSecurityCommand.java,v 1.7 2006/07/13 06:35:59 jzhai Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/07/13 06:35:59 $ Tag: $Name: $
 */

public class UpdateSecurityCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commSecObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral", FORM_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
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

		int index = Integer.parseInt((String) map.get("indexID"));
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral iColObj = (ICommodityCollateral) map.get("commSecObj");
		ICommodityCollateral[] actualList = (ICommodityCollateral[]) trxValueMap.get("actual");
		ICommodityCollateral actualCol = actualList[index];
		boolean isCheckListCompleted = false;
		boolean isSecurityNoChecklist = true;
		boolean isSecurityLocationChange = false;
		boolean isSecurityOrganizationChange = false;

		String originalLocation = actualCol.getCollateralLocation();
		if (((iColObj.getCollateralLocation() == null) && (originalLocation != null))
				|| !iColObj.getCollateralLocation().equals(originalLocation)) {
			isSecurityLocationChange = true;
		}

		String securityOrganization = actualCol.getSecurityOrganization();
		if (!iColObj.getSecurityOrganization().equals(securityOrganization)) {
			isSecurityOrganizationChange = true;
		}

		try {
			isCheckListCompleted = CollateralProxyFactory.getProxy().isCollateralCheckListCompleted(actualCol);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Getting isCheckListCompleted throws Exception");
		}

		try {
			isSecurityNoChecklist = CollateralProxyFactory.getProxy().isCollateralNoChecklist(
					actualCol.getCollateralID());
			DefaultLogger.debug(this, "security location change - isSecurityNoChecklist: " + isSecurityNoChecklist);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Getting isSecurityNoChecklist throws Exception");
		}

		if (isSecurityLocationChange && !isSecurityNoChecklist) {
			exceptionMap.put("securityLocation", new ActionMessage("error.collateral.securityLocation"));
		}

		if (isSecurityOrganizationChange && !isSecurityNoChecklist) {
			exceptionMap.put("securityOrganization", new ActionMessage("error.collateral.securityOrganization"));
		}

		if (!isCheckListCompleted) {
			if ((iColObj.getIsLE() != null) && iColObj.getIsLE().equals(ICMSConstant.TRUE_VALUE)) {
				exceptionMap.put("le", new ActionMessage("error.collateral.le.yes.disallowed",
						ICMSConstant.STATE_CHECKLIST_COMPLETED));
			}
		}

		/*
		 * if (iColObj.getLimitCharges() != null &&
		 * iColObj.getLimitCharges().length > 0) { ILimitCharge limit =
		 * iColObj.getLimitCharges()[0]; if (!isCheckListCompleted) { if
		 * (limit.getIsLEByChargeRanking() != null &&
		 * limit.getIsLEByChargeRanking().equals(ICMSConstant.TRUE_VALUE)) {
		 * exceptionMap.put("leCharge", new
		 * ActionMessage("error.collateral.le.yes.disallowed")); } if
		 * (limit.getIsLEByJurisdiction() != null &&
		 * limit.getIsLEByJurisdiction().equals(ICMSConstant.TRUE_VALUE)) {
		 * exceptionMap.put("leJurisdiction", new
		 * ActionMessage("error.collateral.le.yes.disallowed")); } if
		 * (limit.getIsLEByGovernLaws() != null &&
		 * limit.getIsLEByGovernLaws().equals(ICMSConstant.TRUE_VALUE)) {
		 * exceptionMap.put("leGoverningLaw", new
		 * ActionMessage("error.collateral.le.yes.disallowed")); } if
		 * (limit.getIsLE() != null &&
		 * limit.getIsLE().equals(ICMSConstant.TRUE_VALUE)) {
		 * exceptionMap.put("le", new
		 * ActionMessage("error.collateral.le.yes.disallowed")); } }
		 */
		/*
		 * if (isCheckListCompleted) { if (limit.getIsLEByChargeRanking() !=
		 * null &&
		 * limit.getIsLEByChargeRanking().equals(ICMSConstant.TRUE_VALUE) &&
		 * limit.getLEDateByChargeRanking() == null) {
		 * exceptionMap.put("leDateCharge", new
		 * ActionMessage("error.date.mandatory")); } if
		 * (limit.getIsLEByJurisdiction() != null &&
		 * limit.getIsLEByJurisdiction().equals(ICMSConstant.TRUE_VALUE) &&
		 * limit.getLEDateByJurisdiction() == null) {
		 * exceptionMap.put("leDateJurisdiction", new
		 * ActionMessage("error.date.mandatory")); } if
		 * (limit.getIsLEByGovernLaws() != null &&
		 * limit.getIsLEByGovernLaws().equals(ICMSConstant.TRUE_VALUE) &&
		 * limit.getLEDateByGovernLaws() == null) {
		 * exceptionMap.put("leDateGovernginLaw", new
		 * ActionMessage("error.date.mandatory")); } if (limit.getIsLE() != null
		 * && limit.getIsLE().equals(ICMSConstant.TRUE_VALUE) &&
		 * limit.getLEDate() == null) { exceptionMap.put("leDate", new
		 * ActionMessage("error.date.mandatory")); } }
		 */
		if (isCheckListCompleted) {
			if ((iColObj.getIsLE() != null) && iColObj.getIsLE().equals(ICMSConstant.TRUE_VALUE)
					&& (iColObj.getLEDate() == null)) {
				exceptionMap.put("leDate", new ActionMessage("error.date.mandatory"));
			}
		}

		if (exceptionMap.isEmpty()) {
			try {
				CollateralValuator valuator = new CollateralValuator();
				iColObj.setCollateralID(actualCol.getCollateralID());
				valuator.setCollateralCMVFSV(iColObj);
			}
			catch (Exception e) {
				// do nothing.
			}

			ICommodityCollateral[] stageList = (ICommodityCollateral[]) trxValueMap.get("staging");
			stageList[index] = iColObj;
			trxValueMap.put("staging", stageList);
		}

		result.put("commodityMainTrxValue", trxValueMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
