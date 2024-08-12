package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.trx.OBCCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.cms.app.liquidation.proxy.LiquidationProxyFactory;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;
import java.util.Collection;

/**
 * This class implements command
 */
public class MakerReadRejectedCounterpartyListCommand extends AbstractCommand {

    public MakerReadRejectedCounterpartyListCommand() {

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
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
   /* public String[][] getResultDescriptor1() {
        return (new String[][]{
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE} ,

        }
        );
    }*/


    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", FORM_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"maker_process_event", "java.lang.String", GLOBAL_SCOPE },
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
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

        DefaultLogger.debug(this, "Inside doExecute()\n");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String event = (String) inputMap.get("event");
        String groupCCINo = (String) inputMap.get("groupCCINo");
        String trxId = (String) inputMap.get("TrxId");

        String remarks = (String) inputMap.get("remarks");
        DefaultLogger.debug(this, "**********************remarks: " + remarks);

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICCICounterpartyDetailsTrxValue trxValue = (ICCICounterpartyDetailsTrxValue) inputMap.get("ICCICounterpartyDetailsTrxValue");

        if (trxValue != null) {
            trxId = trxValue.getTransactionID();
        }

        if (trxValue == null) {
            trxValue = new OBCCICounterpartyDetailsTrxValue();
        }

        //System.out.println("MakerReadRejectedCounterpartyListCommand event = " + event);
        //System.out.println("MakerReadRejectedCounterpartyListCommand trxId = " + trxId);

        ICCICounterpartyDetails stagingDetails = null;
        ICCICustomerProxy ccproxy = CCICustomerProxyFactory.getProxy();


        try {
            if (trxId != null && !"".equals(trxId)) {
                DefaultLogger.debug(this, "getting by trx id." + trxId);
                trxValue = ccproxy.getCCICounterpartyDetailsByTrxID(theOBTrxContext, trxId);
                stagingDetails = trxValue.getStagingCCICounterpartyDetails();

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

            if (trxValue == null) {
                DefaultLogger.debug(this, "trxValue  is null");
            } else {
                trxValue.setCCICounterpartyDetails(stagingDetails);
                //System.out.println("MakerReadRejectedCounterpartyListCommand (trxValue) ReferenceID " + trxValue.getReferenceID());
               // System.out.println("MakerReadRejectedCounterpartyListCommand (trxValue) getStagingReferenceID " + trxValue.getStagingReferenceID());
            }

            resultMap.put("event", event);
            resultMap.put("TrxId", trxId);
            resultMap.put("maker_process_event", event);
            resultMap.put("ICCICounterpartyDetails", stagingDetails);
            resultMap.put("session.ICCICounterpartyDetails", stagingDetails);
            resultMap.put("ICCICounterpartyDetailsTrxValue", trxValue);
            resultMap.put("remarks", remarks);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()\n\n\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


}
