package com.integrosys.cms.app.newtatmaster.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue;
import com.integrosys.cms.app.newtatmaster.trx.OBTatMasterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface ITatmasterProxyManager {

	public SearchResult getAllTatEvents() throws TatMasterException,TrxParameterException, TransactionException ;
	
	public ITatMasterTrxValue getTatMasterTrxValue(long tatCode)throws TatMasterException, TrxParameterException,TransactionException;
	
	public ITatMasterTrxValue makerUpdateTatMaster(ITrxContext anITrxContext,ITatMasterTrxValue anICCTatMasterTrxValue, INewTatMaster anICCTatMaster)
	throws TatMasterException, TrxParameterException,TransactionException;
	

	public ITatMasterTrxValue getTatMasterByTrxID(String tatCode)throws TatMasterException, TransactionException,CommandProcessingException;
	
	public ITatMasterTrxValue checkerApproveTatMaster(ITrxContext anITrxContext,ITatMasterTrxValue anITatMasterTrxValue)throws TatMasterException, TrxParameterException,
	TransactionException ;
	
	public ITatMasterTrxValue checkerRejectTatMaster(ITrxContext anITrxContext,ITatMasterTrxValue anITatMasterTrxValue) throws ComponentException,
	TrxParameterException, TransactionException ;
	
	public ITatMasterTrxValue makerEditRejectedTatMaster(ITrxContext anITrxContext, ITatMasterTrxValue anITatMasterTrxValue,
			INewTatMaster anTatMaster) throws ComponentException,TrxParameterException, TransactionException;
	
	public ITatMasterTrxValue makerCloseRejectedTatMaster(ITrxContext anITrxContext, ITatMasterTrxValue anITatMasterTrxValue)
	throws TatMasterException, TrxParameterException,TransactionException ;
}
