/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
* @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsFeedBusManagerImpl extends AbstractMutualFundsFeedBusManager {

	public String getMutualFundsFeedEntryEntityName() {
		return IMutualFundsDao.ACTUAL_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME;
	}

	public String getMutualFundsFeedGroupEntityName() {
		return IMutualFundsDao.ACTUAL_MUTUAL_FUNDS_FEED_GROUP_ENTITY_NAME;
	}

	public IMutualFundsFeedGroup updateToWorkingCopy(IMutualFundsFeedGroup workingCopy, IMutualFundsFeedGroup imageCopy)
			throws MutualFundsFeedGroupException {
		Set replicatedStageMutualFundsFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "mutualFundsFeedEntryID" });

		Set mergedMutualFundsFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageMutualFundsFeedEntriesSet, new String[] { "mutualFundsFeedEntryRef" },
				new String[] { "mutualFundsFeedEntryID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedMutualFundsFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedMutualFundsFeedEntriesSet);
		}

		return updateMutualFundsFeedGroup(workingCopy);
	}
	public String getMutualFundsFeedEntryFileMapperName() {
		return IMutualFundsDao.STAGE_FILE_MAPPER_ID;
	}
}
