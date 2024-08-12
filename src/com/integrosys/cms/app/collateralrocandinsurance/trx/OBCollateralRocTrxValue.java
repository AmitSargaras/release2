package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBCollateralRocTrxValue extends OBCMSTrxValue implements
ICollateralRocTrxValue{

	public OBCollateralRocTrxValue() {
	}
	
	ICollateralRoc collateralRoc;
	ICollateralRoc stagingCollateralRoc;
	
	public OBCollateralRocTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public ICollateralRoc getCollateralRoc() {
		return collateralRoc; 
	}

	public ICollateralRoc getStagingCollateralRoc() {
		return stagingCollateralRoc;
	}

	public void setCollateralRoc(ICollateralRoc value) {
		this.collateralRoc = value;
	}

	public void setStagingCollateralRoc(ICollateralRoc value) {
		this.stagingCollateralRoc = value;
	}
}
