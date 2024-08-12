/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/AbstractUpdateCustodianDocTrxOperation.java,v 1.8 2005/02/22 09:31:25 wltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for the updating of custodian doc transaction
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/02/22 09:31:25 $ Tag: $Name: $
 */
public abstract class AbstractUpdateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {
	/**
	 * Get the operation name of the current operation To be implemented by the
	 * concrete class
	 * @return String - the operation name of the current operation
	 */
	public abstract String getOperationName();

	/**
	 * Update the actual custodian transaction
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the custodian specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICustodianTrxValue updateActualCustodianDoc(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
			ICustodianDoc stagingCustDoc = trxValue.getStagingCustodianDoc();
			ICustodianDoc actualCustDoc = trxValue.getCustodianDoc();
			stagingCustDoc.setCustodianDocID(actualCustDoc.getCustodianDocID());

			// CR34 - No longer relevant. There is now one custodian trx for all
			// items belonging to one checklist.
			// stagingCustDoc.setStatus(trxValue.getToState());

			stagingCustDoc.setVersionTime(actualCustDoc.getVersionTime());
			stagingCustDoc.setLastUpdateDate(DateUtil.getDate()); // bernard -
																	// added for
																	// CMS-1394
			ICustodianDoc updatedCustDoc = getSBCustodianBusManager().update(stagingCustDoc);
			trxValue.setCustodianDoc(updatedCustDoc);
			trxValue.setReferenceID(String.valueOf(updatedCustDoc.getCustodianDocID()));
			return trxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CustodianException ex) {
			throw new TrxOperationException(ex);
		}
		catch (java.rmi.RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a custodian transaction
	 * @param anITrxValue - ITrxValue
	 * @return OBCustodianTrxValue - the custodian specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICustodianTrxValue updateCustodianTransaction(ITrxValue anITrxValue) throws TrxOperationException {
		ICMSTrxValue trxValue = getCMSTrxValue(anITrxValue);
		try {
			trxValue = getTrxManager().updateTransaction(trxValue);
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
}
