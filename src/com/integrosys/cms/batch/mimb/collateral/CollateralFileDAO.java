package com.integrosys.cms.batch.mimb.collateral;

import java.util.List;

/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


/**
 * Implementation of {@link ICollateralFileDAO} using hibernate persistence
 * framework.
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public class CollateralFileDAO extends HibernateDaoSupport implements ICollateralFileDAO {

	public ICollateralMarketable createCollateralMarketableItem(ICollateralMarketable collateralMarketableItem) {
		Long key = (Long) getHibernateTemplate().save(collateralMarketableItem);
		collateralMarketableItem.setTempID(key.longValue());
		return collateralMarketableItem;
	}

	public ICash createCollateralCashItem(ICash cashItem) {
		Long key = (Long) getHibernateTemplate().save(cashItem);
		cashItem.setTempID(key.longValue());
		return cashItem;
	}

	//updating the code to support hibernate 4 jars
	/*public void createCollateralMarketableItems(List marketSecFeedList) {
		getHibernateTemplate().saveOrUpdateAll(marketSecFeedList);
	}

	public void createCollateralCashItems(List cashFeedList) {
		getHibernateTemplate().saveOrUpdateAll(cashFeedList);
	}*/
	
	public void createCollateralMarketableItems(List marketSecFeedList) {
		getHibernateTemplate().saveOrUpdate(marketSecFeedList);
	}

	public void createCollateralCashItems(List cashFeedList) {
		getHibernateTemplate().saveOrUpdate(cashFeedList);
	}
}