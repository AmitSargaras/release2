package com.integrosys.cms.batch.feeds.vehicle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationProfileSingletonListener;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

/**
 * @author gp loh
 * @author Chong Jun Yong
 * @since 05 Oct 2008
 * 
 */
public class VehicleFeedsLoader extends AbstractDomainAwareBatchFeedLoader {

	private String region;

	private FlatFileItemReader vehicleFeedsFlatFileItemReader;

	private IVehicleDao vehicleDao;

	private ValuationProfileSingletonListener valuationProfileSingletonListener;

	public void setRegion(String region) {
		this.region = region;
	}

	public void setVehicleFeedsFlatFileItemReader(FlatFileItemReader vehicleFeedsFlatFileItemReader) {
		this.vehicleFeedsFlatFileItemReader = vehicleFeedsFlatFileItemReader;
	}

	public void setValuationProfileSingletonListener(ValuationProfileSingletonListener valuationProfileSingletonListener) {
		this.valuationProfileSingletonListener = valuationProfileSingletonListener;
	}

	/**
	 * @param vehicleDao the vehicleDao to set
	 */
	public void setVehicleDao(IVehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
		doRunStoredProcedure();
	}

	protected void preprocess(Object feed) {
		OBVehicle vehicleFeed = (OBVehicle) feed;
		vehicleFeed.setRegion(this.region);
		vehicleFeed.setCompositeID(this.region + vehicleFeed.getMakeOfVehicle().trim()
				+ vehicleFeed.getModelOfVehicle().trim() + vehicleFeed.getYearOfVehicle());
	}

	protected void postprocess(Map context) {
		super.postprocess(context);
		valuationProfileSingletonListener.reloadSingleton(new OBCollateralSubType(ICMSConstant.SECURITY_TYPE_ASSET,
				ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH));
	}

	protected void doPersistFeedList(List feedList) {
		List newList = new ArrayList();
		Iterator itr = feedList.iterator();

		while (itr.hasNext()) {
			IVehicle obv = (IVehicle) itr.next();
			if (!CommonUtil.isEmpty(obv.getMakeOfVehicle())) {
				newList.add(obv);
			}
		}
		this.vehicleDao.saveOrUpdateVehicleHpPriceFeeds(newList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return this.vehicleFeedsFlatFileItemReader;
	}

}
