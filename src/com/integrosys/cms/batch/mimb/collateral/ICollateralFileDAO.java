package com.integrosys.cms.batch.mimb.collateral;

import java.util.List;

/**
 * <p>
 * Data Access Object interface to be used by MIMB collateral module.
 * 
 * <p>
 * Use {@link #createCollateralCashItems(List)} and
 * {@link #createCollateralMarketableItems(List)} for bulk insert of collateral
 * feeds to gain performance
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 */
public interface ICollateralFileDAO {

	/**
	 * Create marketable security feed into persistent storage, returned back
	 * persisted entity.
	 * 
	 * @param collateralMarketableItem a marketable sec feed
	 * @return persisted marketable sec feed
	 */
	public ICollateralMarketable createCollateralMarketableItem(ICollateralMarketable collateralMarketableItem);

	/**
	 * Create cash feed into persistent storage, returned back persisted entity.
	 * 
	 * @param cashItem a cash feed
	 * @return persisted cash feed
	 */
	public ICash createCollateralCashItem(ICash cashItem);

	/**
	 * Create all the market sec feed in the list supplied, this will use the
	 * bulk insert facility of JDBC
	 * 
	 * @param marketSecFeedList list of market sec feeds to be persisted
	 */
	public void createCollateralMarketableItems(List marketSecFeedList);

	/**
	 * Create all the cash feed in the list supplied, this will use the bulk
	 * insert facility of JDBC
	 * 
	 * @param cashFeedList list of cash feeds to be persisted
	 */
	public void createCollateralCashItems(List cashFeedList);

}
