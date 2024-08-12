package com.integrosys.cms.app.insurancecoverage.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Dattatray Thorat
 */
public abstract class InsuranceCoverageReplicationUtils {

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
	public static IInsuranceCoverage replicateInsuranceCoverageForCreateStagingCopy(IInsuranceCoverage insuranceCoverage) {

		IInsuranceCoverage replicatedIdx = (IInsuranceCoverage) ReplicateUtils.replicateObject(insuranceCoverage,
				new String[] { "insuranceCoverageId"});

        return replicatedIdx;
	}
}