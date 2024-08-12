package com.integrosys.cms.ui.cci;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
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
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.cms.host.eai.customer.SearchHeader;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 9, 2007 Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ListCounterpartyCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListCounterpartyCommand() {

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
						"counterpartySearchCriteria",
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						FORM_SCOPE },
				{
						"counterpartySearchCriteria1",
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{
						CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						GLOBAL_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerSeach", "java.lang.String", REQUEST_SCOPE },
				{
						"ICCICounterpartyDetailsTrxValue",
						"com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue",
						SERVICE_SCOPE },
				{ "msgRefNo", "java.lang.String", GLOBAL_SCOPE },
				{ "dbkey", "java.lang.String", SERVICE_SCOPE },
				{
						"ICCICounterpartyDetailsTrxValue",
						"com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue",
						SERVICE_SCOPE }

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
				{ "counterpartyList",
						"com.integrosys.base.businfra.search.SearchResult",
						FORM_SCOPE },
				{ "session.customerlist",
						"com.integrosys.base.businfra.search.SearchResult",
						SERVICE_SCOPE },
				{
						IGlobalConstant.GLOBAL_COUNTERPARTY_SEARCH_CRITERIA_OBJ,
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						GLOBAL_SCOPE },
				{
						"counterpartySearchCriteria1",
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						SERVICE_SCOPE },
				{
						"ICCICounterpartyDetailsTrxValue",
						"com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue",
						SERVICE_SCOPE },
				{ "msgRefNo", "java.lang.String", GLOBAL_SCOPE },
				{ "dbkey", "java.lang.String", SERVICE_SCOPE },
				{ "counterpartyList",
						"com.integrosys.base.businfra.search.SearchResult",
						FORM_SCOPE },
				{ "session.customerlist",
						"com.integrosys.base.businfra.search.SearchResult",
						SERVICE_SCOPE },
				{
						CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						GLOBAL_SCOPE },
				{
						"ICCICounterpartyDetailsTrxValue",
						"com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue",
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
	public HashMap doExecuteExternal(HashMap map)
			throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Inside doExecute()");
		String customerSeach = (String) map.get("customerSeach");

		String msgRefNo = (String) map.get("msgRefNo");

		// For Ajax
		// String searchEvent = (String) map.get("searchevent");

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map
				.get("theOBTrxContext");

		CounterpartySearchCriteria searchCriteria = (CounterpartySearchCriteria) map
				.get(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ);

		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
		}
		if (theOBTrxContext != null)
			searchCriteria.setCtx(theOBTrxContext);

		searchCriteria.setNItems(20);

		ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
		// ---------------- 3 . Listing ----------------
		/*
		 * DefaultLogger.debug(this, "start index: " +
		 * searchCriteria.getStartIndex()); DefaultLogger.debug(this,
		 * "getCustomerName: " + searchCriteria.getCustomerName());
		 * DefaultLogger.debug(this, "getLegalID: " +
		 * searchCriteria.getLegalID()); DefaultLogger.debug(this, "getLeIDType: " +
		 * searchCriteria.getLeIDType()); DefaultLogger.debug(this, "getIdNO: " +
		 * searchCriteria.getIdNO()); DefaultLogger.debug(this, "getAll: " +
		 * searchCriteria.getAll()); DefaultLogger.debug(this, "getNItems: " +
		 * searchCriteria.getNItems()); DefaultLogger.debug(this,
		 * "getGroupCCINo: " + searchCriteria.getGroupCCINo());
		 */

		DefaultLogger.debug(this, "3 . Listing  " + msgRefNo);
		try {
			DefaultLogger.debug(this,"** searchCriteria ** ");
			DefaultLogger.debug(this, AccessorUtil
					.printMethodValue(searchCriteria));

			// 1.Retrieve Header Information
			final SBCustomerManager customerManager = CustomerProxyFactory.getProxy().getSBCustomerManager();

			SearchHeader sh = customerManager.getSearchCustomerMultipleHeader(msgRefNo);

			searchCriteria.setDbKey(sh.getDBKey());

			resultMap.put("dbkey", sh.getDBKey());

			// 2.Retrieve Results Information
			SearchResult sr = null;

			searchCriteria.setMsgRefNo(msgRefNo);

//			searchCriteria.setCifSource(leIDType);

			// TODO: External Search
			// if ("customer_list".equals(event)){
			if ("true".equals(customerSeach)) {
				searchCriteria.setCustomerSeach(true);

				sr = custproxy.searchCCICustomer(searchCriteria);
				// sr = searchExternalCustomer(searchCriteria);

				// resultMap.put("dbkey", "1234");

			} else {
				sr = custproxy.searchCCICustomer(searchCriteria);
			}

			resultMap.put("counterpartyList", sr);
			resultMap.put("session.customerlist", sr);

			// resultMap.put("counterpartySearchCriteria1", searchCriteria);
		} catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}

		resultMap.put(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
				searchCriteria);
		// Remove MsgRefNo from Service Scope
		resultMap.remove("msgRefNo");

		DefaultLogger.debug(this, "Existing doExecute()");

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
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

		String customerSeach = (String) map.get("customerSeach");

		String msgRefNo = (String) map.get("msgRefNo");

		String searchSource = (String) map.get("searchSource");

		DefaultLogger.debug(this, "** customerSeach+msgRefNo = "
				+ customerSeach + msgRefNo);

		// Redirect to External Search if MsgRefNo Present
		if (StringUtils.isNotEmpty(msgRefNo)
				&& StringUtils.equals(customerSeach, "true")
				|| StringUtils.isNotEmpty(searchSource)) {
			return doExecuteExternal(map);
		}

		String startIndex = (String) map.get("startIndex");
		String indicator = (String) map.get("indicator");
		
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map
				.get("theOBTrxContext");
		CounterpartySearchCriteria formCriteria = (CounterpartySearchCriteria) map
				.get("counterpartySearchCriteria");
		CounterpartySearchCriteria searchCriteria = null;

		if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
			DefaultLogger.debug(this, "- Search Criteria from Session !");
			searchCriteria = (CounterpartySearchCriteria) map
					.get("counterpartySearchCriteria1");
			if (searchCriteria != null) {
				if (startIndex != null) {
					int startIndexInt = Integer.parseInt(startIndex);
					searchCriteria.setStartIndex(startIndexInt);
				} 
			}
		}
		if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !");
			searchCriteria = formCriteria;
		}

		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
		}
		if (theOBTrxContext != null)
			searchCriteria.setCtx(theOBTrxContext);

		searchCriteria.setNItems(20);

		DefaultLogger.debug(this, "Is CustomerSeach: " + customerSeach);
		try {
			ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
			SearchResult sr = null;
	
			if ("true".equals(customerSeach)) {
				searchCriteria.setCustomerSeach(true);
				sr = custproxy.searchCCICustomer(searchCriteria);
			} else {
				sr = custproxy.searchCCICustomer(searchCriteria);
			}
			resultMap.put("counterpartyList", sr);
			resultMap.put("session.customerlist", sr);
			resultMap.put(
					IGlobalConstant.GLOBAL_COUNTERPARTY_SEARCH_CRITERIA_OBJ,
					searchCriteria);
			resultMap.put("counterpartySearchCriteria1", searchCriteria);
		} catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Existing doExecute()");

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
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
		if (isNotEmptyStr(criteria.getGroupCCINo())) {
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
