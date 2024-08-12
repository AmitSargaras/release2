/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/AddDepositCommand.java,v 1.6 2005/08/26 10:13:22 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.cash;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
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
import java.util.List;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/26 10:13:22 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class AddDepositCommand extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{{"form.depositObject", "java.util.HashMap", FORM_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                { "theOBTrxContext",
					"com.integrosys.cms.app.transaction.OBTrxContext",
					FORM_SCOPE },
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {"stpMode", "java.lang.String", REQUEST_SCOPE},
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
                       
				{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
		  
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
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                
                { "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
//			 { "facilityIdList", "java.util.List", SERVICE_SCOPE },
			 { "facilityIdList", "java.util.List", REQUEST_SCOPE }			 
        });
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
        String strStpMode = (String) map.get("stpMode");
        boolean stpMode = false; //Stp switch off
        if (StringUtils.isNotEmpty(strStpMode))
            stpMode = new Boolean(strStpMode).booleanValue();

        try {
            // Modified by KLYong: Stp FD Account Listing
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
                                        obCashDep.setLien(respCashDep.getLien());
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
                                exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Account number specified was not found."));
                            }
                        }
                    }
                }
            }

            ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
            ICashCollateral iCash = (ICashCollateral) itrxValue.getStagingCollateral();

            if (exceptionMap.isEmpty()) {
                addDeposit(iCash, obCashDep);
                itrxValue.setStagingCollateral(iCash);
                result.put("serviceColObj", itrxValue);

                try {
                    CollateralValuator valuator = new CollateralValuator();
                    valuator.setCollateralCMVFSV(iCash);
                }
                catch (Exception e) {
                    DefaultLogger.warn(this, "Collateral ID: \t [" + iCash.getCollateralID() + "] \t" +
                            "Security Number: \t [" + iCash.getSCISecurityID() + "] \t " +
                            "[Error in calculating cmv and fsv]", e);
                }
            }
        }
        catch (Exception e) {
            CommandProcessingException cpe = new CommandProcessingException(
                    "failed to add deposit into cash collateral");
            cpe.initCause(e);
            throw cpe;
        }
     /* String event = (String) map.get("event");
		
		DefaultLogger.debug(this, "Inside doExecute() AddPrepareLienCommand "+event);
		
		String indexID = (String) map.get("indexID");
		ILienMethod obLien = (OBLien)map.get("OBLien");
		
		List list = (List) map.get("lienList");
		ILienMethod ilien[] = new ILienMethod[list.size()];
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ILienMethod lienObj = new OBLien();
				lienObj = (ILienMethod) list.get(i);
				ilien[i] = lienObj;

			}
		}
		//obLien.get
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICashCollateral iCash = (ICashCollateral) (((ICollateralTrxValue) map.get("serviceColObj"))
                .getStagingCollateral());
		//iCash.getDepositInfo()[0].setLien(ilien);
		ICashDeposit[] cashDeposit = iCash.getDepositInfo();
		ICashDeposit cash = cashDeposit[0];
		cash.setLien(ilien);
		
		//iCash.setDepositInfo(cash)
		
        //iCash.setDepositInfo(depositInfo);
		
		result.put("OBLien", obLien);
		result.put("event",event);
		result.put("lienList", list);
		result.put("indexID", indexID);
		
		ICollateralTrxValue trxValue = new OBCollateralTrxValue();
		trxValue.setTrxContext(ctx);
		trxValue.setStagingCollateral(iCash);
		
		try {
			ICollateralTrxValue	trx = CollateralProxyFactory.getProxy().createLien(trxValue);
		} catch (CollateralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        result.put("facilityIdList", "");
        result.put("subtype", map.get("subtype"));
//		DefaultLogger.debug(this, "*******ResultMap is :" + map.get("subtype"));
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    public static void addDeposit(ICashCollateral iCash, ICashDeposit iCashDep) {
        ICashDeposit[] existingArray = iCash.getDepositInfo();
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        ICashDeposit[] newArray = new ICashDeposit[arrayLength + 1];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        newArray[arrayLength] = iCashDep;

        iCash.setDepositInfo(newArray);
    }

}
