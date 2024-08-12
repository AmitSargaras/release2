package com.integrosys.cms.host.stp.common;

import com.integrosys.cms.host.stp.bus.IStpTransJdbc;
import com.integrosys.cms.host.stp.bus.StpTransJdbcImpl;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Nov 7, 2008
 * Time: 9:30:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpErrorCodeList implements IStpErrorCodeList {
    private HashMap stpBizErrorCodeListMap;

    private IStpTransJdbc stpTransJdbc;

    public IStpTransJdbc getStpTransJdbc() {
        return stpTransJdbc;
    }

    public void setStpTransJdbc(IStpTransJdbc stpTransJdbc) {
        this.stpTransJdbc = stpTransJdbc;
    }

    public StpErrorCodeList() {
    }

    public HashMap getStpBizErrorCodeList(){
        stpBizErrorCodeListMap = new HashMap();
        stpBizErrorCodeListMap = (HashMap)getStpTransJdbc().getStpBizErrorCode();
        return stpBizErrorCodeListMap;
    }

}
