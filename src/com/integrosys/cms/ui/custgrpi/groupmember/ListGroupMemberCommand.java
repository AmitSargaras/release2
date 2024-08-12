package com.integrosys.cms.ui.custgrpi.groupmember;

import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

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
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.cci.CIFSearchCommand;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.host.eai.customer.SearchHeader;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 9, 2007 Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ListGroupMemberCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListGroupMemberCommand() {

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
						SERVICE_SCOPE }, });
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
		if (StringUtils.equals(customerSeach, "true")) {
			return doExecuteExternal(map);
		}

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map
				.get("theOBTrxContext");
		GroupMemberSearchCriteria formCriteria = (GroupMemberSearchCriteria) map
				.get(CustGroupUIHelper.form_groupMemberSearchCriteria);
		GroupMemberSearchCriteria searchCriteria = null;

		if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
			DefaultLogger.debug(this, "- Search Criteria from Session !");
			searchCriteria = (GroupMemberSearchCriteria) map
					.get(CustGroupUIHelper.service_groupMemberSearchCriteria);
			if (searchCriteria != null) {
				if (startIndex != null) {
					int startIndexInt = Integer.parseInt(startIndex);
					searchCriteria.setStartIndex(startIndexInt);
				} else {
					searchCriteria.setStartIndex(formCriteria.getStartIndex());
				}

				searchCriteria.setNItems(formCriteria.getNItems());
			}
		}
		if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !"
					+ formCriteria.toString());
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
			ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory
					.getProxy();
			SearchResult customerlist = null;
			if ("true".equals(customerSeach)) {
				searchCriteria.setCustomerSeach(true);
				customerlist = proxy.searchEntryDetails(searchCriteria);

			} else {
				customerlist = proxy.searchEntryDetails(searchCriteria);
			}

			if (customerlist != null) {
				Collection v = (Collection) customerlist.getResultList();
//				System.out.println("ListGroupMemberCommand v.size() = "+ v.size());

			}
			resultMap.put(CustGroupUIHelper.form_groupMemberSearchList,
					customerlist);
			resultMap.put(CustGroupUIHelper.service_groupMemberSearchList,
					customerlist);
			resultMap.put(
					IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ,
					searchCriteria);
			resultMap.put(CustGroupUIHelper.service_groupMemberSearchCriteria,
					searchCriteria);
		} catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Existing doExecute()");

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private boolean isEmptySearchCriteria(GroupMemberSearchCriteria criteria) {

		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getSourceType())) {
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

		DefaultLogger.debug(this, "Inside doExecuteExternal()");
		String event = (String) map.get("event");
		String startIndex = (String) map.get("startIndex");
		String indicator = (String) map.get("indicator");
		String customerSeach = (String) map.get("customerSeach");

		String msgRefNo = (String) map.get("msgRefNo");

//		GroupMemberSearchCriteria formCriteria = (GroupMemberSearchCriteria) map
//				.get(CustGroupUIHelper.form_groupMemberSearchCriteria);

		CounterpartySearchCriteria searchCriteria = (CounterpartySearchCriteria) map
		.get(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ);		
		// For Ajax
		// String searchEvent = (String) map.get("searchevent");

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		// OBTrxContext theOBTrxContext = (OBTrxContext) map
		// .get("theOBTrxContext");

		// CounterpartySearchCriteria searchCriteria =
		// (CounterpartySearchCriteria) map
		// .get(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ);

		// if (theOBTrxContext != null)
		// searchCriteria.setCtx(theOBTrxContext);

		ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
		// ---------------- 3 . Listing ----------------

		DefaultLogger.debug(this, "3 . Listing  " + msgRefNo);
		try {
			DefaultLogger.debug(this, "** searchCriteria ** ");
			// DefaultLogger.debug(this, AccessorUtil
			// .printMethodValue(searchCriteria));

			// 1.Retrieve Header Information
			final SBCustomerManager customerManager = CustomerProxyFactory
					.getProxy().getSBCustomerManager();

			SearchHeader sh = customerManager
					.getSearchCustomerMultipleHeader(msgRefNo);

			// searchCriteria.setDbKey(sh.getDBKey());

			resultMap.put("dbkey", sh.getDBKey());

			// 2.Retrieve Results Information
			SearchResult sr = null;

			//CounterpartySearchCriteria searchCriteria = new CounterpartySearchCriteria();

			searchCriteria.setMsgRefNo(msgRefNo);

			searchCriteria.setCustomerSeach(true);

			searchCriteria.setCifSource(searchCriteria.getCifSource());

			sr = custproxy.searchCCICustomer(searchCriteria);

			// resultMap.put("counterpartyList", sr);
			// resultMap.put("session.customerlist", sr);

			resultMap.put(CustGroupUIHelper.form_groupMemberSearchList, sr);
			resultMap.put(CustGroupUIHelper.service_groupMemberSearchList, sr);
			resultMap.put(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ, searchCriteria);
			// resultMap.put(
			// IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ,
			// searchCriteria);
			// resultMap.put(CustGroupUIHelper.service_groupMemberSearchCriteria,
			// searchCriteria);

			// resultMap.put("counterpartySearchCriteria1", searchCriteria);

			resultMap
					.remove(IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ);
			resultMap
					.remove(CustGroupUIHelper.service_groupMemberSearchCriteria);

		} catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}

		// resultMap.put(CIFSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
		// searchCriteria);
		// Remove MsgRefNo from Service Scope
		resultMap.remove("msgRefNo");

		DefaultLogger.debug(this, "Existing doExecute()");

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}

}
