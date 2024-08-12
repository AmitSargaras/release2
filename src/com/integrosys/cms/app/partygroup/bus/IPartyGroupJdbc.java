/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.partygroup.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;



/**
 * 
 @author $Author: Bharat Waghela $
 */
public interface IPartyGroupJdbc {
	List getAllPartyGroup() throws PartyGroupException;
	/*List getAllPartyGroupSearch(String login)throws PartyGroupException;*/
	List listPartyGroup(long branchCode) throws PartyGroupException;
	
	List getPartyByFacilityList(String partyName) throws PartyGroupException;
	
	Map<String,Integer> getFailedPartyRequestforScm() throws PartyGroupException;
	
	List<Long> getFailedListforAdd(String partyId);
	
    List<Long> getFailedListforUpd(String partyId);
    
    List<OBJsInterfaceLog> getInterfaceLogDetailsForParty(Long id) ;
    
    void updateTheFailedResponseLog(OBJsInterfaceLog log);

	Map<String, String> getPartyDetailsForECBFParty(String partyId);
	
	void updateSCFJSLogTableForPartyAndCam(String partyId);
	
	int insertPartyToInterfaceLogBackupTable();


}
