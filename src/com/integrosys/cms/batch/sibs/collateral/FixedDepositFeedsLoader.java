/**
 * 
 */
package com.integrosys.cms.batch.sibs.collateral;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

/**
 * @author Loh Gek Phuang
 * @author Chong Jun Yong
 * @since 02 Oct 2008
 * 
 */
public class FixedDepositFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private final String COLL_FD_ENTITY_NAME = "collateralFD";

	private FlatFileItemReader fixedDepositFlatFileReader;

	public ICollateralDao collateralDao;

	public void setFixedDepositFlatFileReader(FlatFileItemReader fixedDepositFlatFileReader) {
		this.fixedDepositFlatFileReader = fixedDepositFlatFileReader;
	}

	public FlatFileItemReader getFixedDepositFlatFileReader() {
		return fixedDepositFlatFileReader;
	}

	public void setCollateralDao(ICollateralDao dao) {
		this.collateralDao = dao;
	}

	public ICollateralDao getCollateralDao() {
		return collateralDao;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected void doPersistFeedList(List collateralList) {
		getCollateralDao().saveCollateralFDList(COLL_FD_ENTITY_NAME, collateralList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getFixedDepositFlatFileReader();
	}
}
