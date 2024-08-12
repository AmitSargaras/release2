package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.sectorlimit.ISectorLimitParameterProxy;
/**
 * Author: KC Chin
 * Date: Aug 10, 2010
 */
public class SectorLimitCommand extends AbstractCommand{

	private ISectorLimitParameterProxy sectorLimitProxy;

    public ISectorLimitParameterProxy getSectorLimitProxy() {
        return sectorLimitProxy;
    }

    public void setSectorLimitProxy(ISectorLimitParameterProxy sectorLimitProxy) {
        this.sectorLimitProxy = sectorLimitProxy;
    }
}