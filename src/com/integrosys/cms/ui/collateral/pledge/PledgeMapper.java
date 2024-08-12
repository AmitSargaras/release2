package com.integrosys.cms.ui.collateral.pledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.OBCollateralLimitMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.support.FacilityCodeBasedCollateralUpdateMetaInfo;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyImpl;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PledgeMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		PledgeForm form = (PledgeForm) cForm;
		ILimitProfile limitProfile = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ILimit[] limits = null;
		ILimit limit = null;
		ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		ICollateral iCol = trxValue.getStagingCollateral();
		if (limitProfile != null) {
			limits = limitProfile.getLimits();
			for (int i = 0; i < limits.length; i++) {
				if (form.getFacilityNo().equals(String.valueOf(limits[i].getLimitID()))) {
					limit = limits[i];
					break;
				}
			}
		}
		else {
			try {
				iCol = trxValue.getCollateral();
				List limitProfileIdsList = new ArrayList();
				ILimitProxy proxy = new LimitProxyImpl();
				limitProfileIdsList = proxy.getLimitProfileIdsByApprLmts(iCol.getCollateralID());
				boolean isFound = false;
				for (int i = 0; i < limitProfileIdsList.size(); i++) {
					Long limitProfileIdTemp = (Long) limitProfileIdsList.get(i);
					limitProfile = proxy.getLimitProfile(limitProfileIdTemp.longValue());
					limits = limitProfile.getLimits();
					for (int j = 0; j < limits.length; j++) {
						if (form.getFacilityNo().equals(String.valueOf(limits[j].getLimitID()))) {
							limit = limits[j];
							isFound = true;
							break;
						}
					}
					if (isFound) {
						break;
					}
				}
			}
			catch (LimitException le) {
				throw new MapperException("limitProfile id list with collateral id = \"" + iCol.getCollateralID()
						+ "\" can not be found" + le);
			}
		}
		String currencyCode = IGlobalConstant.DEFAULT_CURRENCY;
		if (limit != null) {
			currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
		}
		ICollateralLimitMap[] collateralLimitMaps = iCol.getCollateralLimits();
		ICollateralLimitMap collateralLimitMap = null;
		if (collateralLimitMaps != null) {
			for (int i = 0; i < collateralLimitMaps.length; i++) {
				if (form.getSciSysGenId().equals(String.valueOf(collateralLimitMaps[i].getSCISysGenID()))) {
					collateralLimitMap = collateralLimitMaps[i];
					break;
				}
				else if (ICMSConstant.HOST_STATUS_DELETE.equals(collateralLimitMaps[i].getSCIStatus())
						&& form.getFacilityNo().equals(String.valueOf(collateralLimitMaps[i].getLimitID()))) {
					collateralLimitMap = collateralLimitMaps[i];
					break;
				}
			}
		}

		if (collateralLimitMap == null) {
			collateralLimitMap = new OBCollateralLimitMap();
			// TODO: to be used as key for the map used later for charge info
			collateralLimitMap.setChargeID(RandomUtils.nextLong());
			collateralLimitMap.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
			collateralLimitMap.setSCIStatus(ICMSConstant.HOST_STATUS_INSERT);

			// update to desired source id based on the facility codes,
			// collateral type/subtype
			FacilityCodeBasedCollateralUpdateMetaInfo[] tradingFacilityCollateralUpdateMetaInfos = (FacilityCodeBasedCollateralUpdateMetaInfo[]) BeanHouse
					.get("tradingFacilityCollateralUpdateMetaInfo");
			for (int i = 0; i < tradingFacilityCollateralUpdateMetaInfos.length; i++) {
				FacilityCodeBasedCollateralUpdateMetaInfo metainfo = tradingFacilityCollateralUpdateMetaInfos[i];
				boolean matchedFacCode = ArrayUtils.contains(metainfo.getApplicableFacilityCodes(), form
						.getSibsFacilityCode());
				boolean matchedCollateralTypeOrSubType = ArrayUtils.contains(metainfo.getCollateralTypes(), iCol
						.getCollateralType().getTypeCode())
						|| ArrayUtils.contains(metainfo.getCollateralSubTypes(), iCol.getCollateralSubType()
								.getSubTypeCode());

				if (matchedFacCode && matchedCollateralTypeOrSubType) {
					collateralLimitMap.setSourceID(metainfo.getCollateralSourceId());
					if (!iCol.getSourceId().equals(metainfo.getCollateralSourceId())) {
						iCol.setSourceId(metainfo.getCollateralSourceId());
					}
				}
			}

			collateralLimitMap.setSCILimitID(form.getFacilityId());

			try {
				String chargeID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_LIMIT_MAP, true);
				collateralLimitMap.setSCISysGenID(Long.parseLong(chargeID));
			}
			catch (Exception e) {
				throw new MapperException("failed to get sequence of [" + ICMSConstant.SEQUENCE_COL_LIMIT_MAP
						+ "] using sequence manager [" + SequenceManager.class + "]", e);
			}

			collateralLimitMap.setChangeIndicator(new Character(ICMSConstant.YES.charAt(0)));
		}

		collateralLimitMap.setLimitID(Long.parseLong(form.getFacilityNo()));
		collateralLimitMap.setCmsLimitProfileId(limitProfile.getLimitProfileID());
		collateralLimitMap.setSCILegalEntityID(limitProfile.getLEReference());
		collateralLimitMap.setSCISubProfileID(limitProfile.getCustRef());

		if (StringUtils.isNotBlank(form.getChargeInfoUsageIndicator())) {
			if (ICollateralLimitMap.CHARGE_INFO_AMOUNT_USAGE == form.getChargeInfoUsageIndicator().charAt(0)) {
				collateralLimitMap.setPledgeAmount(new Amount(UIUtil.mapStringToBigDecimal(form.getPledgeAmount()),
						new CurrencyCode(currencyCode)));
				collateralLimitMap.setPledgeAmountPercentage(0);
			}
			else if (ICollateralLimitMap.CHARGE_INFO_PERCENTAGE_USAGE == form.getChargeInfoUsageIndicator().charAt(0)) {
				collateralLimitMap.setPledgeAmountPercentage(Double.parseDouble(form.getPledgeAmountPercentage()));
				collateralLimitMap.setPledgeAmount(null);
			}
		}

		if (StringUtils.isNotBlank(form.getAmountDrawIndicator())) {
			if (ICollateralLimitMap.CHARGE_INFO_AMOUNT_USAGE == form.getAmountDrawIndicator().charAt(0)) {
				collateralLimitMap.setDrawAmount(new Amount(UIUtil.mapStringToBigDecimal(form.getAmountDraw()),
						new CurrencyCode(currencyCode)));
				collateralLimitMap.setDrawAmountPercentage(0);
			}
			else if (ICollateralLimitMap.CHARGE_INFO_PERCENTAGE_USAGE == form.getAmountDrawIndicator().charAt(0)) {
				collateralLimitMap.setDrawAmountPercentage(Double.parseDouble(form.getAmountDrawPercentage()));
				collateralLimitMap.setDrawAmount(null);
			}
		}

		return collateralLimitMap;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String event = (String) map.get("event");

		int decPlaces = 2;
		boolean withCCY = false;

		List returnList = (List) obj;
		ICollateral collateral = (ICollateral) returnList.get(0);
		ICollateralLimitMap collateralLimitMap = (ICollateralLimitMap) returnList.get(1);
		ILimit limit = (ILimit) returnList.get(2);
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (limitProfile == null && collateralLimitMap != null) {
			try {
				ILimitProxy proxy = new LimitProxyImpl();
				limitProfile = proxy.getLimitProfile(collateralLimitMap.getCmsLimitProfileId());
			}
			catch (LimitException le) {
				throw new MapperException("limitProfile with id [" + collateralLimitMap.getCmsLimitProfileId()
						+ "] can not be found", le);
			}
		}
		PledgeForm form = (PledgeForm) cForm;

		if (collateral != null) {
			if (collateral.getCollateralType() != null) {
				form.setCollateralType(collateral.getCollateralType().getTypeName());
				form.setCollateralTypeCode(collateral.getCollateralType().getTypeCode());
			}
			if (collateral.getCollateralSubType() != null) {
				form.setCollateralSubType(collateral.getCollateralSubType().getSubTypeName());
				form.setCollateralSubTypeCode(collateral.getCollateralSubType().getSubTypeCode());
			}
			form.setCollateralId("-");
			if (trxValue.getCollateral() != null && trxValue.getCollateral().getCollateralID() > 0) {
				form.setCollateralId(String.valueOf(trxValue.getCollateral().getCollateralID()));
			}
			form.setSibsCollateralId(collateral.getSCISecurityID());
		}

		if (limit != null) {
			form.setFacilityNo(String.valueOf(limit.getLimitID()));
			form.setFacilityId(limit.getLimitRef());
			form.setFacilityDescription(limit.getFacilityDesc());
			form.setSibsFacilityCode(limit.getFacilityCode());
			form.setSibsFacilitySeq(String.valueOf(limit.getFacilitySequence()));
			form.setCurrency(limit.getApprovedLimitAmount().getCurrencyCode());
			try {
				form.setActivatedLimit(UIUtil.formatAmount(limit.getActivatedLimitAmount(), decPlaces, locale, true));
				form.setApprovedLimit(UIUtil.formatAmount(limit.getApprovedLimitAmount(), decPlaces, locale, true));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception Caught", e);
			}
		}

		try {
			if (limitProfile != null && StringUtils.isNotBlank(limitProfile.getBCAReference())) {
				form.setSibsApplicationNo(limitProfile.getBCAReference());
			}
			else if (limit != null) {
				form.setSibsApplicationNo(limit.getLimitProfileReferenceNumber());
			}

			if (collateralLimitMap != null) {
				form.setSciSysGenId(String.valueOf(collateralLimitMap.getSCISysGenID()));

				String pledgeAmountUsageIndicator = "";
				String drawAmountUsageIndicator = "";
				if (CollateralAction.EVENT_VIEW.equals(event) && ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
					pledgeAmountUsageIndicator = String.valueOf(trxValue.getCollateral()
							.getChargeInfoPledgeAmountUsageIndicator());
					drawAmountUsageIndicator = String.valueOf(trxValue.getCollateral()
							.getChargeInfoDrawAmountUsageIndicator());
				}
				else {
					pledgeAmountUsageIndicator = String.valueOf(trxValue.getStagingCollateral()
							.getChargeInfoPledgeAmountUsageIndicator());
					drawAmountUsageIndicator = String.valueOf(trxValue.getStagingCollateral()
							.getChargeInfoDrawAmountUsageIndicator());
				}

				if (String.valueOf(ICollateral.CHARGE_INFO_AMOUNT_USAGE).equals(pledgeAmountUsageIndicator)) {
					form.setPledgeAmount(UIUtil.formatAmount(collateralLimitMap.getPledgeAmount(), decPlaces, locale,
							withCCY));
				}
				else {
					form.setPledgeAmountPercentage(String.valueOf(collateralLimitMap.getPledgeAmountPercentage()));
				}

				if (String.valueOf(ICollateral.CHARGE_INFO_AMOUNT_USAGE).equals(drawAmountUsageIndicator)) {
					form.setAmountDraw(UIUtil.formatAmount(collateralLimitMap.getDrawAmount(), decPlaces, locale,
							withCCY));
				}
				else {
					form.setAmountDrawPercentage(String.valueOf(collateralLimitMap.getDrawAmountPercentage()));
				}

				form.setChargeInfoUsageIndicator(pledgeAmountUsageIndicator);
				form.setAmountDrawIndicator(drawAmountUsageIndicator);
			}
		}
		catch (RuntimeException e) {
			throw new MapperException("failed to map OB to form object", e);
		}
		catch (Exception e) {
			throw new MapperException("failed to format amount", e);
		}
		return form;
	}
}
