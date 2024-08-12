package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankReplicationUtils;

/**
 * 
 * @author abhijit.rudrakshawar
 */
public class MakerSaveUpdateSystemBankOperation extends AbstractSystemBankTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveUpdateSystemBankOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK;
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
        ISystemBankTrxValue idxTrxValue = getSystemBankTrxValue(anITrxValue);
        ISystemBank stage = idxTrxValue.getStagingSystemBank();
        ISystemBank replicatedSystemBank = SystemBankReplicationUtils.replicateSystemBankForCreateStagingCopy(stage);
        idxTrxValue.setStagingSystemBank(replicatedSystemBank);

        ISystemBankTrxValue trxValue = createStagingSystemBank(idxTrxValue);
        trxValue = updateSystemBankTrx(trxValue);
        return super.prepareResult(trxValue);
    }

	
	

}
