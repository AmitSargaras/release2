/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewLimitsAmountMapper.java,v 1.17 2006/10/27 14:17:18 jzhan Exp $
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
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2006/10/27 14:17:18 $ Tag: $Name: $
 */
public class ViewLimitsAmountMapper extends AbstractCommonMapper {

	/**
	 * Default Construtor
	 */
	public ViewLimitsAmountMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "innerOuterBcaObList", "java.util.HashMap", SERVICE_SCOPE },
				{ "limitTrxProfile", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
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
		HashMap updatedLimitsMap = new HashMap();
		ViewLimitsForm vForm = (ViewLimitsForm) cForm;
		String[] limitIDs = vForm.getLimitIDs();
		String[] activateLimitAmount = vForm.getActivatedLimits();
		String[] requiredSecurityCoverages = vForm.getRequiredSecurityCoverages();
		Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);
		String reason = vForm.getZerorisedReason();
		String zerorisedDate = vForm.getZerorisedDate();
		String isZerorised = vForm.getIsZerorised();
		ILimitTrxValue originalProfile = (ILimitTrxValue) inputs.get("limitTrxProfile");
		ILimitTrxValue[] originalValues = originalProfile.getLimitTrxValues();
		if (originalValues != null) {
			for (int i = 0; i < originalValues.length; i++) {
				String transactionID = originalValues[i].getTransactionID();
				ILimit limit = originalValues[i].getLimit();
				for (int x = 0; x < limitIDs.length; x++) {
					if (limitIDs[x].equals(String.valueOf(limit.getLimitID()))) {
						limit
								.setRequiredSecurityCoverage(requiredSecurityCoverages[i].equals("") ? "0"
										: requiredSecurityCoverages[i]);
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
						if ((activateLimitAmount[i].trim().length() != 0) && !activateLimitAmount[i].equals("-")) {
							limit.setLimitActivatedInd(true);
						}
						limit.setActivatedLimitAmount(actLimitAmt);

						limit.setZerorisedReason(reason);
						if (isZerorised != null) {
							limit.setIsLimitZerorised(true);
							limit.setZerorisedDate(DateUtil.convertDate(zerorisedDate));
						}
						else {
							limit.setIsLimitZerorised(false);
						}
					}
				}
				/*
				 * //map limitActivated and activatedAmount if
				 * (isActivated!=null) { for (int z=0; z<isActivated.length;
				 * z++) { if
				 * (isActivated[z].equals(String.valueOf(limit.getLimitID()))) {
				 * limit.setLimitActivatedInd(true); Amount actLimitAmt = new
				 * Amount();
				 * actLimitAmt.setCurrencyCode(limit.getActivatedLimitAmount
				 * ().getCurrencyCodeAsObject()); actLimitAmt.setAmount(new
				 * BigDecimal(activateLimitAmount[i]));
				 * limit.setActivatedLimitAmount(actLimitAmt); } } } //map
				 * limitZerorised, zerorisedDate and zerorisedReason if
				 * (limitZerorised!=null) { for (int y=0;
				 * y<limitZerorised.length; y++) { if
				 * (limitZerorised[y].equals(String
				 * .valueOf(limit.getLimitID()))) {
				 * limit.setIsLimitZerorised(true);
				 * limit.setZerorisedDate(DateUtil
				 * .convertDate(dateOfZerorisations[i]));
				 * limit.setZerorisedReason(reasons[i]); } } }
				 */
				updatedLimitsMap.put(transactionID, limit);
			}
		}
		return updatedLimitsMap;
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
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		CurrencyCode base = new CurrencyCode(""); // new
													// CurrencyCode(CommonUtil.
													// getBaseCurrency());
		ForexHelper fr = new ForexHelper();
		CountryList cList = CountryList.getInstance();
		List ctryLbls = (List) CountryList.getInstance().getCountryLabels();
		List ctryVals = (List) CountryList.getInstance().getCountryValues();
		ILimitTrxValue limitTrxValue = (ILimitTrxValue) obj;
		String event = (String) map.get("event");
		HashMap outerlimitObHash = (HashMap) map.get("innerOuterBcaObList");
		if (outerlimitObHash == null) {
			outerlimitObHash = limitTrxValue.getBcaList();
		}
		ViewLimitsForm form = (ViewLimitsForm) cForm;
		form.setFromPage(event);
		ILimitTrxValue[] limitTrxValues = limitTrxValue.getLimitTrxValues();
		ArrayList limitIDsList = new ArrayList();
		ArrayList limitRefList = new ArrayList();
		ArrayList limitStatusList = new ArrayList();
		ArrayList productDescList = new ArrayList();
		ArrayList leSubProfileList = new ArrayList();
		ArrayList limitLocationList = new ArrayList();
		ArrayList bcalocationList = new ArrayList();
		ArrayList securityIDList = new ArrayList();
		ArrayList limitSecurityLinkageList = new ArrayList();
		ArrayList securityTypeList = new ArrayList();
		ArrayList securityLocationList = new ArrayList();
		ArrayList securityOrganizationList = new ArrayList();
		ArrayList isSameBCAList = new ArrayList();
		ArrayList isDAPList = new ArrayList();

		int limitCount = limitTrxValues == null ? 0 : limitTrxValues.length;

		if (limitCount != 0) {
			String[] approvedCcy = new String[limitCount];
			String[] activatedCcy = new String[limitCount];
			String[] approvedLimit = new String[limitCount];
			String[] activatedLimit = new String[limitCount];
			String[] outerLimitRefs = new String[limitCount];
			String[] reqSecCoverage = new String[limitCount];

			double totalAppLimitAmt = 0;
			double totalActLimitAmt = 0;
			ILimit stagingLimit1 = null;
			String remarks = limitTrxValues[0].getRemarks();

			if (LimitsAction.RE_PROCESS_LIMITS.equals(event) || LimitsAction.PREPARE_CLOSE_LIMITS.equals(event)
					|| LimitsAction.TO_TRACK.equals(event)) {
				stagingLimit1 = limitTrxValues[0].getStagingLimit();
			}
			else {
				stagingLimit1 = limitTrxValues[0].getLimit();
			}
			form.setRemarks(remarks);
			String reason = stagingLimit1.getZerorisedReason() == null ? "" : stagingLimit1.getZerorisedReason();
			form.setZerorisedReason(reason);
			String zerorisedDate = stagingLimit1.getZerorisedDate() == null ? "" : DateUtil
					.convertToDisplayDate(stagingLimit1.getZerorisedDate());
			form.setZerorisedDate(zerorisedDate);
			boolean isZerorised = stagingLimit1.getIsLimitZerorised();
			if (isZerorised) {
				form.setIsZerorised("Y");
			}
			else {
				form.setIsZerorised("N");
			}

			for (int i = 0; i < limitCount; i++) {
				ILimit limit = limitTrxValues[i].getLimit();
				ILimit stagingLimit = limitTrxValues[i].getStagingLimit();
				Amount actLimitAmt = null;
				String reqSecCov = "0";
				if (LimitsAction.RE_PROCESS_LIMITS.equals(event) || LimitsAction.PREPARE_CLOSE_LIMITS.equals(event)
						|| LimitsAction.TO_TRACK.equals(event)) {
					actLimitAmt = stagingLimit.getActivatedLimitAmount();
					reqSecCov = stagingLimit.getRequiredSecurityCoverage();
				}
				else {
					actLimitAmt = limit.getActivatedLimitAmount();
					reqSecCov = limit.getRequiredSecurityCoverage();
				}

//				if (reqSecCov != null) {
//					reqSecCoverage[i] = MapperUtil.mapDoubleToString(doureqSecCov, 0, locale);	//Shiv
//				}
//				else {
//					reqSecCoverage[i] = "";
//				}
				String limitID = String.valueOf(limit.getLimitID());
				limitIDsList.add(limitID);
				String limitRef = limit.getLimitRef();
				limitRefList.add(limitRef);
				// Changed by Priya - CMSSP-669 - Deleted status is not shown
				// for the deleted Limits in the Process Limit Details Screen.
				String limitStatus = limit.getLimitStatus();
				limitStatusList.add(limitStatus);
				// End of CMSSP-669
				String productKey = limit.getProductDesc() == null ? "" : limit.getProductDesc();
				String productDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
						CategoryCodeConstant.PRODUCT_DESCRIPTION, productKey) == null ? "-" : CommonDataSingleton
						.getCodeCategoryLabelByValue(CategoryCodeConstant.PRODUCT_DESCRIPTION, productKey);
				productDescList.add(productDesc);
				HashMap outerLimitMisc = (HashMap) outerlimitObHash.get(limitID);
				String leID = ((String) outerLimitMisc.get("leId")) == null ? "-" : (String) outerLimitMisc.get("leId");
				String custName = ((String) outerLimitMisc.get("custName")) == null ? "-" : (String) outerLimitMisc
						.get("custName");
				leSubProfileList.add(leID + "<br>" + custName);
				String limitCountryCode = limit.getBookingLocation().getCountryCode();
				String limitLocation = "";
				for (int k = 0; k < ctryLbls.size(); k++) {
					if ((limitCountryCode != null) && limitCountryCode.equals(ctryVals.get(k))) {
						limitLocation = (String) ctryLbls.get(k);
					}
				}
				limitLocationList.add(limitLocation);
				String bcaRef = ((String) outerLimitMisc.get("bcaRef")) == null ? "-" : (String) outerLimitMisc
						.get("bcaRef");
				String bkgLoc = (cList.getCountryName((String) outerLimitMisc.get("bkgLoc"))) == null ? "-" : cList
						.getCountryName((String) outerLimitMisc.get("bkgLoc"));
				bcalocationList.add(bcaRef + "<br>" + bkgLoc);
				boolean isSameBCA = limit.getIsInnerOuterSameBCA();
				if (isSameBCA) {
					isSameBCAList.add("Y");
				}
				else {
					isSameBCAList.add("N");
				}
				outerLimitRefs[i] = limit.getOuterLimitRef();
				if ((outerLimitRefs[i] == null) || "0".equals(outerLimitRefs[i])) {
					outerLimitRefs[i] = "-";
				}

				Amount appLimitAmt = limit.getApprovedLimitAmount();

				try {
					String activatedLimitStr = null, activatedLimitCcyStr = null;
					if (actLimitAmt != null) {
						activatedLimitStr = CurrencyManager.convertToDisplayString(locale, actLimitAmt);
						activatedLimitCcyStr = actLimitAmt.getCurrencyCode();
					}
					String appLimitStr = CurrencyManager.convertToDisplayString(locale, appLimitAmt);

					activatedLimit[i] = activatedLimitStr;
					activatedCcy[i] = activatedLimitCcyStr;
					approvedLimit[i] = appLimitStr;
					approvedCcy[i] = appLimitAmt.getCurrencyCode();
				}
				catch (Exception e) {
					DefaultLogger.error(this, "", e);
					throw new MapperException(e.getMessage());
				}

				if ((limit.getOuterLimitRef() != null) && !limit.getOuterLimitRef().equals("0")
						&& (limitStatus != null) && !limitStatus.equals(ICMSConstant.STATE_DELETED)) {
					try {
						totalAppLimitAmt += fr.convertAmount(appLimitAmt, base);
						totalActLimitAmt += fr.convertAmount(actLimitAmt, base);
					}
					catch (Exception e) {
						DefaultLogger.debug(this, e);
					}
				}
				ICollateralAllocation[] collaterals = limit.getCollateralAllocations();
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
				securityIDList.add(securityID);
				limitSecurityLinkageList.add(linkage);
				securityLocationList.add(location);
				securityOrganizationList.add(organization);
				securityTypeList.add(securityType);
				boolean isDAP = limit.getIsDAPError();
				if (isDAP) {
					isDAPList.add("Y");
				}
				else {
					isDAPList.add("N");
				}
			}
			form.setLimitIDs((String[]) limitIDsList.toArray(new String[0]));
			form.setLimitRefs((String[]) limitRefList.toArray(new String[0]));
			form.setLimitStatusList((String[]) limitStatusList.toArray(new String[0]));
			form.setLeSubProfiles((String[]) leSubProfileList.toArray(new String[0]));
			form.setProductDescs((String[]) productDescList.toArray(new String[0]));
			form.setLimitLocation((String[]) limitLocationList.toArray(new String[0]));
			form.setBcaLocation((String[]) bcalocationList.toArray(new String[0]));
			form.setOuterLimitIDs(outerLimitRefs);
			form.setIsSameBCA((String[]) isSameBCAList.toArray(new String[0]));
			form.setApprovedLimits(approvedLimit);
			form.setApprovedCurrencies(approvedCcy);
			form.setActivatedLimits(activatedLimit);
			form.setActivatedCurrencies(activatedCcy);
			form.setSecurityIDs((String[]) securityIDList.toArray(new String[0]));
			form.setRequiredSecurityCoverages(reqSecCoverage);
			form.setLimitSecurityLinkages((String[]) limitSecurityLinkageList.toArray(new String[0]));
			form.setSecurityTypes((String[]) securityTypeList.toArray(new String[0]));
			form.setSecurityLocations((String[]) securityLocationList.toArray(new String[0]));
			form.setSecurityOrganizations((String[]) securityOrganizationList.toArray(new String[0]));
			form.setIsDAP((String[]) isDAPList.toArray(new String[0]));
		}
		return form;
	}
}
