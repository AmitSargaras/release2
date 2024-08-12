package com.integrosys.cms.app.riskType.trx;

import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IRiskTypeTrxValue extends ICMSTrxValue{

	public IRiskType getRiskType();

	public IRiskType getStagingRiskType();

	public void setRiskType(IRiskType value);

	public void setStagingRiskType(IRiskType value);
}
