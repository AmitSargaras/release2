package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.HashMap;

/**
 * Author: Syukri
 * Date: Jul 1, 2008
 */
public class SearchCounterpartyCommand extends AbstractCommand {

    public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
        String indicator = (String) map.get("indicator");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
        CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
        CustomerSearchCriteria searchCriteria = null;

        if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
            DefaultLogger.debug(this, "- Search Criteria from Session !");
            searchCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria1");
            if (searchCriteria != null) {
                searchCriteria.setStartIndex(formCriteria.getStartIndex());
                searchCriteria.setNItems(formCriteria.getNItems());
            }
        }
        if (searchCriteria == null) {
            DefaultLogger.debug(this, "- Search Criteria from Form !");
            searchCriteria = formCriteria;
        }

        ITeam team = (ITeam)(map.get(IGlobalConstant.USER_TEAM));
        long teamTypeID = team.getTeamType().getTeamTypeID();

        if (teamTypeID == ICMSConstant.TEAM_TYPE_MR ) {
              searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
          }
        searchCriteria.setCtx(theOBTrxContext);
        DefaultLogger.debug(this, "start index: " + searchCriteria.getStartIndex());
       // DefaultLogger.debug(this, "getCustomerName: " + searchCriteria.getCustomerName());
        DefaultLogger.debug(this, "getLegalID: " + searchCriteria.getLegalID());
        DefaultLogger.debug(this, "getLeIDType: " + searchCriteria.getLeIDType());
        DefaultLogger.debug(this, "getIdNO: " + searchCriteria.getIdNO());
        DefaultLogger.debug(this, "getAll: " + searchCriteria.getAll());
        DefaultLogger.debug(this, "getNItems: " + searchCriteria.getNItems());

        try {
            ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
            SearchResult sr = custproxy.searchCustomerInfoOnly(searchCriteria);

            result.put("customerList", sr);
            result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
            result.put("customerSearchCriteria1", searchCriteria);
            result.put("session.customerlist", sr);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }

    public String[][] getParameterDescriptor() {
        return (new String[][] {
            {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
            {"customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
            {"customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", SERVICE_SCOPE },
            { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
            { "event", "java.lang.String", REQUEST_SCOPE },
            { "indicator", "java.lang.String", REQUEST_SCOPE },
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][] {
            { "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
            { "session.customerlist", "com.integrosys.base.businfra.search.SearchResult", GLOBAL_SCOPE },
            { IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
            { "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", SERVICE_SCOPE }
        });
    }

    private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
        if (isNotEmptyStr(criteria.getCustomerName())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getLegalID())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getLeIDType())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getIdNO())) {
            return false;
        }
        return true;
    }

    private boolean isNotEmptyStr(String str) {
        if (str == null) {
            return false;
        }
        if (str.trim().equals("")) {
            return false;
        }
        return true;
    }
}
