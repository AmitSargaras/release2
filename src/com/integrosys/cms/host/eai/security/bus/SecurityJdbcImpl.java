package com.integrosys.cms.host.eai.security.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.PlantEquipDetail;
import com.integrosys.cms.host.eai.security.bus.asset.SpecificChargeDetail;
import com.integrosys.cms.host.eai.security.bus.asset.VehicleDetail;
import com.integrosys.cms.host.eai.security.bus.asset.VesselDetail;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * Implementation of ISecurityJdbc using springframework jdbc infrastructure.
 * 
 * @author Chong Jun Yong
 * @since 07.08.2008
 */
public class SecurityJdbcImpl extends JdbcDaoSupport implements ISecurityJdbc {

	private final Logger logger = LoggerFactory.getLogger(SecurityJdbcImpl.class);

	private int securitySearchCount;

	public void setSecuritySearchCount(int securitySearchCount) {
		this.securitySearchCount = securitySearchCount;
	}

	public double retrieveMargin(String countryCode, String collateralSubTypeCode) {
		String query = "SELECT THRESHOLD_PERCENT, VALUATION_FREQUENCY_UNIT, VALUATION_FREQUENCY "
				+ "FROM CMS_SECURITY_PARAMETER WHERE COUNTRY_ISO_CODE = ? AND SECURITY_SUB_TYPE_ID = ?";

		Double margin = (Double) getJdbcTemplate().query(query, new Object[] { countryCode, collateralSubTypeCode },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return new Double(rs.getDouble("THRESHOLD_PERCENT"));
						}

						return new Double(100.00);
					}

				});

		return margin.doubleValue();
	}

	public List searchAssetVehicleCollaterals(List cmsCollateralIdList, Map assetVehicleParameters) {
		Validate.notEmpty(cmsCollateralIdList, "'cmsCollateralIdList' must not be empty");
		Validate.notEmpty(assetVehicleParameters, "'assetVehicleParameters' must not be empty.");

		List resultList = new ArrayList();

		int fromIndex = 0;
		int toIndex = this.securitySearchCount;

		while (toIndex <= cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(fromIndex, toIndex);

			resultList.addAll(doSearchAssetVehicleCollaterals(persistentSubList, assetVehicleParameters));

			fromIndex = toIndex;
			toIndex += this.securitySearchCount;
		}

		if (fromIndex < cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(fromIndex, cmsCollateralIdList.size());

			resultList.addAll(doSearchAssetVehicleCollaterals(persistentSubList, assetVehicleParameters));
		}

		return resultList;
	}

	/**
	 * Do internally for the pre-defined size of cms collateral id list.
	 */
	protected List doSearchAssetVehicleCollaterals(List cmsCollateralIdList, Map assetVehicleParameters) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT asset.cms_collateral_id, type_of_asset, reg_no, veh.engine_number, veh.chassis_number ");
		buf.append("FROM cms_asset asset LEFT OUTER JOIN cms_asset_vehicle veh ON ");
		buf.append("asset.cms_collateral_id = veh.cms_collateral_id WHERE asset.cms_collateral_id IN (");
		buf.append(prepareInExpression(cmsCollateralIdList)).append(") ");

		List argList = new ArrayList();

		String registrationNo = (String) assetVehicleParameters.get("specificChargeDetail.registrationNo");
		String chassisNo = (String) assetVehicleParameters.get("vehicleDetail.chassisNumber");
		String engineNo = (String) assetVehicleParameters.get("vehicleDetail.engineNumber");
		String vehicleType = (String) assetVehicleParameters.get("specificChargeDetail.assetType.standardCodeValue");

		if (StringUtils.isNotBlank(vehicleType)) {
			buf.append("AND asset.type_of_asset = ? ");
			argList.add(vehicleType);
		}

		if (StringUtils.isNotBlank(registrationNo)) {
			buf.append("AND asset.reg_no LIKE '% ").append(registrationNo).append("' ");
		}

		if (StringUtils.isNotBlank(chassisNo)) {
			buf.append("AND veh.chassis_number LIKE '%").append(chassisNo).append("' ");
		}

		if (StringUtils.isNotBlank(engineNo)) {
			buf.append("AND veh.engine_number LIKE '%").append(engineNo).append("' ");
		}

		return getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetSecurity asset = new AssetSecurity();
				asset.setCMSSecurityId(rs.getLong("cms_collateral_id"));

				StandardCode assetType = new StandardCode();
				assetType.setStandardCodeNumber(CategoryCodeConstant.VEHICLE_TYPE);
				assetType.setStandardCodeValue(rs.getString("type_of_asset"));

				SpecificChargeDetail specificChargeDetail = new SpecificChargeDetail();
				specificChargeDetail.setAssetType(assetType);

				asset.setSpecificChargeDetail(specificChargeDetail);

				VehicleDetail veh = new VehicleDetail();
				veh.setChassisNumber(rs.getString("chassis_number"));
				veh.setEngineNumber(rs.getString("engine_number"));
				veh.setRegistrationNo(rs.getString("reg_no"));

				asset.setVehicleDetail(veh);

				return asset;
			}
		});
	}

	public List searchAssetAircraftCollaterals(List cmsCollateralIdList, Map assetAircraftParameters) {
		Validate.notEmpty(cmsCollateralIdList, "'cmsCollateralIdList' must not be empty");
		Validate.notEmpty(assetAircraftParameters, "'assetAircraftParameters' must not be empty.");

		List resultList = new ArrayList();

		int fromIndex = 0;
		int toIndex = this.securitySearchCount;

		while (toIndex <= cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(fromIndex, toIndex);

			resultList.addAll(doSearchAssetAircraftCollaterals(persistentSubList, assetAircraftParameters));

			fromIndex = toIndex;
			toIndex += this.securitySearchCount;
		}

		if (fromIndex < cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(fromIndex, cmsCollateralIdList.size());

			resultList.addAll(doSearchAssetAircraftCollaterals(persistentSubList, assetAircraftParameters));
		}

		return resultList;
	}

	protected List doSearchAssetAircraftCollaterals(List cmsCollateralIdList, Map assetAircraftParameters) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT asset.cms_collateral_id, type_of_asset, manufacture_year, model_no ");
		buf.append("FROM cms_asset asset LEFT OUTER JOIN cms_asset_aircraft aircraft ON ");
		buf.append("asset.cms_collateral_id = aircraft.cms_collateral_id WHERE asset.cms_collateral_id IN (");
		buf.append(prepareInExpression(cmsCollateralIdList)).append(") ");

		List argList = new ArrayList();

		String aircraftType = (String) assetAircraftParameters.get("specificChargeDetail.assetType.standardCodeValue");
		Long manufactureYear = (Long) assetAircraftParameters.get("specificChargeDetail.manufactureYear");
		String model = (String) assetAircraftParameters.get("specificChargeDetail.model.standardCodeValue");

		if (StringUtils.isNotBlank(aircraftType)) {
			buf.append("AND asset.type_of_asset = ? ");
			argList.add(aircraftType);
		}

		if (manufactureYear != null && manufactureYear.longValue() > 0) {
			buf.append("AND asset.manufacture_year = ? ");
			argList.add(manufactureYear);
		}

		if (StringUtils.isNotBlank(model)) {
			buf.append("AND asset.model_no LIKE '%").append(model).append("' ");
		}

		return getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetSecurity asset = new AssetSecurity();
				asset.setCMSSecurityId(rs.getLong("cms_collateral_id"));

				StandardCode assetType = new StandardCode();
				assetType.setStandardCodeNumber(CategoryCodeConstant.AIRCRAFT_TYPE);
				assetType.setStandardCodeValue(rs.getString("type_of_asset"));

				StandardCode model = new StandardCode();
				assetType.setStandardCodeNumber("MODEL");
				assetType.setStandardCodeValue(rs.getString("model_no"));

				SpecificChargeDetail specificChargeDetail = new SpecificChargeDetail();
				specificChargeDetail.setAssetType(assetType);
				specificChargeDetail.setManufactureYear(new Long(rs.getLong("manufacture_year")));
				specificChargeDetail.setModel(model);

				asset.setSpecificChargeDetail(specificChargeDetail);

				return asset;
			}
		});
	}

	public List searchAssetPlantEquipCollaterals(List cmsCollateralIdList, Map assetPlantEquipParameters) {
		Validate.notEmpty(cmsCollateralIdList, "'cmsCollateralIdList' must not be empty");
		Validate.notEmpty(assetPlantEquipParameters, "'assetPlantEquipParameters' must not be empty.");

		List resultList = new ArrayList();

		while (securitySearchCount <= cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(0, securitySearchCount);

			resultList.addAll(doSearchAssetPlantEquipCollaterals(persistentSubList, assetPlantEquipParameters));

			persistentSubList.clear();
		}

		if (cmsCollateralIdList.size() > 0) {
			resultList.addAll(doSearchAssetPlantEquipCollaterals(cmsCollateralIdList, assetPlantEquipParameters));
		}

		return resultList;
	}

	protected List doSearchAssetPlantEquipCollaterals(List cmsCollateralIdList, Map assetPlantEquipParameters) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT asset.cms_collateral_id, type_of_asset, manufacture_year, model_no, plantequip.serial_no ");
		buf.append("FROM cms_asset asset LEFT OUTER JOIN cms_asset_plant_equip plantequip ON ");
		buf.append("asset.cms_collateral_id = plantequip.cms_collateral_id WHERE asset.cms_collateral_id IN (");
		buf.append(prepareInExpression(cmsCollateralIdList)).append(") ");

		List argList = new ArrayList();

		String plantEquipType = (String) assetPlantEquipParameters
				.get("specificChargeDetail.assetType.standardCodeValue");
		String serialNo = (String) assetPlantEquipParameters.get("plantEquipDetail.serialNo");
		Long manufactureYear = (Long) assetPlantEquipParameters.get("specificChargeDetail.manufactureYear");
		String model = (String) assetPlantEquipParameters.get("specificChargeDetail.model.standardCodeValue");

		if (StringUtils.isNotBlank(plantEquipType)) {
			buf.append("AND asset.type_of_asset = ? ");
			argList.add(plantEquipType);
		}

		if (manufactureYear != null && manufactureYear.longValue() > 0) {
			buf.append("AND asset.manufacture_year = ? ");
			argList.add(manufactureYear);
		}

		if (StringUtils.isNotBlank(model)) {
			buf.append("AND asset.model_no LIKE '%").append(model).append("' ");
		}

		if (StringUtils.isNotBlank(serialNo)) {
			buf.append("AND plantequip.serial_no LIKE '%").append(serialNo).append("' ");
		}

		return getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetSecurity asset = new AssetSecurity();
				asset.setCMSSecurityId(rs.getLong("cms_collateral_id"));

				StandardCode assetType = new StandardCode();
				assetType.setStandardCodeNumber(CategoryCodeConstant.PLANT_EQUIP_TYPE);
				assetType.setStandardCodeValue(rs.getString("type_of_asset"));

				StandardCode model = new StandardCode();
				assetType.setStandardCodeNumber("MODEL");
				assetType.setStandardCodeValue(rs.getString("model_no"));

				SpecificChargeDetail specificChargeDetail = new SpecificChargeDetail();
				specificChargeDetail.setAssetType(assetType);
				specificChargeDetail.setManufactureYear(new Long(rs.getLong("manufacture_year")));
				specificChargeDetail.setModel(model);

				PlantEquipDetail plantEquip = new PlantEquipDetail();
				plantEquip.setSerialNo(rs.getString("serial_no"));

				asset.setSpecificChargeDetail(specificChargeDetail);
				asset.setPlantEquipDetail(plantEquip);

				return asset;
			}
		});
	}

	public List searchAssetVesselCollaterals(List cmsCollateralIdList, Map assetVesselParameters) {
		Validate.notEmpty(cmsCollateralIdList, "'cmsCollateralIdList' must not be empty");
		Validate.notEmpty(assetVesselParameters, "'assetVesselParameters' must not be empty.");

		List resultList = new ArrayList();

		int fromIndex = 0;
		int toIndex = this.securitySearchCount;

		while (toIndex <= cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(fromIndex, toIndex);

			resultList.addAll(doSearchAssetVesselCollaterals(persistentSubList, assetVesselParameters));

			fromIndex = toIndex;
			toIndex += this.securitySearchCount;
		}

		if (fromIndex < cmsCollateralIdList.size()) {
			List persistentSubList = cmsCollateralIdList.subList(fromIndex, cmsCollateralIdList.size());

			resultList.addAll(doSearchAssetVesselCollaterals(persistentSubList, assetVesselParameters));
		}

		return resultList;
	}

	protected List doSearchAssetVesselCollaterals(List cmsCollateralIdList, Map assetVesselParameters) {

		StringBuffer buf = new StringBuffer();
		buf.append("SELECT asset.cms_collateral_id, type_of_asset, vessel.build_year, vessel.reg_country ");
		buf.append("FROM cms_asset asset LEFT OUTER JOIN cms_asset_vessel vessel ON ");
		buf.append("asset.cms_collateral_id = vessel.cms_collateral_id WHERE asset.cms_collateral_id IN (");
		buf.append(prepareInExpression(cmsCollateralIdList)).append(") ");

		List argList = new ArrayList();

		String vesselType = (String) assetVesselParameters.get("specificChargeDetail.assetType.standardCodeValue");
		Long builtYear = (Long) assetVesselParameters.get("vesselDetail.builtYear");
		String registeredCountry = (String) assetVesselParameters.get("vesselDetail.registeredCountry");

		if (StringUtils.isNotBlank(vesselType)) {
			buf.append("AND asset.type_of_asset = ? ");
			argList.add(vesselType);
		}

		if (builtYear != null && builtYear.longValue() > 0) {
			buf.append("AND vessel.build_year = ? ");
			argList.add(builtYear);
		}

		if (StringUtils.isNotBlank(registeredCountry)) {
			buf.append("AND vessel.reg_country = ? ");
			argList.add(registeredCountry);
		}

		return getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetSecurity asset = new AssetSecurity();
				asset.setCMSSecurityId(rs.getLong("cms_collateral_id"));

				StandardCode assetType = new StandardCode();
				assetType.setStandardCodeNumber(CategoryCodeConstant.VESSEL_TYPE);
				assetType.setStandardCodeValue(rs.getString("type_of_asset"));

				SpecificChargeDetail specificChargeDetail = new SpecificChargeDetail();
				specificChargeDetail.setAssetType(assetType);

				VesselDetail vessel = new VesselDetail();
				vessel.setBuiltYear(new Long(rs.getLong("build_year")));
				vessel.setRegisteredCountry(rs.getString("reg_country"));

				asset.setSpecificChargeDetail(specificChargeDetail);
				asset.setVesselDetail(vessel);

				return asset;
			}
		});
	}

	/**
	 * Based on the list of cms collateral id, construct a comma separated
	 * values.
	 * 
	 * @param cmsCollateralIdList list of cms collateral id
	 * @return string value consist of cms collateral ids separated by the comma
	 */
	protected String prepareInExpression(List cmsCollateralIdList) {
		StringBuffer buf = new StringBuffer();

		for (Iterator itr = cmsCollateralIdList.iterator(); itr.hasNext();) {
			Long id = (Long) itr.next();
			buf.append(id);

			if (itr.hasNext()) {
				buf.append(", ");
			}
		}

		return buf.toString();
	}

	public Long getCmsSecurityIdByOldCmsSecurityId(long oldCmsSecurityId) {
		String query = "SELECT CMS_COLLATERAL_ID FROM CMS_SECURITY WHERE CMS_COLLATERAL_ID = ?";
		try {
			long cmsSecurityId = getJdbcTemplate().queryForLong(query, new Object[] { new Long(oldCmsSecurityId) });
			return new Long(cmsSecurityId);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public Long getInsuranceIdByOldInsuranceId(long cmsCollateralId, long oldCmsInsuranceSecId) {
		String query = "SELECT INSURANCE_POLICY_ID FROM CMS_INSURANCE_POLICY WHERE CMS_COLLATERAL_ID = ? and INSURANCE_POLICY_ID = ?";
		try {
			long cmsInsurancePolicyId = getJdbcTemplate().queryForLong(query,
					new Object[] { new Long(cmsCollateralId), new Long(oldCmsInsuranceSecId) });

			return new Long(cmsInsurancePolicyId);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public Long getPledgorIdByOldCMSPledgorId(long CMSPledgorId) {
		String query = "select cms_pledgor_dtl_id from sci_pledgor_dtl where cms_pledgor_dtl_id = ?";
		try {
			long cmsid = getJdbcTemplate().queryForLong(query, new Object[] { new Long(CMSPledgorId) });

			return new Long(cmsid);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public AssetSecurity retrieveAssetVehiclePbrPbtGoodsStatusDetail(long cmsCollateralId) {
		String securitySubTypeId = (String) getJdbcTemplate().queryForObject(
				"SELECT security_sub_type_id FROM cms_security WHERE cms_collateral_id = ? ",
				new Object[] { new Long(cmsCollateralId) }, String.class);

		if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(securitySubTypeId)) {
			return (AssetSecurity) getJdbcTemplate().query(
					"SELECT good_status, pbt_pbr_indicator FROM cms_asset asset, cms_asset_vehicle veh "
							+ "WHERE asset.cms_collateral_id = veh.cms_collateral_id AND asset.cms_collateral_id = ?",
					new Object[] { new Long(cmsCollateralId) }, new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								String goodsStatus = rs.getString("good_status");
								String pbtPbrInd = rs.getString("pbt_pbr_indicator");

								StandardCode goodStatusCode = new StandardCode();
								goodStatusCode.setStandardCodeValue(goodsStatus);

								SpecificChargeDetail specificCharge = new SpecificChargeDetail();
								specificCharge.setGoodStatus(goodStatusCode);

								VehicleDetail vehicle = new VehicleDetail();
								vehicle.setPBTPBRInd(pbtPbrInd);

								AssetSecurity asset = new AssetSecurity();
								asset.setSpecificChargeDetail(specificCharge);
								asset.setVehicleDetail(vehicle);

								return asset;
							}
							return null;
						}
					});
		}
		else {
			return null;
		}
	}

	public boolean isCollateralDeleted(Long cmsCollateralId, String losSecurityId, String sourceId) {
		try {
			if (cmsCollateralId != null) {
				String collateralStatus = (String) getJdbcTemplate().queryForObject(
						"SELECT collateral_status FROM cms_security WHERE cms_collateral_id = ?",
						new Object[] { cmsCollateralId }, String.class);
				return ICMSConstant.HOST_COL_STATUS_DELETED.equals(collateralStatus);
			}
			else if (losSecurityId != null && sourceId != null) {
				String collateralStatus = (String) getJdbcTemplate().queryForObject(
						"SELECT collateral_status FROM cms_security WHERE los_security_dtl_id = ? AND source_id = ?",
						new Object[] { losSecurityId, sourceId }, String.class);
				return ICMSConstant.HOST_COL_STATUS_DELETED.equals(collateralStatus);
			}
		}
		catch (DataAccessException ex) {
			logger.warn("failed to retrieve collateral status for collateral, CMS key [" + cmsCollateralId
					+ "], LOS key [" + losSecurityId + "], Source [" + sourceId + "]", ex);
			return false;
		}

		return false;
	}
}
