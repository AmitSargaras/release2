package com.integrosys.cms.app.directorMaster.proxy;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a director master.
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public interface IDirectorMasterProxyManager {

	public List searchDirector(String login) throws DirectorMasterException,TrxParameterException,TransactionException;
	public SearchResult getAllDirectorMaster() throws DirectorMasterException,TrxParameterException,TransactionException;
	public SearchResult getAllActual(String searchBy,String searchText) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMaster disableDirectorMaster(IDirectorMaster directorMaster) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMaster enableDirectorMaster(IDirectorMaster directorMaster) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerCloseRejectedDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anIDirectorMasterTrxValue) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMaster getDirectorMasterById(long id) throws DirectorMasterException,TrxParameterException,TransactionException ;
	public IDirectorMaster updateDirectorMaster(IDirectorMaster directorMaster) throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IDirectorMasterTrxValue makerDisableDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster)throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerEnableDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster)throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerUpdateDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster)
	throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerEditRejectedDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anIDirectorMasterTrxValue, IDirectorMaster anDirectorMaster) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue getDirectorMasterTrxValue(long aDirectorMasterId) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue getDirectorMasterByTrxID(String aTrxID) throws DirectorMasterException,TransactionException,CommandProcessingException;
	public IDirectorMasterTrxValue checkerApproveDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anIDirectorMasterTrxValue) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue checkerRejectDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anIDirectorMasterTrxValue) throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerCreateDirectorMaster(ITrxContext anITrxContext, IDirectorMaster anICCDirectorMaster)throws DirectorMasterException,TrxParameterException,TransactionException;
	
	public IDirectorMasterTrxValue makerUpdateSaveUpdateDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster)
	throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerUpdateSaveCreateDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster)
	throws DirectorMasterException,TrxParameterException,TransactionException;
	public IDirectorMasterTrxValue makerSaveDirectorMaster(ITrxContext anITrxContext, IDirectorMaster anICCDirectorMaster)throws DirectorMasterException,TrxParameterException,TransactionException;
	
	public boolean isDinNumberUnique(String dinNumber);
	
	public boolean isDirectorNameUnique(String directorName);
}
