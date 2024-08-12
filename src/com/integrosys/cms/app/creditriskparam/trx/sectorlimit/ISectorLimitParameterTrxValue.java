package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;

/**
 * Author: Syukri
 * Date: Jun 3, 2008
 */
public interface ISectorLimitParameterTrxValue extends ICMSTrxValue {
	
	IMainSectorLimitParameter getActualMainSectorLimitParameter();
    void setActualMainSectorLimitParameter(IMainSectorLimitParameter iMainSectorLimitParameter);

    IMainSectorLimitParameter getStagingMainSectorLimitParameter();
    void setStagingMainSectorLimitParameter(IMainSectorLimitParameter iMainSectorLimitParameter);

}
