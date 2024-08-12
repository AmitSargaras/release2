package com.integrosys.cms.app.json.endpoint;

import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.party.dto.PartyRootRequest;

public interface IPartyService {
	
	public PartyRootRequest retrievePartyDetails(String custId,String action,String scmFlag,IJsInterfaceLog log) throws Exception;
}
