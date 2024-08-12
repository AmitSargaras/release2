package com.integrosys.cms.app.udf.trx;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.udf.bus.IUdf;

public interface IUdfTrxValue extends ICMSTrxValue{

	public IUdf getUdf();

	public IUdf getStagingUdf();

	public void setUdf(IUdf value);

	public void setStagingUdf(IUdf value);
}
