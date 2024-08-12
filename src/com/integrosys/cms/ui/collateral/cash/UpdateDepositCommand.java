/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/UpdateDepositCommand.java,v 1.7 2005/08/26 10:13:22 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.cash;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.IStpSyncProxy;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/26 10:13:22 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class UpdateDepositCommand extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{{"form.depositObject", "java.util.HashMap", FORM_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE}, {"subtype", "java.lang.String", REQUEST_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                {"stpMode", "java.lang.String", REQUEST_SCOPE},
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE}
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
        return (new String[][]{
        		{ "indexID", "java.lang.String", REQUEST_SCOPE },
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},});
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        HashMap objMap = (HashMap) map.get("form.depositObject");
        OBCashDeposit obCashDep = (OBCashDeposit) objMap.get("deposit");
        String indexID = (String) map.get("indexID");
        String strStpMode = (String) map.get("stpMode");
        boolean stpMode = false; //Stp switch off
        if (StringUtils.isNotEmpty(strStpMode))
            stpMode = new Boolean(strStpMode).booleanValue();

        try {
            //Modified by KLYong: Stp FD Account Listing
            boolean isValidDeposit = false;
            if (stpMode && obCashDep.getOwnBank()) {
                if (!StringUtils.isEmpty(obCashDep.getDepositRefNo())) {
                    ArrayList stpArrlist = new ArrayList();
                    ArrayList depositList = new ArrayList();
                    HashMap stpMapParam = new HashMap();
                    ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
                    stpMapParam.put(IStpConstants.RES_RECORD_RETURN, PropertyManager.getValue(IStpConstants.STP_SKT_HDR_NUMBER_RECORD));
                    ILimitProfile lmtProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

                    stpArrlist.add(user);
                    stpArrlist.add(stpMapParam);
                    stpArrlist.add(obCashDep);
                    try {
                        IStpSyncProxy stpProxy = (IStpSyncProxy) BeanHouse.get("stpSyncProxy");
                        depositList = (ArrayList) stpProxy.submitTask(IStpTransType.TRX_TYPE_SEARCH_FD_ACCT_LIST,
                                stpArrlist.toArray());
                    }
                    catch (StpCommonException e) {
                        exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc()));
                    }

                    if (exceptionMap.isEmpty()) {
                        String accountNum = obCashDep.getDepositReceiptNo();

                        if (!StringUtils.isEmpty(accountNum)) { // Account number specified
                            for (int i = 0; i < depositList.size(); i++) {
                                OBCashDeposit respCashDep = (OBCashDeposit) depositList.get(i);
                                if (StringUtils.equals(respCashDep.getDepositReceiptNo(), accountNum)) {
                                    //Andy Wong, 6 July 2009: condition checking on hold status and col exists flag to allow pledge
                                    if (StringUtils.equals(respCashDep.getCollateralExists(), "N") &&
                                            ((StringUtils.equals("CC", lmtProfile.getApplicationType()) && (StringUtils.equals("C", respCashDep.getHoldStatus()) || StringUtils.isEmpty(respCashDep.getHoldStatus())))
                                                    ||
                                                    (!StringUtils.equals("CC", lmtProfile.getApplicationType()) && (StringUtils.equals("L", respCashDep.getHoldStatus()) || StringUtils.equals("M", respCashDep.getHoldStatus()) || StringUtils.isEmpty(respCashDep.getHoldStatus()))))) {
                                        obCashDep.setDepositAmount(respCashDep.getDepositAmount());
                                        obCashDep.setDepositMaturityDate(respCashDep.getDepositMaturityDate());
                                        obCashDep.setTenure(respCashDep.getTenure());
                                        obCashDep.setTenureUOM(respCashDep.getTenureUOM());
                                        obCashDep.setIssueDate(respCashDep.getIssueDate());
                                    } else {
                                        CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.FD_HOLD_STATUS);
                                        exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "FD cannot be pledged. " + (commonCode.getCommonCodeLabel(respCashDep.getHoldStatus())!=null?commonCode.getCommonCodeLabel(respCashDep.getHoldStatus()):"Available")
                                                + ", Collateral Exists : " + (StringUtils.equals("Y", respCashDep.getCollateralExists()) ? "Yes" : "No")));
                                    }

                                    isValidDeposit = true; // If found and not hold by loan and credit card
                                    break;
                                }
                            }
                            if (!isValidDeposit && exceptionMap.isEmpty()) { // Not valid
                                exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY,
                                        "Account number specified was not found."));
                            }
                        }
                    }
                }
            }

            ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
            ICashCollateral iCashCollateral = (ICashCollateral) itrxValue.getStagingCollateral();

            if (exceptionMap.isEmpty()) {
                int index = Integer.parseInt((String) map.get("indexID"));
                DefaultLogger.debug(this, "SubType:" + map.get("subtype"));
                DefaultLogger.debug(this, "Index is:" + index);

                ICashDeposit depArray[] = iCashCollateral.getDepositInfo();
                depArray[index] = obCashDep;
                iCashCollateral.setDepositInfo(depArray);

                itrxValue.setStagingCollateral(iCashCollateral);
                result.put("serviceColObj", itrxValue);
                result.put("subtype", map.get("subtype"));
            }

            try {
                DefaultLogger.debug(this, ">>>>>>>>>>>>> calling valuator");
                String isChanged = (String) objMap.get("isChanged");
                if ("true".equals(isChanged)) {
                    CollateralValuator valuator = new CollateralValuator();
                    valuator.setCollateralCMVFSV(iCashCollateral);
                    DefaultLogger.debug(this, ">>>>>>>>>>>>> iCash.getValuation = " + iCashCollateral.getValuation());
                }
            }
            catch (Exception e) {
                DefaultLogger.warn(this, "Collateral ID: \t [" + iCashCollateral.getCollateralID() + "] \t" +
                        "Security Number: \t [" + iCashCollateral.getSCISecurityID() + "] \t " +
                        "[Error in calculating cmv and fsv]", e);
            }


        } catch (Exception e) {
            DefaultLogger.error(this, "got exception in doExecute", e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        result.put("indexID", indexID);
        result.put("subtype", map.get("subtype"));
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

}
