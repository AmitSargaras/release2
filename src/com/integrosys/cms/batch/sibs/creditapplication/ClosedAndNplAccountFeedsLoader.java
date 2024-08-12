package com.integrosys.cms.batch.sibs.creditapplication;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

/**
 * Batch Job for closed and non performing loan account feeds
 * 
 * @author Chong Jun Yong
 * 
 */
public class ClosedAndNplAccountFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private final String CA_NPL_CLOSEDACC_ENTITY_NAME = "creditAppNplClosedAcc";

	private FlatFileItemReader closedAndNplAccountsFlatFileItemReader;

	private ICreditAppDao creditAppDao;

	public void setClosedAndNplAccountsFlatFileItemReader(FlatFileItemReader closedAndNplAccountsFlatFileItemReader) {
		this.closedAndNplAccountsFlatFileItemReader = closedAndNplAccountsFlatFileItemReader;
	}

	public FlatFileItemReader getClosedAndNplAccountsFlatFileItemReader() {
		return closedAndNplAccountsFlatFileItemReader;
	}

	public void setCreditAppDao(ICreditAppDao creditAppDao) {
		this.creditAppDao = creditAppDao;
	}

	public ICreditAppDao getCreditAppDao() {
		return creditAppDao;
	}

	protected void doPersistFeedList(List feedList) {
		getCreditAppDao().saveCreditAppNplClosedAccList(CA_NPL_CLOSEDACC_ENTITY_NAME, feedList);
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getClosedAndNplAccountsFlatFileItemReader();
	}

}
