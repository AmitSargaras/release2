/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/AbstractWarehouseTrxOperation.java,v 1.10 2004/11/03 09:07:42 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

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
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.OBWarehouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.10 $
 * @since $Date: 2004/11/03 09:07:42 $ Tag: $Name: $
 */
public abstract class AbstractWarehouseTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
				IWarehouseTrxValue trxValue = getWarehouseTrxValue(value);
				IWarehouse[] stagingWarehouses = trxValue.getStagingWarehouse();
				WarehouseTrxDAO dao = new WarehouseTrxDAO();
				IWarehouseTrxValue tempTrx = dao.getWarehouseTrxValue(stagingWarehouses, true);
				if ((tempTrx.getStatus() != null) && (!tempTrx.getStatus().equals(ICMSConstant.STATE_ND))
						&& (!tempTrx.getStatus().equals(ICMSConstant.STATE_ACTIVE))
						&& (!tempTrx.getStatus().equals(ICMSConstant.STATE_DELETED))) {
					throw new Exception("Concurrent Exception: Warehouse Transaction exists!!!");
				}
			}
			return value;
		}
		catch (Exception e) {
			e.printStackTrace();
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
	 * This method set the primary key from the original to the copied checklist
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal - IWarehouse[]
	 * @param aCopy - IWarehouse[]
	 * @return IWarehouse[] - the copied object with required attributes from
	 *         the original checklist
	 * @throws TrxOperationException on errors
	 */
	protected IWarehouse[] mergeWarehouse(IWarehouse[] anOriginal, IWarehouse[] aCopy) throws TrxOperationException {
		HashMap actualMap = new HashMap();
		for (int i = 0; i < anOriginal.length; i++) {
			actualMap.put(new Long(anOriginal[i].getCommonRef()), anOriginal[i]);
		}

		// get actual groupid if available
		long groupID = getGroupID(anOriginal);
		groupID = (groupID == ICMSConstant.LONG_INVALID_VALUE) ? getGroupID(aCopy) : groupID;

		for (int i = 0; i < aCopy.length; i++) {
			IWarehouse copy = aCopy[i];
			IWarehouse original = (IWarehouse) actualMap.get(new Long(copy.getCommonRef()));
			if (original == null) {
				copy.setWarehouseID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else {
				copy.setWarehouseID(original.getWarehouseID());
				copy.setVersionTime(original.getVersionTime());
			}
			copy.setGroupID(groupID);
		}
		return aCopy;
	}

	private long getGroupID(IWarehouse[] whs) {
		if (whs != null) {
			for (int i = 0; i < whs.length; i++) {
				if (whs[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return whs[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Create the staging document item doc
	 * @param anIWarehouseTrxValue - IWarehouseTrxValue
	 * @return IWarehouseTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         errors
	 */
	protected IWarehouseTrxValue createStagingWarehouse(IWarehouseTrxValue anIWarehouseTrxValue)
			throws TrxOperationException {
		try {
			// Step-1 : generate new groupid
			long groupID = generateSeq(CommodityConstant.SEQUENCE_WAREHOUSE_GROUP_SEQ);

			// Step-2 : set groupID into all elements
			IWarehouse[] stagingWHs = anIWarehouseTrxValue.getStagingWarehouse();
			setGroupID(stagingWHs, groupID);

			// Step-3 : create the staging whs
			ICommodityMainInfoManager mgr = getStagingBusManager();
			stagingWHs = (IWarehouse[]) mgr.createInfo(stagingWHs);

			// Step-4 : update the staging value and staging reference id(group
			// id used as the staging ref id)
			anIWarehouseTrxValue.setStagingReferenceID(Long.toString(groupID));
			anIWarehouseTrxValue.setStagingWarehouse(stagingWHs);

			return anIWarehouseTrxValue;

		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Update the actual Warehouse from the staging Warehouse
	 * @param anIWarehouseTrxValue - IWarehouseTrxValue
	 * @return IWarehouseTrxValue - the Warehouse trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IWarehouseTrxValue updateActualWarehouse(IWarehouseTrxValue anIWarehouseTrxValue)
			throws TrxOperationException {
		try {
			IWarehouse[] staging = anIWarehouseTrxValue.getStagingWarehouse();
			IWarehouse[] actual = anIWarehouseTrxValue.getWarehouse();
			IWarehouse[] updActual = (IWarehouse[]) CommonUtil.deepClone(staging);
			updActual = mergeWarehouse(actual, updActual);
			IWarehouse[] actualWarehouse = (IWarehouse[]) getBusManager().updateInfo(updActual);
			anIWarehouseTrxValue.setWarehouse(actualWarehouse);
			return anIWarehouseTrxValue;
		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualWarehouse(): " + ex.toString());
		}
		catch (java.io.IOException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualWarehouse(): " + ex.toString());
		}
	}

	/**
	 * Update a Warehouse transaction
	 * @param anIWarehouseTrxValue - ITrxValue
	 * @return IWarehouseTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IWarehouseTrxValue updateWarehouseTransaction(IWarehouseTrxValue anIWarehouseTrxValue)
			throws TrxOperationException {
		try {
			anIWarehouseTrxValue = prepareTrxValue(anIWarehouseTrxValue, "update");
			ICMSTrxValue tempValue = super.updateTransaction(anIWarehouseTrxValue);
			OBWarehouseTrxValue newValue = new OBWarehouseTrxValue(tempValue);
			newValue.setWarehouse(anIWarehouseTrxValue.getWarehouse());
			newValue.setStagingWarehouse(anIWarehouseTrxValue.getStagingWarehouse());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IWarehouseTrxValue - the document item specific trx value object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is a ClassCastException
	 */
	protected IWarehouseTrxValue getWarehouseTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IWarehouseTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBWarehouseTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging Warehouse session bean
	 * @return SBCommodityMainInfoManager - the remote handler for the staging
	 *         Warehouse session bean
	 */
	protected ICommodityMainInfoManager getStagingBusManager() {
		return CommodityMainInfoManagerFactory.getStagingManager();
	}

	/**
	 * To get the remote handler for the Warehouse session bean
	 * @return SBCommodityMainInfoManager - the remote handler for the Warehouse
	 *         session bean
	 */
	protected ICommodityMainInfoManager getBusManager() {
		return CommodityMainInfoManagerFactory.getManager();
	}

	/**
	 * Prepares a trx object
	 */
	protected IWarehouseTrxValue prepareTrxValue(IWarehouseTrxValue anIWarehouseTrxValue, String action) {
		if (anIWarehouseTrxValue != null) {
			IWarehouse[] actual = anIWarehouseTrxValue.getWarehouse();
			IWarehouse[] staging = anIWarehouseTrxValue.getStagingWarehouse();

			String stagingReferenceID = ((staging != null) && (staging.length > 0)) ? String.valueOf(staging[0]
					.getGroupID()) : null;
			anIWarehouseTrxValue.setStagingReferenceID(stagingReferenceID);

			if (action.equalsIgnoreCase("create")) {
				String referenceID = ((actual != null) && (actual.length > 0)) ? String.valueOf(actual[0].getGroupID())
						: null;
				anIWarehouseTrxValue.setReferenceID(referenceID);
			}
			else {
				String referenceID = ((actual != null) && (actual.length > 0)) ? String.valueOf(actual[0].getGroupID())
						: stagingReferenceID;
				// String referenceID = stagingReferenceID;
				anIWarehouseTrxValue.setReferenceID(referenceID);
			}

			return anIWarehouseTrxValue;

		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IWarehouseTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IWarehouseTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
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

	private void setGroupID(IWarehouse[] array, long groupID) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				((OBWarehouse) array[i]).setGroupID(groupID);
			}
		}
	}

	protected IWarehouseTrxValue createTransaction(IWarehouseTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value, "create");
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBWarehouseTrxValue newValue = new OBWarehouseTrxValue(tempValue);
			newValue.setWarehouse(value.getWarehouse());
			newValue.setStagingWarehouse(value.getStagingWarehouse());
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

}