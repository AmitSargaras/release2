package com.integrosys.cms.app.ws.dto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

@Service
public class DigitalLibraryDTOMapper {

	


	
	

	public DigitalLibraryRequestDTO getDigitalLibraryRequestDTOWithActualValues(DigitalLibraryRequestDTO requestDTO) {

		DigitalLibraryRequestDTO digitalLibraryRequestDTO =new DigitalLibraryRequestDTO();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this,
				"Inside getDigitalLibraryRequestDTOWithActualValues of digitalLibraryDetailsDTOMapper================:::::");
		System.out.println("Inside getDigitalLibraryRequestDTOWithActualValues of digitalLibraryDetailsDTOMapper================:::::");
		SimpleDateFormat pDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		ILimitProfile profile = new OBLimitProfile();
		String errorCode = null;
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		ILimitDAO limitDAO = LimitDAOFactory.getDAO();
		System.out.println("requestDTO:::::"+requestDTO);
		if(!requestDTO.getPartyId().isEmpty()) {
		if(limitDAO.getPartyId(requestDTO.getPartyId())) {
		digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
		}else {
			errors.add("partyId",new ActionMessage("error.invalid.value"));
		}
		}
		System.out.println("Checked for party Id:"+requestDTO.getPartyId());
		
		/*if(!requestDTO.getSystemId().isEmpty()) {
		Object sysIdObj = masterObj.getObjectByEntityNameAndCatEntry("entryCode", requestDTO.getSystemId().trim(),
				"systemId",errors,"SYSTEM");
		if(!(sysIdObj instanceof ActionErrors)){
			digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
		}else {
			errors.add("systemId",new ActionMessage("error.invalid.value"));
		}
		}*/
		
		
		if(!requestDTO.getSystemId().isEmpty()) {
		if(limitDAO.getSystemName(requestDTO.getSystemId())){
			digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
		}else {
			errors.add("systemId",new ActionMessage("error.invalid.value"));
		}
		}
		
		if(!requestDTO.getEmployeeCodeRM().isEmpty()) {
			if(limitDAO.getEmployeeCodeRM(requestDTO.getEmployeeCodeRM())){
				digitalLibraryRequestDTO.setEmployeeCodeRM(requestDTO.getEmployeeCodeRM());
			}else {
				errors.add("rmEmpId",new ActionMessage("error.invalid.value"));
			}
			}
		
		if(!requestDTO.getPanNo().isEmpty()) {
		if(limitDAO.getPanNo(requestDTO.getPanNo())) {
		digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
		}else {
			errors.add("panNo",new ActionMessage("error.invalid.value"));
		}
		}
		
		if(!requestDTO.getPartyName().isEmpty()) {
		if(limitDAO.getPartyName(requestDTO.getPartyName())) {
			digitalLibraryRequestDTO.setPartyName(requestDTO.getPartyName());
			}else {
				errors.add("partyName",new ActionMessage("error.invalid.value"));
			}
		}
		
		
		if(null !=digitalLibraryRequestDTO.getPartyName() && null !=digitalLibraryRequestDTO.getPartyId()) {
			if(limitDAO.getPartyNameAndPartyID(requestDTO.getPartyName(),requestDTO.getPartyId())) {
				digitalLibraryRequestDTO.setPartyName(requestDTO.getPartyName());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				}else {
					errors.add("partyName",new ActionMessage("error.invalidPartyIdPartyName.value"));
					errors.add("partyId",new ActionMessage("error.invalidPartyIdPartyName.value"));
				}
			}
		
		if(null !=digitalLibraryRequestDTO.getPartyName() && null !=digitalLibraryRequestDTO.getSystemId()) {
			if(limitDAO.getPartyNameAndSystemID(requestDTO.getPartyName(),requestDTO.getSystemId())) {
				digitalLibraryRequestDTO.setPartyName(requestDTO.getPartyName());
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				}else {
					errors.add("partyName",new ActionMessage("error.invalidSystemIdPartyName.value"));
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPartyName.value"));
				}
			}
		
		if(null !=digitalLibraryRequestDTO.getPanNo() && null !=digitalLibraryRequestDTO.getPartyId()) {
			if(limitDAO.getPanNoAndPartyID(requestDTO.getPanNo(),requestDTO.getPartyId())) {
				digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				}else {
					errors.add("panNo",new ActionMessage("error.invalidPanNoPartyID.value"));
					errors.add("partyId",new ActionMessage("error.invalidPanNoPartyID.value"));
				}
			}
		
		
		if(null !=digitalLibraryRequestDTO.getSystemId() && null !=digitalLibraryRequestDTO.getPartyId()) {
			if(limitDAO.getSystemIDAndPartyID(requestDTO.getSystemId(),requestDTO.getPartyId())) {
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				}else {
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPartyId.value"));
					errors.add("partyId",new ActionMessage("error.invalidSystemIdPartyId.value"));
				}
			}
		
		if(null !=digitalLibraryRequestDTO.getSystemId() && null !=digitalLibraryRequestDTO.getPanNo()) {
			if(limitDAO.getPanNoAndSystemId(requestDTO.getPanNo(),requestDTO.getSystemId())) {
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
				}else {
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPanNo.value"));
					errors.add("panNo",new ActionMessage("error.invalidSystemIdPanNo.value"));
				}
			}
		
		
		if(null !=digitalLibraryRequestDTO.getSystemId() && null !=digitalLibraryRequestDTO.getPartyId() && null !=digitalLibraryRequestDTO.getPanNo()) {
			if(limitDAO.getSystemIDAndPartyIDAndPanNo(requestDTO.getSystemId(),requestDTO.getPartyId(),requestDTO.getPanNo())) {
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
				}else {
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPartyIdPanNo.value"));
					errors.add("partyId",new ActionMessage("error.invalidSystemIdPartyIdPanNo.value"));
					errors.add("panNo",new ActionMessage("error.invalidSystemIdPartyIdPanNo.value"));
				}
			}
		
		
		
		
		/*if(!requestDTO.getDocType().isEmpty()) {
		Object docTypeObj = masterObj.getObjectByEntityNameAndCatEntry("entryCode", requestDTO.getDocType().trim(),
				"docType",errors,"IMG_UPLOAD_CATEGORY");
		if(!(docTypeObj instanceof ActionErrors)){
			digitalLibraryRequestDTO.setDocType(requestDTO.getDocType());
		}else {
			errors.add("docType",new ActionMessage("error.invalid.value"));
		}
		}*/
		
		
		if(!requestDTO.getDocType().isEmpty()) {
			String EntryCode = limitDAO.getEntryCode(requestDTO.getDocType().trim(),"IMG_UPLOAD_CATEGORY");
			if(null != EntryCode) {
				digitalLibraryRequestDTO.setDocType(EntryCode);
			}else {
				errors.add("docType",new ActionMessage("error.invalid.value"));
			}
		}
		
		
		try { 
			
			if(!requestDTO.getDocRecvFromDate().isEmpty()) {
		if (! (errorCode = Validator.checkDate(requestDTO.getDocRecvFromDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
			errors.add("docRecvFromDate", new ActionMessage("error.invalid.format"));
		 }	else {
			 String d1 = (String)requestDTO.getDocRecvFromDate();
				String[] d2 = d1.split("/");
				if(d2.length == 3) {
					if(d2[2].length() != 4) {
						errors.add("docRecvFromDate", new ActionMessage("error.invalid.format"));
					}else {
						
						relationshipDateFormat.parse(requestDTO.getDocRecvFromDate().toString().trim());
						digitalLibraryRequestDTO.setDocRecvFromDate(requestDTO.getDocRecvFromDate().trim());
						
				}
				}
		 }
	 }
	} catch (ParseException e) {
		errors.add("docRecvFromDate", new ActionMessage("error.invalid.format"));
	}
		
		
		
		try { 
			
			if(!requestDTO.getDocRecvToDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocRecvToDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docRecvToDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocRecvToDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docRecvToDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocRecvToDate().toString().trim());
							digitalLibraryRequestDTO.setDocRecvToDate(requestDTO.getDocRecvToDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docRecvToDate", new ActionMessage("error.invalid.format"));
		}
		
		
		try { 
			
			if(!requestDTO.getDocTagFromDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocTagFromDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docTagFromDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocTagFromDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docTagFromDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocTagFromDate().toString().trim());
							digitalLibraryRequestDTO.setDocTagFromDate(requestDTO.getDocTagFromDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docTagFromDate", new ActionMessage("error.invalid.format"));
		}
		
		
		try { 
			
			if(!requestDTO.getDocTagToDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocTagToDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docTagToDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocTagToDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docTagToDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocTagToDate().toString().trim());
							digitalLibraryRequestDTO.setDocTagToDate(requestDTO.getDocTagToDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docTagToDate", new ActionMessage("error.invalid.format"));
		}
		
		
		
		try { 
			
			if(!requestDTO.getDocFromDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocFromDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docFromDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocFromDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docFromDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocFromDate().toString().trim());
							digitalLibraryRequestDTO.setDocFromDate(requestDTO.getDocFromDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docFromDate", new ActionMessage("error.invalid.format"));
		}
		
		try { 
			
			if(!requestDTO.getDocToDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocToDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docToDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocToDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docToDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocToDate().toString().trim());
							digitalLibraryRequestDTO.setDocToDate(requestDTO.getDocToDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docToDate", new ActionMessage("error.invalid.format"));
		}
		
		
		
		
		
	
		if(!requestDTO.getDocFromAmt().isEmpty()) {
			boolean docAmtflag = true;
		if (!(errorCode =Validator.checkAmount(requestDTO.getDocFromAmt(), true,1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
				.equals(Validator.ERROR_NONE)) {
			errors.add("docFromAmt", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.NUMBER, errorCode), "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
			docAmtflag = false;
	 }else {
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(requestDTO.getDocFromAmt()));
			if( codeFlag == true) {
				errors.add("Locale.getDefault()", new ActionMessage("error.string.invalidCharacter"));
				docAmtflag = false;
			}
		}
		
       if(docAmtflag) {
    	digitalLibraryRequestDTO.setDocFromAmt(requestDTO.getDocFromAmt());
		}
		}
		
		
		if(!requestDTO.getDocToAmt().isEmpty()) {
			boolean docAmtflag = true;
		if (!(errorCode =Validator.checkAmount(requestDTO.getDocToAmt(), true,1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
				.equals(Validator.ERROR_NONE)) {
			errors.add("docToAmt", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.NUMBER, errorCode), "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
			docAmtflag = false;
	 }else {
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(requestDTO.getDocToAmt()));
			if( codeFlag == true) {
				errors.add("Locale.getDefault()", new ActionMessage("error.string.invalidCharacter"));
				docAmtflag = false;
			}
		}
		
       if(docAmtflag) {
    	digitalLibraryRequestDTO.setDocToAmt(requestDTO.getDocToAmt());
		}
		}
		
		if(null != digitalLibraryRequestDTO.getDocToAmt() && null != digitalLibraryRequestDTO.getDocFromAmt()) {
			BigDecimal docfromAmt =new BigDecimal("0");
			BigDecimal doctoAmt =new BigDecimal("0");
			 docfromAmt =new BigDecimal(digitalLibraryRequestDTO.getDocFromAmt());
			 doctoAmt =new BigDecimal(digitalLibraryRequestDTO.getDocToAmt());
			/*Double docfromAmt = Double.parseDouble(digitalLibraryRequestDTO.getDocFromAmt());
			Double doctoAmt = Double.parseDouble(digitalLibraryRequestDTO.getDocToAmt());*/
			if( docfromAmt.compareTo(doctoAmt) > 0) {
				errors.add("docFromAmt",new ActionMessage("error.fromAmt.validation"));
			}
		}
		
		/*if(!requestDTO.getSystemName().isEmpty()) {
			Object sysNameObj = masterObj.getObjectByEntityNameAndEntryName("entryCode", requestDTO.getSystemName().trim(),
					"systemName",errors,"SYSTEM");
			if(!(sysNameObj instanceof ActionErrors)){
				digitalLibraryRequestDTO.setSystemName(requestDTO.getSystemName());
			}else {
				errors.add("systemName",new ActionMessage("error.invalid.value"));
			}
			}
		*/
		
		if(!requestDTO.getSystemName().isEmpty()) {
			String EntryCode = limitDAO.getEntryCode(requestDTO.getSystemName(),"SYSTEM");
			if(null != EntryCode) {
				digitalLibraryRequestDTO.setSystemName(EntryCode);
			}else {
				errors.add("systemName",new ActionMessage("error.invalid.value"));
			}
		}
		
		//digitalLibraryRequestDTO.setSystemName(requestDTO.getSystemName());
		if(null != digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
		Calendar date1 = Calendar.getInstance();
		date1.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocRecvFromDate()));
		Calendar date2 = Calendar.getInstance();
		date2.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocRecvToDate()));
		if(date1.after(date2)) {
			errors.add("docRecvFromDate",new ActionMessage("error.fromdate.validation"));
		}
		
		}
		
		if(null != digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
			Calendar date1 = Calendar.getInstance();
			date1.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocTagFromDate()));
			Calendar date2 = Calendar.getInstance();
			date2.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocTagToDate()));
			if(date1.after(date2)) {
				errors.add("docTagFromDate",new ActionMessage("error.fromdate.validation"));
			}
			
			}
		
		
		if(null != digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
			Calendar date1 = Calendar.getInstance();
			date1.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocFromDate()));
			Calendar date2 = Calendar.getInstance();
			date2.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocToDate()));
			if(date1.after(date2)) {
				errors.add("docFromDate",new ActionMessage("error.fromdate.validation"));
			}
			
			}
		System.out.println("Checked for getDocCode:"+requestDTO.getDocCode());
		if(!requestDTO.getDocCode().isEmpty()) {
			if(limitDAO.getCheckDocumentCode(requestDTO.getDocCode())) {
				digitalLibraryRequestDTO.setDocCode(requestDTO.getDocCode());
				}else {
					errors.add("docCode",new ActionMessage("error.invalid.value"));
				}
			}
		
		
		
		
		digitalLibraryRequestDTO.setErrors(errors);
		return digitalLibraryRequestDTO;
	}
	
	public DigitalLibraryRequestDTO getDigitalLibraryRequestDTOWithActualValuesForV2(DigitalLibraryRequestDTO requestDTO) {

		DigitalLibraryRequestDTO digitalLibraryRequestDTO =new DigitalLibraryRequestDTO();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this,
				"Inside getDigitalLibraryRequestDTOWithActualValues of digitalLibraryDetailsDTOMapper================:::::");
		System.out.println("Inside getDigitalLibraryRequestDTOWithActualValues of digitalLibraryDetailsDTOMapper================:::::");
		SimpleDateFormat pDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		String errorCode = null;
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		ILimitDAO limitDAO = LimitDAOFactory.getDAO();
		System.out.println("requestDTO:::::"+requestDTO);
		if(!requestDTO.getPartyId().isEmpty()) {
		if(limitDAO.getPartyId(requestDTO.getPartyId())) {
		digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
		}else {
			errors.add("partyId",new ActionMessage("error.invalid.value"));
		}
		}
		System.out.println("Checked for party Id:"+requestDTO.getPartyId());		
		
		if(!requestDTO.getSystemId().isEmpty()) {
		if(limitDAO.getSystemName(requestDTO.getSystemId())){
			digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
		}else {
			errors.add("systemId",new ActionMessage("error.invalid.value"));
		}
		}
		
		if(!requestDTO.getEmployeeCodeRM().isEmpty()) {
			if(limitDAO.getEmployeeCodeRM(requestDTO.getEmployeeCodeRM())){
				digitalLibraryRequestDTO.setEmployeeCodeRM(requestDTO.getEmployeeCodeRM());
			}else {
				errors.add("rmEmpId",new ActionMessage("error.invalid.value"));
			}
			}
		
		if(!requestDTO.getPanNo().isEmpty()) {
		if(limitDAO.getPanNo(requestDTO.getPanNo())) {
		digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
		}else {
			errors.add("panNo",new ActionMessage("error.invalid.value"));
		}
		}
		
		if(!requestDTO.getPartyName().isEmpty()) {
		if(limitDAO.getPartyName(requestDTO.getPartyName())) {
			digitalLibraryRequestDTO.setPartyName(requestDTO.getPartyName());
			}else {
				errors.add("partyName",new ActionMessage("error.invalid.value"));
			}
		}
		
		
		if(null !=digitalLibraryRequestDTO.getPartyName() && null !=digitalLibraryRequestDTO.getPartyId()) {
			if(limitDAO.getPartyNameAndPartyID(requestDTO.getPartyName(),requestDTO.getPartyId())) {
				digitalLibraryRequestDTO.setPartyName(requestDTO.getPartyName());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				}else {
					errors.add("partyName",new ActionMessage("error.invalidPartyIdPartyName.value"));
					errors.add("partyId",new ActionMessage("error.invalidPartyIdPartyName.value"));
				}
			}
		
		if(null !=digitalLibraryRequestDTO.getPartyName() && null !=digitalLibraryRequestDTO.getSystemId()) {
			if(limitDAO.getPartyNameAndSystemID(requestDTO.getPartyName(),requestDTO.getSystemId())) {
				digitalLibraryRequestDTO.setPartyName(requestDTO.getPartyName());
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				}else {
					errors.add("partyName",new ActionMessage("error.invalidSystemIdPartyName.value"));
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPartyName.value"));
				}
			}
		
		if(null !=digitalLibraryRequestDTO.getPanNo() && null !=digitalLibraryRequestDTO.getPartyId()) {
			if(limitDAO.getPanNoAndPartyID(requestDTO.getPanNo(),requestDTO.getPartyId())) {
				digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				}else {
					errors.add("panNo",new ActionMessage("error.invalidPanNoPartyID.value"));
					errors.add("partyId",new ActionMessage("error.invalidPanNoPartyID.value"));
				}
			}
		
		
		if(null !=digitalLibraryRequestDTO.getSystemId() && null !=digitalLibraryRequestDTO.getPartyId()) {
			if(limitDAO.getSystemIDAndPartyID(requestDTO.getSystemId(),requestDTO.getPartyId())) {
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				}else {
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPartyId.value"));
					errors.add("partyId",new ActionMessage("error.invalidSystemIdPartyId.value"));
				}
			}
		
		if(null !=digitalLibraryRequestDTO.getSystemId() && null !=digitalLibraryRequestDTO.getPanNo()) {
			if(limitDAO.getPanNoAndSystemId(requestDTO.getPanNo(),requestDTO.getSystemId())) {
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
				}else {
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPanNo.value"));
					errors.add("panNo",new ActionMessage("error.invalidSystemIdPanNo.value"));
				}
			}
		
		
		if(null !=digitalLibraryRequestDTO.getSystemId() && null !=digitalLibraryRequestDTO.getPartyId() && null !=digitalLibraryRequestDTO.getPanNo()) {
			if(limitDAO.getSystemIDAndPartyIDAndPanNo(requestDTO.getSystemId(),requestDTO.getPartyId(),requestDTO.getPanNo())) {
				digitalLibraryRequestDTO.setSystemId(requestDTO.getSystemId());
				digitalLibraryRequestDTO.setPartyId(requestDTO.getPartyId());
				digitalLibraryRequestDTO.setPanNo(requestDTO.getPanNo());
				}else {
					errors.add("systemId",new ActionMessage("error.invalidSystemIdPartyIdPanNo.value"));
					errors.add("partyId",new ActionMessage("error.invalidSystemIdPartyIdPanNo.value"));
					errors.add("panNo",new ActionMessage("error.invalidSystemIdPartyIdPanNo.value"));
				}
			}
		
		
		
		
		/*if(!requestDTO.getDocType().isEmpty()) {
		Object docTypeObj = masterObj.getObjectByEntityNameAndCatEntry("entryCode", requestDTO.getDocType().trim(),
				"docType",errors,"IMG_UPLOAD_CATEGORY");
		if(!(docTypeObj instanceof ActionErrors)){
			digitalLibraryRequestDTO.setDocType(requestDTO.getDocType());
		}else {
			errors.add("docType",new ActionMessage("error.invalid.value"));
		}
		}*/
		
		
		if(!requestDTO.getDocType().isEmpty()) {
			String EntryCode = limitDAO.getEntryCode(requestDTO.getDocType().trim(),"IMG_UPLOAD_CATEGORY");
			if(null != EntryCode) {
				digitalLibraryRequestDTO.setDocType(EntryCode);
			}else {
				errors.add("docType",new ActionMessage("error.invalid.value"));
			}
		}
		
		
		try { 
			
			if(!requestDTO.getDocRecvFromDate().isEmpty()) {
		if (! (errorCode = Validator.checkDate(requestDTO.getDocRecvFromDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
			errors.add("docRecvFromDate", new ActionMessage("error.invalid.format"));
		 }	else {
			 String d1 = (String)requestDTO.getDocRecvFromDate();
				String[] d2 = d1.split("/");
				if(d2.length == 3) {
					if(d2[2].length() != 4) {
						errors.add("docRecvFromDate", new ActionMessage("error.invalid.format"));
					}else {
						
						relationshipDateFormat.parse(requestDTO.getDocRecvFromDate().toString().trim());
						digitalLibraryRequestDTO.setDocRecvFromDate(requestDTO.getDocRecvFromDate().trim());
						
				}
				}
		 }
	 }
	} catch (ParseException e) {
		errors.add("docRecvFromDate", new ActionMessage("error.invalid.format"));
	}
		
		
		
		try { 
			
			if(!requestDTO.getDocRecvToDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocRecvToDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docRecvToDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocRecvToDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docRecvToDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocRecvToDate().toString().trim());
							digitalLibraryRequestDTO.setDocRecvToDate(requestDTO.getDocRecvToDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docRecvToDate", new ActionMessage("error.invalid.format"));
		}
		
		
		try { 
			
			if(!requestDTO.getDocTagFromDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocTagFromDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docTagFromDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocTagFromDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docTagFromDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocTagFromDate().toString().trim());
							digitalLibraryRequestDTO.setDocTagFromDate(requestDTO.getDocTagFromDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docTagFromDate", new ActionMessage("error.invalid.format"));
		}
		
		
		try { 
			
			if(!requestDTO.getDocTagToDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocTagToDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docTagToDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocTagToDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docTagToDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocTagToDate().toString().trim());
							digitalLibraryRequestDTO.setDocTagToDate(requestDTO.getDocTagToDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docTagToDate", new ActionMessage("error.invalid.format"));
		}
		
		
		
		try { 
			
			if(!requestDTO.getDocFromDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocFromDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docFromDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocFromDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docFromDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocFromDate().toString().trim());
							digitalLibraryRequestDTO.setDocFromDate(requestDTO.getDocFromDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docFromDate", new ActionMessage("error.invalid.format"));
		}
		
		try { 
			
			if(!requestDTO.getDocToDate().isEmpty()) {
			if (! (errorCode = Validator.checkDate(requestDTO.getDocToDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
				errors.add("docToDate", new ActionMessage("error.invalid.format"));
			 }	else {
				 String d1 = (String)requestDTO.getDocToDate();
					String[] d2 = d1.split("/");
					if(d2.length == 3) {
						if(d2[2].length() != 4) {
							errors.add("docToDate", new ActionMessage("error.invalid.format"));
						}else {
							
							relationshipDateFormat.parse(requestDTO.getDocToDate().toString().trim());
							digitalLibraryRequestDTO.setDocToDate(requestDTO.getDocToDate().trim());
							
					}
					}
		 }
			}
		} catch (ParseException e) {
			errors.add("docToDate", new ActionMessage("error.invalid.format"));
		}
		
		
		
		
		
	
		if(!requestDTO.getDocFromAmt().isEmpty()) {
			boolean docAmtflag = true;
		if (!(errorCode =Validator.checkAmount(requestDTO.getDocFromAmt(), true,1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
				.equals(Validator.ERROR_NONE)) {
			errors.add("docFromAmt", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.NUMBER, errorCode), "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
			docAmtflag = false;
	 }else {
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(requestDTO.getDocFromAmt()));
			if( codeFlag == true) {
				errors.add("Locale.getDefault()", new ActionMessage("error.string.invalidCharacter"));
				docAmtflag = false;
			}
		}
		
       if(docAmtflag) {
    	digitalLibraryRequestDTO.setDocFromAmt(requestDTO.getDocFromAmt());
		}
		}
		
		
		if(!requestDTO.getDocToAmt().isEmpty()) {
			boolean docAmtflag = true;
		if (!(errorCode =Validator.checkAmount(requestDTO.getDocToAmt(), true,1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
				.equals(Validator.ERROR_NONE)) {
			errors.add("docToAmt", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.NUMBER, errorCode), "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
			docAmtflag = false;
	 }else {
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(requestDTO.getDocToAmt()));
			if( codeFlag == true) {
				errors.add("Locale.getDefault()", new ActionMessage("error.string.invalidCharacter"));
				docAmtflag = false;
			}
		}
		
       if(docAmtflag) {
    	digitalLibraryRequestDTO.setDocToAmt(requestDTO.getDocToAmt());
		}
		}
		
		if(null != digitalLibraryRequestDTO.getDocToAmt() && null != digitalLibraryRequestDTO.getDocFromAmt()) {
			BigDecimal docfromAmt =new BigDecimal("0");
			BigDecimal doctoAmt =new BigDecimal("0");
			 docfromAmt =new BigDecimal(digitalLibraryRequestDTO.getDocFromAmt());
			 doctoAmt =new BigDecimal(digitalLibraryRequestDTO.getDocToAmt());
			/*Double docfromAmt = Double.parseDouble(digitalLibraryRequestDTO.getDocFromAmt());
			Double doctoAmt = Double.parseDouble(digitalLibraryRequestDTO.getDocToAmt());*/
			if( docfromAmt.compareTo(doctoAmt) > 0) {
				errors.add("docFromAmt",new ActionMessage("error.fromAmt.validation"));
			}
		}
		
		/*if(!requestDTO.getSystemName().isEmpty()) {
			Object sysNameObj = masterObj.getObjectByEntityNameAndEntryName("entryCode", requestDTO.getSystemName().trim(),
					"systemName",errors,"SYSTEM");
			if(!(sysNameObj instanceof ActionErrors)){
				digitalLibraryRequestDTO.setSystemName(requestDTO.getSystemName());
			}else {
				errors.add("systemName",new ActionMessage("error.invalid.value"));
			}
			}
		*/
		
		if(!requestDTO.getSystemName().isEmpty()) {
			String EntryCode = limitDAO.getEntryCode(requestDTO.getSystemName(),"SYSTEM");
			if(null != EntryCode) {
				digitalLibraryRequestDTO.setSystemName(EntryCode);
			}else {
				errors.add("systemName",new ActionMessage("error.invalid.value"));
			}
		}
		
		//digitalLibraryRequestDTO.setSystemName(requestDTO.getSystemName());
		if(null != digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
		Calendar date1 = Calendar.getInstance();
		date1.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocRecvFromDate()));
		Calendar date2 = Calendar.getInstance();
		date2.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocRecvToDate()));
		if(date1.after(date2)) {
			errors.add("docRecvFromDate",new ActionMessage("error.fromdate.validation"));
		}
		
		}
		
		if(null != digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
			Calendar date1 = Calendar.getInstance();
			date1.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocTagFromDate()));
			Calendar date2 = Calendar.getInstance();
			date2.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocTagToDate()));
			if(date1.after(date2)) {
				errors.add("docTagFromDate",new ActionMessage("error.fromdate.validation"));
			}
			
			}
		
		
		if(null != digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
			Calendar date1 = Calendar.getInstance();
			date1.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocFromDate()));
			Calendar date2 = Calendar.getInstance();
			date2.setTime(DateUtil.convertDate(Locale.getDefault(), digitalLibraryRequestDTO.getDocToDate()));
			if(date1.after(date2)) {
				errors.add("docFromDate",new ActionMessage("error.fromdate.validation"));
			}
			
			}
		System.out.println("Checked for getDocCode:"+requestDTO.getDocCode());
		
		
		  if(!requestDTO.getDocCode().isEmpty()) { Set<String> values =
		  limitDAO.getCheckDocumentCodeForV2(requestDTO.getDocCode());
		  if(values.size()>0) {
		  
		  StringBuilder docCodeValue = new StringBuilder(""); for (String s :
		  values) { docCodeValue.append(s + ","); } String str =
		  docCodeValue.toString(); str = str.replaceAll(",$", "");
		  digitalLibraryRequestDTO.setDocCode(str); }else {
		  errors.add("docCode",new ActionMessage("error.invalid.value"));
		  
		  } }
		 
		
		
		  digitalLibraryRequestDTO.setDocCode(requestDTO.getDocCode());
		
		digitalLibraryRequestDTO.setErrors(errors);
		return digitalLibraryRequestDTO;
		
		
	}
	


}
