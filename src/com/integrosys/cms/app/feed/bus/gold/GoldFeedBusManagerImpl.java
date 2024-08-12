package com.integrosys.cms.app.feed.bus.gold;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

public class GoldFeedBusManagerImpl extends AbstractGoldFeedBusManager {

	public String getGoldFeedGroupEntityName() {
		return IGoldDao.ACTUAL_GOLD_FEED_GROUP;
	}

	public IGoldFeedGroup updateToWorkingCopy(IGoldFeedGroup workingCopy, IGoldFeedGroup imageCopy)
			throws GoldFeedGroupException {
		Set replicatedStageGoldFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "goldFeedEntryID" });

		Set mergedGoldFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageGoldFeedEntriesSet, new String[] { "goldFeedEntryRef" },
				new String[] { "goldFeedEntryID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedGoldFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedGoldFeedEntriesSet);
		}

		return updateGoldFeedGroup(workingCopy);
	}

}
