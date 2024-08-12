package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * <p>
 * Replication utility used for replicating bond to interact with persistent
 * storage
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public abstract class MutualFundsReplicationUtils {

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
	public static IMutualFundsFeedGroup replicateMutualFundsFeedGroupForCreateStagingCopy(IMutualFundsFeedGroup mutualFundsFeedGroup) {
		Set replicatedMutualFundsFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				mutualFundsFeedGroup.getFeedEntriesSet(), new String[] { "mutualFundsFeedEntryID" });

		IMutualFundsFeedGroup replicatedGroup = (IMutualFundsFeedGroup) ReplicateUtils.replicateObject(mutualFundsFeedGroup,
				new String[] { "mutualFundsFeedGroupID" });

		replicatedGroup.setFeedEntriesSet(replicatedMutualFundsFeedEntriesSet);

		return replicatedGroup;
	}
}
