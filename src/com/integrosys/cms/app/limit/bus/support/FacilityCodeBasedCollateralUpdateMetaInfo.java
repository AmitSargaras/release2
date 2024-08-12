package com.integrosys.cms.app.limit.bus.support;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

/**
 * Meta Info for update collateral info based on the facility codes provided.
 * 
 * @author Chong Jun Yong
 * @see FacilityCodeBasedCollateralUpdateMetaInfoArrayPropertyEditor
 * @see com.integrosys.cms.host.eai.limit.actualtrxhandler.FacilityCodeBasedCollateralUpdateHandler
 */
public final class FacilityCodeBasedCollateralUpdateMetaInfo {

	/** Prefix to be used for Property editor for facility codes */
	public static final String FACILITY_CODES_PREFIX = "FAC_CODE_";

	/** Prefix to be used for Property editor for collateral source id */
	public static final String COLLATERAL_SOURCE_ID_PREFIX = "COL_SOURCE_";

	/** Prefix to be used for Property editor for collateral types */
	public static final String COLLATERAL_TYPES_PREFIX = "COL_TYPE_";

	/** Prefix to be used for Property editor for collateral subtypes */
	public static final String COLLATERAL_SUBTYPES_PREFIX = "COL_SUBTYPE_";

	private String[] applicableFacilityCodes;

	private String collateralSourceId;

	private String[] collateralTypes;

	private String[] collateralSubTypes;

	public String[] getApplicableFacilityCodes() {
		return applicableFacilityCodes;
	}

	public String getCollateralSourceId() {
		return collateralSourceId;
	}

	public String[] getCollateralTypes() {
		return collateralTypes;
	}

	public String[] getCollateralSubTypes() {
		return collateralSubTypes;
	}

	private FacilityCodeBasedCollateralUpdateMetaInfo(Builder builder) {
		this.applicableFacilityCodes = builder.applicableFacilityCodes;
		this.collateralSourceId = builder.collateralSourceId;
		this.collateralTypes = builder.collateralTypes;
		this.collateralSubTypes = builder.collateralSubTypes;
	}

	public static class Builder {
		private String[] applicableFacilityCodes;

		private String collateralSourceId;

		private String[] collateralTypes;

		private String[] collateralSubTypes;

		public Builder forFacilityCodes(String[] applicableFacilityCodes) {
			this.applicableFacilityCodes = applicableFacilityCodes;
			return this;
		}

		public Builder forCollateralSourceId(String collateralSourceId) {
			this.collateralSourceId = collateralSourceId;
			return this;
		}

		public Builder forCollateralTypes(String[] collateralTypes) {
			this.collateralTypes = collateralTypes;
			return this;
		}

		public Builder forCollateralSubTypes(String[] collateralSubTypes) {
			this.collateralSubTypes = collateralSubTypes;
			return this;
		}

		public FacilityCodeBasedCollateralUpdateMetaInfo build() {
			return new FacilityCodeBasedCollateralUpdateMetaInfo(this);
		}
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("FacilityCodeBasedCollateralUpdateMetaInfo: ");
		buf.append("Facility Codes [").append(ArrayUtils.toString(this.applicableFacilityCodes)).append("], ");
		buf.append("Collateral Source Id [").append(this.collateralSourceId).append("], ");
		buf.append("Collateral Types [").append(ArrayUtils.toString(this.collateralTypes)).append("], ");
		buf.append("Collateral SubTypes [").append(ArrayUtils.toString(this.collateralSubTypes)).append("]");

		return buf.toString();
	}

	private static int hashCode(Object[] array) {
		int prime = 31;
		if (array == null) {
			return 0;
		}
		int result = 1;
		for (int index = 0; index < array.length; index++) {
			result = prime * result + (array[index] == null ? 0 : array[index].hashCode());
		}
		return result;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FacilityCodeBasedCollateralUpdateMetaInfo.hashCode(applicableFacilityCodes);
		result = prime * result + ((collateralSourceId == null) ? 0 : collateralSourceId.hashCode());
		result = prime * result + FacilityCodeBasedCollateralUpdateMetaInfo.hashCode(collateralSubTypes);
		result = prime * result + FacilityCodeBasedCollateralUpdateMetaInfo.hashCode(collateralTypes);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		FacilityCodeBasedCollateralUpdateMetaInfo other = (FacilityCodeBasedCollateralUpdateMetaInfo) obj;
		if (!Arrays.equals(applicableFacilityCodes, other.applicableFacilityCodes)) {
			return false;
		}

		if (collateralSourceId == null) {
			if (other.collateralSourceId != null) {
				return false;
			}
		}
		else if (!collateralSourceId.equals(other.collateralSourceId)) {
			return false;
		}

		if (!Arrays.equals(collateralSubTypes, other.collateralSubTypes)) {
			return false;
		}

		if (!Arrays.equals(collateralTypes, other.collateralTypes)) {
			return false;
		}

		return true;
	}

}
