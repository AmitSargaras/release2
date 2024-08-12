package com.integrosys.cms.app.json.dao;

import com.integrosys.cms.app.json.party.dto.RetrieveScmPartyRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ScmPartyDao {
	public RetrieveScmPartyRequest getPartyDetails(String custId) ;
	public String getBorrowerScmFlag(String custId);
	public String generateSourceSeqNo();
	public String getStgBorrowerScmFlag(String refId);
	public String getBorrowerScmFlagforCAM(String custId);
    public String getLatestOperationStatus (String custId,String module);

	
}
