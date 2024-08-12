package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IProductMasterTrxValue extends ICMSTrxValue{

	public IProductMaster getProductMaster();

	public IProductMaster getStagingProductMaster();

	public void setProductMaster(IProductMaster value);

	public void setStagingProductMaster(IProductMaster value);
}
