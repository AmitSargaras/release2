/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.cms.batch.common.filereader.CSVTokenizer;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CSVFeedReader implements IFeedReader {
	private PropertyUtil prop;

	private String CSV_DELIM_STR = ",";

	private String FEED_DATE_FORMAT = "yyyyMMdd";

	private FeedConfiguration configuration;

	private BufferedReader br;

	private String currentLine;

	public CSVFeedReader() {
		init();
	}

	protected void init() {
		prop = PropertyUtil.getInstance(FeedConstant.feedLayoutPropFileName);
		CSV_DELIM_STR = prop.getProperty(FeedConstant.PROPNAME_CSV_DELIM);
		FEED_DATE_FORMAT = prop.getProperty(FeedConstant.PROPNAME_DATE_FORMAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.batch.reuterfeed.IFeedProcessor#initialize()
	 */
	public void initialize() throws Exception {
		// TODO Auto-generated method stub
		br = new BufferedReader(new FileReader(configuration.getFeedFileName()));
		if (configuration.getIncludeHeaderCol()) {
			br.readLine();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.batch.reuterfeed.IFeedProcessor#hasMoreRows()
	 */
	public boolean hasMoreRows() throws Exception {
		// TODO Auto-generated method stub
		currentLine = br.readLine();
		return (currentLine != null) && !"".equals(currentLine.trim());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedProcessor#processNextLineFromFeed
	 * () retrieve the field mapping definition from feedConfig sort the fields
	 * according to sequence in the configuration for each token in the next
	 * line of feed, store the value in the map value will be stored as string,
	 * and will be mapped to correct type in postprocessor after validation
	 */
	public void processNextLineFromFeed() throws Exception {
		// TODO Auto-generated method stub
		List l = getFieldList();
		int i = 0;
		CSVTokenizer tokenizer = new CSVTokenizer(currentLine, CSV_DELIM_STR);
		while (tokenizer.hasMoreTokens()) {
			String nextToken = tokenizer.nextToken();
			if (i < l.size()) {
				FeedFieldDef nextField = (FeedFieldDef) (l.get(i));
				nextField.setFieldValue(nextToken);
			}
			i = i + 1;
		}
	}

	private List getFieldList() {
		Map fieldDef = configuration.getFieldsMapping();
		List l = new ArrayList();
		l.addAll(fieldDef.values());
		Collections.sort(l);
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.batch.reuterfeed.IFeedProcessor#clearup()
	 */
	public void clearup() throws Exception {
		// TODO Auto-generated method stub
		try {
			if (br != null) {
				br.close();
			}
		}
		catch (Exception ex) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedReader#setFeedConfiguration(
	 * com.integrosys.cms.batch.reuterfeed.FeedConfiguration)
	 */
	public void setFeedConfiguration(FeedConfiguration config) {
		configuration = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedReader#getFeedConfiguration()
	 */
	public FeedConfiguration getFeedConfiguration() {
		return configuration;
	}

}
