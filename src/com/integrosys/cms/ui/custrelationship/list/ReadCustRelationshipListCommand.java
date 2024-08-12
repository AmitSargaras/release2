/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.custrelationship.trx.OBCustRelationshipTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;
import com.integrosys.cms.ui.custrelationship.CustRelUtils;
/**
 * @author siew kheat
 *
 */
public class ReadCustRelationshipListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
	        {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	        {"event", "java.lang.String", REQUEST_SCOPE},
	        {"sub_profile_id", "java.lang.String", REQUEST_SCOPE},
	        {"customerType", "java.lang.String", REQUEST_SCOPE},
	        {"customerID", "java.lang.String", REQUEST_SCOPE},
            {"CustRelationshipTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, // Produce the offset.
	        {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{ "session.customerlist",
				"com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
			{"trxId", "java.lang.String", REQUEST_SCOPE},
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
				"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
        });
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
        return (new String[][]{// Produce all the feed entries.
            {"CustRelationshipTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, // Produce the offset.
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
            {"length", "java.lang.Integer", SERVICE_SCOPE}, // To populate the form.
            {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            {"customerType", "java.lang.String", SERVICE_SCOPE},
            {"entRelLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
            {"entRelValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
				"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
            {CustRelationshipListForm.MAPPER,
             "com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue",
             FORM_SCOPE}});
            
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

        ICustRelationshipTrxValue trxValue = new OBCustRelationshipTrxValue();
        String parentSubProfileIDStr = "";
        String parentCustomerType = "";
        
        try {
        	
        	parentSubProfileIDStr = (String)map.get("sub_profile_id");
        	parentCustomerType = (String)map.get("customerType");
        	long parentSubProfileID = (parentSubProfileIDStr == null) ? 0 
        								 : Long.parseLong(parentSubProfileIDStr);
        	
        	OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
        	String trxId = (String)map.get("trxId");
        	DefaultLogger.debug(this, "*** trxId = " + trxId);
        	DefaultLogger.debug(this, "*** parentCustomerType = " + parentCustomerType);

            ICustRelationshipProxy custRelaionshipProxy = CustRelationshipProxyFactory.getProxy();
            
            if(trxId == null || trxId.equals("")) {
            	trxValue = custRelaionshipProxy.getCustRelationshipTrxValue(theOBTrxContext, parentSubProfileID);
            } else
            	trxValue = custRelaionshipProxy.getCustRelationshipTrxValueByTrxID(theOBTrxContext, trxId);

            DefaultLogger.debug(this,
                    "after getting customer relationship trx from proxy.");

            String event = (String)map.get("event");
            
            DefaultLogger.debug(this, "trxValue : " + trxValue);
            
            if (trxValue == null) {
            	trxValue = new OBCustRelationshipTrxValue();
            	trxValue.setParentSubProfileID(parentSubProfileID);
            }
            
            // If this is the very first online read, then there will be
            // no staging records. So copy the actual records as staging
            // records.
            if (trxValue.getStagingCustRelationship() == null &&
            		trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE)) {
                trxValue.setStagingCustRelationship((ICustRelationship[])CommonUtil.deepClone(
                        trxValue.getCustRelationship()));
            }

            if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE) ||
                    trxValue.getStatus().equals(ICMSConstant.STATE_ND) ||
                    CustRelationshipListAction.EVENT_READ.equals(event)) {
                // Set the staging to be the same as actual.
                trxValue.setStagingCustRelationship((ICustRelationship[])CommonUtil.deepClone(
                        trxValue.getCustRelationship()));
            }

            if (trxValue.getCustRelationship() == null) {
                trxValue.setCustRelationship(new ICustRelationship[0]);
            }
            
            // if sub profile id is not in request scope... 
            // get it from trxValue if available
            if (parentSubProfileID == 0 && 
            		trxValue.getParentSubProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
            	parentSubProfileID = trxValue.getParentSubProfileID();
            	parentSubProfileIDStr = String.valueOf(parentSubProfileID);
            }
            
            // Getting parentID from customer ID 
        	// when both actual and staging are empty in  
        	// maker edit rejected transaction
        	String customerIDStr = (String)map.get("customerID");
        	if ((parentSubProfileID == 0 || parentSubProfileID == ICMSConstant.LONG_INVALID_VALUE)
        			&& customerIDStr != null) {
        		parentSubProfileIDStr = customerIDStr;
        	}
            
            ICMSCustomer custOB = null;
            
            custOB = (ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
            
            if ( custOB == null && parentSubProfileID != 0 && 
            		parentSubProfileID != ICMSConstant.LONG_INVALID_VALUE) {
            	
            	ICCICustomerProxy cciCustproxy = CCICustomerProxyFactory.getProxy();
            	
                String sub_profile_id1 = cciCustproxy.searchCustomer(parentSubProfileID);
                
                if (sub_profile_id1 != null) {
                    custOB = this.getCustomer(sub_profile_id1);
                }
                if (custOB != null) {
                    
                    resultMap.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, custOB);
                }
            }
            
            
            CommonCodeList commonCode = CommonCodeList .getInstance(null, null, ICMSUIConstant.ENTITY_REL, null);
            Collection entRelValues = commonCode.getCommonCodeValues();
            Collection entRelLabels = commonCode.getCommonCodeLabels();
            if (entRelValues == null) {
            	entRelValues = new ArrayList();
            }
            if (entRelLabels == null) {
            	entRelLabels = new ArrayList();
            }
            
            entRelValues = CustRelUtils.removeIntFromCollection(CustRelConstants.SHAREHOLDER_ID, entRelValues);
            entRelLabels = CustRelUtils.removeStringFromCollection(CustRelConstants.SHAREHOLDER_LABEL, entRelLabels);
            resultMap.put("entRelValues", entRelValues);
            resultMap.put("entRelLabels", entRelLabels);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        resultMap.put("CustRelationshipTrxValue", trxValue);
        resultMap.put("offset", new Integer(0));
        resultMap.put("length", new Integer(10));
        resultMap.put(CustRelationshipListForm.MAPPER, trxValue);
        resultMap.put("sub_profile_id", parentSubProfileIDStr);
        resultMap.put("customerType", parentCustomerType);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
    
    
    /**
     * Get customer object
     * @param sub_profile_id
     * @return
     */
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
}
