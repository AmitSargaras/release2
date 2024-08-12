/* 
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.ui.custrelationship.customer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import java.util.*;

/**
* Describe this class.
* Purpose:Return 
* Description: return to customer search result list
*
* @author $Author$<br>
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/

public class ReturnCustomerCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public ReturnCustomerCommand() {

    }

    /**
     * Defines a two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {CRCustSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", GLOBAL_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
            {"custName", "java.lang.String", REQUEST_SCOPE},
        }
                );
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
        return (new String[][]{
            {"counterpartyList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
            { "session.customerlist","com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
        }
                );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException on errors
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        CounterpartySearchCriteria objSearch = (CounterpartySearchCriteria) map.get(CRCustSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ);
        String event = (String) map.get("event");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        OBTrxContext theOBTrxContext = (OBTrxContext) map .get("theOBTrxContext");
        String custName = (String) map.get("custName");
        DefaultLogger.debug(this, "Inside doExecute()");
        if (event.equals("prepare")) {

        } else {
            try {
                DefaultLogger.debug(this, "Before Doing Search");
                ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
                
                if (objSearch == null) {
	                objSearch = new CounterpartySearchCriteria();
	                objSearch.setCustomerSeach(true);
	                objSearch.setCustomerName(custName);
	                objSearch.setNItems(20);
                }
                
                if (theOBTrxContext != null)
                     objSearch.setCtx(theOBTrxContext);
                     
			          SearchResult sr = custproxy.searchCCICustomer(objSearch);

                result.put("counterpartyList", sr);
                result.put("session.customerlist", sr);
                  if (sr == null) {
                    DefaultLogger.debug(this, "Result is null");
                } else {
                    }


            } catch (Exception e) {
                DefaultLogger.debug(this, "got exception in doExecute" + e);
                throw (new CommandProcessingException(e.getMessage()));
            }
            
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;

    }

}
