/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/RejectUpdateTATOperation.java,v 1.1 2003/09/03 03:04:56 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation allows the update of TAT
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 03:04:56 $ Tag: $Name: $
 */
public class RejectUpdateTATOperation extends UpdateTATOperation {
	/**
	 * Defaulc Constructor
	 */
	public RejectUpdateTATOperation() {
		super();
	}

	/**
	 * This method defines the process that should initialised values that would
	 * be required in the <code>performProcess</code> method
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		// this preProcess is to maintain fromState so that it will not be
		// overwritten by the
		// current status which is rejected. i.e. we don't want to have a
		// scenario where we have
		// from statte: rejected, to state: rejected too.
		try {
			String fromState = value.getFromState();
			DefaultLogger.debug(this, "From State: " + fromState);
			// then do super.preProcess
			value = super.preProcess(value);
			// now set back the from state to overwrite what was created in
			// preProcess
			ICMSTrxValue trxValue = getCMSTrxValue(value);
			trxValue.setFromState(fromState);
			return trxValue;
		}
		catch (TransactionException e) {
			throw new TrxOperationException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException("Caught Unknown Exception!", e);
		}
	}
}