/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/ReadGeneralInfoCommand.java,v 1.46 2006/09/26 03:15:08 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.deal.trx.OBCommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.46 $
 * @since $Date: 2006/09/26 03:15:08 $ Tag: $Name: $
 */

public class ReadGeneralInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, { "previous", "java.lang.String", REQUEST_SCOPE },
				{ "dealID", "java.lang.String", REQUEST_SCOPE }, { "dealType", "java.lang.String", REQUEST_SCOPE },
				{ "fromToDo", "java.lang.String", REQUEST_SCOPE }, });

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "generalInfoObj", "java.util.HashMap", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "previous_event", "java.lang.String", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityLimitMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "ARM_Code", "java.lang.String", SERVICE_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "originalSublimit", "com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit",
						REQUEST_SCOPE },
				{ "contractNo", "java.lang.String", REQUEST_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ "selectedWRList", "java.util.Collection", SERVICE_SCOPE },
				{ "TitleDocWR", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", SERVICE_SCOPE },
				{ "TitleDocWRNeg", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE }, { "tab", "java.lang.String", SERVICE_SCOPE },
				{ "fromToDo", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	// Method modified by Pratheepa for CR129
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		ICommodityDealTrxValue trxValue = new OBCommodityDealTrxValue();
		ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
		ICommodityDeal dealObj = new OBCommodityDeal();

		String previous = (String) map.get("previous");

		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		if (event.equals(GeneralInfoAction.EVENT_TRACK)) {
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			TOP_LOOP: for (int i = 0; i < team.getTeamMemberships().length; i++) {// parse
																					// team
																					// membership
																					// to
																					// validate
																					// user
																					// first
				for (int j = 0; j < team.getTeamMemberships()[i].getTeamMembers().length; j++) { // parse
																									// team
																									// memebers
																									// to
																									// get
																									// the
																									// team
																									// member
																									// first
																									// .
																									// .
					if (team.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
							.getUserID()) {
						if (team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_FAM_USER) {
							event = GeneralInfoAction.EVENT_USER_TO_TRACK;
							break TOP_LOOP;
						}
						else if (team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_GAM_USER) {
							event = GeneralInfoAction.EVENT_USER_TO_TRACK;
							break TOP_LOOP;
						}
						else if (team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_RCO_USER) {
							event = GeneralInfoAction.EVENT_USER_TO_TRACK;
							break TOP_LOOP;
						}
						else if (team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_SCO_USER) {
							event = GeneralInfoAction.EVENT_USER_TO_TRACK;
							break TOP_LOOP;
						}
					}
				}
			}
		}
		HashMap dealObjMap = new HashMap();
		String fromToDo = (map.get("fromToDo") == null) ? null : (String) map.get("fromToDo");
		DefaultLogger.debug(this, "========= fromToDo: " + fromToDo);
		result.put("fromToDo", fromToDo);

		if (event.equals(GeneralInfoAction.EVENT_PROCESS_UPDATE)) {
			String trxID = (String) map.get("trxID");
			DefaultLogger.debug(this, "========= trxID: " + trxID);
			try {
				trxValue = proxy.getCommodityDealTrxValue(ctx, trxID, true);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else if (event.equals(GeneralInfoAction.EVENT_PROCESS) || event.equals(GeneralInfoAction.EVENT_USER_PROCESS)
				|| event.equals(GeneralInfoAction.EVENT_USER_TO_TRACK)
				|| event.equals(GeneralInfoAction.EVENT_PREPARE_CLOSE)
				|| (event.equals(GeneralInfoAction.EVENT_PREPARE_CLOSE_DEAL) && (fromToDo != null))
				|| event.equals(GeneralInfoAction.EVENT_TRACK)) {
			String trxID = (String) map.get("trxID");
			DefaultLogger.debug(this, "========= trxID: " + trxID);
			try {
				trxValue = proxy.getCommodityDealTrxValue(ctx, trxID, true);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else if (event.equals(GeneralInfoAction.EVENT_PREPARE_ADD_DEAL)) {
			String dealType = (String) map.get("dealType");
			dealObj.setDealTypeCode(dealType);
			dealObj.setDealDate(DateUtil.getDate());
			trxValue.setStagingCommodityDeal(dealObj);
			result.put("dealCollateral", null);
		}
		else {
			String idStr = (String) map.get("dealID");
			DefaultLogger.debug(this, "============= dealID: " + idStr);
			long id = Long.parseLong(idStr);
			try {
				trxValue = proxy.getCommodityDealTrxValue(ctx, id);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		if (event.equals(GeneralInfoAction.EVENT_USER_PROCESS)) {
			if ((previous == null) || (previous.length() == 0)) {
				previous = GeneralInfoAction.EVENT_USER_PROCESS;
			}
			event = EVENT_PROCESS;
		}
		if (event.equals(GeneralInfoAction.EVENT_USER_TO_TRACK)) {
			previous = GeneralInfoAction.EVENT_USER_TO_TRACK;
			event = GeneralInfoAction.EVENT_TRACK;
		}

		if (previous != null) {
			result.put("previous_event", previous);
		}
		if (GeneralInfoAction.EVENT_TRACK.equals(event) && ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
			ICommodityDeal stagingObj = trxValue.getStagingCommodityDeal();
			ICommodityDeal actualObj = trxValue.getCommodityDeal();
			stagingObj.setTitleDocsAll(actualObj.getTitleDocsLatest());
			stagingObj.setTitleDocsHistory(actualObj.getTitleDocsHistory());

			trxValue.setStagingCommodityDeal(stagingObj);
		}
		if (event.equals(EVENT_READ)) {
			dealObj = trxValue.getCommodityDeal();
		}
		else {
			dealObj = trxValue.getStagingCommodityDeal();
		}

		/*
		 * if (!CommodityDealAction.EVENT_PROCESS.equals(event) &&
		 * dealObj.getIsAnyWRTitleDoc()) { dealObj =
		 * sortWarehouseReceipt(dealObj); if (EVENT_READ.equals(event)){
		 * trxValue.setCommodityDeal(dealObj); } else {
		 * trxValue.setStagingCommodityDeal(dealObj); } }
		 */
		if (event.equals(CommodityDealAction.EVENT_PREPARE_UPDATE)
				&& trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)) {
			try {

				trxValue.setStagingCommodityDeal((ICommodityDeal) AccessorUtil.deepClone(trxValue.getCommodityDeal()));

			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		if (dealObj.getCollateralID() > 0) {
			result.put("securityID", String.valueOf(dealObj.getCollateralID()));
			try {
				ICommodityCollateral dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy()
						.getCollateral(dealObj.getCollateralID(), true);
				result.put("dealCollateral", dealCollateral);
				dealObjMap.put("dealCollateral", dealCollateral);

			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			result.put("dealCollateral", null);
		}
		String strLimitID = CommodityDealUtil.generateLimitIDByDeal(dealObj);
		if (strLimitID != null) {
			result.put("limitID", strLimitID);
		}

		if (dealObj.getSubLimitID() > 0) {
			result.put("originalSublimit", getSubLimit(dealObj));
		}
		if (dealObj.getContractID() > 0) {
			result.put("contractNo", String.valueOf(dealObj.getContractID()));
		}
		if (dealObj.getContractProfileID() > 0) {
			try {
				ICommodityMaintenanceProxy mainProxy = CommodityMaintenanceProxyFactory.getProxy();
				IProfile profile = mainProxy.getProfileByProfileID(dealObj.getContractProfileID());
				result.put("profileService", CommodityDealUtil.sortBuyerSupplier(profile));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			result.put("profileService", null);
		}

		DefaultLogger.debug(this, "========= event: " + event);
		DefaultLogger.debug(this, "========= fromToDo: " + fromToDo);
		if ((event.equals(GeneralInfoAction.EVENT_PREPARE_CLOSE_DEAL) && (fromToDo == null))) {
			if (!trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					&& !trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
				result.put("wip", "wip");
			}
		}
		else if (event.equals(GeneralInfoAction.EVENT_PREPARE_UPDATE)
				&& (!trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE) && !trxValue.getStatus().equals(
						ICMSConstant.STATE_ND))) {
			result.put("wip", "wip");
		}

		if (event.equals(GeneralInfoAction.EVENT_PREPARE_CLOSE_DEAL)) {
			Amount totalCashDeposit = dealObj.getTotalCashDepositAmt();
			// Check the total cash deposit is greater than 0, if yes, not allow
			// to close deal
			if ((totalCashDeposit != null) && (totalCashDeposit.getAmount() > 0)) {
				result.put("CloseDealErr", "CloseDealErr");
			}
			else {
				Amount balanceDealAmt = dealObj.getBalanceDealAmt();
				Quantity balanceDealQty = dealObj.getBalanceDealQty();
				if (((balanceDealAmt != null) && (balanceDealAmt.getAmount() > 0))
						|| ((balanceDealQty != null) && ((balanceDealQty.getQuantity() != null) && (balanceDealQty
								.getQuantity().doubleValue() > 0)))) {
					result.put("CloseDealErr", "CloseDealErr");
				}
			}
		}

		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ITrxContext aTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		try {
			ILimit[] newLimits = limitProxy.getFilteredNilColCheckListLimits(aTrxContext, limitProfile);
			limitProfile.setLimits(newLimits);

			HashMap commodityLimitMap = CollateralProxyUtil.getCommodityLimitMaps(limitProfile);
			commodityLimitMap = CommodityDealUtil.getCustomerLimit(commodityLimitMap);
			result.put("commodityLimitMap", commodityLimitMap);
			dealObjMap.put("commodityLimitMap", commodityLimitMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		String armCode = "";

		if (limitProfile != null) {
			try {
				HashMap famMap = limitProxy.getFAMName(limitProfile.getLimitProfileID());
				armCode = (String) famMap.get(ICMSConstant.FAM_CODE);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		result.put("ARM_Code", armCode);

		if (event.equals(CommodityDealAction.EVENT_PREPARE_UPDATE)
				|| event.equals(CommodityDealAction.EVENT_PROCESS_UPDATE)
				|| event.equals(CommodityDealAction.EVENT_PREPARE_ADD_DEAL)
				|| event.equals(CommodityDealAction.EVENT_PREPARE_CLOSE_DEAL)) {
			String[] teamCountry = team.getCountryCodes();
			String customerCountry = limitProfile.getOriginatingLocation().getCountryCode();
			boolean isCustomerBelongTeam = false;
			if (customerCountry != null) {
				for (int i = 0; (i < teamCountry.length) && !isCustomerBelongTeam; i++) {
					if (customerCountry.equals(teamCountry[i])) {
						isCustomerBelongTeam = true;
					}
				}
			}
			else {
				isCustomerBelongTeam = true;
			}
			if (!isCustomerBelongTeam) {
				throw new AccessDeniedException("BCA Booking Location is not belongs to team location.");
			}
		}

		Collection selectedWRList = new ArrayList();
		if (dealObj.getReceiptReleases() != null) {
			IReceiptRelease[] releaseList = dealObj.getReceiptReleases();
			for (int i = 0; i < releaseList.length; i++) {
				if (releaseList[i].getSettleWarehouseReceipts() != null) {
					ISettleWarehouseReceipt[] settleWRList = releaseList[i].getSettleWarehouseReceipts();
					for (int j = 0; j < settleWRList.length; j++) {
						selectedWRList.add(String.valueOf(settleWRList[j].getWarehouseReceipt().getRefID()));
					}
				}
			}
		}

		result.put("from_event", event);
		result.put("commodityDealTrxValue", trxValue);

		dealObjMap.put("obj", dealObj);
		result.put("selectedWRList", selectedWRList);
		result.put("generalInfoObj", dealObjMap);
		result.put("TitleDocWR", null);
		result.put("TitleDocWRNeg", null);
		result.put("tab", CommodityDealConstant.TAB_GENERAL_INFO);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ISubLimit getSubLimit(ICommodityDeal dealObj) throws CommandProcessingException {
		DefaultLogger.debug(this, "getSubLimit - Begin.");
		try {
			ICommodityCollateral dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy()
					.getCollateral(dealObj.getCollateralID(), true);
			ICollateralLimitMap[] colLimitMapArray = dealCollateral.getCollateralLimits();
			long limitId = dealObj.getLimitID();
			long subLimitID = dealObj.getSubLimitID();
			DefaultLogger.debug(this, " - LimitId : " + limitId);
			DefaultLogger.debug(this, " - SubLimitId : " + subLimitID);
			for (int i = 0; i < colLimitMapArray.length; i++) {
				DefaultLogger.debug(this, " ColLmtMap - LimitId : " + colLimitMapArray[i].getLimitID());
				if (limitId == colLimitMapArray[i].getLimitID()) {
					ISubLimit[] slArray = colLimitMapArray[i].getSubLimit();
					DefaultLogger.debug(this, "Len of SL array : " + (slArray == null ? 0 : slArray.length));
					if (slArray != null) {
						for (int index = 0; index < slArray.length; index++) {
							DefaultLogger.debug(this, "ColLmtMap - SubLimitID : " + slArray[index].getSubLimitID());
							if (subLimitID == slArray[index].getSubLimitID()) {
								DefaultLogger.debug(this, "Found the Sub Limit.");
								return slArray[index];
							}
						}
					}
				}
			}
		}
		catch (CollateralException e) {
			throw new CommandProcessingException(e.getMessage());
		}
		finally {
			DefaultLogger.debug(this, "getSubLimit - End.");
		}
		return null;
	}
}
