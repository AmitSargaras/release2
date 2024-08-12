package com.integrosys.cms.app.valuationAmountAndRating.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class ValuationAmountAndRatingReplicationUtils {

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
	public static IValuationAmountAndRating replicateValuationAmountAndRatingForCreateStagingCopy(IValuationAmountAndRating valuationAmountAndRating) {

		IValuationAmountAndRating replicatedIdx = (IValuationAmountAndRating) ReplicateUtils.replicateObject(valuationAmountAndRating,
				new String[] { "id"});

        return replicatedIdx;
	}
}
