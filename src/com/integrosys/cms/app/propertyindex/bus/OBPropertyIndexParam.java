/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;
import java.util.Set;

/**
 * An entity to represent a property index.
 * 
 * @author Tan Pei Cheng
 * @author Andy Wong
 * @author Chong Jun Yong
 */
public class OBPropertyIndexParam implements Serializable {

	private static final long serialVersionUID = 6142088594079531121L;

	private long indexItemID;

	private String indexType;

	private int year;

	private String quarterCode;

	private String stateCode;

	private Set applicablePropertyTypes;

	private Set applicableSecSubTypes;

	private Set applicableDistricts;

	private Set applicableMukims;

	private Double index;

	private Double currIndex;

	public Set getApplicableDistricts() {
		return applicableDistricts;
	}

	public Set getApplicableMukims() {
		return applicableMukims;
	}

	public Set getApplicablePropertyTypes() {
		return applicablePropertyTypes;
	}

	public Set getApplicableSecSubTypes() {
		return applicableSecSubTypes;
	}

	public Double getCurrIndex() {
		return this.currIndex;
	}

	public Double getIndex() {
		return this.index;
	}

	public long getIndexItemID() {
		return indexItemID;
	}

	public String getIndexType() {
		return indexType;
	}

	public String getQuarterCode() {
		return quarterCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public int getYear() {
		return year;
	}

	public void setApplicableMukims(Set value) {
		this.applicableMukims = value;
	}

	public void setApplicableDistricts(Set value) {
		this.applicableDistricts = value;
	}

	public void setApplicablePropertyTypes(Set value) {
		this.applicablePropertyTypes = value;
	}

	public void setApplicableSecSubTypes(Set value) {
		this.applicableSecSubTypes = value;
	}

	public void setCurrIndex(Double value) {
		this.currIndex = value;
	}

	public void setIndex(Double value) {
		this.index = value;
	}

	public void setIndexItemID(long value) {
		this.indexItemID = value;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public void setQuarterCode(String value) {
		this.quarterCode = value;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public void setYear(int value) {
		this.year = value;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currIndex == null) ? 0 : currIndex.hashCode());
		result = prime * result + ((applicableDistricts == null) ? 0 : applicableDistricts.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((indexType == null) ? 0 : indexType.hashCode());
		result = prime * result + ((applicableMukims == null) ? 0 : applicableMukims.hashCode());
		result = prime * result + ((applicablePropertyTypes == null) ? 0 : applicablePropertyTypes.hashCode());
		result = prime * result + ((quarterCode == null) ? 0 : quarterCode.hashCode());
		result = prime * result + ((applicableSecSubTypes == null) ? 0 : applicableSecSubTypes.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + year;
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
		OBPropertyIndexParam other = (OBPropertyIndexParam) obj;
		if (currIndex == null) {
			if (other.currIndex != null) {
				return false;
			}
		}
		else if (!currIndex.equals(other.currIndex)) {
			return false;
		}
		if (applicableDistricts == null) {
			if (other.applicableDistricts != null) {
				return false;
			}
		}
		else if (!applicableDistricts.equals(other.applicableDistricts)) {
			return false;
		}
		if (index == null) {
			if (other.index != null) {
				return false;
			}
		}
		else if (!index.equals(other.index)) {
			return false;
		}
		if (indexType == null) {
			if (other.indexType != null) {
				return false;
			}
		}
		else if (!indexType.equals(other.indexType)) {
			return false;
		}
		if (applicableMukims == null) {
			if (other.applicableMukims != null) {
				return false;
			}
		}
		else if (!applicableMukims.equals(other.applicableMukims)) {
			return false;
		}
		if (applicablePropertyTypes == null) {
			if (other.applicablePropertyTypes != null) {
				return false;
			}
		}
		else if (!applicablePropertyTypes.equals(other.applicablePropertyTypes)) {
			return false;
		}
		if (quarterCode == null) {
			if (other.quarterCode != null) {
				return false;
			}
		}
		else if (!quarterCode.equals(other.quarterCode)) {
			return false;
		}
		if (applicableSecSubTypes == null) {
			if (other.applicableSecSubTypes != null) {
				return false;
			}
		}
		else if (!applicableSecSubTypes.equals(other.applicableSecSubTypes)) {
			return false;
		}
		if (stateCode == null) {
			if (other.stateCode != null) {
				return false;
			}
		}
		else if (!stateCode.equals(other.stateCode)) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBPropertyIndexParam [");
		buf.append("indexType=");
		buf.append(indexType);
		buf.append(", year=");
		buf.append(year);
		buf.append(", quarterCode=");
		buf.append(quarterCode);
		buf.append(", stateCode=");
		buf.append(stateCode);
		buf.append(", index=");
		buf.append(index);
		buf.append(", currIndex=");
		buf.append(currIndex);
		buf.append(", applicableDistricts=");
		buf.append(applicableDistricts);
		buf.append(", applicableSecSubTypes=");
		buf.append(applicableSecSubTypes);
		buf.append(", applicablePropertyTypes=");
		buf.append(applicablePropertyTypes);
		buf.append(", applicableMukims=");
		buf.append(applicableMukims);
		buf.append(", indexItemID=");
		buf.append(indexItemID);
		buf.append("]");
		return buf.toString();
	}
}
