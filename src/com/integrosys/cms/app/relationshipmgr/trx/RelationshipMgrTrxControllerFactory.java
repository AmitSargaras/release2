package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * Trx controller factory
 */
public class RelationshipMgrTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController relationshipMgrReadController;

    private ITrxController relationshipMgrTrxController;
    
    private ITrxController relationshipMgrInsertFileTrxController;
    
    private ITrxController readRelationshipMgrInsertFileTrxController;
    
	/**
	 * @return the relationshipMgrInsertFileTrxController
	 */
	public ITrxController getRelationshipMgrInsertFileTrxController() {
		return relationshipMgrInsertFileTrxController;
	}

	/**
	 * @param relationshipMgrInsertFileTrxController the relationshipMgrInsertFileTrxController to set
	 */
	public void setRelationshipMgrInsertFileTrxController(
			ITrxController relationshipMgrInsertFileTrxController) {
		this.relationshipMgrInsertFileTrxController = relationshipMgrInsertFileTrxController;
	}

	/**
	 * @return the readRelationshipMgrInsertFileTrxController
	 */
	public ITrxController getReadRelationshipMgrInsertFileTrxController() {
		return readRelationshipMgrInsertFileTrxController;
	}

	/**
	 * @param readRelationshipMgrInsertFileTrxController the readRelationshipMgrInsertFileTrxController to set
	 */
	public void setReadRelationshipMgrInsertFileTrxController(
			ITrxController readRelationshipMgrInsertFileTrxController) {
		this.readRelationshipMgrInsertFileTrxController = readRelationshipMgrInsertFileTrxController;
	}

	/**
	 * @return the relationshipMgrReadController
	 */
	public ITrxController getRelationshipMgrReadController() {
		return relationshipMgrReadController;
	}

	/**
	 * @param relationshipMgrReadController the relationshipMgrReadController to set
	 */
	public void setRelationshipMgrReadController(
			ITrxController relationshipMgrReadController) {
		this.relationshipMgrReadController = relationshipMgrReadController;
	}

	/**
	 * @return the relationshipMgrTrxController
	 */
	public ITrxController getRelationshipMgrTrxController() {
		return relationshipMgrTrxController;
	}

	/**
	 * @param relationshipMgrTrxController the relationshipMgrTrxController to set
	 */
	public void setRelationshipMgrTrxController(
			ITrxController relationshipMgrTrxController) {
		this.relationshipMgrTrxController = relationshipMgrTrxController;
	}

	public RelationshipMgrTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        {
            return getRelationshipMgrReadController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getRelationshipMgrInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getRelationshipMgrInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadRelationshipMgrInsertFileTrxController();
        }
        return getRelationshipMgrTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_RELATIONSHIP_MGR)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_RELATIONSHIP_MGR_ID))) {
            return true;
        }
        return false;
    }
}
