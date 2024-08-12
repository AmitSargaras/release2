package com.integrosys.cms.app.tatdoc.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class TatDocReplicationUtils {
	
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
	public static ITatDoc replicateTatDocForCreateStagingCopy(ITatDoc tatDoc) {
		ITatDoc replicatedGroup = (ITatDoc) ReplicateUtils.replicateObject(tatDoc,
				new String[] { "tatDocID" });
		
		Set replicatedTatDocDraftSet = null;
		if(tatDoc.getDraftListSet()!=null&&tatDoc.getDraftListSet().size()!=0){
			replicatedTatDocDraftSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				tatDoc.getDraftListSet(), new String[] { "draftID" });
			replicatedGroup.setDraftListSet(replicatedTatDocDraftSet);
		}

		return replicatedGroup;
	}
}
