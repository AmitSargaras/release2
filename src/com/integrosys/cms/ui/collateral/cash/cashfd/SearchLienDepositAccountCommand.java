package com.integrosys.cms.ui.collateral.cash.cashfd;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchLienDepositAccountCommand extends AbstractCommand {
    private final Logger logger = LoggerFactory.getLogger(SearchLienDepositAccountCommand.class);

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"form.depositObject", "java.util.HashMap", FORM_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{{"fdInfoList", "java.util.List", SERVICE_SCOPE},
                {"depositObj", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", SERVICE_SCOPE},
                {"applicationType", "java.lang.String", REQUEST_SCOPE},
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
        logger.debug("SearchLienDepositAccountCommand::doExecute...");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ArrayList stpArrlist = new ArrayList();
        HashMap stpMapParam = new HashMap();

        HashMap objMap = (HashMap) map.get("form.depositObject");
        OBCashDeposit obCashDep = (OBCashDeposit) objMap.get("deposit");
        try {
            //Modified by KLYong: Stp FD Account Listing
            ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
            stpMapParam.put(IStpConstants.RES_RECORD_RETURN, PropertyManager.getValue(IStpConstants.STP_SKT_HDR_NUMBER_RECORD));

            stpArrlist.add(user);
            stpArrlist.add(stpMapParam);
            stpArrlist.add(obCashDep);
            ArrayList depositList = new ArrayList();
            try {
                StpSyncProxyImpl stpProxy = (StpSyncProxyImpl) BeanHouse.get("stpSyncProxy");
                depositList = (ArrayList) stpProxy.submitTask(IStpTransType.TRX_TYPE_SEARCH_FD_ACCT_LIST, stpArrlist.toArray());
            } catch (StpCommonException e) {
                exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc()));
            }
            String accountNum = obCashDep.getDepositReceiptNo();
            boolean isValidDeposit = false;

            if (!StringUtils.isEmpty(accountNum)) { //Account number specified
                for (int i = 0; i < depositList.size(); i++) {
                    OBCashDeposit respCashDep = (OBCashDeposit) depositList.get(i);
                    if (StringUtils.equals(respCashDep.getDepositReceiptNo(), accountNum)) {
                        isValidDeposit = true; //If found
                        break;
                    }
                }
                if (isValidDeposit) { //Valid
                    result.put("fdInfoList", depositList);
                } else {
                    exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Account number specified was not found."));
                }
            } else {
                result.put("fdInfoList", depositList);
            }
            result.put("depositObj", obCashDep);
        } catch (Exception e) {
            DefaultLogger.error(this, "got exception in doExecute", e);
            throw (new CommandProcessingException(e.getMessage()));
        }

        ILimitProfile lmtProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        result.put("applicationType", lmtProfile.getApplicationType());
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
