package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBProductMasterTrxValue extends OBCMSTrxValue implements
IProductMasterTrxValue{

	public OBProductMasterTrxValue() {
	}
	
	IProductMaster productMaster;
	IProductMaster stagingProductMaster;
	
	public OBProductMasterTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IProductMaster getProductMaster() {
		return productMaster;
	}
	public void setProductMaster(IProductMaster productMaster) {
		this.productMaster = productMaster;
	}
	public IProductMaster getStagingProductMaster() {
		return stagingProductMaster;
	}
	public void setStagingProductMaster(IProductMaster stagingProductMaster) {
		this.stagingProductMaster = stagingProductMaster;
	}
}
