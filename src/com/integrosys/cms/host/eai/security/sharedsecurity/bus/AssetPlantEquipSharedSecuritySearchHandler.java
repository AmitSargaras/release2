package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;

/**
 * Share Security Search Handler for Asset Plant & Equipment, AB101
 * 
 * @author Chong Jun Yong
 * 
 */
public class AssetPlantEquipSharedSecuritySearchHandler extends AbstractSharedSecuritySearchHandler {

	protected void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security) {
		AssetSecurity asset = (AssetSecurity) getSecurityDao().retrieve(new Long(security.getCollateralID()),
				AssetSecurity.class);

		PlantEquipDetailSearchItem item = new PlantEquipDetailSearchItem();
		item.setPlantEquipType(asset.getSpecificChargeDetail().getAssetType());
		if (item.getPlantEquipType() != null) {
			item.getPlantEquipType().setStandardCodeNumber("PLANT_EQUIP");
		}

		item.setModel(asset.getSpecificChargeDetail().getModel());
		if (item.getModel() != null) {
			item.getModel().setStandardCodeNumber("MODEL");
		}

		if (asset.getPlantEquipDetail() != null) {
			item.setSerialNo(asset.getPlantEquipDetail().getSerialNo());
			item.setManufactureYear(asset.getPlantEquipDetail().getManufactureYear());
		}

		result.setPlantEquip(item);

	}

	protected void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria) {
		PlantEquipDetailSearchItem plantEquip = searchCriteria.getPlantEquip();

		if (plantEquip.getPlantEquipType() != null
				&& StringUtils.isNotBlank(plantEquip.getPlantEquipType().getStandardCodeValue())) {
			criteria.setAssetType(plantEquip.getPlantEquipType().getStandardCodeValue());
		}

		if (plantEquip.getModel() != null && StringUtils.isNotBlank(plantEquip.getModel().getStandardCodeValue())) {
			criteria.setModelNo(plantEquip.getModel().getStandardCodeValue());
		}

		if (plantEquip.getManufactureYear() != null && plantEquip.getManufactureYear().longValue() > 0) {
			criteria.setYearOfManufacture(plantEquip.getManufactureYear().toString());
		}

		if (StringUtils.isNotBlank(plantEquip.getSerialNo())) {
			criteria.setSerialNo(plantEquip.getSerialNo());
		}
	}

}
