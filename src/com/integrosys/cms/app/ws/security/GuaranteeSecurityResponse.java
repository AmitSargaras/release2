package com.integrosys.cms.app.ws.security;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;
import com.integrosys.cms.app.ws.common.SecuritySubTypeEnum;

public class GuaranteeSecurityResponse extends AbstractSecurityResponse {
	
	public static final String SECURITY_TYPE_LBL = "Security Type";
	public static final String SECURITY_SUB_TYPE_LBL = "Security Sub Type";
	public static final String SECURITY_CURRENCY_LBL = "Security Currency";
	public static final String SECURITY_PRIORITY_LBL = "Security Priority";
	public static final String START_DATE_OF_GUARANTEE_LBL = "Start Date Of Guarantee";
	public static final String SECURITY_MATURITY_DATE_LBL = "Security Maturity Date";
	public static final String GUARANTOR_FULL_NAME_LBL = "Guarantor Full Name";
	public static final String GUARANTOR_ADDRESS_LBL = "Guarantor Address";
	public static final String REFERENCE_NO_OF_GUARANTEE_LBL = "Reference No. Of Guarantee";
	public static final String LOANABLE_AMOUNT_LBL = "Loanable Amount";
	
	public static final String NETWORTH_LBL = "Networth";
	public static final String NETWORTHDATE_LBL = "Networth Date";
	public static final String GUARANTOR_TYPE_LBL = "Guarantor Type";
	
	public static final String STARTDATE_LBL = "Start Date";
	public static final String MATURITYDATE_LBL = "Maturity Date";
	public static final String GUARANTORNAME_LBL = "Guarantor Name";

	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String securityType="-";
		String securitySubType="-";
		String securityCurrency="-";
		String securityPriority="-";
		String startdateofGuarantee="-";
		String securityMaturityDate="-";
		String guarantorFullName="-";
		String guarantorAddress="-";
		String referenceNumberOfGuarantee="-"; 
		String loanableAmount="0.00";
		
		String networth="-";
		String networthDate="-";

		String guarantorName="-";
		String guarantorType="-";
		
		
		
		StringBuilder responseMsgBldr = new StringBuilder();
		OBGuaranteeCollateral obGuaranteeCollateral = (OBGuaranteeCollateral)iCollateralInstance;
		
		
		if(obGuaranteeCollateral.getCollateralType()!=null){
			securityType = obGuaranteeCollateral.getCollateralType().getTypeName();
		}
		if(obGuaranteeCollateral.getCollateralSubType()!=null){
			securitySubType = obGuaranteeCollateral.getCollateralSubType().getSubTypeName();
		}
		if(obGuaranteeCollateral.getCurrencyCode()!=null && !obGuaranteeCollateral.getCurrencyCode().isEmpty()){
			securityCurrency = obGuaranteeCollateral.getCurrencyCode().trim();
		}
		if(obGuaranteeCollateral.getSecPriority()!=null && !obGuaranteeCollateral.getSecPriority().isEmpty()){
			securityPriority = obGuaranteeCollateral.getSecPriority().equalsIgnoreCase("Y")?"Primary":"Secondary";
		}
		
		if(args[1]!=null && !args[1].isEmpty()){
			
				if((SecuritySubTypeEnum.GT400.name().equalsIgnoreCase(args[1])
						|| SecuritySubTypeEnum.GT402.name().equalsIgnoreCase(args[1])) ){
			
					if(obGuaranteeCollateral.getGuaranteeDate()!=null){
						startdateofGuarantee = sdf.format(obGuaranteeCollateral.getGuaranteeDate());
					}
					if(obGuaranteeCollateral.getCollateralMaturityDate()!=null){
						securityMaturityDate = sdf.format(obGuaranteeCollateral.getCollateralMaturityDate());
					}
					
					if(SecuritySubTypeEnum.GT402.name().equalsIgnoreCase(args[1])){
						
						if(obGuaranteeCollateral.getGuarantersName()!=null && !obGuaranteeCollateral.getGuarantersName().isEmpty()){
							guarantorName = obGuaranteeCollateral.getGuarantersName();
						}
					}
				}
				if((SecuritySubTypeEnum.GT405.name().equalsIgnoreCase(args[1])
						|| SecuritySubTypeEnum.GT402.name().equalsIgnoreCase(args[1])
						|| SecuritySubTypeEnum.GT406.name().equalsIgnoreCase(args[1])
						|| SecuritySubTypeEnum.GT408.name().equalsIgnoreCase(args[1])) ){
					
					if(obGuaranteeCollateral.getClaimDate()!=null){
						networthDate = sdf.format(obGuaranteeCollateral.getClaimDate());
					}
					if(obGuaranteeCollateral.getGuaranteeAmount()!=null){
						networth = obGuaranteeCollateral.getGuaranteeAmount().getAmountAsBigDecimal().toString();
					}
					if(obGuaranteeCollateral.getGuarantorType()!=null && !obGuaranteeCollateral.getGuarantorType().isEmpty()){
						CommonCodeEntryDAO commonCodeEntryDAO = new CommonCodeEntryDAO();
						try {
							guarantorType = commonCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(obGuaranteeCollateral.getGuarantorType(), "GUARANTOR_TYPE");
						} catch (DBConnectionException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
		}
		if(obGuaranteeCollateral.getGuarantersFullName()!=null && !obGuaranteeCollateral.getGuarantersFullName().isEmpty()){
			guarantorFullName = obGuaranteeCollateral.getGuarantersFullName();
		}
		if(obGuaranteeCollateral.getAddressLine1()!=null && !obGuaranteeCollateral.getAddressLine1().isEmpty()){
			guarantorAddress = obGuaranteeCollateral.getAddressLine1();
		}
		if(obGuaranteeCollateral.getReferenceNo()!=null && !obGuaranteeCollateral.getReferenceNo().isEmpty()){
			referenceNumberOfGuarantee = obGuaranteeCollateral.getReferenceNo();
		}
		if(obGuaranteeCollateral.getCMV()!=null){
			loanableAmount = calculateLoanableAmount(obGuaranteeCollateral.getCMV(),obGuaranteeCollateral.getMargin());
		}
		
		//Response Message
		responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL,args[0]));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_TYPE_LBL,securityType));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_SUB_TYPE_LBL,securitySubType));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_CURRENCY_LBL,securityCurrency));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_PRIORITY_LBL,securityPriority));
		
		if(args[1]!=null && !args[1].isEmpty()){
			if(SecuritySubTypeEnum.GT400.name().equalsIgnoreCase(args[1])){
				responseMsgBldr.append(setResponseMessageValues(START_DATE_OF_GUARANTEE_LBL,startdateofGuarantee));
				responseMsgBldr.append(setResponseMessageValues(SECURITY_MATURITY_DATE_LBL,securityMaturityDate));
				responseMsgBldr.append(setResponseMessageValues(GUARANTOR_FULL_NAME_LBL,guarantorFullName));
			}
			if((SecuritySubTypeEnum.GT405.name().equalsIgnoreCase(args[1])
					|| SecuritySubTypeEnum.GT406.name().equalsIgnoreCase(args[1])
					|| SecuritySubTypeEnum.GT408.name().equalsIgnoreCase(args[1]))){
				responseMsgBldr.append(setResponseMessageValues(NETWORTH_LBL,networth));
				responseMsgBldr.append(setResponseMessageValues(NETWORTHDATE_LBL,networthDate));
				responseMsgBldr.append(setResponseMessageValues(GUARANTOR_FULL_NAME_LBL,guarantorFullName));
				responseMsgBldr.append(setResponseMessageValues(GUARANTOR_TYPE_LBL,guarantorType));
			}
			if(SecuritySubTypeEnum.GT402.name().equalsIgnoreCase(args[1])){
				responseMsgBldr.append(setResponseMessageValues(NETWORTHDATE_LBL,networthDate));
				responseMsgBldr.append(setResponseMessageValues(STARTDATE_LBL,startdateofGuarantee));
				responseMsgBldr.append(setResponseMessageValues(MATURITYDATE_LBL,securityMaturityDate));
				responseMsgBldr.append(setResponseMessageValues(GUARANTORNAME_LBL,guarantorName));
				responseMsgBldr.append(setResponseMessageValues(GUARANTOR_TYPE_LBL,guarantorType));
			}
		}
		
		responseMsgBldr.append(setResponseMessageValues(GUARANTOR_ADDRESS_LBL,guarantorAddress));
		responseMsgBldr.append(setResponseMessageValues(REFERENCE_NO_OF_GUARANTEE_LBL,referenceNumberOfGuarantee));
		responseMsgBldr.append(setLastLineMessageInResponse(LOANABLE_AMOUNT_LBL,loanableAmount));
		responseMsgBldr.append("\n");

		return responseMsgBldr.toString();
	}
	
	public String calculateLoanableAmount(Amount cmv, double loanMargin){
		String loanableAmountStr="0.00";
		if(cmv!=null && loanMargin >= 0){
			BigDecimal amountCMV= cmv.getAmountAsBigDecimal();
			BigDecimal margin= new BigDecimal(loanMargin);
			BigDecimal loanableAmt = amountCMV.subtract((amountCMV.multiply(margin)).divide(new BigDecimal(100)));
			loanableAmountStr=loanableAmt.toString();
		}
		
		return loanableAmountStr;
	}
	
}

