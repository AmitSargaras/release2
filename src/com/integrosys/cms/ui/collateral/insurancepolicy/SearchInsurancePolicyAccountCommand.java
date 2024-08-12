package com.integrosys.cms.ui.collateral.insurancepolicy;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: KLYong
 * @version: ${VERSION}
 * @since: Nov 8, 2008 2:18:05 AM
 */
public class SearchInsurancePolicyAccountCommand extends AbstractCommand {
    private final Logger logger = LoggerFactory.getLogger(SearchInsurancePolicyAccountCommand.class);

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"insurancePolicyObj", "com.integrosys.cms.app.collateral.bus.IInsurancePolicy", FORM_SCOPE}, {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE}
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
        logger.debug("SearchInsurancePolicyAccountCommand::doExecute...");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ArrayList stpArrlist = new ArrayList();

        IInsurancePolicy iInsurancePolicy = (IInsurancePolicy) map.get("insurancePolicyObj");
        try {
            if (!StringUtils.isEmpty(iInsurancePolicy.getDebitingACNo())) {
                ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
                stpArrlist.add(user);
                stpArrlist.add(iInsurancePolicy);
                try {
                    StpSyncProxyImpl stpProxy = (StpSyncProxyImpl) BeanHouse.get("stpSyncProxy");
                    Object objectMsg = stpProxy.submitTask(IStpTransType.TRX_TYPE_ACC_VERIFY, stpArrlist.toArray());

                    String isStpStatus = "";
                    if (objectMsg != null) {
                        isStpStatus = "success";
                        result.put("isStpStatus", isStpStatus);
                    }
                    else
                        isStpStatus = "fail";
                } catch (StpCommonException e) {
                    exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc()));
                }
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
