/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/AbstractDDNTrxOperation.java,v 1.5 2005/10/21 07:19:37 lyng Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.SBDDNBusManager;
import com.integrosys.cms.app.ddn.bus.SBDDNBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of ddn trx
 * operations
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/10/21 07:19:37 $ Tag: $Name: $
 */
public abstract class AbstractDDNTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	 * This method set the primary key from the original to the copied ddn
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done. @ param anOrginal of IDDN type
	 * @param aCopy of IDDN type
	 * @return IDDN - the copied object with required attributes from the
	 *         original ddn
	 * @throws TrxOperationException on errors
	 */
	protected IDDN mergeDDN(IDDN anOriginal, IDDN aCopy) throws TrxOperationException {
		aCopy.setDDNID(anOriginal.getDDNID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the trx object containing the created staging
	 *         document item
	 * @throws TrxOperationException if errors
	 */
	protected IDDNTrxValue createStagingDDN(IDDNTrxValue anIDDNTrxValue) throws TrxOperationException {
		try {
			IDDN stageDDN = anIDDNTrxValue.getStagingDDN();
			stageDDN.setRemarks(anIDDNTrxValue.getRemarks());
			IDDN ddn = getSBStagingDDNBusManager().createDDN(anIDDNTrxValue.getStagingDDN());
			anIDDNTrxValue.setStagingDDN(ddn);
			anIDDNTrxValue.setStagingReferenceID(String.valueOf(ddn.getDDNID()));
			return anIDDNTrxValue;
		}
		catch (DDNException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a ddn transaction @ param anITrxValue of ITrxValue type
	 * @return IDDNTrxValue - the document item specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IDDNTrxValue updateDDNTransaction(IDDNTrxValue anIDDNTrxValue) throws TrxOperationException {
		try {
			anIDDNTrxValue = prepareTrxValue(anIDDNTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anIDDNTrxValue);
			OBDDNTrxValue newValue = new OBDDNTrxValue(tempValue);
			newValue.setDDN(anIDDNTrxValue.getDDN());
			newValue.setStagingDDN(anIDDNTrxValue.getStagingDDN());
			return newValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return IDDNTrxValue - the document item specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IDDNTrxValue getDDNTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IDDNTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBDDNTrxValue: " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging ddn session bean
	 * @return SBDDNBusManager - the remote handler for the staging ddn session
	 *         bean
	 */
	protected SBDDNBusManager getSBStagingDDNBusManager() {
		SBDDNBusManager remote = (SBDDNBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_STAGING_DDN_BUS_JNDI,
				SBDDNBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the DDN session bean
	 * @return SBDDNBusManager - the remote handler for the DDN session bean
	 */
	protected SBDDNBusManager getSBDDNBusManager() {
		SBDDNBusManager remote = (SBDDNBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_DDN_BUS_JNDI,
				SBDDNBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected IDDNTrxValue prepareTrxValue(IDDNTrxValue anIDDNTrxValue) {
		if (anIDDNTrxValue != null) {
			IDDN actual = anIDDNTrxValue.getDDN();
			IDDN staging = anIDDNTrxValue.getStagingDDN();
			if ((actual != null)
					&& (actual.getDDNID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anIDDNTrxValue.setReferenceID(String.valueOf(actual.getDDNID()));
			}
			else {
				anIDDNTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getDDNID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anIDDNTrxValue.setStagingReferenceID(String.valueOf(staging.getDDNID()));
			}
			else {
				anIDDNTrxValue.setStagingReferenceID(null);
			}
			return anIDDNTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of IDDNTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IDDNTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}