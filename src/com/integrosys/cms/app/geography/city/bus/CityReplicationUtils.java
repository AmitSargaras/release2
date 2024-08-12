package com.integrosys.cms.app.geography.city.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public class CityReplicationUtils {

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
	public static ICity replicateCityForCreateStagingCopy(ICity city) {

		ICity replicatedIdx = (ICity) ReplicateUtils.replicateObject(city,
				new String[] { "id"});

		if(replicatedIdx.getStateId() == null)
			replicatedIdx.setStateId(new OBState());
		
		replicatedIdx.getStateId().setIdState(city.getStateId().getIdState());
		
		/*if(replicatedIdx.getCountryId() == null)
			replicatedIdx.setCountryId(new OBCountry());
		
		if(replicatedIdx.getRegionId() == null)
			replicatedIdx.setRegionId(new OBRegion());
		
		replicatedIdx.getCountryId().setId(city.getCountryId().getId());
		replicatedIdx.getStateId().setId(city.getStateId().getId());
		replicatedIdx.getRegionId().setId(city.getRegionId().getId());
		
		replicatedIdx.getCountryId().setIdCountry(city.getCountryId().getIdCountry());		
		replicatedIdx.getRegionId().setIdRegion(city.getRegionId().getIdRegion());*/		
        return replicatedIdx;
	}
}
