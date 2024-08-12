package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBCollateralNewMasterTrxValue extends OBCMSTrxValue implements ICollateralNewMasterTrxValue{

    public  OBCollateralNewMasterTrxValue(){}

    ICollateralNewMaster collateralNewMaster ;
    ICollateralNewMaster stagingCollateralNewMaster ;

    public OBCollateralNewMasterTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public ICollateralNewMaster getCollateralNewMaster() {
		return collateralNewMaster;
	}

	public void setCollateralNewMaster(ICollateralNewMaster collateralNewMaster) {
		this.collateralNewMaster = collateralNewMaster;
	}

	public ICollateralNewMaster getStagingCollateralNewMaster() {
		return stagingCollateralNewMaster;
	}

	public void setStagingCollateralNewMaster(ICollateralNewMaster stagingCollateralNewMaster) {
		this.stagingCollateralNewMaster = stagingCollateralNewMaster;
	}
    
   

}
