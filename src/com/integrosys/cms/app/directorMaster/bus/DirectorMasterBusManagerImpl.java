package com.integrosys.cms.app.directorMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 *Bus Manager Implication to communicate with DAO and JDBC
 */
public class DirectorMasterBusManagerImpl extends AbstractDirectorMasterBusManager implements IDirectorMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging Director Master table  
     * 
     */
	
	
	public String getDirectorMasterName() {
		return IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return DirectorMaster Object
	 * 
	 */
	

	public IDirectorMaster getDirectorMaster(long id) throws DirectorMasterException,TrxParameterException,TransactionException {
		
		return getDirectorMasterDao().load( getDirectorMasterName(), id);
	}

	/**
	 * @return WorkingCopy-- updated director master Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IDirectorMaster updateToWorkingCopy(IDirectorMaster workingCopy, IDirectorMaster imageCopy)
	throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IDirectorMaster updated;
		try{
			workingCopy.setDinNo((imageCopy.getDinNo()));
			workingCopy.setName(imageCopy.getName());
			workingCopy.setDirectorCode(imageCopy.getDirectorCode());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			updated = updateDirectorMaster(workingCopy);
			return updated;
		}catch (Exception e) {
			throw new DirectorMasterException("Error while Copying copy to main file");
		}
	}
	
	/**
	 * @return List of all authorized director master
	 */
	

	public SearchResult getAllDirectorMaster()throws DirectorMasterException{
		 return getDirectorMasterDao().getAllDirectorMaster();
	}

	public boolean isDinNumberUnique(String dinNumber) {
		return getDirectorMasterDao().isDinNumberUnique(dinNumber);
	}

	public boolean isDirectorNameUnique(String directorName) {
		return getDirectorMasterDao().isDirectorNameUnique(directorName);
	}	
}
