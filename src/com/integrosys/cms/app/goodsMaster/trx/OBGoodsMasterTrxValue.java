package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBGoodsMasterTrxValue extends OBCMSTrxValue implements IGoodsMasterTrxValue{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OBGoodsMasterTrxValue() {
	}
	
	IGoodsMaster goodsMaster;
	IGoodsMaster stagingGoodsMaster;
	
	public OBGoodsMasterTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IGoodsMaster getGoodsMaster() {
		return goodsMaster;
	}
	public void setGoodsMaster(IGoodsMaster goodsMaster) {
		this.goodsMaster = goodsMaster;
	}
	public IGoodsMaster getStagingGoodsMaster() {
		return stagingGoodsMaster;
	}
	public void setStagingGoodsMaster(IGoodsMaster stagingGoodsMaster) {
		this.stagingGoodsMaster = stagingGoodsMaster;
	}
}
