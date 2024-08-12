package com.integrosys.cms.app.collateral.bus.valuation;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.collateral.bus.IValuation;

public interface IValuationDAO {

	public boolean retrieveValuationParams(IValuationModel valModel, List errorList) throws ValuationException;

	public boolean retrieveValuationDate(IValuationModel valModel, List errorList) throws ValuationException;

	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException;

	public void persistValuation(IValuationModel valModel) throws ValuationException;

	public Map retrievePrevValuationInfo(long collateralId, String currSourceType) throws ValuationException;

	public void persistFsvCmv(IValuationModel valModel) throws ValuationException;

	public void persistOtherInfo(IValuationModel valModel) throws ValuationException;

	public IValuation retrieveLatestValuationByCollateralIdAndSourceType(long collateralId, String sourceType)
			throws ValuationException;

}