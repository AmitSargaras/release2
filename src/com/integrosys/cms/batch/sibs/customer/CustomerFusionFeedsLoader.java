package com.integrosys.cms.batch.sibs.customer;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

/**
 * Batch Job for customer fusion feeds
 * 
 * @author Chong Jun Yong
 * 
 */
public class CustomerFusionFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private final String CUSTOMER_FUSION_ENTITY_NAME = "customerFusion";

	private ICustomerDao customerDao;

	private FlatFileItemReader fusionFlatFileItemReader;

	public void setFusionFlatFileItemReader(FlatFileItemReader fusionFlatFileItemReader) {
		this.fusionFlatFileItemReader = fusionFlatFileItemReader;
	}

	public FlatFileItemReader getFusionFlatFileItemReader() {
		return fusionFlatFileItemReader;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	protected void doPersistFeedList(List feedList) {
		getCustomerDao().fuseCustomerInfo(CUSTOMER_FUSION_ENTITY_NAME, feedList);
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getFusionFlatFileItemReader();
	}

}
