package com.integrosys.cms.app.valuationAgency.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;


public abstract class ValuationAgencyReplicationUtils {

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
	public static IValuationAgency replicateValuationAgencyForCreateStagingCopy(
			IValuationAgency valuationAgency) {

		IValuationAgency replicatedIdx = (IValuationAgency) ReplicateUtils
				.replicateObject(valuationAgency, new String[] { "country" ,"state" ,"region","cityTown"});
		if (replicatedIdx.getCountry() == null)
		{
			if(valuationAgency.getCountry()!=null){
        	replicatedIdx.setCountry(new OBCountry());
        	replicatedIdx.getCountry().setIdCountry(valuationAgency.getCountry().getIdCountry());
		}
		}
		/*else{
		   	replicatedIdx.getCountry().setIdCountry(valuationAgency.getCountry().getIdCountry());
			}*/
		
		if (replicatedIdx.getRegion() == null){
			if(valuationAgency.getRegion()!=null){
			replicatedIdx.setRegion(new OBRegion());
        	replicatedIdx.getRegion().setIdRegion(valuationAgency.getRegion().getIdRegion());
			}
		}
		/*else{
	    	replicatedIdx.getRegion().setIdRegion(valuationAgency.getRegion().getIdRegion());
	    		}*/
		
		if (replicatedIdx.getState() == null){
			if(valuationAgency.getState()!=null){
			replicatedIdx.setState(new OBState());        	
        	replicatedIdx.getState().setIdState(valuationAgency.getState().getIdState());
        	}
      	}

		if (replicatedIdx.getCityTown() == null){
			
			if(valuationAgency.getCityTown()!=null){
				replicatedIdx.setCityTown(new OBCity());
			     	replicatedIdx.getCityTown().setIdCity(valuationAgency.getCityTown().getIdCity());
				 	}
     	}
		
        	
	
		
		
		return replicatedIdx;
	}
}