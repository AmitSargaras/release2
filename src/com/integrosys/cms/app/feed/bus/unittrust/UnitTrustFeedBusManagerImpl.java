/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/UnitTrustFeedBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/03
 */
public class UnitTrustFeedBusManagerImpl extends AbstractUnitTrustFeedBusManager {

	public String getUnitTrustFeedEntryEntityName() {
		return IUnitTrustDao.ACTUAL_UNIT_TRUST_FEED_ENTRY_ENTITY_NAME;
	}

	public String getUnitTrustFeedGroupEntityName() {
		return IUnitTrustDao.ACTUAL_UNIT_TRUST_FEED_GROUP_ENTITY_NAME;
	}

	public IUnitTrustFeedGroup updateToWorkingCopy(IUnitTrustFeedGroup workingCopy, IUnitTrustFeedGroup imageCopy)
			throws UnitTrustFeedGroupException {
		// replicate staging copy's feed entries set
		Set replicatedStagingFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "unitTrustFeedEntryID" });

		// synchronize copy between actual and staging
		Set mergedUnitTrustFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStagingFeedEntriesSet, new String[] { "unitTrustFeedEntryRef" },
				new String[] { "unitTrustFeedEntryID", "versionTime" });

		// refresh actual feed entries set using the synchronized set
		workingCopy.getFeedEntriesSet().clear();
		if (mergedUnitTrustFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedUnitTrustFeedEntriesSet);
		}

		return updateUnitTrustFeedGroup(workingCopy);
	}

}
