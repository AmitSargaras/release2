package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.*;
import com.integrosys.cms.app.creditriskparam.proxy.bankentitybranch.IBankEntityBranchParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 4:53:34 PM
 * Desc: List bank entity branch param command class
 */
//public class ListBankEntityBranchParamCmd
//        extends AbstractCommand {
public class ListBankEntityBranchParamCmd
    extends BankEntityBranchParamCmd {

    public String[][] getParameterDescriptor() {
        return new String[][]{
//                {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        };
    }


    public String[][] getResultDescriptor() {
        return new String[][]{// Produce all the feed entries.
                {"bankEntityBranchTrxValue", "com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue", SERVICE_SCOPE},
                {"BankEntityBranchParamForm", "java.lang.Object", FORM_SCOPE},
                {"wip", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
        };
    }


    public HashMap doExecute(HashMap hashMap)
            throws CommandValidationException, CommandProcessingException,
            AccessDeniedException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        OBTrxContext trxContext = (OBTrxContext) hashMap.get("theOBTrxContext");
        IBankEntityBranchTrxValue trxValue = null;
        IBankEntityBranchParamList branchParamList = new OBBankEntityBranchParamList();
        String event = (String) hashMap.get("event");
        String trxID = (String) hashMap.get("TrxId");

//        IBankEntityBranchParamProxy proxy = BankEntityBranchParamProxyFactory.getProxyManager();
        IBankEntityBranchParamProxy proxy = getBankEntityBranchParamProxy();
        try {
            if ("maker_prepare_resubmit".equals(event)
                    || "maker_prepare_close".equals(event)
                    || "to_track".equals(event)
                    || "checker_prepare".equals(event)) {
                trxValue = proxy.getBankEntityBranchTrxValueByTrxID(trxContext, trxID);
                branchParamList.setBankEntityBranchParams(trxValue.getStagingBankEntityBranchParam());
                resultMap.put("fromEvent", event);
            } else {
                trxValue = proxy.getBankEntityBranchTrxValue(trxContext);
                branchParamList.setBankEntityBranchParams(trxValue.getBankEntityBranchParam());
            }

            branchParamList.setLastActionBy(trxValue.getUserInfo());
            branchParamList.setLastRemarks(trxValue.getRemarks());

            if ("maker_prepare_update".equals(event))
                if ((ICMSConstant.STATE_ND.equals(trxValue.getStatus())
                        && trxValue.getStagingBankEntityBranchParam() != null
                        && trxValue.getStagingBankEntityBranchParam().size() > 0)
                        || ICMSConstant.STATE_PENDING_UPDATE.equals(trxValue.getStatus())
                        || ICMSConstant.STATE_REJECTED.equals(trxValue.getStatus())) {
                    resultMap.put("wip", "wip");
                }

            resultMap.put("BankEntityBranchParamForm", branchParamList);
            resultMap.put("bankEntityBranchTrxValue", trxValue);
        } catch (BankEntityBranchParamException e) {
            DefaultLogger.error(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
