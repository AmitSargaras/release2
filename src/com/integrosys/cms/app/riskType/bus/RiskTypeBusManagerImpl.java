package com.integrosys.cms.app.riskType.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class RiskTypeBusManagerImpl extends AbstractRiskTypeBusManager implements IRiskTypeBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging RiskType table  
     * 
     */
	
	
	public String getRiskTypeName() {
		return IRiskTypeDao.ACTUAL_RISK_TYPE_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated RiskType Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IRiskType updateToWorkingCopy(IRiskType workingCopy, IRiskType imageCopy)
	throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IRiskType updated;
		try{
			workingCopy.setRiskTypeName(imageCopy.getRiskTypeName());
			workingCopy.setRiskTypeCode(imageCopy.getRiskTypeCode());
			
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setCreateBy(imageCopy.getCreateBy());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			updated = updateRiskType(workingCopy);
			return updateRiskType(updated);
		}catch (Exception e) {
			throw new RiskTypeException("Error while Copying copy to main file");
		}
	}
	
	public IRiskType deleteToWorkingCopy(IRiskType workingCopy, IRiskType imageCopy)
			throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException {
				IRiskType updated;
				try{
					workingCopy.setRiskTypeName(imageCopy.getRiskTypeName());
					workingCopy.setRiskTypeCode(imageCopy.getRiskTypeCode());
					workingCopy.setStatus(imageCopy.getStatus());
					workingCopy.setDeprecated(imageCopy.getDeprecated());
					workingCopy.setCreateBy(imageCopy.getCreateBy());
					workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
					updated = updateRiskType(workingCopy);
					return deleteRiskType(updated);
				}catch (Exception e) {
					throw new RiskTypeException("Error while Copying copy to main file");
				}
			}
			
	
	/**
	 * @return List of all authorized RiskType
	 */
	

	public SearchResult getAllRiskType()throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getRiskTypeJdbc().getAllRiskType();
	}
	
	/**
	 * @return List of all authorized RiskType
	 */
	

	public SearchResult getAllFilteredRiskType(String code,String name)throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getRiskTypeJdbc().getAllFilteredRiskType(code,name);
	}
	
	
}
