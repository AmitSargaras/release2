/**
 * 
 */
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
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManager;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Absract Operation Class of PolicyCapGroup
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public abstract class AbstractPolicyCapGroupTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * @param actualPcg
	 * @param stagingPcg
	 * @return
	 * @throws TrxOperationException
	 */
	protected IPolicyCapGroup mergePolicyCapGroup(IPolicyCapGroup actualPcg, IPolicyCapGroup stagingPcg)
			throws TrxOperationException {

		// get actual groupid if available
		long policyCapGroupId = actualPcg.getPolicyCapGroupID();
		DefaultLogger.debug(this, "actual policyCapGroupId=" + policyCapGroupId);

		// Merving Policy Cap Group
		if (actualPcg == null) {
			stagingPcg.setPolicyCapGroupID(ICMSConstant.LONG_INVALID_VALUE);
		}
		else {
			stagingPcg.setPolicyCapGroupID(actualPcg.getPolicyCapGroupID());
			;
			stagingPcg.setVersionTime(actualPcg.getVersionTime());
		}

		// Merging Policy Cap Array
		IPolicyCap[] actualList = actualPcg.getPolicyCapArray();
		IPolicyCap[] stagingList = stagingPcg.getPolicyCapArray();

		HashMap actualMap = new HashMap();
		for (int i = 0; i < actualList.length; i++) {
			actualMap.put(new Long(actualList[i].getCommonRef()), actualList[i]);
		}

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
			// ((OBPolicyCap)staging).setGroupID(groupID);
		}
		DefaultLogger.debug(this, "============ merge == END");

		return stagingPcg;
	}

	/**
	 * Create a staging policyCapGroup
	 * @param policyCapGroupTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	protected IPolicyCapGroupTrxValue createStagingPolicyCapGroup(IPolicyCapGroupTrxValue policyCapGroupTrxValue)
			throws TrxOperationException {

		try {
			IPolicyCapGroup value = policyCapGroupTrxValue.getStagingPolicyCapGroup();

			IPolicyCapGroup policyCapGroup = getSBStagingPolicyCapBusManager().createPolicyCapGroup(value);
			DefaultLogger.debug(this, "policyCapGroup=" + policyCapGroup);

			// Save staging policy Cap Group
			policyCapGroupTrxValue.setStagingPolicyCapGroup(policyCapGroup);

			DefaultLogger.debug(this, "policyCapGroup.getPolicyCapGroupID()=" + policyCapGroup.getPolicyCapGroupID());
			policyCapGroupTrxValue.setStagingReferenceID(String.valueOf(policyCapGroup.getPolicyCapGroupID()));
			return policyCapGroupTrxValue;
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
	 * Create a policy cap group transaction
	 * @param anIPolicyCapGroupTrxValue - IPolicyCapGroupTrxValue
	 * @return IPolicyCapGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IPolicyCapGroupTrxValue createPolicyCapGroupTransaction(IPolicyCapGroupTrxValue policyCapGroupTrxValue)
			throws TrxOperationException {
		try {
			policyCapGroupTrxValue = prepareTrxValue(policyCapGroupTrxValue);
			ICMSTrxValue trxValue = createTransaction(policyCapGroupTrxValue);
			OBPolicyCapGroupTrxValue obPolicyCapGroupTrxValue = new OBPolicyCapGroupTrxValue(trxValue);
			obPolicyCapGroupTrxValue.setStagingPolicyCapGroup(policyCapGroupTrxValue.getStagingPolicyCapGroup());
			obPolicyCapGroupTrxValue.setPolicyCapGroup(policyCapGroupTrxValue.getPolicyCapGroup());
			return obPolicyCapGroupTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Update a policy cap group transaction
	 * @param anIPolicyCapGroupTrxValue - ITrxValue
	 * @return IPolicyCapGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IPolicyCapGroupTrxValue updatePolicyCapGroupTransaction(IPolicyCapGroupTrxValue anIPolicyCapGroupTrxValue)
			throws TrxOperationException {
		try {
			anIPolicyCapGroupTrxValue = prepareTrxValue(anIPolicyCapGroupTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anIPolicyCapGroupTrxValue);
			OBPolicyCapGroupTrxValue newValue = new OBPolicyCapGroupTrxValue(tempValue);
			newValue.setPolicyCapGroup(anIPolicyCapGroupTrxValue.getPolicyCapGroup());
			newValue.setStagingPolicyCapGroup(anIPolicyCapGroupTrxValue.getStagingPolicyCapGroup());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	// ==================================
	// Update Methods
	// ==================================
	/**
	 * Update the actual Policy Cap Group from the staging Policy Cap Group
	 * @param anIPolicyCapGroupTrxValue - IPolicyCapGroupTrxValue
	 * @return IPolicyCapGroupTrxValue - the PolicyCap trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IPolicyCapGroupTrxValue updateActualPolicyCapGroup(IPolicyCapGroupTrxValue anIPolicyCapGroupTrxValue)
			throws TrxOperationException {
		try {
			IPolicyCapGroup stagingPcg = anIPolicyCapGroupTrxValue.getStagingPolicyCapGroup();
			IPolicyCapGroup actualPcg = anIPolicyCapGroupTrxValue.getPolicyCapGroup();
			IPolicyCapGroup updActualPcg = (IPolicyCapGroup) CommonUtil.deepClone(stagingPcg);
			DefaultLogger.debug(this, "$$$Debug:::UPD1 staging=" + stagingPcg);
			DefaultLogger.debug(this, "$$$Debug:::UPD2 actual =" + actualPcg);
			DefaultLogger.debug(this, "$$$Debug:::UPD3 updActual=" + updActualPcg);
			updActualPcg = mergePolicyCapGroup(actualPcg, updActualPcg);
			DefaultLogger.debug(this, "$$$Debug:::UPD4 updActual=" + updActualPcg);
			IPolicyCapGroup actualPolicyCapGroup = getSBPolicyCapBusManager().updatePolicyCapGroup(updActualPcg);
			DefaultLogger.debug(this, "$$$Debug:::UPD5 updActual=" + actualPolicyCapGroup);
			anIPolicyCapGroupTrxValue.setPolicyCapGroup(actualPolicyCapGroup);
			return anIPolicyCapGroupTrxValue;

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
			throw new TrxOperationException("Exception in updateActualPolicyCapGroup(): " + ex.toString());
		}
		catch (IOException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualPolicyCapGroup(): " + ex.toString());
		}
	}

	// ========================================================
	// Start of helper methods - to be used by child classes
	// ========================================================
	/**
	 * Prepares a trx object
	 */
	protected IPolicyCapGroupTrxValue prepareTrxValue(IPolicyCapGroupTrxValue policyCapGroupTrxValue) {
		if (policyCapGroupTrxValue != null) {
			IPolicyCapGroup actualPcg = policyCapGroupTrxValue.getPolicyCapGroup();
			IPolicyCapGroup stagingPcg = policyCapGroupTrxValue.getStagingPolicyCapGroup();

			// set reference id
			if (actualPcg != null) {
				DefaultLogger.debug(this, "actualPcg.getPolicyCapGroupID()=" + actualPcg.getPolicyCapGroupID());
				policyCapGroupTrxValue.setReferenceID(String.valueOf(actualPcg.getPolicyCapGroupID()));
			}
			else {
				policyCapGroupTrxValue.setReferenceID(null);
			}

			// set staging reference id
			if ((stagingPcg != null) && (stagingPcg.getPolicyCapGroupID() != ICMSConstant.LONG_INVALID_VALUE)) {
				policyCapGroupTrxValue.setStagingReferenceID(String.valueOf(stagingPcg.getPolicyCapGroupID()));
			}
			else {
				policyCapGroupTrxValue.setStagingReferenceID(null);
			}

			return policyCapGroupTrxValue;
		}

		return null;
	}

	/**
	 * Helper method to cast a generic trx value object to a IPolicyCapGroup
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IPolicyCapGroupTrxValue - the policy cap specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IPolicyCapGroupTrxValue getPolicyCapGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IPolicyCapGroupTrxValue) anITrxValue;
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
	protected ITrxResult prepareResult(IPolicyCapGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
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

	/**
	 * Generate sequence number based on the sequnece name given
	 * @param sequenceName
	 * @return
	 * @throws PolicyCapException
	 */
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

}
