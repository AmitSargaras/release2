package com.integrosys.cms.batch.feeds.stock;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationProfileSingletonListener;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

public class StockPriceFileLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader stockPriceFileReader;

	private IStockPriceFileDAO stockPriceFileDAO;

	private ValuationProfileSingletonListener valuationProfileSingletonListener;

	public void setStockPriceFileReader(FlatFileItemReader stockPriceFileReader) {
		this.stockPriceFileReader = stockPriceFileReader;
	}

	public void setStockPriceFileDAO(IStockPriceFileDAO stockPriceFileDAO) {
		this.stockPriceFileDAO = stockPriceFileDAO;
	}

	public void setValuationProfileSingletonListener(ValuationProfileSingletonListener valuationProfileSingletonListener) {
		this.valuationProfileSingletonListener = valuationProfileSingletonListener;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected void postprocess(Map context) {
		super.postprocess(context);
		this.valuationProfileSingletonListener.reloadSingleton(new OBCollateralSubType(
				ICMSConstant.SECURITY_TYPE_MARKETABLE, null));
	}

	protected void doPersistFeedList(List feedList) {
		this.stockPriceFileDAO.createStockPriceItems(feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return this.stockPriceFileReader;
	}

}
