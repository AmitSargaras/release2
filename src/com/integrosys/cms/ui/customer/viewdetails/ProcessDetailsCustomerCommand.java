package com.integrosys.cms.ui.customer.viewdetails;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;
import java.util.ArrayList;

public class ProcessDetailsCustomerCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public ProcessDetailsCustomerCommand() {

    }

    /**
     * Defines an two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"fam", "java.lang.String", REQUEST_SCOPE},
                {"famcode", "java.lang.String", REQUEST_SCOPE},
                {"transactionID", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"subProfileID", "java.lang.String", REQUEST_SCOPE},
                {"iCustomer", "java.lang.String", REQUEST_SCOPE},
                {"limitProfileID", "java.lang.String", REQUEST_SCOPE}
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
                {"customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE},
                {"limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE},
                {"trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE},
                {"transactionID", "java.lang.String", REQUEST_SCOPE},
                {"fam", "java.lang.String", SERVICE_SCOPE},
                {"famcode", "java.lang.String", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"isMainBorrowerOnly", "java.lang.String", GLOBAL_SCOPE},

        });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String fam = null;
        String famcode = null;
        ICMSCustomer custOB = null;
        ArrayList mainBorrowerList = new ArrayList();
        String isMainBorrowerOnly = "N";

        String event = (String) map.get("event");
        String subProfileID = (String) map.get("subProfileID");


        ILimitProfile aILimitProfile = null;
        ILimitProxy limitProxy = LimitProxyFactory.getProxy();
        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();


        try {             
            if (subProfileID != null) {
                custOB = this.getCustomer(subProfileID);
                if (custOB != null) {
                    mainBorrowerList = custproxy.getMBlistByCBleId(custOB.getCustomerID());
                    if (mainBorrowerList == null || mainBorrowerList.size() == 0) {
                        isMainBorrowerOnly = "Y";
                    }

                    if (custOB.getNonBorrowerInd()) {
                        if (Long.toString(custOB.getCustomerID()) != null) {
                            fam = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_NAME);
                            famcode = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_CODE);
                        }
                    }

                }
            }

            String limitProfileID = (String) map.get("limitProfileID");
            if (limitProfileID != null) {
                aILimitProfile = this.getLimitProfile(limitProfileID);
            }


            result.put("limitprofileOb", aILimitProfile);
            result.put("customerOb", custOB);
            result.put("isMainBorrowerOnly", isMainBorrowerOnly);
            result.put("fam", fam);
            result.put("famcode", famcode);
            returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
            returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
            return returnMap;
        } catch (Exception e) {
            DefaultLogger.error(this, "got exception in doExecute", e);
            throw (new CommandProcessingException(e.getMessage()));
        }
    }

    private ICMSCustomer getCustomer(String sub_profile_id) {
        ICMSCustomer custOB = null;
        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
        try {
            custOB = custproxy.getCustomer(Long.parseLong(sub_profile_id));
            if (custOB != null) {
                return custOB;
            }
        } catch (Exception e) {

        }
        return custOB;

    }

    private ILimitProfile getLimitProfile(String limitProfileID) {
        ILimitProfile aILimitProfile = null;
        ILimitProfileTrxValue trxLimitProfile = null;
        ILimitProxy limitProxy = LimitProxyFactory.getProxy();
        try {
            trxLimitProfile = limitProxy.getTrxLimitProfile(Long.parseLong(limitProfileID));
            aILimitProfile = trxLimitProfile.getLimitProfile();
            if (aILimitProfile != null) {
                return aILimitProfile;
            }
        } catch (Exception e) {

        }
        return aILimitProfile;

    }
}
