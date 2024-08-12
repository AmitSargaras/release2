package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBExcludedFacilityTrxValue extends OBCMSTrxValue implements
IExcludedFacilityTrxValue{

	public OBExcludedFacilityTrxValue() {
	}
	
	IExcludedFacility excludedFacility;
	IExcludedFacility stagingExcludedFacility;
	
	public OBExcludedFacilityTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public IExcludedFacility getExcludedFacility() {
		return excludedFacility; 
	}

	public IExcludedFacility getStagingExcludedFacility() {
		return stagingExcludedFacility;
	}

	public void setExcludedFacility(IExcludedFacility value) {
		this.excludedFacility = value;
	}

	public void setStagingExcludedFacility(IExcludedFacility value) {
		this.stagingExcludedFacility = value;
	}
}
