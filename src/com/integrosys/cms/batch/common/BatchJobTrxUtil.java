package com.integrosys.cms.batch.common;

import javax.ejb.SessionContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-9
 * @Tag com.integrosys.cms.batch.common.BatchJobTrxUtil.java
 */
public class BatchJobTrxUtil {
	private SessionContext context = null;

	private UserTransaction trx = null;

	private boolean isBegin = false;

	public BatchJobTrxUtil(SessionContext context) {
		this.context = context;
		isBegin = false;
	}

	public void beginUserTrx() throws SystemException, NotSupportedException {
		DefaultLogger.debug(this, "- BeginFlag : " + isBegin);
		if (isBegin) {
			DefaultLogger.debug(this, "User Trx Begined Already !");
			return;
		}
		if (trx == null) {
			trx = context.getUserTransaction();
			trx.setTransactionTimeout(getTrxTimeOut());
		}
		trx.begin();
		isBegin = true;
	}

	public void commitUserTrx() throws Exception {
		if (!isBegin) {
			DefaultLogger.debug(this, "User Trx Hv't Begined !");
			return;
		}
		isBegin = false;
		trx.commit();
	}

	public void rollbackUserTrx() {
		if (!isBegin) {
			DefaultLogger.debug(this, "User Trx Hv't Begined !");
			return;
		}
		try {
			isBegin = false;
			trx.rollback();
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "User Trx RollBack : " + e.getMessage());
		}
	}

	private int getTrxTimeOut() {
		String strTrxTimeout = PropertyManager.getValue(ICMSConstant.BATCH_TRX_TIMEOUT_SEC_PROP);
		int trxTimeout = 0;
		try {
			trxTimeout = Integer.parseInt(strTrxTimeout);
		}
		catch (Exception e) {
			trxTimeout = 1000;
		}
		DefaultLogger.debug(this, " - TrxTimeOut: " + trxTimeout);
		return trxTimeout;
	}
}
