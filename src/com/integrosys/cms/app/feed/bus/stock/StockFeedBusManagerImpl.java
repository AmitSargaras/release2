/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockFeedBusManagerImpl.java,v 1.3 2003/09/18 07:25:31 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/18
 */
public class StockFeedBusManagerImpl extends AbstractStockFeedBusManager {

	public String getStockFeedEntryEntityName() {
		return IStockDao.ACTUAL_STOCK_FEED_ENTRY_ENTITY_NAME;
	}

	public String getStockFeedGroupEntityName() {
		return IStockDao.ACTUAL_STOCK_FEED_GROUP_ENTITY_NAME;
	}

	public IStockFeedGroup updateToWorkingCopy(IStockFeedGroup workingCopy, IStockFeedGroup imageCopy)
			throws StockFeedGroupException {
		Set replicatedStageStockFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "stockFeedEntryID" });

		Set mergedStockFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageStockFeedEntriesSet, new String[] { "stockFeedEntryRef" },
				new String[] { "stockFeedEntryID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedStockFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedStockFeedEntriesSet);
		}

		return updateStockFeedGroup(workingCopy);
	}
	public String getStockFeedEntryFileMapperName() {
        return IStockDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

}
