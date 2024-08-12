package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractBusManager implements IBusManager{

	private IBankingArrangementFacExclusionDao dao;

	private IBankingArrangementFacExclusionJdbc jdbc;

	public IBankingArrangementFacExclusionDao getDao() {
		return dao;
	}

	public void setDao(IBankingArrangementFacExclusionDao dao) {
		this.dao = dao;
	}

	public IBankingArrangementFacExclusionJdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(IBankingArrangementFacExclusionJdbc jdbc) {
		this.jdbc = jdbc;
	}
	
	public abstract String getName();
	
	public IBankingArrangementFacExclusion getById(long id)
			throws BankingArrangementFacExclusionException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getDao().get(getName(), new Long(id));
		} else {
			throw new BankingArrangementFacExclusionException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public IBankingArrangementFacExclusion create(
			IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException {
		if (!(obj == null)) {
			return getDao().create(getName(), obj);
		} else {
			throw new BankingArrangementFacExclusionException(
					"ERROR- BankingArrangementFacExclusion object   is null. ");
		}
	}
	
	public IBankingArrangementFacExclusion update(IBankingArrangementFacExclusion item)
			throws BankingArrangementFacExclusionException, TrxParameterException,
			TransactionException {
		try {
			return getDao().update(getName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BankingArrangementFacExclusionException("current BankingArrangementFacExclusion");
		}
	}
	
	public IBankingArrangementFacExclusion delete(IBankingArrangementFacExclusion item)
			throws BankingArrangementFacExclusionException, TrxParameterException,
			TransactionException {
		try {
			return getDao().delete(getName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BankingArrangementFacExclusionException("current BankingArrangementFacExclusion");
		}
	}
	
	public SearchResult getAll() throws BankingArrangementFacExclusionException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return getJdbc().getAll();
	}
}