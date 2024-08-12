//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ISecurityCoverage;
import com.integrosys.cms.app.collateral.bus.OBSecurityCoverage;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import static com.integrosys.cms.app.common.util.MapperUtil.bigDecimalToString;
import static com.integrosys.cms.app.common.util.MapperUtil.doubleToString;
import static com.integrosys.cms.app.common.util.MapperUtil.stringToBigDecimal;
import static com.integrosys.cms.app.common.util.MapperUtil.stringToDouble;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetGenChargeStockDetailsMapper extends AbstractCommonMapper	//extends AssetGenChargeMapper
{
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				});
	}

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) map.get(SERVICE_COLLATERAL_OBJ);
		ICollateral stageCollateral = collateralTrx.getStagingCollateral();
		
		AssetGenChargeStockDetailsForm form=(AssetGenChargeStockDetailsForm)cForm;
		IGeneralChargeDetails chargeDetails = new OBGeneralChargeDetails();
		
		chargeDetails.setLocation(form.getStockLocation());
		chargeDetails.setLocationDetail(form.getStockLocationDetail());

		chargeDetails.setCalculatedDP(form.getCalculatedDP());
//		chargeDetails.setFundedShare(form.getFundedShare());
		
		chargeDetails.setDpShare(form.getDpShare());
		chargeDetails.setDpShare(form.getStockdocMonth());
		chargeDetails.setDpShare(form.getStockdocYear());
		chargeDetails.setLastApprovedBy(form.getLastApprovedBy());
		chargeDetails.setLastApprovedOn(form.getLastApprovedOn());
		chargeDetails.setLastUpdatedBy(form.getLastUpdatedBy());
		chargeDetails.setLastUpdatedOn(form.getLastUpdatedOn());
		
		chargeDetails.setRemarkByMaker(form.getRemarkByMaker());
		chargeDetails.setTotalLoanable(form.getTotalLoanable());
		chargeDetails.setMigrationFlag_DP_CR(form.getMigrationFlag_DP_CR());

		
		
		IGeneralChargeStockDetails stockDetails= new OBGeneralChargeStockDetails();
		stockDetails.setApplicableForDp(form.getApplicableForDp());
		stockDetails.setComponent(form.getComponents());
		stockDetails.setMarginType(form.getMarginType());
		stockDetails.setMargin(form.getMargin());
		
		if( form.getCmsRefId() > 0  	) {
			stockDetails.setCmsRefId(form.getCmsRefId());
			}else {
				stockDetails.setCmsRefId(ICMSConstant.LONG_MIN_VALUE);	
		}
		

		
		if(form.getAmount()!=null && !"".equals(form.getAmount())){
				stockDetails.setComponentAmount(UIUtil.removeComma(form.getAmount())); //Phase 3 CR:comma separated
		}
		
		
		if(form.getHasInsurance()!=null && "on".equals(form.getHasInsurance())){
			stockDetails.setHasInsurance("Y");
			stockDetails.setInsuranceCompanyName(form.getInsuranceCompanyName());
			stockDetails.setInsuranceCompanyCategory(form.getInsuranceCompanyCategory());
			if(form.getInsuredAmount()!=null && !"".equals(form.getInsuredAmount())){
					stockDetails.setInsuredAmount(form.getInsuredAmount());
			}
			stockDetails.setEffectiveDateOfInsurance(UIUtil.convertDate(locale, form.getEffectiveDateOfInsurance()));
			stockDetails.setExpiryDate(UIUtil.convertDate(locale, form.getExpiryDate()));
			stockDetails.setInsuranceDescription(form.getInsuranceDescription());

			stockDetails.setInsurancePolicyNo(form.getInsurancePolicyNo());
			stockDetails.setInsuranceCoverNote(form.getInsuranceCoverNote());
			stockDetails.setInsuranceCurrency(form.getInsuranceCurrency());
			stockDetails.setTotalPolicyAmount(form.getTotalPolicyAmount());
			stockDetails.setInsuranceRecivedDate(UIUtil.convertDate(locale, form.getInsuranceRecivedDate()));
			stockDetails.setInsuranceDefaulted(form.getInsuranceDefaulted());
			stockDetails.setInsurancePremium(form.getInsurancePremium());
			
		}else{
			stockDetails.setHasInsurance("N");
		}
	
		if(form.getEvent().equals("create_current_asset")||form.getEvent().equals("edit_current_asset")){
			stockDetails.setStockType("CurrentAsset");
			stockDetails.setStockComponentCat("ValueStock");
		}
		else if(form.getEvent().equals("create_current_liabilities")||form.getEvent().equals("edit_current_liabilities")){
			stockDetails.setStockType("CurrentLiabilities");
			stockDetails.setStockComponentCat("LessCreditors");
		}
		
		else if(form.getEvent().equals("create_value_debtors")||form.getEvent().equals("edit_value_debtors")){
			stockDetails.setStockType("ValueDebtors");
			stockDetails.setStockComponentCat("ValueDebtors");
		}
		else if(form.getEvent().equals("create_less_value_advances")||form.getEvent().equals("edit_less_value_advances")){
			stockDetails.setStockType("LessAdvances");
			stockDetails.setStockComponentCat("LessAdvances");
		}
//		TODO: Calculation will be changed later
		stockDetails.setLonable(UIUtil.removeComma(form.getLonable())); //Phase 3 CR:comma separated
		if(form.getStockLocation()!=null && !"".equals(form.getStockLocation().trim())){
			stockDetails.setLocationId(Long.parseLong(form.getStockLocation()));
		}
		stockDetails.setLocationDetail(form.getStockLocationDetail());
		
		IGeneralChargeStockDetails [] chargeStockDetails= new IGeneralChargeStockDetails[1];
		chargeStockDetails[0]=stockDetails;
		chargeDetails.setGeneralChargeStockDetails(chargeStockDetails);

		chargeDetails.setStatus(form.getDueDateStatus());

		
		chargeDetails.setCoverageAmount(stringToBigDecimal(UIUtil.removeComma(form.getCoverageAmount())));
		chargeDetails.setAdHocCoverageAmount(stringToBigDecimal(UIUtil.removeComma(form.getAdHocCoverageAmount()) ));
		chargeDetails.setCoveragePercentage(stringToDouble(form.getCoveragePercentage()));
		
		DefaultLogger.debug(this, "Inside AssetGenChargeStockDetailsMapper.mapFormToOB()");
		return chargeDetails;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside AssetGenChargeStockDetailsMapper.mapOBToForm");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) map.get(SERVICE_COLLATERAL_OBJ);
		ICollateral stageCollateral = collateralTrx.getStagingCollateral();
		
		IGeneralChargeDetails chargeDetails= (IGeneralChargeDetails) obj;
		
		AssetGenChargeStockDetailsForm form= new AssetGenChargeStockDetailsForm();
		if(chargeDetails.getDueDate()!=null)
		form.setDueDate(DateUtil.formatDate(locale, chargeDetails.getDueDate()));
		form.setDocCode(chargeDetails.getDocCode());
		form.setStockdocMonth(chargeDetails.getStockdocMonth());
		form.setStockdocYear(chargeDetails.getStockdocYear());
		
		form.setStockLocation(chargeDetails.getLocation());
		form.setStockLocationDetail(chargeDetails.getLocationDetail());
		form.setCalculatedDP(chargeDetails.getCalculatedDP());
		
		form.setDpShare(chargeDetails.getDpShare());
		form.setStockdocMonth(chargeDetails.getStockdocMonth());
		form.setStockdocYear(chargeDetails.getStockdocYear());
		form.setLastApprovedBy(chargeDetails.getLastApprovedBy());
		form.setLastApprovedOn(chargeDetails.getLastApprovedOn());
		form.setLastUpdatedBy(chargeDetails.getLastUpdatedBy());
		form.setLastUpdatedOn(chargeDetails.getLastUpdatedOn());
		form.setRemarkByMaker(chargeDetails.getRemarkByMaker());
		form.setDueDateStatus(chargeDetails.getStatus());
		form.setDpCalculateManually(chargeDetails.getDpCalculateManually());

		form.setTotalLoanable(chargeDetails.getTotalLoanable());
		form.setMigrationFlag_DP_CR(chargeDetails.getMigrationFlag_DP_CR());

		
		
		IGeneralChargeStockDetails[] stockDetailsArr = chargeDetails.getGeneralChargeStockDetails();
	if(stockDetailsArr!=null && stockDetailsArr.length>0) {
		IGeneralChargeStockDetails stockDetails = chargeDetails.getGeneralChargeStockDetails()[0];
		form.setComponents(stockDetails.getComponent());
		if(stockDetails.getComponentAmount()!=null){
			form.setAmount(UIUtil.formatWithCommaAndDecimal(stockDetails.getComponentAmount().toString())); //Phase 3 CR:comma separated
		}
		
		if("Y".equals(stockDetails.getHasInsurance())){
			form.setHasInsurance("on");
			form.setInsuranceCompanyName(stockDetails.getInsuranceCompanyName());
			form.setInsuranceCompanyCategory(stockDetails.getInsuranceCompanyCategory());
			
			if(stockDetails.getInsuredAmount()!=null ){
					form.setInsuredAmount(stockDetails.getInsuredAmount().toString());
			}
			form.setEffectiveDateOfInsurance(DateUtil.formatDate(locale, stockDetails.getEffectiveDateOfInsurance()));
			form.setExpiryDate(DateUtil.formatDate(locale, stockDetails.getExpiryDate()));
			form.setInsuranceDescription(stockDetails.getInsuranceDescription());
			
			form.setInsurancePolicyNo(stockDetails.getInsurancePolicyNo());
			form.setInsuranceCoverNote(stockDetails.getInsuranceCoverNote());
			form.setInsuranceCurrency("INR");
			form.setTotalPolicyAmount(stockDetails.getTotalPolicyAmount());
			form.setInsuranceRecivedDate(DateUtil.formatDate(locale, stockDetails.getInsuranceRecivedDate()));
			form.setInsuranceDefaulted(stockDetails.getInsuranceDefaulted());
			form.setInsurancePremium(stockDetails.getInsurancePremium());
		}		
		form.setStockDetailType(stockDetails.getStockType());
		form.setStockComponentCat(stockDetails.getStockComponentCat());
		
//		TODO: Calculation will be changed later
		form.setLonable(UIUtil.formatWithCommaAndDecimal(stockDetails.getLonable())); //Phase 3 CR:comma separated
		form.setMarginType(stockDetails.getMarginType());
		form.setMargin(stockDetails.getMargin());	
		
		if( stockDetails.getCmsRefId() > 0 ) {
		form.setCmsRefId(stockDetails.getCmsRefId());		
		}else {
		form.setCmsRefId(ICMSConstant.LONG_MIN_VALUE);	
		}
	   
	   form.setDueDateStatus(chargeDetails.getStatus());
		
		
		form.setApplicableForDp(stockDetails.getApplicableForDp());
				form.setDpCalculateManually(chargeDetails.getDpCalculateManually());
		
		}

		if("prepare".equals(cForm.getEvent())) {
			form.setCoverageAmount(((AssetGenChargeStockDetailsForm) cForm).getCoverageAmount());
			form.setAdHocCoverageAmount(((AssetGenChargeStockDetailsForm) cForm).getAdHocCoverageAmount());
			form.setCoveragePercentage(((AssetGenChargeStockDetailsForm) cForm).getCoveragePercentage());
		}
		else {
			form.setCoverageAmount(bigDecimalToString(chargeDetails.getCoverageAmount()));
			form.setAdHocCoverageAmount(bigDecimalToString(chargeDetails.getAdHocCoverageAmount()));
			form.setCoveragePercentage(doubleToString(chargeDetails.getCoveragePercentage()));
		}
		return form;
	}
}
