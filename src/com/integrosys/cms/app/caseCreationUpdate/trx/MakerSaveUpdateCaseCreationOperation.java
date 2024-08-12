package com.integrosys.cms.app.caseCreationUpdate.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveUpdateCaseCreationOperation extends AbstractCaseCreationTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveUpdateCaseCreationOperation()
    {
        super();
    }

	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CASECREATION;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ICaseCreationTrxValue idxTrxValue = getCaseCreationTrxValue(anITrxValue);
        ICaseCreation stage = idxTrxValue.getStagingCaseCreation();
        ICaseCreation replicatedCaseCreation = CaseCreationReplicationUtils.replicateCaseCreationForCreateStagingCopy(stage);
     //   replicatedCaseCreation.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingCaseCreation(replicatedCaseCreation);

        ICaseCreationTrxValue trxValue = createStagingCaseCreation(idxTrxValue);
        trxValue = updateCaseCreationTrx(trxValue);
        return super.prepareResult(trxValue);
    }

	
	

}
