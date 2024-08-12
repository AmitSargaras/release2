/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.customer;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Entity Limit Customer Search Command
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class ELCustSearchCommand extends AbstractCommand {
public class ELCustSearchCommand extends EntityLimitCommand {

	public final static String EXTERNAL_SEARCH_CRITERIA_OBJ = "ELCustSearchCoammnd.customerSearchCriteria";
	
	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		
		return (new String[][] {
				{IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
			    {"customerSearchCriteria",
					"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", FORM_SCOPE },
				{"customerSearchCriteria1",
					"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "customerList",
						"com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "session.customerlist",
						"com.integrosys.base.businfra.search.SearchResult", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", GLOBAL_SCOPE },
				{ "customerSearchCriteria1",
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", SERVICE_SCOPE } 
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
		
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map
				.get("theOBTrxContext");
		CounterpartySearchCriteria formCriteria = (CounterpartySearchCriteria) map
				.get("customerSearchCriteria");
		CounterpartySearchCriteria searchCriteria = null;
		
		if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
			DefaultLogger.debug(this, "- Search Criteria from Session !");
			searchCriteria = (CounterpartySearchCriteria) map
					.get("customerSearchCriteria1");
            if (searchCriteria != null) {
				searchCriteria.setStartIndex(formCriteria.getStartIndex());
                searchCriteria.setNItems(formCriteria.getNItems());
            }
		}
		if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !");
			searchCriteria = formCriteria;
		}
		
		searchCriteria.setCustomerSeach(true);

        ITeam team = (ITeam)(map.get(IGlobalConstant.USER_TEAM));
    	long teamTypeID = team.getTeamType().getTeamTypeID();

    	if (teamTypeID == ICMSConstant.TEAM_TYPE_MR ) {
  			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
  		}
		searchCriteria.setCtx(theOBTrxContext);
		DefaultLogger.debug(this, "start index: "
				+ searchCriteria.getStartIndex());
//        DefaultLogger.debug(this, "getCustomerName: "
//                + searchCriteria.getCustomerName());
        DefaultLogger.debug(this, "getLegalID: "
                + searchCriteria.getLegalID());
        DefaultLogger.debug(this, "getLeIDType: "
                + searchCriteria.getLeIDType());
        DefaultLogger.debug(this, "getIdNO: "
                + searchCriteria.getIdNO());
        DefaultLogger.debug(this, "getAll: "
                + searchCriteria.getAll());
        DefaultLogger.debug(this, "getNItems: "
                + searchCriteria.getNItems());
        try {
        	ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
			SearchResult sr = custproxy.searchCCICustomer(searchCriteria);
			
            result.put("customerList", sr);
			result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
			result.put("customerSearchCriteria1", searchCriteria);
			result.put("session.customerlist", sr);
			
		} catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
		
	}

	private boolean isEmptySearchCriteria(CounterpartySearchCriteria criteria) {
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
