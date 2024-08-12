package com.integrosys.cms.app.json.dao;

import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.json.line.dto.RetrieveScmLineRequest;

public interface ScmLineDao {
	public RetrieveScmLineRequest getLineDetails(String xrefId,String limitProfileId,String LineNo,String SerialNo) ;
	public String getScmFlagfromMain(String limitProfileId,String LineNo,String SerialNo ) ;
	public String getScmFlagfromStg(String xrefId) ;
	public String generateSourceSeqNo();
	public String getLatestOperationStatus(String limitProfileId,String LineNo,String SerialNo ) ;
}
