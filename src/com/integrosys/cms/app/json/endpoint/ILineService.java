package com.integrosys.cms.app.json.endpoint;

import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.LineRootRequest;

public interface ILineService {
	
	public LineRootRequest retrieveLineDetails(String xrefId, String lmtProfileId,String limitId,String custId,IJsInterfaceLog log) throws Exception;
	public LineRootRequest retrieveLineDetailsforStp(String srcRefId,IJsInterfaceLog loggingDto) throws Exception ;
	
}

