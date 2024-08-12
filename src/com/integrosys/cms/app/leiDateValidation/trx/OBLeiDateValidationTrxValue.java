package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBLeiDateValidationTrxValue extends OBCMSTrxValue implements
ILeiDateValidationTrxValue{

	public OBLeiDateValidationTrxValue() {
	}
	
	ILeiDateValidation leiDateValidation;
	ILeiDateValidation stagingLeiDateValidation;
	
	public OBLeiDateValidationTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public ILeiDateValidation getLeiDateValidation() {
		return leiDateValidation;
	}
	public void setLeiDateValidation(ILeiDateValidation leiDateValidation) {
		this.leiDateValidation = leiDateValidation;
	}
	public ILeiDateValidation getStagingLeiDateValidation() {
		return stagingLeiDateValidation;
	}
	public void setStagingLeiDateValidation(ILeiDateValidation stagingLeiDateValidation) {
		this.stagingLeiDateValidation = stagingLeiDateValidation;
	}
}