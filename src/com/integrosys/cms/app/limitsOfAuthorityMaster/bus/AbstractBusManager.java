package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractBusManager implements IBusManager{

	private ILimitsOfAuthorityMasterDao dao;

	private ILimitsOfAuthorityMasterJdbc jdbc;

	public ILimitsOfAuthorityMasterDao getDao() {
		return dao;
	}

	public void setDao(ILimitsOfAuthorityMasterDao dao) {
		this.dao = dao;
	}

	public ILimitsOfAuthorityMasterJdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(ILimitsOfAuthorityMasterJdbc jdbc) {
		this.jdbc = jdbc;
	}
	
	public abstract String getName();
	
	public ILimitsOfAuthorityMaster getById(long id)
			throws LimitsOfAuthorityMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getDao().get(getName(), new Long(id));
		} else {
			throw new LimitsOfAuthorityMasterException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public ILimitsOfAuthorityMaster create(
			ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException {
		if (!(obj == null)) {
			return getDao().create(getName(), obj);
		} else {
			throw new LimitsOfAuthorityMasterException(
					"ERROR- LimitsOfAuthorityMaster object   is null. ");
		}
	}
	
	public ILimitsOfAuthorityMaster update(ILimitsOfAuthorityMaster item)
			throws LimitsOfAuthorityMasterException, TrxParameterException,
			TransactionException {
		try {
			return getDao().update(getName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new LimitsOfAuthorityMasterException("current LimitsOfAuthorityMaster");
		}
	}
	
	public ILimitsOfAuthorityMaster delete(ILimitsOfAuthorityMaster item)
			throws LimitsOfAuthorityMasterException, TrxParameterException,
			TransactionException {
		try {
			return getDao().delete(getName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new LimitsOfAuthorityMasterException("current LimitsOfAuthorityMaster");
		}
	}
	
	public SearchResult getAllLimitsOfAuthority() throws LimitsOfAuthorityMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return getJdbc().getAllLimitsOfAuthority();
	}
}