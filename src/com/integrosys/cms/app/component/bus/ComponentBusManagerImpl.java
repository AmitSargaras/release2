package com.integrosys.cms.app.component.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.AbstractComponentBusManager;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayDao;

public class ComponentBusManagerImpl extends AbstractComponentBusManager implements IComponentBusManager {

	
	public String getComponentName() {
		return IComponentDao.ACTUAL_COMPONENT_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return Component Object
	 * 
	 */
	

	public IComponent getSystemBankById(long id) throws ComponentException,TrxParameterException,TransactionException {
		
		return getComponentDao().load( getComponentName(), id);
	}

	/**
	 * @return WorkingCopy-- updated Component Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IComponent updateToWorkingCopy(IComponent workingCopy, IComponent imageCopy)
	throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IComponent updated;
		try{
			
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setComponentType(imageCopy.getComponentType());
			workingCopy.setComponentCode(imageCopy.getComponentCode());
			workingCopy.setComponentName(imageCopy.getComponentName());
			workingCopy.setHasInsurance(imageCopy.getHasInsurance());
			//Start:-------->Abhishek Naik
			workingCopy.setAge(imageCopy.getAge());
			workingCopy.setDebtors(imageCopy.getDebtors());
			//End:-------->Abhishek Naik
			//Start santosh
			workingCopy.setComponentCategory(imageCopy.getComponentCategory());
			workingCopy.setApplicableForDp(imageCopy.getApplicableForDp());
			//end santosh
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateComponent(workingCopy);
			return updateComponent(updated);
		}catch (Exception e) {
			throw new ComponentException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized Component
	 */
	

	public SearchResult getAllComponent()throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getComponentJdbc().getAllComponent();
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getComponentDao().deleteTransaction(obFileMapperMaster);		
	}

	
	

	
	
}
