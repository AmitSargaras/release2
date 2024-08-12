package com.integrosys.cms.app.json.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.json.dao.IJsInterfaceLogDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.json.endpoint.ILineService;
import com.integrosys.cms.app.json.line.dto.LineRootRequest;
import com.integrosys.cms.app.json.line.dto.LineRootResponse;
import com.integrosys.cms.app.json.line.dto.ResponseString;
import com.integrosys.cms.app.json.line.dto.Status;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

public class LineWebserviceClientImpl  implements ILineWebserviceClient {
	@Autowired
	@Qualifier("partyRestTemplate")
	private RestTemplate lineRestTemplate;
	
	private List<String> customResponses = new ArrayList<String>();

	private String defaultResponse = new String();
	
	private LineRootRequest LineDetails;
	
	private LineRootRequest lineDetailsForScheduler;
	
	ILimitDAO lineDao = LimitDAOFactory.getDAO();
	
	@Autowired
    private ILineService     lineServiceEndPoint;
	IJsInterfaceLogDao logDao = (IJsInterfaceLogDao) BeanHouse.get("loggingDao");

	public LineRootResponse sendRetrieveRequest(String xrefId,String limitProfileId,String limitId,String custId,IJsInterfaceLog log) throws Exception {
		DefaultLogger.debug(this,"Inside sendRetrieveRequest LineWebserviceClientImpl.java line 55 ");
		LineDetails =  lineServiceEndPoint.retrieveLineDetails(xrefId,limitProfileId,limitId,custId,log);
//		DefaultLogger.debug(this,"SCM webservice "+LineDetails);
		//HttpEntity<LineRootRequest> request = new HttpEntity<LineRootRequest>(LineDetails);
		DefaultLogger.debug(this,"----LineWebservice.java line 58 sendRetrieveRequest()----");
		if(null != LineDetails) {
			DefaultLogger.debug(this,"SCM webservice -- LineDetails>>"+LineDetails);
			DefaultLogger.debug(this,"SCM webservice -- xrefId>>"+xrefId);
			DefaultLogger.debug(this,"SCM webservice -- limitProfileId>>"+limitProfileId);
			DefaultLogger.debug(this,"SCM webservice -- limitId>>"+limitId);
			DefaultLogger.debug(this,"SCM webservice -- custId >>"+custId);
		}
		
		HttpEntity<Object> request = new HttpEntity<Object>(LineDetails, WebServiceUtil.createScmPartyHttpHeader());
		ObjectMapper Obj = new ObjectMapper();  
		String jsonStr = Obj.writeValueAsString(LineDetails);
		DefaultLogger.debug(this,"SCM webservice after json object for line .");
		log.setRequestDateTime(DateUtil.now().getTime());
		log.setInterfaceName(PropertyManager.getValue("scm.web.service.line.retrieve.endpoint"));
		System.out.println("LineWebserviceClientImpl scm.web.service.line.retrieve.endpoint value from proerty=> "+PropertyManager.getValue("scm.web.service.line.retrieve.endpoint"));
		DefaultLogger.debug(this,"LineWebserviceClientImpl scm.web.service.line.retrieve.endpoint value from proerty=> "+PropertyManager.getValue("scm.web.service.line.retrieve.endpoint"));
		log.setRequestMessage(jsonStr);
		log =logDao.createOrUpdateInterfaceLog(log);
		System.out.println("LineWebserviceClientImpl => SCM webservice =>After  createOrUpdateInterfaceLog(log) method");
		DefaultLogger.debug(this,"LineWebserviceClientImpl => SCM webservice =>After  createOrUpdateInterfaceLog(log) method");
		return send(getLoginUrl(),log, HttpMethod.POST, request, LineRootResponse.class).getBody();
		//return send(getLoginUrl(),log, HttpMethod.POST, jsonStr, LineRootResponse.class).getBody();
	}

	
	
	private <S, T> ResponseEntity<T> send(String url,IJsInterfaceLog log, HttpMethod method, HttpEntity<S> request , Class<T> clazz) throws Exception {
		ResponseEntity<T> response = null;
		DefaultLogger.debug(this, "Env: " + PropertyManager.getValue("scm.deployment.environment", "QA"));
		try {
			if (PropertyManager.getValue("scm.deployment.environment", "QA").equalsIgnoreCase("Local")) {
				response = customResponse(url, method, request, clazz);
				if(!response.getStatusCode().is2xxSuccessful()) {
					throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Data not found");
				}else {
					DefaultLogger.debug(this, "Inside else of send method ");
//					DefaultLogger.debug(this, "Request Body "+request);
//					DefaultLogger.debug(this, "Response Body "+response);
					log.setResponseMessage(WebServiceUtil.entityToString(response));
					log = logDao.createOrUpdateInterfaceLog(log);
				}
			} else {
				response = lineRestTemplate.exchange(url, method, request, clazz);
				log.setResponseMessage(WebServiceUtil.entityToString(response));
				log = logDao.createOrUpdateInterfaceLog(log);
			}
		} catch (Exception hsce) {
			DefaultLogger.error(this, hsce.getMessage(), hsce);
			log.setErrorCode("9");
			log.setErrorMessage(hsce.getMessage());
			log.setStatus("Error");
			log = logDao.createOrUpdateInterfaceLog(log);
			throw new Exception("Error Parsing Custom response ", hsce);
		}  finally {
			DefaultLogger.info(this, "URI: "+url
//					+"\nRequest: " + WebServiceUtil.entityToString(request) 
//								+ "\nResponse: " + WebServiceUtil.entityToString(response)
								);
		}
		return response;
	}

	private static String getLoginUrl() {
		return PropertyManager.getValue("scm.web.service.line.retrieve.endpoint");
	}
	
	private <S,T> ResponseEntity<T> customResponse(String url, HttpMethod method, HttpEntity<S> request, Class<T> clazz) throws Exception  {
		HttpStatus statusCode = HttpStatus.OK;
		if(clazz.equals(Void.class)) {
			return new ResponseEntity<T>(statusCode);
		}
		
		String jsonArray = " {\r\n" + 
				"   \"status\":    {\r\n" + 
				"      \"isOverriden\": false,\r\n" + 
				"      \"replyCode\": 0,\r\n" + 
				"      \"replyText\": \"0\",\r\n" + 
				"      \"memo\": null,\r\n" + 
				"      \"externalReferenceNo\": \"1234546456526645424\",\r\n" + 
				"      \"internalReferenceNumber\": \"2021138541200886\",\r\n" + 
				"      \"postingDate\": {\"dateString\": \"20210518000000\"},\r\n" + 
				"      \"errorCode\": \"0\",\r\n" + 
				"      \"extendedReply\": {\"messages\": null},\r\n" + 
				"      \"validationErrors\": null,\r\n" + 
				"      \"userReferenceNumber\": null\r\n" + 
				"   },\r\n" + 
				"   \"maintenanceType\": null,\r\n" + 
				"   \"configVersionId\": null,\r\n" + 
				"   \"responseString\":    {\r\n" + 
				"      \"StatusCode\": 0,\r\n" + 
				"      \"StatusMessage\": \"success\",\r\n" + 
				"      \"ResponseMessage\":       [\r\n" + 
				"         \"Error Number: 20006\",\r\n" + 
				"         \"Error Message: Party Details created\",\r\n" + 
				"         \"Party ID: 51001\"\r\n" + 
				"      ]\r\n" + 
				"   }\r\n" + 
				"}";
//		DefaultLogger.debug(this, "jsonBody " +jsonArray);
		ObjectMapper mapper = new ObjectMapper();		
		try {
			return new ResponseEntity<T>((T) mapper.readValue(jsonArray, clazz), statusCode);
		} catch (IOException e) {
			DefaultLogger.info(this, e.getMessage(), e);
			throw new Exception("Error Parsing Custom response ", e);
		}
	}

	
	 public boolean processFailedReleaseLineRequests(OBJsInterfaceLog log) {
		 HttpEntity<Object> request = new HttpEntity<Object>(log.getRequestMessage());
		 LineRootResponse response;
		 String scmErrorStringList=PropertyManager.getValue("error.strings.scm.job");
			System.out.println("processFailedReleaseLineRequests => Property error.strings.scm.job scmErrorStringList=>"+scmErrorStringList);
			boolean flag = false;
			int failCount= log.getFailCount();
		 try {
			response = sendRequestForScheduler(log);
			log.setResponseDateTime(DateUtil.now().getTime());
			ResponseString responseString  = response.getResponseString();
			Status statusString = response.getStatus();
			log.setResponseDateTime(DateUtil.now().getTime());
			if(responseString!=null) {
				System.out.println("******** inside Linewebserviceclientimpl *********** processFailedReleaseLineRequests responseString!=null ******line 185  ");
			log.setErrorCode(String.valueOf(responseString.getStatusCode()));
			List errorMsgList = responseString.getResponseMessage();
			StringBuilder errorMsg = new StringBuilder();
			for(int i=0;i<errorMsgList.size();i++) {
				errorMsg.append(errorMsgList.get(i)); 
			}
			log.setErrorMessage(errorMsg.toString());
			log.setStatus(responseString.getStatusCode()==0 ?"Success":"Fail");
			System.out.println("processFailedlINERequests****192*****responseString.getStatusCode() : "+responseString.getStatusCode()+" if statuscode is 0, directly marking status to SUCCESS ");

			if(!"Success".equalsIgnoreCase(log.getStatus())){
				try {
					System.out.println("***********inside Linewebserviceclientimpl *********** processFailedReleaseLineRequests try ");
					System.out.println("***********line 198***before add failCount  : "+failCount+", log.getErrorMessage()  : "+log.getErrorMessage()+", log.getStatus()"+log.getStatus());
					if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
				failCount=failCount+1;
				log.setFailCount(failCount);
				System.out.println(" *****202******inside if found error so increased FailCount : "+log.getFailCount());
				}
				}catch(Exception e) {
					System.out.println("**********inside Linewebserviceclientimpl *********** processFailedReleaseLineRequests catch while setting fail Count inside if for response string not null Exception  : "+e);
				}
				if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
					System.out.println("***********line 208***before checking failCount<2 or not  log.getFailCount() : "+log.getFailCount()+", log.getErrorMessage()  : "+log.getErrorMessage()+", log.getStatus()"+log.getStatus());
					if(log.getFailCount()<2) {
							if (null != scmErrorStringList && !"".equals(scmErrorStringList)) {
								String[] scmErrorStrlist = scmErrorStringList.split("|||");
								if ((scmErrorStringList).contains(log.getErrorMessage())) {
									System.out.println("***********line 213**setting staus=error and flag=true for matching log.getErrorMessage() : "+ log.getErrorMessage());
									log.setStatus("Error");
									flag = true;
								} else if (log.getErrorMessage().contains("20007")) {
									System.out.println("processFailedLineRequests****215***** as error message contains 20007, setting status as success ");
									log.setStatus("Success");
								} else {
									log.setStatus("Fail");
								}
							}
					System.out.println("223 Flag =>"+flag+" .. log.getStatus()=>"+log.getStatus());
					if(flag == false && !"Success".equalsIgnoreCase(log.getStatus())) {
						log.setStatus("Fail");
					}
					}else {
						System.out.println("***********line 228**inside else ****as log.getFailCount() is not less than 2 setting log.getStatus() to fail");
						log.setStatus("Fail");
					}
				}
				}
				
			
			if("".equals(log.getPartyId()) || null == log.getPartyId()){
				log.setStatus("Fail");
			}
			System.out.println("processFailedReleaseLineRequests =>After party id check condition => log.getStatus()=>"+log.getStatus()+ " .. log.getPartyId() =>"+log.getPartyId());
			
			
			lineDao.updateTheFailedResponseLog(log);
			System.out.println("processFailedReleaseLineRequests****241***updated the interface log table at line module for "+log.getId());
		    if(log.getStatus().equalsIgnoreCase("Success")) {
		    	return true;
		    	}else{
		    		return false;}
			}else {
				log.setResponseDateTime(DateUtil.now().getTime());
				log.setErrorCode(statusString.getErrorCode());
				log.setErrorMessage(statusString.getReplyText());
				log.setStatus("Error");
				
				flag = false;
				
				try {
					System.out.println("***********inside Linewebserviceclientimpl *********** processFailedReleaseLineRequests *****252**else for responseString=null  try**** ");
					System.out.println("***********line 256***before add failCount  : "+failCount+", log.getErrorMessage()  : "+log.getErrorMessage());
				if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
				failCount=failCount+1;
				log.setFailCount(failCount);
				System.out.println(" inside else for responseString=null****if found error so increased FailCount : "+log.getFailCount());
				}
				}catch(Exception e) {
					System.out.println("while setting fail Count inside else for response string null Exception  : "+e);
				}
				
				if (null != log.getErrorMessage() && !"".equals(log.getErrorMessage())) {
					if (null != scmErrorStringList && !"".equals(scmErrorStringList)) {
						System.out.println("***** inside else for responseString=null******line 269***before checking failCount<2 or not  log.getFailCount() : "+ log.getFailCount() + ", log.getStatus()" + log.getStatus());
						if (log.getFailCount() < 2) {
							String[] scmErrorStrlist = scmErrorStringList.split("|||");
							if ((scmErrorStringList).contains(log.getErrorMessage())) {
								System.out.println("***** inside else for responseString=null*******line 273**setting staus=error and flag=true for matching log.getErrorMessage() : "+ log.getErrorMessage());
								log.setStatus("Error");
								flag = true;
							} else if (log.getErrorMessage().contains("20007")) {
								System.out.println("processFailedLineRequests****277***** as error message contains 20007, setting status as success ");
								log.setStatus("Success");
							} else {
								log.setStatus("Fail");
							}
						}
					}

					if (flag == false && !"Success".equalsIgnoreCase(log.getStatus())) {
						System.out.println(
								"***** inside else for responseString=null*******line 287**inside if flag=false and status=!success setting log.getStatus() to fail");
						log.setStatus("Fail");
					}
				}
				System.out.println("292 Flag =>"+flag+" .. log.getStatus()=>"+log.getStatus());
				
				if("".equals(log.getPartyId()) || null == log.getPartyId()){
					log.setStatus("Fail");
				}
				System.out.println("processFailedReleaseLineRequests => else => log.getStatus()=>"+log.getStatus());
				lineDao.updateTheFailedResponseLog(log);	
				System.out.println("processFailedReleaseLineRequests****301***updated the interface log table at line module for response string null for "+log.getId());
				return false;
			}
		} catch (Exception e) {
			System.out.println( "Error in processing the data for "+ log.getLimitProfileId()+" "+log.getLine_no()+" "+log.getSerial_no());
			log.setErrorCode("9");
			log.setErrorMessage(e.getMessage()); 
			log.setStatus("Error");
			
			flag = false;
			
			try {
				System.out.println(" inside releaseline catch***305******** failCount : "+failCount);
			if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
			failCount=failCount+1;
			log.setFailCount(failCount);
			System.out.println(" inside releaseline catch****314* if found error so increased FailCount : "+log.getFailCount());
			}
			}catch(Exception e1) {
				System.out.println("inside releaseline catch*****317****** while setting fail Count Exception inside catch  : "+e1);
			}
			
			
			if (null != log.getErrorMessage() && !"".equals(log.getErrorMessage())) {
				if (null != scmErrorStringList && !"".equals(scmErrorStringList)) {
					System.out.println("***** inside releaseline catch******line 323*** log.getFailCount() : "+ log.getFailCount() + ", log.getStatus()" + log.getStatus());
					if (log.getFailCount() < 2) {
						String[] scmErrorStrlist = scmErrorStringList.split("|||");
						if ((scmErrorStringList).contains(log.getErrorMessage())) {
							System.out.println("***** inside releaseline catch***327******setting staus=error and flag=true for matching log.getErrorMessage() : "+ log.getErrorMessage());
							log.setStatus("Error");
							flag = true;
						} else if (log.getErrorMessage().contains("20007")) {
							System.out.println(
									"processFailedLineRequests****332***** as error message contains 20007, setting status as success ");
							log.setStatus("Success");
						} else {
							log.setStatus("Fail");
						}
					}
				}
				System.out.println("332 Flag =>"+flag+" .. log.getStatus()=>"+log.getStatus());
				if(flag == false && !"Success".equalsIgnoreCase(log.getStatus())) {
					System.out.println("***** inside releaseline catch*******line 341**inside if flag=false and status=!success setting log.getStatus() to fail");
					log.setStatus("Fail");
				}
			}
			
			if("".equals(log.getPartyId()) || null == log.getPartyId()){
				log.setStatus("Fail");
			}
			System.out.println("Exception processFailedReleaseLineRequests => Catch => log.getStatus()=>"+log.getStatus());
						
			lineDao.updateTheFailedResponseLog(log);
			System.out.println("processFailedReleaseLineRequests****352*****inside catch******updated the interface log table at line module  for "+log.getId());
			e.printStackTrace();
			return false;
		}
	 }
	 
	 public LineRootResponse sendRequestForScheduler(OBJsInterfaceLog log) throws Exception {
		    System.out.println("Calling Send method for Scheduler for Release line");
		    lineDetailsForScheduler = new ObjectMapper().readValue(log.getRequestMessage(), LineRootRequest.class);  
			HttpEntity<Object> request = new HttpEntity<Object>(log.getRequestMessage(),WebServiceUtil.createScmPartyHttpHeader());
//			System.out.println("Request created before calling the send in line "+request);
			return sendforScheduler(getLoginUrl(),log, HttpMethod.POST, request, LineRootResponse.class).getBody();
		}
	 
	 private <S, T> ResponseEntity<T> sendforScheduler(String url,OBJsInterfaceLog log, HttpMethod method, HttpEntity<S> request , Class<T> clazz) throws Exception {
			ResponseEntity<T> response = null;
			System.out.println("Env: " + PropertyManager.getValue("scm.deployment.environment", "QA"));
			try {
				if (PropertyManager.getValue("scm.deployment.environment", "QA").equalsIgnoreCase("Local")) {
					response = customResponse(url, method, request, clazz);
					if(!response.getStatusCode().is2xxSuccessful()) {
						throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Data not found");
					}else {
						System.out.println("Inside else of send method ");
//						System.out.println("Request Body "+request);
//						System.out.println("Response Body "+response);
						log.setResponseMessage(WebServiceUtil.entityToString(response));
					}
				} else {
					System.out.println("Since its not dev sending data to SCM through rest");
					response = lineRestTemplate.exchange(url, method, request, clazz);
					log.setResponseMessage(WebServiceUtil.entityToString(response));
				}
			} catch (Exception hsce) {
				System.out.println(" Error Parsing Custom response "+hsce.getMessage());
				log.setErrorCode("9");
				log.setErrorMessage(hsce.getMessage());
				log.setStatus("Error");
				lineDao.updateTheFailedResponseLog(log);
				throw new Exception("Error Parsing Custom response ", hsce);
			}  finally {
				DefaultLogger.info(this, "URI: "+url
//						+"\nRequest: " + WebServiceUtil.entityToString(request) 
//									+ "\nResponse: " + WebServiceUtil.entityToString(response)
									);
			}
			return response;
		}
	 
	 public LineRootResponse sendRetrieveRequestforStp(String srcRefId,OBJsInterfaceLog log) throws Exception {
		 DefaultLogger.debug(this, "Inside sendRetrieveRequestforStp => Going for  lineServiceEndPoint.retrieveLineDetailsforStp(srcRefId,log)...");
			LineDetails =  lineServiceEndPoint.retrieveLineDetailsforStp(srcRefId,log);
//			DefaultLogger.debug(this,"SCM webservice "+LineDetails);
			ObjectMapper Obj = new ObjectMapper();  
			String jsonStr = Obj.writeValueAsString(LineDetails);
			HttpEntity<Object> request = new HttpEntity<Object>(LineDetails,WebServiceUtil.createScmPartyHttpHeader());
//			DefaultLogger.debug(this,"SCM webservice after json object for party "+jsonStr);
			log.setRequestDateTime(DateUtil.now().getTime());
			log.setInterfaceName(PropertyManager.getValue("scm.web.service.line.retrieve.endpoint"));
			log.setRequestMessage(jsonStr);
			return sendforSTP(getLoginUrl(),log, HttpMethod.POST, request, LineRootResponse.class).getBody();
			//return send(getLoginUrl(),log, HttpMethod.POST, jsonStr, LineRootResponse.class).getBody();
		}
	 
	 private <S, T> ResponseEntity<T> sendforSTP(String url,OBJsInterfaceLog log, HttpMethod method, HttpEntity<S> request , Class<T> clazz) throws Exception {
		 System.out.println("Inside sendforSTP .. url=>"+url);	
		 ResponseEntity<T> response = null;
			System.out.println("Env: " + PropertyManager.getValue("scm.deployment.environment", "QA"));
			try {
				if (PropertyManager.getValue("scm.deployment.environment", "QA").equalsIgnoreCase("Local")) {
					response = customResponse(url, method, request, clazz);
					if(!response.getStatusCode().is2xxSuccessful()) {
						throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Data not found");
					}else {
						System.out.println("Inside else of send method ");
//						System.out.println("Request Body "+request);
//						System.out.println("Response Body "+response);
						log.setResponseMessage(WebServiceUtil.entityToString(response));
					}
				} else {
					System.out.println("Since its not dev sending data to SCM through rest");
					response = lineRestTemplate.exchange(url, method, request, clazz);
					System.out.println("After lineRestTemplate.exchange(url, method, request, clazz) line number. 267 ...");
					log.setResponseMessage(WebServiceUtil.entityToString(response));
				}
			} catch (Exception hsce) {
				System.out.println(" Error Parsing Custom response "+hsce.getMessage());
				log.setErrorCode("9");
				log.setErrorMessage(hsce.getMessage());
				log.setStatus("Error");
				lineDao.updateTheFailedResponseLog(log);
				hsce.printStackTrace();
				throw new Exception("Error Parsing Custom response ", hsce);
			}  finally {
				DefaultLogger.info(this, "URI: "+url
//						+"\nRequest: " + WebServiceUtil.entityToString(request) 
//									+ "\nResponse: " + WebServiceUtil.entityToString(response)
									);
			}
			return response;
		}
	
	public void addCustomResponse(String response) {
		this.customResponses.add(response);
	}

	
	public List<String> getAllCustomResponses() {
		return this.customResponses;
	}

	public void setDefaultResponse(String defaultResponse) {
		this.defaultResponse = defaultResponse;
	}

	
	public String getDefaultResponse() {
		return this.defaultResponse;
	}

	@Bean
	public RestTemplate lineRestTemplate() {
	    return new RestTemplate();
	}
}
