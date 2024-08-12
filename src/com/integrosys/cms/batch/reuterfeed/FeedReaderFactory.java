/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedReaderFactory {
	private static FeedReaderFactory instance;

	private Map feedReaderTypeMap;

	public static final String FEED_TYPE_CSV = "csv";

	public static final String FEED_TYPE_XSL = "xsl";

	private FeedReaderFactory() {
		feedReaderTypeMap = new HashMap();
		feedReaderTypeMap.put(FEED_TYPE_CSV, "com.integrosys.cms.batch.reuterfeed.CSVFeedReader");
		feedReaderTypeMap.put(FEED_TYPE_XSL, "com.integrosys.cms.batch.reuterfeed.ExcelFeedReader");
	}

	public static FeedReaderFactory getInstance() {
		if (instance == null) {
			synchronized (FeedReaderFactory.class) {
				if (instance == null) {
					instance = new FeedReaderFactory();
				}
			}
		}
		return instance;
	}

	public IFeedReader getFeedReader(String feedType) throws Exception {
		String feedLower = feedType.toLowerCase().trim();
		String feedReaderClass = (String) feedReaderTypeMap.get(feedType);
		Class c = Class.forName(feedReaderClass);
		return (IFeedReader) (c.newInstance());
	}
}
