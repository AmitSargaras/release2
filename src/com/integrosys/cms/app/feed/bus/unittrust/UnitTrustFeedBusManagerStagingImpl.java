package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/08
 */
public class UnitTrustFeedBusManagerStagingImpl extends AbstractUnitTrustFeedBusManager {

	public IUnitTrustFeedGroup createUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException {
		group = getUnitTrustDao().createUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getUnitTrustDao().updateUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);
	}

	public IUnitTrustFeedGroup updateUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException {
		group = getUnitTrustDao().updateUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getUnitTrustDao().updateUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IUnitTrustFeedGroup group) {
		Set updateFeedEntriesSetWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "unitTrustFeedEntryRef", Long.class, "unitTrustFeedEntryID");

		group.getFeedEntriesSet().clear();
		if (updateFeedEntriesSetWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateFeedEntriesSetWithFeedEntryRefSet);
		}
	}

	public String getUnitTrustFeedEntryEntityName() {
		return IUnitTrustDao.STAGE_UNIT_TRUST_FEED_ENTRY_ENTITY_NAME;
	}

	public String getUnitTrustFeedGroupEntityName() {
		return IUnitTrustDao.STAGE_UNIT_TRUST_FEED_GROUP_ENTITY_NAME;
	}

	public IUnitTrustFeedGroup updateToWorkingCopy(IUnitTrustFeedGroup workingCopy, IUnitTrustFeedGroup imageCopy)
			throws UnitTrustFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' is not implemented yet for staging bus manager.");
	}

}
