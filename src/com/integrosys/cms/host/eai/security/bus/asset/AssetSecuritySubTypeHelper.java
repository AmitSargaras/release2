package com.integrosys.cms.host.eai.security.bus.asset;

import org.apache.commons.lang.Validate;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.StandardCode;

/**
 * <p>
 * Utitlity class to deal with Asset security and it's subtypes.
 * <p>
 * Currently, is to create new instance of subtype when there is no subtype
 * instance inside <tt>AssetSecurity</tt>
 * @author Chong Jun Yong
 * @see AssetSecurity
 */
public abstract class AssetSecuritySubTypeHelper {

	private static final String CATEGORY_CODE_COL_SUBTYPE = "54";

	private static final StandardCode aircraftSubType = new StandardCode(CATEGORY_CODE_COL_SUBTYPE,
			ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT);

	private static final StandardCode vehicleSubType = new StandardCode(CATEGORY_CODE_COL_SUBTYPE,
			ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH);

	private static final StandardCode plantEquipSubType = new StandardCode(CATEGORY_CODE_COL_SUBTYPE,
			ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT);

	private static final StandardCode goldSubType = new StandardCode(CATEGORY_CODE_COL_SUBTYPE,
			ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD);

	private static final StandardCode vesselSubType = new StandardCode(CATEGORY_CODE_COL_SUBTYPE,
			ICMSConstant.COLTYPE_ASSET_VESSEL);

	/**
	 * <p>
	 * Create subtype instance into asset instance provided if there is no
	 * subtype instance at the first place.
	 * <p>
	 * This should be invoked just before creation of asset into persistence
	 * storage.
	 * @param asset instance of asset security
	 * @param subtype subtype instance to be used to determine what's the
	 *        subtype of asset security it is
	 */
	public static void createSubTypeInstanceIfEmpty(AssetSecurity asset, StandardCode subtype) {
		Validate.notNull(asset, "AssetSecurity instance must be provided.");
		Validate.notNull(subtype, "Security SubType instance must be provided.");

		if (aircraftSubType.equals(subtype) && asset.getAircraftDetail() == null) {
			asset.setAircraftDetail(new AirCraftDetail());
		}
		else if (vehicleSubType.equals(subtype) && asset.getVehicleDetail() == null) {
			asset.setVehicleDetail(new VehicleDetail());
		}
		else if (plantEquipSubType.equals(subtype) && asset.getPlantEquipDetail() == null) {
			asset.setPlantEquipDetail(new PlantEquipDetail());
		}
		else if (goldSubType.equals(subtype) && asset.getGoldDetail() == null) {
			asset.setGoldDetail(new GoldDetail());
		}
		else if (vesselSubType.equals(subtype) && asset.getVesselDetail() == null) {
			asset.setVesselDetail(new VesselDetail());
		}
	}
}
