/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/UpdateLimitsMapper.java,v 1.15 2005/07/07 09:57:41 lyng Exp $
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
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: lyng $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/07/07 09:57:41 $ Tag: $Name: $
 */
public class UpdateLimitsMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public UpdateLimitsMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitTrxValue", SERVICE_SCOPE },
				{ ICommonEventConstant.REQUEST_LOCALE, "java.util.Locale", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } }

		);
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
		try {
			DefaultLogger.debug("value of limitId is ", "");
			ViewLimitsForm aform = (ViewLimitsForm) cForm;
			DefaultLogger.debug(this, "initialising form" + aform.getLimitID());
			OBLimitTrxValue trxVal = (OBLimitTrxValue) inputs.get("trxValue");
			DefaultLogger.debug(this, "initialising trxval" + aform.getLimitID());
			ILimit limit;
			limit = trxVal.getLimit();
			if (limit == null) {
				throw new MapperException("The limit ob is null in mapper");
			}
			Amount amt = null;
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			// Locale locale =
			// (Locale)inputs.get(ICommonEventConstant.REQUEST_LOCALE);
			DefaultLogger.debug(this, "actlmtamt" + aform.getLimitID());
			DefaultLogger.debug(this, "AMOUNT" + aform.getAmount());
			DefaultLogger.debug(this, "CURRENCY" + aform.getCurrencyCode());
			DefaultLogger.debug(this, "value of req sec coverage in form is" + aform.getRequiredSecurityCoverage());
			amt = CurrencyManager.convertToAmount(locale, aform.getCurrencyCode(), aform.getAmount());
			limit.setActivatedLimitAmount(amt);
			
				limit.setRequiredSecurityCoverage(aform.getRequiredSecurityCoverage());  // Shiv 190911

			return (limit);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error occured in UpdateLimitsMapper" + e);

			return null;
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
		ViewLimitsForm aForm = (ViewLimitsForm) cForm;
		double balanceOfSecurityValue = 0;
		ForexHelper fr = new ForexHelper();
		try {
			if (obj != null) {
				OBLimit sr = (OBLimit) obj;
				ILimitSysXRef sysXRef[];
				sysXRef = sr.getLimitSysXRefs();
				// long sysXreference = sysXRef[0].getXRefID();
				// String str = Long.toString(sysXreference);
				String str = null;
				OBCustomerSysXRef custXRef = null;
				if ((sysXRef != null) && (sysXRef.length != 0)) {
					str = sysXRef[0].getCustomerSysXRef().getExternalXRef();
					custXRef = (OBCustomerSysXRef) sysXRef[0].getCustomerSysXRef();
				}

				// OBLimitSysXRef sysXRef[] =
				// (OBLimitSysXRef[])sr.getLimitSysXRefs();
				// OBCustomerSysXRef custXRef =(OBCustomerSysXRef)
				// sysXRef[0].getCustomerSysXRef();
				if ((sr.getOuterLimitRef() != null) && (!sr.getOuterLimitRef().equals(""))) {
					aForm.setOuterLimitRef(sr.getOuterLimitRef());
				}
				else {
					aForm.setOuterLimitRef("-");
				}
				aForm.setLimitID(String.valueOf(sr.getLimitID()));
				aForm.setOuterLimitID(String.valueOf(sr.getOuterLimitID()));
				aForm.setActivatedLimitAmt(sr.getActivatedLimitAmount());
				aForm.setApprovedLimitAmt(sr.getApprovedLimitAmount());
				if (sr.getOperationalLimit() != null) {
					aForm.setOperationalLimit(sr.getOperationalLimit());
				}

				aForm.setBookingLoc(sr.getBookingLocation().getCountryCode()); // todo
																				// ,
																				// to
																				// display
																				// org
																				// code
																				// eventually
																				// ?
				String prodDesc = "-";
				if (sr.getProductDesc() != null) {
					if (CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.PRODUCT_DESCRIPTION, sr
							.getProductDesc().toString()) != null) {
						prodDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
								CategoryCodeConstant.PRODUCT_DESCRIPTION, sr.getProductDesc().toString());
					}
				}
				if (sr.getCollateralAllocations() != null) {
					if (sr.getCollateralAllocations().length != 0) {
						ICollateralAllocation colAlloc[] = sr.getCollateralAllocations();
						for (int i = 0; i < sr.getCollateralAllocations().length; i++) {
							if (colAlloc[i].getCollateral().getFSVBalance() != null) {
								balanceOfSecurityValue = balanceOfSecurityValue
										+ fr.convertAmount(colAlloc[i].getCollateral().getFSVBalance(), sr
												.getApprovedLimitAmount().getCurrencyCodeAsObject());
							}
						}
					}
					aForm.setBalanceSecurityValue(Double.toString(balanceOfSecurityValue));
				}
				aForm.setProductDesc(prodDesc);
				aForm.setLimitRef(sr.getLimitRef());
				aForm.setLimitStatus(sr.getLimitStatus());
				if (sr.getLimitTenor() != null) {
					aForm.setLimitTenor(sr.getLimitTenor().longValue());
				}
				aForm.setExpiryDate(DateUtil.convertToDisplayDate(sr.getLimitExpiryDate()));
				aForm.setLimitType(sr.getLimitType());
				if (custXRef != null) {
					aForm.setSysXRefCountry(custXRef.getBookingLocation());
					aForm.setSysXRefOrg(custXRef.getBookingLocation());
					aForm.setExternalSys(custXRef.getExternalXRef());
				}

				
					aForm.setRequiredSecurityCoverage(sr.getRequiredSecurityCoverage() ); // Shiv 190911
					
				aForm.setSysXRef(str);
				aForm.setActualSecurityCoverage(Float.toString(sr.getActualSecurityCoverage()));
				if (sr.getLimitRef() != null) {
					aForm.setLimitRef(sr.getLimitRef());
				}

				DefaultLogger.debug(this, "Before putting vector result");

			}
			else {
				DefaultLogger.debug(this, "obj is null");
			}
		}
		catch (Exception e) {
			aForm.setSysXRefCountry("-");
			aForm.setSysXRefOrg("-");
			aForm.setExternalSys("-");
			DefaultLogger.error(this, "error is" + e);

		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
