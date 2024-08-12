/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * AbstractCommonCodeEntriesTrxOperation.java
 *
 * Created on February 6, 2007, 12:03 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.SBCommonCodeEntryManager;
import com.integrosys.cms.app.commoncodeentry.bus.SBCommonCodeEntryManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * 
 * @author Eric
 */
public abstract class AbstractCommonCodeEntriesTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	protected ICommonCodeEntriesTrxValue getCommonCodeEntriesTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			if (anITrxValue instanceof ICommonCodeEntriesTrxValue) {
				DefaultLogger.debug(this, "ITrxValue is an instance of ICommonCodeEntriesTrxValue");

				return (ICommonCodeEntriesTrxValue) anITrxValue;
			}
			else {
				DefaultLogger
						.debug(this,
								"ITrxValue is not an instance of ICommonCodeEntriesTrxValue , creating new identical ICommonCodeEntriesTrxValue object");

				return new OBCommonCodeEntriesTrxValue(anITrxValue);
			}
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception is converting ITrxValue into ICommonCodeEntriesTrxValue : "
					+ ex.toString(), ex);
		}
	}

	protected ICommonCodeEntriesTrxValue prepareTrxValue(ICommonCodeEntriesTrxValue commonCodeEntriesTrxValue) {
		if (commonCodeEntriesTrxValue != null) {
			DefaultLogger.debug(this, "commonCodeEntriesTrxValue value not null");
			ICommonCodeEntries actual = commonCodeEntriesTrxValue.getCommonCodeEntries();
			ICommonCodeEntries staging = commonCodeEntriesTrxValue.getStagingCommonCodeEntries();

			if (actual != null) {
				commonCodeEntriesTrxValue.setReferenceID(String.valueOf(actual.getCategoryCodeId()));

				DefaultLogger.debug(this, "Category Code Id : " + actual.getCategoryCodeId());
			}
			else {
				// commonCodeEntriesTrxValue.setReferenceID (
				// commonCodeEntriesTrxValue.getReferenceID () );

				DefaultLogger.debug(this, "Actual data not found in ICommonCodeEntriesTrxValue !");
			}

			if ((staging != null) && (staging.getCategoryCodeId() != ICMSConstant.LONG_INVALID_VALUE)) {
				commonCodeEntriesTrxValue.setStagingReferenceID(commonCodeEntriesTrxValue.getStagingReferenceID());
			}
			else {
				commonCodeEntriesTrxValue.setStagingReferenceID(null);

				DefaultLogger.debug(this, "Staging data not found in ICommonCodeEntriesTrxValue !");
			}

			return commonCodeEntriesTrxValue;
		}

		return null;
	}

	protected ICommonCodeEntriesTrxValue createStagingCommonCodeEntries(ICommonCodeEntriesTrxValue value)
			throws TrxOperationException {
		SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();

		try {
			DefaultLogger.debug(this, "value.getStagingReferenceID () : " + value.getStagingCommonCodeEntries());

			OBCommonCodeEntries entries = manager.setStaging(value.getStagingCommonCodeEntries(), new Long(value
					.getStagingReferenceID()));
			value.setStagingCommonCodeEntries(entries);
			value.setStagingReferenceID(entries.getStagingReferenceID());

			DefaultLogger.debug(this, "entries.getStagingReferenceID () : " + entries.getStagingReferenceID());
		}
		catch (Exception ex) {
			ex.printStackTrace();

			throw new TrxOperationException(ex.getMessage());
		}

		return value;
	}

	protected ICommonCodeEntriesTrxValue restoreStageCommonCodeEntry(ICommonCodeEntriesTrxValue value)
			throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "restoreStaging: " + value.getStagingReferenceID());
			getSBCommonCodeEntryManager().restoreStageCommonCodeEntry(new Long(value.getStagingReferenceID()));
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex.getMessage());
		}
		return value;
	}

	protected ICommonCodeEntriesTrxValue updateCommonCodeEntriesTrx(ICommonCodeEntriesTrxValue value)
			throws TrxOperationException {
		value = prepareTrxValue(value);

		DefaultLogger.debug(this, "Now updating data in transaction table");
		DefaultLogger.debug(this, "value.getReferenceID () : " + value.getReferenceID());
		DefaultLogger.debug(this, "value.getStagingReferenceID () : " + value.getStagingReferenceID());

		ICMSTrxValue tempValue = super.updateTransaction(value);

		OBCommonCodeEntriesTrxValue ret = new OBCommonCodeEntriesTrxValue(tempValue);

		ret.setCommonCodeEntries(value.getCommonCodeEntries());
		ret.setCommonCodeEntriesList(value.getCommonCodeEntriesList());

		return ret;
	}

	protected ICommonCodeEntriesTrxValue updateActualCommonCodeEntries(ICommonCodeEntriesTrxValue value)
			throws TrxOperationException {
		ICommonCodeEntries stagingValue = value.getStagingCommonCodeEntries();
		ICommonCodeEntries actualValue = value.getCommonCodeEntries();

		try {
			ICommonCodeEntries clone = (ICommonCodeEntries) CommonUtil.deepClone(stagingValue);

			clone.setVersionTime(actualValue.getVersionTime());

			SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();
			ICommonCodeEntries updated = manager.updateCommonCodeEntries(clone);
			value.setStagingCommonCodeEntries(updated);

			return value;
		}
		catch (Exception ex) {
			ex.printStackTrace();

			throw new TrxOperationException(ex.getMessage());
		}
	}

	protected ITrxResult prepareResult(ICommonCodeEntriesTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected final SBCommonCodeEntryManager getSBCommonCodeEntryManager() {
		Object obj = BeanController.getEJB(ICMSJNDIConstant.SB_COMMON_CODE_MANAGER_ENTRY_HOME,
				SBCommonCodeEntryManagerHome.class.getName());

		if (obj == null) {
			throw new NullPointerException("Unable to locate SBCommonCodeEntryManager");
		}

		return (SBCommonCodeEntryManager) obj;
	}

}
