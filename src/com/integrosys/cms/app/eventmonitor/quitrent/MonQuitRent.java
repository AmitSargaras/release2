/*
 * Created on Feb 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.eventmonitor.quitrent;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.SessionContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MonQuitRent extends AbstractMonCommon {
	private static final String query = "UPDATE CMS_PROPERTY SET QUIT_RENT_RECEIPT = 'N'";

	protected IMonitorDAO[] getDAOArray() {
		return null;
	}

	protected IMonRule getTriggerRule() {
		return null;
	}

	public IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException {
		return null;
	}

	public String getEventName() {
		return "";
	}

	public void start(String countryCode, SessionContext context) throws EventMonitorException {

		String strCommitloops = PropertyManager.getValue(ICMSConstant.BATCH_TRX_COMMIT_LOOPS_PROP);
		String strTrxTimeout = PropertyManager.getValue(ICMSConstant.BATCH_TRX_TIMEOUT_SEC_PROP);
		int trxTimeout = 0;
		int commitRecordsCount = 0;
		try {
			commitRecordsCount = Integer.parseInt(strCommitloops);
		}
		catch (Exception e) {
			commitRecordsCount = 10;
			DefaultLogger.error(this, "monitoring job commit loops not set, defaulting to 10 ");
		}
		try {
			trxTimeout = Integer.parseInt(strTrxTimeout);
		}
		catch (Exception e) {
			trxTimeout = 1000;
			DefaultLogger.error(this, "monitoring job Transaction Timeout not set, defaulting to 1000 seconds ");
		}
		try {
			if (checkRequireTrigger()) {
				DefaultLogger.debug(this, "start of reset quit rent");
				UserTransaction ut = context.getUserTransaction();
				ut.setTransactionTimeout(trxTimeout);
				ut.begin();
				updateQuitRent();
				ut.commit();
				DefaultLogger.debug(this, "end of reset quit rent");
			}
			DefaultLogger.debug(this, "method of updating quit rent ended here");
		}
		catch (SystemException e) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ", e);
		}
		catch (NotSupportedException e) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ", e);
		}
		catch (HeuristicMixedException e) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ", e);
		}
		catch (HeuristicRollbackException e) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ", e);
		}
		catch (RollbackException e) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ", e);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean checkRequireTrigger() {
		Date curDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(curDate);
		if ((c.get(Calendar.MONTH) == Calendar.JANUARY) && (c.get(Calendar.DAY_OF_MONTH) == 1)) {
			return true;
		}
		return false;
	}

	private void updateQuitRent() throws Exception {
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			dbUtil.executeUpdate();
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "", ex);
			throw ex;
		}
		finally {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
	}
}
