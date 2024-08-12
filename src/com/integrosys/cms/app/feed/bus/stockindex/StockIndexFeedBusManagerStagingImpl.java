/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/StockIndexFeedBusManagerStagingImpl.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 */
public class StockIndexFeedBusManagerStagingImpl extends AbstractStockIndexFeedBusManager {

	public IStockIndexFeedGroup createStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException {
		group = getStockIndexDao().createStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getStockIndexDao().updateStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);
	}

	public IStockIndexFeedGroup updateStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException {
		group = getStockIndexDao().updateStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getStockIndexDao().updateStockIndexFeedGroup(getStockIndexFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IStockIndexFeedGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "stockIndexFeedEntryRef", Long.class, "stockIndexFeedEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getStockIndexFeedEntryEntityName() {
		return IStockIndexDao.STAGE_STOCK_INDEX_FEED_ENTRY_ENTITY_NAME;
	}

	public String getStockIndexFeedGroupEntityName() {
		return IStockIndexDao.STAGE_STOCK_INDEX_FEED_GROUP_ENTITY_NAME;
	}

	public IStockIndexFeedGroup updateToWorkingCopy(IStockIndexFeedGroup workingCopy, IStockIndexFeedGroup imageCopy)
			throws StockIndexFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented for staging bus manager.");
	}

}
