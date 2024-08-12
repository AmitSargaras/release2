package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface INpaTraqCodeMasterTrxValue extends ICMSTrxValue{

	public INpaTraqCodeMaster getNpaTraqCodeMaster();

	public INpaTraqCodeMaster getStagingNpaTraqCodeMaster();

	public void setNpaTraqCodeMaster(INpaTraqCodeMaster value);

	public void setStagingNpaTraqCodeMaster(INpaTraqCodeMaster value);
}
