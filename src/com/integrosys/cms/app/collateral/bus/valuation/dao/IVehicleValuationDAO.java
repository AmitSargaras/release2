package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.util.Map;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationDAO;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;

public interface IVehicleValuationDAO extends IValuationDAO {
	public Map retrieveFeedInfo() throws ValuationException;
    public Map retrieveRegionInfo() throws ValuationException;    
}
