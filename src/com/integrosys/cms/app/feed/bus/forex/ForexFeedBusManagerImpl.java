/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/ForexFeedBusManagerImpl.java,v 1.2 2003/08/11 06:36:51 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.creditApproval.bus.ICreditApprovalDao;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/11
 */
public class ForexFeedBusManagerImpl extends AbstractForexFeedBusManager {

	public String getForexFeedEntryEntityName() {
		return IForexDao.ACTUAL_FOREX_FEED_ENTRY_ENTITY_NAME;
	}

	public String getForexFeedGroupEntityName() {
		return IForexDao.ACTUAL_FOREX_FEED_GROUP_ENTITY_NAME;
	}

	public IForexFeedGroup updateToWorkingCopy(IForexFeedGroup workingCopy, IForexFeedGroup imageCopy)
			throws ForexFeedGroupException {
		Set replicatedStageForexFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "forexFeedEntryID" });

		Set mergedForexFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageForexFeedEntriesSet, new String[] { "forexFeedEntryRef" },
				new String[] { "forexFeedEntryID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedForexFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedForexFeedEntriesSet);
		}

		return updateForexFeedGroup(workingCopy);
	}

	
	public String getForexFeedEntryFileMapperName() {
        return IForexDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

}
