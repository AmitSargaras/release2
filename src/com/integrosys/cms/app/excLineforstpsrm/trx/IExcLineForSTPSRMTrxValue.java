package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IExcLineForSTPSRMTrxValue extends ICMSTrxValue{

	public IExcLineForSTPSRM getActual();

	public IExcLineForSTPSRM getStaging();

	public void setActual(IExcLineForSTPSRM value);

	public void setStaging(IExcLineForSTPSRM value);
}
