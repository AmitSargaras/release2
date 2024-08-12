package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 * Bus Manager Implication for staging Relationship Manager 
 */
public class RelationshipMgrBusManagerStagingImpl extends AbstractRelationshipMgrBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank  table  
     * 
     */
	
    public String getRelationshipMgrName() {
        return IRelationshipMgrDAO.STAGING_ENTITY_NAME;
    }
    /**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

    public IRelationshipMgr updateToWorkingCopy(IRelationshipMgr workingCopy, IRelationshipMgr imageCopy)
            throws RelationshipMgrException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the RelationshipMgr details
	 */
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr RelationshipMgr) throws RelationshipMgrException , TrxParameterException, TransactionException{
		throw new IllegalStateException("'RelationshipMgr' cannot be null.");	
	}
	@Override
	public IHRMSData getHRMSEmpDetails(String rmEmpID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IRelationshipMgr getRMDetails(String rmID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insertHRMSData(String[] data) {
		// TODO Auto-generated method stub
	}
	@Override
	public void updateHRMSData(IHRMSData  ihrmsData,String[] data) {
		// TODO Auto-generated method stub
	}
}