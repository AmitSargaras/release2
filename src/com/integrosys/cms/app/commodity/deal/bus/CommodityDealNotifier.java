/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CommodityDealNotifier.java,v 1.14 2006/08/16 10:01:23 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.dealapproval.DealApprovalListener;
import com.integrosys.cms.app.eventmonitor.dealapproval.OBDealApproval;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.CountryList;

/**
 * @author $Author: hmbao $
 * @version $Revision: 1.14 $
 * @since $Date: 2006/08/16 10:01:23 $ Tag: $Name: $
 */
public final class CommodityDealNotifier {

	private static final String EVENT_CREATE_CMDT_DEAL_APPROVAL = "EV_CREATE_DEAL_APPROVAL";

	private static final String EVENT_CREATE_CMDT_DEAL_REJECTION = "EV_CREATE_DEAL_REJECTION";

	private static final String RULE_CREATE_CMDT_DEAL_APPROVAL = "R_CREATE_DEAL_APPROVAL";

	private static final String RULE_CREATE_CMDT_DEAL_REJECTION = "R_CREATE_DEAL_REJECTION";

	private static final String EVENT_UPDATE_CMDT_DEAL_APPROVAL = "EV_UPDATE_DEAL_APPROVAL";

	private static final String EVENT_UPDATE_CMDT_DEAL_REJECTION = "EV_UPDATE_DEAL_REJECTION";

	private static final String RULE_UPDATE_CMDT_DEAL_APPROVAL = "R_UPDATE_DEAL_APPROVAL";

	private static final String RULE_UPDATE_CMDT_DEAL_REJECTION = "R_UPDATE_DEAL_REJECTION";

	private static final String EVENT_CLOSE_CMDT_DEAL_APPROVAL = "EV_CLOSE_DEAL_APPROVAL";

	private static final String EVENT_CLOSE_CMDT_DEAL_REJECTION = "EV_CLOSE_DEAL_REJECTION";

	private static final String RULE_CLOSE_CMDT_DEAL_APPROVAL = "R_CLOSE_DEAL_APPROVAL";

	private static final String RULE_CLOSE_CMDT_DEAL_REJECTION = "R_CLOSE_DEAL_REJECTION";

	private static HashMap ruleMap;

	private static HashMap eventMap;
	static {
		eventMap = new HashMap(6);
		eventMap.put(ICMSConstant.ACTION_OFFICER_APPROVE_CREATE_DEAL, EVENT_CREATE_CMDT_DEAL_APPROVAL);
		eventMap.put(ICMSConstant.ACTION_FAM_REJECT_CREATE_DEAL, EVENT_CREATE_CMDT_DEAL_REJECTION);
		eventMap.put(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CREATE_DEAL, EVENT_CREATE_CMDT_DEAL_REJECTION);
		eventMap.put(ICMSConstant.ACTION_OFFICER_APPROVE_UPDATE_DEAL, EVENT_UPDATE_CMDT_DEAL_APPROVAL);
		eventMap.put(ICMSConstant.ACTION_FAM_REJECT_UPDATE_DEAL, EVENT_UPDATE_CMDT_DEAL_REJECTION);
		eventMap.put(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_UPDATE_DEAL, EVENT_UPDATE_CMDT_DEAL_REJECTION);
		eventMap.put(ICMSConstant.ACTION_OFFICER_APPROVE_CLOSE_DEAL, EVENT_CLOSE_CMDT_DEAL_APPROVAL);
		eventMap.put(ICMSConstant.ACTION_FAM_REJECT_CLOSE_DEAL, EVENT_CLOSE_CMDT_DEAL_REJECTION);
		eventMap.put(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CLOSE_DEAL, EVENT_CLOSE_CMDT_DEAL_REJECTION);

		ruleMap = new HashMap(6);
		ruleMap.put(EVENT_CREATE_CMDT_DEAL_APPROVAL, RULE_CREATE_CMDT_DEAL_APPROVAL);
		ruleMap.put(EVENT_CREATE_CMDT_DEAL_REJECTION, RULE_CREATE_CMDT_DEAL_REJECTION);
		ruleMap.put(EVENT_UPDATE_CMDT_DEAL_APPROVAL, RULE_UPDATE_CMDT_DEAL_APPROVAL);
		ruleMap.put(EVENT_UPDATE_CMDT_DEAL_REJECTION, RULE_UPDATE_CMDT_DEAL_REJECTION);
		ruleMap.put(EVENT_CLOSE_CMDT_DEAL_APPROVAL, RULE_CLOSE_CMDT_DEAL_APPROVAL);
		ruleMap.put(EVENT_CLOSE_CMDT_DEAL_REJECTION, RULE_CLOSE_CMDT_DEAL_REJECTION);
	}

	public void process(ICommodityDealNotifyInfo notifyInfo) throws CommodityException, EventHandlingException {
		if ((notifyInfo != null) && (notifyInfo.getOperationName() != null)) {
			String eventID = (String) eventMap.get(notifyInfo.getOperationName());
			if (eventID != null) {
				fireEvent(eventID, notifyInfo);
				return;
			}
		}
		throw new CommodityException("No event found for operation : " + notifyInfo.getOperationName());
	}

	private void fireEvent(String eventID, ICommodityDealNotifyInfo notifyInfo) throws CommodityException,
			EventHandlingException {

		ICommodityDeal deal = notifyInfo.getDeal();
		ICommodityCollateral commodity = notifyInfo.getCommodity();
		String securityLocationCtryCode = notifyInfo.getOriginatingCountry();

		OBCustomerSearchResult customerInfo = getCustomerInfo(deal);
		String leID = customerInfo.getLegalReference();
		String leName = customerInfo.getLegalName();

		DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : eventID - " + eventID);
		DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : securityLocationCtryCode - "
				+ securityLocationCtryCode);
		DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : LeID - " + leID);
		DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : leName - " + leName);
		DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : deal - " + deal.getDealNo());

		OBDealApproval dealApproval = new OBDealApproval();
		dealApproval.setTransactionID(notifyInfo.getTransactionID());
		dealApproval.setDealNo(deal.getDealNo());
		dealApproval.setDealAmount(deal.getDealAmt());
		dealApproval.setSecurityId((commodity == null) ? null : commodity.getSCISecurityID());
		dealApproval.setSecurityLoc(CountryList.getInstance().getCountryName(securityLocationCtryCode));
		dealApproval.setLeID(leID);
		dealApproval.setLeName(leName);
		dealApproval.setTrxUserID(notifyInfo.getTrxUserID());
		dealApproval.setTrxUserTeamID(notifyInfo.getTrxUserTeamID());

		long profileID = deal.getContractProfileID();
		if (profileID != ICMSConstant.LONG_INVALID_VALUE) {
			IProfile profile = getProfileByProfileID(profileID);
			DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : profile - " + profile);
			dealApproval.setComProductType(CommodityCategoryList.getInstance().getCommProductItem(
					profile.getCategory(), profile.getProductType()));
			dealApproval.setComProductSubType(profile.getProductSubType());
			DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : product type - "
					+ dealApproval.getComProductType());
			DefaultLogger.debug(this, "########## CommodityDealNotification.fireEvent : product subtype - "
					+ dealApproval.getComProductSubType());
		}

		ArrayList list = new ArrayList(3);
		list.add(dealApproval);
		list.add("");
		list.add(constructRuleParam((String) ruleMap.get(eventID)));

		new DealApprovalListener().fireEvent(eventID, list);
	}

	private IRuleParam constructRuleParam(String notificationRuleID) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID(notificationRuleID);
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);
		return param;
	}

	private IProfile getProfileByProfileID(long profileID) throws CommodityException {
		CommodityMainInfoSearchCriteria criteria = new CommodityMainInfoSearchCriteria(
				ICommodityMainInfo.INFO_TYPE_PROFILE);
		criteria.setSearchBy(CommodityMainInfoSearchCriteria.SEARCH_BY_ID);
		criteria.setInfoID(new Long(profileID));
		IProfile[] profiles = (IProfile[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
		return ((profiles != null) && (profiles.length > 0) ? profiles[0] : null);
	}

	private OBCustomerSearchResult getCustomerInfo(ICommodityDeal deal) throws CommodityException {
		try {
			CustomerDAO dao = new CustomerDAO();
			ArrayList lmtList = new ArrayList();
			OBCustomerSearchResult customer = null;
			if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(deal.getCustomerCategory())) {
				DefaultLogger.debug(this, "Customer : CB - Limit : " + deal.getCoBorrowerLimitID());
				lmtList.add(String.valueOf(deal.getCoBorrowerLimitID()));
				customer = (OBCustomerSearchResult) dao.getCBInfoByLimitIDList(lmtList).iterator().next();
			}
			else {
				DefaultLogger.debug(this, "Customer : MB - Limit : " + deal.getLimitID());
				lmtList.add(String.valueOf(deal.getLimitID()));
				customer = (OBCustomerSearchResult) dao.getMBInfoByLimitIDList(lmtList).iterator().next();
			}
			return customer;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException("Error to Search Customer Info.");
		}
	}
}
