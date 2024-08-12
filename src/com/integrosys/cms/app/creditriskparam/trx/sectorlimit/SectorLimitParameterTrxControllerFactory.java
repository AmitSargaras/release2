package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Author: Syukri
 * Date: Jun 4, 2008
 */
public class SectorLimitParameterTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController sectorLimitReadController;

	private ITrxController sectorLimitTrxContoller;

    public ITrxController getSectorLimitReadController() {
        return sectorLimitReadController;
    }

    public void setSectorLimitReadController(ITrxController sectorLimitReadController) {
        this.sectorLimitReadController = sectorLimitReadController;
    }

    public ITrxController getSectorLimitTrxContoller() {
        return sectorLimitTrxContoller;
    }

    public void setSectorLimitTrxContoller(ITrxController sectorLimitTrxContoller) {
        this.sectorLimitTrxContoller = sectorLimitTrxContoller;
    }

    public SectorLimitParameterTrxControllerFactory() {
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
        if (ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER.equals(iTrxParameter.getAction()) ||
        		ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_ID.equals(iTrxParameter.getAction()) ||
                ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_TRXID.equals(iTrxParameter.getAction()))
            return getSectorLimitReadController();
        else
            return getSectorLimitTrxContoller();
    }
}
