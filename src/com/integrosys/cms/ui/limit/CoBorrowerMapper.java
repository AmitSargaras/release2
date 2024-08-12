/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CoBorrowerMapper.java,v 1.16 2006/09/27 06:09:07 hshii Exp $
 */
package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: hshii $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/09/27 06:09:07 $ Tag: $Name: $
 */
public class CoBorrowerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CoBorrowerMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } }

		);
	}

	/**
	 * afasdfsdaf This method is used to map the Form values into Corresponding
	 * OB Values and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		try {
			LimitsForm aform = (LimitsForm) cForm;

			ICoBorrowerLimitTrxValue coBorrowerTrxValue = (ICoBorrowerLimitTrxValue) inputs
					.get("coBorrowerLimitTrxValue");
			ICoBorrowerLimit coBorrowerLimit = coBorrowerTrxValue.getLimit();

			if (coBorrowerLimit == null) {
				throw new MapperException("The limit ob is null in mapper");
			}

			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			Amount amt = CurrencyManager.convertToAmount(locale, coBorrowerLimit.getApprovedLimitAmount()
					.getCurrencyCode(), aform.getDrawingLimitAmt());
			coBorrowerLimit.setActivatedLimitAmount(amt);

			return coBorrowerLimit;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.toString());
		}
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		ForexHelper fr = new ForexHelper();
		LimitsForm aForm = (LimitsForm) cForm;
		try {
			ICoBorrowerLimit coBorrowerLimit = (ICoBorrowerLimit) obj;

			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			aForm.setLeID(coBorrowerLimit.getMainBorrowerCust().getCMSLegalEntity().getLEReference());
			aForm.setMainBorrowerName(coBorrowerLimit.getMainBorrowerCust().getCMSLegalEntity().getLegalName());
			aForm.setCoLeID(coBorrowerLimit.getCustomer().getCMSLegalEntity().getLEReference());
			aForm.setCoBorrowerName(coBorrowerLimit.getCustomer().getCMSLegalEntity().getLegalName());

			aForm.setLimitID(String.valueOf(coBorrowerLimit.getLimitID()));
			aForm.setOuterLimitID(String.valueOf(coBorrowerLimit.getOuterLimitID()));
			if ((coBorrowerLimit.getOuterLimitRef() != null) && (coBorrowerLimit.getOuterLimitRef().length() > 0)
					&& !coBorrowerLimit.getOuterLimitRef().equals("0")) {
				aForm.setOuterLimitRef(coBorrowerLimit.getOuterLimitRef());
			}

			if ((coBorrowerLimit.getApprovedLimitAmount() != null)
					&& (coBorrowerLimit.getApprovedLimitAmount().getCurrencyCode() != null)) {
				String currencyCode = coBorrowerLimit.getApprovedLimitAmount().getCurrencyCode();
				aForm.setCurrencyCode(currencyCode);
				aForm.setApprovedLimitAmt(UIUtil.formatAmount(coBorrowerLimit.getApprovedLimitAmount(), 2, locale,
						false));
			}

			if (coBorrowerLimit.getActivatedLimitAmount() != null) {
				aForm.setDrawingLimitAmt(UIUtil.formatAmount(coBorrowerLimit.getActivatedLimitAmount(), 2, locale,
						false));
			}
			/*
			 * if (coBorrowerLimit.getOutstandingAmount() != null) {
			 * aForm.setOutstandingBalance(currencyCode + " " +
			 * UIUtil.formatAmount(coBorrowerLimit.getOutstandingAmount(), 2,
			 * locale, false)); }
			 * 
			 * if (coBorrowerLimit.getOperationalLimit() != null) {
			 * aForm.setOperationalLimit(currencyCode + " " +
			 * CurrencyManager.convertToString(locale,
			 * coBorrowerLimit.getOperationalLimit())); }
			 */
			if (coBorrowerLimit.getBookingLocation().getCountryCode() != null) {
				aForm.setBookingLoc(coBorrowerLimit.getBookingLocation().getCountryCode()); // todo
																							// :
																							// show
																							// org
																							// code
																							// too
																							// ?
			}

			if (coBorrowerLimit.getProductDesc() != null) {
				aForm.setProductDesc(CommonDataSingleton.getCodeCategoryLabelByValue(
						CategoryCodeConstant.PRODUCT_DESCRIPTION, coBorrowerLimit.getProductDesc()));
			}
			/*
			 * if (coBorrowerLimit.getFacilityDesc() != null) {
			 * aForm.setFacilityDesc
			 * (CommonDataSingleton.getCodeCategoryLabelByValue(
			 * ICategoryEntryConstant.FACILITY_DESCRIPTION,
			 * coBorrowerLimit.getFacilityDesc())); }
			 */
			String lmtRef = coBorrowerLimit.getLimitRef();
			/*
			 * if (coBorrowerLimit.getSourceId() != null) { lmtRef = lmtRef +
			 * " - " +
			 * CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant
			 * .CATEGORY_SOURCE_SYSTEM, coBorrowerLimit.getSourceId()); }
			 */
			aForm.setLimitRef(lmtRef);
			/*
			 * aForm.setLastUpdatedDate(DateUtil.formatDate(locale,
			 * coBorrowerLimit.getLastUpdatedDate()));
			 */

			aForm.setLimitStatus(coBorrowerLimit.getStatus());
			/*
			 * aForm.setLimitTenorUnit(coBorrowerLimit.getLimitTenorUnit());
			 * 
			 * if (coBorrowerLimit.getLimitTenor() != null) {
			 * aForm.setLimitTenor
			 * (String.valueOf(coBorrowerLimit.getLimitTenor())); }
			 */
			// aForm.setExpiryDate(DateUtil.formatDate(locale,coBorrowerLimit.
			// getLimitExpiryDate()));
			// aForm.setLimitType(coBorrowerLimit.getLimitType());
			aForm.setZerorisedReason(coBorrowerLimit.getZerorisedReasons());
			// aForm.setIsZerorised(UIUtil.convertBooleanToStr(coBorrowerLimit.
			// getIsLimitZerorised()));
			aForm.setZerorisedDate(DateUtil.formatDate(locale, coBorrowerLimit.getZerorisedDate()));

			/*
			 * aForm.setLimitAdviseInd(UIUtil.convertBooleanToStr(coBorrowerLimit
			 * .getLimitAdviseInd()));
			 * aForm.setLimitCommittedInd(UIUtil.convertBooleanToStr
			 * (coBorrowerLimit.getLimitCommittedInd()));
			 * aForm.setSharedLimitInd
			 * (UIUtil.convertBooleanToStr(coBorrowerLimit
			 * .getSharedLimitInd()));
			 */

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.toString());
		}

		return aForm;
	}

}