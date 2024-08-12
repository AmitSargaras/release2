/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ReadCustodianDocIDTrxOperation.java,v 1.4 2003/07/28 02:09:12 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation is used for reading a transaction object, and its associated
 * business object.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/28 02:09:12 $ Tag: $Name: $
 */

public class ReadCustodianDocIDTrxOperation extends ReadCustodianDocTrxOperation {
	public ReadCustodianDocIDTrxOperation() {
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CUSTODIAN_ID_DOC;
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
		if (null == value) {
			throw new TransactionException("ITrxValue is null!");
		}
		ICMSTrxValue newValue = super.getCMSTrxValue(value);
		String refID = newValue.getReferenceID();
		if (null == refID) {
			throw new TransactionException("ReferenceID is null!");
		}
		try {
			SBCMSTrxManager mgr = getTrxManager();
			newValue = getCMSTrxValue(mgr.getTrxByRefIDAndTrxType(refID, ICMSConstant.INSTANCE_CUSTODIAN));
			Long custDocID = new Long(newValue.getStagingReferenceID());
			ICustodianDoc stagingCustDoc = getSBStagingCustodianBusManager().getCustodianDoc(custDocID.longValue());
			OBCustodianTrxValue custDocTrxValue = new OBCustodianTrxValue(newValue);
			custDocTrxValue.setStagingCustodianDoc(stagingCustDoc);
			custDocID = new Long(refID);
			custDocTrxValue.setCustodianDoc(getSBCustodianBusManager().getCustodianDoc(custDocID.longValue()));
			return custDocTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TransactionException("Caught Exception at getTransaction: " + ex.toString());
		}
	}
}
