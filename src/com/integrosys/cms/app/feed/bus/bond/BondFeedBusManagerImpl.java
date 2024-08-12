/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.bond;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.creditApproval.bus.ICreditApprovalDao;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/03
 */
public class BondFeedBusManagerImpl extends AbstractBondFeedBusManager {

	public String getBondFeedEntryEntityName() {
		return IBondDao.ACTUAL_BOND_FEED_ENTRY_ENTITY_NAME;
	}

	public String getBondFeedGroupEntityName() {
		return IBondDao.ACTUAL_BOND_FEED_GROUP_ENTITY_NAME;
	}

	public IBondFeedGroup updateToWorkingCopy(IBondFeedGroup workingCopy, IBondFeedGroup imageCopy)
			throws BondFeedGroupException {
		Set replicatedStageBondFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "bondFeedEntryID" });

		Set mergedBondFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageBondFeedEntriesSet, new String[] { "bondFeedEntryRef" },
				new String[] { "bondFeedEntryID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedBondFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedBondFeedEntriesSet);
		}

		return updateBondFeedGroup(workingCopy);
	}
	public String getBondFeedEntryFileMapperName() {
        return IBondDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }
}
