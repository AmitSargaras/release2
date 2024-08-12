package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class RelationshipMgrReplicationUtils {

	/**
	 * <p>
	 * Replicate Property Index which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IRelationshipMgr replicateRelationshipMgrForCreateStagingCopy(IRelationshipMgr relationshipMgr) {

		IRelationshipMgr replicatedIdx = (IRelationshipMgr) ReplicateUtils.replicateObject(relationshipMgr,
				new String[] { "relationshipMgrId"});

		if(replicatedIdx.getRegion() == null)
			replicatedIdx.setRegion(new OBRegion());
		
		replicatedIdx.getRegion().setIdRegion(relationshipMgr.getRegion().getIdRegion());
		
        return replicatedIdx;
	}
}