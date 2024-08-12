/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveCreateTemplateOperation.java,v 1.4 2003/07/23 07:32:14 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

import java.rmi.RemoteException;

/**
 * This operation allows a checker to approve a template create
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/23 07:32:14 $ Tag: $Name: $
 */
public class CheckerApproveCreateTemplateOperation extends AbstractTemplateTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_TEMPLATE;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITemplateTrxValue trxValue = getTemplateTrxValue(anITrxValue);
		trxValue = createActualTemplate(trxValue);
		trxValue = updateStagingTemplate(trxValue);
		trxValue = updateTemplateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ITemplateTrxValue createActualTemplate(ITemplateTrxValue anITemplateTrxValue) throws TrxOperationException {
		try {
			ITemplate template = anITemplateTrxValue.getStagingTemplate();
			template.setCountry("IN");
			DefaultLogger.debug(this, "Before Actual Create TemplateID: " + template.getTemplateID());
			ITemplate actualTemplate = getSBCheckListTemplateBusManager().create(template);
			DefaultLogger.debug(this, "After Actual Create TemplateID: " + template.getTemplateID());
			anITemplateTrxValue.setTemplate(actualTemplate);
			anITemplateTrxValue.setReferenceID(String.valueOf(actualTemplate.getTemplateID()));
			return anITemplateTrxValue;
		}
		catch (CheckListTemplateException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualTemplate(): " + ex.toString());
		}
	}

	private ITemplateTrxValue updateStagingTemplate(ITemplateTrxValue anITemplateTrxValue) throws TrxOperationException {
		try {
			ITemplate staging = anITemplateTrxValue.getStagingTemplate();
			ITemplate actual = anITemplateTrxValue.getTemplate();
			ITemplate updStaging = (ITemplate) CommonUtil.deepClone(actual);
			updStaging = mergeTemplate(staging, updStaging);
			staging = getSBStagingCheckListTemplateBusManager().update(updStaging);
			anITemplateTrxValue.setStagingTemplate(updStaging);
			return anITemplateTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (CheckListTemplateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateStagingTemplate(): " + ex.toString());
		}
	}
}