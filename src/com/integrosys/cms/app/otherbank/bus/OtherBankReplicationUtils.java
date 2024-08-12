package com.integrosys.cms.app.otherbank.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class OtherBankReplicationUtils {

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
	public static IOtherBank replicateOtherBankForCreateStagingCopy(IOtherBank otherBank) {

        IOtherBank replicatedIdx = (IOtherBank) ReplicateUtils.replicateObject(otherBank,
				new String[] { "otherBankId"});
        
        if(replicatedIdx.getCity() == null){
        	if(otherBank.getCity()!=null && !otherBank.getCity().equals("")){
	        	replicatedIdx.setCity(new OBCity());
				replicatedIdx.getCity().setIdCity(otherBank.getCity().getIdCity());
        	}	
		}	
		
		if(replicatedIdx.getState() == null){
			if(otherBank.getState()!=null && !otherBank.getState().equals("")){
				replicatedIdx.setState(new OBState());
				replicatedIdx.getState().setIdState(otherBank.getState().getIdState());
			}	
		}	
		
		if(replicatedIdx.getRegion() == null){
			if(otherBank.getRegion()!=null && !otherBank.getRegion().equals("")){
				replicatedIdx.setRegion(new OBRegion());
				replicatedIdx.getRegion().setIdRegion(otherBank.getRegion().getIdRegion());
			}	
		}	
		
		if(replicatedIdx.getCountry() == null){
			if(otherBank.getCountry()!=null && !otherBank.getCountry().equals("")){
				replicatedIdx.setCountry(new OBCountry());
				replicatedIdx.getCountry().setIdCountry(otherBank.getCountry().getIdCountry());
			}	
		}	

        return replicatedIdx;
	}
}