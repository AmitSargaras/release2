package com.integrosys.cms.app.ws.security;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.OBKeymanInsurance;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageDAO;

public class InsuranceSecurityResponse extends AbstractSecurityResponse {
	
//	public static final String INSURED_COMPONENT_LBL = "Insured Component";
	public static final String INSURANCE_POLICY_NO_LBL = "InsurancePolicy No.";
	public static final String INSURER_COMPANY_NAME_LBL = "Insurer's Company Name";
//	public static final String INSURANCE_COVERAGE_TYPE_LBL = "Insurance Coverage Type";
//	public static final String POLICY_AMOUNT_LBL = "Policy Amount";
	public static final String INSURED_AMOUNT_LBL = "Insured Amount";
	public static final String EFFECTIVE_DATE_OF_INSURANCE_LBL = "Effective Date Of Insurance";
	public static final String INSURANCE_EXPIRY_DATE_LBL = "Insurance Expiry Date";
	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
//		String insuredComponent="-";
		String insurancePolicyNo="-";
		String insurersCompanyName = "-";  
//		String insuranceCoverageType = "-";
//		String policyAmount = "-";
		String insuredAmount = "-";
		String effectivedateofInsurance = "-";
		String insuranceExpiryDate = "-";
		
		StringBuilder responseMsgBldr = new StringBuilder();
		OBKeymanInsurance obKeymanInsurance = (OBKeymanInsurance)iCollateralInstance;
		
		if(obKeymanInsurance.getPolicyNo()!=null && !obKeymanInsurance.getPolicyNo().isEmpty()){
			insurancePolicyNo = obKeymanInsurance.getPolicyNo();
		}
		if(obKeymanInsurance.getInsurerName()!=null && !obKeymanInsurance.getInsurerName().isEmpty()){
			IInsuranceCoverageDAO insuranceCoverageDAOInstance = (IInsuranceCoverageDAO)BeanHouse.get("insuranceCoverageDAO");
			insurersCompanyName = insuranceCoverageDAOInstance.getInsuranceCompanyName(obKeymanInsurance.getInsurerName());
		}
		/*if(obKeymanInsurance.getInsuranceType()!=null && !obKeymanInsurance.getInsuranceType().isEmpty()){
			insuranceCoverageType = obKeymanInsurance.getInsuranceType();
		}*/
		if(obKeymanInsurance.getInsuredAmount()!=null){
			insuredAmount = obKeymanInsurance.getInsuredAmount().getAmountAsBigDecimal().toString();
		}
		if(obKeymanInsurance.getInsEffectiveDate()!=null){
			effectivedateofInsurance = sdf.format(obKeymanInsurance.getInsEffectiveDate());
		}
		if(obKeymanInsurance.getInsExpiryDate()!=null){
			insuranceExpiryDate = sdf.format(obKeymanInsurance.getInsExpiryDate());
		}
		
		/*if(iCollateralInstance.getCollateralType()!=null){
			securityType = iCollateralInstance.getCollateralType().getTypeName();
		}
		*/
		//Response Message
		responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
//		responseMsgBldr.append(setResponseMessageValues(INSURED_COMPONENT_LBL, insuredComponent));
		responseMsgBldr.append(setResponseMessageValues(INSURANCE_POLICY_NO_LBL, insurancePolicyNo));
		responseMsgBldr.append(setResponseMessageValues(INSURER_COMPANY_NAME_LBL, insurersCompanyName));
//		responseMsgBldr.append(setResponseMessageValues(INSURANCE_COVERAGE_TYPE_LBL, insuranceCoverageType));
//		responseMsgBldr.append(setResponseMessageValues(POLICY_AMOUNT_LBL, policyAmount));
		responseMsgBldr.append(setResponseMessageValues(INSURED_AMOUNT_LBL, insuredAmount));
		responseMsgBldr.append(setResponseMessageValues(EFFECTIVE_DATE_OF_INSURANCE_LBL, effectivedateofInsurance));
		responseMsgBldr.append(setLastLineMessageInResponse(INSURANCE_EXPIRY_DATE_LBL, insuranceExpiryDate));
		responseMsgBldr.append("\n");

		return responseMsgBldr.toString();
	}
	
}

