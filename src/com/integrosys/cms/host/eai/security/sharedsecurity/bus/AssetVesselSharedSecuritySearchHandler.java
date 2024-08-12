package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;

/**
 * Share Security Search Handler for Asset Vessel, AB111
 * 
 * @author Chong Jun Yong
 * 
 */
public class AssetVesselSharedSecuritySearchHandler extends AbstractSharedSecuritySearchHandler {

	protected void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security) {
		AssetSecurity asset = (AssetSecurity) getSecurityDao().retrieve(new Long(security.getCollateralID()),
				AssetSecurity.class);

		VesselDetailSearchItem item = new VesselDetailSearchItem();
		item.setVesselType(asset.getSpecificChargeDetail().getAssetType());
		if (item.getVesselType() != null) {
			item.getVesselType().setStandardCodeNumber("VESSEL_TYPE");
		}

		if (asset.getVesselDetail() != null) {
			item.setBuiltYear(asset.getVesselDetail().getBuiltYear());
			item.setRegisteredCountry(asset.getVesselDetail().getRegisteredCountry());
		}

		result.setVessel(item);
	}

	protected void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria) {
		VesselDetailSearchItem vessel = searchCriteria.getVessel();

		if (vessel.getVesselType() != null && StringUtils.isNotBlank(vessel.getVesselType().getStandardCodeValue())) {
			criteria.setAssetType(vessel.getVesselType().getStandardCodeValue());
		}

		if (vessel.getBuiltYear() != null && vessel.getBuiltYear().longValue() > 0) {
			criteria.setBuiltYear(vessel.getBuiltYear().toString());
		}

		if (StringUtils.isNotBlank(vessel.getRegisteredCountry())) {
			criteria.setFlagRegistered(vessel.getRegisteredCountry());
		}
	}
}
