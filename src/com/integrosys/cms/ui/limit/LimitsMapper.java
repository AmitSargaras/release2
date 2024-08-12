/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsMapper.java,v 1.3 2003/09/02 10:22:39 pooja Exp $
 */
package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 10:22:39 $ Tag: $Name: $
 */
public class LimitsMapper extends AbstractCommonMapper {
	public static final int DISPLAY_SCALE = 2;
	public static final int SCALE = 4;
	public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	
	/**
	 * Default Construtor
	 */
	public LimitsMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE } });
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
		ILimitTrxValue trxValue = (ILimitTrxValue) inputs.get("service.limitTrxValue");
		ILimit limit = trxValue.getStagingLimit();

		LimitsForm aForm = (LimitsForm) cForm;

		if (LimitsAction.EVENT_DELETE_ITEM.equals(aForm.getEvent())) {
			if (aForm.getDeleteItems() != null) {
				String[] id = aForm.getDeleteItems();

				ILimitSysXRef[] oldList = limit.getLimitSysXRefs();
				if (id.length <= oldList.length) {
					int numDelete = 0;
					for (int i = 0; i < id.length; i++) {
						if (Integer.parseInt(id[i]) < oldList.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						ILimitSysXRef[] newList = new OBLimitSysXRef[oldList.length - numDelete];
						int i = 0, j = 0;
						while (i < oldList.length) {
							if ((j < id.length) && (Integer.parseInt(id[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = oldList[i];
							}
							i++;
						}
						limit.setLimitSysXRefs(newList);
					}
				}
			}
		}
		else {
			DefaultLogger.debug(this, "<<<<<<<< required security coverage value.. "
					+ aForm.getRequiredSecurityCoverage());
			if ((aForm.getRequiredSecurityCoverage() != null)
					&& (aForm.getRequiredSecurityCoverage().trim().length() > 0)) {
				DefaultLogger.debug(this, "<<<<<<<< required security coverage is inputted.. "
						+ aForm.getRequiredSecurityCoverage());
				limit.setRequiredSecurityCoverage(aForm.getRequiredSecurityCoverage());  //Shiv 190911
			}
		}

		return limit;
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
			if (obj != null) {
				ILimit limit = null;
				boolean hasError = false;
				if (obj instanceof ILimit) {
					limit = (ILimit) obj;
				}
				else {
					limit = (ILimit) ((Object[]) obj)[0];
					hasError = "hasError".equals((String) ((Object[]) obj)[1]);
				}
				Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
				double balanceOfSecurityValue = 0;

				aForm.setLimitID(String.valueOf(limit.getLimitID()));
				aForm.setOuterLimitID(String.valueOf(limit.getOuterLimitID()));
				if ((limit.getOuterLimitRef() != null) && (limit.getOuterLimitRef().length() > 0)
						&& !limit.getOuterLimitRef().equals("0")) {
					aForm.setOuterLimitRef(limit.getOuterLimitRef());
				}

				String currencyCode = "";
				if ((limit.getApprovedLimitAmount() != null)
						&& (limit.getApprovedLimitAmount().getCurrencyCode() != null)) {
					currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
					aForm.setApprovedLimitAmt(currencyCode + " "
							+ UIUtil.formatAmount(limit.getApprovedLimitAmount(), 2, locale, false));
				}

				/*
				 * if (limit.getActivatedLimitAmount() != null) {
				 * aForm.setActivatedLimitAmt(currencyCode+ " "+
				 * UIUtil.formatAmount(limit.getActivatedLimitAmount(), 2,
				 * locale, false)); }
				 */

				if (limit.getDrawingLimitAmount() != null) {
					aForm.setDrawingLimitAmt(currencyCode + " "
							+ UIUtil.formatAmount(limit.getDrawingLimitAmount(), 2, locale, false));
				}
				
				if (limit.getOutstandingAmount() != null) {
					aForm.setOutstandingBalance(currencyCode + " "
							+ UIUtil.formatAmount(limit.getOutstandingAmount(), 2, locale, false));
				}

				if (limit.getOperationalLimit() != null) {
					aForm.setOperationalLimit(currencyCode + " "
							+ CurrencyManager.convertToString(locale, limit.getOperationalLimit()));
				}

				if (limit.getBookingLocation().getCountryCode() != null) {
					aForm.setBookingLoc(limit.getBookingLocation().getOrganisationCode());
//					aForm.setBookingLoc(limit.getBookingLocation().getCountryCode()); // todo
																						// :
																						// show
																						// org
																						// code
																						// too
																						// ?
				}

				if (limit.getProductDesc() != null) {
					aForm.setProductDesc(CommonDataSingleton.getCodeCategoryLabelByValue(
							CategoryCodeConstant.PRODUCT_DESCRIPTION, limit.getProductDesc()));
				}

				if (limit.getFacilityDesc() != null) {
					aForm.setFacilityDesc(CommonDataSingleton.getCodeCategoryLabelByValue(
							ICategoryEntryConstant.FACILITY_DESCRIPTION, limit.getFacilityDesc()));
				}

				String lmtRef = limit.getLimitRef();
				if (limit.getSourceId() != null) {
					lmtRef = lmtRef
							+ " - "
							+ CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_SOURCE_SYSTEM,
									limit.getSourceId());
				}

				aForm.setLastUpdatedDate(DateUtil.formatDate(locale, limit.getLastUpdatedDate()));
				aForm.setLimitRef(lmtRef);
				aForm.setLimitStatus(limit.getLimitStatus());
				aForm.setLimitTenorUnit(limit.getLimitTenorUnit());
				MILimitUIHelper helper = new MILimitUIHelper();
				aForm.setLmtTenorBasisDesc(helper.getLmtTenorBasisDesc(limit.getLimitTenorUnit()));
				if (limit.getLimitTenor() != null) {
					aForm.setLimitTenor(String.valueOf(limit.getLimitTenor()));
				}
				aForm.setLimitSecuredType(limit.getLimitSecuredType());
				aForm.setExpiryDate(DateUtil.formatDate(locale, limit.getLimitExpiryDate()));

				aForm.setLimitType(limit.getLimitType());

				aForm.setZerorisedReason(limit.getZerorisedReason());
				aForm.setIsZerorised(UIUtil.convertBooleanToStr(limit.getIsLimitZerorised()));
				aForm.setZerorisedDate(DateUtil.formatDate(locale, limit.getZerorisedDate()));

				if (!LimitsAction.EVENT_DELETE_ITEM.equals(aForm.getEvent()) && !hasError) {
						aForm.setRequiredSecurityCoverage(limit.getRequiredSecurityCoverage());   //Shiv 190911
				}
				aForm.setDeleteItems(null);
				
				if (limit.getActualSecCoverageAmt() != null && 
						limit.getActualSecCoverageAmt().getCurrencyCode() != null && 
						limit.getActualSecCoverageAmt().getAmount() >= 0 	
					) {
                	
					aForm.setActualSecCoverageAmt(currencyCode + " "
							+ UIUtil.formatAmount(limit.getActualSecCoverageAmt(), DISPLAY_SCALE, locale, false));
					
					if (limit.getOutstandingAmount() != null && 
						limit.getOutstandingAmount().getCurrencyCode() != null && 
						limit.getOutstandingAmount().getAmount() >= 0 	
					) {
						// sec coverage amt / outstanding value *100 = x%							
						BigDecimal result = 
							limit.getActualSecCoverageAmt().getAmountAsBigDecimal().divide( 
									limit.getOutstandingAmount().getAmountAsBigDecimal(), SCALE, ROUNDING_MODE ).multiply( new BigDecimal( 100 ) );
						
						result = result.setScale( DISPLAY_SCALE );				
	                	aForm.setActualSecCoveragePercent( result.toString() );
					}
                } 
				if (limit.getActualSecCoverageOMVAmt() != null && 
						limit.getActualSecCoverageOMVAmt().getCurrencyCode() != null && 
						limit.getActualSecCoverageOMVAmt().getAmount() >= 0 	
					) {
                	
					aForm.setActualSecCoverageOMVAmt(currencyCode + " "
							+ UIUtil.formatAmount(limit.getActualSecCoverageOMVAmt(), DISPLAY_SCALE, locale, false));
					
					if (limit.getOutstandingAmount() != null && 
						limit.getOutstandingAmount().getCurrencyCode() != null && 
						limit.getOutstandingAmount().getAmount() >= 0 	
					) {
						// sec coverage amt / outstanding value *100 = x%							
						BigDecimal result = 
							limit.getActualSecCoverageOMVAmt().getAmountAsBigDecimal().divide( 
									limit.getOutstandingAmount().getAmountAsBigDecimal(), SCALE, ROUNDING_MODE ).multiply( new BigDecimal( 100 ) );
						result = result.setScale( DISPLAY_SCALE );				
	                	aForm.setActualSecCoverageOMVPercent( result.toString() );
					}
                }
				if (limit.getActualSecCoverageFSVAmt() != null && 
						limit.getActualSecCoverageFSVAmt().getCurrencyCode() != null && 
						limit.getActualSecCoverageFSVAmt().getAmount() >= 0 	
					) {
                	
					aForm.setActualSecCoverageFSVAmt(currencyCode + " "
							+ UIUtil.formatAmount(limit.getActualSecCoverageFSVAmt(), DISPLAY_SCALE, locale, false));
					
					if (limit.getOutstandingAmount() != null && 
						limit.getOutstandingAmount().getCurrencyCode() != null && 
						limit.getOutstandingAmount().getAmount() >= 0 	
					) {
						// sec coverage amt / outstanding value *100 = x%							
						BigDecimal result = 
							limit.getActualSecCoverageFSVAmt().getAmountAsBigDecimal().divide( 
									limit.getOutstandingAmount().getAmountAsBigDecimal(), SCALE, ROUNDING_MODE ).multiply( new BigDecimal( 100 ) );
						result = result.setScale( DISPLAY_SCALE );				
	                	aForm.setActualSecCoverageFSVPercent( result.toString() );
					}
                }
								

				aForm.setLimitAdviseInd(UIUtil.convertBooleanToStr(limit.getLimitAdviseInd()));
				aForm.setLimitCommittedInd(UIUtil.convertBooleanToStr(limit.getLimitCommittedInd()));
				aForm.setSharedLimitInd(UIUtil.convertBooleanToStr(limit.getSharedLimitInd()));

				double currentFSVBalance = 0;
				try {
					if (limit.getCollateralAllocations() != null) {
						if (limit.getCollateralAllocations().length != 0) {
							// aForm.setClean(false);
							ICollateralAllocation colAlloc[] = limit.getCollateralAllocations();
							for (int i = 0; i < limit.getCollateralAllocations().length; i++) {
								if ((colAlloc[i].getCollateral().getFSVBalance() != null)
										&& !ICMSConstant.HOST_STATUS_DELETE.equals(colAlloc[i].getHostStatus())) {
									currentFSVBalance = colAlloc[i].getCollateral().getFSVBalance().getAmount();
								}
								if (currentFSVBalance != 0) { // convert only if
																// it's not = 0
									balanceOfSecurityValue = balanceOfSecurityValue
											+ fr.convertAmount(colAlloc[i].getCollateral().getFSVBalance(), limit
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
			throw new MapperException(e.toString());
		}
		return aForm;
	}

}
