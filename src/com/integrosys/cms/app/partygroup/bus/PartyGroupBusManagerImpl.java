package com.integrosys.cms.app.partygroup.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Bus Manager Imlication declares the methods used by Dao and Jdbc
 */

public class PartyGroupBusManagerImpl extends AbstractPartyGroupBusManager
		implements IPartyGroupBusManager {

	/**
	 * 
	 * This method give the entity name of staging party group table
	 * 
	 */

	public String getPartyGroupName() {
		return IPartyGroupDao.ACTUAL_PARTY_GROUP_NAME;
	}

	/**
	 * @return List of all authorized Party Group
	 */

	public List getAllPartyGroup() {
		return getPartyGroupJdbc().getAllPartyGroup();
	}

	public List getPartyByFacilityList(String partyName) {
		return getPartyGroupJdbc().getPartyByFacilityList(partyName);
	}
	/**
	 * @return WorkingCopy-- updated party group Object
	 * @param working
	 *            copy-- Entry of Actual Table
	 * @param image
	 *            Copy-- Entry Of Staging Table
	 * 
	 *            After Approval From Checker the Working Copy is updated as per
	 *            the image copy.
	 * 
	 */

	public IPartyGroup updateToWorkingCopy(IPartyGroup workingCopy,
			IPartyGroup imageCopy) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		IPartyGroup updated;
		try {
			workingCopy.setPartyCode(imageCopy.getPartyCode());
			workingCopy.setPartyName(imageCopy.getPartyName());
			workingCopy.setGroupExpLimit(imageCopy.getGroupExpLimit());

			// AccessorUtil.copyValue(imageCopy, workingCopy, new String[] {
			// "Id" });
			updated = updatePartyGroup(workingCopy);
		} catch (PartyGroupException e) {
			throw new PartyGroupException(
					"Error while Copying copy to main file");
		}

		return updatePartyGroup(updated);
	}
	public boolean isPartyCodeUnique(String partyCode){
		 return getPartyGroupDao().isPartyCodeUnique(partyCode);
	 }

	public SearchResult getPartyList(String type, String text) throws PartyGroupException {
		return getPartyGroupDao().getPartyList(type, text);
	}
}
