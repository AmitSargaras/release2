package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface ICollateralNewMasterTrxValue  extends ICMSTrxValue {

    public ICollateralNewMaster getCollateralNewMaster();

    public ICollateralNewMaster getStagingCollateralNewMaster();

    public void setCollateralNewMaster(ICollateralNewMaster value);

    public void setStagingCollateralNewMaster(ICollateralNewMaster value);
}
