package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 22, 2008
 * Time: 5:07:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICGCValuationDAO {

    public double getTotalFacOutstandingAmt(IValuationModel valModel);
    public double getTotalSecOMVAmt(IValuationModel valModel);
    public void persistOtherInfo(IValuationModel valModel);
    
}
