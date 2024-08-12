package com.integrosys.cms.app.segmentWiseEmail.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;

public abstract class SegmentWiseEmailReplicationUtils {

	/**
	 * <p>
	 * Replicate SegmentWiseEmail which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static ISegmentWiseEmail replicateSegmentWiseEmailForCreateStagingCopy(ISegmentWiseEmail segmentWiseEmail) {

		ISegmentWiseEmail replicatedIdx = (ISegmentWiseEmail) ReplicateUtils.replicateObject(segmentWiseEmail,
				new String[] { "id"});

        return replicatedIdx;
	}
}
