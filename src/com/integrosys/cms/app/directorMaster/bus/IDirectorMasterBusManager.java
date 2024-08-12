package com.integrosys.cms.app.directorMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */
public interface IDirectorMasterBusManager {
	
		List searchDirector(String login) throws DirectorMasterException,TrxParameterException,TransactionException;
		IDirectorMaster getDirectorMasterById(long id) throws DirectorMasterException,TrxParameterException,TransactionException;
		SearchResult getAllDirectorMaster()throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllDirectorMaster(String searchBy,String searchText)throws DirectorMasterException,TrxParameterException,TransactionException;
		IDirectorMaster updateDirectorMaster(IDirectorMaster item) throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IDirectorMaster disableDirectorMaster(IDirectorMaster item) throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		//
		IDirectorMaster enableDirectorMaster(IDirectorMaster item) throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		//END
		IDirectorMaster saveDirectorMaster(IDirectorMaster item) throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IDirectorMaster updateToWorkingCopy(IDirectorMaster workingCopy, IDirectorMaster imageCopy) throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IDirectorMaster createDirectorMaster(IDirectorMaster systemBankBranch)throws DirectorMasterException;

		public boolean isDinNumberUnique(String dinNumber);
		
		public boolean isDirectorNameUnique(String directorName);
}
