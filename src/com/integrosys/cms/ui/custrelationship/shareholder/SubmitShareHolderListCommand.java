/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;

/**
 * @author siew kheat
 *
 */
public class SubmitShareHolderListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the input fields as a List of offset (String), length
            // (String) and feed group OB.
            {"shareHolderMap", "java.util.HashMap", FORM_SCOPE}, // Consume the current feed entries to be saved as a whole.
            {"CustShareHolderTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
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
    			"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", REQUEST_SCOPE},
            {"CustShareHolderTrxValue", 
                	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}
        	};
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

            HashMap inputHash = (HashMap)map.get("shareHolderMap");
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
            ICustShareholder[] inputCustShareHolderArr = (ICustShareholder[])inputHash.get("shareHolder");

            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

            // Added because when going from "view limits" to "manual feeds
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(customer);
            trxContext.setLimitProfile(null);

            // Session-scoped trx value.
            ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
                    "CustShareHolderTrxValue");
            ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();

            // Update using the input unit prices.
            if (inputCustShareHolderArr != null) {
                DefaultLogger.debug(this,
                        "number of existing entries = " + inputCustShareHolderArr.length);

                for (int i = 0; i < inputCustShareHolderArr.length; i++) {
                	stagingShareHolderArr[offset + i].setPercentageOwn(
                			inputCustShareHolderArr[i].getPercentageOwn());
                	
                	stagingShareHolderArr[offset + i].setLastUpdateDate(new java.util.Date());
                	stagingShareHolderArr[offset + i].setLastUpdateUser(trxContext.getUser().getUserName());
                }
            }
            
            if (stagingShareHolderArr != null) {
	            for (int i = 0; i < stagingShareHolderArr.length; i++) {
	            	if (stagingShareHolderArr[i].getPercentageOwn() == null || 
	            			stagingShareHolderArr[i].getPercentageOwn().doubleValue() == 0) {
	            		exceptionMap.put(CustRelConstants.ERROR_CUST_SHAREHOLDER, new ActionMessage("error.cust.relationship.percentages.empty"));
	            	}
	            }
	            
	            double total = 0;
	            for (int i = 0; i < stagingShareHolderArr.length; i++) {
	            	if (stagingShareHolderArr[i].getPercentageOwn() != null && 
	            			stagingShareHolderArr[i].getPercentageOwn().doubleValue() > 0) {
	            		total += stagingShareHolderArr[i].getPercentageOwn().doubleValue();
	            		
	            		if (total > 100) {
		            		exceptionMap.put(CustRelConstants.ERROR_CUST_SHAREHOLDER, new ActionMessage("error.cust.relationship.percentages.exceeds"));
		            		break;
		            	}
	            	}
	            }
            }
            
            DefaultLogger.debug(this, "Current State is " + value.getStatus());
            if ((value.getStatus().equals(ICMSConstant.STATE_ND) || value.getStatus().equals(ICMSConstant.STATE_NEW) || 
            		value.getStatus().equals(ICMSConstant.STATE_ACTIVE) || 
            		value.getStatus().equals(ICMSConstant.STATE_REJECTED_UPDATE) || 
            		value.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) &&
                	(value.getStagingCustShareholder() == null || value.getStagingCustShareholder().length == 0) &&
                	(value.getCustShareholder() == null || value.getCustShareholder().length == 0))
            {
                exceptionMap.put(CustRelConstants.ERROR_CUST_SHAREHOLDER, new ActionMessage("error.no.entries"));
            }
            
            if (exceptionMap.isEmpty()) {
	            value.setStagingCustShareholder(stagingShareHolderArr);
	
	            ICustRelationshipProxy proxy = CustRelationshipProxyFactory.getProxy();
	
	            ICustShareholderTrxValue resultValue = null;
	            
	            if (value.getStagingCustShareholder() == null)
	            	value.setStagingCustShareholder(new ICustShareholder[0]);
	            
	            if (value.getParentSubProfileID() == ICMSConstant.LONG_INVALID_VALUE)
	            	value.setParentSubProfileID(parentSubProfileID);
	            
	            // Add new cust share holders
	            resultValue = proxy.makerUpdateCustShareholder(trxContext, value, 
	                		value.getStagingCustShareholder());
	            
	            resultMap.put("request.ITrxValue", resultValue);
            } else {
            	resultMap.put("CustShareHolderTrxValue", value);
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
