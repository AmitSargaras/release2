package com.integrosys.cms.app.discrepency.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 01/06/2011 06:35:00
 */

public class DiscrepencyBusManagerImpl implements IDiscrepencyBusManager {

	private IDiscrepencyDAO discrepencyDAO;
	
	public IDiscrepencyDAO getDiscrepencyDAO() {
		return discrepencyDAO;
	}

	public void setDiscrepencyDAO(IDiscrepencyDAO discrepencyDAO) {
		this.discrepencyDAO = discrepencyDAO;
	}

	public IDiscrepency createDiscrepency(IDiscrepency discrepency) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		return getDiscrepencyDAO().createDiscrepency(discrepency);
	}

	public IDiscrepency deleteDiscrepency(IDiscrepency discrepency) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		return getDiscrepencyDAO().deleteDiscrepency(discrepency);
	}

	public IDiscrepency getDiscrepencyById(long id) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		return getDiscrepencyDAO().getDiscrepencyById(id);
	}

	public SearchResult listDiscrepency(long customerId)
			throws NoSuchDiscrepencyException {
		return getDiscrepencyDAO().listDiscrepency(customerId);
	}

	public IDiscrepency updateDiscrepency(IDiscrepency discrepency) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return getDiscrepencyDAO().updateDiscrepency(discrepency);
	}

	public IDiscrepency updateToWorkingCopy(IDiscrepency workingCopy, IDiscrepency imageCopy)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		IDiscrepency updated;
		try {
			workingCopy.setAcceptedDate(imageCopy.getAcceptedDate());
			workingCopy.setCreationDate(imageCopy.getCreationDate());
			workingCopy.setNextDueDate(imageCopy.getNextDueDate());
			workingCopy.setOriginalTargetDate(imageCopy.getOriginalTargetDate());
			workingCopy.setDiscrepency(imageCopy.getDiscrepency());
			workingCopy.setDiscrepencyRemark(imageCopy.getDiscrepencyRemark());
			workingCopy.setDiscrepencyType(imageCopy.getDiscrepencyType());
			workingCopy.setApprovedBy(imageCopy.getApprovedBy());
			workingCopy.setCritical(imageCopy.getCritical());
			workingCopy.setCounter(imageCopy.getCounter());
			workingCopy.setRecDate(imageCopy.getRecDate());
			workingCopy.setWaiveDate(imageCopy.getWaiveDate());
			workingCopy.setTotalDeferedDays(imageCopy.getTotalDeferedDays());
			workingCopy.setOriginalDeferedDays(imageCopy.getOriginalDeferedDays());
			workingCopy.setDeferedCounter(imageCopy.getDeferedCounter());
			workingCopy.setDeferDate(imageCopy.getDeferDate());
			if(imageCopy.getStatus()!=null){
				if(imageCopy.getStatus().equals("PENDING_CLOSE"))
				workingCopy.setStatus("CLOSED");
				if(imageCopy.getStatus().equals("PENDING_DEFER"))
					workingCopy.setStatus("DEFERED");
				if(imageCopy.getStatus().equals("PENDING_WAIVE"))
					workingCopy.setStatus("WAIVED");
				if(imageCopy.getStatus().equals("PENDING_CREATE"))
					workingCopy.setStatus("ACTIVE");
			}
			workingCopy.setCreditApprover(imageCopy.getCreditApprover());
			workingCopy.setDocRemarks(imageCopy.getDocRemarks());
//			workingCopy.setFacilityList(imageCopy.getFacilityList());
			workingCopy.setFacilityList(imageCopy.getFacilityList());
			updated = updateDiscrepency(workingCopy);
		} catch (NoSuchDiscrepencyException e) {
			throw new NoSuchDiscrepencyException(
					"Error while Copying copy to main file");
		}
		return updateDiscrepency(updated);
	}

	public IDiscrepency makerUpdateSaveCreateDiscrepency(IDiscrepency anICCDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		return getDiscrepencyDAO().createDiscrepency(anICCDiscrepency);
	}

	public IDiscrepency makerUpdateSaveUpdateDiscrepency(IDiscrepency anICCDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		try {
			return getDiscrepencyDAO().updateDiscrepency(anICCDiscrepency);
		} catch (ConcurrentUpdateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
