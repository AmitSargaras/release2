/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;

/**
 * @author siew kheat
 *
 */
public class SubmitCustRelationshipListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the input fields as a List of offset (String), length
            // (String) and feed group OB.
            {"custRelationshipMap", "java.util.HashMap", FORM_SCOPE}, // Consume the current feed entries to be saved as a whole.
            {"CustRelationshipTrxValue",
            	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            {"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            {"customerType", "java.lang.String", SERVICE_SCOPE},
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
				"com.integrosys.cms.app.customer.bus.ICMSCustomer",
				GLOBAL_SCOPE }
        	};
    }


    /**
     * Defines a two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return new String[][]{
        		{"request.ITrxValue",
        			"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", REQUEST_SCOPE},
                {"CustRelationshipTrxValue",
                	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}};
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException on errors
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map)
            throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            HashMap inputHash = (HashMap)map.get("custRelationshipMap");
            int offset = ((Integer)map.get("offset")).intValue();
            String parentSubProfileIDStr = (String)map.get("sub_profile_id");
            long parentSubProfileID = parentSubProfileIDStr == null ? 
            		0 : Long.parseLong(parentSubProfileIDStr);

            ICMSCustomer customer = (ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
            
            //int targetOffset = Integer.parseInt((String)inputHash.get(0));
            //int offset = Integer.parseInt((String)inputList.get(0));
            //int length = Integer.parseInt((String)inputList.get(1));
            // Element at index 2 is the target offset which is not required
            // here.
            ICustRelationship[] inputCustRelArr = (ICustRelationship[])inputHash.get("custRel");

            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

            // Added because when going from "view limits" to "manual feeds
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(customer);
            trxContext.setLimitProfile(null);

            // Session-scoped trx value.
            ICustRelationshipTrxValue value = (ICustRelationshipTrxValue)map.get(
                    "CustRelationshipTrxValue");
            ICustRelationship[] stagingCustRelArr = value.getStagingCustRelationship();

            // Update using the input unit prices.
            if (inputCustRelArr != null) {
                DefaultLogger.debug(this,
                        "number of existing entries = " + inputCustRelArr.length);

                for (int i = 0; i < inputCustRelArr.length; i++) {
                	stagingCustRelArr[offset + i].setRelationshipValue(
                			inputCustRelArr[i].getRelationshipValue());
                	stagingCustRelArr[offset + i].setRemarks(
                			inputCustRelArr[i].getRemarks());
                	
                	stagingCustRelArr[offset + i].setLastUpdateDate(new java.util.Date());
                	stagingCustRelArr[offset + i].setLastUpdateUser(trxContext.getUser().getUserName());
                }
            }
            
            if (stagingCustRelArr != null) {
            	for (int i = 0; i < stagingCustRelArr.length; i++) {
            		if (stagingCustRelArr[i].getRelationshipValue() == null || 
            				stagingCustRelArr[i].getRelationshipValue().length() == 0) {
            			exceptionMap.put(CustRelConstants.ERROR_CUST_REL, new ActionMessage("error.cust.relationship.custrel.empty"));
            		}
            	}
            }
            
            DefaultLogger.debug(this, "Current State is " + value.getStatus());
            if ((value.getStatus().equals(ICMSConstant.STATE_ND) || value.getStatus().equals(ICMSConstant.STATE_NEW) || 
            		value.getStatus().equals(ICMSConstant.STATE_ACTIVE) || 
            		value.getStatus().equals(ICMSConstant.STATE_REJECTED_UPDATE) || 
            		value.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) &&
                	(value.getStagingCustRelationship() == null || value.getStagingCustRelationship().length == 0) &&
                	(value.getCustRelationship() == null || value.getCustRelationship().length == 0))
            {
                exceptionMap.put(CustRelConstants.ERROR_CUST_REL, new ActionMessage("error.no.entries"));
            }
            		
            
            if (exceptionMap.isEmpty()) {
	            value.setStagingCustRelationship(stagingCustRelArr);
	
	            ICustRelationshipProxy proxy = CustRelationshipProxyFactory.getProxy();
	
	            ICustRelationshipTrxValue resultValue = null;
	            
	            //value.setStatus(ICMSConstant.STATE_ND);
	            // value.setCustomerID(parentSubProfileID);
	            
	            if (value.getStagingCustRelationship() == null)
	            	value.setStagingCustRelationship(new ICustRelationship[0]);
	            	
	            
	            if (value.getParentSubProfileID() == ICMSConstant.LONG_INVALID_VALUE)
	            	value.setParentSubProfileID(parentSubProfileID);
	            
	                resultValue = proxy.makerUpdateCustRelationship(trxContext, value,
	                        value.getStagingCustRelationship());
	            
	            resultMap.put("request.ITrxValue", resultValue);
            } else {
            	resultMap.put("CustRelationshipTrxValue", value);
            }

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
