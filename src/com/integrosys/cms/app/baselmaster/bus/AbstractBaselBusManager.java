package com.integrosys.cms.app.baselmaster.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.baselmaster.proxy.IBaselProxyManager;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

public abstract class AbstractBaselBusManager implements IBaselBusManager{
	
	private IBaselJdbc baselJdbc;
	
	private IBaselDao baselMasterDao;

	public IBaselJdbc getBaselJdbc() {
		return baselJdbc;
	}

	public void setBaselJdbc(IBaselJdbc baselJdbc) {
		this.baselJdbc = baselJdbc;
	}
	
		

	public IBaselDao getBaselMasterDao() {
		return baselMasterDao;
	}

	public void setBaselMasterDao(IBaselDao baselMasterDao) {
		this.baselMasterDao = baselMasterDao;
	}

	public abstract String getBaselName();
	
	public SearchResult getAllActualBasel() throws BaselMasterException,TrxParameterException, TransactionException,ConcurrentUpdateException {
			return getBaselJdbc().getAllActualBasel();
	}
	
	public SearchResult getAllActualCommon() throws BaselMasterException,TrxParameterException, TransactionException,ConcurrentUpdateException {
		return getBaselJdbc().getAllActualCommon();
}

	public IBaselMaster createBasel(IBaselMaster basel)	throws BaselMasterException {
		if (!(basel == null)) {
			return getBaselMasterDao().createBasel(getBaselName(), basel);
		} else {
			throw new BaselMasterException(
					"ERROR- Basel object   is null. ");
		}
	}
	

	public IBaselMaster getBaselById(long id)	throws BaselMasterException, TrxParameterException,	TransactionException {
		if (id != 0) {
			return getBaselMasterDao().getBasel(getBaselName(), new Long(id));
		} else {
				throw new BaselMasterException("ERROR-- Key for Object Retrival is null.");
				}
	}
	
	/*public List searchBasel(String login)throws BaselMasterException,TrxParameterException,TransactionException {

		return getBaselJdbc().getAllBaselSearch(login);
	}*/
	
	
	
	/**
	 @return Basel Object after update
	 * 
	 */

	public IBaselMaster updateBasel(IBaselMaster item)throws BaselMasterException, TrxParameterException,
			TransactionException {
		try {
			return getBaselMasterDao().updateBasel(getBaselName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BaselMasterException("current Basel");
		}
	}
	/**
	 @return Basel Object after delete
	 * 
	 */
	public IBaselMaster deleteBasel(IBaselMaster item)
			throws BaselMasterException, TrxParameterException,
			TransactionException {
		try {
			return getBaselMasterDao().deleteBasel(getBaselName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BaselMasterException("current Basel ");
		}
	}
	/**
	 @return Basel Object after create
	 * 
	 */


	/*public boolean isPrevFileUploadPending()
	throws BaselMasterException {
		try {
			return getBaselDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BaselMasterException("File is not in proper format");
		}
	}*/

	public int insertBasel(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws BaselMasterException {
		try {
			return getBaselMasterDao().insertBasel(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BaselMasterException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertBasel(IFileMapperId fileId, IBaselMasterTrxValue trxValue)
			throws BaselMasterException {
		if (!(fileId == null)) {
			return getBaselMasterDao().insertBasel(getBaselName(), fileId, trxValue);
		} else {
			throw new BaselMasterException(
					"ERROR- Basel object is null. ");
		}
	}
	
	public IFileMapperId createFileId(IFileMapperId fileId)
			throws BaselMasterException {
		if (!(fileId == null)) {
			return getBaselMasterDao().createFileId(getBaselName(), fileId);
		} else {
			throw new BaselMasterException(
					"ERROR- Basel object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws BaselMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getBaselMasterDao().getInsertFileList(
					getBaselName(), new Long(id));
		} else {
			throw new BaselMasterException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/*public List getAllStageBasel(String searchBy, String login)throws BaselMasterException,TrxParameterException,TransactionException {

		return getBaselDao().getAllStageBasel(searchBy, login);
	}*/
	
	public List getFileMasterList(String searchBy)throws BaselMasterException,TrxParameterException,TransactionException {

		return getBaselMasterDao().getFileMasterList(searchBy);
	}
	
	
	public IBaselMaster insertActualBasel(String sysId)
	throws BaselMasterException {
		try {
			return getBaselMasterDao().insertActualBasel(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BaselMasterException("File is not in proper format");
		}
	}
	
	public IBaselMaster insertBasel(IBaselMaster basel)
			throws BaselMasterException {
		if (!(basel == null)) {
			return getBaselMasterDao().insertBasel("actualBasel", basel);
		} else {
			throw new BaselMasterException(
					"ERROR- System Bank Branch object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getBaselMasterDao().deleteTransaction(obFileMapperMaster);		
	}
	
	/*public boolean isUniqueCode(String baselName)throws BaselMasterException, TrxParameterException,
	TransactionException {
		return  getBaselDao().isUniqueCode(baselName);

}*/
	
	public SearchResult getSearchBaselList(String baselName)throws BaselMasterException {
		return getBaselMasterDao().getSearchBaselList(baselName);
}
	

	
	

}
