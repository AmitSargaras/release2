package com.integrosys.cms.app.json.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.party.dto.FetchFacilityDetailsRequestDTO;
import com.integrosys.cms.app.json.party.dto.HeaderParam;
import com.integrosys.cms.app.json.party.dto.PartyDetails;
import com.integrosys.cms.app.json.party.dto.PartyRootRequest;
import com.integrosys.cms.app.json.party.dto.RequestString;
import com.integrosys.cms.app.json.party.dto.RetrieveScmPartyRequest;
import com.integrosys.cms.app.json.party.dto.SessionContext;
import com.integrosys.cms.app.json.ws.WebServiceUtil;

public class ScmPartyEndPoint implements IPartyService {

	
	@Autowired
	private ScmPartyDao scmPartyDao;

	public PartyRootRequest retrievePartyDetails(String custId,String action,String scmFlag,IJsInterfaceLog loggingDto) throws Exception {
		try{
		RetrieveScmPartyRequest partyDetailsfromDb  = scmPartyDao.getPartyDetails(custId);
//		DefaultLogger.debug(this, "Party List created "+partyDetailsfromDb);
		String sequence = scmPartyDao.generateSourceSeqNo();
		DefaultLogger.debug(this, "Sequence generated" +sequence);
		DefaultLogger.debug(this, "scmFlag Flag "+scmFlag);
		loggingDto.setStatus("Processing");
		loggingDto.setScmFlag(scmFlag);
		loggingDto.setTransactionId(Long.parseLong(sequence));
		loggingDto.setPartyName(partyDetailsfromDb.getPartyName());
		PartyRootRequest finalScmMsg = createFinalMsgforScm( partyDetailsfromDb, action, scmFlag, sequence); 
		return finalScmMsg;
		}catch(Exception e) {
			DefaultLogger.debug(this, "Error Occured in scmpartyendpoint" +e);
			return null;
		}
		
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public List  createheaderDetails () {
		HeaderParam hp1 = new  HeaderParam();
		HeaderParam hp2 = new  HeaderParam();
		List <HeaderParam> headerList = new ArrayList<HeaderParam>();
		hp1.setKey(WebServiceUtil.AUTHORIZATION);
		hp1.setValue(PropertyManager.getValue("scm.web.service.authorisation"));
		headerList.add(hp1);
		hp2.setKey(WebServiceUtil.API_VERSION);
		hp2.setValue(PropertyManager.getValue("scm.web.service.version.no"));
		headerList.add(hp2);
		return headerList;
	}
	
	public PartyDetails createPartyDetails (RetrieveScmPartyRequest partyDetailsfromDb,String action,String scmFlag,String sequence) {
		PartyDetails partyDetails = new PartyDetails(); 
		if(partyDetails!=null) {
            partyDetails.setUniqueReferenceID(sequence);
		    partyDetails.setScm(scmFlag);
		    partyDetails.setAction(action);
		    partyDetails.setStatus(partyDetailsfromDb.getStatus());
		    partyDetails.setPartyID(partyDetailsfromDb.getPartyId());
		    partyDetails.setPartyName(partyDetailsfromDb.getPartyName());
		    partyDetails.setVertical(partyDetailsfromDb.getVertical());
		    partyDetails.setConstitution(partyDetailsfromDb.getConstitution());
		    partyDetails.setMsme(partyDetailsfromDb.getMsme());
		    partyDetails.setPan(partyDetailsfromDb.getPan());
		    partyDetails.setIndustryType(partyDetailsfromDb.getIndustryType());
		    partyDetails.setTypeOfActivity(partyDetailsfromDb.getTypeOfActivity());
		    partyDetails.setRm(partyDetailsfromDb.getRm());
		    partyDetails.setCaptialLine(partyDetailsfromDb.getCaptialLine());
		    partyDetails.setRiskRating(partyDetailsfromDb.getRiskRating());
		    partyDetails.setCin(partyDetailsfromDb.getCin());
		    partyDetails.setDateOfIncorporation(partyDetailsfromDb.getDateOfIncorporation());
		    partyDetails.setWilfulDefaultStatus(partyDetailsfromDb.getWilfulDefaultStatus());
		    partyDetails.setDateWilfulDefaultStatus(partyDetailsfromDb.getDateWilfulDefaultStatus());
		    partyDetails.setSuitField(partyDetailsfromDb.getSuitField());
		    partyDetails.setSuitAmount(partyDetailsfromDb.getSuitAmount());
		    partyDetails.setDateOfSuit(partyDetailsfromDb.getDateOfSuit());
		    partyDetails.setAddressType(partyDetailsfromDb.getAddressType());
		    partyDetails.setAddress1(partyDetailsfromDb.getAddress1());
		    partyDetails.setAddress2(partyDetailsfromDb.getAddress2());
		    partyDetails.setAddress3(partyDetailsfromDb.getAddress3());
		    partyDetails.setPhone(partyDetailsfromDb.getPhone());
		    partyDetails.setMobile(partyDetailsfromDb.getMobile());
		    partyDetails.setCity(partyDetailsfromDb.getCity());
		    partyDetails.setRegion(partyDetailsfromDb.getRegion());
		    partyDetails.setState(partyDetailsfromDb.getState());
		    partyDetails.setCountry(partyDetailsfromDb.getCountry());
    		partyDetails.setFax(partyDetailsfromDb.getFax());
			partyDetails.setPincode(partyDetailsfromDb.getPincode());
			partyDetails.setEmail(partyDetailsfromDb.getEmail());
		}
		return partyDetails;
	}
	
	public RequestString createReqString (PartyDetails partyDetails) {
		RequestString requestStr = new RequestString();
		requestStr.setPartyDetails(partyDetails);
		return requestStr;
	}
	
	public SessionContext createSessionDetails (String sequence) {
		SessionContext sessionContext = new SessionContext();
		sessionContext.setBankCode(PropertyManager.getValue("scm.webservice.bank.code"));
		sessionContext.setChannel(PropertyManager.getValue("scm.webservice.channel.name"));
		sessionContext.setExternalReferenceNo(sequence);
		sessionContext.setTransactingPartyCode("");
		sessionContext.setUserId(PropertyManager.getValue("scm.webservice.user.id"));
		sessionContext.setTransactionBranch(PropertyManager.getValue("scm.webservice.transaction.branch"));
		return sessionContext;
	}
	
	@SuppressWarnings("unchecked")
	public PartyRootRequest createFinalMsgforScm(RetrieveScmPartyRequest partyDetailsfromDb,String action,String scmFlag,String sequence) {
		PartyRootRequest scmMsgFormat = new PartyRootRequest();
		FetchFacilityDetailsRequestDTO finalDto = new FetchFacilityDetailsRequestDTO();
		finalDto.setRequestString(createReqString(createPartyDetails(partyDetailsfromDb,action,scmFlag,sequence)));
		finalDto.setHeaderParams(createheaderDetails());
		scmMsgFormat.setFetchFacilityDetailsRequestDTO(finalDto);
		scmMsgFormat.setSessionContext(createSessionDetails(sequence));
		
		return scmMsgFormat;
	}
}
