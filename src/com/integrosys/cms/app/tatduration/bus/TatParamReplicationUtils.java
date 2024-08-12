package com.integrosys.cms.app.tatduration.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class TatParamReplicationUtils {
	
	/**
	 * <p>
	 * Replicate Tat Doc which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 * 
	 * @param goldFeedGroup a Tat Doc to be replicated, normally is
	 *        persistence entity.
	 * @return a replicated Tat Doc which primary key is not copied,
	 *         ready to be passed to persistence layer for creation
	 */
	public static ITatParam replicateTatParamForCreateStagingCopy(ITatParam tatParam)
	{
		ITatParam replicatedGroup = (ITatParam) ReplicateUtils.replicateObject(tatParam, new String[] { "tatParamID" });
		
		Set replicatedTatParamDraftSet = null;
		if(tatParam.getTatParamItemList() != null && !tatParam.getTatParamItemList().isEmpty())
		{
			replicatedTatParamDraftSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				tatParam.getTatParamItemList(), new String[] { "tatParamItemId" });
			
		}

		replicatedGroup.setTatParamItemList(replicatedTatParamDraftSet);
		return replicatedGroup;
	}
	
	public static void copyStagingPrimaryKeyToCmsRefId(ITatParam tatParam)
	{
		// Setting the reference ID
		Set updateDraftListRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey
		(tatParam.getTatParamItemList(), "cmsRefID", Long.class, "tatParamItemId");

		if (tatParam.getTatParamItemList() != null && updateDraftListRefSet != null)
		{
			tatParam.getTatParamItemList().clear();
			tatParam.getTatParamItemList().addAll(updateDraftListRefSet);
		}
	}
}
