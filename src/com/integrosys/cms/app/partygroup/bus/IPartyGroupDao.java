package com.integrosys.cms.app.partygroup.bus;

/**
 @author $Author: Bharat waghela $
 */
import java.io.Serializable;

import com.integrosys.base.businfra.search.SearchResult;

public interface IPartyGroupDao {

	static final String ACTUAL_PARTY_GROUP_NAME = "actualOBPartyGroup";

	static final String STAGE_PARTY_GROUP_NAME = "stageOBPartyGroup";

	IPartyGroup getPartyGroup(String entityName, Serializable key)
			throws PartyGroupException;
	
	IPartyGroup getPartyGroupById(Serializable key)
	throws PartyGroupException;

	IPartyGroup updatePartyGroup(String entityName, IPartyGroup item)
			throws PartyGroupException;

	IPartyGroup deletePartyGroup(String entityName, IPartyGroup item)
			throws PartyGroupException;

	IPartyGroup enablePartyGroup(String entityName, IPartyGroup item)
			throws PartyGroupException;

	IPartyGroup createPartyGroup(String entityName, IPartyGroup partyGroup)
			throws PartyGroupException;
	
	public boolean isPartyCodeUnique(String partyCode);
	
	public boolean isPartyNameUnique(String partyName);
	
	public String getCustomerIdCode() ;

	public SearchResult getPartyList(String type, String text) throws PartyGroupException;
}
