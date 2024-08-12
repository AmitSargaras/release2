package com.integrosys.cms.app.cersaiMapping.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;

public abstract class AbstractCersaiMappingBusManager implements
ICersaiMappingBusManager{
	
	private ICersaiMappingDao cersaiMappingDao;

	private ICersaiMappingJdbc cersaiMappingJdbc;

	public ICersaiMappingDao getCersaiMappingDao() {
		return cersaiMappingDao;
	}

	public void setCersaiMappingDao(ICersaiMappingDao cersaiMappingDao) {
		this.cersaiMappingDao = cersaiMappingDao;
	}

	public ICersaiMappingJdbc getCersaiMappingJdbc() {
		return cersaiMappingJdbc;
	}

	public void setCersaiMappingJdbc(ICersaiMappingJdbc cersaiMappingJdbc) {
		this.cersaiMappingJdbc = cersaiMappingJdbc;
	}

	public abstract String getCersaiMappingName();
	
	@Override
	public ICersaiMapping createCersaiMapping(
			ICersaiMapping stagingCersaiMapping)
			throws CersaiMappingException {
		if (!(stagingCersaiMapping == null)) {
			return getCersaiMappingDao().createCersaiMapping(getCersaiMappingName(), stagingCersaiMapping);
		} else {
			throw new CersaiMappingException(
					"ERROR- CersaiMapping object is null. ");
		}
	}
	
	@Override
	public ICersaiMapping getCersaiMappingById(long id)
			throws CersaiMappingException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCersaiMappingDao().getCersaiMapping(
					getCersaiMappingName(), new Long(id));
		} else {
			throw new CersaiMappingException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	
	public ICersaiMapping updateCersaiMapping(ICersaiMapping item)
			throws CersaiMappingException, TrxParameterException,
			TransactionException {
		try {
			return getCersaiMappingDao().updateCersaiMapping(
					getCersaiMappingName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CersaiMappingException("current CersaiMapping");
		}
	}
	

}
