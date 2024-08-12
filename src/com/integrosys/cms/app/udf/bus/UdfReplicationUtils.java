package com.integrosys.cms.app.udf.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public class UdfReplicationUtils {
	/**
		 * <p>
		 * Replicate ExcludedFacility which is ready to create a entity into
		 * persistent storage. Normally to create a staging copy will use this.
		 *
		 * <p>
		 * <b>note</b> must know which field(s) need to be filtered when doing
		 * replication
		 *
		 */
		public static IUdf replicateUdfForCreateStagingCopy(IUdf udf) {

			IUdf replicatedIdx = (IUdf) ReplicateUtils.replicateObject(udf,
					new String[] { "id"});

	        return replicatedIdx;
		}
}
