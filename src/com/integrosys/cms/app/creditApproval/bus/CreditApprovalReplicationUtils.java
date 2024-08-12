package com.integrosys.cms.app.creditApproval.bus;


/**
 * <p>
 * Replication utility used for replicating bond to interact with persistent
 * storage
 * 
 * @author Govind Sahu
 * @since 1.0
 * @see com.integrosys.base.techinfra.util.ReplicateUtils
 */
public abstract class CreditApprovalReplicationUtils {


	/**
	 * <p>
	 * Replicate Credit Approval which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static ICreditApproval replicateCreditApprovalForCreateStagingCopy(ICreditApproval staging) {
		
		OBCreditApproval replicatedOb = new OBCreditApproval();
		replicatedOb.setId(staging.getId());
		replicatedOb.setApprovalCode(staging.getApprovalCode());
		replicatedOb.setApprovalName(staging.getApprovalName());
		replicatedOb.setMaximumLimit(staging.getMaximumLimit());
		replicatedOb.setMinimumLimit(staging.getMinimumLimit());
		replicatedOb.setSegmentId(staging.getSegmentId());
		replicatedOb.setEmail(staging.getEmail());
		replicatedOb.setSenior(staging.getSenior());
		replicatedOb.setRisk(staging.getRisk());
		replicatedOb.setCreateBy(staging.getCreateBy());
		replicatedOb.setCreationDate(staging.getCreationDate());
		replicatedOb.setLastUpdateBy(staging.getLastUpdateBy());
		replicatedOb.setLastUpdateDate(staging.getLastUpdateDate());
		replicatedOb.setDeprecated(staging.getDeprecated());
		replicatedOb.setStatus(staging.getStatus());
		replicatedOb.setDeferralPowers(staging.getDeferralPowers());
		replicatedOb.setWaivingPowers(staging.getWaivingPowers());
	    replicatedOb.setRegionId(staging.getRegionId());
	    replicatedOb.setCpsId(staging.getCpsId());
	    replicatedOb.setEmployeeId(staging.getEmployeeId());
        return replicatedOb;
	}
}
