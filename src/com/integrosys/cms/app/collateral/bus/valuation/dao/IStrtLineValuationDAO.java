package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationDAO;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Apr 22, 2009
 * Time: 11:29:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStrtLineValuationDAO extends IValuationDAO {

    public Map retrieveAssetLife() throws ValuationException;


}
