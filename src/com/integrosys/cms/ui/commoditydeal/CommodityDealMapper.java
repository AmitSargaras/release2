/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealMapper.java,v 1.12 2006/03/16 03:23:02 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/03/16 03:23:02 $ Tag: $Name: $
 */

public class CommodityDealMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommodityDealForm aForm = (CommodityDealForm) cForm;

		return inputs;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommodityDealForm aForm = (CommodityDealForm) cForm;
		ILimitProfile limitProfile = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap calPositionMap = (HashMap) obj;
		ICommodityCollateral dealCollateral = (ICommodityCollateral) calPositionMap.get("dealCollateral");
		IProfile profile = (IProfile) calPositionMap.get("profileService");

		OBCommodityDeal dealObj = new OBCommodityDeal();
		dealObj.setIsEnforceAttained(ICMSConstant.TRUE_VALUE);

		if (dealCollateral != null) {
			aForm.setSecurityID(String.valueOf(dealCollateral.getCollateralID()));
			DefaultLogger.debug(this, "<<<<<<<<< security sub type code: "
					+ dealCollateral.getCollateralSubType().getSubTypeCode());
			aForm.setSecuritySubType(dealCollateral.getCollateralSubType().getSubTypeCode());
		}

		String proposedFaceCcy = null;
		BigDecimal proposedFaceAmt = null;
		Amount faceAmount = null;
		long profileID = ICMSConstant.LONG_INVALID_VALUE;
		if (profile != null) {
			profileID = profile.getProfileID();
			aForm.setProductType(profile.getProductType());
			aForm.setProductSubType(String.valueOf(profile.getProfileID()));
			if (profile.getUnitPrice() != null) {
				dealObj.setActualPrice(profile.getUnitPrice());
				try {
					aForm.setMarketPrice(UIUtil.formatAmount(profile.getUnitPrice(), 6, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
				proposedFaceCcy = profile.getUnitPrice().getCurrencyCode();
				faceAmount = new Amount();
				faceAmount.setCurrencyCode(proposedFaceCcy);
				proposedFaceAmt = profile.getUnitPrice().getAmountAsBigDecimal();
			}
			else {
				proposedFaceAmt = new BigDecimal(0);
				aForm.setMarketPrice("-");
			}
			if ((profile.getPriceDifferential() != null) && !isEmptyOrNull(profile.getDifferentialSign())) {
				String priceDiff = "";
				PriceDifferential priceDiffObj = new PriceDifferential(
						new Amount(profile.getPriceDifferential(), null), profile.getDifferentialSign());
				dealObj.setActualCommonDifferential(priceDiffObj);
				if (CommodityConstant.SIGN_MINUS.equals(profile.getDifferentialSign())) {
					priceDiff = "-";
					proposedFaceAmt = proposedFaceAmt.subtract(profile.getPriceDifferential());
				}
				else if (CommodityConstant.SIGN_PLUS.equals(profile.getDifferentialSign())) {
					priceDiff = "+";
					proposedFaceAmt = proposedFaceAmt.add(profile.getPriceDifferential());
				}
				else {
					// for +/- do nothing
				}
				try {
					priceDiff = priceDiff + " " + UIUtil.formatNumber(profile.getPriceDifferential(), 6, locale);
					aForm.setCommodityDifferential(priceDiff);
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setCommodityDifferential("-");
			}
		}
		else {
			aForm.setMarketPrice("-");
			aForm.setCommodityDifferential("-");
		}
		String tempCcy = (String) inputs.get("proposedFaceValueCcy");
		if (!isEmptyOrNull(tempCcy)) {
			proposedFaceCcy = tempCcy;
		}
		aForm.setProposedFaceValueCcy(proposedFaceCcy);

		String uomValue = (String) inputs.get("uomValue");
		String uomUnit = (String) inputs.get("uomUnit");
		UOMWrapper uomObj = null;
		if (!isEmptyOrNull(uomUnit) && !isEmptyOrNull(uomValue) && (profileID != ICMSConstant.LONG_INVALID_VALUE)) {
			HashMap uomMap = CommodityDealUtil.getUOMMap(profileID);
			uomObj = (UOMWrapper) uomMap.get(uomUnit);
			BigDecimal uomQty = UIUtil.mapStringToBigDecimal(uomValue);
			Quantity qty = new Quantity(uomQty, uomObj);
			dealObj.setContractMarketUOMConversionRate(uomObj.getMarketUOMConversionRate());
			try {
				if (uomObj != null) {
					qty = (Quantity) uomObj.getMarketUOMConversionRate().convert(qty);
				}
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
			if ((uomObj != null) && (proposedFaceAmt != null)) {
				proposedFaceAmt = proposedFaceAmt.multiply(qty.getQuantity());
				if (faceAmount != null) {
					faceAmount.setAmountAsBigDecimal(proposedFaceAmt);
				}
				else {
					faceAmount = new Amount();
					faceAmount.setCurrencyCode(proposedFaceCcy);
					faceAmount.setAmountAsBigDecimal(proposedFaceAmt);
				}
				try {
					aForm.setProposedFaceValueAmt(UIUtil.formatNumber(proposedFaceAmt, 0, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}
		else {
			aForm.setProposedFaceValueAmt("");
		}

		String tempAmt = (String) inputs.get("proposedFaceValueAmt");
		if (!isEmptyOrNull(tempAmt)) {
			proposedFaceAmt = UIUtil.mapStringToBigDecimal(tempAmt);
			try {
				aForm.setProposedFaceValueAmt(UIUtil.formatNumber(proposedFaceAmt, 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setUomValue(uomValue);
		aForm.setUomUnit(uomUnit);

		dealObj.setOrigFaceValue(faceAmount);

		String percentStr = (String) inputs.get("percentageFinancing");
		int percentageFinancing = 0;
		if ((percentStr != null) && (percentStr.length() > 0)) {
			percentageFinancing = Integer.parseInt((String) inputs.get("percentageFinancing"));
			aForm.setPercentageFinancing(String.valueOf(percentageFinancing));
		}
		else {
			aForm.setPercentageFinancing("");
		}
		ILimit limit = (ILimit) calPositionMap.get("limit");
		boolean isInnerLimit = false;
		if (limit != null) {
			isInnerLimit = ((limit.getOuterLimitRef() != null) && (limit.getOuterLimitRef().length() > 0) && !limit
					.getOuterLimitRef().equals("0"));

			aForm.setLimitID((String.valueOf(limit.getLimitID())));
			if ((limit.getActivatedLimitAmount() != null)
					&& (limit.getActivatedLimitAmount().getCurrencyCode() != null)) {
				try {
					if (isInnerLimit) {
						aForm.setActivatedLimit(limit.getActivatedLimitAmount().getCurrencyCode()
								+ " ("
								+ UIUtil.formatNumber(limit.getActivatedLimitAmount().getAmountAsBigDecimal(), 0,
										locale) + ")");
					}
					else {
						aForm.setActivatedLimit(UIUtil.formatAmount(limit.getActivatedLimitAmount(), 0, locale));
					}
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}

		if (proposedFaceAmt != null) {
			BigDecimal proposedDealAmt = CommonUtil.calcAfterPercent(proposedFaceAmt, percentageFinancing);

			DefaultLogger.debug(this, "proposed deal amt: " + proposedDealAmt);
			try {
				aForm.setProposedDealAmt(UIUtil.formatNumber(proposedDealAmt, 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}

			BigDecimal balanceProposedVal = proposedFaceAmt.subtract(proposedDealAmt);
			DefaultLogger.debug(this, "<<<<<<<<<<<<<<< HSHII: balanceProposedVal : " + balanceProposedVal);
			try {
				aForm.setBalanceProposedFaceVal(UIUtil.formatNumber(balanceProposedVal, 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}

			if ((limit != null) && (limit.getActivatedLimitAmount() != null)) {
				ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
				BigDecimal outstandingOpLimit = limit.getActivatedLimitAmount().getAmountAsBigDecimal();
				boolean hasAmountErr = false;
				Amount opLimit = null;
				try {
					opLimit = proxy.getOuterOperationalLimit(limitProfile, limit);
				}
				catch (CommodityDealException e) {
					hasAmountErr = true;
					if ((e.getErrorCode() != null)
							&& e.getErrorCode().equals(AmountConversionException.AMT_CONV_ERR_CODE)) {
						if (isInnerLimit) {
							aForm.setOutstandingOpLimit("(Forex Error)");
						}
						else {
							aForm.setOutstandingOpLimit("Forex Error");
						}
					}
					else {
						throw new MapperException(e.getMessage());
					}
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
				if (!hasAmountErr && (opLimit != null)) {
					outstandingOpLimit = outstandingOpLimit.subtract(opLimit.getAmountAsBigDecimal());
				}

				BigDecimal convertProposedDealAmt = null;
				try {
					convertProposedDealAmt = AmountConversion.getConversionAmount(
							new Amount(proposedDealAmt, new CurrencyCode(proposedFaceCcy)),
							limit.getActivatedLimitAmount().getCurrencyCode()).getAmountAsBigDecimal();
				}
				catch (Exception e) {
					if (isInnerLimit) {
						aForm.setOutstandingOpLimit("(Forex Error)");
					}
					else {
						aForm.setOutstandingOpLimit("Forex Error");
					}
				}
				if (!hasAmountErr && (convertProposedDealAmt != null)) {
					outstandingOpLimit = outstandingOpLimit.subtract(convertProposedDealAmt);
					try {
						if (isInnerLimit) {
							aForm.setOutstandingOpLimit(limit.getActivatedLimitAmount().getCurrencyCode() + " ("
									+ UIUtil.formatNumber(outstandingOpLimit, 0, locale) + ")");
						}
						else {
							aForm.setOutstandingOpLimit(limit.getActivatedLimitAmount().getCurrencyCode() + " "
									+ UIUtil.formatNumber(outstandingOpLimit, 0, locale));
						}
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
			}
		}

		Amount fsv = null;
		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		if (dealCollateral != null) {
			ICollateralParameter colParam = null;

			try {
				colParam = proxy.getCollateralParameter(dealCollateral.getCollateralLocation(), dealCollateral
						.getCollateralSubType().getSubTypeCode());
			}
			catch (Exception e) {
				// ignore the exception when trying to get CRP.
			}

			double hedgingMargin = 0;
			double crp = 0;
			if (colParam != null) {
				crp = colParam.getThresholdPercent();
			}
			fsv = dealObj.getCalculatedFSVAmt(crp, hedgingMargin);
		}

		Amount cmv = dealObj.getCalculatedCMVAmt();

		BigDecimal colPosition = null;
		if (proposedFaceAmt != null) {
			colPosition = proposedFaceAmt;
			if (fsv != null) {
				colPosition = proposedFaceAmt.add(fsv.getAmountAsBigDecimal());
			}
			if (cmv != null) {
				colPosition = cmv.getAmountAsBigDecimal().subtract(colPosition);
			}
			try {
				aForm.setCollateralPosition(UIUtil.formatAmount(proposedFaceCcy, colPosition, 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "proposedFaceValueCcy", "java.lang.String", REQUEST_SCOPE },
				{ "proposedFaceValueAmt", "java.lang.String", REQUEST_SCOPE },
				{ "percentageFinancing", "java.lang.String", REQUEST_SCOPE },
				{ "uomValue", "java.lang.String", REQUEST_SCOPE },
				{ "uomUnit", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}
}
