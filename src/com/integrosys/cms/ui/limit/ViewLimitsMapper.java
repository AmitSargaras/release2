/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewLimitsMapper.java,v 1.37 2006/09/22 13:17:32 jzhan Exp $
 */
package com.integrosys.cms.ui.limit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.ui.common.ConvertFloatToString;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.37 $
 * @since $Date: 2006/09/22 13:17:32 $ Tag: $Name: $
 */
public class ViewLimitsMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public ViewLimitsMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE } });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		String limitID = (String) inputs.get("limitID");
		OBLimit oblimit = new OBLimit();
		oblimit.setLimitID(java.lang.Long.parseLong(limitID));
		return (oblimit);

	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		ViewLimitsForm aForm = (ViewLimitsForm) cForm;
		ForexHelper fr = new ForexHelper();
		try {
			if (obj != null) {
				OBLimit sr = (OBLimit) obj;
				Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
				String str = "-";
				ILimitSysXRef sysXRef[];
				double balanceOfSecurityValue = 0;
				sysXRef = sr.getLimitSysXRefs();
				if ((sysXRef != null) && (sysXRef.length != 0)) {
					str = sysXRef[0].getCustomerSysXRef().getExternalXRef();
					long sysXreference = sysXRef[0].getXRefID();
					if (sysXreference != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
						str = Long.toString(sysXreference);
					}
					OBCustomerSysXRef custXRef;
					custXRef = (OBCustomerSysXRef) sysXRef[0].getCustomerSysXRef();
					aForm.setSysXRefCountry(custXRef.getBookingLocation());
					aForm.setSysXRefOrg(custXRef.getBookingLocation());
					aForm.setExternalSys(custXRef.getExternalXRef());
				}
				else {
					aForm.setExternalSys("-");
				}
				aForm.setLimitID(String.valueOf(sr.getLimitID()));
				aForm.setOuterLimitID(String.valueOf(sr.getOuterLimitID()));
				if ((sr.getOuterLimitRef() != null) && (!sr.getOuterLimitRef().equals(""))) {
					if (sr.getOuterLimitRef().equals("0")) {
						aForm.setOuterLimitRef("-");
					}
					else {
						aForm.setOuterLimitRef(sr.getOuterLimitRef());
					}
				}
				else {
					aForm.setOuterLimitRef("-");
				}
				if (sr.getActivatedLimitAmount() != null) {
					aForm.setActivatedLimitAmt(sr.getActivatedLimitAmount());
				}
				if (sr.getApprovedLimitAmount() != null) {
					aForm.setApprovedLimitAmt(sr.getApprovedLimitAmount());
				}
				if (sr.getOperationalLimit() != null) {
					aForm.setOperationalLimit(sr.getOperationalLimit());
				}
				if (sr.getBookingLocation().getCountryCode() != null) {
					aForm.setBookingLoc(sr.getBookingLocation().getCountryCode()); // todo
																					// :
																					// show
																					// org
																					// code
																					// too
																					// ?
				}
				String prodDesc = "-";
				if (sr.getProductDesc() != null) {
					if (CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.PRODUCT_DESCRIPTION, sr
							.getProductDesc().toString()) != null) {
						prodDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
								CategoryCodeConstant.PRODUCT_DESCRIPTION, sr.getProductDesc().toString());
					}
				}
				if (prodDesc != null) {
					aForm.setProductDesc(prodDesc);
				}
				if (sr.getLimitRef() != null) {
					aForm.setLimitRef(sr.getLimitRef());
				}
				if (sr.getLimitStatus() != null) {
					aForm.setLimitStatus(sr.getLimitStatus());
				}
				if (sr.getLimitTenorUnit() != null) {
					aForm.setLimitTenorUnit(sr.getLimitTenorUnit());
				}
				if (sr.getLimitTenor() != null) {
					aForm.setLimitTenor(sr.getLimitTenor().longValue());
				}
				if (sr.getLimitSecuredType() != null) {
					aForm.setLimitSecuredType(sr.getLimitSecuredType());
				}
				if (sr.getLimitExpiryDate() != null) {
					aForm.setExpiryDate(DateUtil.formatDate(locale, sr.getLimitExpiryDate()));
				}
				// aForm.setExpDt(DateUtil.convertToDisplayDate(sr.
				// getLimitExpiryDate()));
				if (sr.getLimitType() != null) {
					aForm.setLimitType(sr.getLimitType());
				}
				String reason = sr.getZerorisedReason();
				if (reason != null) {
					aForm.setZerorisedReason(reason);
				}
				else {
					aForm.setZerorisedReason("-");
				}
				boolean isZerorised = sr.getIsLimitZerorised();
				if (isZerorised) {
					aForm.setIsZerorised("Yes");
				}
				else {
					aForm.setIsZerorised("No");
				}
				Date zerorisedDate = sr.getZerorisedDate();
				if (zerorisedDate != null) {
					aForm.setZerorisedDate(DateUtil.convertToDisplayDate(zerorisedDate));
				}
				else {
					aForm.setZerorisedDate("-");
				}
				String requiredSecurityCoverage = "";
				String actualSecurityCoverage = "";
				if (sr.getRequiredSecurityCoverage() != null) {
					requiredSecurityCoverage = 	sr.getRequiredSecurityCoverage();   // Shiv 190911
				}
				if (sr.getActualSecurityCoverage() >= 0) {
					// use this instead to make the value tally with the
					// "View Customer Details" page
					BigDecimal bdact = new BigDecimal(sr.getActualSecurityCoverage()).setScale(0,
							BigDecimal.ROUND_HALF_UP);
					actualSecurityCoverage = bdact.toBigInteger().toString();
				}
				aForm.setRequiredSecurityCoverage(requiredSecurityCoverage);
				aForm.setActualSecurityCoverage(actualSecurityCoverage);
				if (str != null) {
					aForm.setSysXRef(str);
				}
				if (sr.getActivatedLimitAmount() != null) {
					aForm.setCurrencyCode(sr.getActivatedLimitAmount().getCurrencyCode());
				}
				if (sr.getApprovedLimitAmount() != null) {
					aForm.setCurrencyCode1(sr.getApprovedLimitAmount().getCurrencyCode());
				}
				try {
					if (sr.getActivatedLimitAmount() != null) {
						aForm.setAmount(CurrencyManager.convertToDisplayString(locale, sr.getActivatedLimitAmount()));
						//aForm.setAmount1(CurrencyManager.convertToDisplayString
						// (locale, sr.getApprovedLimitAmount()));
					}
				}
				catch (Exception e) {
					DefaultLogger.error(this, "error in ViewLimitsMapper is" + e);
				}
				aForm.setLimitAdviseInd(sr.getLimitAdviseInd());
				aForm.setLimitCommittedInd(sr.getLimitCommittedInd());
				aForm.setSharedLimitInd(sr.getSharedLimitInd());
				if (sr.getLimitRef() != null) {
					aForm.setLimitRef(sr.getLimitRef());
				}
				double currentFSVBalance = 0;
				try {
					if (sr.getCollateralAllocations() != null) {
						if (sr.getCollateralAllocations().length != 0) {
							aForm.setClean(false);
							ICollateralAllocation colAlloc[] = sr.getCollateralAllocations();
							for (int i = 0; i < sr.getCollateralAllocations().length; i++) {
								if ((colAlloc[i].getCollateral().getFSVBalance() != null)
										&& !ICMSConstant.HOST_STATUS_DELETE.equals(colAlloc[i].getHostStatus())) {
									currentFSVBalance = colAlloc[i].getCollateral().getFSVBalance().getAmount();
								}
								if (currentFSVBalance != 0) { // convert only if
																// it's not = 0
									balanceOfSecurityValue = balanceOfSecurityValue
											+ fr.convertAmount(colAlloc[i].getCollateral().getFSVBalance(), sr
													.getApprovedLimitAmount().getCurrencyCodeAsObject());
								}
							}
						}
						aForm.setBalanceSecurityValue(Double.toString(balanceOfSecurityValue));
					}
					else {
						aForm.setBalanceSecurityValue("-");
					}
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Forex Exception!", e);
					balanceOfSecurityValue = ICMSConstant.LONG_INVALID_VALUE;
					aForm.setBalanceSecurityValue("Forex Error");
				}
			}
		}
		catch (Exception e) {
			aForm.setSysXRefCountry("-");
			aForm.setSysXRefOrg("-");
			aForm.setExternalSys("-");
			DefaultLogger.error(this, "error is" + e);
		}
		return aForm;
	}
}
