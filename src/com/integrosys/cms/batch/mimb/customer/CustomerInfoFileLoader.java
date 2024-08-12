package com.integrosys.cms.batch.mimb.customer;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryUtil;

public class CustomerInfoFileLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader customerInfoFileReader;

	private ICustomerInfoDAO customerInfoFileDAO;

	/**
	 * @param customerInfoFileReader the customerInfoFileReader to set
	 */
	public void setCustomerInfoFileReader(FlatFileItemReader customerInfoFileReader) {
		this.customerInfoFileReader = customerInfoFileReader;
	}

	/**
	 * @return the customerInfoFileReader
	 */
	public FlatFileItemReader getCustomerInfoFileReader() {
		return customerInfoFileReader;
	}

	public ICustomerInfoDAO getCustomerInfoFileDAO() {
		return customerInfoFileDAO;
	}

	public void setCustomerInfoFileDAO(ICustomerInfoDAO customerInfoFileDAO) {
		this.customerInfoFileDAO = customerInfoFileDAO;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();

        String categoryCode[] = {"8", "56"};
        for (int i=0; i<categoryCode.length; i++)
            CommonCodeEntryUtil.synchronizeCommonCode(categoryCode[i]);
    }

	protected void doPersistFeedList(List feedList) {
		customerInfoFileDAO.createCustomerInfoItems(feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return customerInfoFileReader;
	}

}
