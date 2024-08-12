package com.integrosys.cms.app.commoncode.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.bus.SBCommonCodeTypeBusManager;
import com.integrosys.cms.app.commoncode.bus.SBCommonCodeTypeBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class ReadCommonCodeTypeOperation extends CMSTrxOperation implements ITrxReadOperation {

	/**
	 * Default Constructor
	 */
	public ReadCommonCodeTypeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMON_CODE_TYPE;
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

			OBCommonCodeTypeTrxValue newValue = new OBCommonCodeTypeTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ICommonCodeType stagingCommonCodeType = getSBStagingCommonCodeTypeBusManager().getCategoryById(
						(new Long(stagingRef)).longValue());
				newValue.setStagingCommonCodeType(stagingCommonCodeType);
			}

			if (actualRef != null) {
				ICommonCodeType actualCommonCodeType = getSBCommonCodeTypeBusManager().getCategoryById(
						(new Long(actualRef)).longValue());
				newValue.setCommonCodeType(actualCommonCodeType);
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
	 * @return SBCommonCodeTypeBusManager - the home interface for the staging
	 *         CommonCodeType session bean
	 */
	private SBCommonCodeTypeBusManager getSBStagingCommonCodeTypeBusManager() throws TransactionException {
		SBCommonCodeTypeBusManager remote = (SBCommonCodeTypeBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_COMMON_CODE_TYPE_BUS_JNDI, SBCommonCodeTypeBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingCommonCodeTypeBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBCommonCodeTypeBusManager - the home interface for the
	 *         CommonCodeType session bean
	 */
	private SBCommonCodeTypeBusManager getSBCommonCodeTypeBusManager() throws TransactionException {
		SBCommonCodeTypeBusManager remote = (SBCommonCodeTypeBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMON_CODE_TYPE_BUS_JNDI, SBCommonCodeTypeBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBCommonCodeTypeBusManager is null!");
	}
}
