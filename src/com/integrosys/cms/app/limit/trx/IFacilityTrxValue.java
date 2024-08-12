package com.integrosys.cms.app.limit.trx;

import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Transaction value represent transaction of facility master. This basically is
 * subclass of {@link ICMSTrxValue}, just provide the interface to put/get the
 * domain objects.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public interface IFacilityTrxValue extends ICMSTrxValue {
	public static final String INSTANCE_NAME = "FACILITY";

	public IFacilityMaster getFacilityMaster();

	public IFacilityMaster getStagingFacilityMaster();

	public void setFacilityMaster(IFacilityMaster facilityMaster);

	public void setStagingFacilityMaster(IFacilityMaster stagingFacilityMaster);
}
