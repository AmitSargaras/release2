package com.integrosys.cms.app.geography.state.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public class StateReplicationUtils {

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
	public static IState replicateStateForCreateStagingCopy(IState state) {

		IState replicatedIdx = (IState) ReplicateUtils.replicateObject(state,
				new String[] { "id"});

		if(replicatedIdx.getRegionId() == null)
			replicatedIdx.setRegionId(new OBRegion());
	
		replicatedIdx.getRegionId().setIdRegion(state.getRegionId().getIdRegion());
		
		/*if(replicatedIdx.getCountryId() == null)
			replicatedIdx.setCountryId(new OBCountry());
		
	
		replicatedIdx.getCountryId().setId(state.getCountryId().getId());
		replicatedIdx.getRegionId().setId(state.getRegionId().getId());
		
		replicatedIdx.getCountryId().setIdCountry(state.getCountryId().getIdCountry());*/
		
        return replicatedIdx;
	}
}
