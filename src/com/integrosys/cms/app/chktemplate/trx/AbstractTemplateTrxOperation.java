/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/AbstractTemplateTrxOperation.java,v 1.7 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.*;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

import java.rmi.RemoteException;


/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public abstract class AbstractTemplateTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Create the staging document item doc
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the trx object containing the created staging
	 *         document item
	 * @throws TrxOperationException if errors
	 */
	protected ITemplateTrxValue createStagingTemplate(ITemplateTrxValue anITemplateTrxValue)
			throws TrxOperationException {
		try {
			ITemplate template = getSBStagingCheckListTemplateBusManager().create(anITemplateTrxValue.getStagingTemplate());
			anITemplateTrxValue.setStagingTemplate(template);
			anITemplateTrxValue.setStagingReferenceID(String.valueOf(template.getTemplateID()));
			return anITemplateTrxValue;
		}
		catch (CheckListTemplateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a document item transaction
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the document item specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ITemplateTrxValue updateTemplateTransaction(ITemplateTrxValue anITemplateTrxValue)
			throws TrxOperationException {
		try {
			anITemplateTrxValue = prepareTrxValue(anITemplateTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anITemplateTrxValue);
			OBTemplateTrxValue newValue = new OBTemplateTrxValue(tempValue);

			DefaultLogger.debug(this, "ReferenceID2: " + newValue.getReferenceID());
			DefaultLogger.debug(this, "StagingReferenceID2: " + newValue.getStagingReferenceID());

			newValue.setTemplate(anITemplateTrxValue.getTemplate());
			newValue.setStagingTemplate(anITemplateTrxValue.getStagingTemplate());

			DefaultLogger.debug(this, "ReferenceID3: " + newValue.getReferenceID());
			DefaultLogger.debug(this, "StagingReferenceID3: " + newValue.getStagingReferenceID());

			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * This method set the primary key from the original to the copied template
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal - ITemplate
	 * @param aCopy - ITemplate
	 * @return ITemplate - the copied object with required attributes from the
	 *         original template
	 * @throws TrxOperationException on errors
	 */
	protected ITemplate mergeTemplate(ITemplate anOriginal, ITemplate aCopy) throws TrxOperationException {
		aCopy.setTemplateID(anOriginal.getTemplateID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		if (aCopy.getTemplateItemList() != null) {
            ITemplateItem[] itemList = aCopy.getTemplateItemList();
			if (anOriginal.getTemplateItemList() != null) {
				ITemplateItem[] orgItemList = anOriginal.getTemplateItemList();
				for (int ii = 0; ii < itemList.length; ii++) {
					for (int jj = 0; jj < orgItemList.length; jj++) {
						itemList[ii]
								.setTemplateItemID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
						if (itemList[ii].getItemCode() != null) {
							if (itemList[ii].getItemCode().equals(orgItemList[jj].getItemCode())) {
								itemList[ii].setTemplateItemID(orgItemList[jj].getTemplateItemID());
								break;
							}
						}
					}
				}
				aCopy.setTemplateItemList(itemList);
				return aCopy;
			} else {
                //new requirement to filter off expired item, therefore original item list can be null, no longer exception
                //throw new TrxOperationException("The original list of items is null and the copied list of items is not null !!!");
                for (int ii = 0; ii < itemList.length; ii++) {
                    itemList[ii].setTemplateItemID(ICMSConstant.LONG_INVALID_VALUE);
                }
                aCopy.setTemplateItemList(itemList);
            }
        }
		return aCopy;
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return ITemplateTrxValue - the document item specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ITemplateTrxValue getTemplateTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ITemplateTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBTemplateTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the staging
	 *         checklist session bean
	 */
	protected SBCheckListTemplateBusManager getSBStagingCheckListTemplateBusManager() {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the checklist
	 *         session bean
	 */
	protected SBCheckListTemplateBusManager getSBCheckListTemplateBusManager() {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected ITemplateTrxValue prepareTrxValue(ITemplateTrxValue anITemplateTrxValue) {
		if (anITemplateTrxValue != null) {
			ITemplate actual = anITemplateTrxValue.getTemplate();
			ITemplate staging = anITemplateTrxValue.getStagingTemplate();

			if (actual != null) {
				DefaultLogger.debug(this, "Reference ID: " + actual.getTemplateID());
				anITemplateTrxValue.setReferenceID(String.valueOf(actual.getTemplateID()));
			}
			else {
				anITemplateTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				DefaultLogger.debug(this, "StagingReferenceID: " + staging.getTemplateID());
				anITemplateTrxValue.setStagingReferenceID(String.valueOf(staging.getTemplateID()));
			}
			else {
				anITemplateTrxValue.setStagingReferenceID(null);
			}
			return anITemplateTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ITemplateTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ITemplateTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		DefaultLogger.debug(this, "ReferenceID4: " + value.getReferenceID());
		DefaultLogger.debug(this, "STagingReferenceID4: " + value.getStagingReferenceID());
		result.setTrxValue(value);
		return result;
	}
}