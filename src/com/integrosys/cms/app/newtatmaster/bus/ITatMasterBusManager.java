package com.integrosys.cms.app.newtatmaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface ITatMasterBusManager {
	
	public SearchResult getAllTatEvents()throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public INewTatMaster getTatMasterById(long id)	throws TatMasterException, TrxParameterException,	TransactionException;
	
	public INewTatMaster createTatMaster(INewTatMaster tatMaster)throws TatMasterException;
	
	public INewTatMaster updateToWorkingCopy(INewTatMaster workingCopy, INewTatMaster imageCopy)
	throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException ;
	
	public INewTatMaster updateTatMaster(INewTatMaster item)throws TatMasterException, TrxParameterException,
	TransactionException;

}
