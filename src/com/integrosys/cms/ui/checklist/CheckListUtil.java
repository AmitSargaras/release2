/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditderivative.ICreditDerivative;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.ui.collateral.CommonNatureOfCharge;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2006/10/27 05:42:32 $ Tag: $Name: $
 */
public class CheckListUtil {
	public static String getTrxCountry(long aCheckListID) throws CheckListException {
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICheckList checkList = proxy.getCheckListByID(aCheckListID);
		if (ICMSConstant.DOC_TYPE_CC.equals(checkList.getCheckListType())) {
			return getCCTrxCountry(checkList);
		}
		return getColTrxCountry(checkList);
	}

	public static String getCustodianTrxCountry(ITeam team, ILimitProfile anILimitProfile, ICMSCustomer anCustomer,
			long aCheckListID) throws CheckListException {
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICheckList checkList = proxy.getCheckListByID(aCheckListID);
		if (ICMSConstant.DOC_TYPE_CC.equals(checkList.getCheckListType())) {
			if (isInCountry(team, anILimitProfile, anCustomer)) {
				return getColTrxCountry(anILimitProfile, anCustomer);
			}
			else {
				return getCCTrxCountry(checkList);
			}
		}
		else {
			if (isInCountry(team, anILimitProfile, anCustomer)) {
				return getColTrxCountry(anILimitProfile, anCustomer);
			}
			else {
				if (checkList.getCheckListLocation() != null) {
					return getCCTrxCountry(checkList);
				}
				return null;
			}
		}
	}

	public static String getCCTrxCountry(ICheckList anICheckList) throws CheckListException {
		if (anICheckList.getCheckListLocation() != null) {
			return anICheckList.getCheckListLocation().getCountryCode();
		}
		return null;
	}

	public static String getCCTrxOrgCode(ICheckList anICheckList) throws CheckListException {
		if (anICheckList.getCheckListLocation() != null) {
			return anICheckList.getCheckListLocation().getOrganisationCode();
		}
		return null;
	}

	public static String getColTrxCountry(ICheckList anICheckList) throws CheckListException {
		try {
			ICollateralCheckListOwner owner = (ICollateralCheckListOwner) anICheckList.getCheckListOwner();
			long collateralID = owner.getCollateralID();
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateral collateral = proxy.getCollateral(collateralID, false);
			return collateral.getCollateralLocation();
		}
		catch (CollateralException ex) {
			throw new CheckListException("Exception in getColTrxCountry", ex);
		}
	}

	public static String getColTrxCountry(ILimitProfile anILimitProfile, ICMSCustomer anCustomer) {
		if (anILimitProfile != null) {
			return anILimitProfile.getOriginatingLocation().getCountryCode();
		}
		else {
			return anCustomer.getOriginatingLocation().getCountryCode();
		}
	}

	public static String getColTrxOrgCode(ILimitProfile anILimitProfile, ICMSCustomer anCustomer) {
		if (anILimitProfile != null) {
			return anILimitProfile.getOriginatingLocation().getOrganisationCode();
		}
		else {
			return anCustomer.getOriginatingLocation().getOrganisationCode();
		}
	}

	// For CR CMS-1432 Starts
	/******************************************************/
	public static IRecurrentCheckListSubItem[] filterSubItemList(IRecurrentCheckListSubItem[] recSubItems) {

		try {

			ArrayList list = new ArrayList(Arrays.asList(recSubItems));
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				IRecurrentCheckListSubItem subItem = (IRecurrentCheckListSubItem) iter.next();
				String status = subItem.getStatus();
				if (ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED.equals(status)
						|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(status)) {
					iter.remove();
				}
				else {
					break;
				}
			}

			IRecurrentCheckListSubItem[] filterSubItems = (IRecurrentCheckListSubItem[]) list
					.toArray(new IRecurrentCheckListSubItem[list.size()]);
			return filterSubItems;

		}
		catch (Exception ex) {
			DefaultLogger.debug("com.integrosys.cms.ui.checklist.CheckListUtil", "Exception in filterSubItemList" + ex);
			return recSubItems;
		}

	}

	/******************************************************/
	// For CR CMS-1432 Ends
	// For CR CMS-1432 Starts
	/******************************************************/
	public static int filterSubItemListLength(IRecurrentCheckListSubItem[] recSubItems) {

		try {

			ArrayList list = new ArrayList(Arrays.asList(recSubItems));
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				IRecurrentCheckListSubItem subItem = (IRecurrentCheckListSubItem) iter.next();
				String status = subItem.getStatus();
				if (ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED.equals(status)
						|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(status)) {
					iter.remove();
				}
				else {
					break;
				}
			}

			IRecurrentCheckListSubItem[] filterSubItems = (IRecurrentCheckListSubItem[]) list
					.toArray(new IRecurrentCheckListSubItem[list.size()]);
			return filterSubItems.length;

		}
		catch (Exception ex) {
			DefaultLogger.debug("com.integrosys.cms.ui.checklist.CheckListUtil", "Exception in filterSubItemList" + ex);
			return recSubItems.length;
		}

	}

	/******************************************************/
	// For CR CMS-1432 Ends
	// For CR 26
	/******************************************************/
	public static IConvenantSubItem[] filterSubItemList(IConvenantSubItem[] recSubItems) {

		try {

			ArrayList list = new ArrayList(Arrays.asList(recSubItems));
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				IConvenantSubItem subItem = (IConvenantSubItem) iter.next();
				String status = subItem.getStatus();
				if (ICMSConstant.CONVENANT_STATE_CHECKED.equals(status)
						|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(status)) {
					iter.remove();
				}
				else {
					break;
				}
			}

			IConvenantSubItem[] filterSubItems = (IConvenantSubItem[]) list.toArray(new IConvenantSubItem[list.size()]);
			return filterSubItems;

		}
		catch (Exception ex) {
			DefaultLogger.debug("com.integrosys.cms.ui.checklist.CheckListUtil",
					"Exception in filterSubItemList(IConvenantSubItem)" + ex);
			return recSubItems;
		}

	}

	/******************************************************/
	// For CR 26
	// For CR 26
	/******************************************************/
	public static int filterSubItemListLength(IConvenantSubItem[] recSubItems) {

		try {

			ArrayList list = new ArrayList(Arrays.asList(recSubItems));
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				IConvenantSubItem subItem = (IConvenantSubItem) iter.next();
				String status = subItem.getStatus();
				if (ICMSConstant.CONVENANT_STATE_CHECKED.equals(status)
						|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(status)) {
					iter.remove();
				}
				else {
					break;
				}
			}

			IConvenantSubItem[] filterSubItems = (IConvenantSubItem[]) list.toArray(new IConvenantSubItem[list.size()]);
			return filterSubItems.length;

		}
		catch (Exception ex) {
			DefaultLogger.debug("com.integrosys.cms.ui.checklist.CheckListUtil",
					"Exception in filterSubItemList(IConvenantSubItem)" + ex);
			return recSubItems.length;
		}

	}

	/******************************************************/
	// For CR 26

	/*************************************************************/
	// Start CR29 - List all document held for a BCA
	// Collateral particulars and charge particulars
	// Helper method to get collateral particulars for list of documents held
	// for a BCA
	public static String[] getCollateralParticulars(ICollateral collateralOB, Locale locale) {
		String typeCode = collateralOB.getCollateralSubType().getTypeCode();

		String[] returnStrList = new String[] { "N.A." };
		if (collateralOB != null) {
			if (typeCode.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
				returnStrList = getMarketableSecParticulars((IMarketableCollateral) collateralOB, returnStrList);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)) {
				returnStrList = getPropertyParticulars((IPropertyCollateral) collateralOB, returnStrList);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_COMMODITY)) {
				returnStrList = getCommodityParticulars((ICommodityCollateral) collateralOB, returnStrList);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_INSURANCE)) {
				returnStrList = getInsuranceParticulars(collateralOB, returnStrList);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_CASH)) {
				returnStrList = getCashParticulars((ICashCollateral) collateralOB, returnStrList, locale);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
				returnStrList = getAssetBasedParticulars((IAssetBasedCollateral) collateralOB, returnStrList, locale);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_OTHERS)) {
				returnStrList = getOthersParticulars((IOthersCollateral) collateralOB, returnStrList);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_GUARANTEE)) {
				returnStrList = getGuaranteeParticulars((IGuaranteeCollateral) collateralOB, returnStrList);
			}
			else { // Document type collateral
				returnStrList[0] = "-";
			}
		}
		return returnStrList;
	}

	// helper method to get marketable security collateral particulars
	private static String[] getMarketableSecParticulars(IMarketableCollateral col, String[] returnStrList) {
		int equityCount = 0;
		IMarketableEquity[] marketableEquities = col.getEquityList();
		if (marketableEquities != null) {
			equityCount = marketableEquities.length;
		}
		if (equityCount > 0) {
			StringBuffer strBuffer = null;
			returnStrList = new String[equityCount];
			for (int i = 0; i < equityCount; i++) {
				strBuffer = new StringBuffer();
				strBuffer.append(marketableEquities[i].getRegisteredName() == null ? "-" : marketableEquities[i]
						.getRegisteredName());
				strBuffer.append(", ");
				strBuffer.append(marketableEquities[i].getNoOfUnits());
				strBuffer.append(" units");
				returnStrList[i] = strBuffer.toString();
			}
		}
		return returnStrList;
	}

	// helper method to get property collateral particulars
	private static String[] getPropertyParticulars(IPropertyCollateral col, String[] returnStrList) {
		if ((col.getDescription() != null) && !col.getDescription().equals("")) {
			returnStrList[0] = col.getDescription();
		}
		else {
			returnStrList[0] = "-";
		}
		return returnStrList;
	}

	// helper method to get commodity collateral particulars
	private static String[] getCommodityParticulars(ICommodityCollateral col, String[] returnStrList) {
		int apprCommodityCount = 0;
		IApprovedCommodityType[] apprCommodityTypes = col.getApprovedCommodityTypes();
		if (apprCommodityTypes != null) {
			apprCommodityCount = apprCommodityTypes.length;
		}
		if (apprCommodityCount > 0) {
			returnStrList = new String[apprCommodityCount];
			for (int i = 0; i < apprCommodityCount; i++) {
				long commonRefLong = apprCommodityTypes[i].getCommonRef();
				String commonRef = String.valueOf(commonRefLong);
				commonRef = ((commonRef == null) || commonRef.equals("")) ? "-" : commonRef;
				returnStrList[i] = commonRef;
			}
		}
		return returnStrList;
	}

	// helper method to get Insurance collateral particulars
	private static String[] getInsuranceParticulars(ICollateral col, String[] returnStrList) {
		String subTypeCode = col.getCollateralSubType().getSubTypeCode();
		if (subTypeCode.equals(ICMSConstant.COLTYPE_INS_CR_DEFAULT_SWAPS)) {
			ICreditDefaultSwaps collateral = (ICreditDefaultSwaps) col;
			returnStrList[0] = collateral.getDescription();
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_INS_CR_DERIVATIVE)) {
			ICreditDerivative collateral = (ICreditDerivative) col;
			returnStrList[0] = collateral.getDescription();
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_INS_CR_INS)) {
			ICreditInsurance collateral = (ICreditInsurance) col;
			returnStrList[0] = collateral.getDescription();
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_INS_KEYMAN_INS)) {
			IKeymanInsurance collateral = (IKeymanInsurance) col;
			returnStrList[0] = collateral.getInsuranceType();
		}
		if ((returnStrList[0] == null) || returnStrList[0].equals("")) {
			returnStrList[0] = "-";
		}
		return returnStrList;
	}

	// helper method to get Cash Collateral Particulars
	private static String[] getCashParticulars(ICashCollateral col, String[] returnStrList, Locale locale) {
		int depositCount = 0;
		ICashDeposit[] cashDeposits = col.getDepositInfo();
		if (cashDeposits != null) {
			depositCount = cashDeposits.length;
		}
		if (depositCount > 0) {
			returnStrList = new String[depositCount];
			for (int i = 0; i < depositCount; i++) {
				try {
					returnStrList[i] = cashDeposits[i].getDepositAmount().getCurrencyCode() + " "
							+ CurrencyManager.convertToString(locale, cashDeposits[i].getDepositAmount());
				}
				catch (Exception e) {
					DefaultLogger.debug("CheckListUtil", "getCashParticular amountToString throw exception");
					returnStrList[i] = "-";
				}
			}
		}
		return returnStrList;
	}

	// helper method to get Asset based collateral particulars
	private static String[] getAssetBasedParticulars(IAssetBasedCollateral col, String[] returnStrList, Locale locale) {
		String subTypeCode = col.getCollateralSubType().getSubTypeCode();
		if (subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)) {
			String remarks = null;
			if (col instanceof IChargeCommon) {
				remarks = ((IChargeCommon) col).getRemarks();
			}
			else {
				remarks = ((IGeneralCharge) col).getRemarks();
			}
			remarks = ((remarks == null) || remarks.equals("")) ? "-" : remarks;
			returnStrList[0] = remarks;
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
			int chequeCount = 0;
			IPostDatedCheque[] postDatedCheques = ((IAssetPostDatedCheque) col).getPostDatedCheques();
			if (postDatedCheques != null) {
				chequeCount = postDatedCheques.length;
			}
			if (chequeCount > 0) {
				returnStrList = new String[chequeCount];
				for (int i = 0; i < chequeCount; i++) {
					try {
						returnStrList[i] = postDatedCheques[i].getChequeAmount().getCurrencyCode() + " "
								+ CurrencyManager.convertToString(locale, postDatedCheques[i].getChequeAmount());
					}
					catch (Exception e) {
						DefaultLogger.debug("CheckListUtil", "getAssetBasedParticulars amountToString throw exception");
						returnStrList[i] = "-";
					}
				}
			}
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_GEN_AGENT)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_OPEN)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_AGENT)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_NOAGENT)) {
			returnStrList[0] = col.getCurrencyCode() + ", ";
			String invoiceType = ((IReceivableCommon) col).getInvoiceType();
			returnStrList[0] += ((invoiceType == null) || invoiceType.equals("")) ? "-" : invoiceType;
		}
		return returnStrList;
	}

	// helper method to get others collateral type particulars
	private static String[] getOthersParticulars(IOthersCollateral col, String[] returnStrList) {
		String remarks = col.getDescription();
		returnStrList[0] = ((remarks == null) || remarks.equals("")) ? "-" : remarks;

		return returnStrList;
	}

	// helper method to get guarantee collateral type particulars
	private static String[] getGuaranteeParticulars(IGuaranteeCollateral col, String[] returnStrList) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(col.getDescription() == null ? "-" : col.getDescription());
		strBuffer.append(", ");
		strBuffer.append(col.getReferenceNo() == null ? "-" : "Ref. " + col.getReferenceNo());
		strBuffer.append(", ");
		Date guaranteeDate = col.getGuaranteeDate();
		String temp = null;
		if (guaranteeDate != null) {
			temp = DateUtil.formatDate("d MMM yyyy", guaranteeDate);
		}
		strBuffer.append(temp == null ? "" : temp);
		returnStrList[0] = strBuffer.toString();

		return returnStrList;
	}

	// Helper method to get charge particulars of a collateral for list of
	// documents held for a BCA
	public static String[] getChargeParticulars(ICollateral collateralOB, Locale locale) {
		String typeCode = collateralOB.getCollateralSubType().getTypeCode();
		String subTypeCode = collateralOB.getCollateralSubType().getSubTypeCode();

		String[] returnStrList = new String[] { "N.A." };
		if ((collateralOB != null) && (collateralOB.getLimitCharges() != null)
				&& (collateralOB.getLimitCharges().length > 0)) {
			if (typeCode.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)
					|| typeCode.equals(ICMSConstant.SECURITY_TYPE_COMMODITY)
					|| typeCode.equals(ICMSConstant.SECURITY_TYPE_INSURANCE)
					|| typeCode.equals(ICMSConstant.SECURITY_TYPE_CASH)
					|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
				returnStrList[0] = getLimitChargeParticulars(collateralOB.getLimitCharges()[0], typeCode, locale);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)
					|| typeCode.equals(ICMSConstant.SECURITY_TYPE_OTHERS)
					|| (typeCode.equals(ICMSConstant.SECURITY_TYPE_ASSET) && !subTypeCode
							.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE))) {
				returnStrList = getLimitChargeParticulars(collateralOB.getLimitCharges(), typeCode, locale);
			}
			else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_GUARANTEE)) {
				returnStrList[0] = formatAmountStr(((IGuaranteeCollateral) collateralOB).getGuaranteeAmount(), locale);
			}
			else { // document type collateral
				returnStrList[0] = "-";
			}
		}
		return returnStrList;
	}

	// helper method to get a limit charge particulars
	private static String getLimitChargeParticulars(ILimitCharge charge, String typeCode, Locale locale) {
		String chargeRank = CheckListHelper.formatSecurityRank(charge);
		String chargeNature = "-";
		if (charge.getNatureOfCharge() != null) {
			chargeNature = CommonNatureOfCharge.getNatureOfChargeDescription(typeCode, charge.getNatureOfCharge());
			chargeNature = chargeNature == null ? "-" : chargeNature;
		}
		String chargeAmount = formatAmountStr(charge.getChargeAmount(), locale);
		return chargeRank + ", " + chargeAmount + ", " + chargeNature;
	}

	// helper method to get a list of limit charge particulars
	private static String[] getLimitChargeParticulars(ILimitCharge[] chargeList, String typeCode, Locale locale) {
		String[] returnStrList = new String[chargeList.length];
		for (int i = 1; i <= chargeList.length; i++) {
			for (int j = 0; j < chargeList.length; j++) {
				if ((chargeList[j].getSecurityRank() == i) || (chargeList[j].getSecurityRank() == 0)) {
					returnStrList[i - 1] = getLimitChargeParticulars(chargeList[j], typeCode, locale);
					break;
				}
			}
		}
		return returnStrList;
	}

	private static String formatAmountStr(Amount amt, Locale locale) {
		String returnStr = "-";
		if (amt != null) {
			try {
				returnStr = amt.getCurrencyCode() + " ";
				returnStr += CurrencyManager.convertToString(locale, amt);
			}
			catch (Exception e) {
				DefaultLogger.debug("CheckListUtil.formatAmountStr", amt + "\t" + e.getMessage()
						+ " amount format error");
			}
		}
		return returnStr;
	}

	// End CR29 - List all document held for a BCA
	/*************************************************************************/

	public static boolean allowEditRecurrentCovenantChkList(ICommonUser user, ITeam team, ICMSCustomer customer,
			ILimitProfile profile) {
		return allowByFunctionAccess(user, team) && allowByDDAP(customer, profile, team);
	}

	/**
	 * Determines function access to edit recurrent/recurrent checklist.
	 * @param team The team.
	 * @param user The user.
	 */
	public static boolean allowByFunctionAccess(ICommonUser user, ITeam team) {
		String[] countryList = team.getCountryCodes();

		for (int i = 0; i < team.getTeamMemberships().length; i++) {
			long memType = team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID();
			if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == memType) || (ICMSConstant.TEAM_TYPE_CPC_MAKER == memType)) {
				for (int ii = 0; ii < countryList.length; ii++) {
					if (countryList[ii].equals("IN") || countryList[ii].equals("MY")) {
						return false;
					}
				}
				return true;
			}
			else if ((ICMSConstant.TEAM_TYPE_SSC_MAKER == memType) 
					|| (ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH == memType)
					|| (ICMSConstant.TEAM_TYPE_SSC_CHECKER == memType)
					|| (ICMSConstant.TEAM_TYPE_SSC_CHECKER_WFH == memType)) {
				for (int ii = 0; ii < countryList.length; ii++) {
					if (countryList[ii].equals("IN") || countryList[ii].equals("MY")) {
						return true;
					}
				}
			}
			else {
				return true; // for FAM/ GAM/ RCO/ SCO/ MIS/ CPC Support can
								// access any country
			}
		}
		return true;
	}

	/**
	 * Determines DAP to edit recurrent/recurrent checklist.
	 */
	public static boolean allowByDDAP(ICMSCustomer customer, ILimitProfile profile, ITeam team) {
		if (null == customer) {
			return false;
		}

		if (null == profile) {
			return false;
		}

		if (null == team) {
			return false;
		}
		String countryList[] = team.getCountryCodes();
		for (int i = 0; i < team.getTeamMemberships().length; i++) {
			long memType = team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID();
			if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == memType) || (ICMSConstant.TEAM_TYPE_CPC_MAKER == memType)) {
				for (int ii = 0; ii < countryList.length; ii++) {
					if (countryList[ii].equals(profile.getOriginatingLocation().getCountryCode())
							&& (countryList[ii].equals("IN") || countryList[ii].equals("MY"))) {
						return false;
					}
				}
				return true;
			}
			else if ((ICMSConstant.TEAM_TYPE_SSC_MAKER == memType) 
					|| (ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH == memType)
					|| (ICMSConstant.TEAM_TYPE_SSC_CHECKER == memType) 
					|| (ICMSConstant.TEAM_TYPE_SSC_CHECKER_WFH == memType)) {
				for (int ii = 0; ii < countryList.length; ii++) {
					if (countryList[ii].equals(profile.getOriginatingLocation().getCountryCode())
							&& (countryList[ii].equals("IN") || countryList[ii].equals("MY"))) {
						return true;
					}
				}
			}
			else { // for FAM/ GAM/ RCO/ SCO/ MIS/ CPC Support can access any
					// country
				return true;
			}
		}
		return false;
	}

    public static boolean allowByDDAP(String country, ITeam team) {
        if (null == country) {
            return false;
        }

        if (null == team) {
            return false;
        }
        String countryList[] = team.getCountryCodes();
        for (int i = 0; i < team.getTeamMemberships().length; i++) {
            long memType = team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID();
            //removed check for CPC - since no longer in use.
            if ((ICMSConstant.TEAM_TYPE_SSC_MAKER == memType)
            		|| (ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH == memType)
            		|| (ICMSConstant.TEAM_TYPE_SSC_CHECKER == memType) 
            		|| (ICMSConstant.TEAM_TYPE_SSC_CHECKER_WFH == memType)) {
                for (int ii = 0; ii < countryList.length; ii++) {
                    if (countryList[ii].equals(country)) {
                        return true;
                    }
                }
            }
            else { // for FAM/ GAM/ RCO/ SCO/ MIS/ CPC Support can access any
                    // country
                return true;
            }
        }
        return false;
    }

	/**
	 * Helper method to get correct cpc custodian display statuc
	 * 
	 * @param itm - ICheckListItem
	 */
	public static String getCPCCustodianStatus(ICheckListItem itm) {
		String custDocStatus = "";
		if (itm == null) {
			return custDocStatus;
		}
		if (isCheckListItemInVault(itm) && !isCheckListItemWaived(itm)) {
			if (isCheckListItemDeleted(itm)) {
				if ((itm.getCustodianDocStatus() != null) && !isCustDocItemReceived(itm)) {
					custDocStatus = itm.getCustodianDocStatus();
				}
			}
			else {
				if ((itm.getCustodianDocStatus() == null) || isCustDocItemReceived(itm)) {
					custDocStatus = ICMSConstant.STATE_ITEM_AWAITING;
				}
				else {
					custDocStatus = itm.getCustodianDocStatus();
				}
			}
		}
		return custDocStatus;
	}

	private static boolean isCheckListItemInVault(ICheckListItem itm) {
		return itm.getIsInVaultInd();
	}

	private static boolean isCheckListItemWaived(ICheckListItem itm) {
		return ((itm.getItemStatus() != null) && itm.getItemStatus().equals(ICMSConstant.STATE_ITEM_WAIVED));
	}

	private static boolean isCheckListItemDeleted(ICheckListItem itm) {
		return ((itm.getItemStatus() != null) && itm.getItemStatus().equals(ICMSConstant.STATE_ITEM_DELETED));
	}

	private static boolean isCustDocItemReceived(ICheckListItem itm) {
		return ((itm.getCustodianDocStatus() != null) && itm.getCustodianDocStatus().equals(
				ICMSConstant.STATE_ITEM_RECEIVED));
	}

	private static boolean isCustDocItemLodged(ICheckListItem itm) {
		return ((itm.getCustodianDocStatus() != null) && itm.getCustodianDocStatus().equals(ICMSConstant.STATE_LODGED));
	}

	public static boolean isInCountry(ITeam team, ILimitProfile anILimitProfile) {
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocation = anILimitProfile.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInCountry(ITeam team, ICMSCustomer customer) {
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocation = customer.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInCountry(ITeam team, ILimitProfile anILimitProfile, ICMSCustomer customer) {
		if (anILimitProfile != null) {
			return isInCountry(team, anILimitProfile);
		}
		else {
			return isInCountry(team, customer);
		}
	}

	public static long getTeamType(ITeam team, ICommonUser user) {
		for (int i = 0; i < team.getTeamMemberships().length; i++) {
			for (int j = 0; j < team.getTeamMemberships()[i].getTeamMembers().length; j++) {
				if (team.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
						.getUserID()) {
					return team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID();
				}
			}
		}
		return 0;
	}
}
