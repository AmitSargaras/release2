package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBNpaTraqCodeMasterTrxValue extends OBCMSTrxValue implements INpaTraqCodeMasterTrxValue{

	public OBNpaTraqCodeMasterTrxValue() {
	}
	
	INpaTraqCodeMaster npaTraqCodeMaster;
	INpaTraqCodeMaster stagingNpaTraqCodeMaster;
	
	public OBNpaTraqCodeMasterTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public INpaTraqCodeMaster getNpaTraqCodeMaster() {
		return npaTraqCodeMaster;
	}

	public void setNpaTraqCodeMaster(INpaTraqCodeMaster npaTraqCodeMaster) {
		this.npaTraqCodeMaster = npaTraqCodeMaster;
	}

	public INpaTraqCodeMaster getStagingNpaTraqCodeMaster() {
		return stagingNpaTraqCodeMaster;
	}

	public void setStagingNpaTraqCodeMaster(INpaTraqCodeMaster stagingNpaTraqCodeMaster) {
		this.stagingNpaTraqCodeMaster = stagingNpaTraqCodeMaster;
	}
	
	
	
}
