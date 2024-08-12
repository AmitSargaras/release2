package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedCreateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{

	 public MakerEditRejectedCreateGoodsMasterOperation(){
		 super();
	 }
	  
	public String getOperationName() {
      return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_GOODS_MASTER;
  }
	
	/**
   * Process the transaction
   * 1.	Create the actual data
   * 2.	Update the transaction record
   *
   * @param anITrxValue of ITrxValue type
   * @return ITrxResult - the transaction result
   * @throws com.integrosys.base.businfra.transaction.TrxOperationException
   *          if encounters any error during the processing of the transaction
   */
  public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
	  
      IGoodsMasterTrxValue idxTrxValue = super.getGoodsMasterTrxValue(anITrxValue);
      IGoodsMasterTrxValue trxValue = createStagingGoodsMaster(idxTrxValue);
      trxValue = super.updateGoodsMasterTrx(trxValue);
      return super.prepareResult(trxValue);
  }
}
