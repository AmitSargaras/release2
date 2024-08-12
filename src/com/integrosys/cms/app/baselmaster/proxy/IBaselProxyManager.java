package com.integrosys.cms.app.baselmaster.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.baselmaster.bus.BaselMasterException;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IBaselProxyManager {
	
	public SearchResult getAllActualBasel() throws BaselMasterException,TrxParameterException, TransactionException;
	
	public IBaselMasterTrxValue makerCreateBasel(ITrxContext anITrxContext,IBaselMaster anICCBasel) throws BaselMasterException,
	TrxParameterException, TransactionException;
	
	 public IBaselMasterTrxValue getBaselByTrxID(String aTrxID)throws BaselMasterException, TransactionException,
		CommandProcessingException;
	 
	 public IBaselMasterTrxValue checkerApproveBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue)
		throws BaselMasterException, TrxParameterException,TransactionException;
	 
	 public IBaselMasterTrxValue getBaselTrxValue(long aBaselId)throws BaselMasterException, TrxParameterException,
		TransactionException;
	 
	 public IBaselMasterTrxValue checkerRejectBasel(ITrxContext anITrxContext,IBaselMasterTrxValue anIBaselTrxValue) throws BaselMasterException,
		TrxParameterException, TransactionException ;
	 
	 public IBaselMasterTrxValue makerEditRejectedBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue, IBaselMaster anBasel) throws BaselMasterException,TrxParameterException, TransactionException;
	 
	 public IBaselMasterTrxValue makerCloseRejectedBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue)
		throws BaselMasterException, TrxParameterException,	TransactionException ;
	 
	 public IBaselMasterTrxValue makerUpdateBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anICCBaselTrxValue, IBaselMaster anICCBasel)
		throws BaselMasterException, TrxParameterException,	TransactionException;
	 
	 public IBaselMasterTrxValue makerDeleteBasel(ITrxContext anITrxContext,IBaselMasterTrxValue anICCBaselTrxValue, IBaselMaster anICCBasel)
		throws ComponentException, TrxParameterException,TransactionException ;
	 
	 public SearchResult getSearchBasel(String componentName)throws BaselMasterException;
	 
	 public SearchResult getAllActualCommon() throws BaselMasterException,TrxParameterException, TransactionException;

}
