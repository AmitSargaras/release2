/*
 * Created on Aug 11, 2004
 *
 */
package com.integrosys.cms.batch.commodityfeeds;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.SessionContext;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.batch.common.StartupInit;

/**
 * @author Li Hai Tao
 */
public class CSVFileReader extends AbstractMonitorAdapter {

	public static final String SEP = System.getProperty("file.separator");

	private static final String BATCH_COMMODITY_FEED_DIR = "batch.commodity_feed_dir_path";

	private static final String BATCH_COMMODITY_FEED_FILENAME = "batch.commodity_feed_csv_name";

	private List currencyList;

	public CSVFileReader() {
		StartupInit.init();
	}

	public void start(String inputFilename, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doWork(inputFilename, context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);

		}
	}

	private void doWork(String inputFilename, SessionContext context) {
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		String filename = "";
		try {
			CSVFileReader cp = new CSVFileReader();
			CommodityFeedDAO dao = new CommodityFeedDAO();

			if (!StringUtils.isEmpty(inputFilename)) {
				filename = inputFilename;
			}
			else {
				StringBuffer fileNameBuffer = new StringBuffer();
				DefaultLogger.debug(this, "logger started");
				String config_batch_commodity_feed_dir = PropertyManager.getValue(BATCH_COMMODITY_FEED_DIR);
				DefaultLogger.debug(this, "config_batch_commodity_feed_dir_path " + config_batch_commodity_feed_dir);
				String config_batch_commodity_feed_filename = PropertyManager.getValue(BATCH_COMMODITY_FEED_FILENAME);
				DefaultLogger.debug(this, "config_batch_commodity_feed_csv_name "
						+ config_batch_commodity_feed_filename);

				if ((config_batch_commodity_feed_dir == null) || "".equals(config_batch_commodity_feed_dir)
						|| (config_batch_commodity_feed_filename == null)
						|| "".equals(config_batch_commodity_feed_filename)) {
					/**
					 * Eg.
					 * "//usr/WebSphere/cms/batch/commodityfeeds/PriceFeed.csv";
					 */
					filename = "/usr/WebSphere/cms/batch/commodityfeeds/PriceFeed.csv";
					DefaultLogger.debug(CSVFileReader.class, "Dynamic loading failed, load default file :" + filename);

				}
				else {
					fileNameBuffer.append(config_batch_commodity_feed_dir);
					fileNameBuffer.append(SEP);
					fileNameBuffer.append(config_batch_commodity_feed_filename);
					filename = fileNameBuffer.toString();
					DefaultLogger.debug(CSVFileReader.class, "Dynamic loading succeed, load config file: " + filename);
				}
			}

			trxUtil.beginUserTrx();
			List feedList = dao.getFeedsRIC();
			try {
				feedList = cp.getFeeds(feedList, filename);
			}
			catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
				String temp = "/usr/WebSphere/cms/batch/commodityfeeds/PriceFeed.csv";
				DefaultLogger.error(CSVFileReader.class, "cannot find " + filename + ", try to reload default file :"
						+ temp);
				filename = "/usr/WebSphere/cms/batch/commodityfeeds/PriceFeed.csv";
				feedList = cp.getFeeds(feedList, filename);
			}
			catch (Exception fnfe) {
				fnfe.printStackTrace();
				String temp = "/usr/WebSphere/cms/batch/commodityfeeds/PriceFeed.csv";
				DefaultLogger.error(CSVFileReader.class, "cannot find " + filename + ", try to reload default file :"
						+ temp);
				filename = "/usr/WebSphere/cms/batch/commodityfeeds/PriceFeed.csv";
				feedList = cp.getFeeds(feedList, filename);
			}
			int noOfRecUpdated = dao.updateFeeds(feedList);
			DefaultLogger.debug(this, "Num of update: " + noOfRecUpdated);
			trxUtil.commitUserTrx();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception!", e);
			trxUtil.rollbackUserTrx();
		}
	}

	public List getFeeds(List feedList, String filename) throws FileNotFoundException, IOException, SearchDAOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		OBCommodityFeed feed = null;
		String line = null;
		StringTokenizer st = null;
		int lineNo = 1;
		ArrayList list = new ArrayList();
		boolean canAdd = false;
		line = br.readLine();
		OUTER: while (line != null) {
			// We're not at EOF, so start a row and process from row 3 onwards..
			feed = new OBCommodityFeed();
			canAdd = false;
			int i = 1;
			st = new StringTokenizer(line, ",");
			String token = null;
			DefaultLogger.debug(this, "----------- Print csv file rows ------------");
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				if (token.trim().equals("RICMAINT")) {
					DefaultLogger.debug(this, "----------- BREAKING OUTER ------------");
					break OUTER;
				}
				if (lineNo > 1) {

					if (i == 1) { // ric
						if (!isNullVal(token.trim())) {
							int index = inList(token.trim(), feedList);
							if (index == -1) {
								canAdd = false;
							}
							else {
								canAdd = true;
								feed.setProfileID(((OBCommodityFeed) feedList.get(index)).getProfileID());
							}
							feed.setRic(token.trim());
						}
						else {
							canAdd = false;
						}
					}
					else if (i == 2) { // last price
						if (!isNullVal(token.trim())) {
							try {
								feed.setLastPrice(new Double(token.trim()).doubleValue());
							}
							catch (NumberFormatException e) {
								DefaultLogger.error(this, "load 'last price' failed (" + e.getMessage()
										+ ") , reuse existing 'last price'");
								canAdd = false;
								// feed.setLastPrice(0);
							}
						}
					}
					else if (i == 3) { // last date
						if (!isNullVal(token.trim())) {
							String day = getDateString(token.trim());
							if (day == null) {
								feed.setLastDate(DateUtil.getDate());
							}
							else {
								feed.setLastDate(DateUtil.convertDate(day));
							}
						}
					}
					else if (i == 4) { // current price
						if (!isNullVal(token.trim())) {
							try {
								feed.setCurrentPrice(new Double(token.trim()).doubleValue());
							}
							catch (NumberFormatException e) {
								DefaultLogger.error(this, "load 'current price' failed (" + e.getMessage()
										+ ") , reuse existing 'current price'");
								canAdd = false;
								// feed.setCurrentPrice(0);
							}
						}
					}
					else if (i == 5) { // current date
						if (!isNullVal(token.trim())) {
							String day = getDateString(token.trim());
							if (day == null) {
								feed.setCurrentDate(DateUtil.getDate());
							}
							else {
								feed.setCurrentDate(DateUtil.convertDate(day));
							}

						}
					}
					else if (i == 6) { // CURRENCY CODE
						if (!isNullVal(token.trim()) && isValidCurrency(token.trim().toUpperCase())) {
							feed.setCurrency(token.trim());
						}
						else {
							canAdd = false;
						}
					}
					else if (i == 7) { // size of unit
						if (!isNullVal(token.trim())) {
							feed.setUnitSize(token.trim());
						}
					}
					else {
						// only to print the uncaptured data..
						DefaultLogger.debug("        " + i + " : ", "" + token);
					}
				}
				i++;
			}
			if (canAdd) {
				list.add(feed);
			}
			line = br.readLine();
			lineNo++;
		}

		return list;
	}

	private int inList(String ric, List feedList) {
		if ((feedList != null) && (feedList.size() > 0)) {
			for (int index = 0; index < feedList.size(); index++) {
				if (ric.equals(((OBCommodityFeed) feedList.get(index)).getRic())) {
					return index;
				}
			}
			return -1;
		}
		else {
			return -1;
		}
	}

	private boolean isNullVal(String val) {
		if (val.equals("null") || val.startsWith("-9")) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Format date string to utildate format dd-mm-yyyy.
	 * 
	 * @param dtString
	 * @return String..
	 */
	private String getDateString(String dtString) {
		String formatDtStr = null;
		String day, month, year;
		int iDay, iMonth, iYear;
		try {
			day = dtString.substring(6);
			month = dtString.substring(4, 6);
			year = dtString.substring(0, 4);

			iDay = new Integer(day).intValue();
			iMonth = new Integer(month).intValue();
			iYear = new Integer(year).intValue();

			if ((iYear < 2999) && (iYear > 1000) && (iMonth > 0) && (iMonth < 13) && (iDay > 0) && (iDay < 32)) {
				formatDtStr = day + "-" + month + "-" + year;
			}
		}
		catch (Exception e) {

		}
		return formatDtStr;
	}

	private boolean isValidCurrency(String ccy) throws SearchDAOException {

		if ((ccy == null) || (ccy.length() == 0)) {
			return false;
		}

		if (getCurrencies().contains(ccy)) {
			return true;
		}
		return false;

	}

	private List getCurrencies() throws SearchDAOException {

		if (this.currencyList == null) {
			this.currencyList = new CommodityFeedDAO().getCurrencies();
		}
		return currencyList;

	}

}
