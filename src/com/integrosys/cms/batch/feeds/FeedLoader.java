/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.batch.feeds;

import java.util.List;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.BatchUtil;
import com.integrosys.cms.batch.common.StartupInit;

// exception handeling...

/**
 * Purpose: A batch program that takes in forex rates data in ASCII format. It
 * parses the text file and prepares the data for uploading into the Forex
 * tables. <p/> Description: Please ensure that there the rates data file is in
 * the current directory java -cp %CLASSPATH%
 * -DPropertyFile=%INTEGRO_HOME%\config\currencydenom.properties
 * ;%INTEGRO_HOME%\config\ofa.properties
 * com.integrosys.cms.batch.forex.ForexLoader filename <p/> Please ensure that
 * the ofa.properties file is updated with this entry
 * dbconfig.manager.batch.class
 * =com.integrosys.cms.batch.forex.SCBJDBCConnectionManager
 * 
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 */
public class FeedLoader extends AbstractMonitorAdapter {

	// All not used since running by source system now
	/*
	 * public static final String STOCK_PORTIFOLIO =
	 * "batch.feed.stock.protifolio"; public static final String
	 * STOCK_PORTFOLIO_MARSHA = "batch.feed.stock.portfolio"; public static
	 * final String STOCK_PORTFOLIO_NOMINEES = "batch.feed.stock.protifolio";
	 * public static final String UNIT_TRUST_PORTFOLIO_MARSHA =
	 * "batch.feed.unit_trust.protifolio"; public static final String
	 * UNIT_TRUST_PORTFOLIO_NOMINEES = "batch.feed.unit_trust.protifolio";
	 * public static final String BOND_PORTIFOLIO =
	 * "batch.feed.bond.protifolio"; public static final String
	 * UNIT_TRUST_PORTIFOLIO = "batch.feed.unit_trust.protifolio"; public static
	 * final String CORPORATE_ACTION_PORTIFOLIO =
	 * "batch.feed_corporate.action.protifolio"; public static final String
	 * PORTIFOLIO_RECEIVE_DIR = "batch.feed.receive.dir";
	 */

	// Feeds from Nominees (MBB)
	public static final String STOCK_BATCH_RECEIVE_DIR_900 = "batch.nominees.feed.stock.receive.dir";

	public static final String STOCK_BATCH_RECEIVE_FILENAME_900 = "batch.nominees.feed.stock.receive.filename";

	public static final String UNIT_TRUST_BATCH_RECEIVE_DIR_900 = "batch.nominees.feed.unit_trust.receive.dir";

	public static final String UNIT_TRUST_BATCH_RECEIVE_FILENAME_900 = "batch.nominees.feed.unit_trust.receive.filename";

	// MARSHA Feeds from Share Investor (MBS)
	public static final String STOCK_BATCH_RECEIVE_DIR_992 = "batch.marsha.feed.stock.receive.dir";

	public static final String STOCK_BATCH_RECEIVE_FILENAME_992 = "batch.marsha.feed.stock.receive.filename";

	public static final String UNIT_TRUST_BATCH_RECEIVE_DIR_992 = "batch.marsha.feed.unit_trust.receive.dir";

	public static final String UNIT_TRUST_BATCH_RECEIVE_FILENAME_992 = "batch.marsha.feed.unit_trust.receive.filename";

	/**
	 * Default Constructor
	 */
	public FeedLoader() {
		StartupInit.init();
	}

	/*
	 * static { System.out.println("in FeedLoader's static block."); String
	 * property = "/startup.properties";
	 * StartupController.init(PropertyUtil.getInstance(property));
	 * PropertyManager.getInstance().startup(null);
	 * DefaultLogger.debug("FeedLoader", "Initialize startup");
	 * DefaultLogger.debug("FeedLoader", "Initialize startup"); }
	 */

	public void start(String sourceSystemID, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doWork(context, sourceSystemID);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);
		}
	}

	private void doWork(SessionContext context, String sourceSystemID) {
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		BatchUtil batchUtil = new BatchUtil();

		try {

			String backupDir = (new BatchResourceFactory()).getBatchBackUpDir();
			CSVFileReader cp = new CSVFileReader();
			FeedDAO dao = new FeedDAO();
			String portifolioDir = "";
			String fileName = "";
			boolean isDirCreated;
			boolean isFileBackUped;
			boolean exists;

			if (sourceSystemID != null) {
				if (ICMSConstant.SOURCE_SYSTEM_NOMINEES.equals(sourceSystemID.trim())) {
					portifolioDir = PropertyManager.getValue(STOCK_BATCH_RECEIVE_DIR_900);
					fileName = PropertyManager.getValue(STOCK_BATCH_RECEIVE_FILENAME_900);
				}
				else if (ICMSConstant.SOURCE_SYSTEM_MARSHA.equals(sourceSystemID.trim())) {
					portifolioDir = PropertyManager.getValue(STOCK_BATCH_RECEIVE_DIR_992);
					fileName = PropertyManager.getValue(STOCK_BATCH_RECEIVE_FILENAME_992);
				}
				// portifolioDir =
				// PropertyManager.getValue(PORTIFOLIO_RECEIVE_DIR);
				// fileName = PropertyManager.getValue(STOCK_PORTIFOLIO);

				DefaultLogger.debug(this, "Portifolio file: " + portifolioDir + fileName);

				exists = batchUtil.isFileExists(fileName);
				DefaultLogger.debug(this, "STOCK_PORTIFOLIO exists: " + exists);

				if ((fileName != null) && (fileName.length() > 0)) {
					fileName = portifolioDir + fileName;
					List feedList = cp.readFeeds(fileName, ICMSConstant.STOCK_FEED_GROUP_TYPE);
					trxUtil.beginUserTrx();
					int noOfRecUpdated = dao.updateFeeds(feedList, ICMSConstant.STOCK_FEED_GROUP_TYPE);
					trxUtil.commitUserTrx();
				}
				isDirCreated = batchUtil.makeDir(backupDir);
				isFileBackUped = batchUtil.backupFile(fileName, backupDir);
				DefaultLogger.info(this, "Backup batch file:" + isFileBackUped);
				DefaultLogger.debug(this, "Finished Loading STOCK !!!!!!!!!!");
			}
			else {
				DefaultLogger.debug(this, "Stock Source System is null !!! ");
			}

			if (sourceSystemID != null) {
				if (ICMSConstant.SOURCE_SYSTEM_NOMINEES.equals(sourceSystemID.trim())) {
					portifolioDir = PropertyManager.getValue(UNIT_TRUST_BATCH_RECEIVE_DIR_900);
					fileName = PropertyManager.getValue(UNIT_TRUST_BATCH_RECEIVE_FILENAME_900);
				}
				else if (ICMSConstant.SOURCE_SYSTEM_MARSHA.equals(sourceSystemID.trim())) {
					portifolioDir = PropertyManager.getValue(UNIT_TRUST_BATCH_RECEIVE_DIR_992);
					fileName = PropertyManager.getValue(UNIT_TRUST_BATCH_RECEIVE_FILENAME_992);
				}
				// fileName = PropertyManager.getValue(UNIT_TRUST_PORTIFOLIO);

				exists = batchUtil.isFileExists(fileName);
				DefaultLogger.debug(this, "UNIT_TRUST_PORTIFOLIO exists: " + exists);

				if ((fileName != null) && (fileName.length() > 0)) {
					fileName = portifolioDir + fileName;
					List feedList = cp.readFeeds(fileName, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
					trxUtil.beginUserTrx();
					int noOfRecUpdated = dao.updateFeeds(feedList, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
					trxUtil.commitUserTrx();
				}
				isDirCreated = batchUtil.makeDir(backupDir);
				isFileBackUped = batchUtil.backupFile(fileName, backupDir);

				DefaultLogger.info(this, "Backup batch file:" + isFileBackUped);
				DefaultLogger.debug(this, "Finished Loading UNIT_TRUST !!!!!!!!!!");
			}
			else {
				DefaultLogger.debug(this, "Unit_trust Source System is null !!! ");
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception!", e);
			trxUtil.rollbackUserTrx();
		}
	}
}
