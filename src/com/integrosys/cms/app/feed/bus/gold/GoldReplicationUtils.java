package com.integrosys.cms.app.feed.bus.gold;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;

public abstract class GoldReplicationUtils {
	
	/**
	 * <p>
	 * Replicate Gold Feed Group which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 * 
	 * @param goldFeedGroup a gold feed group to be replicated, normally is
	 *        persistence entity.
	 * @return a replicated gold feed group which primary key is not copied,
	 *         ready to be passed to persistence layer for creation
	 */
	public static IGoldFeedGroup replicateGoldFeedGroupForCreateStagingCopy(IGoldFeedGroup goldFeedGroup) {
		Set replicatedGoldFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				goldFeedGroup.getFeedEntriesSet(), new String[] { "goldFeedEntryID" });

		IGoldFeedGroup replicatedGroup = (IGoldFeedGroup) ReplicateUtils.replicateObject(goldFeedGroup,
				new String[] { "goldFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedGoldFeedEntriesSet);

		return replicatedGroup;
	}
}
