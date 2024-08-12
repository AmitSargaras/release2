/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author user
 *
 */
public class RejectCustRelationshipListCommand extends AbstractCommand {

	   public String[][] getParameterDescriptor() {
	        return new String[][]{
	            // Consume the current feed entries to be saved as a whole.
	           	 {"CustRelationshipTrxValue",
	                 	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, 
	              {"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};

	    }

	    public String[][] getResultDescriptor() {
	        return new String[][]{{"request.ITrxValue",
                "com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", REQUEST_SCOPE}};
	    }


	    public HashMap doExecute(HashMap map)
	            throws CommandProcessingException, CommandValidationException {

	        DefaultLogger.debug(this, "Map is " + map);

	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        HashMap returnMap = new HashMap();

	        try {

	        	ICustRelationshipTrxValue value = (ICustRelationshipTrxValue)map.get(
	                    "CustRelationshipTrxValue");

	            //String remarks = (String)map.get(ExchangeRateListForm.MAPPER);
	            //value.setRemarks(remarks);

	            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

	            // Added because when going from "view limits" to "manual feeds
	            // update", some values are set in the trx context object which is
	            // "global". Hence has to explicitly set the below to null.
	            trxContext.setCustomer(null);
	            trxContext.setLimitProfile(null);

	            ICustRelationshipProxy proxy = CustRelationshipProxyFactory.getProxy();
	            value = proxy.checkerRejectUpdateCustRelationship(trxContext, value);

	            resultMap.put("request.ITrxValue", value);

	        } catch (Exception e) {
	            DefaultLogger.error(this, "Exception caught in doExecute()", e);
	            exceptionMap.put("application.exception", e);
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

	        return returnMap;
	    }
}
