package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;

/**
 * Share Security Search Handler for Asset Vehicle, AB102
 * 
 * @author Chong Jun Yong
 * 
 */
public class AssetVehicleSharedSecuritySearchHandler extends AbstractSharedSecuritySearchHandler {

	protected void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security) {
		AssetSecurity asset = (AssetSecurity) getSecurityDao().retrieve(new Long(security.getCollateralID()),
				AssetSecurity.class);

		VehicleDetailSearchItem vehicle = new VehicleDetailSearchItem();
		vehicle.setVehicleType(asset.getSpecificChargeDetail().getAssetType());
		if (vehicle.getVehicleType() != null) {
			vehicle.getVehicleType().setStandardCodeNumber("VEHICLE_TYPE");
		}
		if (asset.getSpecificChargeDetail() != null) {
			vehicle.setRegistrationNo(asset.getSpecificChargeDetail().getRegistrationNo());
		}

		if (asset.getVehicleDetail() != null) {
			vehicle.setChassisNumber(asset.getVehicleDetail().getChassisNumber());
			vehicle.setEngineNumber(asset.getVehicleDetail().getEngineNumber());
		}

		result.setVehicle(vehicle);
	}

	protected void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria) {
		VehicleDetailSearchItem vehicle = searchCriteria.getVehicle();

		if (vehicle.getVehicleType() != null) {
			criteria.setVehType(vehicle.getVehicleType().getStandardCodeValue());
		}

		if (StringUtils.isNotBlank(vehicle.getRegistrationNo())) {
			criteria.setRegN0(vehicle.getRegistrationNo());
		}

		if (StringUtils.isNotBlank(vehicle.getChassisNumber())) {
			criteria.setChassissNo(vehicle.getChassisNumber());
		}

		if (StringUtils.isNotBlank(vehicle.getEngineNumber())) {
			criteria.setEngineNo(vehicle.getEngineNumber());
		}
	}
}
