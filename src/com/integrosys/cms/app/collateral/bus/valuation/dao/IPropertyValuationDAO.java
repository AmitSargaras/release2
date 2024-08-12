package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.util.List;
import java.util.HashMap;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationDAO;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;

public interface IPropertyValuationDAO extends IValuationDAO {
	public void retrieveValuationProfile(final List propertyValuationProfileParamList) throws ValuationException;

    /**
     * Retrieve property index valuation profile
     *
     * @param propIndexMap
     */
    public void retrieveValuationProfileByPropIndex(HashMap propIndexMap);
}
