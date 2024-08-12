package com.integrosys.cms.app.feed.bus.gold;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

public class GoldFeedBusManagerStagingImpl extends AbstractGoldFeedBusManager {

	public IGoldFeedGroup createGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException {
		group = getGoldDao().createGoldFeedGroup(getGoldFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getGoldDao().updateGoldFeedGroup(getGoldFeedGroupEntityName(), group);
	}

	public IGoldFeedGroup updateGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException {
		group = getGoldDao().updateGoldFeedGroup(getGoldFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getGoldDao().updateGoldFeedGroup(getGoldFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IGoldFeedGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "goldFeedEntryRef", Long.class, "goldFeedEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getGoldFeedGroupEntityName() {
		return IGoldDao.STAGE_GOLD_FEED_GROUP;
	}

	public IGoldFeedGroup updateToWorkingCopy(IGoldFeedGroup workingCopy, IGoldFeedGroup imageCopy)
			throws GoldFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented staging bus manager");
	}
}
