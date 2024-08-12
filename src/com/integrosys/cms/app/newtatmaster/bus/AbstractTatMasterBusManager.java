package com.integrosys.cms.app.newtatmaster.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;



public abstract class AbstractTatMasterBusManager implements ITatMasterBusManager{
	
	
	private ITatMasterDao tatMasterDao;
	private ITatMasterJdbc tatMasterJdbc;

	public ITatMasterJdbc getTatMasterJdbc() {
		return tatMasterJdbc;
	}

	public void setTatMasterJdbc(ITatMasterJdbc tatMasterJdbc) {
		this.tatMasterJdbc = tatMasterJdbc;
	}
	
	
	public ITatMasterDao getTatMasterDao() {
		return tatMasterDao;
	}

	public void setTatMasterDao(ITatMasterDao tatMasterDao) {
		this.tatMasterDao = tatMasterDao;
	}
	
	public abstract String getTatMasterName();

	public SearchResult getAllTatEvents()throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getTatMasterJdbc().getAllTatEvents();
	}
	

	public INewTatMaster getTatMasterById(long id)	throws TatMasterException, TrxParameterException,	TransactionException {
		if (id != 0) {
			return getTatMasterDao().getTatMaster(getTatMasterName(), new Long(id));
		} else {
				throw new ComponentException("ERROR-- Key for Object Retrival is null.");
				}
	}
	
	public INewTatMaster createTatMaster(INewTatMaster tatMaster)
			throws TatMasterException {
		if (!(tatMaster == null)) {
			return getTatMasterDao().createTatMaster(getTatMasterName(), tatMaster);
		} else {
			throw new ComponentException(
					"ERROR- Tat Master object   is null. ");
		}
	}
	
	public INewTatMaster updateTatMaster(INewTatMaster item)throws TatMasterException, TrxParameterException,
	TransactionException {
				try {
					return getTatMasterDao().updateTatMaster(getTatMasterName(), item);
				} catch (HibernateOptimisticLockingFailureException ex) {
					throw new ComponentException("current tat master");
				}
}
	
	
	

}
