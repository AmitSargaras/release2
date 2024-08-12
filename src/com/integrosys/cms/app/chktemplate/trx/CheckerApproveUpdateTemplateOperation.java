/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveUpdateTemplateOperation.java,v 1.7 2003/08/07 02:36:55 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This operation allows a checker to approve the template updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/07 02:36:55 $ Tag: $Name: $
 */
public class CheckerApproveUpdateTemplateOperation extends AbstractTemplateTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_TEMPLATE;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITemplateTrxValue trxValue = getTemplateTrxValue(anITrxValue);
		trxValue = updateActualTemplate(trxValue);
		trxValue = super.updateTemplateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual document item from the staging document item
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ITemplateTrxValue updateActualTemplate(ITemplateTrxValue anITemplateTrxValue) throws TrxOperationException {
		try {
			ITemplate staging = anITemplateTrxValue.getStagingTemplate();
			ITemplate actual = anITemplateTrxValue.getTemplate();
			ITemplate updActual = (ITemplate) CommonUtil.deepClone(staging);
			updActual = mergeTemplate(actual, updActual);
			ITemplate actualTemplate = getSBCheckListTemplateBusManager().update(updActual);
			anITemplateTrxValue.setTemplate(updActual);
			return anITemplateTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CheckListTemplateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualTemplate(): " + ex.toString());
		}
	}
}