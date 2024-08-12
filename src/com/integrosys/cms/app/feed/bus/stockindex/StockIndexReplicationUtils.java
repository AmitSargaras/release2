package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * <p>
 * Replication utility used for replicating Stock Index to interact with
 * persistent storage
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see com.integrosys.base.techinfra.util.ReplicateUtils
 */
public abstract class StockIndexReplicationUtils {

	/**
	 * <p>
	 * Replicate Unit Trust Feed Group which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filter when doing
	 * replication
	 * 
	 * @param stockIndexFeedGroup a stock index feed group to be replicated,
	 *        normally is persistence entity.
	 * @return a replicated stock index feed group which primary key is not
	 *         copied, ready to be passed to persistence layer for creation
	 */
	public static IStockIndexFeedGroup replicateStockIndexFeedGroupForCreateStagingCopy(
			IStockIndexFeedGroup stockIndexFeedGroup) {
		Set replicatedUnitTrustFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				stockIndexFeedGroup.getFeedEntriesSet(), new String[] { "stockIndexFeedEntryID" });

		IStockIndexFeedGroup replicatedGroup = (IStockIndexFeedGroup) ReplicateUtils.replicateObject(
				stockIndexFeedGroup, new String[] { "stockIndexFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedUnitTrustFeedEntriesSet);

		return replicatedGroup;
	}
}
