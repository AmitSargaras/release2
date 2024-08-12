package com.integrosys.cms.app.otherbranch.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class OtherBankBranchReplicationUtils {

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
	public static IOtherBranch replicateOtherBankBranchForCreateStagingCopy(IOtherBranch otherBranch) {

		IOtherBranch replicatedIdx = (IOtherBranch) ReplicateUtils.replicateObject(otherBranch,
				new String[] { "otherBranchId"});
		
		replicatedIdx.setOtherBankCode(new OBOtherBank());
		replicatedIdx.getOtherBankCode().setId(otherBranch.getOtherBankCode().getId());
		
		if(replicatedIdx.getCity() == null){
        	if(otherBranch.getCity()!=null && !otherBranch.getCity().equals("")){
	        	replicatedIdx.setCity(new OBCity());
				replicatedIdx.getCity().setIdCity(otherBranch.getCity().getIdCity());
        	}	
		}	
		
		if(replicatedIdx.getState() == null){
			if(otherBranch.getState()!=null && !otherBranch.getState().equals("")){
				replicatedIdx.setState(new OBState());
				replicatedIdx.getState().setIdState(otherBranch.getState().getIdState());
			}	
		}	
		
		if(replicatedIdx.getRegion() == null){
			if(otherBranch.getRegion()!=null && !otherBranch.getRegion().equals("")){
				replicatedIdx.setRegion(new OBRegion());
				replicatedIdx.getRegion().setIdRegion(otherBranch.getRegion().getIdRegion());
			}	
		}	
		
		if(replicatedIdx.getCountry() == null){
			if(otherBranch.getCountry()!=null && !otherBranch.getCountry().equals("")){
				replicatedIdx.setCountry(new OBCountry());
				replicatedIdx.getCountry().setIdCountry(otherBranch.getCountry().getIdCountry());
			}	
		}	
		
        return replicatedIdx;
	}
}