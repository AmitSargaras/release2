package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationDAO;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;

import java.util.Map;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Nov 21, 2008
 * Time: 11:04:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IMarketableValuationDAO extends IValuationDAO {

    public void retrievePriceCapSetup(Map resultMap) throws ValuationException;

    public void retrieveFeedsInfo(IValuationModel valModel) throws ValuationException;

    public void retrieveFeedPriceInfo(Map resultMap);

    public void retrieveBoardType(final List resultList);
}
