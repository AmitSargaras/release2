package com.integrosys.cms.app.udf.proxy;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;

public interface IUdfProxyManager {
	public IUdf insertUdf(IUdf udf) throws UdfException;
	public IUdf findUdfById(String entityName, long id) throws UdfException;
	public void deleteUdf(IUdf udf) throws UdfException;
	public void freezeUdf(IUdf udf) throws UdfException;
	public IUdf updateUdf(IUdf udf) throws UdfException, TrxParameterException, TransactionException;
	public List findAllUdfs() throws UdfException;	
	public List getUdfSequencesByModuleId (String moduleId)  throws UdfException;
	public List findUdfByStatus(String entityName, String status) throws UdfException;
	public List getUdfByModuleIdAndStatus (String moduleId, String status)  throws UdfException;
	


	public SearchResult getAllActualUdf() throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue makerCreateUdf(ITrxContext anITrxContext, IUdf anICCUdf)throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue getUdfByTrxID(String aTrxID) throws UdfException,TransactionException,CommandProcessingException;
	
	public IUdfTrxValue checkerApproveUdf(ITrxContext anITrxContext, IUdfTrxValue anIUdfTrxValue) throws UdfException,TrxParameterException,TransactionException;
	
//	public boolean isProductCodeUnique(String productCode);
	
	public IUdfTrxValue makerUpdateSaveUpdateUdf(ITrxContext anITrxContext, IUdfTrxValue anICCUdfTrxValue, IUdf anICCUdf)
			throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue makerSaveUdf(ITrxContext anITrxContext, IUdf anICCUdf)throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue getUdfTrxValue(long productCode)
			throws UdfException,TransactionException,CommandProcessingException;

	public IUdfTrxValue makerUpdateUdf(OBTrxContext ctx, IUdfTrxValue trxValueIn,
			OBUdf productMaster)throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue checkerRejectUdf(ITrxContext ctx, IUdfTrxValue trxValueIn)
			throws UdfException,TrxParameterException,TransactionException;

	public IUdfTrxValue makerEditRejectedUdf(ITrxContext ctx, IUdfTrxValue trxValueIn,IUdf anICCUdf)
			throws UdfException,TrxParameterException,TransactionException;

	public IUdfTrxValue makerCloseRejectedUdf(ITrxContext ctx, IUdfTrxValue trxValueIn)
			throws UdfException,TrxParameterException,TransactionException;

	public IUdfTrxValue makerUpdateSaveCreateUdf(ITrxContext ctx, IUdfTrxValue trxValueIn,IUdf anICCUdf)
			throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue makerCloseDraftUdf(ITrxContext ctx, IUdfTrxValue trxValueIn)
			throws UdfException,TrxParameterException,TransactionException;
	
	public IUdfTrxValue makerActivateUdf(
			ITrxContext anITrxContext,
			IUdfTrxValue anICCUdfTrxValue,
			IUdf anICCUdf) throws UdfException,
			TrxParameterException, TransactionException;


	public IUdfTrxValue makerDeleteUdf(ITrxContext anITrxContext,
			IUdfTrxValue anICCUdfTrxValue,
			IUdf anICCUdf) throws UdfException,
			TrxParameterException, TransactionException;
//	public SearchResult getAllFilteredActualUdf(String code,String name)
//			throws UdfException,TrxParameterException,TransactionException;

}
