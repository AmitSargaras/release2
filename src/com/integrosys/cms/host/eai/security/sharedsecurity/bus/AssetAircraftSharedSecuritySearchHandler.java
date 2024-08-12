package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;

/**
 * Share Security Search Handler for Asset Aircraft, AB109
 * 
 * @author Chong Jun Yong
 * 
 */
public class AssetAircraftSharedSecuritySearchHandler extends AbstractSharedSecuritySearchHandler {

	protected void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security) {
		AirCraftDetailSearchItem item = new AirCraftDetailSearchItem();

		AssetSecurity asset = (AssetSecurity) getSecurityDao().retrieve(new Long(security.getCollateralID()),
				AssetSecurity.class);

		item.setAircraftType(asset.getSpecificChargeDetail().getAssetType());
		if (item.getAircraftType() != null) {
			item.getAircraftType().setStandardCodeNumber("AIRCRAFT_TYPE");
		}

		if (asset.getSpecificChargeDetail().getManufactureYear() != null) {
			item.setManufactureYear(asset.getSpecificChargeDetail().getManufactureYear());
		}
		item.setModel(asset.getSpecificChargeDetail().getModel());
		if (item.getModel() != null) {
			item.getModel().setStandardCodeNumber("MODEL");
		}

		result.setAircraft(item);
	}

	protected void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria) {
		AirCraftDetailSearchItem aircraft = searchCriteria.getAircraft();
		if (aircraft.getAircraftType() != null
				&& StringUtils.isNotBlank(aircraft.getAircraftType().getStandardCodeValue())) {
			criteria.setAircraftType(aircraft.getAircraftType().getStandardCodeValue());
		}

		if (aircraft.getModel() != null && StringUtils.isNotBlank(aircraft.getModel().getStandardCodeValue())) {
			criteria.setModelNo(aircraft.getModel().getStandardCodeValue());
		}

		if (aircraft.getManufactureYear() != null && aircraft.getManufactureYear().longValue() > 0) {
			criteria.setYearOfManufacture(aircraft.getManufactureYear().toString());
		}
	}
}
