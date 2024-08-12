/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.transaction;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxProcess;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This controller facilitates transaction operations from the CMS package. It
 * may be subclassed to provide more detailed controlling facilities.
 * 
 * @author Alfred Lee
 * @author Tan Hui Ling
 * @author Chong Jun Yong
 */
public abstract class CMSTrxController extends AbstractTrxController implements ITrxOperationFactory {

	private static final long serialVersionUID = 779442826361191651L;

	/** logger available for sub classes */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Centralized command to process the chain of ITrxProcess, updating
	 * workflow and domain objects.
	 */
	private ITrxProcess trxProcess;

	/**
	 * @return centralized command to process chain of ITrxProcess
	 * @see CMSProcessTrxManager
	 */
	public ITrxProcess getProcessTrxManager() {
		return this.trxProcess;
	}

	/**
	 * To set the centralized command to process chain of ITrxProcess, normally
	 * is {@link CMSProcessTrxManager} instance.
	 * 
	 * @param trxProcess instance of ITrxProces
	 * @see CMSProcessTrxManager
	 */
	public void setProcessTrxManager(ITrxProcess trxProcess) {
		this.trxProcess = trxProcess;
	}

	public CMSTrxController() {
		super();
	}

	/**
	 * To operate the transaction value using the transaction operation getting
	 * from {@link #getOperation(ITrxValue, ITrxParameter)}
	 * 
	 * @param value the transaction value object to be operated
	 * @param param the transaction parameter contain some basic info to
	 *        retrieve the required transaction operation
	 * @return ITrxResult result getting from operating on the transaction value
	 *         supplied, which contain the transaction value object underwent
	 *         workflow process
	 * @throws TransactionException if any other errors occur
	 */
	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TransactionException {
		
		Validate.notNull(value, "'value' transaction value object must not be null.");
		
		Validate.notNull(param, "'param' transaction param object must not be null.");
	
		value.setInstanceName(getInstanceName());
		
		ITrxOperation op = getOperation(value, param);
		
		// TODO: remove this after all dependency injections are done.
		if (getProcessTrxManager() == null) {
			
			setProcessTrxManager(new CMSProcessTrxManager());
		}
		
		return getProcessTrxManager().operateTransaction(op, value);

	}
	
/*public ITrxResult operateScheduler(ITrxValue value, ITrxParameter param) throws TransactionException {
		
		Validate.notNull(value, "'value' transaction value object must not be null.");
		
		Validate.notNull(param, "'param' transaction param object must not be null.");
	
		value.setInstanceName(getInstanceName());
		
		ITrxOperation op = getOperation(value, param);
		
		// TODO: remove this after all dependency injections are done.
		if (getProcessTrxManager() == null) {
			
			setProcessTrxManager(new CMSProcessTrxManager());
		}
	
		return getProcessTrxManager().operateTransaction(op, value);

	}*/

	/**
	 * Based on the transaction value info and transaction parameter info, to
	 * retrieve the transaction operation to be used to operate the transaction
	 * value supplied.
	 * 
	 * @param value the transaction value object to be operated later
	 * @param param the transaction parameter contain some basic info to
	 *        retrieve the required transaction operation
	 * @throws TrxParameterException if there is any error encountered on
	 *         checking the transaction parameter
	 */
	public abstract ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException;

	/**
	 * Retrieve the instance name aka transaction type that this controller
	 * working on.
	 * 
	 * @return the instance name (aka transaction type)
	 */
	public abstract String getInstanceName();

}
