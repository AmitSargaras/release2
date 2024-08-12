/**
 *
 */
package com.integrosys.cms.batch.feeds.vehicle;

import java.util.List;

/**
 * <p>
 * Data Access Object interface to be used by vehicle price feed module.
 * 
 * <p>
 * Use {@link #saveOrUpdateVehicleHpPriceFeeds(List)} for bulk insert to gain
 * performance.
 * 
 * @author gp loh
 * @author Chong Jun Yong
 * @date 05.10.08
 * 
 */
public interface IVehicleDao {

	/**
	 * Save or update the vehicleFeed into persistent storage, returned
	 * persisted data.
	 * 
	 * @param vehicle a vehicle feed to be persisted
	 * @return persited vehicle feed
	 */
	public IVehicle saveOrUpdateVehicleHpPriceFeed(IVehicle vehicle);

	/**
	 * Save or update all the vehicle feeds in the list supplied
	 * 
	 * @param vehiclePriceFeedList list of vehicle feeds to be persisted
	 */
	public void saveOrUpdateVehicleHpPriceFeeds(List vehiclePriceFeedList);

}
