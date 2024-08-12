/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/GeneralInfoMapper.java,v 1.38 2006/11/09 09:44:28 jzhan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.38 $
 * @since $Date: 2006/11/09 09:44:28 $ Tag: $Name: $
 */

public class GeneralInfoMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		GeneralInfoForm aForm = (GeneralInfoForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = null;
		try {
			dealObj = (ICommodityDeal) AccessorUtil.deepClone(trxValue.getStagingCommodityDeal());
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}

		if (aForm.getEvent().endsWith(GeneralInfoAction.EVENT_DELETE_ITEM)) {
			if (aForm.getEvent().startsWith(CommodityDealConstant.CASH_DEPOSIT)) {
				IDealCashDeposit[] cashDeposit = dealObj.getCashDeposit();
				String[] chkDelete = aForm.getDeleteCashDeposit();
				Object[] objArr = deleteArr(cashDeposit, chkDelete);
				if (objArr != null) {
					cashDeposit = new IDealCashDeposit[objArr.length];
					for (int i = 0; i < objArr.length; i++) {
						cashDeposit[i] = (IDealCashDeposit) objArr[i];
					}
				}
				dealObj.setCashDeposit(cashDeposit);
			}
		}

		if (!isEmptyOrNull(aForm.getContractNo())) {
			long contractID = Long.parseLong(aForm.getContractNo());
			dealObj.setContractID(contractID);
			ICommodityCollateral dealCollateral = (ICommodityCollateral) inputs.get("dealCollateral");
			IContract[] contractList = dealCollateral.getContracts();
			if (contractList != null) {
				boolean found = false;
				for (int i = 0; !found && (i < contractList.length); i++) {
					if (contractList[i].getContractID() == contractID) {
						found = true;
						if ((contractList[i].getApprovedCommodityType() != null)
								&& (contractList[i].getApprovedCommodityType().getProfile() != null)) {
							dealObj.setContractProfileID(contractList[i].getApprovedCommodityType().getProfile()
									.getProfileID());
						}
					}
				}
			}
		}
		if (!isEmptyOrNull(aForm.getSecurityID())) {
			dealObj.setCollateralID(Long.parseLong(aForm.getSecurityID()));
		}
		else {
			dealObj.setCollateralID(ICMSConstant.LONG_INVALID_VALUE);
		}
		if (!isEmptyOrNull(aForm.getLimitID())) {
			if (aForm.getLimitID().substring(0, 2).equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER)) {
				dealObj.setLimitID(Long.parseLong(aForm.getLimitID().substring(2, aForm.getLimitID().length())));
				dealObj.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
				dealObj.setCoBorrowerLimitID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else if (aForm.getLimitID().substring(0, 2).equals(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER)) {
				dealObj.setCoBorrowerLimitID(Long.parseLong(aForm.getLimitID()
						.substring(2, aForm.getLimitID().length())));
				dealObj.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER);
				dealObj.setLimitID(ICMSConstant.LONG_INVALID_VALUE);
			}
		}
		else {
			dealObj.setLimitID(ICMSConstant.LONG_INVALID_VALUE);
			dealObj.setCoBorrowerLimitID(ICMSConstant.LONG_INVALID_VALUE);
			dealObj.setCustomerCategory(null);
		}
		dealObj.setDealReferenceNo(aForm.getTpDealRef());
		dealObj.setDealMaturityDate(compareDate(locale, dealObj.getDealMaturityDate(), aForm.getDealMaturityDate()));
		dealObj.setExtendedDealMaturityDate(compareDate(locale, dealObj.getExtendedDealMaturityDate(), aForm
				.getExtendedDealMaturityDate()));
		if (isEmptyOrNull(aForm.getOriginalFaceAmt())) {
			dealObj.setOrigFaceValue(null);
		}
		else {
			try {
				dealObj.setOrigFaceValue(UIUtil.convertToAmount(locale, aForm.getOriginalFaceCcy(), aForm
						.getOriginalFaceAmt()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getPercentageFinancing())) {
			dealObj.setFinancingPct(ICMSConstant.DOUBLE_INVALID_VALUE);
		}
		else {
			try {
				dealObj.setFinancingPct(MapperUtil.mapStringToDouble(aForm.getPercentageFinancing(), locale));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getCashRequirement())) {
			dealObj.setCashReqPct(ICMSConstant.DOUBLE_INVALID_VALUE);
		}
		else {
			try {
				dealObj.setCashReqPct(MapperUtil.mapStringToDouble(aForm.getCashRequirement(), locale));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getMarginCash())) {
			dealObj.setCashMarginPct(ICMSConstant.DOUBLE_INVALID_VALUE);
		}
		else {
			try {
				dealObj.setCashMarginPct(MapperUtil.mapStringToDouble(aForm.getMarginCash(), locale));
			}
			catch (Exception e) {
				e.printStackTrace();
				;
				throw new MapperException(e.getMessage());
			}
		}
		dealObj.setDealDate(compareDate(locale, dealObj.getDealDate(), aForm.getDealDate()));
		dealObj.setShippingMarks(aForm.getShippingMarks());
		dealObj.setLatestShipDate(compareDate(locale, dealObj.getLatestShipDate(), aForm.getLatestShipmentDate()));
		dealObj.setContainerNo(aForm.getContainerNo());
		dealObj.setIsPreSold(aForm.getPreSold());
		dealObj.setIsEnforceAttained(aForm.getEnforcibilityAtt());
		dealObj.setEnforceAttainedDate(compareDate(locale, dealObj.getEnforceAttainedDate(), aForm
				.getEnforcibilityAttDate()));
		dealObj.setRemarks(aForm.getCommDealRemarks());
		DefaultLogger.debug(this, "MapForm2Obj - SubLimitID: " + aForm.getSubLimitID());
		if (!isEmptyOrNull(aForm.getSubLimitID())) {
			dealObj.setSubLimitID(Long.parseLong(aForm.getSubLimitID()));
		}
		else {
			dealObj.setSubLimitID(ICMSConstant.LONG_INVALID_VALUE);
		}
		return dealObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		GeneralInfoForm aForm = (GeneralInfoForm) cForm;
		ICommodityDeal dealObj = null;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ILimitProfile limitProfile = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		HashMap commodityLimitMap = new HashMap();
		Object limit = null;
		// ILimit limit = null;
		if (aForm.getEvent().equals(GeneralInfoAction.EVENT_REFRESH)) {
			String changeLimit = (String) inputs.get("changeLimit");
			dealObj = (ICommodityDeal) obj;
			String limitID = (String) inputs.get("limitID");
			String securityID = (String) inputs.get("securityID");
			if (!isEmptyOrNull(securityID) && !isEmptyOrNull(limitID) && !isEmptyOrNull(changeLimit)
					&& changeLimit.equals(ICMSConstant.TRUE_VALUE)) {
				commodityLimitMap = (HashMap) inputs.get("commodityLimitMap");
				if (commodityLimitMap != null) {
					Collection collateralSet = commodityLimitMap.keySet();
					if (collateralSet != null) {
						boolean found = false;
						Iterator itr = collateralSet.iterator();
						while (!found && itr.hasNext()) {
							ICommodityCollateral colObj = (ICommodityCollateral) itr.next();
							if (String.valueOf(colObj.getCollateralID()).equals(securityID)) {
								found = true;
								Collection limitList = (Collection) commodityLimitMap.get(colObj);
								if (limitList != null) {
									Iterator itr1 = limitList.iterator();
									boolean foundLimit = false;
									while (!foundLimit && itr1.hasNext()) {
										Object tempLimit = itr1.next();
										String strLimitID = CommodityDealUtil.generateStrLmtID(tempLimit);
										// ILimit tempLimit =
										// (ILimit)itr1.next();
										if (strLimitID.equals(limitID)) {
											limit = tempLimit;
											foundLimit = true;
										}
									}
								}

								ICollateralLimitMap[] limitMap = colObj.getCollateralLimits();
								if (limitMap != null) {
									boolean foundLimit = false;
									for (int i = 0; !foundLimit && (i < limitMap.length); i++) {
										if (CollateralHelper.getColLimitMapLimitID(limitMap[i]).equals(limitID)) {
											foundLimit = true;
											if (limitMap[i].getCashReqPct() >= 0) {
												aForm.setCashRequirement(String.valueOf((int) limitMap[i]
														.getCashReqPct()));
												aForm.setColCashReqPct("Y");
											}
											else {
												aForm.setCashRequirement("");
												aForm.setColCashReqPct("N");
											}
										}
									}
								}
							}
						}
					}
				}
				if (limit != null) {
					aForm = setLimitInfoToForm(aForm, limit, limitProfile, locale);
				}

				// add by stupid zhan jia, this is wrong !!!!!!
				if (dealObj.getCashReqPct() >= 0) {
					aForm.setCashRequirement(MapperUtil.mapDoubleToString(dealObj.getCashReqPct(), 0, locale));
				}
				else {
					aForm.setCashRequirement(null);
				}
			}
		}
		else if (!aForm.getEvent().equals(GeneralInfoAction.EVENT_REFRESH)
				&& !aForm.getEvent().endsWith(GeneralInfoAction.EVENT_DELETE_ITEM)) {
			HashMap dealObjMap = (HashMap) obj;
			dealObj = (ICommodityDeal) dealObjMap.get("obj");
			commodityLimitMap = (HashMap) dealObjMap.get("commodityLimitMap");
			if (commodityLimitMap != null) {
				Collection collateralSet = commodityLimitMap.keySet();
				if (collateralSet != null) {
					boolean found = false;
					Iterator itr = collateralSet.iterator();
					while (!found && itr.hasNext()) {
						ICommodityCollateral colObj = (ICommodityCollateral) itr.next();
						if (colObj.getCollateralID() == dealObj.getCollateralID()) {
							found = true;
							Collection limitList = (Collection) commodityLimitMap.get(colObj);
							if (limitList != null) {
								Iterator itr1 = limitList.iterator();
								boolean foundLimit = false;
								while (!foundLimit && itr1.hasNext()) {
									Object tempLimit = itr1.next();
									String strLimitID = CommodityDealUtil.generateStrLmtID(tempLimit);
									// ILimit tempLimit = (ILimit)itr1.next();
									if (strLimitID.equals(CommodityDealUtil.generateLimitIDByDeal(dealObj))) {
										limit = tempLimit;
										foundLimit = true;
									}
								}
							}

							// use for mandatory indicator
							ICollateralLimitMap[] limitMap = colObj.getCollateralLimits();
							if (limitMap != null) {
								boolean foundLimit = false;
								for (int i = 0; !foundLimit && (i < limitMap.length); i++) {
									if (CollateralHelper.getColLimitMapLimitID(limitMap[i]).equals(
											CommodityDealUtil.generateLimitIDByDeal(dealObj))) {
										foundLimit = true;
										if (limitMap[i].getCashReqPct() >= 0) {
											aForm.setColCashReqPct("Y");
										}
										else {
											aForm.setColCashReqPct("N");
										}
									}
								}
							}

							if (!aForm.getEvent().equals(GeneralInfoAction.EVENT_PREPARE_UPDATE)
									&& !aForm.getEvent().equals(GeneralInfoAction.EVENT_PROCESS_UPDATE)
									&& !aForm.getEvent().equals(GeneralInfoAction.EVENT_PREPARE_ADD_DEAL)
									&& !aForm.getEvent().equals(GeneralInfoAction.EVENT_UPDATE_RETURN)) {
								// Cynthia: Added for CR-145
								String secSubType = colObj.getCollateralSubType().getSubTypeName();
								secSubType = (secSubType == null) ? "" : secSubType;
								aForm.setSecurityID(String.valueOf(colObj.getSCISecurityID()) + " - " + secSubType);
								if (limit != null) {
									aForm.setLimitID(CommodityDealUtil.getLimitLabelByLimitType(limit, colObj));
									/*
									 * String prodDesc =CommonDataSingleton.
									 * getCodeCategoryLabelByValue
									 * (CategoryCodeConstant
									 * .PRODUCT_DESCRIPTION,
									 * limit.getProductDesc()); if (prodDesc ==
									 * null) prodDesc = "";
									 * aForm.setLimitID(limit
									 * .getLimitRef()+" - "+prodDesc);
									 */
								}
							}
						}
					}
				}
				if (limit != null) {
					try {
						aForm = setLimitInfoToForm(aForm, limit, limitProfile, locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
			}
			if (aForm.getEvent().equals(GeneralInfoAction.EVENT_PREPARE_UPDATE)
					|| aForm.getEvent().equals(GeneralInfoAction.EVENT_PROCESS_UPDATE)
					|| aForm.getEvent().equals(GeneralInfoAction.EVENT_PREPARE_ADD_DEAL)
					|| aForm.getEvent().equals(GeneralInfoAction.EVENT_UPDATE_RETURN)) {
				aForm.setSecurityID(String.valueOf(dealObj.getCollateralID()));
				aForm.setLimitID(CommodityDealUtil.generateLimitIDByDeal(dealObj));
				if (dealObj.getContractID() > 0) {
					aForm.setContractNo(String.valueOf(dealObj.getContractID()));
				}
			}
			else {
				ICommodityCollateral dealCol = (ICommodityCollateral) dealObjMap.get("dealCollateral");
				if (dealObj.getContractID() > 0) {
					IContract[] contractList = dealCol.getContracts();
					boolean foundContract = false;
					if (contractList != null) {
						for (int i = 0; !foundContract && (i < contractList.length); i++) {
							if (contractList[i].getContractID() == dealObj.getContractID()) {
								foundContract = true;
								aForm.setContractNo(contractList[i].getMainContractNumber());
							}
						}
					}
					if (!foundContract) {
						contractList = dealCol.getDeletedContracts();
						if (contractList != null) {
							for (int i = 0; !foundContract && (i < contractList.length); i++) {
								if (contractList[i].getContractID() == dealObj.getContractID()) {
									foundContract = true;
									aForm.setContractNo(contractList[i].getMainContractNumber());
								}
							}
						}
					}
				}
			}
			aForm.setTpDealRef(dealObj.getDealReferenceNo());
			aForm.setDealMaturityDate(DateUtil.formatDate(locale, dealObj.getDealMaturityDate()));
			aForm.setExtendedDealMaturityDate(DateUtil.formatDate(locale, dealObj.getExtendedDealMaturityDate()));
			aForm.setDealDate(DateUtil.formatDate(locale, dealObj.getDealDate()));
			if ((dealObj.getOrigFaceValue() != null) && (dealObj.getOrigFaceValue().getCurrencyCode() != null)
					&& (dealObj.getOrigFaceValue().getAmount() > 0)) {
				try {
					aForm.setOriginalFaceAmt(UIUtil.formatNumber(dealObj.getOrigFaceValue().getAmountAsBigDecimal(), 2,
							locale));
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new MapperException(e.getMessage());
				}
				aForm.setOriginalFaceCcy(dealObj.getOrigFaceValue().getCurrencyCode());
			}

			if (dealObj.getFinancingPct() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setPercentageFinancing(MapperUtil.mapDoubleToString(dealObj.getFinancingPct(), 0, locale));
			}

			if (dealObj.getCashReqPct() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setCashRequirement(MapperUtil.mapDoubleToString(dealObj.getCashReqPct(), 0, locale));
			}

			if (dealObj.getCashMarginPct() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setMarginCash(MapperUtil.mapDoubleToString(dealObj.getCashMarginPct(), 0, locale));
			}

			aForm.setShippingMarks(dealObj.getShippingMarks());
			aForm.setLatestShipmentDate(DateUtil.formatDate(locale, dealObj.getLatestShipDate()));
			aForm.setContainerNo(dealObj.getContainerNo());
			if ((dealObj.getIsPreSold() == null) || (dealObj.getIsPreSold().length() == 0)) {
				aForm.setPreSold(ICMSConstant.FALSE_VALUE);
			}
			else {
				aForm.setPreSold(dealObj.getIsPreSold());
			}
			if ((dealObj.getIsEnforceAttained() == null) || (dealObj.getIsEnforceAttained().length() == 0)) {
				aForm.setEnforcibilityAtt(ICMSConstant.FALSE_VALUE);
			}
			else {
				aForm.setEnforcibilityAtt(dealObj.getIsEnforceAttained());
			}
			aForm.setEnforcibilityAttDate(DateUtil.formatDate(locale, dealObj.getEnforceAttainedDate()));
			aForm.setCommDealRemarks(dealObj.getRemarks());
		}
		else {
			dealObj = (ICommodityDeal) obj;
		}

		aForm.setTotalCashReceive("No");
		if ((dealObj.getOrigFaceValue() != null) && (dealObj.getOrigFaceValue().getCurrencyCode() != null)) {
			BigDecimal cashReq = null;
			if ((dealObj.getFinancingPct() >= 0) && (dealObj.getOrigFaceValue().getAmount() >= 0)) {
				BigDecimal dealAmt = CommonUtil.calcAfterPercent(dealObj.getOrigFaceValue().getAmountAsBigDecimal(),
						dealObj.getFinancingPct());
				//dealObj.getOrigFaceValue().getAmountAsBigDecimal().multiply(new
				// BigDecimal(dealObj.getFinancingPct()/100));
				try {
					aForm.setDealAmount(UIUtil.formatNumber(dealAmt, 2, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}

				BigDecimal balanceFaceValue = dealObj.getOrigFaceValue().getAmountAsBigDecimal().subtract(dealAmt);
				try {
					aForm.setBalanceOrigFaceVal(UIUtil.formatNumber(balanceFaceValue, 0, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}

				if (dealObj.getCashReqPct() >= 0) {
					cashReq = dealAmt.multiply(new BigDecimal(dealObj.getCashReqPct()).divide(new BigDecimal(100),
							ICMSConstant.PERCENTAGE_SCALE, BigDecimal.ROUND_UNNECESSARY));
					try {
						aForm.setCashReqAmt(UIUtil.formatNumber(cashReq, 0, locale));
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
			}

			BigDecimal actualCashReceived = new BigDecimal(0);
			try {
				Amount amt = dealObj.getTotalReqCashAmt();
				if ((amt != null) && (amt.getCurrencyCode() != null)) {
					actualCashReceived = amt.getAmountAsBigDecimal();
					aForm.setActualCashReceive(UIUtil.formatAmount(amt, 0, locale));
				}
				else {
					if (dealObj.getOrigFaceValue() != null) {
						aForm.setActualCashReceive(dealObj.getOrigFaceValue().getCurrencyCode() + " "
								+ UIUtil.formatNumber(actualCashReceived, 0, locale));
					}
					else {
						aForm.setActualCashReceive("");
					}
				}
			}
			catch (Exception e) {
				aForm.setActualCashReceive("One or more of the currencies do not have a foreign exchange rate");
			}

			if ((cashReq != null) && (actualCashReceived != null) && (actualCashReceived.compareTo(cashReq) >= 0)) {
				aForm.setTotalCashReceive("Yes");
			}
		}

		aForm.setDeleteCashDeposit(null);
		try {
			Amount totalCashDeposit = dealObj.getTotalCashDepositAmt();
			if ((totalCashDeposit != null) && (totalCashDeposit.getCurrencyCode() != null)) {
				aForm.setTotalCashDeposit(UIUtil.formatNumber(totalCashDeposit.getAmountAsBigDecimal(), 0, locale));
			}
		}
		catch (Exception e) {
			aForm.setTotalCashDeposit("One or more of the currencies do not have a foreign exchange rate");
		}
		DefaultLogger.debug(this, "MapObj2Form - SubLimitID: " + dealObj.getSubLimitID());
		if (ICMSConstant.LONG_INVALID_VALUE != dealObj.getSubLimitID()) {
			aForm.setSubLimitID(String.valueOf(dealObj.getSubLimitID()));
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityLimitMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "changeLimit", "java.lang.String", REQUEST_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}

	private static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}

	private Object[] deleteArr(Object[] oldArr, String[] chkDelete) {
		Object[] newList = null;
		if (chkDelete != null) {
			if (chkDelete.length <= oldArr.length) {
				int numDelete = 0;
				for (int i = 0; i < chkDelete.length; i++) {
					if (Integer.parseInt(chkDelete[i]) < oldArr.length) {
						numDelete++;
					}
				}
				if (numDelete != 0) {
					newList = new Object[oldArr.length - numDelete];
					int i = 0, j = 0;
					while (i < oldArr.length) {
						if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
							j++;
						}
						else {
							newList[i - j] = oldArr[i];
						}
						i++;
					}
				}
			}
		}
		else {
			newList = oldArr;
		}
		return newList;
	}

	private GeneralInfoForm setLimitInfoToForm(GeneralInfoForm aForm, Object limit, ILimitProfile limitProfile,
			Locale locale) throws MapperException {
		Amount approvedLimit = null;
		Amount activatedLimit = null;

		if (limit instanceof ILimit) {
			approvedLimit = ((ILimit) limit).getApprovedLimitAmount();
			activatedLimit = ((ILimit) limit).getActivatedLimitAmount();
		}
		else if (limit instanceof ICoBorrowerLimit) {
			approvedLimit = ((ICoBorrowerLimit) limit).getApprovedLimitAmount();
			activatedLimit = ((ICoBorrowerLimit) limit).getActivatedLimitAmount();
		}

		if (approvedLimit != null) {
			aForm.setLimitCcy(approvedLimit.getCurrencyCode());
		}
		else if (limit instanceof ILimit) {
			if (((ILimit) limit).getOperationalLimit() != null) {
				aForm.setLimitCcy(((ILimit) limit).getOperationalLimit().getCurrencyCode());
			}
		}

		// todo.. To be decide on this part
		// Current implementation - Coborrower Limit do not have operational
		// limit
		// coborrower limit will not check for this anything related to
		// operational limit
		boolean hasAmtErr = false;
		if (activatedLimit != null) {
			Amount opLimit = null;
			try {
				if (limit instanceof ILimit) {
					opLimit = CommodityDealProxyFactory.getProxy().getOuterOperationalLimit(limitProfile,
							((ILimit) limit));
				}
			}
			catch (CommodityDealException e) {
				if ((e.getErrorCode() != null) && e.getErrorCode().equals(AmountConversionException.AMT_CONV_ERR_CODE)) {
					aForm.setAvailableOpLimit("Forex Error");
					hasAmtErr = true;
				}
				else {
					throw new MapperException(e.getMessage());
				}
			}
			if (opLimit != null) {
				try {
					if (limit instanceof ILimit) {
						BigDecimal availableOpLimit = ((ILimit) limit).getActivatedLimitAmount()
								.getAmountAsBigDecimal().subtract(opLimit.getAmountAsBigDecimal());
						String strAmount = UIUtil.formatNumber(availableOpLimit, 0, locale);
						aForm.setAvailableOpLimit(opLimit.getCurrencyCode() + " " + strAmount);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new MapperException(e.getMessage());
				}
			}
			else if (!hasAmtErr) {
				try {
					String strAmount = UIUtil.formatNumber(activatedLimit.getAmountAsBigDecimal(), 0, locale);
					aForm.setAvailableOpLimit(activatedLimit.getCurrencyCode() + " " + strAmount);
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}
		return aForm;
	}
}
