/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/UpdateInsurancePolicyCommand.java,v 1.7 2006/09/18 09:50:55 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.insurancepolicy;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_TOTAL_INSURANCE_POLICY_AMT;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/18 09:50:55 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class UpdateInsurancePolicyCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "insurancePolicyObj", "com.integrosys.cms.app.collateral.bus.IInsurancePolicy", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
                { IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                { "stpMode", "java.lang.String", REQUEST_SCOPE }} );
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
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "currencyList", "java.util.List", SERVICE_SCOPE },
				{ SESSION_TOTAL_INSURANCE_POLICY_AMT, String.class.getName() , SERVICE_SCOPE },
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
		// CollateralProxyFactory proxyFactory = new CollateralProxyFactory();
		// ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();

		IInsurancePolicy insurance = (IInsurancePolicy) map.get("insurancePolicyObj");
		int index = Integer.parseInt((String) map.get("indexID"));
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICollateral iCol = (ICollateral) itrxValue.getStagingCollateral();
		IInsurancePolicy insuranceArray[] = iCol.getInsurancePolicies();
        String strStpMode = (String) map.get("stpMode");
        // String docNo = insurance.getDocumentNo();
        boolean stpMode = false; //Stp switch off
        if (StringUtils.isNotEmpty(strStpMode))
            stpMode = new Boolean(strStpMode).booleanValue();

        try {
            //Added by KLYong: Stp Account Verification
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
                    } catch (StpCommonException e) {
                        isStpStatus = false;
                        exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc()));
                    }
                }
            }
            //********** End of Stp Account Veriifcation *************

            if (isStpStatus) {
                String collateralId = (String) map.get("collateralID");
                long lCollateralId = 0;
                if ((collateralId != null) && (collateralId.trim().length() > 0)) {
                    lCollateralId = Long.parseLong(collateralId);
                }
                DefaultLogger.debug(this, "CollId is :" + lCollateralId);
                DefaultLogger.debug(this, "remark :" + insurance.getRemark1());
                DefaultLogger.debug(this, "new amount :" + insurance.getNewAmtInsured());
                DefaultLogger.debug(this, "roof:" + insurance.getRoof());

                int documentCount = SecuritySubTypeUtil.getInsuranceDocumentCount(insurance, iCol, lCollateralId, index);
                /*
                 * boolean isCreate =false; long insPolicyId =
                 * ICMSConstant.LONG_INVALID_VALUE; insPolicyId =
                 * insurance.getInsurancePolicyID();
                 * DefaultLogger.debug(this,"documentId is :"+docNo);
                 * DefaultLogger.debug(this,"insId is :"+insPolicyId);
                 *
                 *
                 * try{ if(docNo!=null && docNo.trim().length()>0 &&
                 * insPolicyId!=ICMSConstant.LONG_INVALID_VALUE){ documentCount =
                 * collateralProxy
                 * .getDocumentNoCount(docNo,isCreate,insPolicyId,lCollateralId); } else
                 * documentCount =
                 * collateralProxy.getDocumentNoCount(docNo,true,ICMSConstant
                 * .LONG_INVALID_VALUE,lCollateralId);
                 *
                 * } catch (Exception e) { DefaultLogger.error(this, e.getMessage(), e);
                 * e.printStackTrace(); throw (new
                 * CommandProcessingException(e.getMessage())); } IInsurancePolicy
                 * insPolicies[] = iCol.getInsurancePolicies(); for(int
                 * i=0;i<insPolicies.length;i++){ IInsurancePolicy tempInsPolicy =
                 * insPolicies[i]; String tempDocNo = tempInsPolicy.getDocumentNo();
                 * if(insPolicyId!=ICMSConstant.LONG_INVALID_VALUE){ if(tempDocNo!=null
                 * && tempDocNo.trim().equalsIgnoreCase(docNo) &&
                 * tempInsPolicy.getInsurancePolicyID()!=insPolicyId ){
                 * DefaultLogger.debug(this,"documentCount is incrementing");
                 * DefaultLogger
                 * .debug(this,"documentIdtemp is :"+tempInsPolicy.getInsurancePolicyID
                 * ()); DefaultLogger.debug(this,"documentIdorigi is :"+insPolicyId);
                 * documentCount++; } } //Commented by Pratheepa on 09/06/2006 to fix
                 * issue CMS-3079 else{ if(tempDocNo!=null &&
                 * tempDocNo.trim().equalsIgnoreCase(docNo) ){ documentCount++; } } }
                 */
                DefaultLogger.debug(this, "*******documentCount is :" + documentCount);
                if (SecuritySubTypeUtil.hasSameInsurancePolicy(insuranceArray, insurance, index)) {
                    exceptionMap.put("insuranceErr", new ActionMessage("error.collateral.asset.gcharge.insurance.duplicate"));
                }
                else if (documentCount > 0) {
                    exceptionMap.put("documentAlreadyBoundToInsurancePolicy", new ActionMessage(
                            "error.collateral.insurancepolicy.documentNumberDuplicate"));
                }
                else {
                    insuranceArray[index] = insurance;
                }
                iCol.setInsurancePolicies(insuranceArray);
                CollateralHelper.updateSecurityCoverageDetails(iCol,null);
                itrxValue.setStagingCollateral(iCol);

                result.put("serviceColObj", itrxValue);
                result.put("subtype", map.get("subtype"));
                result.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(iCol,null));
            }
        } catch (Exception e) {
            DefaultLogger.error(this, "got exception in doExecute", e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
