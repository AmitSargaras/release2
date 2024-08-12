package com.integrosys.cms.batch.mimb.collateral;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryUtil;

public class CollateralMarketableFileLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader collateralMarketableFileReader;

	private ICollateralFileDAO collateralFileDAO;

	/**
	 * @param collateralMarketableFileReader the collateralMarketableFileReader
	 *        to set
	 */
	public void setCollateralMarketableFileReader(FlatFileItemReader collateralMarketableFileReader) {
		this.collateralMarketableFileReader = collateralMarketableFileReader;
	}

	/**
	 * @return the collateralMarketableFileReader
	 */
	public FlatFileItemReader getCollateralMarketableFileReader() {
		return collateralMarketableFileReader;
	}

	public ICollateralFileDAO getCollateralFileDAO() {
		return collateralFileDAO;
	}

	public void setCollateralFileDAO(ICollateralFileDAO collateralFileDAO) {
		this.collateralFileDAO = collateralFileDAO;
	}

	protected void doPersistFeedList(List feedList) {
		collateralFileDAO.createCollateralMarketableItems(feedList);

	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return getCollateralMarketableFileReader();
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();

        String categoryCode[] = {"40", "RELATIONSHIP"};
        for (int i=0; i<categoryCode.length; i++)
            CommonCodeEntryUtil.synchronizeCommonCode(categoryCode[i]);
    }

}
