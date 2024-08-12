package com.integrosys.cms.app.ws.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.WebServiceStatusCode;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateRequestDTO;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateResponseDTO;
import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.ValidationErrorDetailsDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.manualinput.aa.AADetailHelper;

public class CAMExtensionDateServiceFacade {
	
	public static final String dateFormat = "dd/MMM/yyyy";
	
	private ILimitJdbc limitJdbc;

	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}

	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}
	
	@CLIMSWebServiceMethod
	public CAMExtensionDateResponseDTO updateCAMExtensionDate(CAMExtensionDateRequestDTO  request) throws CMSValidationFault, CMSFault {
		
//		validateMandatoryFields(request);
		String responseMsg = "";
		if(request.getPartyId().length() == 0) {
			responseMsg = "Party Id is mandatory";
			
			//list.add("partyId", "Party Id is mandatory");
		}
		
		if(request.getWsConsumer().length() == 0) {
			responseMsg = responseMsg +  " ,wsConsumer is mandatory";
//			list.add("wsConsumer", "wsConsumer is mandatory");
		}
		
		if(request.getCamExtensionDate().length() == 0) {
			responseMsg = responseMsg +" ,CAM Extension Date is mandatory";
//			list.add("camExtensionDate", "CAM Extension Date is mandatory");
		}
		
		
//		validateFieldLengthFromRequest(request);
		
		if(request.getPartyId().length() > 20) {
			responseMsg = responseMsg +" ,Party Id exceeded max expected length";
//			list.add("partyId", "Party Id exceeded max expected length");
		}
		
		if(request.getCamExtensionDate().length() > dateFormat.length()) {
			responseMsg = responseMsg +" ,CAM Extension Date exceeded max expected length";
//			list.add("camExtensionDate", "CAM Extension Date exceeded max expected length");
		}
		
		if(request.getWsConsumer().length() > 50) {
			responseMsg = responseMsg +" ,wsConsumer exceeded max expected length";
//			list.add("wsConsumer", "wsConsumer exceeded max expected length");
		}
		
//		validateData(request);
		
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			date = sdf.parse(request.getCamExtensionDate());
			String actualDate = sdf.format(date);
			if(!request.getCamExtensionDate().equals(actualDate)) {
//				list.add("camExtensionDate", "CAM Extension Date must be a valid date and in " + dateFormat + " format");
				responseMsg = responseMsg +" ,CAM Extension Date must be a valid date and in " + dateFormat + " format";
			}
		}catch(Exception ex) {
//			list.add("camExtensionDate", "CAM Extension Date must be a valid date and in " + dateFormat + " format");
			responseMsg = responseMsg +" ,CAM Extension Date must be a valid date and in " + dateFormat + " format";
		}
		
		
		String partyId = request.getPartyId();
		
		String status = getLimitJdbc().getPartyStatus(partyId);
		if(request.getPartyId().length() > 0) {
			if("INACTIVE".equals(status)){
				responseMsg = responseMsg +", Party is Inactive in system";
			//throw new CMSValidationFault("partyId", "Party is Inactive in system");
			}
			else if("NOTEXIST".equals(status)){
				responseMsg = responseMsg + ", Party does not exists in system";
			//throw new CMSValidationFault("partyId", "Party does not exists in system");
			}
		}
		
		String camId = getLimitJdbc().getCamByLlpLeId(partyId);
		
		if(camId == null) {
			
			responseMsg = responseMsg + " , CAM for party not exists";
			//throw new CMSValidationFault("partyId", "CAM for party not exists");
		}
		
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		
		ILimitProfileTrxValue camTrxVal = null;
		ICMSTrxResult trxResult = null;
		try {
			camTrxVal = proxy.getTrxLimitProfile(Long.parseLong(camId));
			if(camTrxVal != null){
				
				if("PENDING_CREATE".equals(camTrxVal.getStatus())
						||"PENDING_UPDATE".equals(camTrxVal.getStatus())
						||"PENDING_DELETE".equals(camTrxVal.getStatus())
						||"REJECTED".equals(camTrxVal.getStatus())
						||"DRAFT".equals(camTrxVal.getStatus()))
				{
					responseMsg = responseMsg + ", CAM is pending in draft or for approval/rejection/resubmit";
					//throw new CMSException("CAM is pending in draft or for approval/rejection/resubmit");
				}
				Date camExtensionDate = null;
				ILimitProfile actual = camTrxVal.getLimitProfile();
				try {
					camExtensionDate = new SimpleDateFormat(dateFormat).parse(request.getCamExtensionDate());
				} catch (ParseException e) {
					responseMsg = responseMsg + ", Invalid Cam Extension Date Fromat. Expected Date Format dd/MMM/yyyy.";
					//throw e;
				}
				
				if(camExtensionDate.compareTo(DateUtil.clearTime(actual.getApprovalDate())) < 0) {
					responseMsg = responseMsg + ", CAM Extension Date cannot be earlier than CAM Date";
					//throw new CMSValidationFault("camExtensionDate", "CAM Extension Date cannot be earlier than CAM Date");
				}
				
				if(camExtensionDate.compareTo(DateUtil.clearTime(actual.getNextAnnualReviewDate())) < 0) {
					responseMsg = responseMsg + ", CAM Extension Date cannot be earlier than Expiry Date.";
					//throw new CMSValidationFault("camExtensionDate", "CAM Extension Date cannot be earlier than Expiry Date");
				}
				
				if(!AADetailHelper.isFacilityOrLineActive(actual.getLimitProfileID())) {
					responseMsg = responseMsg + ", Facility is pending in draft or for approval/rejection/resubmit OR line is in pending status, awaiting the response from core." ; 
//					throw new CMSException("Facility is pending in draft or for approval/rejection/resubmit "
//							+ "OR line is in pending status, awaiting the response from core");
				}
				
				System.out.println("CAMExtensionDateServiceFacede =>responseMsg=>"+responseMsg);
				
				if("".equals(responseMsg)) {
				if(camExtensionDate.compareTo(DateUtil.clearTime(actual.getExtendedNextReviewDate())) != 0){
					OBTrxContext trxContext = new OBTrxContext();
					actual.setExtendedNextReviewDate(camExtensionDate);
					actual.setNoOfTimesExtended(actual.getNoOfTimesExtended() + 1);
					trxResult = proxy.submitCreateLimitProfile(trxContext, camTrxVal, (OBLimitProfile) actual);
					if(trxResult != null) {
						AADetailHelper.syncExtendedNextReviewDateToLine(actual.getLimitProfileID(), camExtensionDate);
					}
				}
				}
			}else {
				responseMsg = responseMsg + ", CAM for party not exists.";
//				throw new CMSValidationFault("partyId", "CAM for party not exists");
			}		
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Exception in CAMExtensionDateServiceFacede line 178 e=>"+e);
//			throw new CMSException(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Exception in CAMExtensionDateServiceFacede line 182 e=>"+e);
//			throw new CMSException(e.getMessage());
		} catch (LimitException e) {
			e.printStackTrace();
			System.out.println("Exception in CAMExtensionDateServiceFacede line 186 e=>"+e);
//			throw new CMSException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in CAMExtensionDateServiceFacede line 190 e=>"+e);
//			throw new CMSException(e.getMessage());
		}
		
		CAMExtensionDateResponseDTO response = new CAMExtensionDateResponseDTO();
		if(null != responseMsg && !"".equals(responseMsg)) {
			response.setDescription(responseMsg);
			response.setResponseStatus("FAIL");
		}else {
		response.setDescription(partyId);
		response.setResponseStatus("SUCCESS");
		}
		
		return response;
	}
	
	private void validateMandatoryFields(CAMExtensionDateRequestDTO  request) throws CMSValidationFault, CMSFault {
		ValidationErrorDetailsDTOList list = new ValidationErrorDetailsDTOList();
		
		if(request.getPartyId().length() == 0) {
			list.add("partyId", "Party Id is mandatory");
		}
		
		if(request.getWsConsumer().length() == 0) {
			list.add("wsConsumer", "wsConsumer is mandatory");
		}
		
		if(request.getCamExtensionDate().length() == 0) {
			list.add("camExtensionDate", "CAM Extension Date is mandatory");
		}
		
		list.throwErrors();
	}
	
	private void validateFieldLengthFromRequest(CAMExtensionDateRequestDTO  request) throws CMSValidationFault, CMSFault {
		ValidationErrorDetailsDTOList list = new ValidationErrorDetailsDTOList();
		
		if(request.getPartyId().length() > 50) {
			list.add("partyId", "Party Id exceeded max expected length");
		}
		
		if(request.getCamExtensionDate().length() > dateFormat.length()) {
			list.add("camExtensionDate", "CAM Extension Date exceeded max expected length");
		}
		
		if(request.getWsConsumer().length() > 50) {
			list.add("wsConsumer", "wsConsumer exceeded max expected length");
		}
		
		list.throwErrors();
	}
	
	private void validateData(CAMExtensionDateRequestDTO request) throws CMSValidationFault, CMSFault {
		ValidationErrorDetailsDTOList list = new ValidationErrorDetailsDTOList();
		
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			date = sdf.parse(request.getCamExtensionDate());
			String actualDate = sdf.format(date);
			if(!request.getCamExtensionDate().equals(actualDate)) {
				list.add("camExtensionDate", "CAM Extension Date must be a valid date and in " + dateFormat + " format");
			}
		}catch(Exception ex) {
			list.add("camExtensionDate", "CAM Extension Date must be a valid date and in " + dateFormat + " format");
		}
		
		list.throwErrors();
	}
	
	static class ValidationErrorDetailsDTOList {
		
		private List<ValidationErrorDetailsDTO> errors;
		
		public ValidationErrorDetailsDTOList() {
			errors = new ArrayList<ValidationErrorDetailsDTO>(); 
		}
		
		public void add(String field, String validationMessage) {
			ValidationErrorDetailsDTO error = new ValidationErrorDetailsDTO();
			error.setField(field);
			error.setValidationMessage(validationMessage);
			this.errors.add(error);
		}
		
		public void throwErrors() throws CMSValidationFault{
			if (this.errors.size()> 0 ) {
				ErrorDetailDTO error = new ErrorDetailDTO();
				error.setErrorCode(WebServiceStatusCode.VALIDATION_ERROR.name());
				error.setErrorDescription(WebServiceStatusCode.VALIDATION_ERROR.message);
				error.setValidationErrorDetails(this.errors);
				throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), error);
			}
		}
	}
	
}