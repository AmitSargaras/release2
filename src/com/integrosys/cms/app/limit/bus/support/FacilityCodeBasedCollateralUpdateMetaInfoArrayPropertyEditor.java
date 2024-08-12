package com.integrosys.cms.app.limit.bus.support;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Property Editor for building array of
 * <tt>FacilityCodeBasedCollateralUpdateMetaInfo</tt>.
 * 
 * <p>
 * Entries must be separated by a line feed.
 * 
 * <p>
 * For each entry, facility codes, collateral source id, collateral type/subtype
 * must be provided. Values are provided after the prefix.
 * <ul>
 * List of available prefix:
 * <li>FAC_CODE_
 * <li>COL_SOURCE_
 * <li>COL_TYPE_
 * <li>COL_SUBTYPE_
 * </ul>
 * for <i>FAC_CODE_</i> , <i>COL_TYPE_</i> , <i>COL_SUBTYPE_</i> , multiple
 * values can be provided, and separated by pipe character "|".
 * <p>
 * Eg, FAC_CODE_260|261,COL_TYPE_MS|GT,COL_SUBTYPE_MS601|GT401
 * 
 * @author Chong Jun Yong
 * 
 */
public class FacilityCodeBasedCollateralUpdateMetaInfoArrayPropertyEditor extends PropertyEditorSupport {

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException("no value specify for column meta info");
		}

		String[] lines = StringUtils.split(text.trim(), "\r\n");

		List facilityCodeBasedCollateralUpdateMetaInfoList = new ArrayList();
		for (int i = 0; i < lines.length; i++) {
			String[] tokens = StringUtils.split(lines[i].trim(), ',');

			FacilityCodeBasedCollateralUpdateMetaInfo.Builder builder = new FacilityCodeBasedCollateralUpdateMetaInfo.Builder();
			for (int j = 0; j < tokens.length; j++) {
				String token = tokens[j].trim();
				if (token.startsWith(FacilityCodeBasedCollateralUpdateMetaInfo.FACILITY_CODES_PREFIX)) {
					String facilityCodesValue = token
							.substring(FacilityCodeBasedCollateralUpdateMetaInfo.FACILITY_CODES_PREFIX.length());
					String[] facilityCodes = StringUtils.split(facilityCodesValue, '|');
					builder.forFacilityCodes(facilityCodes);
				}
				else if (token.startsWith(FacilityCodeBasedCollateralUpdateMetaInfo.COLLATERAL_SOURCE_ID_PREFIX)) {
					String collateralSourceId = token
							.substring(FacilityCodeBasedCollateralUpdateMetaInfo.COLLATERAL_SOURCE_ID_PREFIX.length());
					builder.forCollateralSourceId(collateralSourceId);
				}
				else if (token.startsWith(FacilityCodeBasedCollateralUpdateMetaInfo.COLLATERAL_TYPES_PREFIX)) {
					String collateralTypesValue = token
							.substring(FacilityCodeBasedCollateralUpdateMetaInfo.COLLATERAL_TYPES_PREFIX.length());
					String[] collateralTypes = StringUtils.split(collateralTypesValue, '|');
					builder.forCollateralTypes(collateralTypes);
				}
				else if (token.startsWith(FacilityCodeBasedCollateralUpdateMetaInfo.COLLATERAL_SUBTYPES_PREFIX)) {
					String collateralSubTypesValue = token
							.substring(FacilityCodeBasedCollateralUpdateMetaInfo.COLLATERAL_SUBTYPES_PREFIX.length());
					String[] collateralSubTypes = StringUtils.split(collateralSubTypesValue, '|');
					builder.forCollateralSubTypes(collateralSubTypes);
				}
				else {
					throw new IllegalArgumentException("value [" + token + "] having unknown prefix");
				}
			}

			FacilityCodeBasedCollateralUpdateMetaInfo metaInfo = builder.build();
			if (ArrayUtils.isEmpty(metaInfo.getCollateralTypes())
					&& ArrayUtils.isEmpty(metaInfo.getCollateralSubTypes())) {
				throw new IllegalArgumentException("Either Collateral Types or Collateral Sub Types must be provided");
			}

			if (ArrayUtils.isEmpty(metaInfo.getApplicableFacilityCodes())) {
				throw new IllegalArgumentException("Facility Codes must be provided");
			}

			if (metaInfo.getCollateralSourceId() == null || metaInfo.getCollateralSourceId().length() == 0) {
				throw new IllegalArgumentException("Collateral Source Id must be provided");
			}

			facilityCodeBasedCollateralUpdateMetaInfoList.add(metaInfo);
		}

		setValue((FacilityCodeBasedCollateralUpdateMetaInfo[]) facilityCodeBasedCollateralUpdateMetaInfoList
				.toArray(new FacilityCodeBasedCollateralUpdateMetaInfo[0]));
	}

}
