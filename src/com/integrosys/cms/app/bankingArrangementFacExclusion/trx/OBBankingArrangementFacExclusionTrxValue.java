package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBBankingArrangementFacExclusionTrxValue extends OBCMSTrxValue implements IBankingArrangementFacExclusionTrxValue{

	public OBBankingArrangementFacExclusionTrxValue() {
	}
	
	IBankingArrangementFacExclusion actual;
	IBankingArrangementFacExclusion staging;
	
	public OBBankingArrangementFacExclusionTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IBankingArrangementFacExclusion getActual() {
		return actual;
	}
	public void setActual(IBankingArrangementFacExclusion actual) {
		this.actual = actual;
	}
	public IBankingArrangementFacExclusion getStaging() {
		return staging;
	}
	public void setStaging(IBankingArrangementFacExclusion staging) {
		this.staging = staging;
	}

}