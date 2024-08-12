package com.integrosys.cms.batch.sibs.customer;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;
import com.integrosys.cms.batch.common.ListRecordsDao;

public class CustomerUpdateFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private final String CUSTOMER_INFO_ENTITY_NAME = "customerSave";

	private FlatFileItemReader updateFlatFileItemReader;

	private ListRecordsDao customerDao;

	public void setUpdateFlatFileItemReader(FlatFileItemReader updateFlatFileItemReader) {
		this.updateFlatFileItemReader = updateFlatFileItemReader;
	}

	public FlatFileItemReader getUpdateFlatFileItemReader() {
		return updateFlatFileItemReader;
	}

	public void setCustomerDao(ListRecordsDao customerDao) {
		this.customerDao = customerDao;
	}

	public ListRecordsDao getCustomerDao() {
		return customerDao;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected void doPersistFeedList(List customerList) {
		getCustomerDao().persist( customerList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getUpdateFlatFileItemReader();

	}
}
