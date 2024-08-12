/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.batch.feeds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.batch.common.StartupInit;

// exception handeling...

/**
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 */
public class GenarateFeedPortifolioItems extends AbstractMonitorAdapter {
	public static final String STOCK_PORTIFOLIO = "batch.feed.stock.protifolio";

	public static final String BOND_PORTIFOLIO = "batch.feed.bond.protifolio";

	public static final String UNIT_TRUST_PORTIFOLIO = "batch.feed.unit_trust.protifolio";

	public static final String CORPORATE_ACTION_PORTIFOLIO = "batch.feed_corporate.action.protifolio";

	public static final String PORTIFOLIO_SEND_DIR = "batch.feed.send.dir";

	/**
	 * Default Constructor
	 */
	public GenarateFeedPortifolioItems() {
		StartupInit.init();
	}

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doWork(context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);
		}
	}

	private void doWork(SessionContext context) {
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		try {
			trxUtil.beginUserTrx();
			String portifolioDir = PropertyManager.getValue(PORTIFOLIO_SEND_DIR);
			String portifolioFileName = PropertyManager.getValue(STOCK_PORTIFOLIO);
			DefaultLogger.debug(this, "File : " + portifolioDir + portifolioFileName);
			DefaultLogger.debug(this, "Dir : " + portifolioDir);
			if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
				writeFile(portifolioDir + portifolioFileName, ICMSConstant.STOCK_FEED_GROUP_TYPE);
			}
			portifolioFileName = PropertyManager.getValue(BOND_PORTIFOLIO);
			if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
				writeFile(portifolioDir + portifolioFileName, ICMSConstant.BOND_FEED_GROUP_TYPE);
			}
			portifolioFileName = PropertyManager.getValue(UNIT_TRUST_PORTIFOLIO);
			if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
				writeFile(portifolioDir + portifolioFileName, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
			}
			portifolioFileName = PropertyManager.getValue(CORPORATE_ACTION_PORTIFOLIO);
			if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
				writeFile(portifolioDir + portifolioFileName, ICMSConstant.CORPORATE_ACTION_FEED_GROUP_TYPE);
			}
			trxUtil.commitUserTrx();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception!", e);
			trxUtil.rollbackUserTrx();
		}
	}

	public static void main(String[] args) throws Exception {
		String portifolioDir = PropertyManager.getValue(PORTIFOLIO_SEND_DIR);
		String portifolioFileName = PropertyManager.getValue(STOCK_PORTIFOLIO);
		DefaultLogger.debug("File : ", portifolioDir + portifolioFileName);
		DefaultLogger.debug("Dir : ", portifolioDir);
		if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
			writeFile(portifolioDir + portifolioFileName, ICMSConstant.STOCK_FEED_GROUP_TYPE);
		}
		portifolioFileName = PropertyManager.getValue(BOND_PORTIFOLIO);
		if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
			writeFile(portifolioDir + portifolioFileName, ICMSConstant.BOND_FEED_GROUP_TYPE);
		}
		portifolioFileName = PropertyManager.getValue(UNIT_TRUST_PORTIFOLIO);
		if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
			writeFile(portifolioDir + portifolioFileName, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
		}
		portifolioFileName = PropertyManager.getValue(CORPORATE_ACTION_PORTIFOLIO);
		if ((portifolioFileName != null) && (portifolioFileName.length() > 0)) {
			writeFile(portifolioDir + portifolioFileName, ICMSConstant.CORPORATE_ACTION_FEED_GROUP_TYPE);
		}
	}

	private static void writeFile(String fileName, String feedType) throws SearchDAOException {
		DefaultLogger.debug("FIle Name is ", fileName);
		OutputStream out;
		try {
			File f = null;
			f = new File(fileName);
			boolean createNewFile = f.createNewFile();
			  if(createNewFile==false) {
			System.out.println("Error while creating new file:"+f.getPath());	
		      }
			if (f.canWrite()) {
				out = new FileOutputStream(f);
				FeedDAO dao = new FeedDAO();
				List list = dao.getFeeds(feedType);
				CSVFileWriter p = new CSVFileWriter(out);
				OBFeed feed;
				for (int i = 0; i < list.size(); i++) {
					feed = (OBFeed) list.get(i);
					p.print("RIC");
					p.println(feed.getRic());
				}
			}
			else {
				DefaultLogger
						.debug("GenerateFeedPortifolioItemsFile", " Could not open protifolioFileName " + fileName);
			}
		}
		catch (IOException e) {
			DefaultLogger.debug("GenarateFeedPortifolioItemsFile", "" + e.getMessage());
		}
	}
}