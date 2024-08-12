package com.integrosys.cms.app.feed.bus.bond;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * <p>
 * Replication utility used for replicating bond to interact with persistent
 * storage
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see com.integrosys.base.techinfra.util.ReplicateUtils
 */
public abstract class BondReplicationUtils {

	/**
	 * <p>
	 * Replicate Bond Feed Group which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 * 
	 * @param bondFeedGroup a bond feed group to be replicated, normally is
	 *        persistence entity.
	 * @return a replicated bond feed group which primary key is not copied,
	 *         ready to be passed to persistence layer for creation
	 */
	public static IBondFeedGroup replicateBondFeedGroupForCreateStagingCopy(IBondFeedGroup bondFeedGroup) {
		Set replicatedBondFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				bondFeedGroup.getFeedEntriesSet(), new String[] { "bondFeedEntryID" });

		IBondFeedGroup replicatedGroup = (IBondFeedGroup) ReplicateUtils.replicateObject(bondFeedGroup,
				new String[] { "bondFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedBondFeedEntriesSet);

		return replicatedGroup;
	}
}
