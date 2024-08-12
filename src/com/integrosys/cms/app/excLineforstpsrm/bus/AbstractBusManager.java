package com.integrosys.cms.app.excLineforstpsrm.bus;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractBusManager implements IBusManager{

	private IExcLineForSTPSRMDao dao;

	private IExcLineForSTPSRMJdbc jdbc;

	public IExcLineForSTPSRMDao getDao() {
		return dao;
	}

	public void setDao(IExcLineForSTPSRMDao dao) {
		this.dao = dao;
	}

	public IExcLineForSTPSRMJdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(IExcLineForSTPSRMJdbc jdbc) {
		this.jdbc = jdbc;
	}
	
	public abstract String getName();
	
	public IExcLineForSTPSRM getById(long id)
			throws ExcLineForSTPSRMException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getDao().get(getName(), new Long(id));
		} else {
			throw new ExcLineForSTPSRMException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public IExcLineForSTPSRM create(
			IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException {
		if (!(obj == null)) {
			return getDao().create(getName(), obj);
		} else {
			throw new ExcLineForSTPSRMException(
					"ERROR- Exclusion Line for STP for SRM object   is null. ");
		}
	}
	
	public IExcLineForSTPSRM update(IExcLineForSTPSRM item)
			throws ExcLineForSTPSRMException, TrxParameterException,
			TransactionException {
		try {
			return getDao().update(getName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ExcLineForSTPSRMException("current Exclusion Line for STP for SRM");
		}
	}
	
	public IExcLineForSTPSRM delete(IExcLineForSTPSRM item)
			throws ExcLineForSTPSRMException, TrxParameterException,
			TransactionException {
		try {
			return getDao().delete(getName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ExcLineForSTPSRMException("current Exclusion Line for STP for SRM");
		}
	}
	
	public SearchResult getAll() throws ExcLineForSTPSRMException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return getJdbc().getAll();
	}
}