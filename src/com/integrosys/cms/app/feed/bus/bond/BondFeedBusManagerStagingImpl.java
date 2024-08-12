/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedBusManagerStagingImpl.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.bond;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.feed.bus.forex.IForexDao;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 */
public class BondFeedBusManagerStagingImpl extends AbstractBondFeedBusManager {

	public IBondFeedGroup createBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException {
		group = getBondDao().createBondFeedGroup(getBondFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getBondDao().updateBondFeedGroup(getBondFeedGroupEntityName(), group);
	}

	public IBondFeedGroup updateBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException {
		group = getBondDao().updateBondFeedGroup(getBondFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getBondDao().updateBondFeedGroup(getBondFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IBondFeedGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "bondFeedEntryRef", Long.class, "bondFeedEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getBondFeedEntryEntityName() {
		return IBondDao.STAGE_BOND_FEED_ENTRY_ENTITY_NAME;
	}

	public String getBondFeedGroupEntityName() {
		return IBondDao.STAGE_BOND_FEED_GROUP_ENTITY_NAME;
	}

	public IBondFeedGroup updateToWorkingCopy(IBondFeedGroup workingCopy, IBondFeedGroup imageCopy)
			throws BondFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
	}
	
	public String getBondFeedEntryFileMapperName() {
		return IBondDao.STAGE_FILE_MAPPER_ID;
	}
}
