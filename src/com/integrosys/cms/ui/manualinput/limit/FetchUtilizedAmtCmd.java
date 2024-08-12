package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityWsLogDao;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.ui.common.UIUtil;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiPortBindingStub;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiServiceLocator;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionFullTypeResponseDTO;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionPKTypeDTO;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyrequest;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType;
import com.ofss.fc.framework.domain.WorkItemViewObjectDTO;
import com.ofss.fc.service.response.TransactionStatus;

public class FetchUtilizedAmtCmd extends AbstractCommand{

public String[][] getParameterDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][]{
				{"systemId","java.lang.String",REQUEST_SCOPE},
				{"lineNo","java.lang.String",REQUEST_SCOPE},
				{"serialNo","java.lang.String",REQUEST_SCOPE},
				{"liabilityId","java.lang.String",REQUEST_SCOPE},
				{"utilizedAmount","java.lang.String",REQUEST_SCOPE},
				{"partyId","java.lang.String",REQUEST_SCOPE},
				
		});
	}

public String[][] getResultDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][]{
				{"fcubsUtilizedAmt","java.lang.String",REQUEST_SCOPE},
				{"fcubsResponce","java.lang.String",REQUEST_SCOPE},
		});
	}
	
public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {

    HashMap result = new HashMap();
    HashMap exceptionMap = new HashMap();
    HashMap temp = new HashMap();
    
    String systemId=(String)map.get("systemId");
    String liabilityId=(String)map.get("liabilityId");
    String lineNo=(String)map.get("lineNo");
    String serialNo =(String)map.get("serialNo");
    String utilizedAmount =(String)map.get("utilizedAmount");
    String partyId=(String)map.get("partyId");
    
   QueryFacilityIOFSResponseDTO doQueryFacilityIO =null;
   
   FacilityWsLog facilityWsLog=new FacilityWsLog();
    
    DefaultLogger.debug(this,"facilityWsLog:"+facilityWsLog);
    facilityWsLog.setFacilitySysId(systemId);
	facilityWsLog.setLineNo(lineNo);
	facilityWsLog.setLiability(liabilityId);
	facilityWsLog.setSerialNo(serialNo);
	facilityWsLog.setPartyId(partyId);
	
    DefaultLogger.debug(this, "Facility webservice: systemId:"+systemId+" lineNo:"+lineNo+" serialNo:"+serialNo+" liabilityId:"+liabilityId+" utilizedAmount:"+utilizedAmount+" partyId:"+partyId);
    
				try{
					String facilityWebservice=PropertyManager.getValue("fcubs.facility.webservice.url");
					DefaultLogger.debug(this, "Facility webservice::::"+facilityWebservice);
					if(null!=facilityWebservice){
						facilityWebservice = facilityWebservice.trim(); 
						DefaultLogger.debug(this, "fcubsWebservice.trim()::::"+facilityWebservice);
					}
			/*	URL url =null;
				DefaultLogger.debug(this, "Facility webservice::::"+fcubsWebservice);
				
				if(null!=fcubsWebservice){
					fcubsWebservice = fcubsWebservice.trim(); 
					DefaultLogger.debug(this, "fcubsWebservice.trim()::::"+fcubsWebservice);
					try {
						url = new URL(fcubsWebservice+"?wsdl");
						//url = new URL(fcubsWebservice+".wsdl");

					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} */
				
				String source = PropertyManager.getValue("fcubs.webservice.facility.source");
			//	String ubscomp = PropertyManager.getValue("fcubs.webservice.facility.ubscomp");
				String userid = PropertyManager.getValue("fcubs.webservice.facility.userid");
				String branch = PropertyManager.getValue("fcubs.webservice.facility.branch");
			//	String moduleid = PropertyManager.getValue("fcubs.webservice.facility.moduleid");
				String facilityService = PropertyManager.getValue("fcubs.webservice.facility.service");
				String facilityOperation = PropertyManager.getValue("fcubs.webservice.facility.operation");
//				String sourceUserid = PropertyManager.getValue("fcubs.webservice.source.userid");
//				String facilityFunctionid = PropertyManager.getValue("fcubs.webservice.facility.functionid");
				
	
				String bankCode = PropertyManager.getValue("fcubs.webservice.facility.bankCode");
				String channel = PropertyManager.getValue("fcubs.webservice.facility.channel");
				String serviceCode = PropertyManager.getValue("fcubs.webservice.facility.serviceCode");
				//String transactingPartyCode = PropertyManager.getValue("fcubs.webservice.facility.transactingPartyCode");
				String transactionBranch = PropertyManager.getValue("fcubs.webservice.facility.transactionBranch");
				String userId = PropertyManager.getValue("fcubs.webservice.facility.userId");
				
				String transactingPartyCode=systemId;
				DefaultLogger.debug(this, " source:"+source+" userid:"+userid+" branch:"+branch+" facilityService:"+facilityService+" facilityOperation:"+facilityOperation);
				DefaultLogger.debug(this, " bankCode:"+bankCode+" channel:"+channel+" serviceCode:"+serviceCode+" transactingPartyCode:"+transactingPartyCode
						+" transactionBranch:"+transactionBranch+" userId:"+userId);
				
				DefaultLogger.debug(this, "creating ElfacilityApplicationServiceSpiServiceLocator");
				ElfacilityApplicationServiceSpiServiceLocator client= new ElfacilityApplicationServiceSpiServiceLocator();
				DefaultLogger.debug(this, "locator:"+client);

				DefaultLogger.debug(this, "setting facility endpoint");
				client.setElfacilityApplicationServiceSpiPortEndpointAddress(facilityWebservice);

				DefaultLogger.debug(this, "getting facility port");
				ElfacilityApplicationServiceSpi elfacilityApplicationServiceSpiPort = client.getElfacilityApplicationServiceSpiPort();
				DefaultLogger.debug(this, "elfacilityApplicationServiceSpiPort:"+elfacilityApplicationServiceSpiPort);
//				ElfacilityApplicationServiceSpiPortBindingStub elfacilityApplicationServiceSpi=new ElfacilityApplicationServiceSpiPortBindingStub();
				
				Calendar c=Calendar.getInstance();
				String externalReferenceNo="CLIMSFACILITY"+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND)+c.get(Calendar.MILLISECOND);
				
				DefaultLogger.debug(this, "externalReferenceNo:"+externalReferenceNo);
				SessionContext sessionContext =new SessionContext();
				sessionContext.setBankCode(bankCode);
				sessionContext.setChannel(channel); 
				sessionContext.setServiceCode(serviceCode);
			 	sessionContext.setExternalReferenceNo(externalReferenceNo);
			 	sessionContext.setTransactingPartyCode(transactingPartyCode);
			 	sessionContext.setTransactionBranch(transactionBranch);
			 	sessionContext.setUserId(userId);
			 
			 	DefaultLogger.debug(this, "sessionContext:"+sessionContext);
				
				QueryFacilityIOFSRequestDTO facIOFSrequestDTO= new QueryFacilityIOFSRequestDTO();

				DefaultLogger.debug(this, "facIOFSrequestDTO:"+facIOFSrequestDTO);
				FcubsHeaderType fcubsheader=new FcubsHeaderType();
				Fcubsbodyrequest fcubsbody=new Fcubsbodyrequest();
				FacilityDescriptionPKTypeDTO facilityIO=new FacilityDescriptionPKTypeDTO();

				DefaultLogger.debug(this, "fcubsheader:"+fcubsheader+" fcubsbody:"+fcubsbody);
				UbscompType ubscomp2 = fcubsheader.getUbscomp();
				DefaultLogger.debug(this,"ubscomp2.FCUBS:"+ubscomp2.FCUBS);
				
				fcubsheader.setSource(source);
				fcubsheader.setUbscomp(ubscomp2.FCUBS);
				fcubsheader.setUserid(userid);
				fcubsheader.setBranch(branch);
				fcubsheader.setService(facilityService);
				fcubsheader.setOperation(facilityOperation);
				fcubsheader.setModuleid("");
				DefaultLogger.debug(this, "creating FcubsHeaderTypeAddlParam:");
				FcubsHeaderTypeAddlParam addl = new FcubsHeaderTypeAddlParam("test","test");
				FcubsHeaderTypeAddlParam[] param=new FcubsHeaderTypeAddlParam[1];
				param[0]=addl;
				
				DefaultLogger.debug(this,"setting addl param:"+param);
				
				fcubsheader.setAddl(param);
				DefaultLogger.debug(this,"after setting addl param:");
				
				
				DefaultLogger.debug(this,"facilityIO before:"+facilityIO);
				
				facilityIO.setLineserial(new BigDecimal(serialNo));
				facilityIO.setLinecode(lineNo);
				facilityIO.setLiabid(liabilityId);
				facilityIO.setLiabno(systemId);
				
				DefaultLogger.debug(this,"facilityIO after:"+facilityIO);
				
				fcubsbody.setFacilityIO(facilityIO);
				
				facIOFSrequestDTO.setFcubsheader(fcubsheader);
				facIOFSrequestDTO.setFcubsbody(fcubsbody);
			
				
				WorkItemViewObjectDTO workItemViewObjectDTO = new WorkItemViewObjectDTO();
				WorkItemViewObjectDTO[] arg2 = new WorkItemViewObjectDTO[1];
				arg2[0] = workItemViewObjectDTO;
				
				DefaultLogger.debug(this,"workItemViewObjectDTO:"+workItemViewObjectDTO+" arg2:"+arg2);
				DefaultLogger.debug(this,"calling doQueryFacilityIO");
				
				
				facilityWsLog.setRequestDateTime(Calendar.getInstance().getTime());
				
				 doQueryFacilityIO = elfacilityApplicationServiceSpiPort.doQueryFacilityIO(sessionContext, facIOFSrequestDTO, arg2);
				 DefaultLogger.debug(this,"doQueryFacilityIO:"+doQueryFacilityIO);
				 
				 DefaultLogger.debug(this,"casting port to stub");
				 ElfacilityApplicationServiceSpiPortBindingStub stub=(ElfacilityApplicationServiceSpiPortBindingStub)elfacilityApplicationServiceSpiPort;
				 DefaultLogger.debug(this,"stub:"+stub);
				 String request = stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString();
				 facilityWsLog.setRequestMessage(request);
				 DefaultLogger.debug(this,"request:"+request);
					
				String response = stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString();
				 facilityWsLog.setResponseMessage(response);
				DefaultLogger.debug(this,"response:"+response);
				
				
				TransactionStatus status = doQueryFacilityIO.getStatus();
				DefaultLogger.debug(this, "doQueryFacilityIO:"+doQueryFacilityIO);
				DefaultLogger.debug(this, "doQueryFacilityIO getFcubsbody:"+doQueryFacilityIO.getFcubsbody());
				
				facilityWsLog.setResponseDateTime(Calendar.getInstance().getTime());
				
				if(null!=status){
					
					DefaultLogger.debug(this,"status:"+status+"reply code:"+status.getReplyCode());
					facilityWsLog.setResponseCode(String.valueOf(status.getReplyCode()));
					facilityWsLog.setErrorMessage(status.getReplyText());
					if(0==status.getReplyCode()) {	
				if(null!=doQueryFacilityIO.getFcubsbody() && null!=doQueryFacilityIO.getFcubsbody().getFacilityFull()){
					DefaultLogger.debug(this, "doQueryFacilityIO getFcubsbody.getFacilityFull():"+doQueryFacilityIO.getFcubsbody().getFacilityFull());
					FacilityDescriptionFullTypeResponseDTO facilityFull = doQueryFacilityIO.getFcubsbody().getFacilityFull();
					
					

					DefaultLogger.debug(this,"status:"+status+"reply code:"+status.getReplyCode()+" facilityFull:"+facilityFull);
	
				
				
				BigDecimal utilizedAmountBd=new BigDecimal("0");
				 utilizedAmount = UIUtil.removeComma(utilizedAmount);
				 DefaultLogger.debug(this,"utilizedAmount:"+utilizedAmount);
				if(null!=utilizedAmount) {
					
					 utilizedAmountBd = new BigDecimal(utilizedAmount);
				}
				
				BigDecimal fcubsUtilizedAmt = facilityFull.getUtilisation();
				DefaultLogger.debug(this, "fcubsUtilizedAmt:"+fcubsUtilizedAmt);
				//String fcubsUtilizedAmt=getUtilizedAmount(systemId,lineNo,serialNo);
				String fcubsResponce="";
//				if(utilizedAmountBd.compareTo(new BigDecimal(fcubsUtilizedAmt))==0) {
//					fcubsResponce="No Change Found";
//				}
				
				if(null==fcubsUtilizedAmt)
					fcubsUtilizedAmt=new BigDecimal("0");
				if(utilizedAmountBd.compareTo(fcubsUtilizedAmt)==0) {
				//fcubsResponce="No Change Found";
			}
				DefaultLogger.debug(this, "fcubsResponce:"+fcubsResponce);
			 
				String fcubsUtilizedAmtStr=""+fcubsUtilizedAmt;
				DefaultLogger.debug(this, "fcubsUtilizedAmtStr:"+fcubsUtilizedAmtStr);
				 result.put("fcubsUtilizedAmt", fcubsUtilizedAmtStr);
				 result.put("fcubsResponce", fcubsResponce);
			 
				}else{
					DefaultLogger.debug(this, "status.getReplyText():"+status.getReplyText());
					
					result.put("fcubsResponce","Either getFCUBSBODY or getFacilityFull are returing null.");
					
					}} else {
						DefaultLogger.debug(this, "status.getReplyText():"+status.getReplyText());
						LimitDAO ldao=new LimitDAO();
					    String facLiabErrorDesc = ldao.getFacLiabErrorDesc(status.getErrorCode());
					    facLiabErrorDesc=status.getErrorCode()+":"+facLiabErrorDesc;
					    DefaultLogger.debug(this, "facLiabErrorDesc:"+facLiabErrorDesc);
					    facilityWsLog.setErrorMessage(facLiabErrorDesc);
						result.put("fcubsResponce", facLiabErrorDesc);
					}
				}else{
						facilityWsLog.setErrorMessage("TransactionStatus is null");
						result.put("fcubsResponce", "TransactionStatus is null");
					}
			
				}catch(Exception e){
					DefaultLogger.debug(this,"exception:"+ e.getMessage());
					e.printStackTrace();
					if(null!=facilityWsLog)
					facilityWsLog.setErrorMessage(e.getMessage());
					result.put("fcubsResponce", e.getMessage());
					
				}
				
				if(null!=facilityWsLog) {
				IFacilityWsLogDao facilityWsLogDao = (IFacilityWsLogDao)BeanHouse.get("facilityWsLogDao");
				IFacilityWsLog createFacilityWsLog = facilityWsLogDao.createFacilityWsLog(facilityWsLog);
				DefaultLogger.debug(this, "createFacilityWsLog id:"+createFacilityWsLog.getId());
				}
				 temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
				 temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			 return temp;

	}

/*
private String getUtilizedAmount(String systemId,String lineno,String serialNo){
	String fcubsUtilizedAmt="0.00";
	if(null!=systemId && null!=lineno && null!=serialNo){
	if("42".equals(systemId) && "240MULCEX".equals(lineno) &&  "01".equals(serialNo)){
		return "30";
	}
	else if ("42".equals(systemId) && "240MULCEX".equals(lineno) &&  "02".equals(serialNo)){
		return "40";
	}else if ("".equals(systemId) && "240MULCEX".equals(lineno) &&  "03".equals(serialNo)){
		return "20";
	}
	else if ("42".equals(systemId) && "240MULCEX".equals(lineno) &&  "04".equals(serialNo)){
		return "10";
	}else if ("42".equals(systemId) && "240MULCEX".equals(lineno) &&  "05".equals(serialNo)){
		return "50";
	}else if ("2222".equals(systemId) && "240MULCEX".equals(lineno) &&  "01".equals(serialNo)){
		return "70";
	}else if ("5675".equals(systemId) && "240MULCEX".equals(lineno) &&  "02".equals(serialNo)){
		return "60";
	}
	else if ("2222".equals(systemId) && "240MULCEX".equals(lineno) &&  "11".equals(serialNo)){
		return "120";
	}else if ("5675".equals(systemId) && "240MULCEX".equals(lineno) &&  "12".equals(serialNo)){
		return "1300";
	}
	}
	return fcubsUtilizedAmt;
	
}*/

}
