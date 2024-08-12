package com.integrosys.cms.host.stp.chain;

import org.apache.commons.chain.impl.ContextBase;
import org.springframework.context.ApplicationContext;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 4, 2008
 * Time: 11:00:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpContext extends ContextBase {
    private IFacilityTrxValue obFacilityTrxValue;
    private ICollateralTrxValue obCollateralTrxValue;
    private IStpMasterTrans obStpMasterTrans;
    private ApplicationContext context;

    public StpContext(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }

    public IFacilityTrxValue getOBFacilityTrxValue() {
        return obFacilityTrxValue;
    }

    public void setOBFacilityTrxValue(IFacilityTrxValue obFacilityTrxValue) {
        this.obFacilityTrxValue = obFacilityTrxValue;
    }

    public ICollateralTrxValue getOBCollateralTrxValue() {
        return obCollateralTrxValue;
    }

    public void setOBCollateralTrxValue(ICollateralTrxValue obCollateralTrxValue) {
        this.obCollateralTrxValue = obCollateralTrxValue;
    }

    public IStpMasterTrans getOBStpMasterTrans() {
        return obStpMasterTrans;
    }

    public void setOBStpMasterTrans(IStpMasterTrans obStpMasterTrans) {
        this.obStpMasterTrans = obStpMasterTrans;
    }
}
