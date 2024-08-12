/**
 * 
 */
package com.integrosys.cms.app.ws.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.AADetailRequestDTO;
import com.integrosys.cms.app.ws.dto.AADetailResponseDTO;
import com.integrosys.cms.app.ws.dto.CAMFacilityNameRequestDTO;
import com.integrosys.cms.app.ws.dto.CAMMonitoringResponsibilityRequestDTO;
import com.integrosys.cms.app.ws.dto.CamDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.OtherCovenantDetailsListRequestDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;
import com.integrosys.cms.ui.manualinput.aa.AADetailValidator;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import java.util.List;
import java.util.ArrayList;
/**
 * @author bhushan.malankar
 *
 */

public class CAMServiceFacade {

	@Autowired
	private CamDetailsDTOMapper camDetailsDTOMapper;

	private ILimitJdbc limitJdbc;

	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}

	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}

	public void setCamDetailsDTOMapper(CamDetailsDTOMapper camDetailsDTOMapper) {
		this.camDetailsDTOMapper = camDetailsDTOMapper;
	}

	@CLIMSWebServiceMethod
	public AADetailResponseDTO  addCAMDetails(AADetailRequestDTO  camRequest) throws CMSValidationFault, CMSFault{
		DefaultLogger.debug(this, "inside addCAMDetails");
		
		String partyId = "";
		if(camRequest.getPartyId()!=null && !camRequest.getPartyId().trim().isEmpty()){
			partyId = camRequest.getPartyId().trim();
		}
			
		String status = getLimitJdbc().getPartyStatus(partyId);
		
		if(status!=null && status.equals("INACTIVE")){
			throw new CMSException("Party is Inactive in system");
		}
		else if(status!=null && status.equals("NOTEXIST")){
			throw new CMSException("Party does not exists in system");
		}
		
		if(camRequest.getWsConsumer()==null || camRequest.getWsConsumer().trim().isEmpty()){
			throw new CMSException("wsConsumer is mandatory");
		}
		 
		String camId = getLimitJdbc().getCamByLlpLeId(partyId);
		AADetailRequestDTO aaDetailRequestDTO = camDetailsDTOMapper.getRequestDTOWithActualValues(camRequest,camId);
		ActionErrors cpsIdErrors = aaDetailRequestDTO.getErrors();
	
		
		HashMap map = new HashMap();
		if(cpsIdErrors.size()>0){
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.CAM);
		}
		AADetailForm form = camDetailsDTOMapper.getFormFromDTO(camRequest);
		ActionErrors errorList = AADetailValidator.validateInput(form, Locale.getDefault()); 
		HashMap newMap = new HashMap();
		newMap.put("1", errorList);
		ValidationUtility.handleError(newMap,CLIMSWebService.CAM);
		AADetailResponseDTO CAMresponse = new AADetailResponseDTO();
		{
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			OBTrxContext trxContext = new OBTrxContext();
			ILimitProfileTrxValue limitProfileTrxVal =null;
			OBLimitProfile actualLimitProfile = null;
			ICMSTrxResult trxResult = null;
			String stagingRefId=null;
			String actualRefId=null;
		
			try {
				/*if(camId!=null && !camId.equals("")){
				limitProfileTrxVal = proxy.getTrxLimitProfile(Long.parseLong(camId));
				if(limitProfileTrxVal!=null && ((limitProfileTrxVal.getStatus().equals("PENDING_CREATE"))
						||(limitProfileTrxVal.getStatus().equals("PENDING_UPDATE"))
						||(limitProfileTrxVal.getStatus().equals("PENDING_DELETE"))
						||(limitProfileTrxVal.getStatus().equals("REJECTED"))||(limitProfileTrxVal.getStatus().equals("DRAFT"))))
				{
					throw new CMSValidationFault("transactionStatus","Unable to update due to invalid transaction Status :"+limitProfileTrxVal.getStatus());
				}
				}
				
				if(limitProfileTrxVal!=null){
					actualLimitProfile =(OBLimitProfile)limitProfileTrxVal.getLimitProfile();
					 if(actualLimitProfile.getCamType()!=null && actualLimitProfile.getCamType().equals("Interim")){
						if(form.getCamType()!=null && form.getCamType().equals("Initial")){
							throw new CMSValidationFault("camType","Invalid CAM Type");
							
						}
					}
					else if(actualLimitProfile.getCamType()!=null && actualLimitProfile.getCamType().equals("Annual")){
						if(form.getCamType()!=null && !form.getCamType().equals("Annual")){
							throw new CMSValidationFault("camType","Invalid CAM Type");
								
						}
					}
					actualLimitProfile = (OBLimitProfile)camDetailsDTOMapper.getActualFromDTO(camRequest,actualLimitProfile);
				}else{*/
					 actualLimitProfile = (OBLimitProfile)camDetailsDTOMapper.getActualFromDTO(camRequest,null);
				//}
				
				trxResult = proxy.submitCreateLimitProfile(trxContext, limitProfileTrxVal, actualLimitProfile);
				String id = "";
				if(trxResult != null && trxResult.getTrxValue() != null){
					limitProfileTrxVal = (ILimitProfileTrxValue)trxResult.getTrxValue();
					if(limitProfileTrxVal!= null){
						stagingRefId=limitProfileTrxVal.getStagingReferenceID();
						actualRefId=limitProfileTrxVal.getReferenceID();
						if(limitProfileTrxVal.getLimitProfile()!= null){
							id = String.valueOf(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
							CAMresponse.setCamId(id);
							CAMresponse.setResponseStatus("CAM_CREATED_SUCCESSFULLY");
							DefaultLogger.debug(this, "Updating Sanctioned Amount Flag from addCAMDetails for partyId :"+partyId );
							CustomerDAOFactory.getDAO().updateSanctionedAmountUpdatedFlag(partyId, ICMSConstant.YES);
							ICMSCustomer cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(partyId,null);
							try {
								ClimesToECBFHelper.sendRequest(cust);
							} catch (Exception e) {
								e.printStackTrace();
								DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
							}

						}
						else{
							DefaultLogger.error(this, "no value found in limitProfileTrxVal.getLimitProfileID(): ");
							throw new CMSException("Server side error");
						}
					}
					else{
						DefaultLogger.error(this, "no value found in limitProfileTrxVal: "+limitProfileTrxVal);
						throw new CMSException("Server side error");
					}
				}else{
					DefaultLogger.error(this, "no value found in trxResult: "+trxResult);
					throw new CMSException("Server side error");
				}

			} catch (LimitException e) {	
				if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_DUPLICATE_AA_NUM)) {
					throw new CMSException("Duplicate CAM Number");
				}
				else{
				throw new CMSException(e.getMessage());
				}
			}
			
			//Cam Online Format Start
			
			 IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
			 ILimitDAO dao = LimitDAOFactory.getDAO();
			 
			 if(camRequest.getOtherCovenantDetailsList()!=null && !camRequest.getOtherCovenantDetailsList().isEmpty()){
				for(OtherCovenantDetailsListRequestDTO otherCovenantDetailsListRequestDTO: camRequest.getOtherCovenantDetailsList()){

					OBOtherCovenant obOtherCovenant=new OBOtherCovenant();
					List <OBOtherCovenant>otherCovenantDetailsList=new ArrayList<OBOtherCovenant>();

					obOtherCovenant.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsStagingIdFromSeq()));
					obOtherCovenant.setCustRef(camRequest.getPartyId());
					obOtherCovenant.setCovenantCategory(otherCovenantDetailsListRequestDTO.getCovenantCategory());			 
					obOtherCovenant.setAdvised(otherCovenantDetailsListRequestDTO.getAdvised());
					obOtherCovenant.setCompiled(otherCovenantDetailsListRequestDTO.getCompiled());
					obOtherCovenant.setCovenantCondition(otherCovenantDetailsListRequestDTO.getCovenantCondition());
					obOtherCovenant.setCovenantDescription(otherCovenantDetailsListRequestDTO.getCovenantDescription());
					obOtherCovenant.setCovenantType(otherCovenantDetailsListRequestDTO.getCovenantType());
					obOtherCovenant.setRemarks(otherCovenantDetailsListRequestDTO.getRemarks());
					obOtherCovenant.setTargetDate(otherCovenantDetailsListRequestDTO.getTargetDate());

					List<String> monListStrList = new ArrayList<String>();
					if(otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()!=null && !otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList().isEmpty()){
						for(CAMMonitoringResponsibilityRequestDTO cmrDTO : otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()) {
							monListStrList.add(cmrDTO.getMonitoringResponsibiltyValue());
						}
					}	
					
					ArrayList monRespList = new ArrayList();
					if(null != monListStrList){
						for (String item : monListStrList) {
							 String[] MRArr1 =item.split("-");
							 monRespList.add(MRArr1[0]);					 
						}	
					}

					List<String> facilityNameStrList = new ArrayList<String>();
					if(otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()!=null){
						for(CAMFacilityNameRequestDTO cfnDTO : otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()) {
							facilityNameStrList.add(cfnDTO.getFacilityNameValue());
						}
					}	
					
					ArrayList facNameList = new ArrayList();		 	 
					if(null != facilityNameStrList){
						for (String item : facilityNameStrList) {
							Pattern pattern = Pattern.compile("^(.+)-\\1");
							Matcher matcher = pattern.matcher(item);
							while (matcher.find()) {
								facNameList.add(matcher.group(1));
							}							 
						}
					}

					String seqNo = null;
					dao = LimitDAOFactory.getDAO();
					try {
						seqNo = dao.getSeqNoForOtherCovenant();
					}catch (Exception e) {
						e.printStackTrace();
					}
					obOtherCovenant.setPreviousStagingId(seqNo);
					otherCovenantDetailsList.add(obOtherCovenant);

					OBOtherCovenant ob;

					if(otherCovenantDetailsList !=null)
					{	
						for(int i=0;i<otherCovenantDetailsList.size();i++) 
						{	

							ob=(OBOtherCovenant)otherCovenantDetailsList.get(i);
							try
							{
								ob.setStagingRefid(stagingRefId);
								ob.setIsUpdate("N");
								ob.setStatus("ACTIVE");
								othercovenantdetailsdaoimpl.insertOtherCovenantDetailsStage(ob);
								ob.setStagingRefid(actualRefId);
								othercovenantdetailsdaoimpl.insertOtherCovenantDetailsActual(ob);

								for(int j=0;j<monRespList.size();j++) {
									OBOtherCovenant ob1 = new OBOtherCovenant();
									ob1.setMonitoringResponsibiltyValue((String)monRespList.get(j));
									ob1.setMonitoringResponsibilityList1((String)monRespList.get(j));
									ob1.setStagingRefid(ob.getOtherCovenantId()+"");
									ob1.setCustRef(ob.getCustRef());
									if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
									{	
										ob1.setStatus("INACTIVE");
									}
									else
									{
										ob1.setStatus("ACTIVE");
									}
									if(ob1.getMonitoringResponsibiltyValue() != null && !ob1.getMonitoringResponsibiltyValue().equals("")) {
										othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);
										othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
									}
								}	

								for(int n=0;n<facNameList.size();n++) {
									OBOtherCovenant ob1 = new OBOtherCovenant();
									ob1.setFacilityNameValue((String)facNameList.get(n));
									ob1.setStagingRefid(ob.getOtherCovenantId()+"");
									ob1.setCustRef(ob.getCustRef());
									ob1.setPreviousStagingId(ob.getPreviousStagingId());
									if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
									{	
										ob1.setStatus("INACTIVE");
									}
									else
									{
										ob1.setStatus("ACTIVE");
									}
									if(ob1.getFacilityNameValue() != null && !ob1.getFacilityNameValue().equals("")) {
										othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);	
										othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
									}
								}
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}						
						}
					}
					//Cam Online Format END
				}
			}
			return CAMresponse;
		}
	}
	
	@CLIMSWebServiceMethod
	public AADetailResponseDTO  updateCAMDetails(AADetailRequestDTO  camRequest) throws CMSValidationFault, CMSFault{
		DefaultLogger.debug(this, "inside updateCAMDetails");
		
		String partyId = "";
		if(camRequest.getPartyId()!=null && !camRequest.getPartyId().trim().isEmpty()){
			partyId = camRequest.getPartyId().trim();
		}
		
		String status = getLimitJdbc().getPartyStatus(partyId);
		
		if(status!=null && status.equals("INACTIVE")){
			throw new CMSException("Party is Inactive in system");
		}
		else if(status!=null && status.equals("NOTEXIST")){
			throw new CMSException("Party does not exists in system");
		}
		
		if(camRequest.getWsConsumer()==null || camRequest.getWsConsumer().trim().isEmpty()){
			throw new CMSException("wsConsumer is mandatory");
		}
		
		String camId = getLimitJdbc().getCamByLlpLeId(partyId);
		AADetailRequestDTO aaDetailRequestDTO = camDetailsDTOMapper.getRequestDTOWithActualValues(camRequest,camId);
		ActionErrors cpsIdErrors = aaDetailRequestDTO.getErrors();
		
		
		HashMap map = new HashMap();
		if(cpsIdErrors.size()>0){
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.CAM);
		}
		AADetailForm form = camDetailsDTOMapper.getFormFromDTO(camRequest);
		ActionErrors errorList = AADetailValidator.validateInput(form, Locale.getDefault()); 
		HashMap newMap = new HashMap();
		newMap.put("1", errorList);
		ValidationUtility.handleError(newMap,CLIMSWebService.CAM);
		AADetailResponseDTO CAMresponse = new AADetailResponseDTO();
		{
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			OBTrxContext trxContext = new OBTrxContext();
			ILimitProfileTrxValue limitProfileTrxVal =null;
			OBLimitProfile actualLimitProfile = null;
			ICMSTrxResult trxResult = null;
			String stagingRefId=null;
			String actualRefId=null;
			
			try {
				if(camId!=null && !camId.equals("")){
					limitProfileTrxVal = proxy.getTrxLimitProfile(Long.parseLong(camId));
					if(limitProfileTrxVal!=null && ((limitProfileTrxVal.getStatus().equals("PENDING_CREATE"))
							||(limitProfileTrxVal.getStatus().equals("PENDING_UPDATE"))
							||(limitProfileTrxVal.getStatus().equals("PENDING_DELETE"))
							||(limitProfileTrxVal.getStatus().equals("REJECTED"))||(limitProfileTrxVal.getStatus().equals("DRAFT"))))
					{
						throw new CMSValidationFault("transactionStatus","Unable to update due to invalid transaction Status :"+limitProfileTrxVal.getStatus());
					}
				}
				
				Date actualExpiryDate = null; 
				
				if(limitProfileTrxVal!=null){
					actualLimitProfile =(OBLimitProfile)limitProfileTrxVal.getLimitProfile();
					if(actualLimitProfile.getCamType()!=null && ("Interim".equalsIgnoreCase(actualLimitProfile.getCamType()) || "Annual".equalsIgnoreCase(actualLimitProfile.getCamType())) ){
						if(form.getCamType()!=null && form.getCamType().equals("Initial")){
							throw new CMSValidationFault("camType","Invalid CAM Type");
							
						}
					}
					if(limitProfileTrxVal.getLimitProfile() != null)
						actualExpiryDate = limitProfileTrxVal.getLimitProfile().getNextAnnualReviewDate();
					
					//Below validation removed : 25-APR-2016 - CAM could be allowed to get updated to Interim CAM TYPE if it is set as Annual.In Front end application works fine
					//To be in sync with front-end operation, this validation should be removed from CAM online WEB service.
					/*else if(actualLimitProfile.getCamType()!=null && actualLimitProfile.getCamType().equals("Annual")){
						if(form.getCamType()!=null && !form.getCamType().equals("Annual")){
							throw new CMSValidationFault("camType","Invalid CAM Type");
							
						}
					}*/
					actualLimitProfile = (OBLimitProfile)camDetailsDTOMapper.getActualFromDTO(camRequest,actualLimitProfile);
				}
				

				if(actualLimitProfile != null) {
					Date newExpiryDate = actualLimitProfile.getNextAnnualReviewDate();
					if(!(actualExpiryDate.compareTo(newExpiryDate) == 0)) {
						DefaultLogger.debug(this, "Updating Sanctioned Amount Flag from updateCAMDetails for partyId :"+partyId );
						CustomerDAOFactory.getDAO().updateSanctionedAmountUpdatedFlag(partyId, ICMSConstant.YES);
					}
				}
				

				trxResult = proxy.submitCreateLimitProfile(trxContext, limitProfileTrxVal, actualLimitProfile);
				String id = "";
				if(trxResult != null && trxResult.getTrxValue() != null){
					limitProfileTrxVal = (ILimitProfileTrxValue)trxResult.getTrxValue();
					if(limitProfileTrxVal!= null){
						stagingRefId=limitProfileTrxVal.getStagingReferenceID();
						actualRefId=limitProfileTrxVal.getReferenceID();
						if(limitProfileTrxVal.getLimitProfile()!= null){
							id = String.valueOf(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
							CAMresponse.setCamId(id);
							CAMresponse.setResponseStatus("CAM_UPDATED_SUCCESSFULLY");
							ICMSCustomer cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(partyId,null);
							try {
								ClimesToECBFHelper.sendRequest(cust);
							} catch (Exception e) {
								e.printStackTrace();
								DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
							}
							
						}
						else{
							DefaultLogger.error(this, "updateCAMDetails - no value found in limitProfileTrxVal.getLimitProfileID(): ");
							throw new CMSException("Server side error");
						}
					}
					else{
						DefaultLogger.error(this, "updateCAMDetails - no value found in limitProfileTrxVal: "+limitProfileTrxVal);
						throw new CMSException("Server side error");
					}
				}else{
					DefaultLogger.error(this, "updateCAMDetails - no value found in trxResult: "+trxResult);
					throw new CMSException("Server side error");
				}
				
			} catch (LimitException e) {	
				if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_DUPLICATE_AA_NUM)) {
					throw new CMSException("Duplicate CAM Number");
				}
				else{
					throw new CMSException(e.getMessage());
				}
			}
			//Cam Online Format Begin
			 IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
			 ILimitDAO dao = LimitDAOFactory.getDAO();
			 if(camRequest.getOtherCovenantDetailsList()!=null && !camRequest.getOtherCovenantDetailsList().isEmpty()){
				 boolean firstIteration = true;

				 for(OtherCovenantDetailsListRequestDTO otherCovenantDetailsListRequestDTO : camRequest.getOtherCovenantDetailsList()){
					 OBOtherCovenant obOtherCovenant=new OBOtherCovenant();
					 List <OBOtherCovenant>otherCovenantDetailsList=new ArrayList<OBOtherCovenant>();

					 obOtherCovenant.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsStagingIdFromSeq()));
					 obOtherCovenant.setCustRef(camRequest.getPartyId());
					 obOtherCovenant.setCovenantCategory(otherCovenantDetailsListRequestDTO.getCovenantCategory());
					 obOtherCovenant.setAdvised(otherCovenantDetailsListRequestDTO.getAdvised());
					 obOtherCovenant.setCompiled(otherCovenantDetailsListRequestDTO.getCompiled());
					 obOtherCovenant.setCovenantCondition(otherCovenantDetailsListRequestDTO.getCovenantCondition());
					 obOtherCovenant.setCovenantDescription(otherCovenantDetailsListRequestDTO.getCovenantDescription());
					 obOtherCovenant.setCovenantType(otherCovenantDetailsListRequestDTO.getCovenantType());
					 obOtherCovenant.setRemarks(otherCovenantDetailsListRequestDTO.getRemarks());
					 obOtherCovenant.setTargetDate(otherCovenantDetailsListRequestDTO.getTargetDate());

					 List<String> monListStrList = new ArrayList<String>();
					 if(otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()!=null){
						 for(CAMMonitoringResponsibilityRequestDTO cmrDTO : otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()) {
							 monListStrList.add(cmrDTO.getMonitoringResponsibiltyValue());
						 }
					 }	

					 ArrayList monRespList = new ArrayList();
					 if(null != monListStrList){
						 for (String item : monListStrList) {
							 String[] MRArr1 =item.split("-");
							 monRespList.add(MRArr1[0]);
						 }	
					 }

					 List<String> facilityNameStrList = new ArrayList<String>();
					 if(otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()!=null){
						 for(CAMFacilityNameRequestDTO cfnDTO : otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()) {
							 facilityNameStrList.add(cfnDTO.getFacilityNameValue());
						 }
					 }	

					 ArrayList facNameList = new ArrayList();		 	 
					 if(null != facilityNameStrList){
						 for (String item : facilityNameStrList) {
							 Pattern pattern = Pattern.compile("^(.+)-\\1");
							 Matcher matcher = pattern.matcher(item);
							 while (matcher.find()) {
								 facNameList.add(matcher.group(1));
							 }							 
						 }
					 }

					 String seqNo = null;
					 dao = LimitDAOFactory.getDAO();
					 try {
						 seqNo = dao.getSeqNoForOtherCovenant();
					 }catch (Exception e) {
						 e.printStackTrace();
					 }
					 obOtherCovenant.setPreviousStagingId(seqNo);
					 if (firstIteration) {
						 othercovenantdetailsdaoimpl.deleteOtherCovenantValues1(obOtherCovenant.getCustRef());	
						 othercovenantdetailsdaoimpl.deleteOtherCovenantValues2(obOtherCovenant.getCustRef());	
						 othercovenantdetailsdaoimpl.deleteOtherCovenantDetailsStage(obOtherCovenant.getCustRef());	
						 othercovenantdetailsdaoimpl.deleteOtherCovenantDetailsActual(obOtherCovenant.getCustRef());							 
						 firstIteration = false;
					 }
					 otherCovenantDetailsList.add(obOtherCovenant);

					 OBOtherCovenant ob;
					 if(otherCovenantDetailsList !=null)
					 {	
						 for(int i=0;i<otherCovenantDetailsList.size();i++) 
						 {	

							 ob=(OBOtherCovenant)otherCovenantDetailsList.get(i);
							 try
							 {
								 if(null != ob.getStagingRefid())
								 {
									 ob.setIsUpdate("Y");
								 }
								 else
								 {
									 ob.setIsUpdate("N");
								 }
								 ob.setStagingRefid(stagingRefId);
								 othercovenantdetailsdaoimpl.insertOtherCovenantDetailsStage(ob);
								 ob.setStagingRefid(actualRefId);
								 ob.setStatus("ACTIVE");
								 othercovenantdetailsdaoimpl.updateOtherCovenantDetailsActual(ob);
							 }
							 catch(Exception e)
							 {
								 e.printStackTrace();
							 }
							 for(int j=0;j<monRespList.size();j++) {
								 OBOtherCovenant ob1 = new OBOtherCovenant();						
								 ob1.setMonitoringResponsibiltyValue((String)monRespList.get(j));
								 ob1.setMonitoringResponsibilityList1((String)monRespList.get(j));
								 ob1.setStagingRefid(ob.getOtherCovenantId()+"");
								 ob1.setCustRef(ob.getCustRef());
								 if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
								 {	
									 ob1.setStatus("INACTIVE");
								 }
								 else
								 {
									 ob1.setStatus("ACTIVE");
								 }
								 if(ob1.getMonitoringResponsibiltyValue() != null && !ob1.getMonitoringResponsibiltyValue().equals("")) {
									 othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);
									 othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
								 }
							 }	

							 for(int n=0;n<facNameList.size();n++) {
								 OBOtherCovenant ob1 = new OBOtherCovenant();
								 ob1.setFacilityNameValue((String)facNameList.get(n));
								 ob1.setStagingRefid(ob.getOtherCovenantId()+"");
								 ob1.setCustRef(ob.getCustRef());
								 ob1.setPreviousStagingId(ob.getPreviousStagingId());
								 if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
								 {	
									 ob1.setStatus("INACTIVE");
								 }
								 else
								 {
									 ob1.setStatus("ACTIVE");
								 }
								 if(ob1.getFacilityNameValue() != null && !ob1.getFacilityNameValue().equals("")) {
									 othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);	
									 othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
								 }
							 }
						 }
					 }
				 }
			 }
			//Cam Online Format END
			return CAMresponse;
		}
	}
	
	
	//SCM Interface 
	//Not required as we are getting party from the request
	/*public String getCustomerIdForSCM() {
		DefaultLogger.debug(this, "inside getCustomerIdForSCM ");
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		OBTrxContext trxContext = new OBTrxContext();
		String custId = trxContext.getCustomer().getCifId();
		DefaultLogger.debug(this, "customer Id "+custId);
		return custId;
	}*/
}
