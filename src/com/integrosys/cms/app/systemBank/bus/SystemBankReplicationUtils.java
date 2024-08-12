package com.integrosys.cms.app.systemBank.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class SystemBankReplicationUtils {

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
	public static ISystemBank replicateSystemBankForCreateStagingCopy(ISystemBank systemBank) {

       
          
      

        ISystemBank replicatedIdx = (ISystemBank) ReplicateUtils.replicateObject(systemBank,
				new String[] { "systemBankId","country","state","region","cityTown"});
        if(systemBank.getCountry()!=null){
        if (replicatedIdx.getCountry() == null)
        	replicatedIdx.setCountry(new com.integrosys.cms.app.geography.country.bus.OBCountry());

        replicatedIdx.getCountry().setIdCountry(systemBank.getCountry().getIdCountry());
        }
        if(systemBank.getState()!=null){
        if (replicatedIdx.getState() == null)
        	replicatedIdx.setState(new OBState());

        replicatedIdx.getState().setIdState(systemBank.getState().getIdState());
        }
        if(systemBank.getRegion()!=null){
        if (replicatedIdx.getRegion() == null)
        	replicatedIdx.setRegion(new OBRegion());

        replicatedIdx.getRegion().setIdRegion(systemBank.getRegion().getIdRegion());
        }
        if(systemBank.getCityTown()!=null){
        if (replicatedIdx.getCityTown() == null)
        	replicatedIdx.setCityTown(new OBCity());

        replicatedIdx.getCityTown().setIdCity(systemBank.getCityTown().getIdCity());
        }
        

        return replicatedIdx;
	}
}