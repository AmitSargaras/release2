/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ReadTemplateOperation.java,v 1.1 2003/07/03 01:37:27 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManager;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManagerHome;

/**
 * This operation is responsible for the creation of a checklist doc transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/03 01:37:27 $ Tag: $Name: $
 */
public class ReadTemplateOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		//return ICMSConstant.ACTION_READ_DOC_ITEM;
        return ICMSConstant.ACTION_READ_TEMPLATE;
    }

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue - the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

			OBTemplateTrxValue newValue = new OBTemplateTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ITemplate stagingTemplate = getSBStagingCheckListTemplateBusManager().getTemplateByID(
						(new Long(stagingRef)).longValue());
				newValue.setStagingTemplate(stagingTemplate);
			}

			if (actualRef != null) {
				ITemplate actualTemplate = getSBCheckListTemplateBusManager()
						.getTemplateByID((new Long(actualRef)).longValue());
				newValue.setTemplate(actualTemplate);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * customer data
	 * @return SBCheckListBusManager - the home interface for the staging
	 *         checklist session bean
	 */
	private SBCheckListTemplateBusManager getSBStagingCheckListTemplateBusManager() throws TransactionException {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingCheckListBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBCheckListBusManager - the home interface for the checklist
	 *         session bean
	 */
	private SBCheckListTemplateBusManager getSBCheckListTemplateBusManager() throws TransactionException {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBCheckListBusManager is null!");
	}
}