package com.integrosys.cms.ui.manualinput.customer;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.hdfcbank.wsdl.isd.panval.PANValServicePortType;
import com.hdfcbank.wsdl.isd.panval.SearchPANValServiceQSService;
import com.hdfcbank.xsd.isd.panval.PANDETAILSTYPE;
import com.hdfcbank.xsd.isd.panval.PANValServiceRequestType;
import com.hdfcbank.xsd.isd.panval.PANValServiceResponseType;
import com.hdfcbank.xsd.isd.panval.PanDetailsRequestType;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.manualinput.party.IPANValidationLog;
import com.integrosys.cms.app.manualinput.party.PANValidationLog;
import com.integrosys.cms.app.manualinput.party.bus.IPanValidationLogDao;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;


	/**
	 * Class created by
	 * @author ankit.limbadia
	 * @since 11-02-2016
	 *
	 */

public class ValidatePANNoWithNSDLCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	protected static ResourceBundle resourceBundle;
	
	static{
		resourceBundle = ResourceBundle.getBundle("ofa",Locale.getDefault());
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "panNo", "java.lang.String", REQUEST_SCOPE },
				{ "partyNameAsPerPan", "java.lang.String", REQUEST_SCOPE },
				{ "dateOfIncorporation", "java.lang.String", REQUEST_SCOPE },
				{ "customerCifId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "customerId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
			});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {	
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", SERVICE_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE },
				{ "panValLogOB", "com.integrosys.cms.app.manualinput.party.PANValidationLog", REQUEST_SCOPE },
				{ "panNSDLResponse", "com.hdfcbank.xsd.isd.panval.PANValServiceResponseType", REQUEST_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "OBCMSCustomerNew","com.integrosys.cms.app.customer.bus.ICMSCustomer",SERVICE_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		String panNo = (String) map.get("panNo");
		String partyNameAsPerPan = (String) map.get("partyNameAsPerPan");
		String dateOfIncorporation = (String) map.get("dateOfIncorporation");
		String customerCifId = (String) map.get("customerCifId");
//		DefaultLogger.info(this,"customerCifId:::"+customerCifId);
		
		DefaultLogger.info(this, "Inside doExecute() ValidatePANNoWithNSDLCommand "+event);
		
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		OBTrxContext obTrxContext =(OBTrxContext)map.get("theOBTrxContext");
		DefaultLogger.debug(this,obTrxContext.getUser().getLoginID());
		String loginId = obTrxContext.getUser().getLoginID();
		String requestXML ="";
		String responseXML ="";
		
		//Preparing URL
		URL panURL = null;
		String panValEndPoint = getFieldValue("PAN_NSDL_WS_URL");
		DefaultLogger.debug(this, "panValEndPoint::::"+panValEndPoint);
		if(panValEndPoint!=null){
			panValEndPoint = panValEndPoint.trim();
			DefaultLogger.debug(this, "panValEndPoint.trim()::::"+panValEndPoint);
			try {
				panURL = new URL(panValEndPoint+"?wsdl");

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		//End
		
		DefaultLogger.debug(this,"calling SearchPANValServiceQSService()...");
		SearchPANValServiceQSService service = new SearchPANValServiceQSService(panURL, new QName("panval.isd.wsdl.hdfcbank.com", "SearchPANValServiceQSService"));
		DefaultLogger.debug(this,"SearchPANValServiceQSService() called..");
		PANValServicePortType panValServicePortType = service.getSearchPANValServiceQSPort();
		DefaultLogger.debug(this,"service connected through port..");
		BindingProvider bindingProvider = (BindingProvider) panValServicePortType;
		//BindingProvider bindingProvider = (BindingProvider) port;
//		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"replace with LB production endpoint url");
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,panValEndPoint);
		
		PANValServiceRequestType panValServiceRequest = null;
		PANValServiceResponseType panNSDLResponse = null;
		//IPANValidationLog panValLogOB = null;
		IPANValidationLog panValLogOB = new PANValidationLog();
		
//		DefaultLogger.info(this,"panNo before prepare request:::"+panNo);
		if(null != panNo && !panNo.isEmpty()){
			panValServiceRequest = prepareRequest(panNo,panValServiceRequest,partyNameAsPerPan,dateOfIncorporation);
//			DefaultLogger.info(this,"panValServiceRequest before sending to server::::"+panValServiceRequest);
			DefaultLogger.info(this,"SearchPANValServiceQSService Setting handler...");
			// Attach the logging handler to the port
			LoggingHandler loggingHandler = new LoggingHandler();
			List<Handler> handlerChain = new ArrayList<Handler>();
			handlerChain.add(loggingHandler);
			((BindingProvider) panValServicePortType).getBinding().setHandlerChain(handlerChain);
			panValLogOB.setRequestDateTime(Calendar.getInstance().getTime());
			
			try{
			panNSDLResponse = panValServicePortType.executePANValService(panValServiceRequest);
			
			requestXML = loggingHandler.getLastRequest();
			DefaultLogger.info(this,"SearchPANValServiceQSService Request xml..."+requestXML);	
			responseXML = loggingHandler.getLastResponse();
			DefaultLogger.info(this,"SearchPANValServiceQSService Response xml..."+responseXML);
			
			panValLogOB.setRequestMessage(requestXML);
			panValLogOB.setResponseMessage(responseXML);
			
//			DefaultLogger.info(this,"panNSDLResponse::::"+panNSDLResponse);
			if(panNSDLResponse!=null && panNSDLResponse.getPANDetails()!=null && panNSDLResponse.getPANDetails().size()>0){
				DefaultLogger.debug(this,"Inside IF :::"+panNSDLResponse.getPANDetails().size());
				for(PANDETAILSTYPE panDetails: panNSDLResponse.getPANDetails()){
					
//					DefaultLogger.debug(this,"PAN NO: "+panDetails.getPAN());
					DefaultLogger.debug(this,"PAN NO : "+panDetails.getPAN());
					DefaultLogger.debug(this,"PAN STATUS : "+panDetails.getPANStatus());
					DefaultLogger.debug(this,"NAME : "+panDetails.getName());
//					DefaultLogger.debug(this,"MIDDLE NAME : "+panDetails.getMiddleName());
//					DefaultLogger.debug(this,"LAST NAME : "+panDetails.getLastName());
					DefaultLogger.debug(this,"DOB : "+panDetails.getDOB());
					DefaultLogger.debug(this,"VALUE1 : "+panDetails.getValue1());
					DefaultLogger.debug(this,"VALUE2 : "+panDetails.getValue2());
					//DefaultLogger.debug(this,"FILLER3 : "+panDetails.getFiller3());
					//DefaultLogger.debug(this,"FILLER4 : "+panDetails.getFiller4());
					//DefaultLogger.debug(this,"FILLER5 : "+panDetails.getFiller5());
					
//					System.out.println("PAN NO: "+panDetails.getPAN());
					System.out.println("PAN NO : "+panDetails.getPAN());
					System.out.println("PAN STATUS : "+panDetails.getPANStatus());
					System.out.println("NAME : "+panDetails.getName());
				/*	System.out.println("MIDDLE NAME : "+panDetails.getMiddleName());
					System.out.println("LAST NAME : "+panDetails.getLastName()); */
					System.out.println("DOB : "+panDetails.getDOB());
					DefaultLogger.debug(this,"VALUE1 : "+panDetails.getValue1());
					DefaultLogger.debug(this,"VALUE2 : "+panDetails.getValue2());
					//System.out.println("FILLER3 : "+panDetails.getFiller3());
					//System.out.println("FILLER4 : "+panDetails.getFiller4());
					//System.out.println("FILLER5 : "+panDetails.getFiller5());
				}
			}
			}catch(Exception e){
				System.err.println("Error encountered while communicating to NSDL Application:::"+e.getCause());
				DefaultLogger.error(this,"Error encountered while communicating to NSDL Application:::"+e.getCause());
				panNSDLResponse= new PANValServiceResponseType();
				panNSDLResponse.setReturnCode("XXX");
				panNSDLResponse.setReturnDescription("RESPONSE NOT RECEIVED FROM HOST APPLICATION (NSDL) ");
			}
//			DefaultLogger.info(this,"panNSDLResponse before createPANValidationLog::::"+panNSDLResponse);
			panValLogOB = createPANValidationLog(panValServiceRequest,panNSDLResponse,obCustomer,loginId,customerCifId,panValLogOB,dateOfIncorporation,partyNameAsPerPan);
		}
		
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("OBCMSCustomerNew", obCustomer);
		resultMap.put("event",event);	
		resultMap.put("panValLogOB", panValLogOB);
		resultMap.put("panNSDLResponse", panNSDLResponse);
		DefaultLogger.debug(this,"Interface Log BO created....");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
	
	protected String getFieldValue(String name) {
		if (resourceBundle.containsKey(name)) {
			return resourceBundle.getString(name);
		} else {
			return null;
		}
	}
	
	protected PANValServiceRequestType prepareRequest(String panNo,PANValServiceRequestType panValServiceRequest, String partyNameAsPerPan, String dateOfIncorporation){
		DefaultLogger.debug(this,"inside prepareRequest::::");
		panValServiceRequest = new PANValServiceRequestType();
		ILimitDAO dao = LimitDAOFactory.getDAO();
		
		if(null != panNo && !panNo.isEmpty()
				&& null != partyNameAsPerPan && !partyNameAsPerPan.isEmpty()
				&& null != dateOfIncorporation && !dateOfIncorporation.isEmpty()){
//			DefaultLogger.debug(this,"pan no::"+panNo);
			panValServiceRequest.setServiceUser("");
			panValServiceRequest.setServicePassword("");
			
			if(getFieldValue("CONSUMER_NAME")!=null){
				DefaultLogger.debug(this,"CONSUMER_NAME:::"+getFieldValue("CONSUMER_NAME"));
				panValServiceRequest.setConsumerName(getFieldValue("CONSUMER_NAME").trim());
			}
			
			if(getFieldValue("NSDL_USER_NAME")!=null){
				DefaultLogger.debug(this,"NSDL_USER_NAME:::"+getFieldValue("NSDL_USER_NAME"));
				panValServiceRequest.setNSDLUserName(getFieldValue("NSDL_USER_NAME").trim());
			}
			
			panValServiceRequest.setReqDateTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime()));
			panValServiceRequest.setReqNo(getFieldValue("NSDL_USER_NAME").trim()+":"+getFieldValue("CONSUMER_NAME").trim()+"_"+calculateJulianDayNumber(new Date())+"_"+dao.getSeqNoForPanRequest());
			DefaultLogger.debug(this,"panValServiceRequest.setReqNo():::"+panValServiceRequest.getReqNo());
			
			PanDetailsRequestType panDetails= new PanDetailsRequestType();
			panDetails.setPAN(panNo);
			//panDetails.setName(replaceSpecialCharacters(partyNameAsPerPan));
			panDetails.setName(partyNameAsPerPan);
			//String dateOfIncorporation = new SimpleDateFormat("dd-MMM-yyyy").format(erosionDate);
			try {
				panDetails.setDOB(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("dd/MMM/yyyy").parse(dateOfIncorporation)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			panValServiceRequest.getPANDetails().add(panDetails);
			
			if(getFieldValue("COST_CENTER_NO")!=null){
				DefaultLogger.debug(this,"COST_CENTER_NO:::"+getFieldValue("COST_CENTER_NO"));
				panValServiceRequest.setCostCenterNo(getFieldValue("COST_CENTER_NO").trim());
			}
			
			if(getFieldValue("CERTIFICATENAME")!=null){
				DefaultLogger.debug(this,"CERTIFICATENAME:::"+getFieldValue("CERTIFICATENAME"));
				panValServiceRequest.setFiller1(getFieldValue("CERTIFICATENAME").trim());
			}
			panValServiceRequest.setFiller2("");
			panValServiceRequest.setFiller3("");
			panValServiceRequest.setFiller4("");
			panValServiceRequest.setFiller5("");
//			DefaultLogger.debug(this,"panValServiceRequest ends:::"+panValServiceRequest);
		}
		return panValServiceRequest;
	}
	
	protected IPANValidationLog createPANValidationLog(PANValServiceRequestType request, PANValServiceResponseType response,ICMSCustomer obCustomer,String loginId,String customerCifId,
			IPANValidationLog panValLogOB,String dateOfIncorporation, String partyNameAsPerPan){
		
		DefaultLogger.info(this,"createPANValidationLog:::::");
//		DefaultLogger.info(this,"customerCifId:::::"+customerCifId);
		
		panValLogOB.setPanNo(request.getPANDetails().get(0).getPAN().trim());
		panValLogOB.setPartyNameAsPerPan(partyNameAsPerPan);
		panValLogOB.setDateOfIncorporation(dateOfIncorporation);
		panValLogOB.setRequestNo(request.getReqNo()!=null?request.getReqNo():"");
		DefaultLogger.info(this,"Before Writing Request Responce.");
		panValLogOB.setResponseDateTime(Calendar.getInstance().getTime());
		if(response!=null){
			DefaultLogger.info(this,"response.getReturnCode():::"+response.getReturnCode());
			panValLogOB.setResponseCode(response.getReturnCode());
		}
		DefaultLogger.info(this,"Before if response!=null && !response.getReturnCode().isEmpty()");
		if(response!=null && !response.getReturnCode().isEmpty() &&
				("1".equalsIgnoreCase(response.getReturnCode()) || "01".equalsIgnoreCase(response.getReturnCode())) ){
			DefaultLogger.info(this, "Inside response not null check condition");
			panValLogOB.setStatus(response.getReturnDescription());
			panValLogOB.setErrorMessage("");
			
			if(response.getPANDetails().get(0).getPANStatus()!=null &&  !response.getPANDetails().get(0).getPANStatus().isEmpty()){
				if("E".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (E) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("X".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (X) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("D".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (D) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EA".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EA) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EC".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EC) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("ED".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (ED) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EI".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EI) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EL".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EL) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EM".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EM) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EP".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EP) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("ES".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (ES) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else if("EU".equalsIgnoreCase(response.getPANDetails().get(0).getPANStatus())){
					DefaultLogger.info(this, "Inside if condition where pan status is (EU) ");
					panValLogOB.setIsPANNoValidated('Y');
				}else{
					DefaultLogger.info(this, "Inside else condition where pan status is (F/N) ");
					panValLogOB.setIsPANNoValidated('N');
				}
			}else{
				DefaultLogger.info(this, "Inside else block :: pan status is NULL!! ");
				panValLogOB.setIsPANNoValidated('N');
			}
		}else{
			DefaultLogger.info(this,"Inside Else");
			panValLogOB.setStatus("Fail");
			panValLogOB.setErrorMessage(response.getReturnDescription());
			panValLogOB.setIsPANNoValidated('N');
		}
		DefaultLogger.info(this,"loginId:"+loginId);
		panValLogOB.setLastValidatedBy(loginId);
		panValLogOB.setLastValidatedDate(Calendar.getInstance().getTime());
		if(obCustomer!=null){
//			DefaultLogger.info(this,"obCustomer.getCifId():::"+obCustomer.getCifId());
			panValLogOB.setPartyID(obCustomer.getCifId()!=null?obCustomer.getCifId():"");
			panValLogOB.setCmsMainProfileID(obCustomer.getCustomerID()!=0l?obCustomer.getCustomerID():0L);
			DefaultLogger.info(this,"panValLogOB.getIsPANNoValidated():::"+panValLogOB.getIsPANNoValidated());
			obCustomer.setIsPanValidated(panValLogOB.getIsPANNoValidated());
		}else{
//			DefaultLogger.info(this,"else customerCifId:::"+customerCifId);
			panValLogOB.setPartyID(customerCifId);
		}
		
		IPanValidationLogDao panValidationLogDao = (IPanValidationLogDao)BeanHouse.get("panValidationLogDao");
		IPANValidationLog createdPanValLog = panValidationLogDao.createInterfaceLog(panValLogOB);
		
		return createdPanValLog;
	}
	
	 private static long calculateJulianDayNumber(Date date) {
	        int year = date.getYear();
	        int month = date.getMonth();
	        int day = date.getDay();
	        
	        // Adjust month and year for January and February
	        if (month <= 2) {
	            year--;
	            month += 12;
	        }
	        
	        // Calculate Julian day number
	        int a = year / 100;
	        int b = 2 - a + (a / 4);
	        long julianDayNumber = (long)(365.25 * (year + 4716)) + (int)(30.6001 * (month + 1)) + day + b - 1524;
	        
	        return julianDayNumber;
	    }
	 
	 public static String replaceSpecialCharacters(String name) {
	        // Define replacements map
	        Map<Character, String> replacements = new HashMap<Character, String>();
	        replacements.put('&', "&amp;");
	        replacements.put('<', "&lt;");

	        // StringBuilder to store replaced name
	        StringBuilder replacedName = new StringBuilder();

	        // Iterate through each character of the input string
	        for (char c : name.toCharArray()) {
	            // Check if the character needs replacement
	            if (replacements.containsKey(c)) {
	                // Append the replacement string
	                replacedName.append(replacements.get(c));
	            } else {
	                // Append the character as it is
	                replacedName.append(c);
	            }
	        }

	        // Convert StringBuilder to String and return
	        return replacedName.toString();
	    }
	
}