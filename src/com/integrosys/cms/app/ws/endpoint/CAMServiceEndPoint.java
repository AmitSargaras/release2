package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;


import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.ws.dto.AADetailRequestDTO;
import com.integrosys.cms.app.ws.dto.AADetailResponseDTO;
import com.integrosys.cms.app.ws.facade.CAMServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "CAMServiceEndPoint", portName = "CAMServiceEndPointPort", 
			endpointInterface = "com.integrosys.cms.app.ws.endpoint.ICAMServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/CAM/")
@HandlerChain(file = "handler-chain.xml")
public class CAMServiceEndPoint implements ICAMServiceEndPoint {

	private CAMServiceFacade camServiceFacade;

	@WebMethod(operationName = "addCAMDetails")
	public AADetailResponseDTO addCAMDetails(
			@WebParam(name = "CAMRequest" ,mode = Mode.IN)  AADetailRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		camServiceFacade = (CAMServiceFacade) BeanHouse.get("camServiceFacade");
		requestDto.setEvent("WS_CREATE_CAM");
		AADetailResponseDTO responseDto = camServiceFacade.addCAMDetails(requestDto);
		return responseDto;
	}
	
	
	@WebMethod(operationName = "updateCAMDetails")
	public AADetailResponseDTO updateCAMDetails(
			@WebParam(name = "CAMRequest" ,mode = Mode.IN)  AADetailRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		camServiceFacade = (CAMServiceFacade) BeanHouse.get("camServiceFacade");
		requestDto.setEvent("WS_UPDATE_CAM");
		//String custId = camServiceFacade.getCustomerIdForSCM();
		AADetailResponseDTO responseDto = camServiceFacade.updateCAMDetails(requestDto);
		processCAMdetailsForScm(responseDto,requestDto.getPartyId());
		return responseDto;
	}
	
	public void processCAMdetailsForScm(AADetailResponseDTO responseDto,String custId) {
		//SCM Interface 
		if (responseDto.getResponseStatus().equals("CAM_UPDATED_SUCCESSFULLY")){
			IJsInterfaceLog log = new OBJsInterfaceLog();
			ScmPartyDao scmPartyDao = (ScmPartyDao)BeanHouse.get("scmPartyDao");
			String mainScmFlag = scmPartyDao.getBorrowerScmFlag(custId);
			String stgScmFlag = scmPartyDao.getBorrowerScmFlagforCAM(custId);
			System.out.println("*****Inside CAMServiceEndPoint****59***caling getLatestOperationStatus() for CAM SCM Interface***** party id :  "+custId);
			String latestOperationStatus = scmPartyDao.getLatestOperationStatus(custId,"CAM");
			System.out.println("*****Inside CAMServiceEndPoint****61******operation is"+latestOperationStatus);
			DefaultLogger.debug(this, "Customer Id in CAM Webservice "+custId);
			DefaultLogger.debug(this, "Main table scm Flag "+mainScmFlag);
			DefaultLogger.debug(this, "Last updated scm flag from log table "+stgScmFlag);
			
			try {
				DefaultLogger.debug(this, "Going to call SCM webservice");
				PrepareSendReceivePartyCommand scmWsCall = new PrepareSendReceivePartyCommand();
				log.setModuleName("CAM");
				log.setIs_udf_upload("N");
				if(latestOperationStatus!=null && latestOperationStatus.equalsIgnoreCase("A")) {
					DefaultLogger.debug(this, "Sending again operation as A because earlier record was rejected ");
					if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "A", "Y",log);
					}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "A","Y", log);
					}else {
						DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
					}
				}else {
					DefaultLogger.debug(this, "Sending operation as U because the party is present in SCM "+latestOperationStatus);
					if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "U", "Y",log);
					}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "U","Y", log);
					}else if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No")) {
						scmWsCall.scmWebServiceCall(custId, "U","N", log);
					}else {
						DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
					}
				}
			}catch(Exception e) {
				DefaultLogger.debug(this, "error in SCM webservice "+e);
			}
		}
		
	}
}
