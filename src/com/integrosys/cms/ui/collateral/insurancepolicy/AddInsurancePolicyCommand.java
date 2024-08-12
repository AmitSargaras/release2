/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/AddInsurancePolicyCommand.java,v 1.4 2006/09/06 01:56:02 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.insurancepolicy;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_TOTAL_INSURANCE_POLICY_AMT;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/06 01:56:02 $ Tag: $Name: $
 */
public class AddInsurancePolicyCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "insurancePolicyObj", "com.integrosys.cms.app.collateral.bus.IInsurancePolicy", FORM_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "stpMode", "java.lang.String", REQUEST_SCOPE } });
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ SESSION_TOTAL_INSURANCE_POLICY_AMT, String.class.getName() , SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE } });
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
		// CollateralProxyFactory proxyFactory = new CollateralProxyFactory();
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		IInsurancePolicy insurance = (IInsurancePolicy) map.get("insurancePolicyObj");
		String strStpMode = (String) map.get("stpMode");
		boolean stpMode = false; // Stp switch off
		if (StringUtils.isNotEmpty(strStpMode))
			stpMode = new Boolean(strStpMode).booleanValue();

		try {
			// Added by KLYong: Stp Account Verification
			boolean isStpStatus = true;
			if (stpMode) {
				ArrayList stpArrlist = new ArrayList();
				if (!StringUtils.isEmpty(insurance.getDebitingACNo())) {
					ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
					stpArrlist.add(user);
					stpArrlist.add(insurance);
					try {
						StpSyncProxyImpl stpProxy = (StpSyncProxyImpl) BeanHouse.get("stpSyncProxy");
						Object objectMsg = stpProxy.submitTask(IStpTransType.TRX_TYPE_ACC_VERIFY, stpArrlist.toArray());
					}
					catch (StpCommonException e) {
						isStpStatus = false;
						exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc()));
					}
				}
			}
			// ********** End of Stp Account Veriifcation *************

			if (isStpStatus) {
				ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

				boolean policyNoExist = false;
				//policyNoExist = collateralProxy.getPolicyNumber(insurance.getPolicyNo(), insurance.getRefID());
				if (!policyNoExist) {
					ICollateral iCol = (ICollateral) itrxValue.getStagingCollateral();
					String collateralId = (String) map.get("collateralID");
					long lCollateralId = 0;
					if ((collateralId != null) && (collateralId.trim().length() > 0)) {
						lCollateralId = Long.parseLong(collateralId);
					}

					int documentCount = 0;
					String docNo = insurance.getDocumentNo();
					boolean isCreate = true;
					long insPolicyId = ICMSConstant.LONG_INVALID_VALUE;
					try {
						if ((docNo != null) && (docNo.trim().length() > 0)) {
							documentCount = collateralProxy.getDocumentNoCount(docNo, isCreate, insPolicyId,
									lCollateralId);
						}
					}
					catch (CollateralException e) {
						throw new CommandProcessingException("failed to retrieve document number count, for docNo ["
								+ docNo + "], isCreate [" + isCreate + "], insPolicyId [" + insPolicyId
								+ "], collateralId [" + lCollateralId + "]", e);
					}

					IInsurancePolicy insPolicies[] = iCol.getInsurancePolicies();
					if (insPolicies == null)
						insPolicies = new IInsurancePolicy[0];
					for (int i = 0; i < insPolicies.length; i++) {
						IInsurancePolicy tempInsPolicy = insPolicies[i];
						String tempDocNo = tempInsPolicy.getDocumentNo();
						if ((tempDocNo != null) && tempDocNo.trim().equalsIgnoreCase(docNo)) {
							documentCount++;
						}
					}

					if (SecuritySubTypeUtil.hasSameInsurancePolicy(iCol.getInsurancePolicies(), insurance, -1)) {
						exceptionMap.put("insuranceErr", new ActionMessage(
								"error.collateral.asset.gcharge.insurance.duplicate"));
					}
					else if (documentCount > 0) {
						exceptionMap.put("documentAlreadyBoundToInsurancePolicy", new ActionMessage(
								"error.collateral.insurancepolicy.documentNumberDuplicate"));
					}
					else {
						addInsurance(iCol, insurance); 
						CollateralHelper.updateSecurityCoverageDetails(iCol,null);
						String totalPolicyAmt = CollateralHelper.getTotalInsurancePolicyAmount(iCol,null);
						itrxValue.setStagingCollateral(iCol);
						result.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(iCol,null));
					}
				}
				else {
					//exceptionMap.put("insPolicyNum", new ActionMessage("error.collateral.insurance.policyNo.exist"));
				}
				result.put("serviceColObj", itrxValue);
				result.put("subtype", map.get("subtype"));
			}
		}
		catch (Exception ex) {
			throw new CommandProcessingException("failed to add insurance policy", ex);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static void addInsurance(ICollateral iCol, IInsurancePolicy insurance) {
		IInsurancePolicy[] existingArray = iCol.getInsurancePolicies();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IInsurancePolicy[] newArray = new IInsurancePolicy[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = insurance;

		iCol.setInsurancePolicies(newArray);
	}
}