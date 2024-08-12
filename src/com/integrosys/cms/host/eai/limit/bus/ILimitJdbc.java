package com.integrosys.cms.host.eai.limit.bus;

import java.util.List;

import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import java.util.Map;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;

public interface ILimitJdbc {

	public Long getLimitProfileIdByCMSLimitProfileID(long cmsLimitProfileId);

	public String getCifNumberByCmsLimitProfileId(long cmsLimitProfileId);

	public Long getLmtIdByLmtProfileIdAndOldLmtId(long limitProfileId, long oldLimitNo);

	public Long getLmtSecMapIdByLmtProfileIdAndOldLmtSecMapId(long limitProfileId, long oldLimitSecMapId);

	public void updateRenewInd(long limitProfileId, String renewStatus);

	public String getLOSLimitIdByCMSLimitId(long CMSLimitId);
	
	public String getCamByLlpLeId(String cifId);
	
	public String getFacilityCodeByCMSLimitId(long CMSLimitId);
	
	public String getPartyStatus(String cifId);
	
	public String isPartyExist(String cifId);
	
	public Object getFacilityByMappingId(long mappingId);
	
	public List getLineDetails(String systemId, String lineNo, String serialNo, String liabBranch);
	
	public String updateLineDetails(String systemId, String lineNo, String serialNo, String liabBranch, String releasedAmount, String closeFlag, String xrefID);
	
	public List getFacilityDetails(String xrefID);
	
	public void updateFacilityDetails(String lmtId, String amount);
	
	Map<String, String> getDetailsForECBFLimit(Map<String, Object> paramMap, String action);
	
	ICommonCodeEntry getEntryByCodeAndCategory(String entryCode, String categoryCode);
	
	String findSegment1ByPartyId(String partyId);
	
	boolean isSystemExistsForParty(String partyId, String systemId);
	
	String getDefaultSegment1CodeValue(String defaultSegmentName);
}
