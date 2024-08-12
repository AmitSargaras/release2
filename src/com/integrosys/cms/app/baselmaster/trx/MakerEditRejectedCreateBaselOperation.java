package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class MakerEditRejectedCreateBaselOperation extends AbstractBaselTrxOperation{
	

	
	 public MakerEditRejectedCreateBaselOperation()
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
	        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_BASEL;
	    }

	    /**
	    * Process the transaction
	    * 1. Create Staging record
	    * 2. Update the transaction record
	    * @param anITrxValue - ITrxValue
	    * @return ITrxResult - the transaction result
	    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
	    */
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	    {
	    	IBaselMasterTrxValue idxTrxValue = getBaselTrxValue(anITrxValue);
	     /*   IHoliday stage = idxTrxValue.getStagingHoliday();
	        IHoliday replicatedHoliday = HolidayReplicationUtils.replicateHolidayForCreateStagingCopy(stage);
	        idxTrxValue.setStagingHoliday(replicatedHoliday);*/

	    	IBaselMasterTrxValue trxValue = createStagingBasel(idxTrxValue);
	        trxValue = updateBaselTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }



}
