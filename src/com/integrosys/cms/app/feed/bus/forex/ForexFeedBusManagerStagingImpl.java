/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/ForexFeedBusManagerStagingImpl.java,v 1.2 2003/08/11 06:36:51 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/11
 */
public class ForexFeedBusManagerStagingImpl extends AbstractForexFeedBusManager {

	public IForexFeedGroup createForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException {
		group = getForexDao().createForexFeedGroup(getForexFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getForexDao().updateForexFeedGroup(getForexFeedGroupEntityName(), group);
	}

	public IForexFeedGroup updateForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException {
		group = getForexDao().updateForexFeedGroup(getForexFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getForexDao().updateForexFeedGroup(getForexFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IForexFeedGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "forexFeedEntryRef", Long.class, "forexFeedEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getForexFeedEntryEntityName() {
		return IForexDao.STAGE_FOREX_FEED_ENTRY_ENTITY_NAME;
	}

	public String getForexFeedGroupEntityName() {
		return IForexDao.STAGE_FOREX_FEED_GROUP_ENTITY_NAME;
	}

	public IForexFeedGroup updateToWorkingCopy(IForexFeedGroup workingCopy, IForexFeedGroup imageCopy)
			throws ForexFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented staging bus manager");
	}
	public String getForexFeedEntryFileMapperName() {
		return IForexDao.STAGE_FILE_MAPPER_ID;
	}
	
}
