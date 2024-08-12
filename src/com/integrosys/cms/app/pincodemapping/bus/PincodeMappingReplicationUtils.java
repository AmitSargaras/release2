package com.integrosys.cms.app.pincodemapping.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.state.bus.OBState;

public abstract class PincodeMappingReplicationUtils {

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
	public static IPincodeMapping replicatePincodeMappingForCreateStagingCopy(
			IPincodeMapping PincodeMapping) {

		IPincodeMapping replicatedIdx = (IPincodeMapping) ReplicateUtils
				.replicateObject(PincodeMapping, new String[] { "PincodeMappingId" });

		if(replicatedIdx.getStateId() == null){
			if(PincodeMapping.getStateId()!=null && !PincodeMapping.getStateId().equals("")){
				replicatedIdx.setStateId(new OBState());
				replicatedIdx.getStateId().setIdState(PincodeMapping.getStateId().getIdState());
			}	
		}
		
		return replicatedIdx;
	}
}
