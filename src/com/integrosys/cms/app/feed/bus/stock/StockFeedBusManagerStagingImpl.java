/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockFeedBusManagerStagingImpl.java,v 1.1 2003/08/07 08:34:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.feed.bus.bond.IBondDao;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/07
 */
public class StockFeedBusManagerStagingImpl extends AbstractStockFeedBusManager {

	public IStockFeedGroup createStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException {
		group = getStockDao().createStockFeedGroup(getStockFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getStockDao().updateStockFeedGroup(getStockFeedGroupEntityName(), group);
	}

	public IStockFeedGroup updateStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException {
		group = getStockDao().updateStockFeedGroup(getStockFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getStockDao().updateStockFeedGroup(getStockFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IStockFeedGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "stockFeedEntryRef", Long.class, "stockFeedEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getStockFeedEntryEntityName() {
		return IStockDao.STAGE_STOCK_FEED_ENTRY_ENTITY_NAME;
	}

	public String getStockFeedGroupEntityName() {
		return IStockDao.STAGE_STOCK_FEED_GROUP_ENTITY_NAME;
	}

	public IStockFeedGroup updateToWorkingCopy(IStockFeedGroup workingCopy, IStockFeedGroup imageCopy)
			throws StockFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
	}
	public String getStockFeedEntryFileMapperName() {
		return IStockDao.STAGE_FILE_MAPPER_ID;
	}

}
