package com.integrosys.cms.app.partygroup.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * Created by IntelliJ IDEA. Replication utility used for replicating stock to
 * interact with persistent
 * 
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class PartyGroupReplicationUtils {

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
	public static IPartyGroup replicatePartyGroupForCreateStagingCopy(
			IPartyGroup PartyGroup) {

		IPartyGroup replicatedIdx = (IPartyGroup) ReplicateUtils
				.replicateObject(PartyGroup, new String[] { "PartyGroupId" });

		return replicatedIdx;
	}
}