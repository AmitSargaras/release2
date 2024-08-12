package com.integrosys.cms.batch.mimb.limit;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryUtil;

public class LimitInformationFileLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader limitInfoFileReader;

	private ILimitInformationFileDAO limitInfoFileDAO;

	/**
	 * @param limitInfoFileReader the limitInfoFileReader to set
	 */
	public void setLimitInfoFileReader(FlatFileItemReader limitInfoFileReader) {
		this.limitInfoFileReader = limitInfoFileReader;
	}

	/**
	 * @return the limitInfoFileReader
	 */
	public FlatFileItemReader getLimitInfoFileReader() {
		return limitInfoFileReader;
	}

	public ILimitInformationFileDAO getLimitInfoFileDAO() {
		return limitInfoFileDAO;
	}

	public void setLimitInfoFileDAO(ILimitInformationFileDAO limitInfoFileDAO) {
		this.limitInfoFileDAO = limitInfoFileDAO;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
        
        String categoryCode[] = {"40", "27"};
        for (int i=0; i<categoryCode.length; i++)
            CommonCodeEntryUtil.synchronizeCommonCode(categoryCode[i]);
    }

	protected void doPersistFeedList(List feedList) {
		getLimitInfoFileDAO().createLimitInfoItems(feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return limitInfoFileReader;
	}
}
