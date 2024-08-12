/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Purpose: Update Policy Cap Group Command
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 31/AUG/2007 $ Tag: $Name: $
 */
public class UpdatePolicyCapGroupCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public UpdatePolicyCapGroupCommand() {
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
				{ "policyCapGroupMap", "java.util.HashMap", FORM_SCOPE }, // Collection
																			// of
																			// com
																			// .
																			// integrosys
																			// .
																			// cms
																			// .
																			// app
																			// .
																			// creditriskparam
																			// .
																			// bus
																			// .
																			// policycap
																			// .
																			// OBPolicyCap
				{ "policyCapGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue", SERVICE_SCOPE },
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

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Updates to the policy cap is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> In Update Command!");

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		IPolicyCapGroupTrxValue policyCapGroupTrxVal = (IPolicyCapGroupTrxValue) map.get("policyCapGroupTrxValue");
		DefaultLogger.debug(this, ">>>>>>>>>>> trxVal:\n" + policyCapGroupTrxVal.getTransactionID());
		HashMap policyCapGroupMap = (HashMap) map.get("policyCapGroupMap");
		DefaultLogger.debug(this, ">>>>>>>>>>> policyCapGroupMap: " + policyCapGroupMap);

		IPolicyCapGroup policyCapGroup = (IPolicyCapGroup) policyCapGroupMap.get("policyCapGroup");

		IPolicyCap[] obPolicyCaps = policyCapGroup.getPolicyCapArray();
		DefaultLogger.debug(this, ">>>>>>>>>>> obPolicyCaps: " + obPolicyCaps);
		IPolicyCapProxyManager proxy = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
		DefaultLogger.debug(this, ">>>>>>>>>>> proxy: " + proxy);

		String isBankGroupString = (String) map.get("isBankGroup");
		boolean isBankGroup = (isBankGroupString == null) ? false : new Boolean(isBankGroupString).booleanValue();
		DefaultLogger.debug(this, ">>>>>>>>>>> isBankGroup : " + isBankGroup);

		try {

			if (!isBankGroup) {
				// validate against its bank group
				String bankGroup = this.getBankGroup(policyCapGroup);
				DefaultLogger.debug(this, ">>>>>>>>>>> bankGroup : " + bankGroup);
				String bankGroupName = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY_GROUP)
						.getCommonCodeLabel(bankGroup);
				validate(exceptionMap, policyCapGroup, bankGroup, bankGroupName, proxy);

			}

			if (exceptionMap.size() == 0) {
				IPolicyCapGroupTrxValue trxValue = proxy.makerUpdatePolicyCapGroup(trxContext, policyCapGroupTrxVal,
						policyCapGroup);
				DefaultLogger.debug(this, "trxValue=" + trxValue);
				resultMap.put("PolicyCapGroupTrxValue", trxValue);
				DefaultLogger.debug(this, "PolicyCapGroupTrxValue");
				resultMap.put("request.ITrxValue", trxValue);
				DefaultLogger.debug(this, "request.ITrxValue");
			}

			// Set CommonCodeList to Board Type
			CommonCodeList boardDescList = CommonCodeList.getInstance(null, null, "BOARD_TYPE", false, policyCapGroup
					.getStockExchange());

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	/**
	 * Validate against bank group caps
	 * @param exceptionMap To keep the action erros if one throws
	 * @param obPolicyCapGroup To Pass the current policyCapGroup
	 * @param bankGroup retrieved bank group for a bank entity
	 * @param proxy proxy of the SBPolicyCapProxy
	 * @throws PolicyCapException on errors
	 */
	private void validate(HashMap exceptionMap, IPolicyCapGroup obPolicyCapGroup, String bankGroup,
			String bankGroupName, IPolicyCapProxyManager proxy) throws PolicyCapException {

		if ((obPolicyCapGroup == null) || (bankGroup == null)) {
			return;
		}

		IPolicyCapGroup group = proxy.getPolicyCapGroupByExchangeBank(obPolicyCapGroup.getStockExchange(), bankGroup);
		IPolicyCap[] groupPolicyCaps = group.getPolicyCapArray();
		IPolicyCap[] submittedPolicyCaps = obPolicyCapGroup.getPolicyCapArray();

		for (int i = 0; i < submittedPolicyCaps.length; i++) {
			String currentBoard = submittedPolicyCaps[i].getBoard();

			for (int j = 0; j < groupPolicyCaps.length; j++) {
				String groupBoard = groupPolicyCaps[j].getBoard();

				if ((currentBoard != null) && (groupBoard != null)) {
					if (currentBoard.equalsIgnoreCase(groupBoard)) {

						DefaultLogger.debug(this, "currentBoard : " + currentBoard + ", groupBoard : " + groupBoard);
						// Checking MaxCollateralCapNonFI
						if (submittedPolicyCaps[i].getMaxCollateralCapNonFI() > groupPolicyCaps[j]
								.getMaxCollateralCapNonFI()) {
							DefaultLogger.debug(this, "... Submmited MaxCollateralCapNonFI exceeds Bank Group cap...");
							DefaultLogger.debug(this, "submittedPolicyCaps[" + i + "].getMaxCollateralCapNonFI() : "
									+ submittedPolicyCaps[i].getMaxCollateralCapNonFI());
							DefaultLogger.debug(this, "groupPolicyCaps[" + j + "].getMaxCollateralCapNonFI() : "
									+ groupPolicyCaps[j].getMaxCollateralCapNonFI());
							exceptionMap.put("maxCollateralCapNonFI" + i, new ActionMessage(
									"error.policycap.exceedGroupCap", bankGroupName, new Float(groupPolicyCaps[j]
											.getMaxCollateralCapNonFI())));
						}

						// Checking QuotaCollateralCapNonFI
						if (submittedPolicyCaps[i].getQuotaCollateralCapNonFI() > groupPolicyCaps[j]
								.getQuotaCollateralCapNonFI()) {
							DefaultLogger
									.debug(this, "... Submmited QuotaCollateralCapNonFI exceeds Bank Group cap...");
							DefaultLogger.debug(this, "submittedPolicyCaps[" + i + "].getQuotaCollateralCapNonFI() : "
									+ submittedPolicyCaps[i].getQuotaCollateralCapNonFI());
							DefaultLogger.debug(this, "groupPolicyCaps[" + j + "].getQuotaCollateralCapNonFI() : "
									+ groupPolicyCaps[j].getQuotaCollateralCapNonFI());
							exceptionMap.put("quotaCollateralCapNonFI" + i, new ActionMessage(
									"error.policycap.exceedGroupCap", bankGroupName, new Float(groupPolicyCaps[j]
											.getQuotaCollateralCapNonFI())));
						}

						// Checking MaxCollateralCapFI
						if (submittedPolicyCaps[i].getMaxCollateralCapFI() > groupPolicyCaps[j].getMaxCollateralCapFI()) {
							DefaultLogger.debug(this, "... Submmited MaxCollateralCapFI exceeds Bank Group cap...");
							DefaultLogger.debug(this, "submittedPolicyCaps[" + i + "].getMaxCollateralCapFI() : "
									+ submittedPolicyCaps[i].getMaxCollateralCapFI());
							DefaultLogger.debug(this, "groupPolicyCaps[" + j + "].getMaxCollateralCapFI() : "
									+ groupPolicyCaps[j].getMaxCollateralCapFI());
							exceptionMap.put("maxCollateralCapFI" + i, new ActionMessage(
									"error.policycap.exceedGroupCap", bankGroupName, new Float(groupPolicyCaps[j]
											.getMaxCollateralCapFI())));
						}

						// Checking QuotaCollateralCapNonFI
						if (submittedPolicyCaps[i].getQuotaCollateralCapFI() > groupPolicyCaps[j]
								.getQuotaCollateralCapFI()) {
							DefaultLogger.debug(this, "... Submmited QuotaCollateralCapFI exceeds Bank Group cap...");
							DefaultLogger.debug(this, "submittedPolicyCaps[" + i + "].getQuotaCollateralCapFI() : "
									+ submittedPolicyCaps[i].getQuotaCollateralCapFI());
							DefaultLogger.debug(this, "groupPolicyCaps[" + j + "].getQuotaCollateralCapFI() : "
									+ groupPolicyCaps[j].getQuotaCollateralCapFI());
							exceptionMap.put("quotaCollateralCapFI" + i, new ActionMessage(
									"error.policycap.exceedGroupCap", bankGroupName, new Float(groupPolicyCaps[j]
											.getQuotaCollateralCapFI())));
						}
					}
				}
				else {
					throw new PolicyCapException("currentBoard or groupBoard is null");
				}
			}
		}
	}

	/**
	 * Retrieve bankGroup by given policyCapGroup, that contains he Bank entity
	 * @param policyCapGroup
	 * @return
	 */
	private String getBankGroup(IPolicyCapGroup policyCapGroup) {
		Collection c = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY_GROUP).getCommonCodeValues();
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Collection c2 = CommonCodeList.getInstance(null, null, CategoryCodeConstant.BANK_ENTITY, key)
					.getCommonCodeValues();
			if (c2.contains(policyCapGroup.getBankEntity())) {
				return key;
			}
		}
		return null;
	}
}
