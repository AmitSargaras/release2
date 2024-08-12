package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * This RelationshipMgrBusManagerImpl implements all the methods of  IRelationshipMgrBusManager 
 * $Author: Dattatray Thorat
 * @version $Revision: 1.2 $
 * @since $Date: 2011/02/18 11:32:23 $ Tag: $Name: $
 */

public class RelationshipMgrBusManagerImpl extends AbstractRelationshipMgrBusManager implements IRelationshipMgrBusManager  {
	
	IRelationshipMgrDAO relationshipMgrDAO;

	/**
	 * @return the relationshipMgrDAO
	 */
	public IRelationshipMgrDAO getRelationshipMgrDAO() {
		return relationshipMgrDAO;
	}

	/**
	 * @param relationshipMgrDAO the relationshipMgrDAO to set
	 */
	public void setRelationshipMgrDAO(IRelationshipMgrDAO relationshipMgrDAO) {
		this.relationshipMgrDAO = relationshipMgrDAO;
	}
	
	public String getRelationshipMgrName(){
		return IRelationshipMgrDAO.ACTUAL_ENTITY_NAME;
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getRelationshipMgr() throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrDAO().getRelationshipMgr();
	}

	/**
	 * @return the RelationshipMgr Details
	 */
	public IRelationshipMgr getRelationshipMgrById(long id) throws RelationshipMgrException {
		return (IRelationshipMgr)getRelationshipMgrDAO().getRelationshipMgrById(id);
	}

	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrDAO().getRelationshipMgrList(rmCode,rmName);
	}

	public SearchResult getRelationshipMgrList(String regionId) throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrDAO().getRelationshipMgrList(regionId);
	}
	
	/**
	 * @return the RelationshipMgr Details
	 */
	public IRelationshipMgr updateRelationshipMgr(IRelationshipMgr RelationshipMgr) throws RelationshipMgrException {
		return (IRelationshipMgr)getRelationshipMgrDAO().updateRelationshipMgr(RelationshipMgr);
	}
	
	/**
	 * @return the RelationshipMgr Details
	 */
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr RelationshipMgr) throws RelationshipMgrException {
		return (IRelationshipMgr)getRelationshipMgrDAO().deleteRelationshipMgr(RelationshipMgr);
	}
	
	/**
	 * @return the RelationshipMgr Details
	 */
	public IRelationshipMgr createRelationshipMgr(IRelationshipMgr RelationshipMgr) throws RelationshipMgrException {
		return (IRelationshipMgr)getRelationshipMgrDAO().createRelationshipMgr(RelationshipMgr);
	}
	
	/**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

	public IRelationshipMgr updateToWorkingCopy(IRelationshipMgr workingCopy,IRelationshipMgr imageCopy) throws RelationshipMgrException,TrxParameterException, 
	TransactionException,ConcurrentUpdateException {
		IRelationshipMgr updated;
		try {
			workingCopy.setRelationshipMgrName(imageCopy.getRelationshipMgrName());
			workingCopy.setRelationshipMgrMailId(imageCopy.getRelationshipMgrMailId());
			workingCopy.setReportingHeadName(imageCopy.getReportingHeadName());
			workingCopy.setReportingHeadMailId(imageCopy.getReportingHeadMailId());
			workingCopy.setRegion(imageCopy.getRegion());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setEmployeeId(imageCopy.getEmployeeId());
			workingCopy.setWboRegion(imageCopy.getWboRegion());
			workingCopy.setReportingHeadRegion(imageCopy.getReportingHeadRegion());
			
			updated = updateRelationshipMgr(workingCopy);
		} catch (RelationshipMgrException e) {
			throw new RelationshipMgrException(
					"Error while Copying copy to main file");
		}

		return updateRelationshipMgr(updated);
	}
	
	public boolean isRMCodeUnique(String rmCode){
		 return getRelationshipMgrDAO().isRMCodeUnique(rmCode);
	 }

	public boolean isValidRegionCode(String regionCode) {
		return getRelationshipMgrDAO().isValidRegionCode(regionCode);
	}
	
	public boolean isRelationshipMgrNameUnique(String relationshipMgrName) {
		return getRelationshipMgrDAO().isRelationshipMgrNameUnique(relationshipMgrName);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getRelationshipMgrDAO().deleteTransaction(obFileMapperMaster);					
	}
	
	
	public boolean isEmployeeIdUnique(String employeeId) {
		return getRelationshipMgrDAO().isEmployeeIdUnique(employeeId);
	}
	
	public IHRMSData getHRMSEmpDetails(String rmEmpID) {
		return getRelationshipMgrDAO().getHRMSEmpDetails(rmEmpID);
	}
	
	public IRelationshipMgr getRMDetails(String rmID) {
		return getRelationshipMgrDAO().getRMDetails(rmID);
	}
	
	public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode) {
		return getRelationshipMgrDAO().getLocalCAD(cadEmployeeCode, cadBranchCode);
	}
	
	public void insertHRMSData(String[] data) {
	}
	public void updateHRMSData(IHRMSData  ihrmsData,String[] data) {

	}
}
