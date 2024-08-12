
package com.integrosys.cms.ui.ecbf.counterparty;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdfcAPI.bean.ClimesBorrowerVo;
import com.hdfcAPI.bean.ClimesBorrowerResponseVo;
import com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl;
import com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplService;
import com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplServiceLocator;
import com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplSoapBindingStub;
//import com.hdfcbank.wsdl.ecbf.counterparty.ClimesBorrowerResponseVo;
//import com.hdfcbank.wsdl.ecbf.counterparty.ClimesBorrowerVo;
//import com.hdfcbank.wsdl.ecbf.counterparty.ClimsBulkImportDetailServiceImpl;
//import com.hdfcbank.wsdl.ecbf.counterparty.ClimsBulkImportDetailServiceImplService;
//import com.hdfcbank.wsdl.ecbf.counterparty.JAXBTransformer;
//import com.hdfcbank.wsdl.ecbf.counterparty.Transformer;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ecbf.counterparty.IECBFCustomerInterfaceLog;
import com.integrosys.cms.app.ecbf.counterparty.IECBFCustomerInterfaceLogDAO;
import com.integrosys.cms.app.ecbf.counterparty.OBECBFCustomerInterfaceLog;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupJdbc;
import com.integrosys.cms.ui.common.UIUtil;
import com.ofss.fc.app.context.SessionContext;

public class ClimesToECBFHelper {
	
	private final static Logger logger = LoggerFactory.getLogger(ClimesToECBFHelper.class);

	public static boolean sendRequest(String partyId) throws Exception{
		
		logger.info("Preparing request to send to ECBF party webservice based on partyId: " + partyId);
		
		ClimesBorrowerVo request = prepareRequest(partyId);
		
		return sendRequest(request, null);
	}
	
	public static boolean sendRequest(IECBFCustomerInterfaceLog log) throws Exception{
		
		logger.info("Preparing request to send to ECBF party webservice based on latest interface request: " + log.getId());
		
		ClimesBorrowerVo request = log.toRequest();
		
		return sendRequest(request, log);
	}
	
	public static void sendRequest(ICMSCustomer customer) throws Exception {
		if(isReqToInterfaceToeCBF(customer)) {
			sendRequest(customer.getCifId());
		}
	}
	
	private static ClimesBorrowerVo prepareRequest(String partyId) {
		ClimesBorrowerVo request = new ClimesBorrowerVo();
		
		IPartyGroupJdbc partyJdbc = (IPartyGroupJdbc)BeanHouse.get("partyGroupJdbc");	
		
		Map<String, String> dataMap = partyJdbc.getPartyDetailsForECBFParty(partyId);
		
		if(dataExistInMap(dataMap, "ADDRESS1")) {
			request.setAddress1(dataMap.get("ADDRESS1"));
		}
		
		if(dataExistInMap(dataMap, "ADDRESS2")) {
			request.setAddress2(dataMap.get("ADDRESS2"));
		}
		
		if(dataExistInMap(dataMap, "ASSETCLASSIFICATION")) {
			request.setAssetClassification(dataMap.get("ASSETCLASSIFICATION"));
		}
		
		if(dataExistInMap(dataMap, "ASSETCLASSIFICATION_VALUE")) {
			//request.setAssetClassificationValue(dataMap.get("ASSETCLASSIFICATION_VALUE"));
		}
		
		if(dataExistInMap(dataMap, "BULKBORROWERNAME")) {
			request.setBulkBorrowerName(dataMap.get("BULKBORROWERNAME"));
		}
		
		if(dataExistInMap(dataMap, "CAMDATE")) {
			request.setCamDate(dataMap.get("CAMDATE"));
		}
		
		if(dataExistInMap(dataMap, "CAMEXPIRYDATE")) {
			request.setCamExpiryDate(dataMap.get("CAMEXPIRYDATE"));
		}
		
		if(dataExistInMap(dataMap, "CAMNUMBER")) {
			request.setCamNumber(dataMap.get("CAMNUMBER"));
		}
		
		if(dataExistInMap(dataMap, "CAMSRMEXPIRYDATE")) {
			request.setCamSRmExpiryDate(dataMap.get("CAMSRMEXPIRYDATE"));
		}
		
		if(dataExistInMap(dataMap, "CAMSANCTIONEDAMOUNT")) {
			request.setCamSanctionedAmount(UIUtil.mapStringToBigDecimal(dataMap.get("CAMSANCTIONEDAMOUNT")));
		}
		else {
			request.setCamSanctionedAmount(BigDecimal.ZERO);
		}
		
		if(dataExistInMap(dataMap, "CAMTYPE")) {
			request.setCamType(dataMap.get("CAMTYPE"));
		}
		
		if(dataExistInMap(dataMap, "CONSTITUTION")) {
			request.setConstitution(dataMap.get("CONSTITUTION"));
		}
		
		if(dataExistInMap(dataMap, "CONSTITUTION_VALUE")) {
			//request.setConstitutionValue(dataMap.get("CONSTITUTION_VALUE"));
		}
		
		if(dataExistInMap(dataMap, "CONTACTNO")) {
			request.setContactNo(dataMap.get("CONTACTNO"));
		}
		
		if(dataExistInMap(dataMap, "DATEOFBIRTH")) {
			request.setDateOfBirth(dataMap.get("DATEOFBIRTH"));
		}
		
		if(dataExistInMap(dataMap, "DATEOFINCORPORATION")) {
			request.setDateOfIncorporation(dataMap.get("DATEOFINCORPORATION"));
		}
		
		if(dataExistInMap(dataMap, "EMAILID")) {
			request.setEmailId(dataMap.get("EMAILID"));
		}
		
		if(dataExistInMap(dataMap, "LOCATION")) {
			request.setLocation(dataMap.get("LOCATION"));
		}
		
		if(dataExistInMap(dataMap, "LOCATION_VALUE")) {
			//request.setLocationValue(dataMap.get("LOCATION_VALUE"));
		}
		
		if(dataExistInMap(dataMap, "PANNUMBER")) {
			request.setPanNumber(dataMap.get("PANNUMBER"));
		}
		
		if(dataExistInMap(dataMap, "PARTYID")) {
			request.setPartyId(dataMap.get("PARTYID"));
		}
		
		if(dataExistInMap(dataMap, "PINCODE")) {
			request.setPinCode(dataMap.get("PINCODE"));
		}
		
		if(dataExistInMap(dataMap, "PRESENTTURNOVER")) {
			request.setPresentTurnOver(UIUtil.mapStringToBigDecimal(dataMap.get("PRESENTTURNOVER")));
		}
		else {
			request.setPresentTurnOver(BigDecimal.ZERO);
		}
		
		if(dataExistInMap(dataMap, "PREVIOUSTURNOVER")) {
			request.setPreviousTurnOver(UIUtil.mapStringToBigDecimal(dataMap.get("PREVIOUSTURNOVER")));
		}
		else {
			request.setPreviousTurnOver(BigDecimal.ZERO);
		}
		
		if(dataExistInMap(dataMap, "RAMRATING")) {
			request.setRamRating(dataMap.get("RAMRATING"));
		}
		
		if(dataExistInMap(dataMap, "RAMRATING_VALUE")) {
			//request.setRamRatingValue(dataMap.get("RAMRATING_VALUE"));
		}
		
		if(dataExistInMap(dataMap, "RMNAME")) {
			request.setRmName(dataMap.get("RMNAME"));
		}
		
		if(dataExistInMap(dataMap, "SEGMENTNAME")) {
			request.setSegmentName(dataMap.get("SEGMENTNAME"));
		}
		
		if(dataExistInMap(dataMap, "SEGMENTNAME_VALUE")) {
			//request.setSegmentNameValue(dataMap.get("SEGMENTNAME_VALUE"));
		}
		
		if(dataExistInMap(dataMap, "STATUS")) {
			request.setStatus(dataMap.get("STATUS"));
		}
		
		return request;
	}
	
	private static String validateMandatoryFieldFromRequest(ClimesBorrowerVo request) {
		StringBuffer validationMsg = new StringBuffer("");
		
		if(StringUtils.isEmpty(request.getAddress1())) {
			validationMsg.append("Address1, ");
		}
		
//		if(StringUtils.isEmpty(request.getAssetClassificationValue())) {
//			validationMsg.append("Asset Classification, ");
//		}
		
		if(StringUtils.isEmpty(request.getBulkBorrowerName())) {
			validationMsg.append("Name of Bulk Borrower, ");
		}
		
		if(StringUtils.isEmpty(request.getCamDate())) {
			validationMsg.append("CAM Date, ");
		}
		
		if(StringUtils.isEmpty(request.getCamExpiryDate())) {
			validationMsg.append("CAM Expiry Date, ");
		}
		
		if(StringUtils.isEmpty(request.getCamNumber())) {
			validationMsg.append("CAM Number, ");
		}
		
		if(StringUtils.isEmpty(request.getCamSRmExpiryDate())) {
			validationMsg.append("CAM SRM Expiry Date, ");
		}
		
//		if(StringUtils.isEmpty(request.getCamSanctionedAmount())) {
//			validationMsg.append("CAM Sanctioned Amount, ");
//		}
		
		if(StringUtils.isEmpty(request.getCamType())) {
			validationMsg.append("CAM Type, ");
		}
		
//		if(StringUtils.isEmpty(request.getConstitutionValue())) {
//			validationMsg.append("Constitution, ");
//		}
		
		if(StringUtils.isEmpty(request.getContactNo())) {
			validationMsg.append("Contact No., ");
		}
		
		if(StringUtils.isEmpty(request.getDateOfBirth())) {
			validationMsg.append("Date of Birth, ");
		}
		
		if(StringUtils.isEmpty(request.getDateOfIncorporation())) {
			validationMsg.append("Date of Incorporation, ");
		}
		
		if(StringUtils.isEmpty(request.getEmailId())) {
			validationMsg.append("Email Id., ");
		}
		
//		if(StringUtils.isEmpty(request.getLocationValue())) {
//			validationMsg.append("City, ");
//		}
		
		if(StringUtils.isEmpty(request.getPanNumber())) {
			validationMsg.append("PAN Number, ");
		}
		
		if(StringUtils.isEmpty(request.getPartyId())) {
			validationMsg.append("PartyId, ");
		}
		
		if(StringUtils.isEmpty(request.getPinCode())) {
			validationMsg.append("Pin Code, ");
		}
		
//		if(StringUtils.isEmpty(request.getPresentTurnOver())) {
//			validationMsg.append("Turn Over ( Present Financial Year), ");
//		}
//		
//		if(StringUtils.isEmpty(request.getPreviousTurnOver())) {
//			validationMsg.append("Turn Over ( Previous Financial Year), ");
//		}
//		
//		if(StringUtils.isEmpty(request.getRamRatingValue())) {
//			validationMsg.append("RAM Rating, ");
//		}
		
		if(StringUtils.isEmpty(request.getRmName())) {
			validationMsg.append("RM Name, ");
		}
		
//		if(StringUtils.isEmpty(request.getSegmentNameValue())) {
//			validationMsg.append("Segment Name, ");
//		}
		
		if(StringUtils.isEmpty(request.getStatus())) {
			validationMsg.append("Status, ");
		}
		
		return validationMsg.toString();
	}
	
	private static String validateECBFIdMapping(ClimesBorrowerVo request) {
		StringBuffer validationMsg = new StringBuffer("");
		
		if(StringUtils.isEmpty(request.getAssetClassification())){
			validationMsg.append("Asset Classification, ");
		}
		
		if(StringUtils.isEmpty(request.getLocation())){
			validationMsg.append("City, ");
		}
		
		if(StringUtils.isEmpty(request.getSegmentName())){
			validationMsg.append("Segment Name, ");
		}
		
		if(StringUtils.isEmpty(request.getConstitution())){
			validationMsg.append("Constitution, ");
		}
		
		if(StringUtils.isEmpty(request.getRamRating())){
			validationMsg.append("Ram Rating, ");
		}
		
		return validationMsg.toString();
	}
	
	public static boolean sendRequest(ClimesBorrowerVo request, IECBFCustomerInterfaceLog log) throws Exception {
		boolean success = false;
		
		logger.info("Checking for mandatory field validation");
		
		String validationMandateMsg = validateMandatoryFieldFromRequest(request);
		String validationMappingMsg = validationMandateMsg.length() == 0 ? validateECBFIdMapping(request) : "";
		
		if(log == null) {
			log = new OBECBFCustomerInterfaceLog(request);
		}
		
		log.setRequestDateTime(DateUtil.now().getTime());
		log.setInterfaceStatus("P");
		log.setIs_udf_upload("_");
		
		IECBFCustomerInterfaceLogDAO dao = (IECBFCustomerInterfaceLogDAO) BeanHouse.get("ecbfCustomerInterfaceLogDAO");
		
		if(validationMandateMsg.length() > 0) {
			validationMandateMsg = validationMandateMsg.substring(0, validationMandateMsg.length() - 2);
			logger.info("Mandatory field [" + validationMandateMsg + "] needed. Interface calling failed...");
			log.setErrorMessage(validationMandateMsg + (validationMandateMsg.split(", ").length > 1 ? " fields are" : " field is") + " mandatory.");
			log.setInterfaceStatus("F");
		}
		else if(validationMappingMsg.length() > 0) {
			validationMappingMsg = validationMappingMsg.substring(0, validationMappingMsg.length() - 2);
			logger.info("ECBF Mapping for [" + validationMappingMsg + "] needed at CLIMS. Interface calling failed...");
			log.setErrorMessage("ECBF mapping for " + validationMappingMsg + (validationMappingMsg.split(", ").length > 1 ? " fields are" : " field is") + " missing at CLIMS.");
			log.setInterfaceStatus("F");
		}
		else{
			log = dao.createOrUpdateInterfaceLog(log);

			request.setTransactionrefNum(String.valueOf(log.getId()));
			logger.info("Preparing request for calling ECBF party webservice with transactionrefNum: " + request.getTransactionrefNum());

			try {
				long startTime = System.currentTimeMillis();
//				MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
//				Transformer transformer = new JAXBTransformer();
//	
//				SOAPMessage messageReq = factory.createMessage();
//				SOAPBody bodyReq = messageReq.getSOAPBody();
//				transformer.dtoToXML(request, bodyReq);
//				ByteArrayOutputStream baosReq = new ByteArrayOutputStream();
//				messageReq.writeTo(baosReq);
				
				
				String endPoint = PropertyManager.getValue("ecbf.counterparty.ws.url");
				logger.debug("ECBF endpoint::::" + endPoint);
				
				//New changes
				logger.debug("Calling ClimsBulkImportDetailServiceImplServiceLocator serviceLocator");
				ClimsBulkImportDetailServiceImplServiceLocator serviceLocator = new ClimsBulkImportDetailServiceImplServiceLocator();
				logger.info("ECBF party webservice ClimsBulkImportDetailServiceImplService() called..");
				serviceLocator.setClimsBulkImportDetailServiceImplEndpointAddress(endPoint);
				
				logger.info("Calling ECBF party webservice ClimsBulkImportDetailServiceImpl() from serviceLocator...");
				
				ClimsBulkImportDetailServiceImpl climsBulkImportDetailServiceImpl = serviceLocator.getClimsBulkImportDetailServiceImpl();
				
				ClimsBulkImportDetailServiceImplSoapBindingStub stub = (ClimsBulkImportDetailServiceImplSoapBindingStub)climsBulkImportDetailServiceImpl;
				
				logger.info("Calling ECBF party webservice method updateBorrowerDetail at " + DateUtil.now().getTime()+" for request "+request.getTransactionrefNum());
				ClimesBorrowerResponseVo response = climsBulkImportDetailServiceImpl.updateBorrowerDetail(request);
				logger.info("Response from ECBF party webservice method updateBorrowerDetail captured successfully at " + DateUtil.now().getTime());
				
				String requestMessage = stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString();
				
				log.setRequestMessage(requestMessage);
				log = dao.createOrUpdateInterfaceLog(log);
				logger.info("requestMessage for request - "+request.getTransactionrefNum()+" is : "+requestMessage);
				
				
				
				
//				logger.info("calling ECBF party webservice ClimsBulkImportDetailServiceImplService()...");
				//ClimsBulkImportDetailServiceImplService service = new ClimsBulkImportDetailServiceImplService();
//				logger.info("ECBF party webservice ClimsBulkImportDetailServiceImplService() called..");
				 
				
				//ClimsBulkImportDetailServiceImpl climsBulkImportDetailService = service.getClimsBulkImportDetailService();
//				logger.info("ECBF party webservice ClimsBulkImportDetailServiceImplService connected through port..");
				
//				BindingProvider bindingProvider = (BindingProvider) climsBulkImportDetailService;
//				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
				
//				logger.info("Calling ECBF party webservice method updateBorrowerDetail at " + DateUtil.now().getTime());
				//ClimesBorrowerResponseVo response = climsBulkImportDetailService.updateBorrowerDetail(request);
//				logger.info("Response from ECBF party webservice method updateBorrowerDetail captured successfully at " + DateUtil.now().getTime());
				
				if(response != null) {
					
					long stopTime = System.currentTimeMillis();
					logger.info("Total time required for the process:" + (stopTime - startTime) + " ms");
					
					log.setResponseDateTime(DateUtil.now().getTime());
					
					String responseMessage = stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString();
					log.setResponseMessage(responseMessage);
					
					logger.info("responseMessage for request - "+request.getTransactionrefNum()+" is : "+responseMessage);
					
					String errorMessage = response != null?response.getErrorMessage():StringUtils.EMPTY;
					String responseStatus = response != null?response.getStatus():StringUtils.EMPTY;
					
//					SOAPMessage messageRes = factory.createMessage();
//					SOAPBody bodyRes = messageRes.getSOAPBody();
//					transformer.dtoToXML(response, bodyRes);
//					ByteArrayOutputStream baosRes = new ByteArrayOutputStream();
//					messageRes.writeTo(baosRes);
				
//					log.setResponseMessage(baosRes.toString());
					log.setErrorMessage(response.getErrorMessage());
					log.setInterfaceStatus(response.getStatus());
					log.setResponsePartyId(response.getPartyId());
				
					success = true;
				}else {
					logger.info("No Response received from ECBF party webservice method updateBorrowerDetail");
					log.setInterfaceStatus("F");
					log.setErrorMessage("No Response received from ECBF party webservice");
				}
			}catch(Exception ex) {
				ex.printStackTrace();
				logger.error("Failed while creating/accessing/calling webservice with error: " + ex.getMessage(), ex);
				log.setErrorMessage(ex.getMessage());
				log.setInterfaceStatus("F");
			}
		}
	
		log = dao.createOrUpdateInterfaceLog(log);
		
		return success;
	}
	
	private static boolean dataExistInMap(Map<String, String> map, String key) {
		return map.containsKey(key) && map.get(key) != null && map.get(key).length() > 0;
	}
	
	private static boolean isReqToInterfaceToeCBF(ICMSCustomer customer) {
		boolean isReq = false;
		if(customer != null && customer.getCMSLegalEntity() != null 
				&& customer.getCMSLegalEntity().getUdfData() != null
				&& customer.getCMSLegalEntity().getUdfData().length > 0) {
			isReq = "YES".equalsIgnoreCase(customer.getCMSLegalEntity().getUdfData()[0].getUdf98());
		}
		
		return isReq;
	}
	
}