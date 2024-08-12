package com.integrosys.cms.app.partygroup.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 *@author $Author: Bharat Waghela $ Abstract Party Group Bus manager
 */
public abstract class AbstractPartyGroupBusManager implements
		IPartyGroupBusManager {

	private IPartyGroupDao partyGroupDao;

	private IPartyGroupJdbc partyGroupJdbc;

	public IPartyGroupDao getPartyGroupDao() {
		return partyGroupDao;
	}

	public void setPartyGroupDao(IPartyGroupDao partyGroupDao) {
		this.partyGroupDao = partyGroupDao;
	}

	public IPartyGroupJdbc getPartyGroupJdbc() {
		return partyGroupJdbc;
	}

	public void setPartyGroupJdbc(IPartyGroupJdbc partyGroupJdbc) {
		this.partyGroupJdbc = partyGroupJdbc;
	}

	public abstract String getPartyGroupName();

	/**
	 * @return Particular Party Group according to the id passed as parameter.
	 * @param party
	 *            Code
	 */

	public IPartyGroup getPartyGroupById(long id) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (id != 0) {
			return getPartyGroupDao().getPartyGroup(getPartyGroupName(),
					new Long(id));
		} else {
			throw new PartyGroupException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}

	public IPartyGroup deletePartyGroup(IPartyGroup item)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		try {
			return getPartyGroupDao().deletePartyGroup(getPartyGroupName(),
					item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new PartyGroupException("current PartyGroup ");
		}
	}

	public IPartyGroup enablePartyGroup(IPartyGroup item)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		try {
			return getPartyGroupDao().enablePartyGroup(getPartyGroupName(),
					item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new PartyGroupException("current PartyGroup ");
		}
	}

	/**
	 * @return PartyGroup Object after create
	 * 
	 */

	public IPartyGroup createPartyGroup(IPartyGroup partyGroup)
			throws PartyGroupException {
		if (!(partyGroup == null)) {

			return getPartyGroupDao().createPartyGroup(getPartyGroupName(),
					partyGroup);
		} else {
			throw new PartyGroupException(
					"ERROR- Party group object   is null. ");
		}
	}

	
	/*public List searchPartyGroupMaster(String login)throws PartyGroupException,TrxParameterException,TransactionException {

		return getPartyGroupJdbc().getAllPartyGroupSearch(login);
	}*/
	
	/**
	 * @return PartyGroup Object after update
	 * 
	 */
	public IPartyGroup updatePartyGroup(IPartyGroup item)
			throws PartyGroupException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		try {
			return getPartyGroupDao().updatePartyGroup(getPartyGroupName(),
					item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new PartyGroupException("Current PartyGroup [" + item
					+ "] was updated before by [" + item.getPartyCode()
					+ "] at [" + item.getPartyName() + "]");
		}

	}
	public boolean isPartyCodeUnique(String partyCode){
		 return getPartyGroupDao().isPartyCodeUnique(partyCode);
	 }
	public boolean isPartyNameUnique(String partyName){
		 return getPartyGroupDao().isPartyNameUnique(partyName);
	 }
	
	
	public SearchResult getPartyList(String type, String text)
		throws PartyGroupException {
		return getPartyGroupDao().getPartyList(type, text);
}

}