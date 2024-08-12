package com.integrosys.cms.app.facilityNewMaster.bus;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
*@author $Author: Abhijit R$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class FacilityNewMasterBusManagerImpl extends AbstractFacilityNewMasterBusManager implements IFacilityNewMasterBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging FacilityNewMaster table  
     * 
     */
	
	
	public String getFacilityNewMasterName() {
		return IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME;
	}

	/**
	 * 
	 * @param id
	 * @return FacilityNewMaster Object
	 * 
	 */
	

	public IFacilityNewMaster getSystemBankById(long id) throws FacilityNewMasterException,TrxParameterException,TransactionException {
		
		return getFacilityNewMasterDao().load( getFacilityNewMasterName(), id);
	}

	/**
	 * @return WorkingCopy-- updated FacilityNewMaster Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IFacilityNewMaster updateToWorkingCopy(IFacilityNewMaster workingCopy, IFacilityNewMaster imageCopy)
	throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IFacilityNewMaster updated;
		try{
			workingCopy.setNewFacilityCode(imageCopy.getNewFacilityCode());
			workingCopy.setNewFacilityName(imageCopy.getNewFacilityName());
			workingCopy.setNewFacilityType(imageCopy.getNewFacilityType());
			workingCopy.setNewFacilityCategory(imageCopy.getNewFacilityCategory());
			workingCopy.setLineNumber(imageCopy.getLineNumber());
			workingCopy.setPurpose(imageCopy.getPurpose());
			workingCopy.setNewFacilitySystem(imageCopy.getNewFacilitySystem());
			workingCopy.setWeightage(imageCopy.getWeightage());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setRuleId(imageCopy.getRuleId());
			workingCopy.setRuleId(imageCopy.getRuleId());
			workingCopy.setProductAllowed(imageCopy.getProductAllowed());
			workingCopy.setCurrencyRestriction(imageCopy.getCurrencyRestriction());
			workingCopy.setRevolvingLine(imageCopy.getRevolvingLine());
			workingCopy.setLineCurrency(imageCopy.getLineCurrency());
			workingCopy.setIntradayLimit(imageCopy.getIntradayLimit());
			workingCopy.setStlFlag(imageCopy.getStlFlag());
			workingCopy.setLineDescription(imageCopy.getLineDescription());
			workingCopy.setScmFlag(imageCopy.getScmFlag());
			workingCopy.setSelectedRiskTypes(imageCopy.getSelectedRiskTypes());
			workingCopy.setAvailAndOptionApplicable(imageCopy.getAvailAndOptionApplicable());
			workingCopy.setLineExcludeFromLoa(imageCopy.getLineExcludeFromLoa());
			workingCopy.setIdlApplicableFlag(imageCopy.getIdlApplicableFlag());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateFacilityNewMaster(workingCopy);
			return updateFacilityNewMaster(updated);
		}catch (Exception e) {
			throw new FacilityNewMasterException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized FacilityNewMaster
	 */
	

	public SearchResult getAllFacilityNewMaster()throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getFacilityNewMasterJdbc().getAllFacilityNewMaster();
	}

	public boolean isFacilityNameUnique(String facilityName) {
		return getFacilityNewMasterDao().isFacilityNameUnique(facilityName);
	}
	/**
	 * getFilteredActualFacilityNewMaster -  return List of FacilityNewMaster based on input criteria
	 */
	public SearchResult getFilteredActualFacilityNewMaster(String code, String name,
			String category, String type, String system, String line)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		 return getFacilityNewMasterJdbc().getFilteredActualFacilityNewMaster(code,name,category,type,system,line);
	}

}
