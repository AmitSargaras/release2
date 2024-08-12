package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.udf.bus.IUdf;

public class OBUdfTrxValue extends OBCMSTrxValue implements
IUdfTrxValue{

	public OBUdfTrxValue() {
	}
	
	IUdf udf;
	IUdf stagingUdf;
	
	public OBUdfTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IUdf getUdf() {
		return udf;
	}
	public void setUdf(IUdf udf) {
		this.udf = udf;
	}
	public IUdf getStagingUdf() {
		return stagingUdf;
	}
	public void setStagingUdf(IUdf stagingUdf) {
		this.stagingUdf = stagingUdf;
	}


}
