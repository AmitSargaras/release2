package com.integrosys.cms.app.discrepency.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public interface IDiscrepencyBusManager {

	public SearchResult listDiscrepency(long customerId) throws NoSuchDiscrepencyException;

	public IDiscrepency getDiscrepencyById(long id) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException;
	
	public IDiscrepency updateDiscrepency(IDiscrepency discrepency ) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IDiscrepency updateToWorkingCopy(IDiscrepency workingCopy, IDiscrepency imageCopy) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IDiscrepency createDiscrepency(IDiscrepency discrepency )throws NoSuchDiscrepencyException, TrxParameterException, TransactionException;
	
	public IDiscrepency deleteDiscrepency(IDiscrepency discrepency ) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException;
	
	public IDiscrepency makerUpdateSaveCreateDiscrepency(IDiscrepency anICCDiscrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException;
	
	public IDiscrepency makerUpdateSaveUpdateDiscrepency(IDiscrepency anICCDiscrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException,ConcurrentUpdateException;
}
