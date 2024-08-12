/**
 * 
 */
package com.integrosys.cms.app.ws.aop;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;
import com.integrosys.cms.app.ws.dto.AADetailRequestDTO;
import com.integrosys.cms.app.ws.dto.AADetailResponseDTO;
import com.integrosys.cms.app.ws.dto.AdhocFacilityRequestDTO;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsResponseDTO;
import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.FacilityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACResponseDTO;
import com.integrosys.cms.app.ws.dto.InterfaceHelper;
import com.integrosys.cms.app.ws.dto.InterfaceLoggingDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsRequestDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsResponseDTO;
import com.integrosys.cms.app.ws.dto.RequestDTO;
import com.integrosys.cms.app.ws.dto.ResponseDTO;
import com.integrosys.cms.app.ws.dto.SecurityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.SecurityRequestDTO;
import com.integrosys.cms.app.ws.facade.CAMExtensionDateServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.app.ws.jax.common.ExceptionHandler;

/**
 * @author bhushan.malankar
 * @date 14/08/2014
 * This class is use as handler for auditing request,response and error in CLIMS Web Services.
 * Spring AOP is used to handle all web services request responses.
 * 
 * @see com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod
 * 
 */
@Aspect
public class AuditHandler {

	@Autowired
	private InterfaceHelper interfaceHelper;

	public void setInterfaceHelper(InterfaceHelper interfaceHelper) {
		this.interfaceHelper = interfaceHelper;
	}

	/**
	 * 
	 */
	public AuditHandler() {

	}

	/**
	 * 
	 * @param pjp ProceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "@annotation(com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod)")
	public Object logEntry(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		RequestDTO request = null;
		RequestDTO requestCloned = null;
		ResponseDTO response = null;
		ErrorDetailDTO error = null;
		InterfaceLoggingDTO loggingDTO = new InterfaceLoggingDTO(); 
		loggingDTO.setRequestDate(new Date());
		loggingDTO.setInterfaceName(pjp.getSignature().getName()+"["+pjp.getSignature().getDeclaringTypeName()+"]");
		Object[] arguments = pjp.getArgs();
		for (int i = 0; i < arguments.length; i++) {
			Object obj = arguments[i];
			if (obj instanceof RequestDTO) {
			
				//Start : To store web service request values in database and required for Reconciliation report
				if(obj instanceof PartyDetailsRequestDTO){
					loggingDTO.setPartyName(((PartyDetailsRequestDTO)obj).getPartyName());
					loggingDTO.setPartyId(((PartyDetailsRequestDTO)obj).getClimsPartyId());
					loggingDTO.setParticulars(((PartyDetailsRequestDTO)obj).getPartyName());
					loggingDTO.setI_name("Party Details");
					if("savePartyDetails".equalsIgnoreCase(pjp.getSignature().getName())){
						loggingDTO.setOperation("Save");
					}else{
						loggingDTO.setOperation("Update");
					}
				}else if(obj instanceof AADetailRequestDTO){
					loggingDTO.setPartyId(((AADetailRequestDTO)obj).getPartyId());
					loggingDTO.setCamNo(((AADetailRequestDTO)obj).getCamNumber());
					loggingDTO.setParticulars(((AADetailRequestDTO)obj).getCamNumber());
					loggingDTO.setI_name("CAM Details");
					if("addCAMDetails".equalsIgnoreCase(pjp.getSignature().getName())){
						loggingDTO.setOperation("Save");
					}else{
						loggingDTO.setOperation("Update");
					}
				}else if(obj instanceof FacilityDetailRequestDTO){
					loggingDTO.setCamId(((FacilityDetailRequestDTO)obj).getCamId());
					loggingDTO.setFacilityId(((FacilityDetailRequestDTO)obj).getClimsFacilityId());
					
					loggingDTO.setI_name("Facility Details");
					if("addFacilityDetails".equalsIgnoreCase(pjp.getSignature().getName())){
						loggingDTO.setOperation("Save");
					}else{
						loggingDTO.setOperation("Update");
					}
					
					loggingDTO.setParticulars(((FacilityDetailRequestDTO)obj).getFacilityCategoryId());
					
					if( ((FacilityDetailRequestDTO)obj).getSecurityList() !=null && ((FacilityDetailRequestDTO)obj).getSecurityList().size()>0){
						List<SecurityDetailRequestDTO> securityList = ((FacilityDetailRequestDTO)obj).getSecurityList();
						
						StringBuffer sb = new StringBuffer();
						StringBuffer secSubTypeSB = new StringBuffer();
						CommonCodeEntryDAO commonCodeEntryDAO = new CommonCodeEntryDAO();
						for(SecurityDetailRequestDTO secDTO : securityList){
							sb.append(secDTO.getCpsSecurityId()+",");
							if(secDTO.getSecuritySubType()!=null && !secDTO.getSecuritySubType().isEmpty()){
								secSubTypeSB.append(commonCodeEntryDAO.getEntryNameByEntrycodeAndCCode(secDTO.getSecuritySubType(),"54")+",");
							}
						}
						if(sb!=null && sb.length()>0){
							if(sb.toString().contains(",")){
								loggingDTO.setCpsSecId(sb.toString().substring(0,sb.lastIndexOf(",")));
							}
						}
						if(secSubTypeSB!=null && secSubTypeSB.length()>0){
							if(secSubTypeSB.toString().contains(",")){
								loggingDTO.setSecSubType(secSubTypeSB.toString().substring(0,secSubTypeSB.lastIndexOf(",")));
							}
						}
					}
					
				}else if(obj instanceof FacilityReadRequestDTO){
					loggingDTO.setFacilityId(((FacilityReadRequestDTO)obj).getFacilityID());
				}else if(obj instanceof SecurityRequestDTO){
					loggingDTO.setSecurityId(((SecurityRequestDTO)obj).getSourceSecurityId());
				}else if(obj instanceof DocumentsRequestDTO){
					loggingDTO.setPartyId(((DocumentsRequestDTO)obj).getPartyId());
					
					loggingDTO.setI_name("Documents Details");
					if("addDocumentsDetails".equalsIgnoreCase(pjp.getSignature().getName())){
						loggingDTO.setOperation("Save");
					}
					
					if( ((DocumentsRequestDTO)obj).getDocumentList() !=null && ((DocumentsRequestDTO)obj).getDocumentList().size()>0){
						List<DocumentsDetailRequestDTO> docDetList = ((DocumentsRequestDTO)obj).getDocumentList();
						
						StringBuffer sb = new StringBuffer();
						StringBuilder sbDocCode = new StringBuilder();
						
						for(DocumentsDetailRequestDTO docDetDTO : docDetList){
							sb.append(docDetDTO.getCpsDocumentId()+",");
							sbDocCode.append(docDetDTO.getDocumentCode()+",");
						}
						if(sb!=null && sb.length()>0){
							if(sb.toString().contains(",")){
								loggingDTO.setCpsDocId(sb.toString().substring(0,sb.lastIndexOf(",")));
							}
						}
						if(sbDocCode!=null && sbDocCode.length()>0){
							if(sbDocCode.toString().contains(",")){
								loggingDTO.setParticulars(sbDocCode.toString().substring(0,sbDocCode.lastIndexOf(",")));
							}
						}
					}
				}else if(obj instanceof DocumentsReadRequestDTO){
					loggingDTO.setPartyId(((DocumentsReadRequestDTO)obj).getPartyId());
				}else if(obj instanceof DiscrepancyRequestDTO){
					
					loggingDTO.setPartyId(((DiscrepancyRequestDTO)obj).getPartyId());

					loggingDTO.setI_name("Discrepancy Details");
					if("addDiscrepancyDetails".equalsIgnoreCase(pjp.getSignature().getName())){
						loggingDTO.setOperation("Save");
					}
					
					if( ((DiscrepancyRequestDTO)obj).getDiscrepancyList() !=null && ((DiscrepancyRequestDTO)obj).getDiscrepancyList().size()>0){
						List<DiscrepancyDetailRequestDTO> discrepancyList = ((DiscrepancyRequestDTO)obj).getDiscrepancyList();
						
						StringBuffer sb = new StringBuffer();
						StringBuilder discrpRemarksSB = new StringBuilder();
						
						for(DiscrepancyDetailRequestDTO discrepancyDetDTO : discrepancyList){
							sb.append(discrepancyDetDTO.getCpsDiscrepancyId()+",");
							discrpRemarksSB.append(discrepancyDetDTO.getRemarks()+",");
						}
						if(sb!=null && sb.length()>0){
							if(sb.toString().contains(",")){
								loggingDTO.setCpsDiscrId(sb.toString().substring(0,sb.lastIndexOf(",")));
							}
						}
						if(discrpRemarksSB!=null && discrpRemarksSB.length()>0){
							if(discrpRemarksSB.toString().contains(",")){
								loggingDTO.setParticulars(discrpRemarksSB.toString().substring(0,discrpRemarksSB.lastIndexOf(",")));
							}
						}
					}
				}else if(obj instanceof DiscrepancyReadRequestDTO){
					loggingDTO.setPartyId(((DiscrepancyReadRequestDTO)obj).getPartyId());
				}else if(obj instanceof FacilitySCODDetailRequestDTO){
					loggingDTO.setCamId(((FacilitySCODDetailRequestDTO)obj).getCamId());
					loggingDTO.setFacilityId(((FacilitySCODDetailRequestDTO)obj).getClimsFacilityId());
					loggingDTO.setI_name("Facility Details");
				}else if(obj instanceof AdhocFacilityRequestDTO){
					loggingDTO.setCamId(((AdhocFacilityRequestDTO)obj).getCamId());
					loggingDTO.setFacilityId(((AdhocFacilityRequestDTO)obj).getClimsFacilityId());
					loggingDTO.setI_name("Facility Details");
				}else if(obj instanceof CAMExtensionDateRequestDTO) {
					CAMExtensionDateRequestDTO cObj = (CAMExtensionDateRequestDTO) obj;
					cObj.trim();
					if(cObj.getPartyId().length() > 50) {
						loggingDTO.setPartyId(cObj.getPartyId().substring(0, 48) + "..");
					}else
						loggingDTO.setPartyId(cObj.getPartyId());
				
					if(cObj.getCamExtensionDate().length() > CAMExtensionDateServiceFacade.dateFormat.length()) {
						loggingDTO.setCamExtDate(cObj.getCamExtensionDate().substring(0, CAMExtensionDateServiceFacade.dateFormat.length() - 2) + "..");
					}else
						loggingDTO.setCamExtDate(cObj.getCamExtensionDate());
					
					loggingDTO.setI_name("CAM Extension Date");
					loggingDTO.setOperation("Update");
				}
				else if(obj instanceof ISACRequestDTO) {
					ISACRequestDTO cObj = (ISACRequestDTO) obj;
					
					if(null  != cObj.getRefNumber()) {
						loggingDTO.setIsacReferenceNo(cObj.getRefNumber());
					}else
						loggingDTO.setIsacReferenceNo("");
					
					loggingDTO.setI_name("ISAC");
					loggingDTO.setIsacmakerid(cObj.getMaker_id());
					loggingDTO.setIsaccheckerid(cObj.getChecker_id());
					loggingDTO.setOperation(cObj.getEvent());
				}
				
				
				//End
				request = (RequestDTO) obj;
//				loggingDTO.setRequestDto(request);
				loggingDTO.setWsClient(request.getWsConsumer());
				try{
					requestCloned = (RequestDTO)request.clone();
				}catch (CloneNotSupportedException ce) {
					ce.printStackTrace();
				}
				loggingDTO.setRequestDto(requestCloned);
				break;
			}
		}
		try {
			// calling actual method
			retVal = pjp.proceed();
			
			if (retVal instanceof ResponseDTO) {
				response = (ResponseDTO) retVal;
				loggingDTO.setResponseDTO(response);
				if(retVal instanceof PartyDetailsResponseDTO){
					loggingDTO.setPartyId(((PartyDetailsResponseDTO)retVal).getPartyId());
				}else if(retVal instanceof AADetailResponseDTO){
					loggingDTO.setCamId(((AADetailResponseDTO)retVal).getCamId());
				}else if(retVal instanceof FacilityResponseDTO){
					loggingDTO.setFacilityId(((FacilityResponseDTO)retVal).getFacilityId());
					List<SecurityDetailResponseDTO> securityResponseList = ((FacilityResponseDTO)retVal).getSecurityResponseList();
					if(securityResponseList!=null && securityResponseList.size()>0){
						StringBuilder climsSecIdSB = new StringBuilder();
						for(SecurityDetailResponseDTO secResDTO : securityResponseList){
							climsSecIdSB.append(secResDTO.getSecurityId()+",");
							
							if(climsSecIdSB!=null && climsSecIdSB.length()>0){
								if(climsSecIdSB.toString().contains(",")){
									loggingDTO.setSecurityId(climsSecIdSB.toString().substring(0,climsSecIdSB.lastIndexOf(",")));
								}
							}
						}
					}
				}else if(retVal instanceof DocumentsResponseDTO){
					
					 List<DocumentsDetailResponseDTO> documentResponseList = ((DocumentsResponseDTO)retVal).getDocumentResponseList();
					if(documentResponseList!=null && documentResponseList.size()>0){
						StringBuilder climsDocIdSB = new StringBuilder();
						for(DocumentsDetailResponseDTO docDetDTO : documentResponseList){
							climsDocIdSB.append(docDetDTO.getDocumentItemId()+",");
							
							if(climsDocIdSB!=null && climsDocIdSB.length()>0){
								if(climsDocIdSB.toString().contains(",")){
									loggingDTO.setClimsDocId(climsDocIdSB.toString().substring(0,climsDocIdSB.lastIndexOf(",")));
								}
							}
						}
					}
				}else if(retVal instanceof DiscrepancyResponseDTO){
					
					 List<DiscrepancyDetailResponseDTO> discrepancyResponseList = ((DiscrepancyResponseDTO)retVal).getDiscrepancyResponseList();
					if(discrepancyResponseList!=null && discrepancyResponseList.size()>0){
						StringBuilder climsDiscrepancyIdSB = new StringBuilder();
						for(DiscrepancyDetailResponseDTO discDetDTO : discrepancyResponseList){
							climsDiscrepancyIdSB.append(discDetDTO.getDiscrepancyId()+",");
							
							if(climsDiscrepancyIdSB!=null && climsDiscrepancyIdSB.length()>0){
								if(climsDiscrepancyIdSB.toString().contains(",")){
									loggingDTO.setClimsDiscrId(climsDiscrepancyIdSB.toString().substring(0,climsDiscrepancyIdSB.lastIndexOf(",")));
								}
							}
						}
					}
				}
				
				else if(retVal instanceof ISACResponseDTO) {
					ISACResponseDTO cObj = (ISACResponseDTO) retVal;
					
					
					if(null  != cObj.getErrorCode()) {
						loggingDTO.setIsacErrorCode(cObj.getErrorCode());
					}else
						loggingDTO.setIsacErrorCode("");
					
					if(null  != cObj.getErrorMessage()) {
						loggingDTO.setIsacErrorMessage(cObj.getErrorMessage());
					}else
						loggingDTO.setIsacErrorMessage("");
				}
				
				loggingDTO.setResponseDate(new Date());
			}

		} catch (Throwable ex) {
		   // Capturing Error response date time
			loggingDTO.setResponseDate(new Date());
			if (ex instanceof CMSValidationFault) {
				error = ((CMSValidationFault)ex).getFaultInfo();
			}else if (ex instanceof CMSFault) {
				error = ((CMSFault)ex).getFaultInfo();
			}else {
				error = ExceptionHandler.getServerErrorDetailDTO(ex);
			}
			loggingDTO.setErrorDetailDTO( error);
			throw ex;

		} finally {
			try {
				interfaceHelper.interfaceLoggingActivity(loggingDTO);
			} catch (Throwable logError) {
				logError.getStackTrace();
			}
		}

		return retVal;
	}

}
