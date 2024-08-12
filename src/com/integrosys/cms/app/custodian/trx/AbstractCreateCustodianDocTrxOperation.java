/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/AbstractCreateCustodianDocTrxOperation.java,v 1.7 2005/02/22 09:31:25 wltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for the creation of a custodian doc transaction
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/02/22 09:31:25 $ Tag: $Name: $
 */
public abstract class AbstractCreateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {

	/**
	 * Get the opertion name of the current operation This is to be implemented
	 * in the concrete class
	 * @return String - the operation name of the current operation
	 */
	public abstract String getOperationName();

	/**
	 * Create a custodian transaction
	 * @param anITrxValue - ITrxValue
	 * @return OBCustodianTrxValue - the custodian specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICustodianTrxValue createCustodianTransaction(ITrxValue anITrxValue) throws TrxOperationException {
		ICMSTrxValue trxValue = getCMSTrxValue(anITrxValue);
		try {
			trxValue = getTrxManager().createTransaction(trxValue);
			OBCustodianTrxValue custodianTrxValue = null;
			if (!(trxValue instanceof OBCustodianTrxValue)) {
				custodianTrxValue = new OBCustodianTrxValue(trxValue);
				custodianTrxValue.setStagingCustodianDoc(getCustodianTrxValue(anITrxValue).getStagingCustodianDoc());
				custodianTrxValue.setCustodianDoc(getCustodianTrxValue(anITrxValue).getCustodianDoc());
				return custodianTrxValue;
			}
			return getCustodianTrxValue(trxValue);
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Create the actual custodian doc
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the trx object containing the created actual
	 *         custodian doc
	 * @throws TrxOperationException if errors
	 */
	protected ICustodianTrxValue createActualCustodianDoc(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
			ICustodianDoc custDoc = trxValue.getStagingCustodianDoc();

			// CR34 - No longer relevant. There is now one custodian trx for all
			// items belonging to one checklist.
			// custDoc.setStatus(trxValue.getToState());

			custDoc.setLastUpdateDate(DateUtil.getDate()); // bernard - added
															// for CMS-1394
			ICustodianDoc actualCustDoc = getSBCustodianBusManager().create(custDoc);
			trxValue.setCustodianDoc(actualCustDoc);
			trxValue.setReferenceID(String.valueOf(actualCustDoc.getCustodianDocID()));
			return trxValue;
		}
		catch (CustodianException cex) {
			throw new TrxOperationException(cex);
		}
		catch (java.rmi.RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}
}
