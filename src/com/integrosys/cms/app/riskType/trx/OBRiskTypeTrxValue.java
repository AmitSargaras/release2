package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBRiskTypeTrxValue extends OBCMSTrxValue implements
IRiskTypeTrxValue{

	public OBRiskTypeTrxValue() {
	}
	
	IRiskType riskType;
	IRiskType stagingRiskType;
	
	public OBRiskTypeTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IRiskType getRiskType() {
		return riskType;
	}
	public void setRiskType(IRiskType valuationAmountAndRating) {
		this.riskType = valuationAmountAndRating;
	}
	public IRiskType getStagingRiskType() {
		return stagingRiskType;
	}
	public void setStagingRiskType(IRiskType stagingRiskType) {
		this.stagingRiskType = stagingRiskType;
	}
}
