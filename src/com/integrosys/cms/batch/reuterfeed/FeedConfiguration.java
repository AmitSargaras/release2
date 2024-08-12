/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;
import javax.transaction.UserTransaction;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedConfiguration {
	public static final String FIELD_TYPE_STRING = "string";

	public static final String FIELD_TYPE_INTEGER = "integer";

	public static final String FIELD_TYPE_LONG = "long";

	public static final String FIELD_TYPE_DOUBLE = "double";

	public static final String FIELD_TYPE_FLOAT = "float";

	public static final String FIELD_TYPE_DATE = "date";

	private int batchSize = 20;

	private String feedFileName;

	private boolean includeHeaderCol;

	private String feedFormat;

	private String tableName;

	private Map fieldsMapping;

	private List validatorList;

	private List feedPostProcessorList;

	private List feedPersisterList;

	/**
	 * @return Returns the batchSize.
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize The batchSize to set.
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return Returns the feedFileName.
	 */
	public String getFeedFileName() {
		return feedFileName;
	}

	/**
	 * @param feedFileName The feedFileName to set.
	 */
	public void setFeedFileName(String feedFileName) {
		this.feedFileName = feedFileName;
	}

	/**
	 * @return Returns the feedFormat.
	 */
	public String getFeedFormat() {
		return feedFormat;
	}

	/**
	 * @param feedFormat The feedFormat to set.
	 */
	public void setFeedFormat(String feedFormat) {
		this.feedFormat = feedFormat;
	}

	/**
	 * @return Returns the feedPersisterList.
	 */
	public List getFeedPersisterList() {
		return feedPersisterList;
	}

	/**
	 * @param feedPersisterList The feedPersisterList to set.
	 */
	public void setFeedPersisterList(List feedPersisterList) {
		this.feedPersisterList = feedPersisterList;
	}

	/**
	 * @return Returns the feedPostProcessorList.
	 */
	public List getFeedPostProcessorList() {
		return feedPostProcessorList;
	}

	/**
	 * @param feedPostProcessorList The feedPostProcessorList to set.
	 */
	public void setFeedPostProcessorList(List feedPostProcessorList) {
		this.feedPostProcessorList = feedPostProcessorList;
	}

	/**
	 * @return Returns the validatorList.
	 */
	public List getValidatorList() {
		return validatorList;
	}

	/**
	 * @param validatorList The validatorList to set.
	 */
	public void setValidatorList(List validatorList) {
		this.validatorList = validatorList;
	}

	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName The tableName to set.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return Returns the fieldsMapping.
	 */
	public Map getFieldsMapping() {
		return fieldsMapping;
	}

	/**
	 * @param fieldsMapping The fieldsMapping to set.
	 */
	public void setFieldsMapping(Map fieldsMapping) {
		this.fieldsMapping = fieldsMapping;
	}

	/**
	 * @param includeHeaderCol The includeHeaderCol to set.
	 */
	public void setIncludeHeaderCol(boolean includeHeaderCol) {
		this.includeHeaderCol = includeHeaderCol;
	}

	public boolean getIncludeHeaderCol() {
		return includeHeaderCol;
	}

	public void processFeed(SessionContext context) throws Exception {
		DefaultLogger.debug(this, "start process feed ");
		IFeedReader reader = FeedReaderFactory.getInstance().getFeedReader(feedFormat);
		reader.setFeedConfiguration(this);
		UserTransaction userTran = null;
		boolean restartTrans = true;
		int curRemain = batchSize;
		reader.initialize();
		while (reader.hasMoreRows()) {
			if (restartTrans) {
				userTran = context.getUserTransaction();
				userTran.begin();
				restartTrans = false;
			}
			try {
				DefaultLogger.debug(this, "process next line ");
				reader.processNextLineFromFeed();
				List errorList = new ArrayList();
				boolean valid = true;
				for (int i = 0; i < validatorList.size(); i++) {
					IFeedValidator validator = (IFeedValidator) (validatorList.get(i));
					if (!validator.validateFeed(fieldsMapping, errorList)) {
						valid = false;
					}
				}
				if (valid) {
					for (int i = 0; i < feedPostProcessorList.size(); i++) {
						IFeedPostProcessor feedPostProcessor = (IFeedPostProcessor) (feedPostProcessorList.get(i));
						feedPostProcessor.performFeedPostProcess(fieldsMapping);
					}
					for (int j = 0; j < feedPersisterList.size(); j++) {
						IFeedPersister persister = (IFeedPersister) (feedPersisterList.get(j));
						persister.performPersistence(tableName, fieldsMapping);
					}
					curRemain = curRemain - 1;
					if (curRemain == 0) {
						userTran.commit();
						userTran = null;
						restartTrans = true;
						curRemain = batchSize;
					}
				}
				else {
					DefaultLogger.debug(this, errorList);
				}
			}
			catch (Exception ex) {
				userTran.rollback();
				userTran = null;
				restartTrans = true;
				curRemain = batchSize;
			}
		}
		if ((curRemain > 0) && (userTran != null)) {
			userTran.commit();
			userTran = null;
		}
		reader.clearup();
		DefaultLogger.debug(this, "end process feed ");
	}
}
