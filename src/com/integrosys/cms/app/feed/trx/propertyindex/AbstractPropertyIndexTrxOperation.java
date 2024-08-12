/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/AbstractPropertyIndexTrxOperation.java,v 1.1 2003/08/20 10:59:58 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.propertyindex;

//java

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;
import com.integrosys.cms.app.feed.bus.propertyindex.SBPropertyIndexFeedBusManager;
import com.integrosys.cms.app.feed.bus.propertyindex.SBPropertyIndexFeedBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:58 $ Tag: $Name: $
 */
public abstract class AbstractPropertyIndexTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * @param anIPropertyIndexFeedGroupTrxValue -
	 *        IPropertyIndexFeedGroupTrxValue
	 * @return IPropertyIndexFeedGroupTrxValue - the trx object containing the
	 *         created staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IPropertyIndexFeedGroupTrxValue createStagingPropertyIndexFeedGroup(
			IPropertyIndexFeedGroupTrxValue anIPropertyIndexFeedGroupTrxValue) throws TrxOperationException {
		try {
			IPropertyIndexFeedGroup propertyIndexFeedGroup = getSBStagingPropertyIndexFeedBusManager()
					.createPropertyIndexFeedGroup(anIPropertyIndexFeedGroupTrxValue.getStagingPropertyIndexFeedGroup());
			anIPropertyIndexFeedGroupTrxValue.setStagingPropertyIndexFeedGroup(propertyIndexFeedGroup);
			anIPropertyIndexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(propertyIndexFeedGroup
					.getPropertyIndexFeedGroupID()));
			return anIPropertyIndexFeedGroupTrxValue;
		}
		catch (PropertyIndexFeedGroupException e) {
			throw new TrxOperationException(e.toString());
		}
		catch (RemoteException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a propertyIndexFeedGroup transaction
	 * @param anIPropertyIndexFeedGroupTrxValue - ITrxValue
	 * @return IPropertyIndexFeedGroupTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IPropertyIndexFeedGroupTrxValue updatePropertyIndexFeedGroupTransaction(
			IPropertyIndexFeedGroupTrxValue anIPropertyIndexFeedGroupTrxValue) throws TrxOperationException {
		try {
			anIPropertyIndexFeedGroupTrxValue = prepareTrxValue(anIPropertyIndexFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIPropertyIndexFeedGroupTrxValue's version time = "
					+ anIPropertyIndexFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIPropertyIndexFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBPropertyIndexFeedGroupTrxValue newValue = new OBPropertyIndexFeedGroupTrxValue(tempValue);
			newValue.setPropertyIndexFeedGroup(anIPropertyIndexFeedGroupTrxValue.getPropertyIndexFeedGroup());
			newValue.setStagingPropertyIndexFeedGroup(anIPropertyIndexFeedGroupTrxValue
					.getStagingPropertyIndexFeedGroup());

			DefaultLogger.debug(this, "newValue's version time = " + newValue.getVersionTime());

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
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IPropertyIndexFeedGroupTrxValue - the document item specific trx
	 *         value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroupTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IPropertyIndexFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBPropertyIndexFeedGroupTrxValue: "
					+ cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging propertyIndexFeedGroup session
	 * bean
	 * @return SBPropertyIndexFeedBusManager - the remote handler for the
	 *         staging propertyIndexFeedGroup session bean
	 */
	protected SBPropertyIndexFeedBusManager getSBStagingPropertyIndexFeedBusManager() {
		SBPropertyIndexFeedBusManager remote = (SBPropertyIndexFeedBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI_STAGING,
				SBPropertyIndexFeedBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the propertyIndexFeedGroup session bean
	 * @return SBPropertyIndexFeedBusManager - the remote handler for the
	 *         propertyIndexFeedGroup session bean
	 */
	protected SBPropertyIndexFeedBusManager getSBPropertyIndexFeedBusManager() {
		SBPropertyIndexFeedBusManager busMgr = (SBPropertyIndexFeedBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI, SBPropertyIndexFeedBusManagerHome.class
						.getName());

		if (busMgr == null) {
			DefaultLogger.error(this, "Unable to get property index feed manager");
		}

		return busMgr;
	}

	/**
	 * Prepares a trx object
	 */
	protected IPropertyIndexFeedGroupTrxValue prepareTrxValue(
			IPropertyIndexFeedGroupTrxValue anIPropertyIndexFeedGroupTrxValue) {
		if (anIPropertyIndexFeedGroupTrxValue != null) {
			IPropertyIndexFeedGroup actual = anIPropertyIndexFeedGroupTrxValue.getPropertyIndexFeedGroup();
			IPropertyIndexFeedGroup staging = anIPropertyIndexFeedGroupTrxValue.getStagingPropertyIndexFeedGroup();
			if (actual != null) {
				anIPropertyIndexFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getPropertyIndexFeedGroupID()));
			}
			else {
				anIPropertyIndexFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIPropertyIndexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging
						.getPropertyIndexFeedGroupID()));
			}
			else {
				anIPropertyIndexFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIPropertyIndexFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IPropertyIndexFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IPropertyIndexFeedGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * This method set the primary key from the original to the copied checklist
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal
	 * @param aCopy - ICheckList
	 * @return ICheckList - the copied object with required attributes from the
	 *         original checklist
	 * @throws TrxOperationException on errors
	 */
	protected IPropertyIndexFeedGroup mergePropertyIndexFeedGroup(IPropertyIndexFeedGroup anOriginal,
			IPropertyIndexFeedGroup aCopy) throws TrxOperationException {
		aCopy.setPropertyIndexFeedGroupID(anOriginal.getPropertyIndexFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

}