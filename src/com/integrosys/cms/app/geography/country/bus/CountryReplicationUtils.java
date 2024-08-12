package com.integrosys.cms.app.geography.country.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public class CountryReplicationUtils {

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
	public static ICountry replicateCountryForCreateStagingCopy(ICountry country) {

		ICountry replicatedIdx = (ICountry) ReplicateUtils.replicateObject(country,
				new String[] { "id"});

        return replicatedIdx;
	}
}
