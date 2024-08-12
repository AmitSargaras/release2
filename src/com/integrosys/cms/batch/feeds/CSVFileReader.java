package com.integrosys.cms.batch.feeds;

import java.util.List;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.batch.common.FeedHelper;

public class CSVFileReader extends AbstractMonitorAdapter {

	private final String FEED_STOCK_KEY = "feed.stock";

	private final String FEED_UT_KEY = "feed.unittrust";

	private final String FEED_CA_SHARES_KEY = "feed.sharesoutstanding";

	private final String FEED_BOND_KEY = "feed.bond";

	public List readFeeds(String inputFile, String feedType) throws java.io.FileNotFoundException, java.io.IOException {
		FeedHelper feedHelper = new FeedHelper();
		DefaultLogger.debug(this, "feedType = " + feedType);

		if (ICMSConstant.STOCK_FEED_GROUP_TYPE.equals(feedType)) {
			DefaultLogger.debug(this, "FEED_STOCK_KEY = " + FEED_STOCK_KEY);
			return feedHelper.getListForFeed(inputFile, FEED_STOCK_KEY);
		}
		if (ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE.equals(feedType)) {
			DefaultLogger.debug(this, "FEED_UT_KEY = " + FEED_UT_KEY);
			return feedHelper.getListForFeed(inputFile, FEED_UT_KEY);
		}
		// Not Used
		if (ICMSConstant.CORPORATE_ACTION_FEED_GROUP_TYPE.equals(feedType)) {
			DefaultLogger.debug(this, "FEED_CA_SHARES_KEY = " + FEED_CA_SHARES_KEY);
			return feedHelper.getListForFeed(inputFile, FEED_CA_SHARES_KEY);
		}
		// Not Used
		if (ICMSConstant.BOND_FEED_GROUP_TYPE.equals(feedType)) {
			DefaultLogger.debug(this, "FEED_BOND_KEY = " + FEED_BOND_KEY);
			return feedHelper.getListForFeed(inputFile, FEED_BOND_KEY);
		}
		throw new IllegalArgumentException("Unknown Feed Type: " + feedType);
	}

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doWork(countryCode, context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);
		}
	}

	private void doWork(String countryCode, SessionContext context) {
		CSVFileReader cp = new CSVFileReader();
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		try {
			trxUtil.beginUserTrx();
			List feedList = cp.readFeeds(countryCode, "CORPORATE_ACTION");
			// FeedDAO dao = new FeedDAO();
			// dao.updateFeeds(feedList,"STOCK");
			trxUtil.commitUserTrx();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception!", e);
			trxUtil.rollbackUserTrx();
		}
	}
}
