package com.integrosys.cms.app.feed.bus.stock;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * <p>
 * Replication utility used for replicating stock to interact with persistent
 * storage
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see com.integrosys.base.techinfra.util.ReplicateUtils
 */
public abstract class StockReplicationUtils {

	/**
	 * <p>
	 * Replicate Stock Feed Group which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 * 
	 * @param stockFeedGroup a bond feed group to be replicated, normally is
	 *        persistence entity.
	 * @return a replicated stock feed group which primary key is not copied,
	 *         ready to be passed to persistence layer for creation
	 */
	public static IStockFeedGroup replicateStockFeedGroupForCreateStagingCopy(IStockFeedGroup stockFeedGroup) {
		
		Set replicatedStockFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				stockFeedGroup.getFeedEntriesSet(), new String[] { "stockFeedEntryID" });

		IStockFeedGroup replicatedGroup = (IStockFeedGroup) ReplicateUtils.replicateObject(stockFeedGroup,
				new String[] { "stockFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedStockFeedEntriesSet);

		return replicatedGroup;
	}
}
