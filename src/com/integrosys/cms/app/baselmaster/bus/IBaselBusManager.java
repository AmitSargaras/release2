package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;

public interface IBaselBusManager {
	public SearchResult getAllActualBasel()throws BaselMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IBaselMaster createBasel(IBaselMaster basel)	throws BaselMasterException;
	
	public IFileMapperId createFileId(IFileMapperId fileId)throws BaselMasterException;
	
	public IBaselMaster insertBasel(IBaselMaster basel)	throws BaselMasterException;
	
	public IBaselMaster getBaselById(long id)	throws BaselMasterException, TrxParameterException,	TransactionException;
	
	public IBaselMaster updateBasel(IBaselMaster item)throws BaselMasterException, TrxParameterException,TransactionException ;
	
	public IBaselMaster updateToWorkingCopy(IBaselMaster workingCopy, IBaselMaster imageCopy)
	throws BaselMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException ;
	
	public SearchResult getSearchBaselList(String baselName)throws BaselMasterException ;
	
	public SearchResult getAllActualCommon() throws BaselMasterException,TrxParameterException, TransactionException,
	ConcurrentUpdateException;
}
