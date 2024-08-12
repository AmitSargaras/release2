package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.SearchHeader;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 9, 2007 Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This is a generic External Customer Search Command , call by AJAX script
 *
 * @author allen
 */
public class CIFSearchCommand extends AbstractCommand {

    public final static String EXTERNAL_SEARCH_CRITERIA_OBJ = "CIFSearchCommand.counterpartySearchCriteria";

    /**
     * Default Constructor
     */
    public CIFSearchCommand() {

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

                {"event", "java.lang.String", REQUEST_SCOPE},

                // Search Keys
                {"legalId", "java.lang.String", REQUEST_SCOPE},
                {"leIDType", "java.lang.String", REQUEST_SCOPE},
                {"customerName", "java.lang.String", REQUEST_SCOPE},
                {"idNO", "java.lang.String", REQUEST_SCOPE},
                {"dbkey", "java.lang.String", REQUEST_SCOPE},

                // For Subsequent Search
                {"msgRefNo", "java.lang.String", GLOBAL_SCOPE},

                {CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
                        "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
                        GLOBAL_SCOPE},

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

                // Search Keys
                {"legalId", "java.lang.String", REQUEST_SCOPE},
                {"leIDType", "java.lang.String", REQUEST_SCOPE},
                {"customerName", "java.lang.String", REQUEST_SCOPE},
                {"idNO", "java.lang.String", REQUEST_SCOPE},
                {"dbkey", "java.lang.String", REQUEST_SCOPE},

                // For Subsequent Search
                {"msgRefNo", "java.lang.String", GLOBAL_SCOPE},

                {"found", "java.util.String", REQUEST_SCOPE},

                {
                        CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
                        "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
                        GLOBAL_SCOPE},

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
    public HashMap doExecute(HashMap map) throws CommandProcessingException,
            CommandValidationException {
        DefaultLogger.debug(this, "Inside doExecute()");
        String event = (String) map.get("event");

        // Search Parameters
        String legalId = StringUtils.defaultString((String) map.get("legalId"))
                .toUpperCase();

        String leIDType = (String) map.get("leIDType"); // leIDType = Source ID

        String customerName = StringUtils.defaultString(
                (String) map.get("customerName")).toUpperCase();
        String msgRefNo = (String) map.get("msgRefNo");

        String idNO = (String) map.get("idNO");

        String dbkey = (String) map.get("dbkey");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        CounterpartySearchCriteria searchCriteria = null;

        try {

            // Constructs SearchCriteria from parameters

            searchCriteria = new CounterpartySearchCriteria();

            searchCriteria.setLeIDType(leIDType);

            searchCriteria.setLegalID(legalId);
            searchCriteria.setCustomerName(customerName);
            searchCriteria.setIdNO(idNO);
            // searchCriteria.setCifSource(leIDType);

            searchCriteria.setDbKey(dbkey);

            searchCriteria.setMsgRefNo(msgRefNo);

            DefaultLogger.debug(this, AccessorUtil
                    .printMethodValue(searchCriteria));

            validate(searchCriteria);

            ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
            // ---------------- 1 . First Search ----------------
            if (StringUtils.equals(event, "first_search")) {
                DefaultLogger.debug(this, "1 . First Search	  :" + msgRefNo);

                // Set MsgRefNo to empty
                searchCriteria.setMsgRefNo("");

                // Set DBKey
                searchCriteria.setDbKey(dbkey);

                // Trigger External Search SI
                try {
                    msgRefNo = custproxy.searchExternalCustomer(searchCriteria);
//					msgRefNo = custproxy.searchCCICustomer(searchCriteria);

                    // set MsgRefNo to Map
                    if (StringUtils.isEmpty(msgRefNo))
                        throw new Exception("Unable to get MsgRefNo");

                    // Update MsgRefNo
                    searchCriteria.setMsgRefNo(msgRefNo);
                    resultMap.put("msgRefNo", msgRefNo);


                } catch (Exception e) {
                    // Exception , Stop Searching
                    resultMap.put("found", "E");
                }

                resultMap.put("found", ICMSConstant.FALSE_VALUE);
            }

            // ---------------- 2. Subsequent Search ----------------
            else if (StringUtils.equals(event, "subsequent_search")) {
                DefaultLogger.debug(this, "2. Subsequent Search  :" + msgRefNo);

                try {
                    final SBCustomerManager customerManager = CustomerProxyFactory.getProxy().getSBCustomerManager();

                    SearchHeader sh = customerManager.getSearchCustomerMultipleHeader(msgRefNo);

                    if (sh.getStatus().equals(IEaiConstant.STAT_SUCCESS)) {
                        dbkey = StringUtils.defaultString(sh.getDBKey());

                        // Set DB Key
                        searchCriteria.setDbKey(dbkey);
                        resultMap.put("dbkey", dbkey);

                        resultMap.put("msgRefNo", msgRefNo);

                        resultMap.put("found", ICMSConstant.TRUE_VALUE);

                        // Set SearchCriteria into Service Scope
                        resultMap.put(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
                                searchCriteria);

                    }
                    if (sh.getStatus().equals(IEaiConstant.STAT_PROCESSING)) {
                        // do nothing
                        resultMap.put("found", ICMSConstant.FALSE_VALUE);

                    } else if (sh.getStatus().equals(
                            IEaiConstant.STAT_EXCEPTION)) {
                        resultMap.put("found", "E");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.put("found", "E");
                }

            }

        } catch (ValidationError e) {
            // Return Validation Error Message
            resultMap.put("found", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("found", "E");

        }

        DefaultLogger.debug(this, "Existing doExecute()");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return temp;
    }

    private void validate(CounterpartySearchCriteria criteria)
            throws ValidationError {

        if (StringUtils.isEmpty(criteria.getCifSource())) {
            throw new ValidationError("CIF Source is mandatory.");
        }

        if (StringUtils.isEmpty(criteria.getLegalID())
                && StringUtils.isEmpty(criteria.getCustomerName())
                && StringUtils.isEmpty(criteria.getIdNO())) {
            throw new ValidationError(
                    "Either one of the search key is mandatory.");
        }

    }

    /**
     * CU002 Search Field Validation Exception
     *
     * @author allen
     */
    private class ValidationError extends Exception {
		public ValidationError(String msg) {
			super(msg);
		}
	}

}
