package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManager;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class ReadPolicyCapOperation extends CMSTrxOperation implements ITrxReadOperation {

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_POLICY_CAP;
	}

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue - the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException if
	 *         any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			DefaultLogger.debug(this, ">>>>>>>>>>>>>> In ReadPolicyCapOperation.getTransaction");
			ICMSTrxValue trxValue = (ICMSTrxValue) anITrxValue;
			DefaultLogger.debug(this, ">>>>>>>>>>>>>> trxValue.getReferenceID()=" + trxValue.getReferenceID());
			DefaultLogger.debug(this, ">>>>>>>>>>>>>> trxValue.getTransactionType()=" + trxValue.getTransactionType());
			trxValue = getTrxManager()
					.getTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());

			DefaultLogger.debug(this, ">>>>>>>>>>>>>>> Trx Value reference id = " + trxValue.getReferenceID() + "\n"
					+ trxValue);
			OBPolicyCapTrxValue newValue = new OBPolicyCapTrxValue(trxValue);
			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, ">>>>>>>>>>> Actual Reference: " + actualRef + " , Staging Reference: "
					+ stagingRef);

			if (stagingRef != null) {
				Long stagingGroupID = new Long(stagingRef);
				IPolicyCap[] staging = getSBStagingPolicyCapBusManager().getPolicyCapByGroupID(stagingGroupID);
				if ((staging != null) && (staging.length > 0)) {

				}
				newValue.setStagingPolicyCap(staging);
			}

			if (actualRef != null) {
				Long actualGroupID = new Long(actualRef);
				IPolicyCap[] actual = getSBPolicyCapBusManager().getPolicyCapByGroupID(actualGroupID);
				if ((actual != null) && (actual.length > 0)) {

				}
				newValue.setPolicyCap(actual);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}

	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * policy cap data
	 * @return SBPolicyCapBusManager - the home interface for the staging policy
	 *         cap session bean
	 */
	private SBPolicyCapBusManager getSBStagingPolicyCapBusManager() throws TransactionException {
		SBPolicyCapBusManager remote = (SBPolicyCapBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_POLICY_CAP_BUS_JNDI, SBPolicyCapBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingPolicyCapBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * policy cap data
	 * @return SBPolicyCapBusManager - the home interface for the policy cap
	 *         session bean
	 */
	private SBPolicyCapBusManager getSBPolicyCapBusManager() throws TransactionException {
		SBPolicyCapBusManager remote = (SBPolicyCapBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_POLICY_CAP_BUS_JNDI, SBPolicyCapBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBPolicyCapBusManager is null!");
	}
}
