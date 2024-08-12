package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerImpl extends AbstractRelationshipMgrBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getRelationshipMgrName() {
        return IRelationshipMgrDAO.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  holiday can never be working copy
	 */
    
    public IRelationshipMgr updateToWorkingCopy(IRelationshipMgr workingCopy, IRelationshipMgr imageCopy)
            throws RelationshipMgrException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
    
    public IRelationshipMgr deleteRelationshipMgr(
			IRelationshipMgr relationshipMgr) throws RelationshipMgrException,
			TrxParameterException, TransactionException {
		throw new RelationshipMgrException("IRelationshipMgr object is null");
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