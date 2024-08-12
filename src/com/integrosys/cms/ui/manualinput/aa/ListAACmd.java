/*
 * Created on Apr 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ListAACmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "source", "java.lang.String", REQUEST_SCOPE },
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "prevSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "optionList", "java.util.List", REQUEST_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "prevSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			// used for search by aplpha name
			String indicator = (String) map.get("indicator");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
			CustomerSearchCriteria sessionCriteria = null;
			String source = (String) (map.get("source"));

			// form criteria will be empty if user do pagination, retrieve the
			// search crit from session and
			if(isNotEmptyStr(formCriteria.getFacilitySystem()) && isNotEmptyStr(formCriteria.getFacilitySystemID())){
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
				String cust = customerDAO.searchPartyIDBySysID(formCriteria.getFacilitySystem(), formCriteria.getFacilitySystemID());
				formCriteria.setLegalID(cust);
			}
			
			// set the page index that user selects
			if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
				sessionCriteria = (CustomerSearchCriteria) map.get("prevSearchCriteria");
				if (sessionCriteria != null) {
					sessionCriteria.setStartIndex(formCriteria.getStartIndex());
				}
			}
			if (sessionCriteria == null) {
				sessionCriteria = formCriteria;
			}
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			long teamTypeID = team.getTeamType().getTeamTypeID();

			if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
				sessionCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
			}

			if ("limit".equals(source)) {
				sessionCriteria.setByLimit(true);
			}

			sessionCriteria.setCtx(theOBTrxContext);

			AAUIHelper helper = new AAUIHelper();
			SearchResult sr = helper.getSBMIAAProxy().searchMICustomer(sessionCriteria);
			result.put("optionList", helper.getOptionList(source));
			result.put("customerList", sr);
			result.put("prevSearchCriteria", sessionCriteria);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getCustomerName())) {
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
