package com.integrosys.cms.app.newtatmaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;

public class TatMasterBusManagerImpl extends AbstractTatMasterBusManager implements ITatMasterBusManager{
	
	public String getTatMasterName() {
		return ITatMasterDao.ACTUAL_TAT_MASTER_NAME;
	}
	
	public SearchResult getAllTatEvents()throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getTatMasterJdbc().getAllTatEvents();
	}
	
	
	public INewTatMaster updateToWorkingCopy(INewTatMaster workingCopy, INewTatMaster imageCopy)
	throws TatMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		INewTatMaster updated;
		try{
			
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setStartTime(imageCopy.getStartTime());
			workingCopy.setEndTime(imageCopy.getEndTime());
			workingCopy.setUserEvent(imageCopy.getUserEvent());
			workingCopy.setTimingHours(imageCopy.getTimingHours());
			workingCopy.setTimingMin(imageCopy.getTimingMin());
			workingCopy.setCreatedBy(imageCopy.getCreatedBy());
			workingCopy.setCreatedOn(imageCopy.getCreatedOn());
			workingCopy.setLastUpdatedBy(imageCopy.getLastUpdatedBy());
			workingCopy.setLastUpdatedOn(imageCopy.getLastUpdatedOn());
			workingCopy.setEventCode(imageCopy.getEventCode());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateTatMaster(workingCopy);
			return updateTatMaster(updated);
		}catch (Exception e) {
			throw new ComponentException("Error while Copying copy to main file");
		}


	}

}
