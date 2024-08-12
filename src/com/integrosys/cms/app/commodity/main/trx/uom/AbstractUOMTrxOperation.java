/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/AbstractUOMTrxOperation.java,v 1.5 2004/08/02 08:35:02 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.OBUnitofMeasure;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/02 08:35:02 $ Tag: $Name: $
 */
public abstract class AbstractUOMTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Pre process. Prepares the transaction object for persistance
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		try {
			if (value.getTransactionID() == null) {
				IUnitofMeasureTrxValue trxValue = getUnitofMeasureTrxValue(value);
				IUnitofMeasure[] stagingUOMs = trxValue.getStagingUnitofMeasure();
				UnitofMeasureTrxDAO dao = new UnitofMeasureTrxDAO();
				IUnitofMeasureTrxValue tempTrx = dao.getUnitofMeasureTrxValue(stagingUOMs, true);
				if ((tempTrx.getStatus() != null) && (!tempTrx.getStatus().equals(ICMSConstant.STATE_ND))
						&& (!tempTrx.getStatus().equals(ICMSConstant.STATE_ACTIVE))) {
					throw new Exception("Concurrent Exception: Unit of Measure Transaction exists!!!");
				}
			}
			return value;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws com.integrosys.base.businfra.transaction.TransactionException on
	 *         error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Helper method to compare the staging and actual list to identify those
	 * that are actual and those that needs to tbe created. This is accompilshed
	 * by comparing the common reference id of the staging and actual objects.
	 * 
	 * @param actualList - IUnitofMeasure[]
	 * @param stagingList - IUnitofMeasure[]
	 * @return IUnitofMeasure - the copied object with required attributes from
	 *         the original UnitofMeasure
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	private IUnitofMeasure[] merge(IUnitofMeasure[] actualList, IUnitofMeasure[] stagingList)
			throws TrxOperationException {

		HashMap actualMap = new HashMap();
		for (int i = 0; i < actualList.length; i++) {
			actualMap.put(new Long(actualList[i].getCommonReferenceID()), actualList[i]);
		}

		// get actual groupid if available
		long groupID = getGroupID(actualList);
		groupID = (groupID == ICMSConstant.LONG_INVALID_VALUE) ? getGroupID(stagingList) : groupID;

		for (int i = 0; i < stagingList.length; i++) {
			IUnitofMeasure staging = stagingList[i];
			IUnitofMeasure actual = (IUnitofMeasure) actualMap.get(new Long(staging.getCommonReferenceID()));
			if (actual == null) {
				staging.setUnitofMeasureID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else {
				staging.setUnitofMeasureID(actual.getUnitofMeasureID());
				staging.setVersionTime(actual.getVersionTime());
			}
			staging.setGroupID(groupID);
		}
		return stagingList;
	}

	/**
	 * Helper method to get group id given a list of commodity prices.
	 * 
	 * @param uoms - IUnitofMeasure[]
	 * @return group id
	 */
	private long getGroupID(IUnitofMeasure[] uoms) {
		if (uoms != null) {
			for (int i = 0; i < uoms.length; i++) {
				if (uoms[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return uoms[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Create the staging UnitofMeasure item
	 * 
	 * @param aTrxValue - IUnitofMeasureTrxValue
	 * @return IUnitofMeasureTrxValue - the trx object containing the created
	 *         staging UnitofMeasure item
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         errors
	 */
	protected IUnitofMeasureTrxValue createStaging(IUnitofMeasureTrxValue aTrxValue) throws TrxOperationException {
		try {
			// Step-1 : generate new groupid
			long groupID = generateSeq(CommodityConstant.SEQUENCE_UOM_GROUP_SEQ);

			// Step-2 : set groupID into all elements
			IUnitofMeasure[] stagingUOMs = aTrxValue.getStagingUnitofMeasure();
			setGroupID(stagingUOMs, groupID);

			// Step-3 : create the staging uoms
			ICommodityMainInfoManager mgr = getStagingBusManager();
			stagingUOMs = (IUnitofMeasure[]) mgr.createInfo(stagingUOMs);

			// Step-4 : update the staging value and staging reference id(group
			// id used as the staging ref id)
			aTrxValue.setStagingReferenceID(Long.toString(groupID));
			aTrxValue.setStagingUnitofMeasure(stagingUOMs);

			return aTrxValue;
		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Create actual unit of measure record.
	 * 
	 * @param aTrxValue is of type IUnitofMeasureTrxValue
	 * @return unit of measure transaction value
	 * @throws TrxOperationException on errors creating the commodity price
	 */
	protected IUnitofMeasureTrxValue createActual(IUnitofMeasureTrxValue aTrxValue) throws TrxOperationException {
		try {
			// get from staging to create actual
			IUnitofMeasure[] uoms = aTrxValue.getStagingUnitofMeasure();
			ICommodityMainInfoManager mgr = getBusManager();
			uoms = (IUnitofMeasure[]) mgr.createInfo(uoms);
			aTrxValue.setUnitofMeasure(uoms); // set into actual
			return aTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update the actual UnitofMeasure from the staging UnitofMeasure
	 * 
	 * @param aTrxValue - IUnitofMeasureTrxValue
	 * @return IUnitofMeasureTrxValue - the UnitofMeasure trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IUnitofMeasureTrxValue updateActual(IUnitofMeasureTrxValue aTrxValue) throws TrxOperationException {
		try {
			IUnitofMeasure[] stagingUOMs = aTrxValue.getStagingUnitofMeasure();
			IUnitofMeasure[] actualUOMs = aTrxValue.getUnitofMeasure();
			IUnitofMeasure[] tobeUpdatedUOMs = (IUnitofMeasure[]) CommonUtil.deepClone(stagingUOMs);
			tobeUpdatedUOMs = merge(actualUOMs, tobeUpdatedUOMs);
			IUnitofMeasure[] updatedUOMs = (IUnitofMeasure[]) getBusManager().updateInfo(tobeUpdatedUOMs);
			aTrxValue.setUnitofMeasure(updatedUOMs);
			return aTrxValue;
		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActual(): " + ex.toString());
		}
		catch (java.io.IOException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActual(): " + ex.toString());
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IUnitofMeasureTrxValue
	 * @return IUnitofMeasureTrxValue
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors creating transaction
	 */
	protected IUnitofMeasureTrxValue createTransaction(IUnitofMeasureTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBUnitofMeasureTrxValue newValue = new OBUnitofMeasureTrxValue(tempValue);
			newValue.setUnitofMeasure(value.getUnitofMeasure());
			newValue.setStagingUnitofMeasure(value.getStagingUnitofMeasure());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update a UnitofMeasure transaction
	 * 
	 * @param aTrxValue - ITrxValue
	 * @return IUnitofMeasureTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IUnitofMeasureTrxValue updateTransaction(IUnitofMeasureTrxValue aTrxValue) throws TrxOperationException {
		try {
			aTrxValue = prepareTrxValue(aTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(aTrxValue);
			OBUnitofMeasureTrxValue newValue = new OBUnitofMeasureTrxValue(tempValue);
			newValue.setUnitofMeasure(aTrxValue.getUnitofMeasure());
			newValue.setStagingUnitofMeasure(aTrxValue.getStagingUnitofMeasure());
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
	 * Helper method to cast a generic trx value object to a UnitofMeasure
	 * specific trx value object
	 * 
	 * @param anITrxValue - ITrxValue
	 * @return IUnitofMeasureTrxValue - the document item specific trx value
	 *         object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is a ClassCastException
	 */
	protected IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IUnitofMeasureTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBUnitofMeasureTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging UnitofMeasure session bean
	 * 
	 * @return SBCommodityMainInfoManager - the remote handler for the staging
	 *         session bean
	 */
	protected ICommodityMainInfoManager getStagingBusManager() {
		return CommodityMainInfoManagerFactory.getStagingManager();
	}

	/**
	 * To get the remote handler for the UnitofMeasure session bean
	 * 
	 * @return SBCommodityMainInfoManager - the remote handler for the
	 *         UnitofMeasure session bean
	 */
	protected ICommodityMainInfoManager getBusManager() {
		return CommodityMainInfoManagerFactory.getManager();
	}

	/**
	 * Prepares a trx object. Updates the trx object with the correct actual and
	 * staging reference id which in this case will be the group id which
	 * identifies a set of uoms.
	 * 
	 * @param aTrxValue is of type IUnitofMeasureTrxValue
	 * @return IUnitofMeasureTrxValue
	 */
	protected IUnitofMeasureTrxValue prepareTrxValue(IUnitofMeasureTrxValue aTrxValue) {
		if (aTrxValue != null) {
			IUnitofMeasure[] actualValues = aTrxValue.getUnitofMeasure();
			IUnitofMeasure[] stagingValues = aTrxValue.getStagingUnitofMeasure();

			// updates the transaction value with the correct reference id and
			// staging reference id
			// which in this case will be the group id which identifies a set of
			// uoms
			String referenceID = ((actualValues != null) && (actualValues.length > 0)) ? String.valueOf(actualValues[0]
					.getGroupID()) : null;
			aTrxValue.setReferenceID(referenceID);

			String stagingReferenceID = ((stagingValues != null) && (stagingValues.length > 0)) ? String
					.valueOf(stagingValues[0].getGroupID()) : null;
			aTrxValue.setStagingReferenceID(stagingReferenceID);
			return aTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param aTrxValue is of type IUnitofMeasureTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IUnitofMeasureTrxValue aTrxValue) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(aTrxValue);
		return result;
	}

	protected long generateSeq(String sequenceName) throws CommodityException {
		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException(this.getClass().getName() + " : Exception in generating Sequence '"
					+ sequenceName + "' \n The exception is : " + e);
		}
	}

	protected void setGroupID(IUnitofMeasure[] uoms, long groupID) {
		if (uoms != null) {
			for (int i = 0; i < uoms.length; i++) {
				((OBUnitofMeasure) uoms[i]).setGroupID(groupID);
			}
		}
	}
}