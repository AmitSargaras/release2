package com.integrosys.cms.app.json.ws;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
import com.integrosys.cms.app.json.endpoint.IPartyService;
import com.integrosys.cms.app.json.party.dto.ResponseString;
import com.integrosys.cms.app.json.party.dto.PartyRootRequest;
import com.integrosys.cms.app.json.party.dto.PartyRootResponse;
import com.integrosys.cms.app.json.ws.WebServiceUtil;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupJdbc;
import com.integrosys.cms.app.json.party.dto.Status;


public class PartyWebserviceClientImpl  implements IPartyWebserviceClient {
		@Autowired
		@Qualifier("partyRestTemplate")
		private RestTemplate partyRestTemplate;

		private List<String> customResponses = new ArrayList<String>();

		private String defaultResponse = new String();
		
		private PartyRootRequest PartyDetails;
		private PartyRootRequest partyDetailsForScheduler;
		
		@Autowired
        private IPartyService     partyServiceEndPoint;
		IJsInterfaceLogDao logDao = (IJsInterfaceLogDao) BeanHouse.get("loggingDao");
		
		IPartyGroupJdbc partyJdbc = (IPartyGroupJdbc)BeanHouse.get("partyGroupJdbc");	

		
		
  
		public PartyRootResponse sendRetrieveRequest(String custId,String action,String scmFlag,IJsInterfaceLog log) throws Exception {
			PartyDetails =  partyServiceEndPoint.retrievePartyDetails(custId,action,scmFlag,log);
//			DefaultLogger.debug(this,"SCM webservice "+PartyDetails);

			//HttpEntity<Object> request = new HttpEntity<Object>(headers,PartyDetails);
			HttpEntity<Object> request = new HttpEntity<Object>(PartyDetails, WebServiceUtil.createScmPartyHttpHeader());

			ObjectMapper Obj = new ObjectMapper();  
			String jsonStr = Obj.writeValueAsString(PartyDetails);
//			DefaultLogger.debug(this,"SCM webservice after json object for party "+jsonStr);
			log.setRequestDateTime(DateUtil.now().getTime());
			log.setInterfaceName(PropertyManager.getValue("scm.web.service.party.retrieve.endpoint"));
			log.setOperation(action);
			log.setPartyId(custId);
			//log.setRequestMessage(WebServiceUtil.entityToString(request));
			log.setRequestMessage(jsonStr);
			log = logDao.createOrUpdateInterfaceLog(log);
			return send(getLoginUrl(),log, HttpMethod.POST, request, PartyRootResponse.class).getBody();
			//return send(getLoginUrl(),log, HttpMethod.POST, jsonStr, PartyRootResponse.class).getBody();
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
//						DefaultLogger.debug(this, "Request Body "+request);
//						DefaultLogger.debug(this, "Response Body "+response);
						log.setResponseMessage(WebServiceUtil.entityToString(response));
						log = logDao.createOrUpdateInterfaceLog(log);
					}
				} else {
					response = partyRestTemplate.exchange(url, method, request, clazz);
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
//						+"\nRequest: " + WebServiceUtil.entityToString(request) 
//									+ "\nResponse: " + WebServiceUtil.entityToString(response)
									);
			}
			return response;
		}
			

		private static String getLoginUrl() {
			return PropertyManager.getValue("scm.web.service.party.retrieve.endpoint");
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
//			DefaultLogger.debug(this, "jsonBody " +jsonArray);
			ObjectMapper mapper = new ObjectMapper();		
			try {
				return new ResponseEntity<T>((T) mapper.readValue(jsonArray, clazz), statusCode);
			} catch (IOException e) {
				DefaultLogger.info(this, e.getMessage(), e);
				throw new Exception("Error Parsing Custom response ", e);
			}
		}
		

		
		 public boolean processFailedPartyRequests(OBJsInterfaceLog log ) {
			 PartyRootResponse response;
			 	String scmErrorStringList=PropertyManager.getValue("error.strings.scm.job");
				System.out.println("processFailedPartyRequests => Property error.strings.scm.job scmErrorStringList=>"+scmErrorStringList);
				boolean flag = false;
				int failCount= log.getFailCount();
			 try {
//				System.out.println("SCM webservice request "+log.getRequestMessage());
				response = sendRequestForScheduler(log);
				System.out.println("processFailedPartyRequests =>After get SCM webservice response ");
				log.setResponseDateTime(DateUtil.now().getTime());
				ResponseString responseString  = response.getResponseString();
				Status statusString = response.getStatus();
				log.setResponseDateTime(DateUtil.now().getTime());
				
				if(responseString!=null) {
					System.out.println("******** inside partywebserviceclientimpl *********** processFailedPartyRequests responseString!=null ******line 183  : ");
				log.setErrorCode(String.valueOf(responseString.getStatusCode()));
				List errorMsgList = responseString.getResponseMessage();
				StringBuilder errorMsg = new StringBuilder();
				for(int i=0;i<errorMsgList.size();i++) {
					errorMsg.append(errorMsgList.get(i)); 
				}
				log.setErrorMessage(errorMsg.toString());  
				System.out.println("processFailedPartyRequests****191*****responseString.getStatusCode() : "+responseString.getStatusCode()+" if statuscode is 0, directly marking status to SUCCESS ");
				log.setStatus(responseString.getStatusCode()==0 ?"Success":"Fail");
				if(!"Success".equalsIgnoreCase(log.getStatus())){	
				try {
					System.out.println("***********inside partywebserviceclientimpl *********** processFailedPartyRequests try ");
					System.out.println("***********line 194***before add failCount  : "+failCount+", log.getErrorMessage()  : "+log.getErrorMessage()+", log.getStatus()"+log.getStatus());
					if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
				failCount=failCount+1;
				log.setFailCount(failCount);
				System.out.println(" *****198******inside if found error so increased FailCount : "+log.getFailCount());
				}
				}catch(Exception e) {
					System.out.println("**********inside partywebserviceclientimpl *********** processFailedPartyRequests catch while setting fail Count inside if for response string not null Exception  : "+e);
				}
				
				
				if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
					System.out.println("***********line 207***before checking failCount<2 or not  log.getFailCount() : "+log.getFailCount()+", log.getErrorMessage()  : "+log.getErrorMessage()+", log.getStatus()"+log.getStatus());
					if(log.getFailCount()<2) {
					if(null != scmErrorStringList && !"".equals(scmErrorStringList)) {
						String[] scmErrorStrlist = scmErrorStringList.split("|||");
							if((scmErrorStringList).contains(log.getErrorMessage())) {
								System.out.println("***********line 213**setting staus=error and flag=true for matching log.getErrorMessage() : "+log.getErrorMessage());
								log.setStatus("Error");
								flag = true;
							}else if(log.getErrorMessage().contains("20001")) {
								System.out.println("processFailedPartyRequests****217***** as error message contains 20001, setting status as success ");
								log.setStatus("Success");
							}else {
								log.setStatus("Fail");
							}
						}
					System.out.println("221 Flag =>"+flag+" .. log.getStatus()=>"+log.getStatus());
					if(flag == false && !"Success".equalsIgnoreCase(log.getStatus())) {
						log.setStatus("Fail");
					}
					
					}else {
						System.out.println("***********line 227**inside else ****as log.getFailCount() is not less than 2 setting log.getStatus() to fail");
						log.setStatus("Fail");
					}
				}
				}
				System.out.println("processFailedPartyRequests =>Before party id check condition => log.getStatus()=>"+log.getStatus()+ " .. log.getPartyId() =>"+log.getPartyId());
				
				
				if("".equals(log.getPartyId()) || null == log.getPartyId()){
					log.setStatus("Fail");
				}
				System.out.println("processFailedPartyRequests =>After party id check condition => log.getStatus()=>"+log.getStatus()+ " .. log.getPartyId() =>"+log.getPartyId()+" ..log.getId()=>"+log.getId());
				
				//Write an update to update the status table
				partyJdbc.updateTheFailedResponseLog(log);
				System.out.println("processFailedPartyRequests****242***updated the interface log table at party module for "+log.getId());
			    if(log.getStatus().equalsIgnoreCase("Success")) {
			    	return true;
			    	}else{
			    		return false;}
				}
				else {
					log.setResponseDateTime(DateUtil.now().getTime());
					log.setErrorCode(statusString.getErrorCode());
					log.setErrorMessage(statusString.getReplyText());
					log.setStatus("Error");
					flag = false;
					
					try {
						System.out.println("***********inside partywebserviceclientimpl *********** processFailedPartyRequests *****256**else for responseString=null  try**** ");
						System.out.println("***********line 257***before add failCount  : "+failCount+", log.getErrorMessage()  : "+log.getErrorMessage());
					if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
					failCount=failCount+1;
					log.setFailCount(failCount);
					System.out.println(" inside else for responseString=null****if found error so increased FailCount : "+log.getFailCount());
					}
					}catch(Exception e) {
						System.out.println("while setting fail Count inside else for response string null Exception  : "+e);
					}
					
					if(null != log.getErrorMessage()&& !"".equals(log.getErrorMessage())){
						if(null != scmErrorStringList && !"".equals(scmErrorStringList)) {
							System.out.println("***** inside else for responseString=null******line 269***before checking failCount<2 or not  log.getFailCount() : "+log.getFailCount()+", log.getStatus()"+log.getStatus());
							if(log.getFailCount()<2) {
							String[] scmErrorStrlist = scmErrorStringList.split("|||");
							if((scmErrorStringList).contains(log.getErrorMessage())) {
									System.out.println("***** inside else for responseString=null*******line 275**setting staus=error and flag=true for matching  log.getErrorMessage(): "+log.getErrorMessage());
									log.setStatus("Error");
									flag = true;
							}else if(log.getErrorMessage().contains("20001")) {
								System.out.println("processFailedPartyRequests****281***** as error message contains 20001, setting status as success ");
								log.setStatus("Success");
								}else {
									log.setStatus("Fail");
								}
						  }
						}
						System.out.println("284 Flag =>"+flag+" .. log.getStatus()=>"+log.getStatus());
						if(flag == false && !"Success".equalsIgnoreCase(log.getStatus())) {
							System.out.println("***** inside else for responseString=null*******line 286**inside if flag=false and status=!success setting log.getStatus() to fail");
							log.setStatus("Fail");
						}
						

					}
					

					
					if("".equals(log.getPartyId()) || null == log.getPartyId()){
						log.setStatus("Fail");
					}
					System.out.println("processFailedPartyRequests => else => log.getStatus()=>"+log.getStatus());
					partyJdbc.updateTheFailedResponseLog(log);
					System.out.println("processFailedPartyRequests****297***updated the interface log table at party module for response string null for "+log.getId());

					return false;
				}
			} catch (Exception e) {
				System.out.println("Error in processing the data for "+ log.getLimitProfileId()+" "+log.getLine_no()+" "+log.getSerial_no());
				log.setErrorCode("9");
				log.setErrorMessage(e.getMessage()); 
				log.setStatus("Error");
				flag = false;
				
				try {
					System.out.println(" inside catch***309******** failCount : "+failCount);
				if(null != log.getErrorMessage()  && !"".equals(log.getErrorMessage())){
				failCount=failCount+1;
				log.setFailCount(failCount);
				System.out.println(" inside catch****313* if found error so increased FailCount : "+log.getFailCount());
				}
				}catch(Exception e1) {
					System.out.println("inside catch*****316****** while setting fail Count Exception inside catch  : "+e1);
				}
				
				if(null != log.getErrorMessage() && !"".equals(log.getErrorMessage())){
					if(null != scmErrorStringList && !"".equals(scmErrorStringList)) {
						System.out.println("***** inside catch******line 321*** log.getFailCount() : "+log.getFailCount()+", log.getStatus()"+log.getStatus());
						
						if(log.getFailCount()<2) {
						String[] scmErrorStrlist = scmErrorStringList.split("|||");
						if((scmErrorStringList).contains(log.getErrorMessage())) {
								System.out.println("***** inside catch***327******setting staus=error and flag=true for matching log.getErrorMessage() : "+log.getErrorMessage());
								log.setStatus("Error");
								flag = true;
						}else if(log.getErrorMessage().contains("20001")) {
							System.out.println("processFailedPartyRequests****335***** as error message contains 20001, setting status as success ");
							log.setStatus("Success");
							}else {
								log.setStatus("Fail");
							}
						}
						}
					System.out.println("336 Flag =>"+flag+" .. log.getStatus()=>"+log.getStatus());
					
					if(flag == false && !"Success".equalsIgnoreCase(log.getStatus())) {
						System.out.println("***** inside catch*******line 329**inside if flag=false and status=!success setting log.getStatus() to fail");
						log.setStatus("Fail");
					}
				}

				if("".equals(log.getPartyId()) || null == log.getPartyId()){
					log.setStatus("Fail");
				}
				System.out.println("Exception processFailedPartyRequests => Catch => log.getStatus()=>"+log.getStatus());
				partyJdbc.updateTheFailedResponseLog(log);
				System.out.println("processFailedPartyRequests****349****inside catch***updated the interface log table at party module for "+log.getId());
				e.printStackTrace();
				return false;
			}
		 }
		 
		 
		 public PartyRootResponse sendRequestForScheduler(OBJsInterfaceLog log) throws Exception {
			    System.out.println("Calling Send method for Scheduler for party/CAM");
			    try {
				    partyDetailsForScheduler = new ObjectMapper().readValue(log.getRequestMessage(), PartyRootRequest.class);  
				    }catch(Exception e){
				    	System.out.println("Exception in sendRequestForScheduler() for partyDetailsForScheduler variable.");//e is not printed because of sensitive data issue.
				    }
				HttpEntity<Object> request = new HttpEntity<Object>(log.getRequestMessage(),WebServiceUtil.createScmPartyHttpHeader());
				System.out.println("Printing request details before sending the data");
				System.out.println(getLoginUrl());
//				System.out.println(request);
//				System.out.println(request.toString());
				//return send(getLoginUrl(),log, HttpMethod.POST, log.getRequestMessage(), PartyRootResponse.class).getBody();
			    return sendforScheduler(getLoginUrl(),log, HttpMethod.POST,request , PartyRootResponse.class).getBody();
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
//							System.out.println("Request Body "+request);
//							System.out.println("Response Body "+response);
							log.setResponseMessage(WebServiceUtil.entityToString(response));
							//log = logDao.createOrUpdateInterfaceLog(log);
						}
					} else {
						System.out.println("Since its not dev sending data to SCM through rest");
						response = partyRestTemplate.exchange(url, method, request, clazz);
						log.setResponseMessage(WebServiceUtil.entityToString(response));
						//log = logDao.createOrUpdateInterfaceLog(log);
					}
				} catch (Exception hsce) {
					System.out.println("Inside exception while sendin the data "+ hsce.getMessage());
					log.setErrorCode("9");
					log.setErrorMessage(hsce.getMessage());
					log.setStatus("Error");
					partyJdbc.updateTheFailedResponseLog(log);
					throw new Exception("Error Parsing Custom response ", hsce);
				}  finally {
					DefaultLogger.info(this, "URI: "+url
//							+"\nRequest: " + WebServiceUtil.entityToString(request) 
//										+ "\nResponse: " + WebServiceUtil.entityToString(response)
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
		public RestTemplate partyRestTemplate() {
		    return new RestTemplate();
		}
}
