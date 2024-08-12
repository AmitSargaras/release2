package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Author: Syukri
 * Date: Jun 2, 2008
 */
public class SectorLimitException extends OFAException {

    public SectorLimitException() {
        super();
    }

    public SectorLimitException(String msg) {
        super(msg);
    }

    public SectorLimitException(Throwable throwable) {
        super(throwable);
    }

    public SectorLimitException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
