package com.integrosys.cms.app.lei.json.helper;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.lei.json.dto.ExtendedReply;
import com.integrosys.cms.app.lei.json.dto.LEIDetailsResponseDto;
import com.integrosys.cms.app.lei.json.dto.LeiRequestDTO;
import com.integrosys.cms.app.lei.json.dto.LeiResponseDTO;
import com.integrosys.cms.app.lei.json.dto.PostingDate;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILRequest;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILResponse;
import com.integrosys.cms.app.lei.json.dto.StatusDTO;
import com.integrosys.cms.app.lei.json.endpoint.ILEIService;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;

public class LeiResponseHelperImpl implements ILeiResponseHelper{
	
	
	@Autowired
    private ILEIService leiServiceEndPoint;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss +05:30");
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
	
	public LeiResponseDTO mockLeiResponse(ICMSCustomer obCustomer,LeiRequestDTO leiRequestDTO) throws Exception {

		LeiResponseDTO leiResponseDTO = new LeiResponseDTO();
		StatusDTO statusDTO = new StatusDTO();
		PostingDate postingDate = new PostingDate();
		ExtendedReply extendedReply = new ExtendedReply();
		RetrieveLEICCILResponse response=new RetrieveLEICCILResponse();
		LEIDetailsResponseDto leiDetailsResponse = new LEIDetailsResponseDto(); 
		Random rand = new Random();
		statusDTO.setIsOverriden(false);
		statusDTO.setErrorCode("0");
		statusDTO.setReplyCode(0L);
		statusDTO.setReplyText("0");
		statusDTO.setMemo(null);
		statusDTO.setExternalReferenceNo(leiRequestDTO.getSessionContext().getExternalReferenceNo());
		statusDTO.setInternalReferenceNumber("32842342"+String.valueOf(rand.nextInt(2147483500)));
		postingDate.setDateString("20221104000000");
		statusDTO.setPostingDate(postingDate);
		extendedReply.setMessages(null);
		statusDTO.setExtendedReply(extendedReply);
		statusDTO.setValidationErrors(null);
		statusDTO.setUserReferenceNumber(null);
		
		leiResponseDTO.setStatus(statusDTO);
		leiResponseDTO.setMaintenanceType(null);
		leiResponseDTO.setConfigVersionId(null);
		
		String validityStatus = "LAV03-Business Identifier is not provided";
		
		if(obCustomer != null) {
			if(obCustomer.getCustomerName()!=null || !obCustomer.getCustomerName().isEmpty()) {
				leiDetailsResponse.setLegalName(obCustomer.getCustomerName());
			}else {
				leiDetailsResponse.setLegalName("XYZ");
			}
			
			if(obCustomer.getEntity()!=null || !obCustomer.getEntity().isEmpty()) {
				try {
					Object obj = masterObj.getObjectByEntityNameAndEntryCode("entryCode", obCustomer.getEntity().trim(),"Entity");
					if (null != obj) {
						leiDetailsResponse.setLegalForm(((ICommonCodeEntry) obj).getEntryName());
					} else {
						leiDetailsResponse.setLegalForm("OTHER");
					}
				} catch (Exception e) {
					leiDetailsResponse.setLegalForm("OTHER");
				} 
			}else {
				leiDetailsResponse.setLegalForm("OTHER");
			}
			
		}else {
			leiDetailsResponse.setLegalName("XYZ");
			leiDetailsResponse.setLegalForm("OTHER");
		}

		leiDetailsResponse.setLeiNumber(leiRequestDTO.getValidateLEINumFromCCILRequestDTO().getRequestString().getLeiNumber());

		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE,(int)(Math.random()*(501)-100));
		dt = c.getTime();
		leiDetailsResponse.setNextRenewalDate(dateFormat.format(dt));
				
		response.setReferenceNumber(leiRequestDTO.getValidateLEINumFromCCILRequestDTO().getRequestString().getReferenceNumber());
		response.setValidityStatus(validityStatus);
		response.setErrorCode(returnErrorCode(leiRequestDTO.getValidateLEINumFromCCILRequestDTO().getRequestString()));
		response.setErrorMessage(returnErrorMessage(returnErrorCode(leiRequestDTO.getValidateLEINumFromCCILRequestDTO().getRequestString())));
		if(response.getErrorCode() == "LAE000") {
			response.setHttpStatusCode(200);
		}else {
			response.setHttpStatusCode(400);
		}
		if(response.getErrorCode() == "LAE000") {
			leiDetailsResponse.setRegistrationStatus("ISSUED");
		}else {
			leiDetailsResponse.setRegistrationStatus("NOT ISSUED");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonLeiDetails =null;
		try {
			jsonLeiDetails = mapper.writeValueAsString(leiDetailsResponse);
		}catch(Exception e){
			e.getMessage();
		}
		
		response.setLeiDetails(jsonLeiDetails);
		response.setLeiDetails(response.getLeiDetails());				
		response.setSignature(returnSignature(response));
		leiResponseDTO.setResponseString(response);

		return leiResponseDTO;
		
	}

	private String returnSignature(RetrieveLEICCILResponse response) {
		
	    String signature = response.getReferenceNumber()+response.getLeiDetails()+
	   			  		   response.getValidityStatus()+response.getHttpStatusCode()+
	   			  		   response.getErrorCode()+response.getErrorMessage();

		String output = null;
		String secretKey = null;
		try {
			String sKey = PropertyManager.getValue("ccil.lei.secretKey");
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
		    		    
		    output = signedSignature(hash);
		    
	  	}
	  	catch (NoSuchAlgorithmException e) {e.printStackTrace();}
	  	catch (InvalidKeyException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
		return output;
	}

	private String signedSignature(byte[] signHash) {

		   String pkey = PropertyManager.getValue("ccil.lei.privateKey");

		   try {

			   byte[] privateKeyBytes = readBytesFromFile(pkey);
			   PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			   KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			   PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			   Signature sig = Signature.getInstance("SHA1WithRSA");
			   sig.initSign(privateKey);
	           sig.update(signHash);	           
	           return DatatypeConverter.printBase64Binary(sig.sign());  
	           
		   } catch(NoSuchAlgorithmException e1) {				
			   e1.printStackTrace();
		   } catch(InvalidKeyException e2) {
			   e2.printStackTrace();
		   } catch(SignatureException e3) {
			   e3.printStackTrace();
		   } catch (FileNotFoundException e2) {
			   e2.printStackTrace();
		   } catch (InvalidKeySpecException e5) {
			   e5.printStackTrace();
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
		   
		return null;
	}

	private String returnErrorMessage(String code) {
		int value =Integer.parseInt(code.substring(3));
		String em=null;
		switch (value) {
		  case 0:
			  em="Success.";
		    break;
		  case 1:
			  em="LEI Number is mandatory.";
		    break;
		  case 2:
			  em="LEI Number is not as per valid ISO Format.";
		    break;
		  case 3:
			  em="LEI Number is not found in CCIL Repository.";
		    break;
		  case 4:
			  em="Concurrent Connection has reached to Max Limit.";
		    break;
		  case 5:
			  em="Requests allowed per day have reached to Max limit.";
		    break;
		  case 6:
			  em="Client ID is mandatory.";
		    break;
		  case 7:
			  em="Please enter valid Client ID.";
		    break;
		  case 8:
			  em="Client Reference Number is mandatory.";
		    break;
		  case 9:
			  em="Please enter valid Client Reference Number.";
		    break;
		  case 10:
			  em="Technical Error.";
		    break;
		  case 11:
			  em="Checksum is mandatory.";
		    break;
		  case 12:
			  em="Invalid Checksum.";
		    break;
		  case 13:
			  em="Invalid Identifier.";
		    break;
		  case 14:
			  em="Authentication Failed.";
		    break;
		}    
		return em;
	}

	private String returnErrorCode(RetrieveLEICCILRequest leiRequestDTO) {
		String ec=null;
		if(leiRequestDTO.getLeiNumber().trim()=="") {
			ec="LAE001";
		}else if(!isValidLEI(leiRequestDTO.getLeiNumber())) {
			ec="LAE002";
		}else if(leiRequestDTO.getClientId().trim()=="") {
			ec="LAE006";
		}else if(leiRequestDTO.getClientId().length()!=8) {
			ec="LAE007";
		}else if(leiRequestDTO.getReferenceNumber().trim()=="") {
			ec="LAE008";
		}else if(leiRequestDTO.getReferenceNumber().length()<1 || leiRequestDTO.getReferenceNumber().length()>20 ) {
			ec="LAE009";
		}else if(leiRequestDTO.getChecksum().trim()=="") {
			ec="LAE011";
		}else if(isValidChecksum(leiRequestDTO.getChecksum())) {
			ec="LAE012";
		}else if(isValidIdentifier(leiRequestDTO.getIdentifier())) {
			ec="LAE013";
		}else if(isSignatureVerified()) {
			ec="LAE014";
		}else {
			ec="LAE000";
		}
		
		return ec;
	}


	private boolean isSignatureVerified() {

		return false;
	}

	private boolean isValidChecksum(String checksum) {

		return false;
	}
	public static boolean isValidLEI(String identifier)
	{
		String regex = "^[0-9]{4}[0]{2}[0-9A-Z]{12}[0-9]{2}$";
		Pattern p = Pattern.compile(regex);
		if (identifier == null) {
			return false;
		}
		Matcher m = p.matcher(identifier);
		return m.matches();
	}
	public static boolean isValidIdentifier(String identifier)
	{
		String regex = "^([a-zA-Z]{5})(\\d{4})([a-zA-Z]{1})$"; // Considering Identifier as PAN number
		Pattern p = Pattern.compile(regex);
		if (identifier == null) {
			return false;
		}
		Matcher m = p.matcher(identifier);
		return m.matches();
	}

	public ILEIService getLeiServiceEndPoint() {
		return leiServiceEndPoint;
	}

	public void setLeiServiceEndPoint(ILEIService leiServiceEndPoint) {
		this.leiServiceEndPoint = leiServiceEndPoint;
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
