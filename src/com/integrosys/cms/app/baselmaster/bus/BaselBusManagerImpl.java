package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;

public class BaselBusManagerImpl extends AbstractBaselBusManager implements IBaselBusManager{
	
	public String getBaselName() {
		return IBaselDao.ACTUAL_BASEL_NAME;
	}

	
	public SearchResult getAllActualBasel() throws BaselMasterException,TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		
		  return getBaselJdbc().getAllActualBasel();
	}
	
	public SearchResult getAllActualCommon() throws BaselMasterException,TrxParameterException, TransactionException,
	ConcurrentUpdateException {

		return getBaselJdbc().getAllActualCommon();
	}
	
	
	
	
	public IBaselMaster updateToWorkingCopy(IBaselMaster workingCopy, IBaselMaster imageCopy)
	throws BaselMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IBaselMaster updated;
		try{
			
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setSystem(imageCopy.getSystem());
			workingCopy.setSystemValue(imageCopy.getSystemValue());
			workingCopy.setBaselValidation(imageCopy.getBaselValidation());
			workingCopy.setExposureSource(imageCopy.getExposureSource());
			workingCopy.setReportHandOff(imageCopy.getReportHandOff());
			workingCopy.setLastUpdatedBy(imageCopy.getLastUpdatedBy());
			workingCopy.setLastUpdatedOn(imageCopy.getLastUpdatedOn());
			workingCopy.setCreatedBy(imageCopy.getCreatedBy());
			workingCopy.setCreatedOn(imageCopy.getCreatedOn());
			
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateBasel(workingCopy);
			return updateBasel(updated);
		}catch (Exception e) {
			throw new ComponentException("Error while Copying copy to main file");
		}


	}


	
	

	
}
