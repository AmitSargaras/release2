/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedBusManagerStagingImpl.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsFeedBusManagerStagingImpl extends AbstractMutualFundsFeedBusManager {

	public IMutualFundsFeedGroup createMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException {
		group = getMutualFundsDao().createMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getMutualFundsDao().updateMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);
	}

	public IMutualFundsFeedGroup updateMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException {
		group = getMutualFundsDao().updateMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getMutualFundsDao().updateMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IMutualFundsFeedGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "mutualFundsFeedEntryRef", Long.class, "mutualFundsFeedEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getMutualFundsFeedEntryEntityName() {
		return IMutualFundsDao.STAGE_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME;
	}

	public String getMutualFundsFeedGroupEntityName() {
		return IMutualFundsDao.STAGE_MUTUAL_FUNDS_FEED_GROUP_ENTITY_NAME;
	}

	public IMutualFundsFeedGroup updateToWorkingCopy(IMutualFundsFeedGroup workingCopy, IMutualFundsFeedGroup imageCopy)
			throws MutualFundsFeedGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
	}
	public String getMutualFundsFeedEntryFileMapperName() {
		return IMutualFundsDao.STAGE_FILE_MAPPER_ID;
	}
}
