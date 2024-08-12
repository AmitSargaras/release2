package com.integrosys.cms.app.lei.json.endpoint;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.lei.bus.ILeiJdbc;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILRequest;
import com.integrosys.cms.app.lei.json.dto.LeiRequestDTO;
import com.integrosys.cms.app.lei.json.dto.SessionContext;
import com.integrosys.cms.app.lei.json.dto.ValidateLEINumFromCCILRequestDTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.*;


public class LEICCILEndPoint implements ILEIService {

	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static ILeiJdbc leiJdbc = (ILeiJdbc)BeanHouse.get("leiJdbc");		

	public LeiRequestDTO getLeiRequest(String leiCode) throws Exception {
		
		LeiRequestDTO leiRequestDTO = new LeiRequestDTO();
		ValidateLEINumFromCCILRequestDTO validateLEINumFromCCILRequestDTO = new ValidateLEINumFromCCILRequestDTO();

		SessionContext sessionContext = sessionContextDetails();
		RetrieveLEICCILRequest retrieveLEICCILRequest = retrieveLEIDetails(leiCode);
		validateLEINumFromCCILRequestDTO.setRequestString(retrieveLEICCILRequest);
		leiRequestDTO.setValidateLEINumFromCCILRequestDTO(validateLEINumFromCCILRequestDTO);
		leiRequestDTO.setSessionContext(sessionContext);
		
		return leiRequestDTO;
		
	}
	
	private static SessionContext sessionContextDetails() {
		
		SessionContext sessionContext = new SessionContext();
		
		sessionContext.setChannel(PropertyManager.getValue("ccil.lei.channel"));
		sessionContext.setBankCode(PropertyManager.getValue("ccil.lei.bankcode"));
		sessionContext.setExternalReferenceNo(leiJdbc.generateExternalReferenceNo());
		sessionContext.setTransactionBranch(PropertyManager.getValue("ccil.lei.transaction.branch"));
		sessionContext.setUserId(PropertyManager.getValue("ccil.lei.userid"));
		
		return sessionContext;
		
	}
	private static RetrieveLEICCILRequest retrieveLEIDetails(String leiCode) throws Exception {
		try{
			RetrieveLEICCILRequest retrieveLEICCILRequest  = new RetrieveLEICCILRequest();
			String clientId=PropertyManager.getValue("ccil.lei.clientId");
			String refNum="HDFC"+leiJdbc.generateReferenceNumber();
			
			retrieveLEICCILRequest.setLeiNumber(leiCode);
			retrieveLEICCILRequest.setChecksum(getChecksum(clientId,leiCode,refNum));
			retrieveLEICCILRequest.setClientId(clientId);
			retrieveLEICCILRequest.setIdentifier("");
			retrieveLEICCILRequest.setReferenceNumber(refNum);

			return retrieveLEICCILRequest;			

		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}	
	}

	private static String getChecksum(String clientId,String leiCode,String refNum) {
		
		String checksum=clientId+leiCode+refNum;
		String hashChecksum = null;
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
			    
			    byte[] hash = hasher.doFinal(checksum.getBytes(UTF_8));
			    
			    // to base64
			    hashChecksum = DatatypeConverter.printBase64Binary(hash);
			    System.out.print("Checksum HMACSHA256 Hash : "+hashChecksum);
			    
		  	}
		  	catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		  	catch (InvalidKeyException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}

			return hashChecksum;
	}	

}
