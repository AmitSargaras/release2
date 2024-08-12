/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This class represents collateral type, such as asset, property, document,
 * cash, insurance, etc.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2003/06/20
 */
public class OBCollateralType implements ICollateralType {

	private static final long serialVersionUID = -6210114152808581234L;

	private String typeCode;

	private String typeName;

	/**
	 * Default Constructor.
	 */
	public OBCollateralType() {
	}

	public OBCollateralType(String typeCode) {
		this(typeCode, null);
	}

	public OBCollateralType(String typeCode, String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralType
	 */
	public OBCollateralType(ICollateralType obj) {
		this(obj.getTypeCode(), obj.getTypeName());
	}

	/**
	 * Get collateral type code.
	 * 
	 * @return String
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * Set collateral type code.
	 * 
	 * @param typeCode is of type String
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * Get collateral type name.
	 * 
	 * @return String
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Set collateral type name.
	 * 
	 * @param typeName is of type String
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBCollateralType [");
		buf.append("typeCode=");
		buf.append(typeCode);
		buf.append(", typeName=");
		buf.append(typeName);
		buf.append("]");
		return buf.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
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
		OBCollateralType other = (OBCollateralType) obj;
		if (typeCode == null) {
			if (other.typeCode != null) {
				return false;
			}
		}
		else if (!typeCode.equals(other.typeCode)) {
			return false;
		}
		return true;
	}

}