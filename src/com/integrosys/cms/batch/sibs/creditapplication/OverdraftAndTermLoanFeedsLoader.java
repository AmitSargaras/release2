package com.integrosys.cms.batch.sibs.creditapplication;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;
import com.integrosys.cms.batch.common.ListRecordsDao;

/**
 * Batch Job for Overdraft and Term Loan Feeds
 * 
 * @author Loh Gek Phuang
 * @author Chong Jun Yong
 * 
 */
public class OverdraftAndTermLoanFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private final String CA_OD_TL_ENTITY_NAME = "creditAppODTL";

	private FlatFileItemReader overdraftAndTermLoanFlatFileItemReader;

	public ListRecordsDao creditAppDao;

	public void setOverdraftAndTermLoanFlatFileItemReader(FlatFileItemReader overdraftAndTermLoanFlatFileItemReader) {
		this.overdraftAndTermLoanFlatFileItemReader = overdraftAndTermLoanFlatFileItemReader;
	}

	public FlatFileItemReader getOverdraftAndTermLoanFlatFileItemReader() {
		return overdraftAndTermLoanFlatFileItemReader;
	}

	public void setCreditAppDao(ListRecordsDao dao) {
		this.creditAppDao = dao;
	}

	public ListRecordsDao getCreditAppDao() {
		return creditAppDao;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRebindPackage();
		doRunStoredProcedure();
	}

	protected void doPersistFeedList(List feedList) {
		getCreditAppDao().persist(feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getOverdraftAndTermLoanFlatFileItemReader();
	}
}
