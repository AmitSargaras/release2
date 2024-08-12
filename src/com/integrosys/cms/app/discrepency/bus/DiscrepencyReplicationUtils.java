package com.integrosys.cms.app.discrepency.bus;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */
public class DiscrepencyReplicationUtils {

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
	public static IDiscrepency replicateDiscrepencyForCreateStagingCopy(IDiscrepency discrepency) {

		IDiscrepency replicatedIdx = new OBDiscrepency();
	
		replicatedIdx.setAcceptedDate(discrepency.getAcceptedDate());
		replicatedIdx.setCreationDate(discrepency.getCreationDate());
		replicatedIdx.setNextDueDate(discrepency.getNextDueDate());
		replicatedIdx.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replicatedIdx.setDiscrepency(discrepency.getDiscrepency());
		replicatedIdx.setRecDate(discrepency.getRecDate());
		replicatedIdx.setWaiveDate(discrepency.getWaiveDate());
		replicatedIdx.setDeferDate(discrepency.getDeferDate());
		replicatedIdx.setStatus(discrepency.getStatus());
		replicatedIdx.setCreditApprover(discrepency.getCreditApprover());
		replicatedIdx.setDocRemarks(discrepency.getDocRemarks());
		replicatedIdx.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replicatedIdx.setDiscrepencyType(discrepency.getDiscrepencyType());
		replicatedIdx.setApprovedBy(discrepency.getApprovedBy());
		replicatedIdx.setCritical(discrepency.getCritical());
		replicatedIdx.setCounter(discrepency.getCounter());
		replicatedIdx.setCustomerId(discrepency.getCustomerId());
		replicatedIdx.setDeferedCounter(discrepency.getDeferedCounter());
		replicatedIdx.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replicatedIdx.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replicatedIdx.setTransactionStatus(discrepency.getTransactionStatus());
		
		/*Set replicatedFacilitySet = new HashSet();
		Set stagingFacilitySet = discrepency.getFacilityList();
		Iterator itor = stagingFacilitySet.iterator();
		IDiscrepencyFacilityList discrepencyObj = new OBDiscrepencyFacilityList();
		while(itor.hasNext())
		{
			IDiscrepencyFacilityList repdiscrepencyObj = new OBDiscrepencyFacilityList();
			discrepencyObj = (IDiscrepencyFacilityList)itor.next();
			repdiscrepencyObj.setFacilityName(discrepencyObj.getFacilityName());
		    repdiscrepencyObj.setFacilityId(discrepencyObj.getFacilityId());
			replicatedFacilitySet.add(repdiscrepencyObj);
		}*/
		replicatedIdx.setFacilityList(discrepency.getFacilityList());
        return replicatedIdx;
	}
}
