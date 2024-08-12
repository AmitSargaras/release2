package com.integrosys.cms.batch.feeds.stock;

import java.util.List;

/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
/**
 * Implementation of {@link IStockPriceFileDAO} using hibernate persistence
 * framework.
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 */
public class StockPriceFileDAO extends HibernateDaoSupport implements IStockPriceFileDAO {

	public IStockPrice createStockPriceItem(IStockPrice stockPriceItem) {

		Long key = (Long) getHibernateTemplate().save(stockPriceItem);
		stockPriceItem.setTempID(key.longValue());
		return stockPriceItem;

	}
	/*updating the code to support hibernate 4 jars
	public void createStockPriceItems(List stockPriceFeedList) {
		getHibernateTemplate().saveOrUpdateAll(stockPriceFeedList);
	}*/
	public void createStockPriceItems(List stockPriceFeedList) {
		getHibernateTemplate().saveOrUpdate(stockPriceFeedList);
	}
}