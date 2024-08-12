package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * This class implements command
 */
public class MakerReadRejectedCustGrpIdentifierCommand extends AbstractCommand {

    public MakerReadRejectedCustGrpIdentifierCommand() {

    }

    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE}
        }
        );
    }


    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String event = (String) inputMap.get("event");
        String trxId = (String) inputMap.get("TrxId");

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);

        if (trxValue != null) {
            trxId = trxValue.getTransactionID();
        }

        if (trxValue == null) {
            trxValue = new OBCustGrpIdentifierTrxValue();
        }

        DefaultLogger.debug(this, "event = " + event);

        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier aICustGrpIdentifier = null;
        IGroupSubLimit[] grpSubLimitlist = null;

        try {
            if (trxId != null && !"".equals(trxId)) {
                DefaultLogger.debug(this, "getting by trx id.");

                trxValue = proxy.getCustGrpIdentifierByTrxID(theOBTrxContext, trxId);
                aICustGrpIdentifier = trxValue.getStagingCustGrpIdentifier();
                grpSubLimitlist = aICustGrpIdentifier.getGroupSubLimit();
                if (grpSubLimitlist != null && grpSubLimitlist.length > 0) {
                    Debug("stagingObj grpSubLimitlist.length = " + grpSubLimitlist.length);
                } else {
                    Debug("grpSubLimitlist1 is null");
                }
                // if current status is other than ACTIVE & REJECTED, then show workInProgress.
                // i.e. allow edit only if status is either ACTIVE or REJECTED
                if ((!trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)) &&
                        (!trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED))
                        ) {
                    resultMap.put("wip", "wip");
                    DefaultLogger.debug(this, "staging trxValue in session");
                } else {
                    DefaultLogger.debug(this, "TRXVAL In session " + trxValue);
                }

            }
            resultMap.put("event", event);
            resultMap.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
            resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, aICustGrpIdentifier);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"CheckerReadCustGrpIdentifierCommand = " + msg);
    }


}
