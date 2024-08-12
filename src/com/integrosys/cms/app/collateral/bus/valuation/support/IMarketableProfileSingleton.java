package com.integrosys.cms.app.collateral.bus.valuation.support;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IMarketableValuationDAO;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Dec 15, 2008
 * Time: 4:27:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IMarketableProfileSingleton {
    public IMarketableValuationDAO getMarketableValuationDao();
    public void setMarketableValuationDao(IMarketableValuationDAO marketableValuationDao);
    public void init();

    public void reloadData();
    public void clearData();
    public Object getData(String key);
    public Map getData();
}
