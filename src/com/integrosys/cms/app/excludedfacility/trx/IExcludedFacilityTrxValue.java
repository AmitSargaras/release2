package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IExcludedFacilityTrxValue extends ICMSTrxValue{

	public IExcludedFacility getExcludedFacility();

	public IExcludedFacility getStagingExcludedFacility();

	public void setExcludedFacility(IExcludedFacility value);

	public void setStagingExcludedFacility(IExcludedFacility value);
}
