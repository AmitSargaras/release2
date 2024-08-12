package com.integrosys.cms.app.partygroup.bus;

/**
 @author $Author: Bharat Waghela $
 */
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IPartyGroupBusManager {

	IPartyGroup getPartyGroupById(long id) throws PartyGroupException,
			TrxParameterException, TransactionException;

	List getAllPartyGroup();
	List getPartyByFacilityList(String partyName);
	public boolean isPartyCodeUnique(String partyCode);
	public boolean isPartyNameUnique(String partyName);
	
	

		
	/*public List searchPartyGroupMaster(String login) throws PartyGroupException,TrxParameterException,TransactionException;*/

	IPartyGroup updatePartyGroup(IPartyGroup item) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException;

	IPartyGroup updateToWorkingCopy(IPartyGroup workingCopy,
			IPartyGroup imageCopy) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException;

	IPartyGroup createPartyGroup(IPartyGroup partyGroup)
			throws PartyGroupException;

	IPartyGroup deletePartyGroup(IPartyGroup item) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException;

	IPartyGroup enablePartyGroup(IPartyGroup item) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException;

	public SearchResult getPartyList(String type, String text) throws PartyGroupException;
}
