package com.integrosys.cms.batch.sibs.collateral;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

/**
 * Batch Job for share margin finance feeds
 * 
 * @author Chong Jun Yong
 * 
 */
public class ShareMarginFinanceFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private final String COLL_SMF_ENTITY_NAME = "collateralSMF";

	private FlatFileItemReader smfFlatFileItemReader;

	private ICollateralDao collateralDao;

	public FlatFileItemReader getSmfFlatFileItemReader() {
		return smfFlatFileItemReader;
	}

	public ICollateralDao getCollateralDao() {
		return collateralDao;
	}

	public void setSmfFlatFileItemReader(FlatFileItemReader smfFlatFileItemReader) {
		this.smfFlatFileItemReader = smfFlatFileItemReader;
	}

	public void setCollateralDao(ICollateralDao collateralDao) {
		this.collateralDao = collateralDao;
	}

	protected void doPersistFeedList(List feedList) {
		getCollateralDao().saveCollateralSMFList(COLL_SMF_ENTITY_NAME, feedList);
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getSmfFlatFileItemReader();
	}

}
