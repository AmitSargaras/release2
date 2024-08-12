package com.integrosys.cms.app.json.dto;



import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IPartyService {
	
	public ObjectNode retrievePartyDetails(String custId,String action,String scmFlag,IJsInterfaceLog log) throws Exception;
}
