package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ICollateralRocTrxValue extends ICMSTrxValue{

	public ICollateralRoc getCollateralRoc();

	public ICollateralRoc getStagingCollateralRoc();

	public void setCollateralRoc(ICollateralRoc value);

	public void setStagingCollateralRoc(ICollateralRoc value);
}
