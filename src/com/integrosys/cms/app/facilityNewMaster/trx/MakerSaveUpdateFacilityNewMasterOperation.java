package com.integrosys.cms.app.facilityNewMaster.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveUpdateFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveUpdateFacilityNewMasterOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_FACILITY_NEW_MASTER;
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
        IFacilityNewMasterTrxValue idxTrxValue = getFacilityNewMasterTrxValue(anITrxValue);
        IFacilityNewMaster stage = idxTrxValue.getStagingFacilityNewMaster();
        IFacilityNewMaster replicatedFacilityNewMaster = FacilityNewMasterReplicationUtils.replicateFacilityNewMasterForCreateStagingCopy(stage);
     //   replicatedFacilityNewMaster.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingFacilityNewMaster(replicatedFacilityNewMaster);

        IFacilityNewMasterTrxValue trxValue = createStagingFacilityNewMaster(idxTrxValue);
        trxValue = updateFacilityNewMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }

	
	

}
