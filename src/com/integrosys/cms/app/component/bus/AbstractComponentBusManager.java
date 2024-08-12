package com.integrosys.cms.app.component.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public abstract class AbstractComponentBusManager implements IComponentBusManager {

	private IComponentDao componentDao;

	private IComponentJdbc componentJdbc;
	
	public IComponentDao getComponentDao() {
		return componentDao;
	}


	public void setComponentDao(IComponentDao componentDao) {
		this.componentDao = componentDao;
	}


	public IComponentJdbc getComponentJdbc() {
		return componentJdbc;
	}


	public void setComponentJdbc(IComponentJdbc componentJdbc) {
		this.componentJdbc = componentJdbc;
	}

	public abstract String getComponentName();

	public SearchResult getAllComponent() throws ComponentException,TrxParameterException, TransactionException,ConcurrentUpdateException {
		return getComponentJdbc().getAllComponent();
	}

	
	public SearchResult getAllComponent(String searchBy, String searchText)throws ComponentException, TrxParameterException,
			TransactionException {
		return getComponentJdbc().getAllComponent(searchBy,searchText);
	}

	public IComponent getComponentById(long id)	throws ComponentException, TrxParameterException,	TransactionException {
		if (id != 0) {
			return getComponentDao().getComponent(getComponentName(), new Long(id));
		} else {
				throw new ComponentException("ERROR-- Key for Object Retrival is null.");
				}
	}
	
	public List searchComponent(String login)throws ComponentException,TrxParameterException,TransactionException {

		return getComponentJdbc().getAllComponentSearch(login);
	}
	
	/**
	 @return Component Object after update
	 * 
	 */

	public IComponent updateComponent(IComponent item)
			throws ComponentException, TrxParameterException,
			TransactionException {
		try {
			return getComponentDao().updateComponent(
					getComponentName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ComponentException("current Component");
		}
	}
	/**
	 @return Component Object after delete
	 * 
	 */
	public IComponent deleteComponent(IComponent item)
			throws ComponentException, TrxParameterException,
			TransactionException {
		try {
			return getComponentDao().deleteComponent(
					getComponentName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ComponentException("current Component ");
		}
	}
	/**
	 @return Component Object after create
	 * 
	 */

	public IComponent createComponent(
			IComponent component)
			throws ComponentException {
		if (!(component == null)) {
			return getComponentDao().createComponent(getComponentName(), component);
		} else {
			throw new ComponentException(
					"ERROR- Component object   is null. ");
		}
	}


	public boolean isPrevFileUploadPending()
	throws ComponentException {
		try {
			return getComponentDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ComponentException("File is not in proper format");
		}
	}

	public int insertComponent(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws ComponentException {
		try {
			return getComponentDao().insertComponent(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ComponentException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertComponent(IFileMapperId fileId, IComponentTrxValue trxValue)
			throws ComponentException {
		if (!(fileId == null)) {
			return getComponentDao().insertComponent(getComponentName(), fileId, trxValue);
		} else {
			throw new ComponentException(
					"ERROR- Component object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws ComponentException {
		if (!(fileId == null)) {
			return getComponentDao().createFileId(getComponentName(), fileId);
		} else {
			throw new ComponentException(
					"ERROR- Component object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws ComponentException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getComponentDao().getInsertFileList(
					getComponentName(), new Long(id));
		} else {
			throw new ComponentException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageComponent(String searchBy, String login)throws ComponentException,TrxParameterException,TransactionException {

		return getComponentDao().getAllStageComponent(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws ComponentException,TrxParameterException,TransactionException {

		return getComponentDao().getFileMasterList(searchBy);
	}
	
	
	public IComponent insertActualComponent(String sysId)
	throws ComponentException {
		try {
			return getComponentDao().insertActualComponent(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ComponentException("File is not in proper format");
		}
	}
	
	public IComponent insertComponent(
			IComponent component)
			throws ComponentException {
		if (!(component == null)) {
			return getComponentDao().insertComponent("actualComponent", component);
		} else {
			throw new ComponentException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getComponentDao().deleteTransaction(obFileMapperMaster);		
	}
	
	public boolean isUniqueCode(String componentName)throws ComponentException, TrxParameterException,
	TransactionException {
		return  getComponentDao().isUniqueCode(componentName);

}
	
	public SearchResult getSearchComponentList(String componentName)throws ComponentException {
		return getComponentDao().getSearchComponentList(componentName);
}
	
}
