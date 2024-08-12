package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GenericValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.model.PropertyValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.LandAreaMeasure;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIndexParam;
import com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters;

/**
 * <p>
 * Implementation of <tt>IPropertyValuationDAO</tt> to use Jdbc to retrieve
 * valuation meta info.
 * <p>
 * Property Valuation considering Manual Valuation, Source Valuation and Sales &
 * Purchase value as base.
 * 
 * @author Andy Wong
 * @author Cynthia Zhou
 */
public class PropertyValuationDAO extends GenericValuationDAO implements IPropertyValuationDAO {

	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		List errorList = new ArrayList();

		retrievePropertyParam(valModel);
		retrieveValuationParams(valModel, errorList);

		PropertyValuationModel pValModel = (PropertyValuationModel) valModel;

		Map prevValMap = super.retrievePrevValuationInfo(pValModel.getCollateralId(), null);
		prevValMap.remove(ICMSConstant.VALUATION_SOURCE_TYPE_A);

		List valuationModelList = new ArrayList(prevValMap.values());
		if (pValModel.getSpValue() != null && pValModel.getSpValue().getAmount() != 0d && pValModel.getSpDate() != null) {
			IValuationModel saleAndPurchase = new GenericValuationModel();
			saleAndPurchase.setValOMV(pValModel.getSpValue());
			saleAndPurchase.setValuationDate(pValModel.getSpDate());
			valuationModelList.add(saleAndPurchase);
		}

		Collections.sort(valuationModelList, new Comparator() {

			public int compare(Object thisObject, Object thatObject) {
				IValuationModel thisModel = (IValuationModel) thisObject;
				IValuationModel thatModel = (IValuationModel) thatObject;
				Date thisValuationDate = (thisModel == null) ? null : thisModel.getValuationDate();
				Date thatValuationDate = (thatModel == null) ? null : thatModel.getValuationDate();

				if (thisValuationDate != null) {
					return (thatValuationDate == null) ? 1 : (thisValuationDate.compareTo(thatValuationDate) * -1);
				}

				return (thatValuationDate == null) ? 0 : 1;
			}
		});

		IValuationModel latestValuation = (valuationModelList.isEmpty()) ? null : (IValuationModel) valuationModelList
				.get(0);

		if (latestValuation != null) {
			pValModel.setCurrentOMV(latestValuation.getValOMV());
			pValModel.setSpDate(latestValuation.getValuationDate());
			pValModel.setSpValue(latestValuation.getValOMV());
		}
		else {
			pValModel.setSpDate(null);
			pValModel.setSpValue(null);
		}
	}

	private void retrievePropertyParam(IValuationModel valModel) throws ValuationException {
		final PropertyValuationModel pValModel = (PropertyValuationModel) valModel;
		String query = "SELECT SEC.CMV_CURRENCY, SEC.CMV, SEC.SCI_SECURITY_CURRENCY, PR.POSTCODE, PR.STATE, PR.DISTRICT, PR.MUKIM, "
				+ "PR.LAND_AREA, PR.LAND_AREA_UOM, PR.BUILTUP_AREA, PR.BUILTUP_AREA_UOM, "
				+ "PR.SALE_PURCHASE_VALUE, PR.SALE_PURCHASE_DATE, PR.PROPERTY_TYPE, PR.PROPERTY_COMPLETION_STATUS, "
				+ "PR.CATEGORY_OF_LAND_USE "
				+ "FROM CMS_SECURITY SEC, CMS_PROPERTY PR "
				+ "WHERE SEC.CMS_COLLATERAL_ID = ? AND " + "SEC.CMS_COLLATERAL_ID = PR.CMS_COLLATERAL_ID ";

		getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					// String cmvCur = rs.getString("CMV_CURRENCY");
					// String cmv = rs.getString("CMV");
					String postCode = rs.getString("POSTCODE");
					String state = rs.getString("STATE");
					String district = rs.getString("DISTRICT");
					String mukim = rs.getString("MUKIM");
					String landArea = rs.getString("LAND_AREA");
					String landUom = rs.getString("LAND_AREA_UOM");
					String buildArea = rs.getString("BUILTUP_AREA");
					String buildUom = rs.getString("BUILTUP_AREA_UOM");

					String spCur = rs.getString("SCI_SECURITY_CURRENCY");
					String spValue = rs.getString("SALE_PURCHASE_VALUE");
					Date spDate = rs.getDate("SALE_PURCHASE_DATE");
					String propType = rs.getString("PROPERTY_TYPE");
					String propCompletionStatus = rs.getString("PROPERTY_COMPLETION_STATUS");

					if (spCur != null && spValue != null && spDate != null
							&& spValue != String.valueOf(ICMSConstant.DOUBLE_INVALID_VALUE)) {
						pValModel.setSpValue(new Amount(Double.parseDouble(spValue), spCur));
						pValModel.setSpDate(spDate);
					}
					pValModel.setPropertyType(propType);
					pValModel.setPropertyCompletionStatus(propCompletionStatus);
					pValModel.setPostCode(postCode);
					pValModel.setStateCode(state);
					pValModel.setDistrictCode(district);
					pValModel.setMukimCode(mukim);
					pValModel.setCategoryOfLandUse(rs.getString("CATEGORY_OF_LAND_USE"));
					if ((landArea != null) && (landUom != null)) {
						LandAreaMeasure measure1 = new LandAreaMeasure();
						measure1.setFrom(Double.parseDouble(landArea));
						measure1.setFromUnit(landUom);
						pValModel.setLandArea(measure1);
					}
					if ((buildArea != null) && (buildUom != null)) {
						LandAreaMeasure measure2 = new LandAreaMeasure();
						measure2.setFrom(Double.parseDouble(buildArea));
						measure2.setFromUnit(buildUom);
						pValModel.setBuildupArea(measure2);
					}
				}

				return null;
			}
		});

	}

	public void retrieveValuationProfile(final List propertyValuationProfileParamList) throws ValuationException {
		String query = "SELECT PP.PARAMETER_ID, PP.OMV_TYPE, PP.VARIATION_OMV, "
				+ "PP.COLLATERAL_SUBTYPE, PP.COUNTRY_CODE, PP.STATE_CODE, "
				+ "PP.DISTRICT_CODE, PP.MUKIM_CODE, PP.POST_CODE, PP.MINIMUM_CURRENT_OMV, "
				+ "PP.LAND_AREA_VALUE_FROM, PP.LAND_AREA_UNIT_FROM, " + "PP.LAND_AREA_VALUE_TO, PP.LAND_AREA_UNIT_TO, "
				+ "PP.BUILDUP_AREA_VALUE_FROM, PP.BUILDUP_AREA_UNIT_FROM, "
				+ "PP.BUILDUP_AREA_VALUE_TO, PP.BUILDUP_AREA_UNIT_TO " + "FROM CMS_PROPERTY_PARAMETERS PP "
				+ "ORDER BY PP.VERSION_TIME DESC";

		List resultList = getJdbcTemplate().query(query, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBPropertyParameters propertyParam = new OBPropertyParameters();
				propertyParam.setParameterId(rs.getLong("PARAMETER_ID"));
				propertyParam.setOmvType(rs.getString("OMV_TYPE"));
				propertyParam.setVariationOMV(rs.getLong("VARIATION_OMV"));
				propertyParam.setCollateralSubType(rs.getString("COLLATERAL_SUBTYPE"));
				propertyParam.setCountryCode(rs.getString("COUNTRY_CODE"));
				propertyParam.setStateCode(rs.getString("STATE_CODE"));
				propertyParam.setDistrictCode(rs.getString("DISTRICT_CODE"));
				propertyParam.setMukimCode(rs.getString("MUKIM_CODE"));
				propertyParam.setPostcode(rs.getString("POST_CODE"));
				String minOmv = rs.getString("MINIMUM_CURRENT_OMV");
				if (minOmv != null) {
					propertyParam.setMinimumCurrentOmv(Double.parseDouble(minOmv));
				}
				else {
					propertyParam.setMinimumCurrentOmv(ICMSConstant.DOUBLE_INVALID_VALUE);
				}

				String landAreaFrom = rs.getString("LAND_AREA_VALUE_FROM");
				String landAreaUomFrom = rs.getString("LAND_AREA_UNIT_FROM");
				String landAreaTo = rs.getString("LAND_AREA_VALUE_TO");
				String landAreaUomTo = rs.getString("LAND_AREA_UNIT_TO");

				String buildAreaFrom = rs.getString("BUILDUP_AREA_VALUE_FROM");
				String buildAreaUomFrom = rs.getString("BUILDUP_AREA_UNIT_FROM");
				String buildAreaTo = rs.getString("BUILDUP_AREA_VALUE_TO");
				String buildAreaUomTo = rs.getString("BUILDUP_AREA_UNIT_TO");

				if ((landAreaFrom == null) || (landAreaUomFrom == null)) {
					propertyParam.setLandAreaValueFrom(ICMSConstant.LONG_INVALID_VALUE);
				}
				else {
					propertyParam.setLandAreaValueFrom(Long.parseLong(landAreaFrom));
					propertyParam.setLandAreaUnitFrom(landAreaUomFrom);
				}

				if ((landAreaTo == null) || (landAreaUomTo == null)) {
					propertyParam.setLandAreaValueTo(-1);
				}
				else {
					propertyParam.setLandAreaValueTo(Long.parseLong(landAreaTo));
					propertyParam.setLandAreaUnitTo(landAreaUomTo);
				}

				if ((buildAreaFrom == null) || (buildAreaUomFrom == null)) {
					propertyParam.setBuildupAreaValueFrom(-1);
				}
				else {
					propertyParam.setBuildupAreaValueFrom(Long.parseLong(buildAreaFrom));
					propertyParam.setBuildupAreaUnitFrom(buildAreaUomFrom);
				}

				if ((buildAreaTo == null) || (buildAreaUomTo == null)) {
					propertyParam.setBuildupAreaValueTo(-1);
				}
				else {
					propertyParam.setBuildupAreaValueTo(Long.parseLong(buildAreaTo));
					propertyParam.setBuildupAreaUnitTo(buildAreaUomTo);
				}

				return propertyParam;
			}

		});

		propertyValuationProfileParamList.addAll(resultList);
	}

	public void retrieveValuationProfileByPropIndex(final HashMap propIndexMap) {
		String query = "SELECT p.PROPERTY_IDX_ID PROPERTY_IDX_ID, s.SECURITY_SUB_TYPE_ID, p.VAL_DESCR, "
				+ "i.STATE_CODE, i.IDX_YEAR, i.IDX_TYPE, i.IDX_VALUE, i.PROPERTY_IDX_ITEM_ID, "
				+ "d.DISTRICT_CODE, m.MUKIM_CODE, t.PROPERTY_TYPE_ID "
				+ "FROM CMS_PROPERTY_IDX p, CMS_PROPERTY_IDX_SEC_SUBTYPE s, CMS_PROPERTY_IDX_ITEM i  "
				+ "LEFT OUTER JOIN CMS_PROPERTY_IDX_DISTRICT_CODE d "
				+ "ON i.PROPERTY_IDX_ITEM_ID = d.PROPERTY_IDX_ITEM_ID " + "AND d.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' "
				+ "LEFT OUTER JOIN CMS_PROPERTY_IDX_MUKIM_CODE m "
				+ "ON i.PROPERTY_IDX_ITEM_ID = m.PROPERTY_IDX_ITEM_ID "
				+ "AND m.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' "
				+ "LEFT OUTER JOIN CMS_PROPERTY_IDX_PROPERTY_TYPE t "
				+ "ON i.PROPERTY_IDX_ITEM_ID = t.PROPERTY_IDX_ITEM_ID "
				+ "AND t.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' "
				+ "WHERE "
				+ "p.PROPERTY_IDX_ID = i.PROPERTY_IDX_ID "
				+ "AND p.PROPERTY_IDX_ID = s.PROPERTY_IDX_ID "
				+ "AND p.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' "
				+ "AND s.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' "
				+ "AND i.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' "
				+ "AND COUNTRY_CODE = 'MY' "
				+ "ORDER BY VAL_DESCR, PROPERTY_IDX_ID desc, PROPERTY_IDX_ITEM_ID desc ";

		getJdbcTemplate().query(query, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String prevIndexID = null;
				String prevIndexItemID = null;
				String prevKey = null;
				OBPropertyIndexParam prevParam = null;
				List list = new ArrayList();
				Set propertyTypes = new HashSet();
				Set secSubTypes = new HashSet();
				Set districts = new HashSet();
				Set mukims = new HashSet();

				while (rs.next()) {
					String indexID = rs.getString("PROPERTY_IDX_ID");
					String indexItemID = rs.getString("PROPERTY_IDX_ITEM_ID");
					String key = rs.getString("VAL_DESCR");

					if ((prevIndexID != null && !prevIndexID.equals(indexID))
							|| (prevIndexItemID != null && !prevIndexItemID.equals(indexItemID))) {
						prevParam.setApplicablePropertyTypes(propertyTypes);
						prevParam.setApplicableSecSubTypes(secSubTypes);
						prevParam.setApplicableDistricts(districts);
						prevParam.setApplicableMukims(mukims);
						list.add(prevParam);
						prevParam = null;
						propertyTypes = new HashSet();
						secSubTypes = new HashSet();
						districts = new HashSet();
						mukims = new HashSet();
					}
					if (prevKey != null && !prevKey.equals(key)) {
						propIndexMap.put(prevKey, list);
						list = new ArrayList();
					}

					if (prevParam == null) {
						prevParam = new OBPropertyIndexParam();
					}

					String propTypeCode = rs.getString("PROPERTY_TYPE_ID");
					String secSubTypeCode = rs.getString("SECURITY_SUB_TYPE_ID");
					String districtCode = rs.getString("DISTRICT_CODE");
					String mukimCode = rs.getString("MUKIM_CODE");

					propertyTypes.add(propTypeCode);
					secSubTypes.add(secSubTypeCode);
					districts.add(districtCode);
					mukims.add(mukimCode);

					prevParam.setIndexType(key);
					prevParam.setIndexItemID(Long.parseLong(indexItemID));
					prevParam.setStateCode(rs.getString("STATE_CODE"));
					prevParam.setIndex(new Double(rs.getDouble("IDX_VALUE")));
					prevParam.setYear(rs.getInt("IDX_YEAR"));
					prevParam.setQuarterCode(rs.getString("IDX_TYPE"));
					prevKey = key;
					prevIndexID = indexID;
					prevIndexItemID = indexItemID;
				}

				if (prevIndexID != null && prevIndexItemID != null) {
					prevParam.setApplicablePropertyTypes(propertyTypes);
					prevParam.setApplicableSecSubTypes(secSubTypes);
					prevParam.setApplicableDistricts(districts);
					prevParam.setApplicableMukims(mukims);
					list.add(prevParam);

				}
				if (prevKey != null) {
					propIndexMap.put(prevKey, list);
				}
				return null;
			}
		});
	}
}
