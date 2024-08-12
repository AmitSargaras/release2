package com.integrosys.cms.batch.sema;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

public class FacilityFileLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader facilityFileReader;

	private IFacilityFileDAO facilityFileDAO;

	/**
	 * @param facilityFileReader the facilityFileReader to set
	 */
	public void setFacilityFileReader(FlatFileItemReader facilityFileReader) {
		this.facilityFileReader = facilityFileReader;
	}

	/**
	 * @return the facilityFileReader
	 */
	public FlatFileItemReader getFacilityFileReader() {
		return facilityFileReader;
	}

	public IFacilityFileDAO getFacilityFileDAO() {
		return facilityFileDAO;
	}

	public void setFacilityFileDAO(IFacilityFileDAO facilityFileDAO) {
		this.facilityFileDAO = facilityFileDAO;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected void doPersistFeedList(List feedList) {
		facilityFileDAO.createFacilityFileItems(feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return facilityFileReader;
	}

}
