package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBExcLineForSTPSRMTrxValue extends OBCMSTrxValue implements IExcLineForSTPSRMTrxValue{

	public OBExcLineForSTPSRMTrxValue() {
	}
	
	IExcLineForSTPSRM actual;
	IExcLineForSTPSRM staging;
	
	public OBExcLineForSTPSRMTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IExcLineForSTPSRM getActual() {
		return actual;
	}
	public void setActual(IExcLineForSTPSRM actual) {
		this.actual = actual;
	}
	public IExcLineForSTPSRM getStaging() {
		return staging;
	}
	public void setStaging(IExcLineForSTPSRM staging) {
		this.staging = staging;
	}

}