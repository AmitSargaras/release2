package com.integrosys.cms.ui.custgrpi.groupmember;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.cci.CIFSearchCommand;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 9, 2007 Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ReturnListGroupMemberCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public ReturnListGroupMemberCommand() {

    }

    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][] {
                { IGlobalConstant.USER_TEAM,
                        "com.integrosys.component.bizstructure.app.bus.ITeam",
                        GLOBAL_SCOPE },
                {
                        CustGroupUIHelper.form_groupMemberSearchCriteria,
                        "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria",
                        FORM_SCOPE },
                {
                        CustGroupUIHelper.service_groupMemberSearchCriteria,
                        "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria",
                        SERVICE_SCOPE },
                { "theOBTrxContext",
                        "com.integrosys.cms.app.transaction.OBTrxContext",
                        FORM_SCOPE },
                { "event", "java.lang.String", REQUEST_SCOPE },
                { "indicator", "java.lang.String", REQUEST_SCOPE },
                { "startIndex", "java.lang.String", REQUEST_SCOPE },
                { "customerSeach", "java.lang.String", REQUEST_SCOPE },

                { "msgRefNo", "java.lang.String", GLOBAL_SCOPE },
                { "dbkey", "java.lang.String", SERVICE_SCOPE },

                {
                        CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
                        "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
                        GLOBAL_SCOPE },

                {
                        CustGroupUIHelper.service_groupTrxValue,
                        "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue",
                        SERVICE_SCOPE },
                { CustGroupUIHelper.service_groupMemberSearchList,	"com.integrosys.base.businfra.search.SearchResult",	SERVICE_SCOPE },
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
        return (new String[][] {
                { "customerSeach", "java.lang.String", REQUEST_SCOPE },
                { "msgRefNo", "java.lang.String", GLOBAL_SCOPE },
                { "dbkey", "java.lang.String", SERVICE_SCOPE },
                { CustGroupUIHelper.form_groupMemberSearchList,
                        "com.integrosys.base.businfra.search.SearchResult",
                        FORM_SCOPE },
                { CustGroupUIHelper.service_groupMemberSearchList,
                        "com.integrosys.base.businfra.search.SearchResult",
                        SERVICE_SCOPE },
                {
                        IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ,
                        "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria",
                        GLOBAL_SCOPE },
                {
                        CustGroupUIHelper.service_groupMemberSearchCriteria,
                        "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria",
                        SERVICE_SCOPE },

                {
                        CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
                        "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
                        GLOBAL_SCOPE },

                {
                        CustGroupUIHelper.service_groupTrxValue,
                        "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue",
                        SERVICE_SCOPE },


        });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param map
     *            is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *             on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *             on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException,
            CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");
        String event = (String) map.get("event");
        String startIndex = (String) map.get("startIndex");
        String indicator = (String) map.get("indicator");
        String customerSeach = (String) map.get("customerSeach");

        // 1. External Search Redirect
        String msgRefNo = (String) map.get("msgRefNo");

        DefaultLogger.debug(this, "*** MsgRefNo = " + msgRefNo);
        DefaultLogger.debug(this, "*** customerSeach = " + customerSeach);

        // Redirect to External Search if MsgRefNo Present
        // if (StringUtils.isNotEmpty(msgRefNo)
        // && StringUtils.equals(customerSeach, "true")) {
       // if (StringUtils.equals(customerSeach, "true")) {
           // return doExecuteExternal(map);
       // }

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        OBTrxContext theOBTrxContext = (OBTrxContext) map .get("theOBTrxContext");
        GroupMemberSearchCriteria formCriteria = (GroupMemberSearchCriteria) map  .get(CustGroupUIHelper.form_groupMemberSearchCriteria);
        GroupMemberSearchCriteria searchCriteria = null;
        SearchResult sr  = (SearchResult) map .get(CustGroupUIHelper.service_groupMemberSearchList);

        try {
            resultMap.put(CustGroupUIHelper.form_groupMemberSearchList, sr);
            resultMap.put(CustGroupUIHelper.service_groupMemberSearchList, sr);
            resultMap.put(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ, searchCriteria);
        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
