package com.integrosys.cms.app.creditriskparam.trx.policycap;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.OBPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManager;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public abstract class AbstractPolicyCapTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * This method set the primary key from the original to the copied policy
	 * cap objects. It is required for the case of updating staging from actual
	 * and vice versa as there is a need to perform a deep clone of the object
	 * and set the required attribute in the object to the original one so that
	 * a proper update can be done.
	 * @param actualList - IPolicyCap
	 * @param stagingList - IPolicyCap
	 * @return IPolicyCap - the copied object with required attributes from the
	 *         original policy cap
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IPolicyCap[] mergePolicyCap(IPolicyCap[] actualList, IPolicyCap[] stagingList)
			throws TrxOperationException {
		DefaultLogger.debug(this, "============ merge == START");
		HashMap actualMap = new HashMap();
		for (int i = 0; i < actualList.length; i++) {
			actualMap.put(new Long(actualList[i].getCommonRef()), actualList[i]);
		}

		// get actual groupid if available
		long groupID = getGroupID(actualList);
		DefaultLogger.debug(this, "actual groupID=" + groupID);

		for (int i = 0; i < stagingList.length; i++) {
			IPolicyCap staging = stagingList[i];
			IPolicyCap actual = (IPolicyCap) actualMap.get(new Long(staging.getCommonRef()));

			DefaultLogger.debug(this, "in abstract actual.getPolicyCapID()=" + actual.getPolicyCapID());
			DefaultLogger.debug(this, "in abstract actual.getVersionTime()=" + actual.getVersionTime());

			DefaultLogger.debug(this, "in abstract staging.getPolicyCapID()=" + staging.getPolicyCapID());
			DefaultLogger.debug(this, "in abstract staging.getVersionTime()=" + staging.getVersionTime());

			if (actual == null) {
				staging.setPolicyCapID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else {
				staging.setPolicyCapID(actual.getPolicyCapID());
				;
				staging.setVersionTime(actual.getVersionTime());
			}
			((OBPolicyCap) staging).setGroupID(groupID);
		}
		DefaultLogger.debug(this, "============ merge == END");
		return stagingList;
	}

	/**
	 * Helper method to get group id given a list of policy cap.
	 * 
	 * @param list - IPolicyCap[]
	 * @return group id
	 */
	private long getGroupID(IPolicyCap[] list) {
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return list[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	// ==================================
	// Create Methods
	// ==================================
	/**
	 * Create the staging policy cap
	 * @param anIPolicyCapTrxValue - IPolicyCapTrxValue
	 * @return IPolicyCapTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IPolicyCapTrxValue createStagingPolicyCap(IPolicyCapTrxValue anIPolicyCapTrxValue)
			throws TrxOperationException {
		try {
			// IPolicyCap[] actual = anIPolicyCapTrxValue.getPolicyCap();

			IPolicyCap[] value = anIPolicyCapTrxValue.getStagingPolicyCap();

			long generatedGroupID = generateSeq(ICMSConstant.SEQUENCE_POLICY_CAP_GROUP_SEQ); // Step
																								// -
																								// 1
																								// :
																								// Create
																								// groupID
																								// and
																								// fillIn
			setGroupID(value, generatedGroupID); // Step-2 : set groupID into
													// all elements
			anIPolicyCapTrxValue.setStagingPolicyCap(value); // Step-3 : prepare
																// staging value
																// and
																// Transaction
																// value value
			IPolicyCap[] policyCapList = getSBStagingPolicyCapBusManager().create(
					anIPolicyCapTrxValue.getStagingPolicyCap()); // Step-4
																	// create
																	// the
																	// staging
																	// document

			DefaultLogger.debug(this, "policyCapList=" + policyCapList);

			IPolicyCap[] valueCreated = null;
			try {
				valueCreated = (IPolicyCap[]) policyCapList;
			}
			catch (ClassCastException e) {
				e.printStackTrace();
				throw e;
			}
			DefaultLogger.debug(this, "$$$ debug:Staging_3.7 - staging info typecasted.");

			// Set the groupID(of any one of the element. say 1st element) as
			// staging reference
			anIPolicyCapTrxValue.setStagingPolicyCap(valueCreated);

			// anIPolicyCapTrxValue.setReferenceID(String.valueOf(actual[0].
			// getGroupID()));

			DefaultLogger.debug(this, "valueCreated[0].getGroupID()=" + valueCreated[0].getGroupID());
			anIPolicyCapTrxValue.setStagingReferenceID(String.valueOf(valueCreated[0].getGroupID()));
			DefaultLogger.debug(this, "$$$ debug:Staging_4");
			return anIPolicyCapTrxValue;
		}
		catch (PolicyCapException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex);
		}

	}

	/**
	 * Create a policy cap transaction
	 * @param anIPolicyCapTrxValue - IPolicyCapTrxValue
	 * @return IPolicyCapTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IPolicyCapTrxValue createPolicyCapTransaction(IPolicyCapTrxValue anIPolicyCapTrxValue)
			throws TrxOperationException {
		try {
			anIPolicyCapTrxValue = prepareTrxValue(anIPolicyCapTrxValue);
			ICMSTrxValue trxValue = createTransaction(anIPolicyCapTrxValue);
			OBPolicyCapTrxValue policyCapTrxValue = new OBPolicyCapTrxValue(trxValue);
			policyCapTrxValue.setStagingPolicyCap(anIPolicyCapTrxValue.getStagingPolicyCap());
			policyCapTrxValue.setPolicyCap(anIPolicyCapTrxValue.getPolicyCap());
			return policyCapTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	// ==================================
	// Update Methods
	// ==================================
	/**
	 * Update the actual Policy Cap from the staging Policy Cap
	 * @param anIPolicyCapTrxValue - IPolicyCapTrxValue
	 * @return IPolicyCapTrxValue - the PolicyCap trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IPolicyCapTrxValue updateActualPolicyCap(IPolicyCapTrxValue anIPolicyCapTrxValue)
			throws TrxOperationException {
		try {
			IPolicyCap[] staging = anIPolicyCapTrxValue.getStagingPolicyCap();
			IPolicyCap[] actual = anIPolicyCapTrxValue.getPolicyCap();
			IPolicyCap[] updActual = (IPolicyCap[]) CommonUtil.deepClone(staging);
			DefaultLogger.debug(this, "$$$Debug:::UPD1 staging=" + staging);
			DefaultLogger.debug(this, "$$$Debug:::UPD2 actual =" + actual);
			DefaultLogger.debug(this, "$$$Debug:::UPD3 updActual=" + updActual);
			updActual = mergePolicyCap(actual, updActual);
			DefaultLogger.debug(this, "$$$Debug:::UPD4 updActual=" + updActual);
			IPolicyCap[] actualPolicyCap = getSBPolicyCapBusManager().update(updActual);
			DefaultLogger.debug(this, "$$$Debug:::UPD5 updActual=" + actualPolicyCap);
			anIPolicyCapTrxValue.setPolicyCap(actualPolicyCap);
			return anIPolicyCapTrxValue;

		}
		catch (ConcurrentUpdateException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (PolicyCapException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualPolicyCap(): " + ex.toString());
		}
		catch (IOException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualPolicyCap(): " + ex.toString());
		}
	}

	/**
	 * Update a policy cap transaction
	 * @param anIPolicyCapTrxValue - ITrxValue
	 * @return IPolicyCapTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IPolicyCapTrxValue updatePolicyCapTransaction(IPolicyCapTrxValue anIPolicyCapTrxValue)
			throws TrxOperationException {
		try {
			anIPolicyCapTrxValue = prepareTrxValue(anIPolicyCapTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anIPolicyCapTrxValue);
			OBPolicyCapTrxValue newValue = new OBPolicyCapTrxValue(tempValue);
			newValue.setPolicyCap(anIPolicyCapTrxValue.getPolicyCap());
			newValue.setStagingPolicyCap(anIPolicyCapTrxValue.getStagingPolicyCap());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	// ========================================================
	// Start of helper methods - to be used by child classes
	// ========================================================
	/**
	 * Prepares a trx object
	 */
	protected IPolicyCapTrxValue prepareTrxValue(IPolicyCapTrxValue anIPolicyCapTrxValue) {
		if (anIPolicyCapTrxValue != null) {
			IPolicyCap[] actual = anIPolicyCapTrxValue.getPolicyCap();
			IPolicyCap[] staging = anIPolicyCapTrxValue.getStagingPolicyCap();

			// set reference id
			if ((actual != null) && (actual.length >= 1)) {
				DefaultLogger.debug(this, "actual[0].getGroupID()=" + actual[0].getGroupID());
				anIPolicyCapTrxValue.setReferenceID(String.valueOf(actual[0].getGroupID()));
			}
			else {
				anIPolicyCapTrxValue.setReferenceID(null);
			}

			// set staging reference id
			if ((staging != null) && (staging.length >= 1)) {
				anIPolicyCapTrxValue.setStagingReferenceID(String.valueOf(staging[0].getGroupID()));
			}
			else {
				anIPolicyCapTrxValue.setStagingReferenceID(null);
			}

			return anIPolicyCapTrxValue;
		}

		return null;
	}

	/**
	 * Helper method to cast a generic trx value object to a IPolicyCap specific
	 * trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IPolicyCapTrxValue - the policy cap specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IPolicyCapTrxValue getPolicyCapTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IPolicyCapTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBPolicyCapTrxValue: " + cex.toString());
		}
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IPolicyCapTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IPolicyCapTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// ==================================
	// Start of helper methods
	// ==================================
	protected long generateSeq(String sequenceName) throws PolicyCapException {

		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PolicyCapException(this.getClass().getName() + " : Exception in generating Sequence '"
					+ sequenceName + "' \n The exception is : " + e);
		}

	}

	private void setGroupID(IPolicyCap[] list, long groupID) {
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				list[i].setGroupID(groupID);

			}
		}
	}

	/**
	 * To get the remote handler for the staging policy cap session bean
	 * @return SBPolicyCapBusManager - the remote handler for the staging policy
	 *         cap session bean
	 */
	protected SBPolicyCapBusManager getSBStagingPolicyCapBusManager() {
		SBPolicyCapBusManager remote = (SBPolicyCapBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_POLICY_CAP_BUS_JNDI, SBPolicyCapBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the policy cap session bean
	 * @return SBPolicyCapBusManager - the remote handler for the staging policy
	 *         cap session bean
	 */
	protected SBPolicyCapBusManager getSBPolicyCapBusManager() {
		SBPolicyCapBusManager remote = (SBPolicyCapBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_POLICY_CAP_BUS_JNDI, SBPolicyCapBusManagerHome.class.getName());
		return remote;
	}

}
