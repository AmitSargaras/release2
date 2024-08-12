/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/SecuritySubTypeUtil.java,v 1.47 2006/10/09 05:22:19 jzhai Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.collateral.property.ChargeTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Various helper method for collateral module, such as getting .do url based on
 * subtype, filter deleted linkage (pledgor, pledge, charge), auto create charge
 * based on security subtype, etc.
 * 
 * @author jzhai
 * @author Chong Jun Yong
 * @since 2006/10/09
 */
public abstract class SecuritySubTypeUtil {

	public static String getUrl(String securitySubType, int flag) {
		return (getUrl(securitySubType, "read", flag));
	}

	public static String getUrl(String securitySubType, String event) {
		return getUrl(securitySubType, event, 1);
	}

	public static String getUrl(String securitySubType) {
		return getUrl(securitySubType, 1);
	}

	public static ICollateralLimitMap[] retrieveNonDeletedCollateralLimitMap(ICollateral collateral) {
		ICollateralLimitMap[] collateralLimitMaps = collateral.getCollateralLimits();

		if (collateralLimitMaps == null) {
			return null;
		}
		else {
			List nonDeletedCollateralLimitList = new ArrayList();
			for (int i = 0; i < collateralLimitMaps.length; i++) {
				ICollateralLimitMap collateralLimitMap = collateralLimitMaps[i];
				if (!ICMSConstant.HOST_STATUS_DELETE.equals(collateralLimitMap.getSCIStatus())) {
					nonDeletedCollateralLimitList.add(collateralLimitMap);
				}
			}
			return (ICollateralLimitMap[]) nonDeletedCollateralLimitList.toArray(new ICollateralLimitMap[0]);
		}
	}

	public static ICollateralPledgor[] retrieveNonDeletedCollateralPledgorMap(ICollateral collateral) {
		ICollateralPledgor[] collateralPledgorMaps = collateral.getPledgors();

		if (collateralPledgorMaps == null) {
			return null;
		}
		else {
			List nonDeletedCollateralPledgorList = new ArrayList();
			for (int i = 0; i < collateralPledgorMaps.length; i++) {
				ICollateralPledgor collateralPledgorMap = collateralPledgorMaps[i];
				if (!ICMSConstant.HOST_STATUS_DELETE.equals(collateralPledgorMap.getSCIPledgorMapStatus())) {
					nonDeletedCollateralPledgorList.add(collateralPledgorMap);
				}
			}
			return (ICollateralPledgor[]) nonDeletedCollateralPledgorList.toArray(new ICollateralPledgor[0]);
		}
	}

	public static String getUrl(String securitySubType, String event, int flag) {
		if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_INTBR_INDEMNITY)) {
			return ("GteIndemCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_GOVERNMENT)) {
			return ("GteGovtCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_CORP_3RDPARTY)) {
			return ("GteCorp3rdCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_CORP_RELATED)) {
			return ("GteCorpRelCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_SBLC_DIFFCCY)) {
			return ("GteSLCDiffCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY)) {
			return ("GteSLCSameCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_DIFFCCY)) {
			return ("GteBankDiffCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_SAMECCY)) {
			return ("GteBankSameCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_INS_KEYMAN_INS)) {
			return ("InsKeymanCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_INS_CR_INS)) {
			return ("InsCrdtCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_INS_CR_DERIVATIVE)) {
			return ("InsCrdtDerivCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_INS_CR_DEFAULT_SWAPS)) {
			return ("InsSwapCollateral.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_COMMODITY_CHEMICAL)) {
			return ("CommodityMain.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_COMMODITY_ENERGY)) {
			return ("CommodityMain.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_COMMODITY_FERROUS_METAL)) {
			return ("CommodityMain.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_COMMODITY_NON_FERROUS_METAL)) {
			return ("CommodityMain.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_COMMODITY_NON_FERROUS_OTHERS)) {
			return ("CommodityMain.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_COMMODITY_SOFT_BULK)) {
			return ("CommodityMain.do?event=" + event + "&flag=" + flag);
		}
		if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN)) {
			return ("MarksecBondForeignCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_GOVT_LOCAL)) {
			return ("MarksecBillCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_FOREIGN)) {
			return ("MarksecMainForeignCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL)) {
			return ("MarksecMainLocalCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL)) {
			return ("MarksecBondLocalCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_PORTFOLIO_OWN)) {
			return ("MarksecSCBSecCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_PORTFOLIO_OTHERS)) {
			return ("MarksecCustSecCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL)) {
			return ("MarksecNonListedLocalCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_FOREIGN)) {
			return ("MarksecOtherListedForeignCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL)) {
			return ("MarksecOtherListedLocalCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_DIFFCCY)) {
			return ("MarksecGovtForeignDiffCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_SAMECCY)) {
			return ("MarksecGovtForeignSameCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)) {
			return ("PropAgriCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_LAND_VACANT)) {
			return ("PropRuralCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_COM_GENERAL)) {
			return ("PropCommGeneralCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_COM_SHOP_HOUSE)) {
			return ("PropCommShopCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_SPEC_SERVICE_APT)) {
			return ("PropCommServiceAptCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_SPEC_INDUSTRIAL)) {
			return ("PropIndusSpecCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_INDUSTRIAL)) {
			return ("PropIndusCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_RES_STANDARD)) {
			return ("PropResStdCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_RES_LUXURY)) {
			return ("PropResLuxCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_SPEC_HOTEL)) {
			return ("PropSpHotelCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_SPEC_OTHERS)) {
			return ("PropSpOtherCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN)) {
			return ("PropLandUrbanCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
			return ("AssetPostDatedChqsCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_RECV_OPEN)) {
			return ("AssetRecOpenCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_NOAGENT)) {
			return ("AssetRecSpecNonAgentCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_PERSONAL)) {
			return ("GteIndivCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_RECV_GEN_AGENT)) {
			return ("AssetRecGenAgentCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_AGENT)) {
			return ("AssetRecSpecAgentCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS)) {
			return ("AssetSpecOtherCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_VESSEL)) {
			return ("AssetVesselCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
			return ("AssetSpecVehiclesCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
			return ("AssetSpecPlantCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)) {
			return ("AssetAircraftCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE)) {
			return ("AssetGenChargeCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD)) {
			return ("AssetSpecGoldCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_CASH_CASH)) {
			return ("CashCashCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_CASH_FD)) {
			return ("CashFdCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_CASH_REPO)) {
			return ("CashRepoCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_CASH_DIFFCCY)) {
			return ("CashDiffCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_CASH_HKDUSD)) {
			return ("CashHKDUSDCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_CASH_SAMECCY)) {
			return ("CashSameCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_LOU)) {
			return ("DocLoUCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_COMFORT)) {
			return ("DocComfortCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_PLEDGE)) {
			return ("DocPledgeCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_CR_AGREEMENT)) {
			return ("DocGenCreditCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_FX_NETTING)) {
			return ("DocDervNetCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_FX_ISDA)) {
			return ("DocDervISDCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_DEED_SUB)) {
			return ("DocDeedSubCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_LEASE_AGREEMENT)) {
			return ("DocAgreementCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_NA)) {
			return ("CollateralRedirect.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_OTHERS_OTHERSA)) {
			return ("OthersaCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_LETTER_INDEMNITY)) {
			return ("DocLoICollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_DOC_DEED_ASSIGNMENT)) {
			return ("DocDoACollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_NOCOLLATERAL)) {
			return ("NoCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_INWARDLC)) {
			return ("GteInwardLCCollateral.do?event=" + event + "&flag=" + flag);
		}
		else if (securitySubType.equals(ICMSConstant.COLTYPE_GUARANTEE_GOVT_LINK)) {
			return ("GteGovtLinkCollateral.do?event=" + event + "&flag=" + flag);
		}
		else {
			String actualSecuritySubType = CommonDataSingleton.getCodeCategoryLabelByValue(
					CategoryCodeConstant.SEC_SUBTYP, securitySubType);
			if (actualSecuritySubType != null) {
				DefaultLogger.error("SecuritySubTypeUtil.java", "value of actualSecuritySubType is "
						+ actualSecuritySubType);
				String path = getUrl(actualSecuritySubType, event, flag);
				return path;

			}
			else {
				return ("");
			}
		}
	}

	public static Collection getValuerListByCountry(String countryCode) {
		List resultList = new ArrayList();
		if ((countryCode != null) && !countryCode.equals("")) {
			try {
				Map valuerMap = CollateralProxyFactory.getProxy().getCollateralValuer(countryCode);
				resultList = (List) valuerMap.get("valuerName");
				Collections.sort(resultList);
			}
			catch (Exception e) {
				DefaultLogger.error("SecuritySubTypeUtil.java",
						"Exception caught at getValuerListByCountry: country code: " + countryCode);
			}
		}
		return resultList;
	}

	public static void validateSetLimitChargeDetails(ICollateral iColObj, boolean isCheckListCompleted,
			HashMap exceptionMap) {
		if (!canCollateralMaintainMultipleCharge(iColObj)) {
			ILimitCharge limit = iColObj.getLimitCharges()[0];

			limit.setChargeCcyCode(iColObj.getCurrencyCode());
			ILimitCharge[] limitArray = iColObj.getLimitCharges();
			limitArray[0] = limit;
			iColObj.setLimitCharges(limitArray);
		}
		else {
			ILimitCharge[] limitCharges = iColObj.getLimitCharges();
			boolean isSameRank = false;

			for (int i = 0; i < limitCharges.length; i++) {
				if (limitCharges[i] != null) {
					limitCharges[i].setChargeCcyCode(iColObj.getCurrencyCode());
					limitCharges[i].setPriorChargeCcyCode(iColObj.getCurrencyCode());
					if (limitCharges[i].getChargeAmount() != null) {
						limitCharges[i].getChargeAmount().setCurrencyCode(iColObj.getCurrencyCode());
					}
					if (limitCharges[i].getPriorChargeAmount() != null) {
						limitCharges[i].getPriorChargeAmount().setCurrencyCode(iColObj.getCurrencyCode());
					}
					if (!isSameRank && (i + 1 < limitCharges.length) && (limitCharges[i + 1] != null)) {
						if (limitCharges[i].getSecurityRank() == limitCharges[i + 1].getSecurityRank()) {
							isSameRank = true;
						}
					}
					if (StringUtils.isBlank(limitCharges[i].getPartyCharge())) {
						limitCharges[i].setPartyCharge(ILimitCharge.PARTY_CHARGE_FIRST_VALUE);
					}
				}
			}
			if (isSameRank) {
				exceptionMap.put("chargeError", new ActionMessage("error.collateral.charge.rank.same"));
			}

			iColObj.setLimitCharges(limitCharges);
		}
	}

	public static void setCMSCurrency(ICollateral iColObj) {
		String subtypeCode = iColObj.getCollateralSubType().getSubTypeCode();
		if (subtypeCode.equals(ICMSConstant.COLTYPE_CASH_HKDUSD)
				|| subtypeCode.equals(ICMSConstant.COLTYPE_CASH_SAMECCY)) {
			ICashDeposit[] deposit = ((ICashCollateral) iColObj).getDepositInfo();
			if (deposit != null) {
				for (int i = 0; i < deposit.length; i++) {
					deposit[i].setDepositCcyCode(iColObj.getCurrencyCode());
				}
			}
			((ICashCollateral) iColObj).setDepositInfo(deposit);
		}
		else if (iColObj instanceof IMarketableCollateral) {
			IMarketableEquity[] equity = ((IMarketableCollateral) iColObj).getEquityList();
			if (equity != null) {
				for (int i = 0; i < equity.length; i++) {
					equity[i].setItemCurrencyCode(iColObj.getCurrencyCode());
				}
			}
			((IMarketableCollateral) iColObj).setEquityList(equity);
		}
	}

	public static void setCollateralSubTypeCode(ICollateralTrxValue itrxValue, ICollateral iColObj) {
		if (itrxValue != null) {
			if (itrxValue.getCollateral() != null) {
				if (itrxValue.getCollateral().getSCISubTypeValue() != null) {
					if (itrxValue.getCollateral().getCollateralSubType() != null) {
						itrxValue.getCollateral().getCollateralSubType().setSubTypeCode(
								itrxValue.getCollateral().getSCISubTypeValue());
					}
				}
			}
			if (itrxValue.getStagingCollateral() != null) {
				if (itrxValue.getStagingCollateral().getSCISubTypeValue() != null) {
					if (itrxValue.getStagingCollateral().getCollateralSubType() != null) {
						itrxValue.getStagingCollateral().getCollateralSubType().setSubTypeCode(
								itrxValue.getStagingCollateral().getSCISubTypeValue());
					}
				}
			}
		}
		if (iColObj != null) {
			if (iColObj.getSCISubTypeValue() != null) {
				if (iColObj.getCollateralSubType() != null) {
					iColObj.getCollateralSubType().setSubTypeCode(iColObj.getSCISubTypeValue());
				}
			}
		}
	}

	/**
	 * Get a list of limits tied to the collateral as a List.
	 * 
	 * @param col of type ICollateral
	 * @return a List of cms limit id in String
	 */
	public static List getDistinctLimitList(ICollateral col) {
		ICollateralLimitMap[] limitMapList = col.getCurrentCollateralLimits();
		List limitList = new ArrayList();
		int count = limitMapList == null ? 0 : limitMapList.length;
		try {
			for (int i = 0; i < count; i++) {
				String strLmtID = null;
				if (limitMapList[i].getCustomerCategory().equals("MB")) {
					strLmtID = String.valueOf(limitMapList[i].getLimitID());
				}
				else {
					strLmtID = String.valueOf(limitMapList[i].getSCICoBorrowerLimitID());
				}

				limitList.add(strLmtID);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return limitList;
	}

	public static void validateAssetBasedGenCharge(IGeneralCharge iColObj, HashMap map, HashMap resultMap,
			HashMap exceptionMap) {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap limitMap = (HashMap) map.get("limitMap");
		if ((limitMap == null) || (limitMap.size() == 0)) {
			List limitList = getDistinctLimitList(iColObj);
			try {
				limitMap = LimitProxyFactory.getProxy().getApprovedLimitAmount(limitList);
			}
			catch (Exception e) {
				DefaultLogger.error("failed to retrieve approved limit amount", e);
			}
		}
		BigDecimal totalApprovedLimit = AssetGenChargeUtil.getTotalApprovedLimit(limitMap, iColObj.getSCISecurityID(),
				iColObj.getCurrencyCode(), locale);
		AssetGenChargeUtil.validateLimitDetails(totalApprovedLimit, iColObj, exceptionMap);

		if (exceptionMap.size() > 0) {
			boolean isCPC = false;
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			TOP_LOOP: for (int i = 0; i < userTeam.getTeamMemberships().length; i++) {
				for (int j = 0; j < userTeam.getTeamMemberships()[i].getTeamMembers().length; j++) {
					if (userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
							.getUserID()) {
						if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_CPC_MAKER) {
							isCPC = true;
							DefaultLogger.debug("SecuritySubTypeUtil.validateAssetBasedGenCharge",
									"User is cpc maker...");
							break TOP_LOOP;
						}
					}
				}
			}
			if (!isCPC) {
				resultMap.put("forwardPage", CollateralAction.EVENT_READ);
			}
			else {
				resultMap.put("forwardPage", CollateralAction.EVENT_PREPARE_UPDATE);
			}
		}
	}

	public static boolean hasErrorGenChargeDrawingPower(IGeneralCharge iColObj) {

		if (iColObj.getMargin() == ICMSConstant.DOUBLE_INVALID_VALUE) {
			return true;
		}
		if (iColObj.getBankShare() == ICMSConstant.DOUBLE_INVALID_VALUE) {
			return true;
		}
		ICollateralLimitMap[] colLimitMap = iColObj.getCurrentCollateralLimits();
		if (colLimitMap != null) {
			for (int i = 0; i < colLimitMap.length; i++) {

				if (colLimitMap[i].getAppliedLimitAmount() == null) {
					return true;
				}

				if (colLimitMap[i].getReleasedLimitAmount() == null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method to set the data to persist in the DB for the report
	 * generation
	 */
	public static void setAssetGenChargeInfo(IGeneralCharge iColObj) {

		// setting total debtor applicable amount
		IDebtor debtor = iColObj.getDebtor();
		if (debtor != null) {
			debtor.setTotalApplicableAmt(((OBDebtor) debtor).getCalculatedApplicableDebtAmount());
			iColObj.setDebtor(debtor);
		}

		// setting stock recoverable amount
		Collection stockSummaryList = AssetGenChargeUtil.formatStockList(iColObj);
		iColObj = AssetGenChargeUtil.setStockRecoverableAmt(stockSummaryList, iColObj);

		// Setting insurance shortfall amount
		Amount stockInsrShortFallAmt = null;
		Amount stockGrossAmt = ((OBGeneralCharge) iColObj).getStockGrossValue();
		Amount stockTotalValidInsrAmt = ((OBGeneralCharge) iColObj).getTotalValidStockInsrAmount();
		if ((stockGrossAmt == null) || (stockTotalValidInsrAmt == null)
				|| AssetGenChargeUtil.isForexErrorAmount(stockTotalValidInsrAmt)
				|| AssetGenChargeUtil.isForexErrorAmount(stockGrossAmt)) {
			stockInsrShortFallAmt = null;
		}
		else {
			try {
				stockInsrShortFallAmt = (stockTotalValidInsrAmt == null) ? stockGrossAmt : stockGrossAmt
						.subtract(stockTotalValidInsrAmt);
			}
			catch (ChainedException e) {
				IllegalArgumentException iae = new IllegalArgumentException("failed to substract amount ["
						+ stockTotalValidInsrAmt + "] from [" + stockGrossAmt + "]");
				iae.initCause(e);
				throw iae;
			}
		}

		iColObj.setStockInsrShortfallAmount(stockInsrShortFallAmt);

		iColObj.setDrawingPowerLessInsrGrossAmount(((OBGeneralCharge) iColObj)
				.getCalculatedDrawingPowerLessInsrGrossAmount());
		iColObj.setDrawingPowerGrossAmount(((OBGeneralCharge) iColObj).getCalculatedDrawingPowerGrossAmount());
	}

	/**
	 * Helper method to get number of delete item
	 */
	public static int getNumberOfDelete(String[] deleteIDList, int oldListLength) {
		if ((deleteIDList == null) || (deleteIDList.length == 0)) {
			return 0;
		}
		int numDelete = 0;
		if (deleteIDList.length <= oldListLength) {
			for (int i = 0; i < deleteIDList.length; i++) {
				if (Integer.parseInt(deleteIDList[i]) < oldListLength) {
					numDelete++;
				}
			}
		}
		return numDelete;
	}

	/**
	 * Helper method to delete object list from delete item list
	 */
	public static Object[] deleteObjByList(Object[] oldList, Object[] newList, String[] deleteList) {
		int i = 0, j = 0;
		while (i < oldList.length) {
			if ((j < deleteList.length) && (Integer.parseInt(deleteList[j]) == i)) {
				j++;
			}
			else {
				newList[i - j] = oldList[i];
			}
			i++;
		}
		return newList;
	}

	/**
	 * Helper Method to get conversion currency for insurance policy
	 */
	public static String getConversionCcy(ICollateral col) {
		if ((col.getValuation() != null) && (col.getValuation().getCurrencyCode() != null)
				&& !col.getValuation().getCurrencyCode().equals("")) {
			return col.getValuation().getCurrencyCode();
		}
		return col.getCurrencyCode();
	}

	public static boolean hasSameInsurancePolicy(IInsurancePolicy[] insuranceList, IInsurancePolicy obj, int index) {
		if ((insuranceList == null) || (insuranceList.length == 0)) {
			return false;
		}
		for (int i = 0; i < insuranceList.length; i++) {
			if ((i != index)
					&& ((insuranceList[i].getPolicyNo() != null) && (insuranceList[i].getPolicyNo().length() > 0))
					&& insuranceList[i].getPolicyNo().equals(obj.getPolicyNo())
					&& (insuranceList[i].getInsurerName() != null)
					&& insuranceList[i].getInsurerName().equals(obj.getInsurerName())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasSameAddtionalDocumentFacilityDetails(IAddtionalDocumentFacilityDetails[] additonalDocFacDetailsList, IAddtionalDocumentFacilityDetails obj, int index) {
		if ((additonalDocFacDetailsList == null) || (additonalDocFacDetailsList.length == 0)) {
			return false;
		}
		for (int i = 0; i < additonalDocFacDetailsList.length; i++) {
			if ((i != index)
					&& ((additonalDocFacDetailsList[i].getDocFacilityCategory() != null))
					&& additonalDocFacDetailsList[i].getDocFacilityCategory().equals(obj.getDocFacilityCategory())
					&& (additonalDocFacDetailsList[i].getDocFacilityType() != null)
					&& additonalDocFacDetailsList[i].getDocFacilityType().equals(obj.getDocFacilityType())
					&& (additonalDocFacDetailsList[i].getDocFacilityAmount() != null)
					&& additonalDocFacDetailsList[i].getDocFacilityAmount().equals(obj.getDocFacilityAmount())) {
				return true;
			}
		}
		return false;
	}
	
	

	public static String getAverageMargin(Amount cmvAmt, Amount fsvAmt, Locale locale) {
		double returnVal = ICMSConstant.DOUBLE_INVALID_VALUE;
		if ((cmvAmt != null) && (fsvAmt != null)) {
			if (cmvAmt.getAmount() > 0) {
				// returnVal =
				// fsvAmt.divide(cmvAmt.getAmountAsBigDecimal()).getAmountAsDouble
				// ();
				BigDecimal bd1 = fsvAmt.getAmountAsBigDecimal();
				BigDecimal bd2 = cmvAmt.getAmountAsBigDecimal();
				BigDecimal bd3 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_EVEN);
				returnVal = bd3.doubleValue();
			}
		}

		return setMarginDoubleToStr(returnVal, locale);
	}

	public static String setMarginDoubleToStr(double margin, Locale locale) {
		String returnValue = "";
		if ((margin != ICMSConstant.DOUBLE_INVALID_VALUE) && (margin * 100 != ICMSConstant.DOUBLE_INVALID_VALUE)) {
			returnValue = MapperUtil.mapDoubleToString(margin * 100, 0, locale);
		}

		return returnValue;
	}

	public static double setMarginStrToDouble(String marginStr) {
		double returnValue = ICMSConstant.DOUBLE_INVALID_VALUE;
		if (!AbstractCommonMapper.isEmptyOrNull(marginStr)) {
			returnValue = Double.parseDouble(marginStr) / 100;
		}

		return returnValue;
	}

	/**
	 * Helper method to get a list of distinct BCA IDs that belongs to the
	 * security
	 */
	public static List getSecurityBCAList(ICollateral col) {
		ICollateralLimitMap[] limitMapList = col.getCurrentCollateralLimits();
		List bcaList = new ArrayList();
		List bcaMap = new ArrayList();
		if (limitMapList != null) {
			for (int i = 0; i < limitMapList.length; i++) {
				String bcaID = String.valueOf(limitMapList[i].getSCILimitProfileID());
				String subProfileID = String.valueOf(limitMapList[i].getSCISubProfileID());
				if (!bcaMap.contains(bcaID + "," + subProfileID)) {
					bcaList.add(limitMapList[i]);
					bcaMap.add(bcaID + "," + subProfileID);
				}
			}
		}
		return bcaList;
	}

	public static boolean validateChargeType(String chargeType, ICollateralLimitMap[] limitMaps, String fieldName,
			HashMap exceptionMap) throws CommandValidationException {
		if ((limitMaps == null) || (limitMaps.length == 0) || (chargeType == null) || "".equals(chargeType)) {
			return true;
		}
		try {
			int numOfOutLmt = 0;
			ILimitProxy lmtProxy = LimitProxyFactory.getProxy();
			for (int index = 0; index < limitMaps.length; index++) {
				long limitID;
				ILimit limit = null;

				if ("CB".equals(limitMaps[index].getCustomerCategory())) {

				}
				else {
					limitID = limitMaps[index].getLimitID();
					limit = lmtProxy.getLimit(limitID);
				}

				if (limit != null) {
					if ("OUTER".equals(limit.getLimitType())) {
						numOfOutLmt++;
					}
				}
				if (numOfOutLmt == 2) {
					if (!isGeneralChargeType(chargeType)) {
						exceptionMap.put(fieldName, new ActionMessage("error.collateral.chargetype.sharedsecurity"));
						return false;
					}
					return true;
				}
			}

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandValidationException(e.getMessage());
		}
	}

	public static boolean validateChargeType(ICollateral iColObj, HashMap exceptionMap)
			throws CommandValidationException {
		ILimitCharge[] lmtCharges = iColObj.getLimitCharges();
		if ((lmtCharges == null) || (lmtCharges.length == 0)) {
			return true;
		}
		String fieldName = "chargeType";
		if (canCollateralMaintainMultipleCharge(iColObj)) {
			fieldName = "chargeError";
		}
		for (int index = 0; index < lmtCharges.length; index++) {
			if (validateChargeType(lmtCharges[index].getChargeType(), lmtCharges[index].getLimitMaps(), fieldName,
					exceptionMap)) {
				continue;
			}
			else {
				return false;
			}
		}
		return true;
	}

	public static void validateLegalEnforce(ICollateral iColObj, boolean isCheckListCompleted, HashMap exceptionMap) {
		if (!isCheckListCompleted) {
			if ((iColObj.getIsLE() != null) && iColObj.getIsLE().equals(ICMSConstant.TRUE_VALUE)) {
				exceptionMap.put("le", new ActionMessage("error.collateral.le.yes.disallowed",
						ICMSConstant.STATE_CHECKLIST_COMPLETED));
			}
		}

		if (isCheckListCompleted) {
			if (ICMSConstant.TRUE_VALUE.equals(iColObj.getIsLE()) && (iColObj.getLEDate() == null)) {
			}
		}
	}

	public static int getInsuranceDocumentCount(IInsurancePolicy insurance, ICollateral iCol, long lCollateralId,
			int index) throws CommandProcessingException {
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		String docNo = insurance.getDocumentNo();
		long insPolicyId = insurance.getInsurancePolicyID();
		int documentCount = 0;

		try {
			if ((docNo != null) && (docNo.trim().length() > 0) && (insPolicyId != ICMSConstant.LONG_INVALID_VALUE)) {
				documentCount = collateralProxy.getDocumentNoCount(docNo, false, insPolicyId, lCollateralId);
			}
			else {
				documentCount = collateralProxy.getDocumentNoCount(docNo, true, ICMSConstant.LONG_INVALID_VALUE,
						lCollateralId);
			}
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException("failed to retrieve document number count for collateral [" + iCol
					+ "]", ex);
		}

		IInsurancePolicy insPolicies[] = iCol.getInsurancePolicies();
		for (int i = 0; i < insPolicies.length; i++) {
			IInsurancePolicy tempInsPolicy = insPolicies[i];
			String tempDocNo = tempInsPolicy.getDocumentNo();
			if (i != index) {
				if ((tempDocNo != null) && tempDocNo.trim().equalsIgnoreCase(docNo)) {
					documentCount++;
				}
			}
		}
		return documentCount;
	}
	
	
	public static int getAddDocFacDetDocumentCount(IAddtionalDocumentFacilityDetails addDocFacDet, ICollateral iCol, long lCollateralId,
			int index) throws CommandProcessingException {
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		String docNo = addDocFacDet.getDocumentNo();
		long addDocFacDetsId = addDocFacDet.getAddDocFacDetID();
		int documentCount = 0;

		try {
			if ((docNo != null) && (docNo.trim().length() > 0) && (addDocFacDetsId != ICMSConstant.LONG_INVALID_VALUE)) {
				documentCount = collateralProxy.getDocumentNoCount(docNo, false, addDocFacDetsId, lCollateralId);
			}
			else {
				documentCount = collateralProxy.getDocumentNoCount(docNo, true, ICMSConstant.LONG_INVALID_VALUE,
						lCollateralId);
			}
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException("failed to retrieve document number count addDocFacDets for collateral [" + iCol
					+ "]", ex);
		}

		IAddtionalDocumentFacilityDetails addDocFacDets[] = iCol.getAdditonalDocFacDetails();
		for (int i = 0; i < addDocFacDets.length; i++) {
			IAddtionalDocumentFacilityDetails tempInsPolicy = addDocFacDets[i];
			String tempDocNo = tempInsPolicy.getDocumentNo();
			if (i != index) {
				if ((tempDocNo != null) && tempDocNo.trim().equalsIgnoreCase(docNo)) {
					documentCount++;
				}
			}
		}
		return documentCount;
	}

	/**
	 * <p>
	 * Whether a collateral can maintain multiple charge info.
	 * <p>
	 * Currently, Property, Others all subtype and Asset Based - Vehicles, Plant
	 * &amp; Equipment, Others, Gold, Aircraft, Vessel and General Charge can
	 * maintain multiple charge.
	 * @param collateral an collateral instance to be checked against
	 * @return whether a collateral can maintain multiple charge info
	 * @deprecated Use
	 *             {@link CollateralDetailFactory#canCollateralMaintainMultipleCharge(ICollateral)}
	 *             instead
	 */
	public static boolean canCollateralMaintainMultipleCharge(ICollateral collateral) {
		return CollateralDetailFactory.canCollateralMaintainMultipleCharge(collateral);
	}

	private static boolean isGeneralChargeType(String chargeType) {
		if ("G".equalsIgnoreCase(chargeType)) {
			return true;
		}
		if ("GENERAL".equalsIgnoreCase(ChargeTypeList.getInstance().getChargeTypeItem(chargeType))) {
			return true;
		}
		return false;
	}
}