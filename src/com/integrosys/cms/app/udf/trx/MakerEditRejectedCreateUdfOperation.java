package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedCreateUdfOperation extends AbstractUdfTrxOperation{

	 public MakerEditRejectedCreateUdfOperation(){
		 super();
	 }
	  
	public String getOperationName() {
      return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_UDF;
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
	  
      IUdfTrxValue idxTrxValue = super.getUdfTrxValue(anITrxValue);
      IUdfTrxValue trxValue = createStagingUdf(idxTrxValue);
      trxValue = super.updateUdfTrx(trxValue);
      return super.prepareResult(trxValue);
  }
}
