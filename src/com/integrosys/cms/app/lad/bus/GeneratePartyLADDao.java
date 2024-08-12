package com.integrosys.cms.app.lad.bus;

import java.util.List;
import java.util.Map;

public interface GeneratePartyLADDao {
	public List getData(String rm_id, String party_id, String due_month,
			String due_year, String segment_id);

	void runProcedure(String rm_Name, String rmId, String party_name,
			String partyId, String dueMonth, String dueYear, String segment);

	void updateToCompletedAndFileName(String rmId, String partyId, String dueMonth,
			String dueYear, String segment,String dirName, String reportGenerationDate);
	
	
	
	public Integer isFilterAlreadyPresent(String rm_id, String party_id, String due_month,
			String due_year, String segment_id);
	
	void updateToFail(String rmId, String partyId, String dueMonth,
			String dueYear, String segment, String reportGenerationDate);
			
	//public List getAllData();
	
	public List getAllData(Boolean isRMUser, String isRMValue);
	
	public List getLimits(Long limitProfileId);
	
	public List<String[]> getLimitsForLadReport(long profileId) ;
	
	public void updateToPartialSuccess(String rmId, String partyId, String dueMonth,
			String dueYear, String segment, String reportGenerationDate);
	
	public void updateToPartialSuccess(String rmId, String partyId, String dueMonth,
			String dueYear, String segment,String reportName, String reportGenerationDate);
}
