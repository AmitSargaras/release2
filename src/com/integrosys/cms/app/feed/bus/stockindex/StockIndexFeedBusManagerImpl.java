/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/StockIndexFeedBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/03 03:27:23 $ Tag: $Name: $
 */
public class StockIndexFeedBusManagerImpl extends AbstractStockIndexFeedBusManager {

	public String getStockIndexFeedEntryEntityName() {
		return IStockIndexDao.ACTUAL_STOCK_INDEX_FEED_ENTRY_ENTITY_NAME;
	}

	public String getStockIndexFeedGroupEntityName() {
		return IStockIndexDao.ACTUAL_STOCK_INDEX_FEED_GROUP_ENTITY_NAME;
	}

	public IStockIndexFeedGroup updateToWorkingCopy(IStockIndexFeedGroup workingCopy, IStockIndexFeedGroup imageCopy)
			throws StockIndexFeedGroupException {
		Set replicatedStageStockIndexFeedEntriesSet = (Set) ReplicateUtils
				.replicateCollectionObjectWithSpecifiedImplClass(imageCopy.getFeedEntriesSet(),
						new String[] { "stockIndexFeedEntryID" });

		Set mergedStockIndexFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
				workingCopy.getFeedEntriesSet(), replicatedStageStockIndexFeedEntriesSet,
				new String[] { "stockIndexFeedEntryRef" }, new String[] { "stockIndexFeedEntryID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedStockIndexFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedStockIndexFeedEntriesSet);
		}

		return updateStockIndexFeedGroup(workingCopy);
	}

}
