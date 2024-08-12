package com.integrosys.cms.app.json.ws;


import com.integrosys.cms.app.json.dto.*;
import com.integrosys.cms.app.json.party.dto.PartyRootResponse;

public interface IPartyWebserviceClient {

	public PartyRootResponse sendRetrieveRequest(String custId,String action,String scmFlag,IJsInterfaceLog log) throws Exception;
	public boolean processFailedPartyRequests(OBJsInterfaceLog log);
	
}
