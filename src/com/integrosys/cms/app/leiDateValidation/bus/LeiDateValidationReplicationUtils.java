package com.integrosys.cms.app.leiDateValidation.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;

public abstract class LeiDateValidationReplicationUtils {
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
	public static ILeiDateValidation replicateLeiDateValidationForCreateStagingCopy(ILeiDateValidation leiDateValidation) {

		ILeiDateValidation replicatedIdx = (ILeiDateValidation) ReplicateUtils.replicateObject(leiDateValidation,
				new String[] { "id"});

        return replicatedIdx;
	}
}

