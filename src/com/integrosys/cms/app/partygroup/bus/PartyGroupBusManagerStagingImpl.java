package com.integrosys.cms.app.partygroup.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author Bharat Waghela Bus Manager Implication for staging Party Group
 */
public class PartyGroupBusManagerStagingImpl extends
		AbstractPartyGroupBusManager {

	/**
	 * 
	 * This method give the entity name of staging party group table
	 * 
	 */

	public String getPartyGroupName() {
		return IPartyGroupDao.STAGE_PARTY_GROUP_NAME;
	}

	/**
	 * This method returns exception as staging Party Group can never be working
	 * copy
	 */

	public IPartyGroup updateToWorkingCopy(IPartyGroup workingCopy,
			IPartyGroup imageCopy) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		throw new IllegalStateException(
				"'updateToWorkingCopy' should not be implemented.");
	}

	public List getAllPartyGroup() {

		return null;
	}
	
	public List getPartyByFacilityList(String partyName) {

		return null;
	}

}