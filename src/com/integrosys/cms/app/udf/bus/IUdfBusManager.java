package com.integrosys.cms.app.udf.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IUdfBusManager {
	public IUdf insertUdf(IUdf udf) throws UdfException;
	public IUdf findUdfById(String entityName, long id) throws UdfException;
	public void deleteUdf(IUdf udf) throws UdfException;
	public IUdf updateUdf(IUdf udf) throws UdfException;
	public List findAllUdfs() throws UdfException;
	public List getUdfSequencesByModuleId (String moduleId)  throws UdfException;
	public void freezeUdf(IUdf udf) throws UdfException;
	public List findUdfByStatus(String entityName, String status) throws UdfException;
	public List getUdfByModuleIdAndStatus (String moduleId, String status)  throws UdfException;
	
	SearchResult getAllUdf()throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
//	public boolean isProductCodeUnique(String productCode);
	IUdf createUdf(IUdf udf)throws UdfException;
	IUdf updateUdfNew(IUdf item) throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	IUdf getUdfById(long id) throws UdfException,TrxParameterException,TransactionException;
	IUdf updateToWorkingCopy(IUdf workingCopy, IUdf imageCopy) throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IUdf deleteUdfNew(IUdf item) throws UdfException,
	TrxParameterException, TransactionException,
	ConcurrentUpdateException;

IUdf enableUdf(IUdf item) throws UdfException,
	TrxParameterException, TransactionException,
	ConcurrentUpdateException;
	
	
}
