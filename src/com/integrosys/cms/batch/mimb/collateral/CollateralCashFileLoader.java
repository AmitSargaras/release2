package com.integrosys.cms.batch.mimb.collateral;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryUtil;

public class CollateralCashFileLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader collateralCashFileReader;

	private ICollateralFileDAO collateralFileDAO;

	/**
	 * @param collateralCashFileReader the collateralCashFileReader to set
	 */
	public void setCollateralCashFileReader(FlatFileItemReader collateralCashFileReader) {
		this.collateralCashFileReader = collateralCashFileReader;
	}

	/**
	 * @return the collateralCashFileReader
	 */
	public FlatFileItemReader getCollateralCashFileReader() {
		return collateralCashFileReader;
	}

	public ICollateralFileDAO getCollateralFileDAO() {
		return collateralFileDAO;
	}

	public void setCollateralFileDAO(ICollateralFileDAO collateralFileDAO) {
		this.collateralFileDAO = collateralFileDAO;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();

        String categoryCode[] = {"40"};
        for (int i=0; i<categoryCode.length; i++)
            CommonCodeEntryUtil.synchronizeCommonCode(categoryCode[i]);
    }

	protected void doPersistFeedList(List feedList) {
		collateralFileDAO.createCollateralCashItems(feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return collateralCashFileReader;
	}

}
