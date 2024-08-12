package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class CustomerReplicationUtils {

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
	public static ICMSCustomer replicateCustomerForCreateStagingCopy(ICMSCustomer customer) {

		ICMSCustomer replicatedIdx = (ICMSCustomer) ReplicateUtils.replicateObject(customer,
				new String[] { "customerId"});

		 if(customer.getCMSLegalEntity()!=null){
		        if (replicatedIdx.getCMSLegalEntity() == null)
		        	replicatedIdx.setCMSLegalEntity(new OBCMSLegalEntity());

		        replicatedIdx.setCMSLegalEntity(customer.getCMSLegalEntity());
		        }
		 
        return replicatedIdx;
	}
}