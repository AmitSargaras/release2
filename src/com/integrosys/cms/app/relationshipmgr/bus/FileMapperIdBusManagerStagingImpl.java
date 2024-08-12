package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging Holiday
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractRelationshipMgrBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging holiday table  
     * 
     */
	
	public String getRelationshipMgrName() {
        return IRelationshipMgrDAO.STAGE_FILE_MAPPER_ID;
    }


    
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

	
	public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode) {
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