package com.integrosys.cms.app.excLineforstpsrm.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.excLineforstpsrm.bus.ExcLineForSTPSRMException;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.trx.IExcLineForSTPSRMTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue;

public interface IProxyManager {

	public SearchResult getAllActual() throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue makerCreate(ITrxContext anITrxContext, IExcLineForSTPSRM obj)throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue getByTrxID(String aTrxID) throws ExcLineForSTPSRMException,TransactionException,CommandProcessingException;
	
	public IExcLineForSTPSRMTrxValue checkerApprove(ITrxContext anITrxContext, IExcLineForSTPSRMTrxValue trx) throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue makerUpdateSaveUpdate(ITrxContext anITrxContext, IExcLineForSTPSRMTrxValue trx, IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue makerSave(ITrxContext anITrxContext, IExcLineForSTPSRM obj)throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;

	public IExcLineForSTPSRMTrxValue getTrxValue(long id)	throws ExcLineForSTPSRMException,TransactionException,CommandProcessingException;
	
	public IExcLineForSTPSRMTrxValue makerUpdate(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn,
			IExcLineForSTPSRM obj)throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue checkerReject(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;

	public IExcLineForSTPSRMTrxValue makerEditRejected(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn,IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;

	public IExcLineForSTPSRMTrxValue makerCloseRejected(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;

	public IExcLineForSTPSRMTrxValue makerUpdateSaveCreate(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn,IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue makerCloseDraft(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;
	
	public IExcLineForSTPSRMTrxValue makerDelete(ITrxContext ctx, IExcLineForSTPSRMTrxValue trxValueIn,IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException,TrxParameterException,TransactionException;

}