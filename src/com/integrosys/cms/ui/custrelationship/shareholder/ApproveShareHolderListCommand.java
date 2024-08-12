/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author siew kheat
 * 
 */
public class ApproveShareHolderListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the current feed entries to be saved as a whole.
              {"CustShareHolderTrxValue", 
                	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, // Produce the offset.
              {"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};
    }


    public String[][] getResultDescriptor() {
        return new String[][]{{"request.ITrxValue",
                               "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", REQUEST_SCOPE}};
    }


    public HashMap doExecute(HashMap map)
            throws CommandProcessingException, CommandValidationException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
                    "CustShareHolderTrxValue");

            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

            // Added because when going from "view limits" to "manual feeds
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);
            if (trxContext == null) {
                DefaultLogger.debug(this,
                        "trxContext obtained from map is null.");
            }

			ICustShareholder[] actualShareHolderArr = value.getCustShareholder();
			ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();
			
			DefaultLogger.debug(this, "actualShareHolderArr : " + actualShareHolderArr);
			DefaultLogger.debug(this, "actualShareHolderArr : " + actualShareHolderArr);
            
            ICustRelationshipProxy proxy = CustRelationshipProxyFactory.getProxy();
            value = proxy.checkerApproveUpdateCustShareholder(trxContext, value);

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
