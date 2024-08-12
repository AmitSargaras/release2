package com.integrosys.cms.app.ws.security;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashCollateral;

public class FixedDepositeSecurityResponse extends AbstractSecurityResponse {
	
	public static final String OWN_OR_OTHER_BANK_ISSUER_LBL = "Own/Other Bank Issuer"; 
	public static final String DEPOSITOR_NAME_LBL = "Depositor Name"; 
	public static final String DEPOSIT_RECEIPT_NUMBER_LBL = "Deposit Receipt Number"; 
	public static final String DEPOSIT_AMOUNT_LBL = "Deposit Amount"; 
	public static final String LIEN_AMOUNT_LBL = "Lien Amount";
	public static final String FD_DATE_LBL = "FD Date";
	public static final String MATURITY_DATE_LBL = "Maturity Date";
	public static final String TENURE_LBL = "Tenure";
	public static final String DEPOSIT_INTEREST_RATE_LBL = "Deposit Interest Rate";
	public static final String STATUS_LBL = "status";
	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String depositorName = "-";
		String depositorReceiptNo = "-";
		String depositAmount = "-";
		String depositMaturityDate = "-";
		String tenure = "-";
		String depositeInterestRate = "-";
		String status = "-";
		String lienAmt = "-";
		String own_or_other_bank_issuer = "-";
		String fdDate = "-";
		
		StringBuilder responseMsgBldr = new StringBuilder();
		
		OBCashCollateral obCashCollateralInstance = (OBCashCollateral)iCollateralInstance;
		
		if(args[1]!=null && !args[1].isEmpty()){
			depositorName = args[1];
		}
		
		ICashDeposit[] depositInfoArray = obCashCollateralInstance.getDepositInfo();
		if(depositInfoArray!=null && depositInfoArray.length>0){
			for(ICashDeposit cashDepObj : depositInfoArray){
				
				if(cashDepObj.getDepositRefNo()!=null && !cashDepObj.getDepositRefNo().isEmpty()){
					depositorReceiptNo = cashDepObj.getDepositRefNo();
				}
				if(cashDepObj.getDepositAmount()!=null){
					depositAmount = Double.toString(cashDepObj.getDepositAmount().getAmount());
				}
				if(cashDepObj.getDepositMaturityDate()!=null){
					depositMaturityDate = sdf.format(cashDepObj.getDepositMaturityDate());
				}
					
				tenure = getTenureDays(cashDepObj.getIssueDate(),cashDepObj.getDepositMaturityDate());
				
				if(!"".equals(cashDepObj.getDepositeInterestRate())){
					depositeInterestRate = Double.toString(cashDepObj.getDepositeInterestRate());
				}
				if(cashDepObj.getActive()!=null && !cashDepObj.getActive().isEmpty()){
					status = cashDepObj.getActive();
				}
				own_or_other_bank_issuer = cashDepObj.getOwnBank()?"Own":"Other";
				
				if(cashDepObj.getIssueDate()!=null){
					fdDate = sdf.format(cashDepObj.getIssueDate());
				}
				
				ILienMethod[] lienArray = cashDepObj.getLien();
				if(lienArray!=null && lienArray.length>0){
					for(ILienMethod lienObj : lienArray){
						lienAmt = Double.toString(lienObj.getLienAmount()); 
						//Response Message
						responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL,args[0]));
						responseMsgBldr.append(setResponseMessageValues(OWN_OR_OTHER_BANK_ISSUER_LBL,own_or_other_bank_issuer));
						responseMsgBldr.append(setResponseMessageValues(DEPOSITOR_NAME_LBL,depositorName));
						responseMsgBldr.append(setResponseMessageValues(DEPOSIT_RECEIPT_NUMBER_LBL,depositorReceiptNo));
						responseMsgBldr.append(setResponseMessageValues(DEPOSIT_AMOUNT_LBL,depositAmount));
						responseMsgBldr.append(setResponseMessageValues(LIEN_AMOUNT_LBL,lienAmt));
						responseMsgBldr.append(setResponseMessageValues(FD_DATE_LBL,fdDate));
						responseMsgBldr.append(setResponseMessageValues(MATURITY_DATE_LBL,depositMaturityDate));
						responseMsgBldr.append(setResponseMessageValues(TENURE_LBL,tenure));
						responseMsgBldr.append(setResponseMessageValues(DEPOSIT_INTEREST_RATE_LBL,depositeInterestRate));
						responseMsgBldr.append(setLastLineMessageInResponse(STATUS_LBL,status));
						responseMsgBldr.append("\n");
					}
				}
			}
		}else{
			//For Security without Fixed Deposit Details
			responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL,args[0]));
			responseMsgBldr.append(setResponseMessageValues(OWN_OR_OTHER_BANK_ISSUER_LBL,own_or_other_bank_issuer));
			responseMsgBldr.append(setResponseMessageValues(DEPOSITOR_NAME_LBL,depositorName));
			responseMsgBldr.append(setResponseMessageValues(DEPOSIT_RECEIPT_NUMBER_LBL,depositorReceiptNo));
			responseMsgBldr.append(setResponseMessageValues(DEPOSIT_AMOUNT_LBL,depositAmount));
			responseMsgBldr.append(setResponseMessageValues(LIEN_AMOUNT_LBL,lienAmt));
			responseMsgBldr.append(setResponseMessageValues(FD_DATE_LBL,fdDate));
			responseMsgBldr.append(setResponseMessageValues(MATURITY_DATE_LBL,depositMaturityDate));
			responseMsgBldr.append(setResponseMessageValues(TENURE_LBL,tenure));
			responseMsgBldr.append(setResponseMessageValues(DEPOSIT_INTEREST_RATE_LBL,depositeInterestRate));
			responseMsgBldr.append(setLastLineMessageInResponse(STATUS_LBL,status));
		}	
		return responseMsgBldr.toString();
	}
	
	private String getTenureDays(Date fdDate, Date matureDate){
	   	 
		String tenure = "-";
		 if(matureDate!=null && fdDate!=null){
			 long difference_ms = (long)(matureDate.getTime()- fdDate.getTime());
			 tenure = (difference_ms/(1000 * 60 * 60 * 24))+1 + " Days";
		 }
		 return tenure;
	}
}
