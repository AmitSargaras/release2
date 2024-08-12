package com.integrosys.cms.app.json.ws;

import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.LineRootResponse;

public interface ILineWebserviceClient {
	
	public LineRootResponse sendRetrieveRequest(String limitProfileId,String xrefId,String LineNo,String SerialNo,IJsInterfaceLog log) throws Exception;
	public boolean processFailedReleaseLineRequests(OBJsInterfaceLog log);
	 public LineRootResponse sendRetrieveRequestforStp(String srcRefId,OBJsInterfaceLog log) throws Exception;

}
