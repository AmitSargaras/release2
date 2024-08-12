package com.integrosys.cms.app.limit.trx;

import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Implementation class of {@link IFacilityTrxValue}.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public class OBFacilityTrxValue extends OBCMSTrxValue implements IFacilityTrxValue {

	private IFacilityMaster actualFacilityMaster;

	private IFacilityMaster stagingFacilityMaster;

	public OBFacilityTrxValue() {
		setTransactionType(INSTANCE_NAME);
	}

	public IFacilityMaster getFacilityMaster() {
		return actualFacilityMaster;
	}

	public IFacilityMaster getStagingFacilityMaster() {
		return stagingFacilityMaster;
	}

	public void setFacilityMaster(IFacilityMaster facilityMaster) {
		this.actualFacilityMaster = facilityMaster;
	}

	public void setStagingFacilityMaster(IFacilityMaster stagingFacilityMaster) {
		this.stagingFacilityMaster = stagingFacilityMaster;
	}

}
