package com.integrosys.cms.app.insurancecoveragedtls.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Dattatry Thorat
 * 
 */
public abstract class InsuranceCoverageDtlsReplicationUtils {

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
	public static IInsuranceCoverageDtls replicateInsuranceCoverageDtlsForCreateStagingCopy(IInsuranceCoverageDtls insuranceCoverageDtls) {

		IInsuranceCoverageDtls replicatedIdx = (IInsuranceCoverageDtls) ReplicateUtils.replicateObject(insuranceCoverageDtls,
				new String[] { "insuranceCoverageDtlsId"});
		
		if(replicatedIdx.getInsuranceCoverageCode() == null)
			replicatedIdx.setInsuranceCoverageCode(new OBInsuranceCoverage());
		
		replicatedIdx.getInsuranceCoverageCode().setId(insuranceCoverageDtls.getInsuranceCoverageCode().getId());

        return replicatedIdx;
	}
}