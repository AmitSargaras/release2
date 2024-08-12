package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerValidator;

public class ChequeValidator {
	public static ActionErrors validateInput(ChequeForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		final double MAX_MARGIN = 100;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		if (!(errorCode = Validator.checkNumber(aForm.getMargin(), false, 0, MAX_MARGIN)).equals(Validator.ERROR_NONE)) {
			errors.add("margin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					MAX_MARGIN + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"aForm.getMargin(): " + aForm.getMargin());
		}
		

		
		Date d = DateUtil.getDate();
		if ((aForm.getIssueDate() != null) && (aForm.getIssueDate().trim().length() != 0)) {
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getIssueDate()));
			
			if (a < 0) {
				errors.add("issueDateError", new ActionMessage("error.date.compareDate.more", "Deposit  Date ",
				"Current Date"));
			}
		
		}
		
		if ((aForm.getReturnDate() != null) && (aForm.getReturnDate().trim().length() != 0)){
			if(aForm.getIssueDate()!=null && !"".equals(aForm.getIssueDate())){
			if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getReturnDate())).before(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getIssueDate())))) {
		errors.add("returnDateError", new ActionMessage(
					"error.date.compareDate.cannotBelater", "Return Date", "Deposit Date "));
		}
			}
		}	
			if ((aForm.getExpiryDate() != null) && (aForm.getExpiryDate().trim().length() != 0)){
				if(aForm.getIssueDate()!=null && !"".equals(aForm.getIssueDate())){
				if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getExpiryDate())).before(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getIssueDate())))) {
			errors.add("expiryDateError", new ActionMessage(
						"error.date.compareDate.cannotBelater", "Expiry Date", "Deposit Date "));
			}
			}
			}
			
			if ((aForm.getExpiryDate() != null) && (aForm.getExpiryDate().trim().length() != 0)){
				if(aForm.getIssueDate()!=null && !"".equals(aForm.getIssueDate())){
					int x=DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getExpiryDate())).compareTo(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getIssueDate())));
				if(x==0) {
			errors.add("expiryDateForEqualError", new ActionMessage(
						"error.date.compareDate.cannotbeequal", "Expiry Date", "Deposit Date "));
			}
			}
			}
		
			
			
			if ((aForm.getStartDate() != null) && (aForm.getStartDate().trim().length() != 0)){
				if(aForm.getMaturityDate()!=null && !"".equals(aForm.getMaturityDate())){
				if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getStartDate())).after(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getMaturityDate())))) {
			errors.add("maturityDateError", new ActionMessage(
						"error.date.compareDate.cannotBelater", "Start Date", "Maturity Date "));
			}
			}
			}
		
			Date d1 = DateUtil.getDate();
			if ((aForm.getMaturityDate() != null) && (aForm.getMaturityDate().trim().length() != 0)) {
				int a1 = d1.compareTo(DateUtil.convertDate(locale, aForm.getMaturityDate()));
				
				if (a1 > 0) {
					errors.add("maturityDate", new ActionMessage("error.date.compareDate.later", "Maturity  Date ",
					"Current Date"));
				}
			
			}
			
			
			Date d2 = DateUtil.getDate();
			if ((aForm.getStartDate() != null) && (aForm.getStartDate().trim().length() != 0)) {
				int a2 = d2.compareTo(DateUtil.convertDate(locale, aForm.getStartDate()));
				
				if (a2 < 0) {
					errors.add("startDate", new ActionMessage("error.date.compareDate.more", "Start  Date ",
					"Current Date"));
				}
			
			}
			
			
		
		if (aForm.getBankName() == null
				|| "".equals(aForm.getBankName().trim())) {
			errors.add("bankNameError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"bankNameError");
		}
		if (aForm.getBranchName() == null
				|| "".equals(aForm.getBranchName().trim())) {
			errors.add("branchNameError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"branchNameError");
		}
		
		if (aForm.getPacketNumber()== null
				|| "".equals(aForm.getPacketNumber().trim())) {
			errors.add("packetNumberError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"packetNumberError");
		}else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getPacketNumber());
			if( nameFlag == true)
				errors.add("packetNumberError", new ActionMessage("error.string.invalidCharacter"));
		   }
		
		
		
		
		if(aForm.getBulkSingle()==null)
		{
		   if (aForm.getChequeNumber()== null
				|| "".equals(aForm.getChequeNumber().trim())) {
			errors.add("chequeNumberError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"chequeNumberError");
		   }else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getChequeNumber());
			if( nameFlag == true)
				errors.add("chequeNumberError", new ActionMessage("error.string.invalidCharacter"));
		   }
		}else if(aForm.getBulkSingle().equals("")){
		  
			 if (aForm.getChequeNumber()== null
					|| "".equals(aForm.getChequeNumber().trim())) {
				errors.add("chequeNumberError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"chequeNumberError");
			  }else{
				boolean nameFlag = ASSTValidator.isValidANDName(aForm.getChequeNumber());
				if( nameFlag == true)
					errors.add("chequeNumberError", new ActionMessage("error.string.invalidCharacter"));
			  }
			
		  }else if(aForm.getBulkSingle().equals("B")){
		
			 if (aForm.getChequeNoFrom()== null
					|| "".equals(aForm.getChequeNoFrom().trim())) {
				errors.add("chequeNoFromError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"chequeNoFromError");
			  }else{
				boolean nameFlag = ASSTValidator.isValidANDName(aForm.getChequeNoFrom());
				if( nameFlag == true)
					errors.add("chequeNoFromError", new ActionMessage("error.string.invalidCharacter"));
			  }	
			
		
		   if (aForm.getChequeNoTo()== null
				|| "".equals(aForm.getChequeNoTo().trim())) {
			errors.add("chequeNoToError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"chequeNoToError");
		   }else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getChequeNoTo());
			if( nameFlag == true)
				errors.add("chequeNoToError", new ActionMessage("error.string.invalidCharacter"));
		   }
		
		}
		
		if (aForm.getRemarks() != null ) {
			 if (aForm.getRemarks().length() > 200) {
					errors.add("remarksError", new ActionMessage(
							"error.remarks.length"));
					DefaultLogger.debug(ManualInputCustomerValidator.class,
							"error.remarks.length");
		}
		}
		
		

		 /*  if (!(errorCode = Validator.checkNumber(aForm.getChequeAmt(), false, 0, 99999999999999999999.99, 3,
		  					locale)).equals(Validator.ERROR_NONE)) {
		  				errors.add("chequeAmtError", new ActionMessage(
		  						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		  						"0", "99999999999999999999.99"));
		  			}
		   */
		   if (!(errorCode = Validator.checkAmount(aForm.getChequeAmt(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("chequeAmtError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", maximumAmt));
			}
		   
		/*   if(aForm.getChequeAmt()!=null && !"".equals(aForm.getChequeAmt())){
		  				boolean nameFlag = ASSTValidator.isValidANDName(aForm.getChequeAmt());
		  				if( nameFlag == true)
		  					errors.add("chequeAmtError", new ActionMessage("error.string.invalidCharacter"));
		  			   }
		*/
		
		/*if (!(errorCode = Validator.checkAmount(aForm.getBefValMargin(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("befValMargin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getBefValMargin(): " + aForm.getBefValMargin());
		}
		if (!(errorCode = Validator.checkDate(aForm.getDateVal(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("dateVal", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getDateVal()");
		}
		if (!(errorCode = Validator.checkAmount(aForm.getChequeAmt(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("chequeAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getChequeAmt()");
		}
		if (!(errorCode = Validator.checkString(aForm.getProceedBankACNo(), false, 0, 15)).equals(Validator.ERROR_NONE)) {
			errors.add("proceedBankACNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					15 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getProceedBankACNo: " + aForm.getProceedBankACNo());
		}
		if (!(errorCode = Validator.checkString(aForm.getLocationBank(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("locationBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getLocationBank: " + aForm.getLocationBank());
		}
		if (!(errorCode = Validator.checkString(aForm.getIssuer(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("issuer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getIssuer() : " + aForm.getIssuer());
		}
		if (!(errorCode = Validator.checkString(aForm.getDraweeBank(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("draweeBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getDraweeBank() : " + aForm.getDraweeBank());
		}
		if (!(errorCode = Validator.checkDate(aForm.getIssueDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("issueDate",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getIssueDate(): " + aForm.getIssueDate());
		}
		if (!(errorCode = Validator.checkDate(aForm.getExpiryDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsValidationHelper",
							"getExpiryDate(): " + aForm.getExpiryDate());
		}*/

		return errors;
	}

}
