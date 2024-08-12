package com.integrosys.cms.app.geography.region.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public class RegionReplicationUtils {

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
	public static IRegion replicateRegionForCreateStagingCopy(IRegion region) {

		IRegion replicatedIdx = (IRegion) ReplicateUtils.replicateObject(region,
				new String[] { "id"});

		if(replicatedIdx.getCountryId() == null)
			replicatedIdx.setCountryId(new OBCountry());
		
//		replicatedIdx.getCountryId().setId(region.getCountryId().getId());
		
		replicatedIdx.getCountryId().setIdCountry(region.getCountryId().getIdCountry());
		
        return replicatedIdx;
	}
}

