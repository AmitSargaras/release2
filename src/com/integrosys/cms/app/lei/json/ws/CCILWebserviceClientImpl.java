package com.integrosys.cms.app.lei.json.ws;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.io.BufferedInputStream;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.InputStream;
import java.nio.charset.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLogDao;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLog;
import com.integrosys.cms.app.lei.json.dto.LEIDetailsResponseDto;
import com.integrosys.cms.app.lei.json.dao.OBLEIValidationLog;
import com.integrosys.cms.app.lei.json.endpoint.ILEIService;
import com.integrosys.cms.app.lei.json.helper.ILeiResponseHelper;
import com.integrosys.cms.app.lei.json.helper.LeiResponseHelperImpl;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILRequest;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILResponse;
import com.integrosys.cms.app.lei.json.ws.WebServiceUtil;
import com.integrosys.cms.app.lei.bus.ILeiJdbc;
import com.integrosys.cms.ui.manualinput.customer.ValidateLEICodeWithCCILCommand;
import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import com.integrosys.cms.app.lei.json.dto.LeiResponseDTO;
import com.integrosys.cms.app.lei.json.dto.LeiRequestDTO;

public class CCILWebserviceClientImpl  implements ICCILWebserviceClient {

		@Autowired
		@Qualifier("restTemplate")
		private RestTemplate restTemplate;
		
		private LeiRequestDTO leiRequestObj;
		private static final Charset UTF_8 = Charset.forName("UTF-8");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss +05:30");
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		
		@Autowired
        private ILEIService leiServiceEndPoint;
		
		@Autowired
        private ILeiResponseHelper leiResponseHelper;
		
		ILEIValidationLogDao logDao = (ILEIValidationLogDao) BeanHouse.get("logLeiDao");
		ILeiJdbc leiJdbc = (ILeiJdbc)BeanHouse.get("leiJdbc");		
  
		public RetrieveLEICCILResponse sendRetrieveRequest(String leiCode,ICMSCustomer obCustomer,ILEIValidationLog log, String customerCifId) throws Exception {
			
			DefaultLogger.debug(this,"<<<<<<<<<<<<<<<<<<<<<<<<<Inside sendRetrieveRequest() >>>>>>>>>>>>>>>>>>>>> ");
			leiRequestObj =  leiServiceEndPoint.getLeiRequest(leiCode);
			DefaultLogger.debug(this,"JSON LEI Request Obj : "+leiRequestObj);
			ObjectMapper Obj = new ObjectMapper();  
			String jsonRequestStr = Obj.writeValueAsString(leiRequestObj);
			DefaultLogger.info(this, "JSON LEI Request : " + jsonRequestStr);
			log.setInterfaceName(getLoginUrl());
			log.setRequestMessage(jsonRequestStr);
			if(obCustomer != null) {
			log.setPartyId(obCustomer.getCifId()!=null?obCustomer.getCifId():"");
			log.setCmsMainProfileID(obCustomer.getCustomerID()!=0l?obCustomer.getCustomerID():0L);
			}else {
			log.setPartyId(customerCifId);
			log.setCmsMainProfileID(0L);
			}
			log.setLeiCode(leiCode);			
			log.setRequestDateTime(Calendar.getInstance().getTime());
			log = logDao.insertInterfaceLog(log);
			try {
				return Obj.readValue(send(getLoginUrl(), log, jsonRequestStr, obCustomer, leiRequestObj), RetrieveLEICCILResponse.class);
			}catch(Exception e) {
				DefaultLogger.info(this, "Got Exception in sendRetrieveRequest method : " + e);
				return null;
			}
		}
			
		private String send(String url, ILEIValidationLog log, String jsonRequestStr, ICMSCustomer obCustomer,LeiRequestDTO leiRequestObj) throws Exception {
			String response = null;
			String leiResponseStr = null; 
			LeiResponseDTO jsonResponseObj = null;
			RetrieveLEICCILResponse mockJsonResponseObj = null;
			ILeiResponseHelper leiResponseHelper = (ILeiResponseHelper) BeanHouse.get("leiResponseHelper") ;
			boolean value = false;
			DefaultLogger.debug(this, "Env: " + PropertyManager.getValue("lei.deployment.environment"));
			try {
				if (PropertyManager.getValue("lei.deployment.environment").equalsIgnoreCase("LOCAL")) {
					jsonResponseObj = leiResponseHelper.mockLeiResponse(obCustomer,leiRequestObj);
					response = customResponse(url,jsonResponseObj);
					ObjectMapper objectMapper = new ObjectMapper();
					jsonResponseObj = objectMapper.readValue(response, LeiResponseDTO.class);
					if(jsonResponseObj.getStatus().getErrorCode().equals("0")) {
						leiResponseStr = objectMapper.writeValueAsString(jsonResponseObj.getResponseString());					
						value = getCert(jsonResponseObj.getResponseString());
						if(value==true) {
							log.setResponseMessage(response);
							log.setErrorCode(jsonResponseObj.getResponseString().getErrorCode());
							log.setErrorMessage(jsonResponseObj.getResponseString().getErrorMessage());
							log = logDao.createOrUpdateInterfaceLog(log);						
							return leiResponseStr;
						}else {
							log.setResponseMessage("");
							log.setErrorCode("LAE014");
							log.setErrorMessage("Authentication Failed.");
							log = logDao.createOrUpdateInterfaceLog(log);						
							return null;
						}	
					}else {
						log.setResponseMessage(response);
						log.setErrorCode(jsonResponseObj.getStatus().getErrorCode());
						log.setErrorMessage(jsonResponseObj.getStatus().getReplyText());
						log = logDao.createOrUpdateInterfaceLog(log);
						return null;						
					}
				} else if (PropertyManager.getValue("lei.deployment.environment").equalsIgnoreCase("SERVER")) {
					ObjectMapper objectMapper = new ObjectMapper();
					jsonResponseObj = restTemplate.postForObject(url, leiRequestObj, LeiResponseDTO.class);
					response = objectMapper.writeValueAsString(jsonResponseObj);
					DefaultLogger.info(this, "LEI Response >>>> " +response);
					if(jsonResponseObj.getStatus().getErrorCode().equals("0")) {
						leiResponseStr = objectMapper.writeValueAsString(jsonResponseObj.getResponseString());					
						DefaultLogger.info(this, "JSON LEI Details Response >>>> " +leiResponseStr);
						value = getCert(jsonResponseObj.getResponseString());
						DefaultLogger.info(this, "LEI Signature getCert response (value) >>>> " +value);
						if(value==true) {
							log.setResponseMessage(response);
							log.setErrorCode(jsonResponseObj.getResponseString().getErrorCode());
							log.setErrorMessage(jsonResponseObj.getResponseString().getErrorMessage());
							log = logDao.createOrUpdateInterfaceLog(log);
							return leiResponseStr;
						}else {
							log.setResponseMessage(response);
							log.setErrorCode(jsonResponseObj.getResponseString().getErrorCode());
							log.setErrorMessage(jsonResponseObj.getResponseString().getErrorMessage());
							log = logDao.createOrUpdateInterfaceLog(log);
							return null;
						}
					}else {
						log.setResponseMessage(response);
						log.setErrorCode(jsonResponseObj.getStatus().getErrorCode());
						log.setErrorMessage(jsonResponseObj.getStatus().getReplyText());
						log = logDao.createOrUpdateInterfaceLog(log);
						return null;						
					}
				} else {
					return null;
				}
			} catch (Exception e) {
				DefaultLogger.error(this, e.getMessage(), e);
				log.setErrorCode("");
				log.setErrorMessage(e.getMessage());
				log.setStatus("Fail");
				log = logDao.createOrUpdateInterfaceLog(log);
				throw new Exception("Error Parsing Custom response ", e);
			}  finally {
				DefaultLogger.info(this, "URI: "+url+"\nRequest: " + jsonRequestStr 
									+ "\nResponse: " + response);
			}
		}
			

		private boolean getCert(RetrieveLEICCILResponse response) {

			String pkey = PropertyManager.getValue("ccil.lei.certificate");
			String sKey = PropertyManager.getValue("ccil.lei.secretKey");

			try {

				String signature = response.getReferenceNumber()+response.getLeiDetails()+
						response.getValidityStatus()+response.getHttpStatusCode()+
						response.getErrorCode()+response.getErrorMessage();
				
				String secretKey=null;
				StringBuilder builder = new StringBuilder();
				BufferedReader buffer = new BufferedReader(new FileReader(sKey));
				while ((secretKey = buffer.readLine()) != null) {				 
					builder.append(secretKey);
				}   
				buffer.close();
				secretKey = builder.toString();			
				Mac hasher = Mac.getInstance("HmacSHA256");
				hasher.init(new SecretKeySpec(secretKey.getBytes(UTF_8), "HmacSHA256"));				    
				byte[] hash = hasher.doFinal(signature.getBytes(UTF_8));				    
				String signHash = DatatypeConverter.printBase64Binary(hash);
				
				PublicKey pubKey = extractPublicKeyFromCertFile(pkey);

				byte[] sign = DatatypeConverter.parseBase64Binary(response.getSignature()); // for decoding purpose 
				byte[] data = DatatypeConverter.parseBase64Binary(signHash);
			
				return verifySignature(pubKey,data,sign);

			} catch (IOException e) {
				DefaultLogger.error(this, "In getCert method : IOException >>>> " +e);
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				DefaultLogger.error(this, "In getCert method : NoSuchAlgorithmException >>>> " +e);
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				DefaultLogger.error(this, "In getCert method : InvalidKeyException >>>> " +e);
				e.printStackTrace();
			} catch(ArrayIndexOutOfBoundsException e) {
				DefaultLogger.error(this, "In getCert method : ArrayIndexOutOfBoundsException >>>> " +e);
				e.printStackTrace();
			} 
		
			return false;

		}


		private boolean verifySignature(PublicKey pubKey, byte[] originalData, byte[] singature) {

			try{
				
				Signature sig = Signature.getInstance("SHA1WithRSA");
	            sig.initVerify(pubKey);
	            sig.update(originalData);
	        
	            return sig.verify(singature);

			} catch (NoSuchAlgorithmException e) {
				DefaultLogger.error(this, "In verifySignature method : NoSuchAlgorithmException >>>> " +e);
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				DefaultLogger.error(this, "In verifySignature method : InvalidKeyException >>>> " +e);
				e.printStackTrace();
			} catch(SignatureException e) {
				DefaultLogger.error(this, "In verifySignature method : SignatureException >>>> " +e);
				e.printStackTrace();
			} 
			
			return false;
		}
	    
		private static String getLoginUrl() {
			return PropertyManager.getValue("ccil.web.service.lei.retrieve.endpoint");
		}
		
		
		private String customResponse(String url,LeiResponseDTO response) throws Exception  {

			ObjectMapper mapper = new ObjectMapper();	
			LEIDetailsResponseDto myLeiObject = null;			
			try {
				myLeiObject = mapper.readValue(response.getResponseString().getLeiDetails(), LEIDetailsResponseDto.class);
				DefaultLogger.debug(this, "Mock jsonResponse : " +mapper.writeValueAsString(myLeiObject));
			} catch (IOException e) {
				DefaultLogger.info(this, e.getMessage(), e);
				throw new Exception("Error Parsing Custom response ", e);
			}
			
			String jsonLeiStr  = "{\\\"LEINumber\\\":\\\""+myLeiObject.getLeiNumber()+"\\\",\\\"LegalName\\\":\\\""+myLeiObject.getLegalName()+"\\\",\\\"NextRenewalDate\\\":\\\""+myLeiObject.getNextRenewalDate()+"\\\",\\\"LegalForm\\\":\\\""+myLeiObject.getLegalForm()+"\\\",\\\"RegistrationStatus\\\":\\\""+myLeiObject.getRegistrationStatus()+"\\\"}";
					
			String jsonArray = 		"{\r\n" + 
									"   \"status\":{\r\n" + 
									"   \"isOverriden\":false,\r\n" + 
									"   \"replyCode\":0,\r\n" + 
									"   \"replyText\":\""+response.getStatus().getReplyText()+"\",\r\n" + 
									"   \"memo\":null,\r\n" +
									"   \"externalReferenceNo\":\""+response.getStatus().getExternalReferenceNo()+"\",\r\n" +
									"   \"internalReferenceNumber\":\""+response.getStatus().getInternalReferenceNumber()+"\",\r\n" +
									"   \"postingDate\":{\r\n" + 
									"      \"dateString\":\""+response.getStatus().getPostingDate().getDateString()+"\"\r\n" + 
									"      },\r\n" + 
									"   \"errorCode\":\""+response.getStatus().getErrorCode()+"\",\r\n" +
									"   \"extendedReply\":{\r\n" + 
									"      \"messages\":null\r\n" + 
									"      },\r\n" + 
									"   \"validationErrors\":null,\r\n" +
									"   \"userReferenceNumber\":null\r\n" +
									"	},\r\n" + 
									"   \"maintenanceType\":null,\r\n" +
									"   \"configVersionId\":null,\r\n" +									
									"   \"responseString\":{\r\n" + 
									"   \"ReferenceNumber\":\""+response.getResponseString().getReferenceNumber()+"\",\r\n" + 
									"   \"ValidityStatus\":\""+response.getResponseString().getValidityStatus()+"\",\r\n" + 
									"   \"LEIDetails\":\""+jsonLeiStr+"\",\r\n" +
									"   \"HttpStatusCode\":200,\r\n" +
									"   \"ErrorMessage\":\""+response.getResponseString().getErrorMessage()+"\",\r\n" +
									"   \"ErrorCode\":\""+response.getResponseString().getErrorCode()+"\",\r\n" +
									"   \"Signature\":\""+response.getResponseString().getSignature()+"\"\r\n" +
									"	}\r\n" + 
									"}";
			
			try {
				LeiResponseDTO myObject = mapper.readValue(jsonArray, LeiResponseDTO.class);
				DefaultLogger.debug(this, "Mock jsonResponse : " +mapper.writeValueAsString(myObject));
				return mapper.writeValueAsString(myObject);
			} catch (IOException e) {
				DefaultLogger.info(this, e.getMessage(), e);
				throw new Exception("Error Parsing Custom response ", e);
			}
		}
		
 	  public boolean processUploadLeiRequests(OBLeiDetailsFile obLeiDetailsFile) {
		  RetrieveLEICCILResponse response = null;
		  boolean flag = false;
		  ILEIValidationLog log = new OBLEIValidationLog();
			try{
			    if(obLeiDetailsFile.getLeiValidationFlag().equals("Y")) {
					response = sendRequestForScheduler(obLeiDetailsFile,log);
				}else {
					DefaultLogger.debug(this, "No Lei_Code for validate LEI...");
					return flag;
				}
				DefaultLogger.debug(this, "LEI webservice response "+response);
				if(response!=null) {
					log.setResponseDateTime(DateUtil.now().getTime());
					log.setErrorCode(response.getErrorCode());
					log.setErrorMessage(response.getErrorMessage());
					String code = response.getErrorCode();
					if(PropertyManager.getValue("ccil.success.code").equals(code)) {						
						ObjectMapper mapper = new ObjectMapper();	
						LEIDetailsResponseDto myLeiObject = null;			
						try {
							myLeiObject = mapper.readValue(response.getLeiDetails(), LEIDetailsResponseDto.class);
						} catch (IOException e) {
							DefaultLogger.info(this, e.getMessage(), e);
							throw new Exception("Error Parsing response ", e);
						}
						java.util.Date leiExpDateUtil =format.parse(myLeiObject.getNextRenewalDate().substring(0,10));
					    java.sql.Date leiExpDate = new java.sql.Date(leiExpDateUtil.getTime());
						leiJdbc.updateMainProfileValidatedLeiExpiryDateFromScheduler(log.getPartyId(), log.getLeiCode(),leiExpDate);
						leiJdbc.updateSubProfileValidatedLeiExpiryDateFromScheduler(log.getPartyId(), log.getLeiCode(),leiExpDate);
						log.setIsLEICodeValidated('Y');
						log.setStatus("Success");
						log.setLastValidatedBy("Scheduler");
						log.setLastValidatedDate(Calendar.getInstance().getTime());	
						flag = true;
					}else {
						log.setIsLEICodeValidated('N');
						log.setStatus("Fail");						
					}
					log = logDao.createOrUpdateInterfaceLog(log);
				}else {
					log.setResponseDateTime(DateUtil.now().getTime());
//					log.setErrorCode("LAE014");
//					log.setErrorMessage("Authentication Failed.");
					log.setStatus("Fail");
					log.setIsLEICodeValidated('N');
					log = logDao.createOrUpdateInterfaceLog(log);
				}
			}catch(Exception ex){
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				log.setResponseDateTime(DateUtil.now().getTime());
				log.setErrorCode("");
				log.setErrorMessage("Exception occurred during request");
				log.setStatus("Fail");
				log.setIsLEICodeValidated('N');
				try {
					log = logDao.createOrUpdateInterfaceLog(log);
				}catch(Exception e) {
					DefaultLogger.debug(this, "got exception in update of log table" + e);
					e.printStackTrace();
				}
				ex.printStackTrace();
			}
			return flag;
		}			 
		 
		 public RetrieveLEICCILResponse sendRequestForScheduler(OBLeiDetailsFile obLeiDetailsFile,ILEIValidationLog log) throws Exception {
			    System.out.println("Calling Send method for Scheduler for LEI");
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				ICMSCustomer customer = custProxy.getCustomerByCIFSource(obLeiDetailsFile.getPartyId().trim(),null);
			    if (customer != null) {
					log.setCmsMainProfileID(customer.getCustomerID()!=0l?customer.getCustomerID():0L);					
				}else {
					log.setCmsMainProfileID(0L);
				}
				leiRequestObj =  leiServiceEndPoint.getLeiRequest(obLeiDetailsFile.getLeiCode());
				DefaultLogger.debug(this,"JSON LEI Request Obj : "+leiRequestObj);
				ObjectMapper Obj = new ObjectMapper();  
				String jsonRequestStr = Obj.writeValueAsString(leiRequestObj);
				DefaultLogger.info(this, "JSON LEI Request : " + jsonRequestStr);
				log.setInterfaceName(getLoginUrl());
				log.setRequestMessage(jsonRequestStr);
				log.setPartyId(obLeiDetailsFile.getPartyId());			
				log.setRequestDateTime(Calendar.getInstance().getTime());
				log.setLeiCode(obLeiDetailsFile.getLeiCode());
				log = logDao.insertInterfaceLog(log);
				try {
					return  Obj.readValue(sendforScheduler(getLoginUrl(), log, jsonRequestStr,leiRequestObj,customer),RetrieveLEICCILResponse.class);
				}catch(Exception e) {
					DefaultLogger.info(this, "Got Exception in sendRequestForScheduler method : " + e);
					return null;
				}		 
		 }
		 private String sendforScheduler(String url,ILEIValidationLog log,String jsonRequestStr, LeiRequestDTO leiRequestObj, ICMSCustomer customer) throws Exception {
				String response = null;
				String leiResponseStr = null; 
				LeiResponseDTO jsonResponseObj = null;
				RetrieveLEICCILResponse mockJsonResponseObj = null;
				ILeiResponseHelper leiResponseHelper = (ILeiResponseHelper) BeanHouse.get("leiResponseHelper") ;
				boolean value = false;
				DefaultLogger.debug(this, "Env: " + PropertyManager.getValue("lei.deployment.environment"));
				try {
					if (PropertyManager.getValue("lei.deployment.environment").equalsIgnoreCase("LOCAL")) {
						jsonResponseObj = leiResponseHelper.mockLeiResponse(customer,leiRequestObj);
						response = customResponse(url,jsonResponseObj);
						ObjectMapper objectMapper = new ObjectMapper();
						jsonResponseObj = objectMapper.readValue(response, LeiResponseDTO.class);
						if(jsonResponseObj.getStatus().getErrorCode().equals("0")) {
							leiResponseStr = objectMapper.writeValueAsString(jsonResponseObj.getResponseString());					
							value = getCert(jsonResponseObj.getResponseString());
							if(value==true) {
								log.setResponseMessage(response);
								log.setErrorCode(jsonResponseObj.getResponseString().getErrorCode());
								log.setErrorMessage(jsonResponseObj.getResponseString().getErrorMessage());
								log = logDao.createOrUpdateInterfaceLog(log);						
								return leiResponseStr;
							}else {
								log.setResponseMessage("");
								log.setErrorCode("LAE014");
								log.setErrorMessage("Authentication Failed.");
								log = logDao.createOrUpdateInterfaceLog(log);						
								return null;
							}
						}else {
							log.setResponseMessage(response);
							log.setErrorCode(jsonResponseObj.getStatus().getErrorCode());
							log.setErrorMessage(jsonResponseObj.getStatus().getReplyText());
							log = logDao.createOrUpdateInterfaceLog(log);
							return null;						
						}

					} else if (PropertyManager.getValue("lei.deployment.environment").equalsIgnoreCase("SERVER")) {
						ObjectMapper objectMapper = new ObjectMapper();
						jsonResponseObj = restTemplate.postForObject(url, leiRequestObj, LeiResponseDTO.class);
						response = objectMapper.writeValueAsString(jsonResponseObj);
						DefaultLogger.info(this, "LEI Response >>>> " +response);
						if(jsonResponseObj.getStatus().getErrorCode().equals("0")) {
							leiResponseStr = objectMapper.writeValueAsString(jsonResponseObj.getResponseString());					
							DefaultLogger.info(this, "JSON LEI Details Response >>>> " +leiResponseStr);
							value = getCert(jsonResponseObj.getResponseString());				
							if(value==true) {
								log.setResponseMessage(response);
								log.setErrorCode(jsonResponseObj.getResponseString().getErrorCode());
								log.setErrorMessage(jsonResponseObj.getResponseString().getErrorMessage());
								log = logDao.createOrUpdateInterfaceLog(log);
								return leiResponseStr;
							}else {
								log.setResponseMessage(response);
								log.setErrorCode(jsonResponseObj.getResponseString().getErrorCode());
								log.setErrorMessage(jsonResponseObj.getResponseString().getErrorMessage());
								log = logDao.createOrUpdateInterfaceLog(log);
								return null;
							}
						}else {
							log.setResponseMessage(response);
							log.setErrorCode(jsonResponseObj.getStatus().getErrorCode());
							log.setErrorMessage(jsonResponseObj.getStatus().getReplyText());
							log = logDao.createOrUpdateInterfaceLog(log);
							return null;						
						}
					} else {
						return null;
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage(), e);
					log.setErrorCode("");
					log.setErrorMessage(e.getMessage());
					log.setStatus("Fail");
					log = logDao.createOrUpdateInterfaceLog(log);
					throw new Exception("Error Parsing Custom response ", e);
				}  finally {
					DefaultLogger.info(this, "URI: "+url+"\nRequest: " + jsonRequestStr 
										+ "\nResponse: " + response);
				}
		}		 
		 
		public ILEIService getLeiServiceEndPoint() {
			return leiServiceEndPoint;
		}


		public void setLeiServiceEndPoint(ILEIService leiServiceEndPoint) {
			this.leiServiceEndPoint = leiServiceEndPoint;
		}


		public ILeiResponseHelper getLeiResponseHelper() {
			return leiResponseHelper;
		}


		public void setLeiResponseHelper(ILeiResponseHelper leiResponseHelper) {
			this.leiResponseHelper = leiResponseHelper;
		}
		
		@Bean
		public RestTemplate restTemplate() {
		    return new RestTemplate();
		}
		
    	// Load the cert file as X509Certificate and return PublicKey
	    private PublicKey extractPublicKeyFromCertFile(String filePath) {
	    	
	    	try {	
	    		
	    		byte[] certificateBytes = readBytesFromFile(filePath);
	            // Load the certificate
	            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
	            X509Certificate x509Cert = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(certificateBytes));
	        	// Extract the public key from the certificate
	        	PublicKey publicKey = x509Cert.getPublicKey();
	        	
	        	return publicKey;	        	
	       
			} catch (IOException e) {
				DefaultLogger.error(this, "In extractPublicKeyFromCertFile method : IOException >>>> " +e);
				e.printStackTrace();
			} catch(ArrayIndexOutOfBoundsException e) {
				DefaultLogger.error(this, "In extractPublicKeyFromCertFile method : ArrayIndexOutOfBoundsException >>>> " +e);
				e.printStackTrace();
			} catch(CertificateException e) {
				DefaultLogger.error(this, "In extractPublicKeyFromCertFile method : CertificateException >>>> " +e);
				e.printStackTrace();
			} 

	    	return null;
	    }

	    
	    private static byte[] readBytesFromFile(String filePath) throws IOException {
	        FileInputStream fileInputStream = null;
	        ByteArrayOutputStream outputStream = null;
	        
	        try {
	            fileInputStream = new FileInputStream(filePath);
	            outputStream = new ByteArrayOutputStream();
	            
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            
	            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	            
	            return outputStream.toByteArray();
	        } finally {
	            if (fileInputStream != null) {
	                fileInputStream.close();
	            }
	            if (outputStream != null) {
	                outputStream.close();
	            }
	        }
	    }

}

