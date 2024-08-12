package com.integrosys.cms.app.discrepency.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */
public abstract class AbstractDiscrepencyBusManager implements IDiscrepencyBusManager{
	
	private IDiscrepencyDAO discrepencyDAO;
	
	public IDiscrepencyDAO getDiscrepencyDAO() {
		return discrepencyDAO;
	}

	public void setDiscrepencyDAO(IDiscrepencyDAO discrepencyDAO) {
		this.discrepencyDAO = discrepencyDAO;
	}

	/**
	 * Gets Entity Name Disprepency 
	 * @return
	 */
	public abstract String getDiscrepencyName();
	
	public IDiscrepency getDiscrepencyById(long id) throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		return getDiscrepencyDAO().getDiscrepency(getDiscrepencyName(), id);
	}
	
	public IDiscrepency getDiscrepency(String entity,long id)
		throws NoSuchDiscrepencyException, TrxParameterException,
		TransactionException {
	return getDiscrepencyDAO().getDiscrepency(getDiscrepencyName(), id);
	}

	public IDiscrepency createDiscrepency(IDiscrepency discrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException {
		return getDiscrepencyDAO().createDiscrepency(getDiscrepencyName(),discrepency);
	}
	
	public IDiscrepency updateDiscrepency(IDiscrepency discrepency)
		throws NoSuchDiscrepencyException, TrxParameterException,
		TransactionException, ConcurrentUpdateException {
	return getDiscrepencyDAO().updateDiscrepency(discrepency);
	}
	
	public SearchResult listDiscrepency(long customerId)throws NoSuchDiscrepencyException {
		return getDiscrepencyDAO().listDiscrepency(customerId);
	}
	
	public IDiscrepency makerUpdateSaveCreateDiscrepency(IDiscrepency discrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException{
		return getDiscrepencyDAO().createDiscrepency(getDiscrepencyName(),discrepency);
	}
		
	public IDiscrepency makerUpdateSaveUpdateDiscrepency(IDiscrepency anICCDiscrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException,ConcurrentUpdateException {
		return getDiscrepencyDAO().updateDiscrepency(anICCDiscrepency);
	}
	
    public IDiscrepency updateToWorkingCopy(IDiscrepency workingCopy, IDiscrepency imageCopy)throws NoSuchDiscrepencyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
	public IDiscrepency deleteDiscrepency(IDiscrepency discrepency) throws NoSuchDiscrepencyException , TrxParameterException, TransactionException{
		return getDiscrepencyDAO().deleteDiscrepency(discrepency);	
	}
}
