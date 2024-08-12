/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCreateTemplateOperation.java,v 1.6 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;

/**
 * This operation creates a pending template
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class MakerCreateTemplateOperation extends AbstractTemplateTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_TEMPLATE;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent template transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ITemplateTrxValue trxValue = getTemplateTrxValue(anITrxValue);
		ITemplate staging = trxValue.getStagingTemplate();
		try {
			if ((staging != null)
					&& (staging.getParentTemplateID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(
						String.valueOf(staging.getParentTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
				trxValue.setTrxReferenceID(parentTrx.getTransactionID());
			}
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITemplateTrxValue trxValue = super.getTemplateTrxValue(anITrxValue);
		trxValue.getStagingTemplate().setCountry("IN");
		trxValue = createStagingTemplate(trxValue);
		trxValue = createTemplateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the document item specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ITemplateTrxValue createTemplateTransaction(ITemplateTrxValue anITemplateTrxValue)
			throws TrxOperationException {
		try {
			anITemplateTrxValue = prepareTrxValue(anITemplateTrxValue);
			ICMSTrxValue trxValue = createTransaction(anITemplateTrxValue);
			OBTemplateTrxValue templateTrxValue = new OBTemplateTrxValue(trxValue);
			templateTrxValue.setStagingTemplate(anITemplateTrxValue.getStagingTemplate());
			templateTrxValue.setTemplate(anITemplateTrxValue.getTemplate());
			return templateTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}