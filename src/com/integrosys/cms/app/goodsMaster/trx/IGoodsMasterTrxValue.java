package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IGoodsMasterTrxValue extends ICMSTrxValue{

	public IGoodsMaster getGoodsMaster();

	public IGoodsMaster getStagingGoodsMaster();

	public void setGoodsMaster(IGoodsMaster value);

	public void setStagingGoodsMaster(IGoodsMaster value);
}
