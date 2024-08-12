/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.caseCreationUpdate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.asst.validator.ASSTValidator;


/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class CaseCreationValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale) 
    {
		ActionErrors errors = new ActionErrors();
		
		
		MaintainCaseCreationForm form = (MaintainCaseCreationForm) commonform;
		Date systemDate = DateUtil.getDate();
		String errorCode = null;
		int errorrCodeInt;
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		String event = form.getEvent();
		
		
	/*	Date startDate = DateUtil.convertDate(locale,form.getStartDate());
		Date endDate = DateUtil.convertDate(locale,form.getEndDate());*/
		
		
		
//		if(form.getDescription()==null||form.getDescription().trim().equals(""))
//		{	
//			
//			errors.add("descriptionError", new ActionMessage("error.string.mandatory"));
//			
//		}
//		
//		else if(!(errorCode = Validator.checkString(form.getDescription(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
//			errors.add("descriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
//					50 + ""));
//		}
//		else {
//			
//			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDescription());
//			if( descriptionFlag == true)
//				errors.add("descriptionError", new ActionMessage("error.string.invalidCharacter"));
//			}
		String status = form.getStatus();
		int len = 0;
		String[] checkBoxSelectedArr = form.getCheckBoxValues();
		
		if("7".equals(status)) {
		
		if(form.getUpdatedVaultNumber() != null) {
			 len = form.getUpdatedVaultNumber().length;
			String[] updateVaultNumberArr = form.getUpdatedVaultNumber();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateVaultNumberArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("vaultNumberError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}
		
		if(form.getUpdatedVaultReceiptDate() != null) {
			 len = form.getUpdatedVaultReceiptDate().length;
			String[] updateVaultReceiptdateArr = form.getUpdatedVaultReceiptDate();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateVaultReceiptdateArr[i];
				if(str == null || "".equals(str)) {
					//if(flag == false) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("vaultReceiptDateError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
					//flag = true;
					//}
				}
				
				/*else {
					
					try {
						if (! (errorCode = Validator.checkDate(str, true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
							errors.add("vaultReceiptDateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
						 }	else {
							 String d1 = str;
								String[] d2 = d1.split("/");
								if(d2.length == 3) {
									if(d2[2].length() != 4) {
										errors.add("vaultReceiptDateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
									}else {
										relationshipDateFormat.parse(str.toString().trim());
									}
								}else {
									errors.add("vaultReceiptDateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
								}
						 }
//						relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
//						facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
					} catch (ParseException e) {
						errors.add("vaultReceiptDateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
					}
				}*/
			}
		}
		
		
		/*if(form.getUpdatedFileBarCode() != null) {
			 len = form.getUpdatedFileBarCode().length;
			String[] updateFileBarCodeArr = form.getUpdatedFileBarCode();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateFileBarCodeArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("fileBarCodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}
		
		if(form.getUpdatedDocBarcode() != null) {
			 len = form.getUpdatedDocBarcode().length;
			String[] updatedocBarcodeArr = form.getUpdatedDocBarcode();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updatedocBarcodeArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("docBarcodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
					
				}
			}
		}
		
		*/
		
		
		/*if(form.getUpdatedVaultLocation() != null) {
			 len = form.getUpdatedVaultLocation().length;
			String[] updateVaultLocationArr = form.getUpdatedVaultLocation();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateVaultLocationArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("vaultLocationError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}*/
		
		/*if(form.getUpdatedRackNumber() != null) {
			 len = form.getUpdatedRackNumber().length;
			String[] updateRackNumberArr = form.getUpdatedRackNumber();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateRackNumberArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("rackNumberError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}*/
		
		}
		
		
		if("8".equals(status) || "9".equals(status)) {
		if(form.getUpdatedUserName() != null) {
			 len = form.getUpdatedUserName().length;
			String[] updateUserNameArr = form.getUpdatedUserName();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateUserNameArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("userNameError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}else {
					if(!"checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						updateUserNameArr[i]="";
					}
				}
			}
			form.setUpdatedUserName(updateUserNameArr);
		}
		
		if(form.getUpdatedSubmittedTo() != null) {
			 len = form.getUpdatedSubmittedTo().length;
			String[] updateSubmittedToArr = form.getUpdatedSubmittedTo();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateSubmittedToArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("submittedToError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}
		
		}
		
//		if("8".equals(status) || "9".equals(status)) {
		if(form.getUpdatedDocAmount() != null) {
			 len = form.getUpdatedDocAmount().length;
			String[] updateDocAmountArr = form.getUpdatedDocAmount();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateDocAmountArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("documentAmountError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}
//		}
		
		
		if("8".equals(status) || "9".equals(status)) {
			if(form.getUpdatedRetrievaldate() != null) {
				 len = form.getUpdatedRetrievaldate().length;
				String[] updateRetrievaldateArr = form.getUpdatedRetrievaldate();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updateRetrievaldateArr[i];
					if(str == null || "".equals(str)) {
						//if(flag == false) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("retrievaldateError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
						//flag = true;
						//}
					}
					/*else {
						
						try {
							if (! (errorCode = Validator.checkDate(str, true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
								errors.add("retrievaldateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
							 }	else {
								 String d1 = str;
									String[] d2 = d1.split("/");
									if(d2.length == 3) {
										if(d2[2].length() != 4) {
											errors.add("retrievaldateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
										}else {
											relationshipDateFormat.parse(str.toString().trim());
										}
									}else {
										errors.add("retrievaldateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
									}
							 }
//							relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
//							facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
						} catch (ParseException e) {
							errors.add("retrievaldateError"+(i+1), new ActionMessage("error.casecreation.date.invalid.format"));
						}
					}*/
				}
			}
			}
		
		
		if(!"maker_confirm_resubmit_delete".equals(event)) {
			if("8".equals(status) || "9".equals(status)) {
				if("".equals(form.getRemarkCheck()) || form.getRemarkCheck() == null) { 
					errors.add("caseCreationUpdateRemarksError", new ActionMessage("error.string.mandatory"));
				}
			}
			}
			
			if("3".equals(status)) {
				if(form.getUpdatedStampDuty() != null) {
					 len = form.getUpdatedStampDuty().length;
					String[] updateStampDutyArr = form.getUpdatedStampDuty();
					String str = "";
					for(int i=0;i<len;i++) {
						str = updateStampDutyArr[i];
						/*if(str == null || "".equals(str)) {
							errors.add("stampDutyError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}*/
						if(str != null && !"".equals(str)) {
						boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(str);
						if(!isNumber) {
							if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
							errors.add("stampDutyError"+(i+1), new ActionMessage("error.amount.format"));
							}
						}
						}
						
					}
				}
				}
			
		
		
		
		
		if("3".equals(status)) {
		if(form.getUpdatedVaultLocation() != null) {
			 len = form.getUpdatedVaultLocation().length;
			String[] updateVaultLocationArr = form.getUpdatedVaultLocation();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateVaultLocationArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("vaultLocationError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}
		}
		
		/*if("3".equals(status)) {
		if(form.getUpdatedStampDuty() != null) {
			 len = form.getUpdatedStampDuty().length;
			String[] updateStampDutyArr = form.getUpdatedStampDuty();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateStampDutyArr[i];
				if(str == null || "".equals(str)) {
					errors.add("stampDutyError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
				}
			}
		}
		}*/
		
		/*if("3".equals(status)) {
		if(form.getUpdatedPlaceOfExecution() != null) {
			 len = form.getUpdatedPlaceOfExecution().length;
			String[] updatePlaceOfExecutionArr = form.getUpdatedPlaceOfExecution();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updatePlaceOfExecutionArr[i];
				if(str == null || "".equals(str)) {
					errors.add("placeOfExecutionError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
				}
			}
		}
		}*/
		
		
//		if("6".equals(status)) {
			/*if(form.getUpdatedDocBarcode() != null) {
				 len = form.getUpdatedDocBarcode().length;
				String[] updatedocBarcodeArr = form.getUpdatedDocBarcode();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updatedocBarcodeArr[i];
					if(str == null || "".equals(str)) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("docBarcodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
					}
				}
			}*/
			
			/*if(form.getUpdatedFileBarCode() != null) {
				 len = form.getUpdatedFileBarCode().length;
				String[] updateFileBarCodeArr = form.getUpdatedFileBarCode();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updateFileBarCodeArr[i];
					if(str == null || "".equals(str)) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("fileBarCodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
					}
				}
			}*/
			
			/*if(form.getUpdatedVaultLocation() != null) {
				 len = form.getUpdatedVaultLocation().length;
				String[] updateVaultLocationArr = form.getUpdatedVaultLocation();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updateVaultLocationArr[i];
					if(str == null || "".equals(str)) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("vaultLocationError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
					}
				}
			}*/
			
//			}
		
		/*if("6".equals(status)) {
		if(form.getUpdatedBoxBarCode() != null) {
			 len = form.getUpdatedBoxBarCode().length;
			String[] updateBoxBarCodeArr = form.getUpdatedBoxBarCode();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateBoxBarCodeArr[i];
				if(str == null || "".equals(str)) {
					errors.add("boxBarCodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
				}
			}
		}
		}*/
		
		
	/*	if("3".equals(status)) {
			if(form.getUpdatedBoxBarCode() != null) {
				 len = form.getUpdatedBoxBarCode().length;
				String[] updateBoxBarCodeArr = form.getUpdatedBoxBarCode();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updateBoxBarCodeArr[i];
					if(str == null || "".equals(str)) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("boxBarCodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
					}
				}
			}
			
			if(form.getUpdatedFileBarCode() != null) {
				 len = form.getUpdatedFileBarCode().length;
				String[] updateFileBarCodeArr = form.getUpdatedFileBarCode();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updateFileBarCodeArr[i];
					if(str == null || "".equals(str)) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("fileBarCodeError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
					}
				}
			}
			}*/
		
		
		/*if("6".equals(status)) {
		if(form.getUpdatedRackNumber() != null) {
			 len = form.getUpdatedRackNumber().length;
			String[] updateRackNumberArr = form.getUpdatedRackNumber();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateRackNumberArr[i];
				if(str == null || "".equals(str)) {
					if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
					errors.add("rackNumberError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
					}
				}
			}
		}
		}*/
		
		/*if("6".equals(status)) {
		if(form.getUpdatedLotNumber() != null) {
			 len = form.getUpdatedLotNumber().length;
			String[] updateLotNumberArr = form.getUpdatedLotNumber();
			String str = "";
			for(int i=0;i<len;i++) {
				str = updateLotNumberArr[i];
				if(str == null || "".equals(str)) {
					errors.add("lotNumberError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
				}
			}
		}
		}*/
		
		/*if("3".equals(status)) {
			if(form.getUpdatedReceivedDate() != null) {
				 len = form.getUpdatedReceivedDate().length;
				String[] updateReceivedDateArr = form.getUpdatedReceivedDate();
				String str = "";
				for(int i=0;i<len;i++) {
					str = updateReceivedDateArr[i];
					if(str == null || "".equals(str)) {
						if("checked".equalsIgnoreCase(checkBoxSelectedArr[i])) {
						errors.add("receivedDateError"+(i+1), new ActionMessage("error.string.mandatory")); //docBarcodeError3
						}
					}
				}
			}
			}*/
		
		
		
		
		/*boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(num);
		if(!isNumber) {
			errors.add("valuationAmount", new ActionMessage("error.amount.format"));
		}*/
		
		
		//SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		//limit.setEscodLevelOneDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL1().trim()));
		
		/*if (requestDTO.getEscodL1() != null && !requestDTO.getEscodL1().toString().trim().isEmpty()) {
			try {
				if (! (errorCode = Validator.checkDate(requestDTO.getEscodL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
					errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
				 }	else {
					 String d1 = (String)requestDTO.getEscodL1();
						String[] d2 = d1.split("/");
						if(d2.length == 3) {
							if(d2[2].length() != 4) {
								errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
							}else {
								relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
								facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
							}
						}
				 }
//				relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
//				facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
			} catch (ParseException e) {
				errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
			}
		}*/
		
		
		
		
		
		
		/**
		 * @author sandiip.shinde : For what purpose it the following block of code.
		 * */
		/*{
			if(form.getDescription().trim()==""){
				errors.add("descriptionError", new ActionMessage("error.string.mandatory"));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDescription());
				if( nameFlag == true)
					errors.add("descriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
		}*/
		/*if(form.getStartDate()==null || form.getStartDate().trim().equals("") )
		{
			errors.add("startDateError", new ActionMessage("error.date.mandatory"));
		}else if(startDate.before(systemDate))
		{
			errors.add("startDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier","Start Date","Current Date"));
			
		}*/
//		if(!(form.getStartDate()==null || form.getStartDate().trim().equals("")) )
//		{
		/*if(form.getEndDate()==null || form.getEndDate().trim().equals("") )
		{
			errors.add("endDateError", new ActionMessage("error.date.mandatory"));
		} else{
			
				if(endDate.before(startDate))
				{
					errors.add("endDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier","End Date","Start Date"));
					
				}
				
		}*/
//		}
		
		return errors;
    }

}
