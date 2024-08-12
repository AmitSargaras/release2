package com.integrosys.cms.app.feed.bus.forex;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * <p>
 * Replication utility used for replicating Forex to interact with persistent
 * storage
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see com.integrosys.base.techinfra.util.ReplicateUtils
 */
public abstract class ForexReplicationUtils {

	/**
	 * <p>
	 * Replicate Forex Feed Group which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 * 
	 * @param forexFeedGroup a forex feed group to be replicated, normally is
	 *        persistence entity.
	 * @return a replicated forex feed group which primary key is not copied,
	 *         ready to be passed to persistence layer for creation
	 */
	public static IForexFeedGroup replicateForexFeedGroupForCreateStagingCopy(IForexFeedGroup forexFeedGroup) {
		Set replicatedForexFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				forexFeedGroup.getFeedEntriesSet(), new String[] { "forexFeedEntryID" });

		IForexFeedGroup replicatedGroup = (IForexFeedGroup) ReplicateUtils.replicateObject(forexFeedGroup,
				new String[] { "forexFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedForexFeedEntriesSet);

		return replicatedGroup;
	}
}
