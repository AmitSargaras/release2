/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralHelper.java,v 1.1 2006/09/15 08:43:12 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.app.common.constant.ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.COLLATERALS_WITHOUT_INSURANCE_STATUS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.RECEIVED;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ISecurityCoverage;
import com.integrosys.cms.app.collateral.bus.OBSecurityCoverage;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyImpl;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * This class is helper class for Collateral UI
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/09/15 08:43:12 $ Tag: $Name: $
 */

public class CollateralHelper {
	public CollateralHelper() {
	}

	public static String getDisplayColLimitMapLimitID(ICollateralLimitMap colLimitMap) {
		if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(colLimitMap.getCustomerCategory())) {
			return colLimitMap.getSCILimitID();
		}
		if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(colLimitMap.getCustomerCategory())) {
			return colLimitMap.getSCICoBorrowerLimitID();
		}
		return null;
	}

	public static String getColLimitMapLimitID(ICollateralLimitMap colLmtMap) {
		String limitID = null;
		if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(colLmtMap.getCustomerCategory())) {
			limitID = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER + String.valueOf(colLmtMap.getLimitID());
		}
		else if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(colLmtMap.getCustomerCategory())) {
			limitID = ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER + String.valueOf(colLmtMap.getCoBorrowerLimitID());
		}
		return limitID;
	}

	public static ILimit getLimit(ICollateralLimitMap colLmtMap) {
		ILimitProxy proxy = new LimitProxyImpl();
		ILimit limit = null;
		try {
			limit = proxy.getLimit(colLmtMap.getLimitID());
		}
		catch (LimitException e) {
			DefaultLogger.error("CollateralHelper.java", "Exception Caught", e);
		}
		return limit;
	}

	public static String getFacilityDescription(ICollateralLimitMap colLmtMap) {
		ILimit limit = getLimit(colLmtMap);
		return limit.getFacilityDesc();
	}
	
	public static List<String> getMandatoryEntitiesForCinForThirdParty() {
		String entityList = PropertyManager.getValue("third.party.security.holder.parameter.value");
		
		return UIUtil.getListFromDelimitedString(entityList, ",");
	}
	
	public static String findCorrectValuation(IPropertyCollateral col) {
		BigDecimal valAmt1 = col.getTotalPropertyAmount_v1().getAmountAsBigDecimal();
		BigDecimal valAmt2 =  col.getTotalPropertyAmount_v2() != null ? col.
				getTotalPropertyAmount_v2().getAmountAsBigDecimal() : null;
		BigDecimal valAmt3 = col.getTotalPropertyAmount_v3() != null ? col.
				getTotalPropertyAmount_v3().getAmountAsBigDecimal() : null;
				
		Date valAmt1Date = DateUtil.clearTime(col.getValcreationdate_v1());	
		Date valAmt2Date = DateUtil.clearTime(col.getValcreationdate_v2());
		Date valAmt3Date = DateUtil.clearTime(col.getValcreationdate_v3());
		
		String val = null;
		boolean isAmtSame = valAmt1.equals(valAmt2) && valAmt1.equals(valAmt3);
		boolean isDateSame = valAmt1Date.equals(valAmt2Date) && valAmt1Date.equals(valAmt3Date);
		boolean isWaiverOrDeferral = (col.getWaiver() != null &&  col.getWaiver().trim().equals("on")) ||
				(col.getDeferral() != null &&  col.getDeferral().trim().equals("on"));
		
		if(isAmtSame && isDateSame) {
			val = "3";
		}else if(isAmtSame) {
			val = valAmt2Date.compareTo(valAmt1Date) >= 0 ?	
					(valAmt3Date.compareTo(valAmt2Date) > 0 || isWaiverOrDeferral
							? "3" : "2") :
						(valAmt3Date.compareTo(valAmt1Date) > 0 ? "3" : "1");
		}else {
			if(!isDateSame) {
				long noOfYears = 1000l * 60 * 60 * 24 * 365;
				int consideringYrs = 3;
				valAmt2 = (valAmt2Date == null || ((valAmt1Date.getTime() - valAmt2Date.getTime())/ noOfYears)
						>= consideringYrs) ? null : valAmt2;
				valAmt3 = (valAmt3Date == null || ((valAmt1Date.getTime() - valAmt3Date.getTime())/ noOfYears) 
						>= consideringYrs) ? null : valAmt3;
			}
			boolean checkFr3 = isWaiverOrDeferral|| valAmt2 == null || 
					(valAmt2 != null && valAmt3 != null && valAmt2.compareTo(valAmt3) > 0) ;
			if(valAmt2 != null && valAmt3 != null && valAmt2.equals(valAmt3)) {
				val = valAmt3.compareTo(valAmt1) < 0 ? "3" : "1";
			}
			else if(valAmt2 != null && valAmt2.equals(valAmt1)) {
				val = checkFr3 ? "3" : "2";
			}else if(valAmt3 != null && valAmt3.equals(valAmt1)) {
				val = !checkFr3 ? "2" : "3";
			}else {
				val = checkFr3 ? ( (valAmt3 == null || valAmt1.compareTo(valAmt3) < 0) ? "1" : "3" ) 
						: ( (valAmt2 == null || isWaiverOrDeferral || valAmt1.compareTo(valAmt2) < 0) ? "1" : "2" );
			}
		}
		
		return val == null ? "1" : val;
	}
	
	public static String findCorrectValuationNew(IPropertyCollateral col, String maxVersion, boolean isWaiverOrDeferral) {
		
	boolean val1IsAdded=false;
	
	boolean val2IsAdded=false;
	boolean val3IsAdded=false;
	System.out.println("CollateralHelper.java=>String findCorrectValuationNew()=>line 135 =>maxVersion=>"+maxVersion);
	if(null == maxVersion) {
        maxVersion = "0";
        val1IsAdded=true;
    }
	System.out.println("CollateralHelper.java=>String findCorrectValuationNew()=>line 140 =>maxVersion=>"+maxVersion);
	if(maxVersion.equals(col.getVersion1())) {
		val1IsAdded=true;
	}
	if(maxVersion.equals(col.getVersion2())) {
		val2IsAdded=true;
	}
	if(maxVersion.equals(col.getVersion3())) {
		val3IsAdded=true;
	}
	BigDecimal valAmt1 = col.getTotalPropertyAmount_v1() != null ? col.
			getTotalPropertyAmount_v1().getAmountAsBigDecimal() : new BigDecimal(0);
	BigDecimal valAmt2 =  col.getTotalPropertyAmount_v2() != null ? col.
			getTotalPropertyAmount_v2().getAmountAsBigDecimal() :  new BigDecimal(0);
	BigDecimal valAmt3 = col.getTotalPropertyAmount_v3() != null ? col.
			getTotalPropertyAmount_v3().getAmountAsBigDecimal() :  new BigDecimal(0);

			if(valAmt1.compareTo( valAmt2 ) < 0 && valAmt1.compareTo(valAmt3) < 0)  {
				val1IsAdded=true;
			System.out.println("val1IsAdded=true");}
				else if (valAmt2.compareTo(valAmt1) < 0 && valAmt2.compareTo(valAmt3) < 0)  {
					val2IsAdded=true;
			System.out.println("val2IsAdded=true");}
				else  {
					val3IsAdded=true;
			System.out.println("val3IsAdded=true");}
			if(null == col.getTotalPropertyAmount_v1() && null == col.getTotalPropertyAmount_v2() && null == col.getTotalPropertyAmount_v3()) {
				val1IsAdded=false;
				 val2IsAdded=false;
				 val3IsAdded=false;
			}
			else if(null != col.getTotalPropertyAmount_v1() && null == col.getTotalPropertyAmount_v2() && null == col.getTotalPropertyAmount_v3() ) {
				val1IsAdded=true;
				 val2IsAdded=false;
				 val3IsAdded=false;
			}
			else if(null == col.getTotalPropertyAmount_v1() && null != col.getTotalPropertyAmount_v2() && null == col.getTotalPropertyAmount_v3() ) {
				val1IsAdded=false;
				 val2IsAdded=true;
				 val3IsAdded=false;
			}
			else if(null == col.getTotalPropertyAmount_v1() && null == col.getTotalPropertyAmount_v2() && null != col.getTotalPropertyAmount_v3() ) {
				val1IsAdded=false;
				 val2IsAdded=false;
				 val3IsAdded=true;
			}
			else if(null != col.getTotalPropertyAmount_v1() && null != col.getTotalPropertyAmount_v2() && null == col.getTotalPropertyAmount_v3() ) {
				val1IsAdded=true;
				 val2IsAdded=true;
				 val3IsAdded=false;
			}
			else if(null != col.getTotalPropertyAmount_v1() && null == col.getTotalPropertyAmount_v2() && null != col.getTotalPropertyAmount_v3() ) {
				val1IsAdded=true;
				 val2IsAdded=false;
				 val3IsAdded=true;
			}
			else if(null == col.getTotalPropertyAmount_v1() && null != col.getTotalPropertyAmount_v2() && null != col.getTotalPropertyAmount_v3() ) {
				val1IsAdded=false;
				 val2IsAdded=true;
				 val3IsAdded=true;
			}
			System.out.println("CollateralHelper.java=>String findCorrectValuationNew()=>line 168 =>val1IsAdded=>"+val1IsAdded+"**val2IsAdded=>"+val2IsAdded+"**val3IsAdded=>"+val3IsAdded);
			System.out.println("CollateralHelper.java=>String findCorrectValuationNew()=>line 169 =>valAmt1=>"+valAmt1+"**valAmt2=>"+valAmt2+"**valAmt3=>"+valAmt3);

			String val = "";
	if(val1IsAdded && (val2IsAdded && !isWaiverOrDeferral) && val3IsAdded ) {
		if(valAmt1.compareTo(valAmt2)==0 && valAmt1.compareTo(valAmt3)==0) {
			val= "1";
		}else if(valAmt1.compareTo(valAmt2)==1)  {
			if(valAmt2.compareTo(valAmt3)==-1)  {
			val= "2";
			}else if(valAmt2.compareTo(valAmt3)==0){
				val= "2";
			}else if (valAmt2.compareTo(valAmt3)==1)  {
				val= "3";
				}
		}else if(valAmt1.compareTo(valAmt2)==0)  {
			if(valAmt1.compareTo(valAmt3)==1)  {
			val= "3";
			}else if(valAmt1.compareTo(valAmt3)==0){
				val= "1";
			}else if (valAmt1.compareTo(valAmt3)==-1)  {
				val= "1";
				}
		}else if(valAmt1.compareTo(valAmt2)==-1)  {
			if(valAmt1.compareTo(valAmt3)==-1)  {
			val= "1";
			}else if(valAmt1.compareTo(valAmt3)==0){
				val= "1";
			}else if (valAmt1.compareTo(valAmt3)==1)  {
				val= "3";
				}
		}
	}else if(val1IsAdded && (val2IsAdded && !isWaiverOrDeferral)) {
		if(valAmt1.compareTo(valAmt2)==0) {
			val= "1";
		}	
		else if(valAmt1.compareTo(valAmt2)==-1)  {
			val= "1";
		}else if (valAmt2.compareTo(valAmt1)==-1)  {
			val= "2";
			}
	}else if(val1IsAdded && val3IsAdded) {
		if(valAmt1.compareTo(valAmt3)==0) {
			val= "1";
		}	
		else if(valAmt1.compareTo(valAmt3)==-1)  {
			val= "1";
		}else if (valAmt3.compareTo(valAmt1)==-1)  {
			val= "3";
			}
	}else if((val2IsAdded && !isWaiverOrDeferral) && val3IsAdded) {
		if(valAmt2.equals(valAmt3)) {
			val= "2";
		}	
		else if(valAmt2.compareTo(valAmt3)==-1)  {
			val= "2";
		}else if (valAmt3.compareTo(valAmt2)==-1)  {
			val= "3";
			}
	}else if(val1IsAdded) {
		val= "1";
	}else if(val2IsAdded && !isWaiverOrDeferral) {
		val= "2";
	}else if(val3IsAdded) {
		val= "3";
	}
	System.out.println("CollateralHelper.java=>String findCorrectValuationNew()=>line 252 =>val=>"+val);
	return val;
}	
	public static void updateSecurityCoverageDetails(ICollateral iCol, List<IInsuranceGC> insuranceList) {
		
		IInsurancePolicy[] insurancePolicies = iCol.getInsurancePolicies();
		BigDecimal coverageAmount = BigDecimal.ZERO;
		BigDecimal adhocCoverageAmount = BigDecimal.ZERO;
		BigDecimal policyAmount = BigDecimal.ZERO;
		Double coveragePercentage = 0d;
		String subTypeCode = iCol.getCollateralSubType()!=null? iCol.getCollateralSubType().getSubTypeCode(): StringUtils.EMPTY;
		
		List<String> collateralWithoutInsStatusList = COLLATERALS_WITHOUT_INSURANCE_STATUS;
		String collateralWithoutInsStatus = PropertyManager.getValue("col.subtype.without.insurance.policy.status",
				StringUtils.join(collateralWithoutInsStatusList.toArray(new String[0]), ","));
		

		
		boolean isAssetBasedGeneralChargeCollateral = COLTYPE_ASSET_GENERAL_CHARGE.equals(subTypeCode);
		
		if(isAssetBasedGeneralChargeCollateral) {
			if(insuranceList!= null && !CollectionUtils.isEmpty(insuranceList)) {
				
				for(IInsuranceGC policy: insuranceList) {
					String insuredAmtStr = policy.getInsuredAmount();
					String policyAmtStr = policy.getInsurancePolicyAmt();
					String policyCurrency = policy.getInsuranceCurrency();

					if(StringUtils.isNotBlank(policyCurrency)) {

						BigDecimal insuredAmt = null;
						BigDecimal policyAmt = null;
						
						if(StringUtils.isNotBlank(insuredAmtStr) && StringUtils.isNotBlank(policyAmtStr)) {
							insuredAmt = CommonUtil.convertToBaseCcy(policyCurrency, insuredAmtStr);
							policyAmt = CommonUtil.convertToBaseCcy(policyCurrency, policyAmtStr);
						}
						
						
						if(StringUtils.isNotBlank(policy.getInsuranceStatus()) && policy.getInsuranceStatus().endsWith(RECEIVED) &&
								policy.getExpiryDate() != null && policy.getExpiryDate().after(DateUtil.getDate()) &&
								!ICMSConstant.YES.equals(policy.getDeprecated()) ) {
							
							if (insuredAmt != null && insuredAmt.longValue()>0) {
								coverageAmount = coverageAmount.add(insuredAmt);
							}
							if (policyAmt != null && policyAmt.longValue()>0) {
								policyAmount = policyAmount.add(policyAmt);
							}
							
						}
					}
					
				}
				
				IGeneralChargeDetails[] generalChargeDetails = ((IGeneralCharge) iCol).getGeneralChargeDetails();
				
				if(!ArrayUtils.isEmpty(generalChargeDetails)) {
					for(IGeneralChargeDetails generalChargeDetail : generalChargeDetails) {
						coveragePercentage = getSecurityCoveragePercentage(coverageAmount,policyAmount,generalChargeDetail.getAdHocCoverageAmount());
						
						generalChargeDetail.setCoverageAmount(coverageAmount);
						generalChargeDetail.setCoveragePercentage(coveragePercentage);
					}
				}
				
				((IGeneralCharge) iCol).setGeneralChargeDetails(generalChargeDetails);
			}
		}
		else {
			
			if(!ArrayUtils.isEmpty(insurancePolicies)) {
				for(IInsurancePolicy policy : insurancePolicies) {
					Amount insuredAmt = policy.getInsuredAmount();
					Amount policyAmt = policy.getInsurableAmount();
					
					if ((StringUtils.isNotBlank(policy.getInsuranceStatus()) && policy.getInsuranceStatus().endsWith(RECEIVED)) 
							|| collateralWithoutInsStatus.contains(subTypeCode)) {
						if(policy.getExpiryDate() != null && policy.getExpiryDate().after(DateUtil.getDate())) {
							if (insuredAmt != null && insuredAmt.getAmount() > 0) {
								
								BigDecimal insuredAmtLocal = CommonUtil.convertToBaseCcy(policy.getCurrencyCode(), insuredAmt.getAmountAsBigDecimal());
								if(insuredAmtLocal!=null)
									coverageAmount = coverageAmount.add(insuredAmtLocal.setScale(2, RoundingMode.HALF_UP));
							}
							if (policyAmt != null && policyAmt.getAmount() > 0) {
								
								BigDecimal policyAmtLocal = CommonUtil.convertToBaseCcy(policy.getCurrencyCode(), policyAmt.getAmountAsBigDecimal());
								if(policyAmtLocal!=null)
									policyAmount = policyAmount.add(policyAmtLocal.setScale(2, RoundingMode.HALF_UP));
							}
						}
					}
				}
				
			}
			
			List<ISecurityCoverage> securityCoverageObj = iCol.getSecurityCoverage();
			ISecurityCoverage securityCoverage = null;
			if(securityCoverageObj != null && !CollectionUtils.isEmpty(securityCoverageObj)) {
				securityCoverage = securityCoverageObj.get(0);
				adhocCoverageAmount = securityCoverage.getAdHocCoverageAmount();
			}
			
			coveragePercentage = getSecurityCoveragePercentage(coverageAmount,policyAmount,adhocCoverageAmount);
			
			if(securityCoverage !=null) {
				securityCoverage.setCoverageAmount(coverageAmount);
				securityCoverage.setCoveragePercentage(coveragePercentage);
				
				iCol.setSecurityCoverage(Arrays.asList(securityCoverage));
			}
		}
		
	}

	private static Double getSecurityCoveragePercentage(BigDecimal coverageAmount, BigDecimal policyAmount,
			BigDecimal adhocCoverageAmount) {
		
		if(coverageAmount.longValue()>=0 && policyAmount.longValue() >=0) {
			
			BigDecimal sumCoverageAndAdoc = adhocCoverageAmount!= null ? coverageAmount.add(adhocCoverageAmount):coverageAmount;
			if(sumCoverageAndAdoc.longValue()>0 && policyAmount.longValue()>0) {
				BigDecimal temp = policyAmount.divide(sumCoverageAndAdoc, 4, RoundingMode.HALF_UP);
				return temp.multiply(new BigDecimal(100))
						.setScale(2, RoundingMode.HALF_UP)
						.doubleValue();
			}
		}
		
		return null;
	}
	
	public static String getTotalInsurancePolicyAmount(ICollateral iCol, List<OBInsuranceGC> insuranceList) {
		
		IInsurancePolicy[] insurancePolicies = iCol !=null? iCol.getInsurancePolicies():null;
		String subTypeCode = iCol != null && iCol.getCollateralSubType()!=null? iCol.getCollateralSubType().getSubTypeCode(): StringUtils.EMPTY;
		
		List<String> collateralWithoutInsStatusList = COLLATERALS_WITHOUT_INSURANCE_STATUS;
		String collateralWithoutInsStatus = PropertyManager.getValue("col.subtype.without.insurance.policy.status",
				StringUtils.join(collateralWithoutInsStatusList.toArray(new String[0]), ","));
		
		BigDecimal totalPolicyAmount = BigDecimal.ZERO;
		
		boolean isAssetBasedGeneralChargeCollateral = COLTYPE_ASSET_GENERAL_CHARGE.equals(subTypeCode);
		
		String totalPolicyAmountStr = StringUtils.EMPTY;
		
		if (isAssetBasedGeneralChargeCollateral) {
			if (!CollectionUtils.isEmpty(insuranceList)) {

				for (IInsuranceGC policy : insuranceList) {

					String policyAmtStr = policy.getInsurancePolicyAmt();
					String policyCurrency = policy.getInsuranceCurrency();

					if (StringUtils.isNotBlank(policyCurrency)) {
						BigDecimal policyAmt = null;
						if (StringUtils.isNotBlank(policyAmtStr)) {
							policyAmt = CommonUtil.convertToBaseCcy(policyCurrency, policyAmtStr);
						}

						if (StringUtils.isNotBlank(policy.getInsuranceStatus())
								&& policy.getInsuranceStatus().endsWith(RECEIVED) && policy.getExpiryDate() != null
								&& policy.getExpiryDate().after(DateUtil.getDate()) && policyAmt != null
								&& !ICMSConstant.YES.equals(policy.getDeprecated())) {

							if (policyAmt.longValue() > 0) {
								totalPolicyAmount = totalPolicyAmount.add(policyAmt);
							}
						}
					}
				}
			}
		}
		else if(!ArrayUtils.isEmpty(insurancePolicies)) {
			for(IInsurancePolicy insurance : insurancePolicies) {
				
				if((StringUtils.isNotBlank(insurance.getInsuranceStatus()) && insurance.getInsuranceStatus().endsWith(RECEIVED)) 
							|| collateralWithoutInsStatus.contains(subTypeCode)) {
					
					Amount policyAmt = insurance.getInsurableAmount();
					
					if(insurance.getExpiryDate() != null && insurance.getExpiryDate().after(DateUtil.getDate())) {
						if (policyAmt != null && policyAmt.getAmount() > 0) {

							BigDecimal policyAmtLocal = CommonUtil.convertToBaseCcy(insurance.getCurrencyCode(), policyAmt.getAmountAsBigDecimal());
							
							if(policyAmtLocal != null)
								totalPolicyAmount = totalPolicyAmount.add(policyAmtLocal.setScale(2, RoundingMode.HALF_UP));
						}
					}
				}
				
			}
		}
		
		totalPolicyAmountStr = MapperUtil.bigDecimalToString(totalPolicyAmount);
		
		return totalPolicyAmountStr;
	}
	
	public static ISecurityCoverage getNewAssetBasedGCSecurityCoverageDetails(List<OBInsuranceGC> insuranceList) {


		BigDecimal coverageAmount = BigDecimal.ZERO;
		BigDecimal policyAmount = BigDecimal.ZERO;
		Double coveragePercentage = 0d;
		

		if(insuranceList!= null && !CollectionUtils.isEmpty(insuranceList)) {
			
			for(IInsuranceGC policy: insuranceList) {
				String insuredAmtStr = policy.getInsuredAmount();
				String policyAmtStr = policy.getInsurancePolicyAmt();
				String policyCurrency = policy.getInsuranceCurrency();

				if(StringUtils.isNotBlank(policyCurrency)) {
					BigDecimal insuredAmt = null;
					BigDecimal policyAmt = null;
					
					if(StringUtils.isNotBlank(insuredAmtStr) && StringUtils.isNotBlank(policyAmtStr)) {
						insuredAmt = CommonUtil.convertToBaseCcy(policyCurrency, insuredAmtStr);
						policyAmt = CommonUtil.convertToBaseCcy(policyCurrency, policyAmtStr);
					}
					
					if(StringUtils.isNotBlank(policy.getInsuranceStatus()) && policy.getInsuranceStatus().endsWith(RECEIVED) &&
							policy.getExpiryDate() != null && policy.getExpiryDate().after(DateUtil.getDate()) &&
							!ICMSConstant.YES.equals(policy.getDeprecated()) ) {
						
						if (insuredAmt != null && insuredAmt.longValue()>0) {
							coverageAmount = coverageAmount.add(insuredAmt);
						}
						if (policyAmt != null && policyAmt.longValue()>0) {
							policyAmount = policyAmount.add(policyAmt);
						}
						
					}
				}
			}
			
			coveragePercentage = getSecurityCoveragePercentage(coverageAmount,policyAmount, BigDecimal.ZERO);
			
			ISecurityCoverage secCov = new OBSecurityCoverage();
			secCov.setCoveragePercentage(coveragePercentage);
			secCov.setCoverageAmount(coverageAmount);
			
			return secCov;
		}
		else 
			return null;
		
	}	
}