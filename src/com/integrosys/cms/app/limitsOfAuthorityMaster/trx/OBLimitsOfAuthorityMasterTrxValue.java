package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBLimitsOfAuthorityMasterTrxValue extends OBCMSTrxValue implements ILimitsOfAuthorityMasterTrxValue{

	public OBLimitsOfAuthorityMasterTrxValue() {
	}
	
	ILimitsOfAuthorityMaster actual;
	ILimitsOfAuthorityMaster staging;
	
	public OBLimitsOfAuthorityMasterTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public ILimitsOfAuthorityMaster getActual() {
		return actual;
	}
	public void setActual(ILimitsOfAuthorityMaster actual) {
		this.actual = actual;
	}
	public ILimitsOfAuthorityMaster getStaging() {
		return staging;
	}
	public void setStaging(ILimitsOfAuthorityMaster staging) {
		this.staging = staging;
	}

}