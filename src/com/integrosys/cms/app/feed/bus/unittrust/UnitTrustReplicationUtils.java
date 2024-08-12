package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * <p>
 * Replication utility used for replicating Unit Trust to interact with
 * persistent storage
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see com.integrosys.base.techinfra.util.ReplicateUtils
 */
public abstract class UnitTrustReplicationUtils {

	/**
	 * <p>
	 * Replicate Unit Trust Feed Group which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filter when doing
	 * replication
	 * 
	 * @param unitTrustFeedGroup a unit trust feed group to be replicated,
	 *        normally is persistence entity.
	 * @return a replicated unit trust feed group which primary key is not
	 *         copied, ready to be passed to persistence layer for creation
	 */
	public static IUnitTrustFeedGroup replicateUnitTrustFeedGroupForCreateStagingCopy(
			IUnitTrustFeedGroup unitTrustFeedGroup) {
		Set replicatedUnitTrustFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				unitTrustFeedGroup.getFeedEntriesSet(), new String[] { "unitTrustFeedEntryID" });

		IUnitTrustFeedGroup replicatedGroup = (IUnitTrustFeedGroup) ReplicateUtils.replicateObject(unitTrustFeedGroup,
				new String[] { "unitTrustFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedUnitTrustFeedEntriesSet);

		return replicatedGroup;
	}
}
