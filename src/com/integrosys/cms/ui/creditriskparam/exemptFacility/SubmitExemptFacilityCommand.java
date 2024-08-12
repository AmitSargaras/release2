package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.ExemptFacilityProxyFactory;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.IExemptFacilityProxy;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class SubmitExemptFacilityCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public SubmitExemptFacilityCommand() {
    }

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"remarks", "java.lang.String", REQUEST_SCOPE},
            {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
//            {"exemptFacility", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility", FORM_SCOPE},
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        });
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
        });
    }
    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap. Updates to the contract financing is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();

        IExemptFacilityGroup group = (IExemptFacilityGroup)map.get("InitialExemptFacilityGroup");
        IExemptFacilityGroupTrxValue trxValue = (IExemptFacilityGroupTrxValue)map.get("exemptFacilityTrxValue");
        OBTrxContext trxContext = (OBTrxContext)map.get("theOBTrxContext");
        String remarks = (String)map.get("remarks");
        DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> trxContext="+trxContext);

        try {
            trxContext.setLimitProfile(null);
            trxContext.setCustomer(null);

            DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> group="+group);
            IExemptFacilityProxy proxy = ExemptFacilityProxyFactory.getProxy();

            if (group != null)
            {
                trxValue.setStagingExemptFacilityGroup(group);
                if (group.getExemptFacility() != null)
                    DefaultLogger.debug(this,"group.getExemptFacility().length = " + group.getExemptFacility().length);
                else
                    DefaultLogger.debug(this,"group.getExemptFacility() is null");
            }
            
            // Codes too messy, get directly from database
            String trxID = trxValue.getTransactionID();
            IExemptFacilityGroupTrxValue trxValueTemp = null;
            if(trxID == null || trxID.equals("")) {
                DefaultLogger.debug(this, ">>>>>>>>>>>> Getting the Transaction object" );
                trxValueTemp = proxy.getExemptFacilityTrxValue(trxContext);
                DefaultLogger.debug(this, ">>>>>>>>>>>>trxValueTemp  "  + trxValueTemp);

            } else {
                DefaultLogger.debug(this, ">>>>>>>>>>>> In getting by trxID");
                trxValueTemp = proxy.getExemptFacilityTrxValueByTrxID(trxContext, trxID);
                DefaultLogger.debug(this, ">>>>>>>>>>>> trxValue=" + trxValueTemp);
            }
            
            
            IExemptFacility[] actual = null;
            IExemptFacility[] staging = null;
            
            // getting actual from database
            if (trxValue != null) {
	            if(trxValue.getExemptFacilityGroup()!=null) {
	            	actual = trxValue.getExemptFacilityGroup().getExemptFacility();
	            }
            }
            if (actual != null) DefaultLogger.debug(this, "actual1 : " + actual.length);
            
            // getting actual from database
//            if (trxValueTemp != null) {
//	            if(trxValueTemp.getExemptFacilityGroup()!=null) {
//	            	actual = trxValueTemp.getExemptFacilityGroup().getExemptFacility();
//	            	trxValue.setExemptFacilityGroup(trxValueTemp.getExemptFacilityGroup());
//	            }
//            }
            
            // getting staging from ui application after maker make changes
            if(trxValue.getStagingExemptFacilityGroup()!=null) {
            	staging = trxValue.getStagingExemptFacilityGroup().getExemptFacility();
            }
            
            if (staging != null) DefaultLogger.debug(this, "staging : " + staging.length);
            if ((trxValue.getStatus().equals(ICMSConstant.STATE_ND) || trxValue.getStatus().equals(ICMSConstant.STATE_NEW) || 
            		trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE) || 
            		trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_UPDATE) || 
            		trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) &&
                	(staging == null || staging.length == 0) &&
                	(actual == null || actual.length == 0))
            {
            	exceptionMap.put("noItemSelected", new ActionMessage("error.no.exempte.item"));
            } else{ 
            	
            	if (staging != null && staging.length == 0)
            		trxValue.getStagingExemptFacilityGroup().setExemptFacilityGroupID(ICMSConstant.LONG_INVALID_VALUE);
            	
            	trxValue.setRemarks(remarks);
            	trxValue = proxy.makerUpdateExemptFacility(trxContext, trxValue, trxValue.getStagingExemptFacilityGroup());
            }

            resultMap.put("request.ITrxValue",trxValue);

        }
        catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this, "Exception in doExecute" + e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }
}