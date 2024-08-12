
package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows to read CreditApproval transaction using transaction
 * id or primary key
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.0 $
 * @since $Date: 2011/04/04 06:52:43 $ Tag: $Name: $
 */
public class ReadCreditApprovalOperation extends AbstractCreditApprovalTrxOperation implements ITrxReadOperation {

	/**
	 * Defaulc Constructor
	 */
	public ReadCreditApprovalOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CREDIT_APPROVAL;
	}

	/**
	 * This method is used to read a transaction object given a transaction ID
	 * 
	 * @param value is the ITrxValue object containing the parameters required
	 *        for retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {

		ICreditApprovalTrxValue vv = (ICreditApprovalTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.trim().equals("")) {

				ICreditApproval CreditApproval = getCreditApprovalBusManager().getCreditApprovalEntry(vv.getReferenceID());

				DefaultLogger.debug(this, "CreditApproval id = " + CreditApproval.getId());

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(CreditApproval.getId()),
						ICMSConstant.INSTANCE_CREDIT_APPROVAL);

				vv = new OBCreditApprovalTrxValue(trxValue);
				vv.setCreditApproval(CreditApproval);

			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBCreditApprovalTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					ICreditApproval creditApproval = getCreditApprovalBusManager().getCreditApprovalEntry(Long.parseLong(vv.getReferenceID()));
					vv.setCreditApproval(creditApproval);


					vv.setCreditApproval(creditApproval);

				}

			}

			DefaultLogger.debug(this, "transaction found for creditapproval group, trx ID is " + trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				ICreditApproval staging = getStagingCreditApprovalFeedBusManager().getCreditApprovalEntry(
						Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingCreditApproval(staging);


			}
			
			return vv;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in Read Operation: ", e);
			e.printStackTrace();
			throw new TrxOperationException("Error in Read Operation: "+e.getMessage());
		}

		
	}

}