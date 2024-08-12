/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewCoBorrowerLimitsAmountMapper.java,v 1.16 2006/10/27 03:04:10 hmbao Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/10/27 03:04:10 $ Tag: $Name: $
 */
public class ViewCoBorrowerLimitsAmountMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } });
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
		ICoBorrowerLimitTrxValue originalTrxValue = (ICoBorrowerLimitTrxValue) inputs.get("coBorrowerLimitTrxValue");
		Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);

		ViewLimitsForm vForm = (ViewLimitsForm) cForm;
		String[] requiredSecurityCoverages = vForm.getCoRequiredSecurityCoverages();
		String[] limitIDs = vForm.getLimitIDs();
		String[] activateLimitAmount = vForm.getActivatedLimits();
		String reason = vForm.getZerorisedReason();
		String zerorisedDate = vForm.getZerorisedDate();
		String isZerorised = vForm.getIsZerorised();
		ICoBorrowerLimitTrxValue[] originalValues = originalTrxValue.getCoBorrowerLimitTrxValues();
		if (originalValues != null) {
			for (int i = 0; i < originalValues.length; i++) {
				ICoBorrowerLimit limit = originalValues[i].getLimit();

				// map security coverages
				for (int x = 0; x < limitIDs.length; x++) {
					if (limitIDs[x].equals(String.valueOf(limit.getLimitID()))) {
						limit
								.setRequiredSecurityCoverages(requiredSecurityCoverages[i].equals("") ? ICMSConstant.FLOAT_INVALID_VALUE
										: Float.parseFloat(requiredSecurityCoverages[i]));
						Amount actLimitAmt = null;
						if ((activateLimitAmount[i] != null) && (activateLimitAmount[i].trim().length() != 0)
								&& !activateLimitAmount[i].equals("-")) {
							try {
								actLimitAmt = CurrencyManager.convertToAmount(locale, limit.getApprovedLimitAmount()
										.getCurrencyCode(), activateLimitAmount[i]);
							}
							catch (Exception e) {
								throw new MapperException(e.getMessage());
							}
						}
						limit.setActivatedLimitAmount(actLimitAmt);
						limit.setZerorisedReasons(reason);
						if (isZerorised != null) {
							limit.setLimitZerorised(true);
							limit.setZerorisedDate(DateUtil.convertDate(zerorisedDate));
						}
						else {
							limit.setLimitZerorised(false);
						}
					}
				}
				originalValues[i].setStagingLimit(limit);
			}
		}
		return originalTrxValue;
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
		// helper class
		CurrencyCode usd = new CurrencyCode(""); // new CurrencyCode(CommonUtil.
													// getBaseCurrency());
		ForexHelper fr = new ForexHelper();
		ICoBorrowerLimitTrxValue limitTrxValue = (ICoBorrowerLimitTrxValue) obj;
		ICoBorrowerLimitTrxValue[] limitTrxValues = limitTrxValue.getCoBorrowerLimitTrxValues();
		String event = (String) map.get("event");
		List ctryLbls = (List) CountryList.getInstance().getCountryLabels();
		List ctryVals = (List) CountryList.getInstance().getCountryValues();
		ViewLimitsForm form = (ViewLimitsForm) cForm;
		form.setFromPage(event);
		ArrayList limitIDsList = new ArrayList();
		ArrayList limitRefList = new ArrayList();
		ArrayList productDescList = new ArrayList();
		ArrayList outerLimitIDsList = new ArrayList();
		ArrayList leIDList = new ArrayList();
		ArrayList leNameList = new ArrayList();
		ArrayList isDAPList = new ArrayList();
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		int limitCount = limitTrxValues == null ? 0 : limitTrxValues.length;
		DefaultLogger.debug(this, "limitCount: ------------------- " + limitCount);
		if (limitCount != 0) {
			String[] approvedCcy = new String[limitCount];
			String[] activatedCcy = new String[limitCount];
			String[] approvedAmt = new String[limitCount];
			String[] activatedAmt = new String[limitCount];

			// start 35
			String[] coSecurityIDs = new String[limitCount];
			String[] coSecurityLocations = new String[limitCount];
			String[] coSecurityOrganization = new String[limitCount];
			String[] coLimitSecurityLinkages = new String[limitCount];
			String[] coSecurityTypes = new String[limitCount];
			String[] coRequiredSecurityCoverages = new String[limitCount];
			// end 35

			ICoBorrowerLimit coBorrowerLimit1 = null;
			if (LimitsAction.RE_PROCESS_CO_BORROWER_LIMITS.equals(event)
					|| LimitsAction.TO_TRACK_CO_BORROWER.equals(event)
					|| LimitsAction.PREPARE_CLOSE_CO_BORROWER_LIMITS.equals(event)) {
				coBorrowerLimit1 = limitTrxValues[0].getStagingLimit();
			}
			else {
				coBorrowerLimit1 = limitTrxValues[0].getLimit();
			}
			String reason = coBorrowerLimit1.getZerorisedReasons() == null ? "" : coBorrowerLimit1
					.getZerorisedReasons();
			String zerorisedDate = coBorrowerLimit1.getZerorisedDate() == null ? "" : DateUtil
					.convertToDisplayDate(coBorrowerLimit1.getZerorisedDate());
			String remarks = limitTrxValues[0].getRemarks();
			form.setZerorisedReason(reason);
			form.setZerorisedDate(zerorisedDate);
			form.setRemarks(remarks);
			boolean isZerorised = coBorrowerLimit1.getLimitZerorised();
			if (isZerorised) {
				form.setIsZerorised("Y");
			}
			else {
				form.setIsZerorised("N");
			}
			double totalAppLimitAmt = 0;
			double totalActLimitAmt = 0;
			for (int i = 0; i < limitCount; i++) {
				ICoBorrowerLimit coBorrowerLimit = limitTrxValues[i].getLimit();
				ICoBorrowerLimit stagingCoBorrowerLimit = limitTrxValues[i].getStagingLimit();
				Amount actLimitAmt = null;
				if (LimitsAction.RE_PROCESS_CO_BORROWER_LIMITS.equals(event)
						|| LimitsAction.TO_TRACK_CO_BORROWER.equals(event)
						|| LimitsAction.PREPARE_CLOSE_CO_BORROWER_LIMITS.equals(event)) {
					actLimitAmt = stagingCoBorrowerLimit.getActivatedLimitAmount();
				}
				else {
					actLimitAmt = coBorrowerLimit.getActivatedLimitAmount();
				}
				String limitID = String.valueOf(coBorrowerLimit.getLimitID());
				limitIDsList.add(limitID);
				String limitRef = coBorrowerLimit.getLimitRef();
				limitRefList.add(limitRef);
				String productKey = coBorrowerLimit.getProductDesc() == null ? "" : coBorrowerLimit.getProductDesc();
				String productDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
						CategoryCodeConstant.PRODUCT_DESCRIPTION, productKey) == null ? "-" : CommonDataSingleton
						.getCodeCategoryLabelByValue(CategoryCodeConstant.PRODUCT_DESCRIPTION, productKey);
				productDescList.add(productDesc);
				String outerLimitRef = coBorrowerLimit.getOuterLimitRef();
				if ((outerLimitRef == null) || "0".equals(outerLimitRef)) {
					outerLimitRef = "-";
				}
				outerLimitIDsList.add(outerLimitRef);
				String limitStatus = coBorrowerLimit.getStatus();
				Amount appLimitAmt = coBorrowerLimit.getApprovedLimitAmount();
				try {
					String activatedLimitStr = null, activatedLimitCcyStr = null;
					if (actLimitAmt != null) {
						activatedLimitStr = CurrencyManager.convertToDisplayString(locale, actLimitAmt);
						activatedLimitCcyStr = actLimitAmt.getCurrencyCode();
					}
					String appLimitStr = CurrencyManager.convertToDisplayString(locale, appLimitAmt);

					activatedAmt[i] = activatedLimitStr;
					activatedCcy[i] = activatedLimitCcyStr;
					approvedAmt[i] = appLimitStr;
					approvedCcy[i] = appLimitAmt.getCurrencyCode();

				}
				catch (Exception e) {
					DefaultLogger.error(this, "", e);
					throw new MapperException(e.getMessage());
				}
				if (outerLimitRef.equals("0") && (limitStatus != null)
						&& !limitStatus.equals(ICMSConstant.STATE_DELETED)) {
					try {
						totalAppLimitAmt += fr.convertAmount(appLimitAmt, usd);
						totalActLimitAmt += fr.convertAmount(actLimitAmt, usd);
					}
					catch (Exception e) {
						DefaultLogger.debug(this, e);
					}
				}
				// start..
				float reqSecCov = ICMSConstant.FLOAT_INVALID_VALUE;
				if (LimitsAction.RE_PROCESS_CO_BORROWER_LIMITS.equals(event)
						|| LimitsAction.TO_TRACK_CO_BORROWER.equals(event)
						|| LimitsAction.PREPARE_CLOSE_CO_BORROWER_LIMITS.equals(event)) {
					actLimitAmt = stagingCoBorrowerLimit.getActivatedLimitAmount();
					reqSecCov = stagingCoBorrowerLimit.getRequiredSecurityCoverages();
				}
				else {
					actLimitAmt = coBorrowerLimit.getActivatedLimitAmount();
					reqSecCov = coBorrowerLimit.getRequiredSecurityCoverages();
				}
				if (reqSecCov != ICMSConstant.FLOAT_INVALID_VALUE) {
					coRequiredSecurityCoverages[i] = MapperUtil.mapDoubleToString(reqSecCov, 0, locale);
				}
				else {
					coRequiredSecurityCoverages[i] = "";
				}
				// end ..

				ICMSCustomer customer = coBorrowerLimit.getCustomer();
				String leID = customer.getCMSLegalEntity().getLEReference();
				String subProfileID = customer.getCustomerReference();
				String leName = customer.getCMSLegalEntity().getLegalName();
				String subProfileName = customer.getCustomerName();
				leIDList.add(leID + "<br>" + subProfileID);
				leNameList.add(leName + "<br>" + subProfileName);
				// start ..
				ICollateralAllocation[] collaterals = coBorrowerLimit.getCollateralAllocations();
				String securityID = "";
				String linkage = "";
				String securityType = "";
				String location = "";
				String organization = "";
				if (collaterals != null) {
					for (int x = 0; x < collaterals.length; x++) {
						securityID += collaterals[x].getCollateral().getSCISecurityID() + "<br>";
						String hostStatus = collaterals[x].getHostStatus();
						if ((hostStatus != null) && hostStatus.equals(ICMSConstant.HOST_STATUS_DELETE)) {
							linkage += "Deleted" + "<br>";
						}
						else {
							linkage += "Active" + "<br>";
						}
						String type = collaterals[x].getCollateral().getCollateralType().getTypeName();
						String subType = collaterals[x].getCollateral().getCollateralSubType().getSubTypeName();
						securityType += type + "/" + subType + "<br>";
						for (int k = 0; k < ctryLbls.size(); k++) {
							if ((collaterals[x].getCollateral().getCollateralLocation() != null)
									&& (collaterals[x].getCollateral().getCollateralLocation()).equals(ctryVals.get(k))) {
								location += (String) ctryLbls.get(k) + "<br>";
							}
						}
						if (collaterals[x].getCollateral().getSecurityOrganization() != null) {
							organization += CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.ORG_CODE,
									collaterals[x].getCollateral().getSecurityOrganization())
									+ "<br>";
						}
					}
				}
				coSecurityIDs[i] = securityID;
				coSecurityLocations[i] = location;
				coSecurityOrganization[i] = organization;
				coLimitSecurityLinkages[i] = linkage;
				coSecurityTypes[i] = securityType;

				// end..
				DefaultLogger.debug(this, "coSecurityIDs[i] " + coSecurityIDs[i]);
				DefaultLogger.debug(this, "coSecurityLocations[i] " + coSecurityLocations[i]);
				DefaultLogger.debug(this, "coLimitSecurityLinkages[i] " + coLimitSecurityLinkages[i]);
				DefaultLogger.debug(this, "coSecurityTypes[i] " + coSecurityTypes[i]);
				DefaultLogger.debug(this, "coRequiredSecurityCoverages[i] " + coRequiredSecurityCoverages[i]);

				boolean isDAP = coBorrowerLimit.getIsDAPError();
				if (isDAP) {
					isDAPList.add("Y");
				}
				else {
					isDAPList.add("N");
				}
			}
			form.setLimitIDs((String[]) limitIDsList.toArray(new String[0]));
			form.setLimitRefs((String[]) limitRefList.toArray(new String[0]));
			form.setProductDescs((String[]) productDescList.toArray(new String[0]));
			form.setOuterLimitIDs((String[]) outerLimitIDsList.toArray(new String[0]));
			form.setApprovedLimits(approvedAmt);
			form.setActivatedLimits(activatedAmt);
			form.setApprovedCurrencies(approvedCcy);
			form.setActivatedCurrencies(activatedCcy);
			form.setLeIDs((String[]) leIDList.toArray(new String[0]));
			form.setLeNames((String[]) leNameList.toArray(new String[0]));
			form.setIsDAP((String[]) isDAPList.toArray(new String[0]));
			form.setCoSecurityIDs(coSecurityIDs);
			form.setCoRequiredSecurityCoverages(coRequiredSecurityCoverages);
			form.setCoLimitSecurityLinkages(coLimitSecurityLinkages);
			form.setCoSecurityLocations(coSecurityLocations);
			form.setCoSecurityOrganizations(coSecurityOrganization);
			form.setCoSecurityTypes(coSecurityTypes);

		}
		return form;
	}
}
