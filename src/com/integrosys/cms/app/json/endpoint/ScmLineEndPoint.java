package com.integrosys.cms.app.json.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.json.dao.ScmLineDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.LimitDetails;
import com.integrosys.cms.app.json.line.dto.LineRootRequest;
import com.integrosys.cms.app.json.line.dto.FetchLimitDetailsRequestDTO;
import com.integrosys.cms.app.json.line.dto.HeaderParam;
import com.integrosys.cms.app.json.line.dto.RequestString;
import com.integrosys.cms.app.json.line.dto.RetrieveScmLineRequest;
import com.integrosys.cms.app.json.line.dto.SessionContext;
import com.integrosys.cms.app.json.ws.WebServiceUtil;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;


public class ScmLineEndPoint implements ILineService {

	
	@Autowired
	private ScmLineDao scmLineDao;
	
	ILimitDAO limitDao = LimitDAOFactory.getDAO();

	
	public LineRootRequest retrieveLineDetails(String xrefId, String limitProfileId,String limitId,String custId,IJsInterfaceLog loggingDto) throws Exception {
		try{
			
		DefaultLogger.debug(this, "Inside retrieveLineDetails() ScmLineEndPoint.java line 35 ");
			
		RetrieveScmLineRequest lineDetailsfromDb  = scmLineDao.getLineDetails(xrefId,limitProfileId,limitId,custId);
		DefaultLogger.debug(this, "Party List created "+lineDetailsfromDb);
		String sequence = scmLineDao.generateSourceSeqNo();
		String latestOperationStatus = scmLineDao.getLatestOperationStatus(limitProfileId, lineDetailsfromDb.getLineNumber(), lineDetailsfromDb.getSerialNumber()); 
		DefaultLogger.debug(this, "Sequence generated" +sequence);
		loggingDto.setStatus("Processing");
		loggingDto.setScmFlag(lineDetailsfromDb.getScmFlag());
		loggingDto.setTransactionId(Long.parseLong(sequence));
		loggingDto.setPartyName(lineDetailsfromDb.getPartyName());
		loggingDto.setOperation(lineDetailsfromDb.getAction());
		loggingDto.setPartyId(lineDetailsfromDb.getPartyId());
		loggingDto.setLimitProfileId(limitProfileId);
		loggingDto.setLine_no(lineDetailsfromDb.getLineNumber());
		loggingDto.setSerial_no(lineDetailsfromDb.getSerialNumber());
		if(!"C".equals(lineDetailsfromDb.getAction())) {
			if(latestOperationStatus!=null && "A".equalsIgnoreCase(latestOperationStatus)) {
			lineDetailsfromDb.setAction("A");
			loggingDto.setOperation("A");
		}
		}
		LineRootRequest finalScmMsg = createFinalMsgforScm( lineDetailsfromDb,sequence); 
		
		return finalScmMsg;
		}catch(Exception e) {
			DefaultLogger.debug(this, "Error Occured in scmpartyendpoint" +e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public LineRootRequest retrieveLineDetailsforStp(String srcRefId,IJsInterfaceLog loggingDto) throws Exception {
		try{
			DefaultLogger.debug(this, "Going for  limitDao.getLineDetailsforStp(srcRefId).. srcRefId=>"+srcRefId);
		List lineData   = limitDao.getLineDetailsforStp(srcRefId);
		DefaultLogger.debug(this, "After limitDao.getLineDetailsforStp(srcRefId).. lineData=>"+lineData);
		RetrieveScmLineRequest lineDetailsfromDb =null;
		if(lineData==null||lineData.isEmpty()) {
			//do nothing
		}else {
			lineDetailsfromDb = (RetrieveScmLineRequest) lineData.get(0);
		}
		DefaultLogger.debug(this, "Party List created "+lineDetailsfromDb);
		String sequence = limitDao.generateSourceSeqNoForStp();
		DefaultLogger.debug(this, "Sequence generated" +sequence);
		loggingDto.setStatus("Processing");
		loggingDto.setScmFlag(lineDetailsfromDb.getScmFlag());
		loggingDto.setTransactionId(Long.parseLong(sequence));
		loggingDto.setPartyName(lineDetailsfromDb.getPartyName());
		loggingDto.setOperation(lineDetailsfromDb.getAction());
		loggingDto.setPartyId(lineDetailsfromDb.getPartyId());
		loggingDto.setLine_no(lineDetailsfromDb.getLineNumber());
		loggingDto.setSerial_no(lineDetailsfromDb.getSerialNumber());
		loggingDto.setLimitProfileId(lineDetailsfromDb.getLimitProfileId());
		System.out.println("retrieveLineDetailsforStp =>lineDetailsfromDb.getLimitProfileId()=>"+lineDetailsfromDb.getLimitProfileId());
		DefaultLogger.debug(this,"retrieveLineDetailsforStp =>lineDetailsfromDb.getLimitProfileId()=>"+lineDetailsfromDb.getLimitProfileId());
		LineRootRequest finalScmMsg = createFinalMsgforScm( lineDetailsfromDb,sequence); 
		
		return finalScmMsg;
		}catch(Exception e) {
			DefaultLogger.debug(this, "Error Occured in scmpartyendpoint" +e);
			e.printStackTrace();
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
	
	public LimitDetails createLineDetails (RetrieveScmLineRequest lineDetailsfromDb,String sequence) {
		LimitDetails limitDetails = new LimitDetails(); 
		if(lineDetailsfromDb!=null) {
			limitDetails.setUniqueReferenceID(sequence);
			limitDetails.setScm(lineDetailsfromDb.getScmFlag());
			limitDetails.setAction(lineDetailsfromDb.getAction());
			limitDetails.setPartyID(lineDetailsfromDb.getPartyId());
			limitDetails.setPartyName(lineDetailsfromDb.getPartyName());
			limitDetails.setFacilityName(lineDetailsfromDb.getFacilityName());
			limitDetails.setSystem(lineDetailsfromDb.getSystem());
			limitDetails.setCurrency(lineDetailsfromDb.getCurrency());
			limitDetails.setReleaseflag(lineDetailsfromDb.getReleaseFlag());
			limitDetails.setAdhocflag(lineDetailsfromDb.getAdhocFlag());
			limitDetails.setAdhocLimitAmount(lineDetailsfromDb.getAdhocLimitAmount());
			limitDetails.setSanctionAmount(lineDetailsfromDb.getSanctionAmount());
			limitDetails.setSublimitFlag(lineDetailsfromDb.getSublimitFlag());
			limitDetails.setSanctionAmountINR(lineDetailsfromDb.getSanctionAmountINR());
			limitDetails.setGuarantee(lineDetailsfromDb.getGuarantee());
			limitDetails.setGuaranteePartyName(lineDetailsfromDb.getGuaranteePartyName());
			limitDetails.setGuaranteeliabilityId(lineDetailsfromDb.getGuaranteeliabilityId());
			limitDetails.setReleaseableAmount(lineDetailsfromDb.getReleaseableAmount());
			limitDetails.setReleasedAmount(lineDetailsfromDb.getReleasedAmount());
			limitDetails.setMainLineNumber(lineDetailsfromDb.getMainLineNumber());
			limitDetails.setMainLinePartyId(lineDetailsfromDb.getMainLinePartyId());
			limitDetails.setMainLinePartyName(lineDetailsfromDb.getMainLinePartyName());
			limitDetails.setMainLineSystemID(lineDetailsfromDb.getMainLineSystemID());
			limitDetails.setSystemId(lineDetailsfromDb.getSystemId());
			limitDetails.setLineNumber(lineDetailsfromDb.getLineNumber());
			limitDetails.setSerialNumber(lineDetailsfromDb.getSerialNumber());
			limitDetails.setLiabBranch(lineDetailsfromDb.getLiabBranch());
			limitDetails.setLimitStartDate(lineDetailsfromDb.getLimitStartDate());
			limitDetails.setAvailableFlag(lineDetailsfromDb.getAvailableFlag());
			limitDetails.setRevolvingLine(lineDetailsfromDb.getRevolvingLine());
			limitDetails.setLimitExpiryDate(lineDetailsfromDb.getLimitExpiryDate());
			limitDetails.setFreezeFlag(lineDetailsfromDb.getFreezeFlag());
			limitDetails.setSegment(lineDetailsfromDb.getSegment());
			limitDetails.setPslFlag(lineDetailsfromDb.getPslFlag());
			limitDetails.setPslValue(lineDetailsfromDb.getPslValue());
			limitDetails.setCommitment(lineDetailsfromDb.getCommitment());
			limitDetails.setRateValue(lineDetailsfromDb.getRateValue());
			limitDetails.setTenorDays(lineDetailsfromDb.getTenorDays());
			limitDetails.setRemarks(lineDetailsfromDb.getRemarks());
			limitDetails.setNpa(lineDetailsfromDb.getNpa());
			limitDetails.setProductAllowed(lineDetailsfromDb.getProductAllowed());
			System.out.println("ScmLineEndPoint.java Line number 171=>LimitDetails createLineDetails=> lineDetailsfromDb serial number=>"+lineDetailsfromDb.getSerialNumber()+" .. " +
					" Segment =>"+lineDetailsfromDb.getSegment()+" ... System =>"+lineDetailsfromDb.getSystem()+" ... system id=>"+lineDetailsfromDb.getSystemId()
					+" ... Line number=>"+lineDetailsfromDb.getLineNumber()+"  .. Party Id=>"+lineDetailsfromDb.getPartyId()+" ... Action=>"+lineDetailsfromDb.getAction()
					+ " ... Liab Branch=>"+lineDetailsfromDb.getLiabBranch()+"  .. Sequence=>"+sequence);
			System.out.println("Inside LimitDetails createLineDetails => set values in lineDetailsfromDb");
		}
		return limitDetails;
	}
	
	public RequestString createReqString (LimitDetails limitDetails) {
		RequestString requestStr = new RequestString();
		requestStr.setLimitDetails(limitDetails);
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
	public LineRootRequest createFinalMsgforScm(RetrieveScmLineRequest lineDetailsfromDb,String sequence) {
		LineRootRequest scmMsgFormat = new LineRootRequest();
		FetchLimitDetailsRequestDTO finalDto = new FetchLimitDetailsRequestDTO();
		finalDto.setHeaderParams(createheaderDetails());
		finalDto.setRequestString(createReqString(createLineDetails(lineDetailsfromDb,sequence)));
		scmMsgFormat.setFetchLimitDetailsRequestDTO(finalDto);
		scmMsgFormat.setSessionContext(createSessionDetails(sequence));
		return scmMsgFormat;
	}

	
}

