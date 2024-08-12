/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecuritySubType.java,v 1.1 2003/10/28 08:40:07 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus;

/**
 * Security sub type class.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/10/28 08:40:07 $ Tag: $Name: $
 */
public class SecuritySubType implements java.io.Serializable {
	private String subTypeCode;

	private String subTypeName;

	private String typeCode;

	private String typeName;

	/**
	 * Default Constructor.
	 */
	public SecuritySubType() {
		super();
	}

	/**
	 * Get security subtype code.
	 * 
	 * @return String
	 */
	public String getSubTypeCode() {
		return subTypeCode;
	}

	/**
	 * Set security subtype code.
	 * 
	 * @param subTypeCode of type String
	 */
	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}

	/**
	 * Get security subtype name.
	 * 
	 * @return String
	 */
	public String getSubTypeName() {
		return subTypeName;
	}

	/**
	 * Set security subtype name.
	 * 
	 * @param subTypeName of type String
	 */
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	/**
	 * Set security type code.
	 * 
	 * @return String
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * Set security type code.
	 * 
	 * @param typeCode of type String
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * Get security type name.
	 * 
	 * @return String
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Set security type name.
	 * 
	 * @param typeName of type String
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
