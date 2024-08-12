/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/AbstractStockIndexFeedBusManager.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 */
public abstract class AbstractStockIndexFeedBusManager implements IStockIndexFeedBusManager {
	
	private IStockIndexDao stockIndexDao;
	
	public IStockIndexDao getStockIndexDao() {
		return this.stockIndexDao;
	}
	
	public void setStockIndexDao(IStockIndexDao stockIndexDao) {
		this.stockIndexDao = stockIndexDao;
	}

	public IStockIndexFeedGroup getStockIndexFeedGroup(long id) throws StockIndexFeedGroupException {
		return getStockIndexDao().getStockIndexFeedGroupByPrimaryKey(getStockIndexFeedGroupEntityName(), new Long(id));
	}

	public IStockIndexFeedGroup getStockIndexFeedGroup(String groupType, String subType)
			throws StockIndexFeedGroupException {

		return getStockIndexDao().getStockIndexFeedGroupByTypeAndSubType(getStockIndexFeedGroupEntityName(), groupType,
				subType);
	}

	public IStockIndexFeedGroup createStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException {
		return getStockIndexDao().createStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);
	}

	public IStockIndexFeedGroup updateStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException {
		return getStockIndexDao().updateStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);
	}

	public IStockIndexFeedGroup deleteStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException {
		getStockIndexDao().deleteStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);

		return group;
	}

	public IStockIndexFeedEntry getStockIndexFeedEntryByRic(String ric) throws StockIndexFeedEntryException {
		return getStockIndexDao().getStockIndexFeedEntryByRic(getStockIndexFeedEntryEntityName(), ric);
	}

	public abstract String getStockIndexFeedGroupEntityName();

	public abstract String getStockIndexFeedEntryEntityName();

}
