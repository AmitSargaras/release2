package com.integrosys.cms.ui.collateral.pledgor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.IStpSyncProxy;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Web command to do the search pledgor, either via own persistent storage, or
 * do a inquiry to a customer repositary system.
 * 
 */
public class SearchPledgorCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "pledgorSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "searchButton", "java.lang.String", REQUEST_SCOPE },
				{ "lastPledgorReturn", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "customerList", "java.util.List", REQUEST_SCOPE },
				{ "lastPledgorReturn", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		CustomerSearchCriteria cSearch = (CustomerSearchCriteria) map.get("pledgorSearchCriteria");
		String searchButton = (String) map.get("searchButton");
		String subtype = (String) map.get("subtype");
		OBCustomerSearchResult lastPledgorReturn = (OBCustomerSearchResult) map.get("lastPledgorReturn");

		result.put("subtype", subtype);

		boolean isStpInquiryEnabled = PropertyManager.getBoolean(IStpConstants.STP_SWITCH_MODE, false);
		boolean hasStpAccess = PropertyManager.getBoolean("has.access.stp.system");
		if (isStpInquiryEnabled && hasStpAccess) {
			IStpSyncProxy stpSyncProxy = StpSyncProxyImpl.getInstance();
			Map stpParamMap = new HashMap();
			stpParamMap.put(IStpConstants.RES_RECORD_RETURN, PropertyUtil.getInstance("/stp.properties").getProperty(
					IStpConstants.STP_SKT_HDR_NUMBER_RECORD));

			if (StringUtils.equals(searchButton, "1")) {
				cSearch.setCustomerName(StringUtils.upperCase(StringUtils.trim(cSearch.getCustomerName())));
				stpParamMap.put("WKMETH", "M");
			}
			else if (StringUtils.equals(searchButton, "2")) {
				stpParamMap.put("WKMETH", "C");
			}
			else if (StringUtils.equals(searchButton, "3")) {
				cSearch.setIdNO(StringUtils.upperCase(StringUtils.trim(cSearch.getIdNO())));
				stpParamMap.put("WKMETH", "I");
			}

			if (lastPledgorReturn != null) {
				if (StringUtils.equals(searchButton, "5") || StringUtils.equals(searchButton, "6")) {
					stpParamMap.put(IStpConstants.HDR_DSP_MORE_SEARCH_IND, "1");
					stpParamMap.put(IStpConstants.HDR_MBASE_MORE_SEARCH_IND, "Y");
					stpParamMap.put(IStpConstants.HDR_MBASE_MORE_SEARCH_METHOD,
							StringUtils.equals(searchButton, "5") ? "B" : "F");
					stpParamMap.put("WKMETH", lastPledgorReturn.getSearchMethod());
					stpParamMap.put("MAINID", lastPledgorReturn.getMainId());

					cSearch.setCustomerName(lastPledgorReturn.getLegalName());
					cSearch.setIdNO(lastPledgorReturn.getIdNo());
					cSearch.setIDType(lastPledgorReturn.getIdType());
					cSearch.setIssuedCountry(lastPledgorReturn.getOrigLocCntry());
					cSearch.setLegalID(lastPledgorReturn.getLegalReference());
				}
			}

			ArrayList stpParamList = new ArrayList();
			stpParamList.add(cSearch);
			stpParamList.add(stpParamMap);
			stpParamList.add(user);

			ArrayList customerList = new ArrayList();
			try {
				customerList = (ArrayList) stpSyncProxy.submitTask(IStpTransType.TRX_TYPE_INQUIRE_CIF, stpParamList
						.toArray());
			}
			catch (Exception e) {
				if (e instanceof StpCommonException) {
					exceptionMap.put("stpError", new ActionMessage("error.stp.inquiry", ((StpCommonException) e)
							.getErrorDesc()));
				}
			}

			for (int i = 0; i < customerList.size(); i++) {
				// Format and trim leading zero
				NumberFormat formatter = new DecimalFormat("####################");
				String cifNo = ((OBCustomerSearchResult) customerList.get(i)).getLegalReference();
				if (StringUtils.isNotEmpty(cifNo)) {
					((OBCustomerSearchResult) customerList.get(i)).setLegalReference(formatter.format(Integer
							.parseInt(cifNo)));
				}
			}

			if (customerList.size() > 0) {
				lastPledgorReturn = (OBCustomerSearchResult) customerList.get(customerList.size() - 1);
				result.put("lastPledgorReturn", lastPledgorReturn);
				result.put("customerList", customerList);
			}

		}
		else {
			// Search local db customer
			if (isEmptySearchCriteria(cSearch)) {
			}
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			long teamTypeID = team.getTeamType().getTeamTypeID();

			if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
				cSearch.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
			}
			cSearch.setCtx(theOBTrxContext);
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			SearchResult sr = custproxy.searchCustomer(cSearch);
			ArrayList customerCol = new ArrayList();
			if (sr == null) {
				sr = new SearchResult();
			}
			else {
				customerCol.addAll(sr.getResultList());
			}

			DefaultLogger.debug(this, "customerCol.size: " + customerCol.size());
			result.put("customerList", customerCol);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
		if (StringUtils.isNotBlank(criteria.getCustomerName())) {
			return false;
		}
		if (StringUtils.isNotBlank(criteria.getLegalID())) {
			return false;
		}
		if (StringUtils.isNotBlank(criteria.getIDType())) {
			return false;
		}
		if (StringUtils.isNotBlank(criteria.getIdNO())) {
			return false;
		}
		if (StringUtils.isNotBlank(criteria.getIssuedCountry())) {
			return false;
		}
		return true;
	}
}
