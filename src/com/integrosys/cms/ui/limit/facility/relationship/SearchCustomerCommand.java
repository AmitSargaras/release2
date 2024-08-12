package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;
import com.integrosys.cms.host.stp.proxy.IStpSyncProxy;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

public class SearchCustomerCommand extends FacilityMainCommand {
    private final Logger logger = LoggerFactory.getLogger(SearchCustomerCommand.class);

    public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "nextTab", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
                { IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                { "searchButton", "java.lang.String", REQUEST_SCOPE},
                { "subtype", "java.lang.String", REQUEST_SCOPE},
                { "lastCustomerReturn", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult", SERVICE_SCOPE},
                { IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }} );
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { {"customerList", "java.util.List", REQUEST_SCOPE},
                { "lastCustomerReturn", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult", SERVICE_SCOPE} });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

        String strStpMode = PropertyUtil.getInstance("/stp.properties").getProperty(IStpConstants.STP_SWITCH_MODE);
        boolean stpMode = false; //Stp switch off
        if (StringUtils.isNotEmpty(strStpMode))
            stpMode = new Boolean(strStpMode).booleanValue();
        
        try {
			// for ui tab
			/*
			 * String currentTab = (String) map.get("nextTab"); if
			 * (StringUtils.isBlank(currentTab)) { currentTab = "relationship"; }
			 * result.put("currentTab", currentTab);
			 */
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
            ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
            CustomerSearchCriteria cSearch = (CustomerSearchCriteria) map.get("customerSearchCriteria");
            String searchButton = (String) map.get("searchButton");
            String subtype = (String) map.get("subtype");
            OBCustomerSearchResult lastCustomerReturn = (OBCustomerSearchResult) map.get("lastCustomerReturn");
            result.put("subtype", subtype);

            if (stpMode) {
                IStpSyncProxy stpSyncProxy = StpSyncProxyImpl.getInstance();
                Map stpParamMap = new HashMap();
                stpParamMap.put(IStpConstants.RES_RECORD_RETURN,
                        PropertyUtil.getInstance("/stp.properties").getProperty(IStpConstants.STP_SKT_HDR_NUMBER_RECORD));

                if (StringUtils.equals(searchButton, "1") || StringUtils.equals(searchButton, "4")) {
                    cSearch.setCustomerName(StringUtils.upperCase(StringUtils.trim(cSearch.getCustomerName())));
                    stpParamMap.put("WKMETH", "M");
                } else if (StringUtils.equals(searchButton, "2") || StringUtils.equals(searchButton, "5")) {
                    stpParamMap.put("WKMETH", "C");
                } else if (StringUtils.equals(searchButton, "3") || StringUtils.equals(searchButton, "6")) {
                    cSearch.setIdNO(StringUtils.upperCase(StringUtils.trim(cSearch.getIdNO())));
                    stpParamMap.put("WKMETH", "I");
                }

                if (lastCustomerReturn != null) {
                    if (StringUtils.equals(searchButton, "9")) {
                        stpParamMap.put(IStpConstants.HDR_DSP_MORE_SEARCH_IND, "1");
                        stpParamMap.put(IStpConstants.HDR_MBASE_MORE_SEARCH_IND, "Y");
    //                    stpParamMap.put(IStpConstants.HDR_MBASE_MORE_SEARCH_METHOD, StringUtils.equals(searchButton, "9") ? "B" : "F");
                        stpParamMap.put(IStpConstants.HDR_MBASE_MORE_SEARCH_METHOD, "F");
                        stpParamMap.put("WKMETH", lastCustomerReturn.getSearchMethod());
                        stpParamMap.put("MAINID", lastCustomerReturn.getMainId());

                        cSearch.setCustomerName(lastCustomerReturn.getLegalName());
                        cSearch.setIdNO(lastCustomerReturn.getIdNo());
                        cSearch.setIDType(lastCustomerReturn.getIdType());
                        cSearch.setIssuedCountry(lastCustomerReturn.getOrigLocCntry());
                        cSearch.setLegalID(lastCustomerReturn.getLegalReference());
                    }
                }

    //            if (isEmptySearchCriteria(cSearch)) {
    //
    //			}
                ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
                long teamTypeID = team.getTeamType().getTeamTypeID();

                if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
                    logger.debug("SearchCustomerCommand::doExecute:teamTypeID - " + teamTypeID);
                    cSearch.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
                }
    //			cSearch.setCtx(theOBTrxContext);
    //			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
    //			SearchResult sr = custproxy.searchCustomer(cSearch);
    //			if (sr == null) {
    //				sr = new SearchResult();
    //			}
                ArrayList stpParamList = new ArrayList();
                stpParamList.add(cSearch);
                stpParamList.add(stpParamMap);
                stpParamList.add(user);

                ArrayList customerList = new ArrayList();
                try {
                    customerList = (ArrayList) stpSyncProxy.submitTask(IStpTransType.TRX_TYPE_INQUIRE_CIF, stpParamList.toArray());
                    for (int i = 0; i<customerList.size(); i++) {
                        NumberFormat formatter = new DecimalFormat("####################"); //Format and trim leading zero
                        String cifNo = ((OBCustomerSearchResult) customerList.get(i)).getLegalReference();
                        if (StringUtils.isNotEmpty(cifNo)) {
                            ((OBCustomerSearchResult) customerList.get(i)).setLegalReference(formatter.format(Integer.parseInt(cifNo)));
                        }
                    }

                    if (customerList.size() > 0) {
                        lastCustomerReturn = (OBCustomerSearchResult) customerList.get(customerList.size() - 1);
                    }
                } catch (StpCommonException e) {
                    exceptionMap.put("stpError", new ActionMessage("error.stp.inquiry", e.getErrorDesc()));
                }
                result.put("lastCustomerReturn", lastCustomerReturn);
    //            logger.debug("SearchCustomerCommand::doExecute:customerList - " + customerList);
                result.put("customerList", customerList);
            }
            else { //Search local db customer
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
        }
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
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
