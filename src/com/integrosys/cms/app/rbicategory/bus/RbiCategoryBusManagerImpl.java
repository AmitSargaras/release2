package com.integrosys.cms.app.rbicategory.bus;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
*@author $Author: Govind.Sahu$
*Bus Manager Implication to communicate with DAO and JDBC
 */
public class RbiCategoryBusManagerImpl extends AbstractRbiCategoryBusManager implements IRbiCategoryBusManager {


	
	/**
	 * 
	 * @param id
	 * @return RbiCategory Object
	 * 
	 */
	

	public IRbiCategory getRbiCategoryById(long id) throws RbiCategoryException,TrxParameterException,TransactionException {
		
		return getRbiCategoryDao().load( getRbiCategoryName(), id);
	}

	/**
	 * @return WorkingCopy-- updated Rbi Category Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IRbiCategory updateToWorkingCopy(IRbiCategory workingCopy, IRbiCategory imageCopy)
	throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IRbiCategory updated;
		try{
			
			workingCopy.setIndustryNameId(imageCopy.getIndustryNameId());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setCreateBy(imageCopy.getCreateBy());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			workingCopy.setLastUpdateDate(imageCopy.getLastUpdateDate());
			workingCopy.setCreationDate(imageCopy.getCreationDate());
			workingCopy.setStatus(imageCopy.getStatus());
			Set replicatedIndNameSet = new HashSet();
			Set stagingIndNameSet = imageCopy.getStageIndustryNameSet();
			Iterator it = stagingIndNameSet.iterator();
			OBIndustryCodeCategory industryNameStageObj = new OBIndustryCodeCategory();
			while(it.hasNext())
			{
				OBIndustryCodeCategory repIndustryNameStageObj = new OBIndustryCodeCategory();
				industryNameStageObj = (OBIndustryCodeCategory)it.next();
				repIndustryNameStageObj.setRbiCategoryId(industryNameStageObj.getRbiCategoryId());
			    repIndustryNameStageObj.setRbiCodeCategoryId(industryNameStageObj.getRbiCodeCategoryId());
				replicatedIndNameSet.add(repIndustryNameStageObj);
				
			}
			workingCopy.setStageIndustryNameSet(replicatedIndNameSet);
			updated = updateRbiCategory(workingCopy);
			return updated;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("Error while Copying copy to main file");
		}


	}
	
	/**
	 * @return List of all authorized Rbi Category
	 */
	

	public List getAllRbiCategoryList()throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return (List) getRbiCategoryDao().getAllRbiCategoryList(getRbiCategoryName());
		
	}
	
	public List getRbiIndCodeByNameList(String indName)throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return (List) getRbiCategoryDao().getRbiIndCodeByNameList(indName,getRbiCategoryName());
		
	}
	
	/**
	 * @return List of all authorized Rbi Category
	 */
	

	public List searchRbiCategory(String srAlph)throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return (List) getRbiCategoryDao().searchRbiCategory(srAlph,getRbiCategoryName());
		
	}

	public String getRbiCategoryName() {
		return IRbiCategoryDao.ACTUAL_RBI_CATEGORY_NAME;
	}


	/**
	 * @return Get Actual RbiCategory Object
	 */
	

	public boolean getActualRbiCategory(IRbiCategory rbiCategory)throws RbiCategoryException {
		return (boolean) getRbiCategoryDao().getActualRbiCategory(getRbiCategoryName(),rbiCategory);
		
	}
	

	/*
	 *  This method return true if Industry Name already approve else return false.
	 */
	public boolean isIndustryNameApprove(String industryNameId) {
		return getRbiCategoryDao().isIndustryNameApprove(getRbiCategoryName(),industryNameId);
	}
	/*
	 *  This method return true if Rbi Code Category already assign to Industry else return false.
	 */
	public List isRbiCodeCategoryApprove(OBRbiCategory stgObRbiCategory, boolean isEdit, OBRbiCategory actObRbiCategory) {
		return getRbiCategoryDao().isRbiCodeCategoryApprove(getRbiCategoryName(), stgObRbiCategory, isEdit, actObRbiCategory);
	}
	

}
